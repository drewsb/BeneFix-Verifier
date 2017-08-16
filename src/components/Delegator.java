package components;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import components.Main.Carrier;
import plan.Plan;
import plan.PlanWarning;
import verifier.DentalVerifier;
import verifier.MedicalVerifier;
import verifier.Verifier;
import verifier.VisionVerifier;
import components.Main.PlanType;

// TODO: Auto-generated Javadoc
/**
 * The Class Delegator.
 *
 * @param <E> the element type
 */
public class Delegator<E extends Plan> extends SwingWorker<ArrayList<Report<? extends Plan>>, String> {

	/** The carrier type. */
	final Carrier carrierType;

	/** The plan type. */
	final PlanType planType;

	/** The selected plans. */
	final ArrayList<File> selectedPlans;

	/** The text area. */
	final JTextArea textArea;

	/** The bar. */
	final JProgressBar bar;

	/** The mon check. */
	final Boolean monCheck;

	/** The cv check. */
	final Boolean cvCheck;

	/** The inc check. */
	final Boolean incCheck;

	/** The pdf check. */
	final Boolean pdfCheck;

	/** The index. */
	int index;

	/** The size. */
	int size;

	/** The start date. */
	String start_date;

	/** The end date. */
	String end_date;

	/** The reports. */
	ArrayList<Report<? extends Plan>> reports;

	/** The plans. */
	ArrayList<E> plans;

	/**
	 * Instantiates a new delegator.
	 *
	 * @param carrierType the carrier type
	 * @param planType the plan type
	 * @param selectedPlans the selected plans
	 * @param monCheck the mon check
	 * @param cvCheck the cv check
	 * @param incCheck the inc check
	 * @param pdfCheck the pdf check
	 * @param textArea the text area
	 * @param bar the bar
	 */
	public Delegator(final Carrier carrierType, PlanType planType, final ArrayList<File> selectedPlans,
			final Boolean monCheck, final Boolean cvCheck, final Boolean incCheck, final Boolean pdfCheck,
			final JTextArea textArea, final JProgressBar bar) {
		this.carrierType = carrierType;
		this.planType = planType;
		this.selectedPlans = selectedPlans;
		this.monCheck = monCheck;
		this.cvCheck = cvCheck;
		this.incCheck = incCheck;
		this.pdfCheck = pdfCheck;
		this.textArea = textArea;
		this.bar = bar;
	}

	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected ArrayList<Report<? extends Plan>> doInBackground() throws Exception {
		int multiplier = (monCheck) ? 1 : 0; // If checking for monotonocity,
												// increase total runtime
		multiplier += (cvCheck) ? 1 : 0; // CV check
		multiplier += (incCheck) ? 1 : 0; // pdf check
		multiplier += (pdfCheck) ? 1 : 0; // pdf check
		multiplier += 1; // Stats check (will always occur)
		size = selectedPlans.size() * multiplier;
		index = 0;
		if (!selectedPlans.isEmpty()) {
			verifyPlans();
		}
		return reports;
	}

	/**
	 * Removes the file extension.
	 *
	 * @param input the input
	 * @return the string
	 */
	public static String removeFileExtension(String input) {
		return input.substring(0, input.lastIndexOf("."));
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 * @throws Exception the exception
	 */
	public ArrayList<Report<? extends Plan>> getValue() throws Exception {
		return this.get();
	}

	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#process(java.util.List)
	 */
	@Override
	protected void process(final List<String> chunks) {
		// Updates the messages text area
		// System.out.println("WHEWHHEW");
		for (final String string : chunks) {
			textArea.append(string);
		}
	}

	/**
	 * Verify plans.
	 *
	 * @throws EncryptedDocumentException the encrypted document exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws OpenXML4JException the open XML 4 J exception
	 */
	public void verifyPlans() throws EncryptedDocumentException, IOException, OpenXML4JException {
		reports = new ArrayList<Report<? extends Plan>>();
		String output;
		for (File selectedPlan : selectedPlans) {
			String filename = removeFileExtension(selectedPlan.getName());
			output = String.format("Performing verification tests for %s.\n", filename);
			System.out.println(output);
			publish(output + "\n");

			Verifier<E> planVerifier = getVerifier(planType, new XSSFWorkbook(selectedPlan));
			ArrayList<E> plans;
			ArrayList<PlanWarning> warnings;

			planVerifier.generatePlans();
			plans = (ArrayList<E>) planVerifier.getPlans();
			Double numPlans = (double) plans.size();

			output = String.format("Statistics computed.");
			System.out.println(output);
			publish(output + "\n");
			index++;
			setProgress(100 * (index) / size);

			if (monCheck) {
				planVerifier.verifyMonotonicity();
				output = "Monotonocity checks complete.";
				System.out.println(output);
				publish(output + "\n");
				index++;
				setProgress(100 * (index) / size);
			}

			if (cvCheck) {
				planVerifier.verifyCV();
				output = "Covariance checks complete.";
				System.out.println(output);
				publish(output + "\n");
				index++;
				setProgress(100 * (index) / size);
			}

			if (incCheck) {
				planVerifier.verifyIncrements();
				output = "Increment checks complete.";
				System.out.println(output);
				publish(output + "\n");
				index++;
				setProgress(100 * (index) / size);
			}

			if (pdfCheck) {
				output = "\nBeginning PDF verification tests.\n";
				publish(output);
				Double plan_index = 0.0;
				for (E plan : plans) {
					if (plan.hasPDFUrl()) {
						planVerifier.verifyPDFMapping(plan);
						Double progress = 100 * (index + (++plan_index / numPlans)) / size;
						setProgress(progress.intValue());

						output = String.format("PDF check complete for plan %s.", plan.getProductName());
						System.out.println(output);
						publish(output + "\n");
					}
				}
				output = "PDF checks complete.\n";
				System.out.println(output);
				publish(output + "\n");
				index++;
			}

			warnings = planVerifier.getWarnings();
			if (!warnings.isEmpty()) {
				for (PlanWarning w : warnings) {
					publish("WARNING: " + w.getWarningMessage() + "\n");
				}
				publish("\n");
			}
			Report<E> report = new Report<E>(filename, plans, warnings, planVerifier.getAttributeIndexMap());
			report.hasTobbacoRates = plans.get(0).hasTobaccoRates();
			reports.add(report);
			output = String.format("File: %s verified\nTotal Errors: %d\nTotal Warnings: %d\n", filename,
					report.getTotalErrorSize(), report.getTotalWarningSize());
			System.out.println(output);
			publish(output + "\n");
		}
		output = "Verification complete.\n";
		System.out.println(output);
		publish(output + "\n");
		index++;
	}

	/**
	 * Gets the verifier.
	 *
	 * @param type the type
	 * @param workbook the workbook
	 * @return the verifier
	 * @throws EncryptedDocumentException the encrypted document exception
	 * @throws InvalidFormatException the invalid format exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("unchecked")
	public Verifier<E> getVerifier(PlanType type, XSSFWorkbook workbook)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		switch (type) {
		case Medical:
			return (Verifier<E>) new MedicalVerifier(workbook, carrierType);
		case Dental:
			return (Verifier<E>) new DentalVerifier(workbook);
		case Vision:
			return (Verifier<E>) new VisionVerifier(workbook);
		}

		return null;
	}

}
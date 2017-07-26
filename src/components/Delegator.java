package components;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import components.Main.Carrier;
import components.Main.State;
import components.Main.PlanType;

public class Delegator<E extends Plan> extends SwingWorker<ArrayList<Report<? extends Plan>>, String> {

	final Carrier carrierType;

	final PlanType planType;

	final ArrayList<File> selectedPlans;

	final JTextArea textArea;

	final JProgressBar bar;

	final Boolean monCheck;

	final Boolean cvCheck;

	final Boolean pdfCheck;

	int index;

	int size;

	String start_date;

	String end_date;

	ArrayList<Report<? extends Plan>> reports;
	
	ArrayList<E> plans;

	public Delegator(final Carrier carrierType, PlanType planType, final ArrayList<File> selectedPlans,
			final Boolean monCheck, final Boolean cvCheck, final Boolean pdfCheck, final JTextArea textArea,
			final JProgressBar bar) {
		this.carrierType = carrierType;
		this.planType = planType;
		this.selectedPlans = selectedPlans;
		this.monCheck = monCheck;
		this.cvCheck = cvCheck;
		this.pdfCheck = pdfCheck;
		this.textArea = textArea;
		this.bar = bar;
	}

	@Override
	protected ArrayList<Report<? extends Plan>> doInBackground() throws Exception {
		int multiplier = (monCheck) ? 1 : 0; // If checking for monotonocity,
												// increase total runtime
		multiplier += (cvCheck) ? 1 : 0; // CV check
		multiplier += (pdfCheck) ? 1 : 0; // pdf check
		multiplier += 1; // Stats check (will always occur)
		size = selectedPlans.size() * multiplier;
		index = 0;
		if (!selectedPlans.isEmpty()) {
			verifyPlans();
		}
		return reports;
	}

	public static String removeFileExtension(String input) {
		return input.substring(0, input.lastIndexOf("."));
	}

	public ArrayList<Report<? extends Plan>> getValue() throws Exception {
		return this.get();
	}

	@Override
	protected void process(final List<String> chunks) {
		// Updates the messages text area
		// System.out.println("WHEWHHEW");
		for (final String string : chunks) {
			textArea.append(string);
		}
	}

	@SuppressWarnings("unchecked")
	public void verifyPlans() throws EncryptedDocumentException, IOException, OpenXML4JException {
		reports = new ArrayList<Report<? extends Plan>>();
		String output;
		for (File selectedPlan : selectedPlans) {
			String filename = selectedPlan.getName();
			output = String.format("Performing verification tests for %s.\n", filename);
			System.out.println(output);
			publish(output + "\n");

			Verifier<E> planVerifier = getVerifier(planType, new XSSFWorkbook(selectedPlan));
			ArrayList<E> plans;
			
			planVerifier.generatePlans();
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

			if (pdfCheck) {
				planVerifier.verifyPDFMapping();
				output = "PDF checks complete.\n";
				System.out.println(output);
				publish(output + "\n");
				index++;
				setProgress(100 * (index) / size);
			}

			plans = (ArrayList<E>) planVerifier.getPlans();
			Report<E> report = new Report<E>(filename, plans);
			report.hasTobbacoRates = plans.get(0).hasTobaccoRates();
			reports.add(report);
			output = String.format("File: %s verified\nTotal Errors: %d\n", filename, report.getTotalErrorSize());
			System.out.println(output);
			publish(output + "\n");
		}
		output = "Verification complete.\n";
		System.out.println(output);
		publish(output + "\n");
		index++;
	}

	public Verifier getVerifier(PlanType type, XSSFWorkbook workbook)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		switch (type) {
		case Medical:
			return new MedicalVerifier(workbook);
		case Dental:
			return new DentalVerifier(workbook);
		case Vision:
			return new VisionVerifier(workbook);
		}

		return null;
	}

}
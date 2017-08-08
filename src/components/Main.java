package components;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker.StateValue;
import javax.swing.UIManager;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import components.Main.State;
import plan.DentalPlan;
import plan.MedicalPlan;
import plan.Plan;
import plan.VisionPlan;
import verifier.Verifier;

public class Main extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	static private final String newline = "\n";

	JButton uploadButton, verifyButton, clearButton;
	JComboBox<String> typeBox;
	JComboBox<String> carrierBox;
	JComboBox<String> stateBox;
	ArrayList<File> selectedPlans;
	JProgressBar progressBar;
	public JTextArea log;
	JFileChooser fc;
	Delegator<?> delegator;
	Verifier<Plan> verifier;
	ArrayList<Report<? extends Plan>> reports;
	JCheckBox monBox, cvBox, incBox, pdfBox, statisticsBox, errorSummaryBox;
	File inputFile;
	String filename;
	Boolean done;
	String year;
	String selectedOperation;
	State selectedState;
	Carrier carrierType;
	PlanType planType;
	HashMap<String, Set<String>> medicalCarriers;
	HashMap<String, Set<String>> dentalCarriers;
	HashMap<String, Set<String>> sourceCarriers;

	public enum Carrier {
		Anthem, UPMC, Aetna, CPA, NEPA, WPA, IBC, CBC, AmeriHealth, UHC, Oxford, Cigna, Horizon, Geisinger, Delta, NONE
	}

	public enum State {
		NJ, PA, CA, OH
	}

	public enum PlanType {
		Medical, Dental, Vision
	}
	
	public static HashMap<String, Carrier> carrierAbbrevMap = new HashMap<String, Carrier>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("Highmark Northeastern PA", Carrier.NEPA);
			put("Highmark BLUE Shield", Carrier.CPA);
			put("Independence", Carrier.IBC);
			put("UnitedHealthcare", Carrier.UHC);
			put("United Oxford", Carrier.Oxford);
			put("Capital BLUE", Carrier.CBC);
			put("Highmark Western", Carrier.WPA);
		}
	};

	public Main() {
		super(new BorderLayout());
		selectedPlans = new ArrayList<File>();
		reports = new ArrayList<Report<? extends Plan>>();
		medicalCarriers = new HashMap<String, Set<String>>();
		dentalCarriers = new HashMap<String, Set<String>>();
		sourceCarriers = medicalCarriers;
		year = "2017";

		selectedPlans = new ArrayList<File>();

		// Create the log first, because the action listeners
		// need to refer to it.
		log = new JTextArea(20, 100);
		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);

		// Create a file chooser
		fc = new JFileChooser();

		fc.setMultiSelectionEnabled(true);

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		Dimension dim = new Dimension();
		dim.setSize(300, 20);
		progressBar.setPreferredSize(dim);

		// Create the open button. We use the image from the JLF
		// Graphics Repository (but we extracted it from the jar).

		uploadButton = new JButton("Upload plans");
		uploadButton.addActionListener(this);

		verifyButton = new JButton("Verify");
		verifyButton.addActionListener(this);

		clearButton = new JButton("Clear plans");
		clearButton.addActionListener(this);
		
		errorSummaryBox = new JCheckBox("Generate Error Summary");
		statisticsBox = new JCheckBox("Generate Statistics Summary");

		monBox = new JCheckBox("Monotonocity");
		cvBox = new JCheckBox("Covariance");
		incBox = new JCheckBox("Increment");
		pdfBox = new JCheckBox("PDF Matching");

		// Options for the JComboBox

		String[] PAcorps = { "Aetna", "UPMC", "CPA", "NEPA", "WPA", "IBC", "CBC", "Geisinger", "UHC" };
		Set<String> PAcarriers = new HashSet<String>(Arrays.asList(PAcorps));
		medicalCarriers.put("PA", PAcarriers);

		String[] NJcorps = { "AmeriHealth", "Aetna", "Cigna", "Horizon", "Oxford" };
		Set<String> NJcarriers = new HashSet<String>(Arrays.asList(NJcorps));
		medicalCarriers.put("NJ", NJcarriers);

		String[] CAcorps = { "Anthem", "HealthNet", "Kaiser", "Sharp", "Sutter", "UHC", "Western" };
		Set<String> CAcarriers = new HashSet<String>(Arrays.asList(CAcorps));
		medicalCarriers.put("CA", CAcarriers);

		String[] OHcorps = { "Anthem" };
		Set<String> OHcarriers = new HashSet<String>(Arrays.asList(OHcorps));
		medicalCarriers.put("OH", OHcarriers);

		String[] PA_dental = { "Delta", "Oxford" };
		Set<String> PA_dental_carriers = new HashSet<String>(Arrays.asList(PA_dental));
		dentalCarriers.put("PA", PA_dental_carriers);

		String[] NJ_dental = {};
		Set<String> NJ_dental_carriers = new HashSet<String>(Arrays.asList(NJ_dental));
		dentalCarriers.put("NJ", NJ_dental_carriers);

		String[] OH_dental = {};
		Set<String> OH_dental_carriers = new HashSet<String>(Arrays.asList(OH_dental));
		dentalCarriers.put("OH", OH_dental_carriers);

		String[] sheets = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
				"17", "18", "19", "20" };

		String[] quarters = { "Q1", "Q2", "Q3", "Q4" };

		Set<String> states = medicalCarriers.keySet();

		String[] selection = { "Compare", "Merge" };

		String[] types = { "Medical", "Dental", "Vision" };

		JLabel carrierLbl = new JLabel("Carrier:");
		JLabel typeLbl = new JLabel("Type: ");
		JLabel stateLbl = new JLabel("State: ");
		carrierBox = new JComboBox<String>(
				medicalCarriers.get("PA").toArray(new String[medicalCarriers.get("PA").size()]));
		typeBox = new JComboBox<String>(types);
		stateBox = new JComboBox<String>(states.toArray(new String[states.size()]));

		JPanel progressPanel = new JPanel();
		progressPanel.add(progressBar);
		
		JPanel sheetPanel = new JPanel();
		sheetPanel.add(errorSummaryBox);
		sheetPanel.add(statisticsBox);
		
		JPanel boxPanel = new JPanel();
		boxPanel.add(monBox);
		boxPanel.add(cvBox);
		boxPanel.add(incBox);
		boxPanel.add(pdfBox);


		JPanel typePanel = new JPanel();
		typePanel.add(typeLbl);
		typePanel.add(typeBox);
		typePanel.add(carrierLbl);
		typePanel.add(carrierBox);
		typePanel.add(stateLbl);
		typePanel.add(stateBox);
		// For layout purposes, put the buttons in a separate panel
		JPanel buttonPanel = new JPanel(); // use FlowLayout
		buttonPanel.add(uploadButton);
		buttonPanel.add(verifyButton);
		buttonPanel.add(clearButton);

		JPanel overall = new JPanel(new GridLayout(5, 1));
		overall.add(progressPanel);
		overall.add(sheetPanel);
		overall.add(boxPanel);
		overall.add(typePanel);
		overall.add(buttonPanel);

		// Add the buttons and the log to this panel.
		add(overall, BorderLayout.PAGE_START);
		add(logScrollPane, BorderLayout.SOUTH);

		// Remember to delete default settings
		stateBox.setSelectedItem("PA");
		carrierBox.setSelectedItem("Aetna");
		typeBox.setSelectedItem("Medical");

		/*
		 * By default, all check boxes are selected
		 */
		monBox.setSelected(true);
		cvBox.setSelected(true);
		incBox.setSelected(true);
		pdfBox.setSelected(false);
		
		errorSummaryBox.setSelected(true);
		statisticsBox.setSelected(true);
	}

	public void actionPerformed(ActionEvent e) {
		// Handle open plan button action.
		if (e.getSource() == uploadButton) {
			progressBar.setValue(0);
			int returnVal = fc.showOpenDialog(Main.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				selectedPlans = new ArrayList<File>(Arrays.asList(fc.getSelectedFiles()));

				// This is where a real application would open the file.
				for (File f : selectedPlans) {
					log.append("Opening: " + f.getName() + "." + newline);
				}
			} else {
				log.append("Open command cancelled by user." + newline);
			}
			log.setCaretPosition(log.getDocument().getLength());
		} else if (e.getSource() == verifyButton) {
			if (selectedPlans.isEmpty()) {
				log.append("No file selected.\n" + newline);
				return;
			}

			/*
			 * Retrieve user inputs and set the according variables
			 */
			checkCarrier();
			checkPlan();
			checkState();

			switch(planType){
			case Medical:
				delegator = new Delegator<MedicalPlan>(carrierType, planType, selectedPlans, monBox.isSelected(), cvBox.isSelected(),
						incBox.isSelected(), pdfBox.isSelected(), log, progressBar);
				break;
			case Dental:
				delegator = new Delegator<DentalPlan>(carrierType, planType, selectedPlans, monBox.isSelected(), cvBox.isSelected(),
						incBox.isSelected(), pdfBox.isSelected(), log, progressBar);
				break;
			case Vision:
				delegator = new Delegator<VisionPlan>(carrierType, planType, selectedPlans, monBox.isSelected(), cvBox.isSelected(),
						incBox.isSelected(), pdfBox.isSelected(), log, progressBar);
				break;
			
			}
			delegator.addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(final PropertyChangeEvent event) {
					switch (event.getPropertyName()) {
					case "progress":
						progressBar.setIndeterminate(false);
						progressBar.setValue((Integer) event.getNewValue());
						// System.out.println(event.getNewValue());
						break;
					case "state":
						switch ((StateValue) event.getNewValue()) {
						case DONE:
							try {
								reports = (delegator).get();
								createExcel();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						case STARTED:
						case PENDING:
							break;
						}
						break;
					}
				}
			});

			System.out.println("executing");
			(delegator).execute();
		} else if (e.getSource() == stateBox) {
			checkState();
			carrierBox.removeAllItems();
			Set<String> c = sourceCarriers.get(selectedState);
			System.out.println(selectedState.toString());
			for (String carrier : c) {
				carrierBox.addItem(carrier);
			}
		} else if (e.getSource() == clearButton) {
			selectedPlans.clear();
			reports.clear();
			log.append("\nCleared files.\n");
		} else if (e.getSource() == typeBox) {
			checkPlan();
			switch (planType) {
			case Medical:
				this.sourceCarriers = medicalCarriers;
				break;
			case Dental:
				this.sourceCarriers = dentalCarriers;
				break;
			}
			carrierBox.removeAllItems();
			Set<String> c = sourceCarriers.get(selectedState.toString());
			if (!c.isEmpty()) {
				for (String carrier : c) {
					carrierBox.addItem(carrier);
				}
			}
		}
	}

	public void createExcel() throws FileNotFoundException, IOException {
		ExcelWriter<? extends Plan> writer = null;
		if (reports.size() == 0) {
			log.append("No input files.");
			return;
		}
		int totalErrors = 0;
		int totalWarnings = 0;
		for(Report<? extends Plan> r : reports){
			totalErrors += r.getTotalErrorSize();
			totalWarnings += r.getTotalWarningSize();
		}
		if(totalErrors + totalWarnings == 0){
			log.append("No output file produced.\n");
			return;
		}
		switch(planType){
		case Medical:
			writer = new MedicalExcelWriter(log, reports);
			break;
		case Dental:
			writer = new DentalExcelWriter(log, reports);
			break;
		case Vision:
			writer = new VisionExcelWriter(log, reports);
			break;
		}
		writer.populateLog();
		if(errorSummaryBox.isSelected()){
			writer.populateErrorSummary();
		}
		if(statisticsBox.isSelected()){
			writer.populateStatisticsSummary();
		}
		
		HashMap<String, XSSFWorkbook> reportMap = writer.getWorkbooks();
		for(Map.Entry<String, XSSFWorkbook> entry : reportMap.entrySet()){
			String outputName = String.format("%s_report.xlsx", entry.getKey());
			log.append(String.format("Output file: %s", outputName));
			XSSFWorkbook workbook = entry.getValue();
			// Create output file
			try (FileOutputStream outputStream = new FileOutputStream(outputName)) {
				workbook.write(outputStream);
			}
			workbook.close();
		}
	}
	
	public void checkState() {
		if (stateBox.getSelectedItem().equals("PA")) {
			this.selectedState = State.PA;
		}
		if (stateBox.getSelectedItem().equals("NJ")) {
			this.selectedState = State.NJ;
		}
		if (stateBox.getSelectedItem().equals("OH")) {
			this.selectedState = State.OH;
		}
		if (stateBox.getSelectedItem().equals("CA")) {
			this.selectedState = State.CA;
		}
	}

	public void checkCarrier() {
		if (carrierBox.getSelectedItem().equals("UPMC")) {
			this.carrierType = Carrier.UPMC;
		} else if (carrierBox.getSelectedItem().equals("Aetna")) {
			carrierType = Carrier.Aetna;
		} else if (carrierBox.getSelectedItem().equals("WPA")) {
			this.carrierType = Carrier.WPA;
		} else if (carrierBox.getSelectedItem().equals("NEPA")) {
			this.carrierType = Carrier.NEPA;
		} else if (carrierBox.getSelectedItem().equals("CPA")) {
			this.carrierType = Carrier.CPA;
		} else if (carrierBox.getSelectedItem().equals("UHC")) {
			this.carrierType = Carrier.UHC;
		} else if (carrierBox.getSelectedItem().equals("IBC")) {
			this.carrierType = Carrier.IBC;
		} else if (carrierBox.getSelectedItem().equals("CBC")) {
			this.carrierType = Carrier.CBC;
		} else if (carrierBox.getSelectedItem().equals("AmeriHealth")) {
			this.carrierType = Carrier.AmeriHealth;
		} else if (carrierBox.getSelectedItem().equals("Oxford")) {
			this.carrierType = Carrier.Oxford;
		} else if (carrierBox.getSelectedItem().equals("Cigna")) {
			this.carrierType = Carrier.Cigna;
		} else if (carrierBox.getSelectedItem().equals("Horizon")) {
			this.carrierType = Carrier.Horizon;
		} else if (carrierBox.getSelectedItem().equals("Geisinger")) {
			this.carrierType = Carrier.Geisinger;
		} else if (carrierBox.getSelectedItem().equals("Delta")) {
			this.carrierType = Carrier.Delta;
		} else if (carrierBox.getSelectedItem().equals("Anthem")) {
			this.carrierType = Carrier.Anthem;
		}
	}

	public void checkPlan() {
		if (typeBox.getSelectedItem().equals("Medical")) {
			this.planType = PlanType.Medical;
		} else if (typeBox.getSelectedItem().equals("Dental")) {
			this.planType = PlanType.Dental;
		} else if (typeBox.getSelectedItem().equals("Vision")) {
			this.planType = PlanType.Vision;
		}
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Main.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("BeneFix Verifier");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window.
		frame.add(new Main());

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				createAndShowGUI();
			}
		});
	}
}

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import components.PDFManager;
import names.ProductName;
import verifier.PDFVerifier;
import components.Main.Carrier;

public class PDFCheckerTests {

	@Test
	public void aetnaTest() throws IOException {
		File tempFile = new File("Temp File");
		URL aetnaURL = new URL("https://s3.amazonaws.com/benefix-production/carrier_plans/plan_pdfs/000/012/084/original/PA_Bronze_Commonwealth_HNOnly_6550_HSA_E.pdf?1501854078");
		FileUtils.copyURLToFile(aetnaURL, tempFile, 1000000, 1000000);
		
		PDFManager pdfManager = new PDFManager(tempFile);
		String text = pdfManager.ToText().toLowerCase();
		String[] tokens = text.split("[\\s\\r\\n]+");
		PDFVerifier aetnaParser = new PDFVerifier(tokens);
		ProductName aetnaName = new ProductName("PA Bronze Commonwealth HNOnly 6550 HSA E");
		aetnaName.setCarrier(Carrier.Aetna);
		aetnaParser.verifyAttributes(aetnaName);
		
		assertTrue(aetnaParser.matchesMetal == 0);
		assertTrue(aetnaParser.matchesPlan == 0);
		assertTrue(aetnaParser.matchesPlanAttribute == 0);
		assertTrue(aetnaParser.matchesHSAAttribute == 0);
		assertTrue(aetnaParser.matchesHRAAttribute == 0);
		assertTrue(aetnaParser.matchesPlusAttribute == 0);
	}
	
	@Test
	public void cbcTest() throws IOException {
		File tempFile = new File("Temp File");
		URL cbcURL = new URL("https://s3.amazonaws.com/benefix-production/carrier_plans/plan_pdfs/000/009/869/original/Gold_PPO_1000-0-25_RX_250-CBC-1011.pdf?1499371591");
		FileUtils.copyURLToFile(cbcURL, tempFile, 1000000, 1000000);
		
		PDFManager pdfManager = new PDFManager(tempFile);
		String text = pdfManager.ToText().toLowerCase();
		String[] tokens = text.split("[\\s\\r\\n]+");
		PDFVerifier cbcParser = new PDFVerifier(tokens);
		ProductName cbcName = new ProductName("Gold PPO 1000/0/25 Rx 250");
		cbcName.setCarrier(Carrier.Aetna);
		cbcParser.verifyAttributes(cbcName);
		
		assertTrue(cbcParser.matchesMetal == 0);
		assertTrue(cbcParser.matchesPlan == 0);
		assertTrue(cbcParser.matchesPlanAttribute == 0);
		assertTrue(cbcParser.matchesHSAAttribute == 0);
		assertTrue(cbcParser.matchesHRAAttribute == 0);
		assertTrue(cbcParser.matchesPlusAttribute == 0);
	}
	
	@Test
	public void geisingerTest() throws IOException {
		File tempFile = new File("Temp File");
		URL geisingerURL = new URL("https://s3.amazonaws.com/benefix-production/carrier_plans/plan_pdfs/000/011/948/original/Marketplace_PPO_15_30_250_Platinum.pdf?1501701530");
		FileUtils.copyURLToFile(geisingerURL, tempFile, 1000000, 1000000);
		
		PDFManager pdfManager = new PDFManager(tempFile);
		String text = pdfManager.ToText().toLowerCase();
		String[] tokens = text.split("[\\s\\r\\n]+");
		PDFVerifier geisingerParser = new PDFVerifier(tokens);
		ProductName geisingerName = new ProductName("Marketplace PPO 15/30/250 Platinum");
		geisingerName.setCarrier(Carrier.Geisinger);
		geisingerParser.verifyAttributes(geisingerName);
		
		assertTrue(geisingerParser.matchesMetal==-1);  //Will produce warning because metal does not appear in PDF
		assertTrue(geisingerParser.matchesPlan==0);
		assertTrue(geisingerParser.matchesPlanAttribute==0);
		assertTrue(geisingerParser.matchesHSAAttribute==0);
		assertTrue(geisingerParser.matchesHRAAttribute==0);
		assertTrue(geisingerParser.matchesPlusAttribute==0);
	}
	
	@Test
	public void cpaTest() throws IOException {
		File tempFile = new File("Temp File");
		URL cpaURL = new URL("https://s3.amazonaws.com/benefix-production/carrier_plans/plan_pdfs/000/011/776/original/Premier_Balance_PPO_2500_A.pdf?1501273265");
		FileUtils.copyURLToFile(cpaURL, tempFile, 1000000, 1000000);
		
		PDFManager pdfManager = new PDFManager(tempFile);
		String text = pdfManager.ToText().toLowerCase();
		String[] tokens = text.split("[\\s\\r\\n]+");
		PDFVerifier cpaParser = new PDFVerifier(tokens);
		ProductName cpaName = new ProductName("Premier Balance PPO $2500 Silver A");
		cpaName.setCarrier(Carrier.CPA);
		cpaParser.verifyAttributes(cpaName);
		
		assertTrue(cpaParser.matchesMetal==-1);  //Will produce warning because metal does not appear in PDF
		assertTrue(cpaParser.matchesPlan==0);
		assertTrue(cpaParser.matchesPlanAttribute==0);
		assertTrue(cpaParser.matchesHSAAttribute==0);
		assertTrue(cpaParser.matchesHRAAttribute==0);
		assertTrue(cpaParser.matchesPlusAttribute==0);
	}
	
	@Test
	public void upmcTest() throws IOException {
		File tempFile = new File("Temp File");
		URL upmcURL = new URL("https://s3.amazonaws.com/benefix-production/carrier_plans/plan_pdfs/000/012/253/original/Silver_HMO_3000_20_50.pdf?1502206636");
		FileUtils.copyURLToFile(upmcURL, tempFile, 1000000, 1000000);
		
		PDFManager pdfManager = new PDFManager(tempFile);
		String text = pdfManager.ToText().toLowerCase();
		String[] tokens = text.split("[\\s\\r\\n]+");
		PDFVerifier upmcParser = new PDFVerifier(tokens);
		ProductName upmcName = new ProductName("SBA Silver HMO $3,000 $20/$50 Standard Network");
		upmcName.setCarrier(Carrier.UPMC);
		upmcParser.verifyAttributes(upmcName);
		
//		for(String s : tokens){
//			System.out.println(s);
//		}
				
		assertTrue(upmcParser.matchesMetal==0);  //Will produce warning because metal does not appear in PDF
		assertTrue(upmcParser.matchesPlan==0);
		assertTrue(upmcParser.matchesPlanAttribute==0); //Could not find plan attribute in PDF
		assertTrue(upmcParser.matchesHSAAttribute==0);
		assertTrue(upmcParser.matchesHRAAttribute==0);
		assertTrue(upmcParser.matchesPlusAttribute==0);
	}

}

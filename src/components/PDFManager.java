package components;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

// TODO: Auto-generated Javadoc
/**
 * The Class PDFManager.
 */
public class PDFManager {

	/** The parser. */
	private PDFParser parser;
	
	/** The pdf stripper. */
	private PDFTextStripper pdfStripper;
	
	/** The pd doc. */
	private PDDocument pdDoc;
	
	/** The cos doc. */
	private COSDocument cosDoc;

	/** The Text. */
	private String Text;
	
	/** The file path. */
	private String filePath;
	
	/** The file. */
	private File file;

	/** The num pages. */
	private int numPages;

	/**
	 * Instantiates a new PDF manager.
	 */
	public PDFManager() {

	}

	/**
	 * Instantiates a new PDF manager.
	 *
	 * @param srcFile the src file
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public PDFManager(File srcFile) throws FileNotFoundException, IOException {
		filePath = srcFile.getAbsolutePath();
		file = new File(filePath);
		parser = new PDFParser(new RandomAccessFile(file, "r"));
		parser.parse();

		cosDoc = parser.getDocument();
		pdfStripper = new PDFTextStripper();
		pdDoc = new PDDocument(cosDoc);
		numPages = pdDoc.getNumberOfPages();
	}

	/**
	 * To text.
	 *
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String ToText() throws IOException {
		this.pdfStripper = null;
		this.pdDoc = null;
		this.cosDoc = null;

		file = new File(filePath);
		parser = new PDFParser(new RandomAccessFile(file, "r"));

		parser.parse();
		cosDoc = parser.getDocument();
		pdfStripper = new PDFTextStripper();
		pdDoc = new PDDocument(cosDoc);
		numPages = pdDoc.getNumberOfPages();
		pdfStripper.setStartPage(1);
		pdfStripper.setEndPage(numPages);

		Text = pdfStripper.getText(pdDoc);
		pdDoc.close();
		return Text;
	}

	/**
	 * To text.
	 *
	 * @param current_document the current document
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String ToText(PDDocument current_document) throws IOException {
		this.pdfStripper = null;
		this.pdDoc = null;
		this.cosDoc = null;

		pdfStripper = new PDFTextStripper();
		pdDoc = current_document;
		Text = pdfStripper.getText(pdDoc);
		pdDoc.close();
		return Text;
	}

	/**
	 * To text.
	 *
	 * @param startPage the start page
	 * @param endPage the end page
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String ToText(int startPage, int endPage) throws IOException {
		this.pdfStripper = null;
		this.pdDoc = null;
		this.cosDoc = null;

		file = new File(filePath);
		parser = new PDFParser(new RandomAccessFile(file, "r"));

		parser.parse();
		cosDoc = parser.getDocument();
		pdfStripper = new PDFTextStripper();
		pdDoc = new PDDocument(cosDoc);
		pdfStripper.setStartPage(startPage);
		pdfStripper.setEndPage(endPage);

		Text = pdfStripper.getText(pdDoc);
		pdDoc.close();
		return Text;
	}

	/**
	 * Gets the num pages.
	 *
	 * @return the num pages
	 */
	public int getNumPages() {
		return numPages;
	}

	/**
	 * Sets the file path.
	 *
	 * @param filePath the new file path
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
package com.sinet.gage.provision.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.sinet.gage.provision.data.model.Administrator;
import com.sinet.gage.provision.data.model.District;
import com.sinet.gage.provision.data.model.School;

/**
 * Component to generate Welcome Letter
 * 
 * @author Team Gage
 *
 */
@Component
public class WelcomeLetterGenerator {

	
	/**
	 * Generate the Welcome letter
	 * 
	 * @param district
	 *            District model for which to generate welcome letter
	 * @param currentUser
	 *            Logged in user name
	 * @return InputStream of generated welcome letter binary stream
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public InputStream generateWelcomeLetter( District district, String currentUser, List<String> courses) throws InvalidFormatException, IOException {

		ByteArrayOutputStream output = new ByteArrayOutputStream();

		XWPFDocument doc = new XWPFDocument(OPCPackage.open(getTemplateFile()));
		if (district != null && StringUtils.isNotBlank(currentUser)) {

			XmlCursor cursor = replaceText(doc, currentUser, district.getAdminUser());

			if ( null != district.getAdministrator() && StringUtils.isNotBlank( district.getUserSpace() ) ) {
				replaceDistrictTable(doc, district.getAdministrator(), district.getUserSpace());
			}

			for (School school : district.getSchoolList()) {
				cursor = addSchoolTable(doc, cursor, school);
			}
		}
		if ( courses != null ) {
			addCourseTable(doc,courses);
		}
		
		doc.write(output);

		return new ByteArrayInputStream(output.toByteArray());
	}

	/**
	 * Add table of Course to Document
	 * 
	 * @param doc
	 *           XWPFDocument reference
	 * @param courseList
	 *            Course list
	 * 
	 */
	private void addCourseTable(XWPFDocument doc, List<String> courseList) {
		// TODO Auto-generated method stub
		// XWPFDocument document = new XWPFDocument();

		XWPFParagraph paragraph = doc.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.addBreak(BreakType.PAGE);
		run.setText("Here is a list of the courses you have access to:");
		run.addCarriageReturn();
		XWPFTable tableOne;
		if (courseList.size() == 1) {
			tableOne = doc.createTable();
			XWPFTableRow row= tableOne.getRow(0);
			row.getCell(0).setText(courseList.get(0));
			return;
		}
		if (courseList.size() == 2) {
			tableOne = doc.createTable(1,2);
			XWPFTableRow row= tableOne.getRow(0);
			row.getCell(0).setText(courseList.get(0));
			row.getCell(1).setText(courseList.get(1));
			return;
		}

		tableOne = doc.createTable(courseList.size() >= 3 ? (courseList.size() / 3) + 1 : 1, 3);

		if (courseList.size() % 3 == 0) {
			tableOne.removeRow(courseList.size() / 3);
		}
		int n = 0;
		for (int i = 0; i <= courseList.size() / 3; i++) {
			XWPFTableRow tableOneRowOne = tableOne.getRow(i);

			for (int j = 0; j < 3; j++) {
				if (courseList.size() > n) {
					tableOneRowOne.getCell(j).setText(courseList.get(n));
					n++;
				}
			}
		}
	}

	private File getTemplateFile() throws IOException {
		Resource templateResource = new ClassPathResource("welcome_letter.docx");
		return templateResource.getFile();
	}

	/**
	 * Adds a new school table in the welcome letter template
	 * 
	 * @param doc
	 *            XWPFDocument reference
	 * @param cursor
	 *            XmlCursor location to add the school table
	 * @param school
	 *            School pojo contains domain information
	 * @return XmlCursor of current location to add next school table
	 */
	private XmlCursor addSchoolTable(XWPFDocument doc, XmlCursor cursor, School school) {
		if (cursor != null) {
			// adding paragraph
			XWPFParagraph p = doc.insertNewParagraph(cursor);
			cursor = p.getCTP().newCursor();

			// create a new table at current location
			XWPFTable table = doc.insertNewTbl(cursor);

			// align table to left 100% width
			CTTblWidth width = table.getCTTbl().addNewTblPr().addNewTblW();
			width.setType(STTblWidth.PCT);
			width.setW(BigInteger.valueOf(5000));
			table.getCTTbl().getTblPr().addNewJc().setVal(STJc.LEFT);

			addHeaderRow(table);

			if ( null != school.getAdministrator() && StringUtils.isNotBlank( school.getUserSpace() ) ) {
				addTableRow(table, school.getAdministrator(), school.getUserSpace());
			}

			cursor = table.getCTTbl().newCursor();
		}
		return cursor;
	}

	/**
	 * Adds Header row to school table
	 * 
	 * @param table
	 *            XWPFTable school table
	 */
	private void addHeaderRow(XWPFTable table) {

		XWPFTableRow tableRow = table.getRow(0);
		CTTrPr trPr = tableRow.getCtRow().addNewTrPr();
		CTHeight ht = trPr.addNewTrHeight();
		ht.setVal(BigInteger.valueOf(500));

		addHeaderCell(tableRow, "School URL", 0);
		addHeaderCell(tableRow, "School Administrator", 1);
		addHeaderCell(tableRow, "School Password", 2);
	}

	/**
	 * Adds cell to Header row of a table
	 * 
	 * @param tableRow
	 *            XWPFTableRow table row
	 * @param headerCelltext
	 *            header cell text
	 * @param cellNo
	 *            cell number to add header cell
	 */
	private void addHeaderCell(XWPFTableRow tableRow, String headerCelltext, int cellNo) {
		XWPFTableCell cell = cellNo == 0 ? tableRow.getCell(cellNo) : tableRow.addNewTableCell();

		// setting table width
		CTTblWidth cellWidth = cell.getCTTc().addNewTcPr().addNewTcW();
		cellWidth.setType(STTblWidth.PCT);
		cellWidth.setW(BigInteger.valueOf(1678));

		// setting vertical alignment to center
		CTTcPr tcpr = cell.getCTTc().addNewTcPr();
		CTVerticalJc va = tcpr.addNewVAlign();
		va.setVal(STVerticalJc.CENTER);

		// setting horizontal alignment to center
		XWPFParagraph p1 = cell.getParagraphs().get(0);
		p1.setAlignment(ParagraphAlignment.CENTER);

		// setting font and size
		XWPFRun r1 = p1.createRun();
		r1.setBold(true);
		r1.setFontSize(10);
		r1.setFontFamily("Calibri");
		r1.setText(headerCelltext);
	}

	/**
	 * Adds a new row to a table in document
	 * 
	 * @param table
	 *            XWPFTable table to add row
	 * @param administrator
	 *            Administrator pojo containing user details
	 * @param userSpace
	 *            Domain login prefix
	 */
	private void addTableRow(XWPFTable table, Administrator administrator, String userSpace) {
		XWPFTableRow tableRow = table.createRow();

		CTTrPr trPr = tableRow.getCtRow().addNewTrPr();
		CTHeight ht = trPr.addNewTrHeight();
		ht.setVal(BigInteger.valueOf(500));

		addRowCell(tableRow, "http://" + userSpace + ".edivatelearn.com", 0);
		addRowCell(tableRow, administrator.getUserName(), 1);
		addRowCell(tableRow, administrator.getPassword(), 2);
	}

	/**
	 * Adds a new cell to a table row
	 * 
	 * @param tableRow
	 *            XWPFTableRow row of the table
	 * @param celltext
	 *            Text for the cell
	 * @param cellNo
	 *            Cell number to add the text
	 */
	private void addRowCell(XWPFTableRow tableRow, String celltext, int cellNo) {
		XWPFTableCell cell = tableRow.getCell(cellNo);

		// setting table width
		CTTblWidth cellWidth = cell.getCTTc().addNewTcPr().addNewTcW();
		cellWidth.setType(STTblWidth.PCT);
		cellWidth.setW(BigInteger.valueOf(1678));

		// setting vertical alignment to center
		CTTcPr tcpr = cell.getCTTc().addNewTcPr();
		CTVerticalJc va = tcpr.addNewVAlign();
		va.setVal(STVerticalJc.CENTER);

		// setting horizontal alignment to left
		XWPFParagraph p1 = cell.getParagraphs().get(0);
		p1.setAlignment(ParagraphAlignment.LEFT);

		// setting font and size
		XWPFRun r1 = p1.createRun();
		r1.setBold(false);
		r1.setFontSize(10);
		r1.setFontFamily("Calibri");
		r1.setText(celltext);
	}

	/**
	 * Replace default districtTable content in welcome letter template
	 * 
	 * @param doc
	 *            XWPFDocument word tempalte document
	 * @param administrator
	 *            Administrator pojo containing user details
	 * @param userSpace
	 *            Domain login prefix
	 */
	private void replaceDistrictTable(XWPFDocument doc, Administrator administrator, String userSpace) {
		for (XWPFTable tbl : doc.getTables()) {
			for (XWPFTableRow row : tbl.getRows()) {
				for (XWPFTableCell cell : row.getTableCells()) {
					for (XWPFParagraph p : cell.getParagraphs()) {
						for (XWPFRun r : p.getRuns()) {
							String text = r.getText(0);
							if (text.contains("[Insert UN here]") && StringUtils.isNotBlank(administrator.getUserName() ) ) {
								text = text.replace("[Insert UN here]", administrator.getUserName());
								r.setText(text, 0);
							} else if (text.contains("[Insert PW here]") && StringUtils.isNotBlank(administrator.getPassword() ) ) {
								text = text.replace("[Insert PW here]", administrator.getPassword());
								r.setText(text, 0);
							} else if (text.contains("[Insert URL here]")) {
								text = text.replace("[Insert URL here]", "http://" + userSpace + ".edivatelearn.com");
								r.setText(text, 0);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Replaces all text placeholder in the welcome letter
	 * 
	 * @param doc
	 *            XWPFDocument template word document
	 * @param currentUser
	 *            Current logged in user name
	 * @param districtUser
	 *            District admin user name
	 * @return XmlCursor cursor location to add tables to document
	 */
	private XmlCursor replaceText(XWPFDocument doc, String currentUser, String districtUser) {
		XmlCursor cursor = null;
		for (XWPFParagraph p : doc.getParagraphs()) {
			List<XWPFRun> runs = p.getRuns();
			if (runs != null) {
				for (XWPFRun r : runs) {
					String text = r.getText(0);
					if (text != null && text.contains("[Recipient]")) {
						text = text.replace("[Recipient]", districtUser);
						r.setText(text, 0);
					} else if (text != null && text.contains("[Your Name]")) {
						text = text.replace("[Your Name]", currentUser);
						r.setText(text, 0);
					} else if (text != null && text.startsWith("We strongly encourage")) {
						cursor = p.getCTP().newCursor();
					}
				}
			}
		}
		return cursor;
	}
}
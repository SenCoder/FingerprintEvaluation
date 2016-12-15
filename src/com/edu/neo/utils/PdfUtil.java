package com.edu.neo.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import com.edu.neo.data.TableRowData;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import static com.edu.neo.consts.MyConst.SD_PATH;

public class PdfUtil {
	
//	public static final String PDF_NAME = SD_PATH + "report.pdf";
	
	public static void makePdf(ArrayList<TableRowData> tableList, String filename) {
		
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(SD_PATH + filename));
			document.open();
			document.addTitle("Cognitive Fingerpritning Based on PIN Password");
			document.addAuthor("Sheng Yuan");

			PdfPTable table = new PdfPTable(5);
			table.setPaddingTop(60f);
			table.setWidthPercentage(80);
			for (int i = 0; i < tableList.size(); i++) {
				table.addCell(i + 1 + "");
				table.addCell(tableList.get(i).getUsername());
				table.addCell(tableList.get(i).getEers()[0] + "");
				table.addCell(tableList.get(i).getEers()[1] + "");
				table.addCell(tableList.get(i).getEers()[2] + "");
			}
			document.add(table);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (document.isOpen()) {
				document.close();
			}
		}
	}
	
}

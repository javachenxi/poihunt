package com.hunt.poi.engine;

import java.util.Arrays;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.hunt.poi.expr.IExprActuator;
import com.hunt.poi.provider.IReadParam;

public class XDocTemplateParser implements ITemplateParser {	
	
	public XDocTemplateParser() {
	}
	
	public POIXMLDocument execute(TemplateContext context, POIXMLDocument poiDoc,
			IReadParam params) {
		
		IExprActuator exprActuator = context.getCurrEngine().getExprActuator();
		
		XWPFDocument document = (XWPFDocument) poiDoc;
		List<XWPFParagraph>  parags = document.getParagraphs();
		
		//parse xdoc paragraphs
		for(XWPFParagraph paragraph: parags) {
			parseXWPFParagraph(paragraph, params, exprActuator);
		}
		
		List<XWPFTable> tables = document.getTables();
		
		//parse xdoc tables
		for(XWPFTable table: tables) {
			parseXWPFTable(table, params, exprActuator);
		}
		
		return poiDoc;
	}
	
	private void parseXWPFTable(XWPFTable xwpfTable, IReadParam params, IExprActuator exprActuator) {

		List<XWPFTableRow> rows = xwpfTable.getRows();
		
		for (int r = 0; r < rows.size(); r++) {
			
			XWPFTableRow row = rows.get(r);
			
			List<XWPFTableCell> cells = row.getTableCells();

			for (int c = 0; c < cells.size(); c++) {
				XWPFTableCell cell = cells.get(c);
				
				List<XWPFParagraph> paragraphs = cell.getParagraphs();

				for (XWPFParagraph paragraph : paragraphs) {
					parseXWPFParagraph(paragraph, params, exprActuator);
				}
				
			}
			
		}

	}
	
	private void parseXWPFParagraph(XWPFParagraph xwpfParagraph, IReadParam params,IExprActuator exprActuator) {
		boolean starttag = false;
		int[] indexFr = { -1, -1 }, indexChar = { -1, -1 };

		List<XWPFRun> xwpfRuns = xwpfParagraph.getRuns();
		// eq the word group
		for (int r = 0; r < xwpfRuns.size(); r++) {

			String tmpText = xwpfRuns.get(r).getText(0);

			if(tmpText == null || tmpText.length() == 0){
				continue;
			}

			char[] testchar = tmpText.toCharArray();

			for (int i = 0; i < testchar.length; i++) {
				char tmpchar = testchar[i];

				switch (tmpchar) {
				case S_TOKEN_PARAM:
					if ((testchar.length - 1 > i && testchar[i + 1] == SS_TOKEN_PARAM)) {
						starttag = true;
						indexChar[0] = i++;
						indexFr[0] = r;
					} else if (testchar.length - 1 == i && r < xwpfRuns.size() - 1) {
						tmpText = xwpfRuns.get(r + 1).getText(0);
						if (tmpText.startsWith(SS_TOKEN_PARAM+"")) {
							starttag = true;
							indexChar[0] = 0;
							indexFr[0] = r + 1;
						}
					}
					break;

				case E_TOKEN_PARAM:
					if (starttag) {
						indexChar[1] = i;
						indexFr[1] = r;
						replaceParam(xwpfRuns, indexFr, indexChar, params , exprActuator);
						// reset
						Arrays.fill(indexFr, -1);
						Arrays.fill(indexChar, -1);
						starttag = false;
					}
					break;
				default:

				}
			}

		}
		
	}

	private void replaceParam(List<XWPFRun> xwpfRuns, int[] indexFr, int[] indexChar, 
			    IReadParam params,IExprActuator exprActuator) {

		StringBuilder strBuild = new StringBuilder();
		StringBuilder onerowstr = new StringBuilder();
		
		int fillIndex = 0;

		for (int tr = indexFr[0]; tr <= indexFr[1]; tr++) {

			XWPFRun xwpfRun = xwpfRuns.get(tr);

			String tmpText = xwpfRun.getText(0);

			if (indexFr[0] == indexFr[1]) {
				strBuild.append(tmpText.substring(indexChar[0], indexChar[1] + 1));
				onerowstr.delete(0, onerowstr.length());
				
				if(indexChar[0] > 0) {
					onerowstr.append(tmpText.substring(0, indexChar[0] + 1) ); 
				}
				
				onerowstr.append(exprActuator.runExpr(strBuild.substring(2, strBuild.length() - 1), params));
				
				if(tmpText.length() > indexChar[1]+1) {
					onerowstr.append(tmpText.substring(indexChar[1]+1));
				}
				xwpfRun.setText(onerowstr.toString(),	0);
				
				break;
			}

			if (tr == indexFr[0]) {
				strBuild.append(tmpText.substring(indexChar[0]));

			} else if (tr == indexFr[1]) {
				strBuild.append(tmpText.substring(0, indexChar[1] + 1));
				fillIndex = fillIndex == 0 ? tr : fillIndex;
			} else {
				fillIndex = fillIndex == 0 ? tr : fillIndex;
				strBuild.append(tmpText);
			}
		}
		
		String exprStr = strBuild.substring(2, strBuild.length() - 1);

		if (indexFr[0] < indexFr[1]) {

			for (int t = indexFr[1]; t >= indexFr[0]; t--) {
				XWPFRun xwpfRun = xwpfRuns.get(t);
				String tmpText = xwpfRun.getText(0);

				if (t == fillIndex) {

					if (t == indexFr[1]) {
						xwpfRun.setText(exprActuator.runExpr(exprStr, params)+ tmpText.substring(indexChar[1] + 1), 0);
					} else {
						xwpfRun.setText(exprActuator.runExpr(exprStr, params), 0);
					}
				} else if (t == indexFr[0]) {

					if (indexChar[0] == 0) {
						xwpfRun.setText("", 0);
					}
				} else if (t == indexFr[1]) {

					if (indexChar[1] == tmpText.length() - 1) {
						xwpfRun.setText("", 0);
					}
				} else {
					xwpfRun.setText("", 0);
				}
			}

		}

	}
	

}

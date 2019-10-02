package com.rabobank.rabocuststmtprocessor.filereaderimpl;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.rabobank.rabocuststmtprocessor.bean.StmtRecord;
import com.rabobank.rabocuststmtprocessor.filereader.StatementReader;

public class XMLReader  implements StatementReader{

	@Override
	public List<StmtRecord> getStmtRecords(String fileContent) {
		DocumentBuilder db;
		List<StmtRecord> stmtRecordList = new ArrayList<>();
		try {
			db = DocumentBuilderFactory.newInstance()
				    .newDocumentBuilder();
		
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(fileContent));

			Document doc = db.parse(is);
			NodeList nodes = ((Document) doc)
			    .getElementsByTagName("record");

			for (int i = 0; i < nodes.getLength(); i++) {         
				Element element = (Element) nodes.item(i);
			    String transactionRef = element.getAttribute("reference");
			    
			    String accountNumber = element.getElementsByTagName("accountNumber").item(0).getTextContent();
			    String description = element.getElementsByTagName("description").item(0).getTextContent();
			    String startBalance = element.getElementsByTagName("startBalance").item(0).getTextContent();
			    String mutation = element.getElementsByTagName("mutation").item(0).getTextContent();
			    String endBalance = element.getElementsByTagName("endBalance").item(0).getTextContent();
			    
			    StmtRecord stmtRecord = new StmtRecord();
				stmtRecord.setTransactionRef(Integer.parseInt(transactionRef));
				stmtRecord.setAccountNum(accountNumber);
				stmtRecord.setDesc(description);
				stmtRecord.setStartBal(new BigDecimal(startBalance));
				stmtRecord.setMutation(new BigDecimal(mutation));
				stmtRecord.setEndBal(new BigDecimal(endBalance));
				stmtRecordList.add(stmtRecord);
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmtRecordList;
	}

}

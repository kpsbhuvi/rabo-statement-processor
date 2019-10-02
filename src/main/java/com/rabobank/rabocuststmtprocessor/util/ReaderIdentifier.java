package com.rabobank.rabocuststmtprocessor.util;

import org.springframework.stereotype.Component;

import com.rabobank.rabocuststmtprocessor.filereader.StatementReader;
import com.rabobank.rabocuststmtprocessor.filereaderimpl.CsvReader;
import com.rabobank.rabocuststmtprocessor.filereaderimpl.XMLReader;

@Component
public class ReaderIdentifier {

	public StatementReader getReader(String fileName) {
		StatementReader reader = null;
		if (!fileName.isEmpty())
			if (fileName.toLowerCase().contains("xml")) {
				reader = new XMLReader();
			} else {
				reader = new CsvReader();
			}
		return reader;
	}

}

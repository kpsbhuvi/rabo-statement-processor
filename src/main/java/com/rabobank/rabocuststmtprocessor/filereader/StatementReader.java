package com.rabobank.rabocuststmtprocessor.filereader;

import java.util.List;

import com.rabobank.rabocuststmtprocessor.bean.StmtRecord;

public interface StatementReader {
	public List<StmtRecord> getStmtRecords(String fileContent);
}

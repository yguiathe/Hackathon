package com.svb.hackathon.repository;

import java.util.List;

import com.amazonaws.services.athena.model.ColumnInfo;
import com.amazonaws.services.athena.model.Row;

public interface AthenaRepository {

	String submitAthenaQuery(String athenaQuery);
	
	void waitForQueryToComplete(String queryExecutionId) throws InterruptedException ;
	
	void processResultRows(String queryExecutionId);
	
	void processRow(List<Row> rowList, List<ColumnInfo> columnInfoList);
}

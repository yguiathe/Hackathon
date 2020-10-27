package com.svb.hackathon.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.athena.AmazonAthena;
import com.amazonaws.services.athena.model.ColumnInfo;
import com.amazonaws.services.athena.model.Datum;
import com.amazonaws.services.athena.model.GetQueryExecutionRequest;
import com.amazonaws.services.athena.model.GetQueryExecutionResult;
import com.amazonaws.services.athena.model.GetQueryResultsRequest;
import com.amazonaws.services.athena.model.GetQueryResultsResult;
import com.amazonaws.services.athena.model.QueryExecutionContext;
import com.amazonaws.services.athena.model.QueryExecutionState;
import com.amazonaws.services.athena.model.ResultConfiguration;
import com.amazonaws.services.athena.model.Row;
import com.amazonaws.services.athena.model.StartQueryExecutionRequest;
import com.amazonaws.services.athena.model.StartQueryExecutionResult;
import com.svb.hackathon.config.ApplicationProperties;

@Service("athenaRepository")
public class AthenaRepositoryImpl implements AthenaRepository {

	private static final Logger log = LoggerFactory.getLogger(AthenaRepositoryImpl.class);

	@Autowired
	private AmazonAthena athenaClient;

	@Autowired
	private ApplicationProperties appProperties;

	@Override
	public String submitAthenaQuery(String athenaQuery) {
		QueryExecutionContext queryExecutionContext = new QueryExecutionContext();
		queryExecutionContext.setDatabase(appProperties.getAws().getAthena().getDatabase());

		ResultConfiguration resultConfiguration = new ResultConfiguration();
		resultConfiguration.setOutputLocation(appProperties.getAws().getAthena().getOutputBucket());

		StartQueryExecutionRequest startQueryExecutionRequest = new StartQueryExecutionRequest();
		startQueryExecutionRequest.setQueryString(athenaQuery);
		startQueryExecutionRequest.setQueryExecutionContext(queryExecutionContext);
		startQueryExecutionRequest.setResultConfiguration(resultConfiguration);

		StartQueryExecutionResult startQueryExecutionResult = athenaClient
				.startQueryExecution(startQueryExecutionRequest);

		return startQueryExecutionResult.getQueryExecutionId();
	}

	@Override
	public void waitForQueryToComplete(String queryExecutionId) throws InterruptedException {
		GetQueryExecutionRequest queryExecutionRequest = new GetQueryExecutionRequest();
		queryExecutionRequest.setQueryExecutionId(queryExecutionId);

		GetQueryExecutionResult queryExecutionResult;

		boolean isQueryStillRunning = true;

		while (isQueryStillRunning) {
			queryExecutionResult = athenaClient.getQueryExecution(queryExecutionRequest);
			String queryState = queryExecutionResult.getQueryExecution().getStatus().getState().toString();

			if (queryState.equals(QueryExecutionState.FAILED.toString())) {
				throw new RuntimeException("Query Failed to run with Error Message: "
						+ queryExecutionResult.getQueryExecution().getStatus().getStateChangeReason());
			} else if (queryState.equals(QueryExecutionState.CANCELLED.toString())) {
				throw new RuntimeException("Query was cancelled.");
			} else if (queryState.equals(QueryExecutionState.SUCCEEDED.toString())) {
				isQueryStillRunning = false;
			} else {
				Thread.sleep(appProperties.getAws().getAthena().getSleepAmountMs());
			}

			log.info("Current Status is: " + queryState);
		}

	}

	@Override
	public void processResultRows(String queryExecutionId) {
		GetQueryResultsRequest queryResultsRequest = new GetQueryResultsRequest();
		queryResultsRequest.setQueryExecutionId(queryExecutionId);

        GetQueryResultsResult getQueryResultsResults = athenaClient.getQueryResults(queryResultsRequest);
        List<ColumnInfo> columnInfoList = getQueryResultsResults.getResultSet().getResultSetMetadata().getColumnInfo();
        int resultSize = getQueryResultsResults.getResultSet().getRows().size();
        log.info("Result size: " + resultSize);
        List<Row> results = getQueryResultsResults.getResultSet().getRows();
        processRow(results, columnInfoList);
            
	}

	@Override
	public void processRow(List<Row> rowList, List<ColumnInfo> columnInfoList) {
		List<String> columns = new ArrayList<>();

        for (ColumnInfo columnInfo : columnInfoList) {
            columns.add(columnInfo.getName());
        }

        for (Row row: rowList) {
            int index = 0;

            for (Datum datum : row.getData()) {
                log.info(columns.get(index) + ": " + datum.getVarCharValue());
                index++;
            }

            log.info("===================================");
        }

	}

}

package com.svb.hackathon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svb.hackathon.repository.AthenaRepository;

@Service("historicalCaseService")
public class HistoricalCasesServiceImpl implements HistoricalCasesService {

	private static final Logger log = LoggerFactory.getLogger(HistoricalCasesServiceImpl.class);
	
	@Autowired
	private AthenaRepository athenaRepository;
	
	@Override
	public void getCasesAudit(String lob, String clientName) throws Exception {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM \"fcrm\".\"audit_hist\" limit 10;";
		String queryExecutionId = athenaRepository.submitAthenaQuery(query);

        log.info("Query submitted: " + System.currentTimeMillis());

        athenaRepository.waitForQueryToComplete(queryExecutionId);

        log.info("Query finished: " + System.currentTimeMillis());

        athenaRepository.processResultRows(queryExecutionId);

	}

}

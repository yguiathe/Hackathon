package com.svb.hackathon.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.svb.hackathon.service.HistoricalCasesService;

@RestController
@RequestMapping("/api/fetch/cases")
public class HistoricalCasesResource {
	
	private static final Logger log = LoggerFactory.getLogger(HistoricalCasesResource.class);
	
	@Autowired
	private HistoricalCasesService caseService;
	
	@GetMapping("/audit/{lob}")
	public ResponseEntity<?> getCasesAudit(@PathVariable String lob) throws Exception{
		caseService.getCasesAudit(lob, "");
		log.info("Athena query ran successfully");
	    return new ResponseEntity<>("Athena query ran successfully", HttpStatus.OK);
	}

}

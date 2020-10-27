package com.svb.hackathon.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.athena.AmazonAthena;
import com.amazonaws.services.athena.AmazonAthenaClientBuilder;

@Configuration
public class AWSConfiguration {
	
	private static final Logger log = LoggerFactory.getLogger(AWSConfiguration.class);

	/**
	 * AthenaClientBuilder to build Athena with the following properties: - Set the
	 * region of the client - Use the instance profile from the EC2 instance as the
	 * credentials provider - Configure the client to increase the execution
	 * timeout.
	 */

	@Autowired
	private ApplicationProperties appProperties;

	@Bean
	public BasicAWSCredentials basicAWSCredentials() {
		return new BasicAWSCredentials(appProperties.getAws().getCredentials().getAccessKey(),
				appProperties.getAws().getCredentials().getSecretKey());
	}

	@Bean(name = "athenaClient")
	public AmazonAthena amazonAthenaClient(AWSCredentials awsCredentials) {
		AmazonAthena athenaClient = AmazonAthenaClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
				.withRegion(appProperties.getAws().getRegion().getName()).build();
		log.info("Instantiating Athena Client Bean");
		return athenaClient;
	}

}

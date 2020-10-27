package com.svb.hackathon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Hackaton.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

	private AWS aws = new AWS();

	public AWS getAws() {
		return aws;
	}

	public void setAws(AWS aws) {
		this.aws = aws;
	}

	@Override
	public String toString() {
		return "ApplicationProperties [aws=" + aws + "]";
	}
	
}


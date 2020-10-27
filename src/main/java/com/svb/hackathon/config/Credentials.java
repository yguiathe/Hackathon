package com.svb.hackathon.config;

public class Credentials {

	private String accessKey;
	private String secretKey;
	
	public String getSecretKey() {
		return secretKey;
	}
	
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	public String getAccessKey() {
		return accessKey;
	}
	
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	@Override
	public String toString() {
		return "Credentials [accessKey=" + accessKey + ", secretKey=" + secretKey + "]";
	}
}

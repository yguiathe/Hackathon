package com.svb.hackathon.config;

public class Athena {

	private String outputBucket;
	private String database;
	private int clientTimeExecution;
	private int sleepAmountMs;
	
	public String getOutputBucket() {
		return outputBucket;
	}
	
	public void setOutputBucket(String outputBucket) {
		this.outputBucket = outputBucket;
	}
	
	public String getDatabase() {
		return database;
	}
	
	public void setDatabase(String database) {
		this.database = database;
	}
	
	public int getClientTimeExecution() {
		return clientTimeExecution;
	}
	
	public void setClientTimeExecution(int clientTimeExecution) {
		this.clientTimeExecution = clientTimeExecution;
	}
	
	public int getSleepAmountMs() {
		return sleepAmountMs;
	}
	
	public void setSleepAmountMs(int sleepAmountMs) {
		this.sleepAmountMs = sleepAmountMs;
	}

	@Override
	public String toString() {
		return "Athena [outputBucket=" + outputBucket + ", database=" + database + ", clientTimeExecution="
				+ clientTimeExecution + ", sleepAmountMs=" + sleepAmountMs + "]";
	}

}

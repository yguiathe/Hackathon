package com.svb.hackathon.config;

public class AWS {

	private Credentials credentials = new Credentials();
	private Region region = new Region();
	private Athena athena = new Athena();
	
	public Credentials getCredentials() {
		return credentials;
	}
	
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	
	public Region getRegion() {
		return region;
	}
	
	public void setRegion(Region region) {
		this.region = region;
	}
	
	public Athena getAthena() {
		return athena;
	}
	
	public void setAthena(Athena athena) {
		this.athena = athena;
	}

	@Override
	public String toString() {
		return "AWS [credentials=" + credentials + ", region=" + region + ", athena=" + athena + "]";
	}
}

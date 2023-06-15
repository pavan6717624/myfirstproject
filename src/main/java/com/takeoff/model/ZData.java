package com.takeoff.model;

public class ZData {

	@Override
	public String toString() {
		return "ZData [itoken=" + itoken + ", etoken=" + etoken + ", symbol=" + symbol + ", name="
				+ name + ", price=" + price + ", expiry=" + expiry + ", strike=" + strike + ", tsize=" + tsize
				+ ", lsize=" + lsize + ", itype=" + itype + ", segment=" + segment + ", exchange=" + exchange + "]";
	}

	public ZData(String line) {
		String data[] = line.split(",");
		itoken = Integer.parseInt(data[0]);
		etoken = Integer.parseInt(data[1]);
		symbol = (data[2]);
		name = (data[3]);
		price = Double.parseDouble(data[4]);
		expiry = (data[5]);
		strike = Double.parseDouble(data[6]);
		tsize = Double.parseDouble(data[7]);
		lsize = Integer.parseInt(data[8]);
		itype = (data[9]);
		segment = (data[10]);
		exchange = (data[11]);

	}

	Integer itoken;
	Integer etoken;
	String symbol;
	String name;
	Double price;
	String expiry;
	Double strike;
	Double tsize;
	Integer lsize;
	String itype;
	String segment;
	String exchange;

	public Integer getItoken() {
		return itoken;
	}

	public void setItoken(Integer itoken) {
		this.itoken = itoken;
	}

	public Integer getEtoken() {
		return etoken;
	}

	public void setEtoken(Integer etoken) {
		this.etoken = etoken;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public Double getStrike() {
		return strike;
	}

	public void setStrike(Double strike) {
		this.strike = strike;
	}

	public Double getTsize() {
		return tsize;
	}

	public void setTsize(Double tsize) {
		this.tsize = tsize;
	}

	public Integer getLsize() {
		return lsize;
	}

	public void setLsize(Integer lsize) {
		this.lsize = lsize;
	}

	public String getItype() {
		return itype;
	}

	public void setItype(String itype) {
		this.itype = itype;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

}

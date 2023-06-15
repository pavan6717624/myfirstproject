package com.takeoff.model;

import java.util.List;

public class OIData {

	Double price;
	Long instrument;
	String call;
	public List<Double> getOpen() {
		return open;
	}

	public void setOpen(List<Double> open) {
		this.open = open;
	}

	public List<Double> getClose() {
		return close;
	}

	public void setClose(List<Double> close) {
		this.close = close;
	}

	public List<Double> getLow() {
		return low;
	}

	public void setLow(List<Double> low) {
		this.low = low;
	}

	public List<Double> getHigh() {
		return high;
	}

	public void setHigh(List<Double> high) {
		this.high = high;
	}

	public List<Double> getVol() {
		return vol;
	}

	public void setVol(List<Double> vol) {
		this.vol = vol;
	}

	public List<Double> getOi() {
		return oi;
	}

	public void setOi(List<Double> oi) {
		this.oi = oi;
	}

	public List<String> getDate() {
		return date;
	}

	public void setDate(List<String> date) {
		this.date = date;
	}

	List<Double> open,close,low,high,vol,oi;
	List<String> date;
	
	
	

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public Long getInstrument() {
		return instrument;
	}

	public void setInstrument(Long instrument) {
		this.instrument = instrument;
	}


	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}

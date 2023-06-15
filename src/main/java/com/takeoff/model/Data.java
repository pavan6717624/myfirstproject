package com.takeoff.model;

public class Data {
	public Data(String date, Double open, Double high, Double low, Double close, Double volume, Double oi) {
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
		this.oi = oi;
	}

	String date;
	Double open, low, close, high, volume, oi;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getClose() {
		return close;
	}

	public void setClose(Double close) {
		this.close = close;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getOi() {
		return oi;
	}

	public void setOi(Double oi) {
		this.oi = oi;
	}

	public String toString() {
		return date + "," + open + "," + high + "," + low + "," + close + "," + volume + "," + oi;
	}
}

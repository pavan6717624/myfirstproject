package com.takeoff.model;

import java.util.List;

@lombok.Data
public class Data {
	
	String name;
	OIData data;
	
	public Data(List<MapData> mapData)
	{
		name=mapData.get(0).getSymbol();
		data=new OIData(mapData);
	}
}

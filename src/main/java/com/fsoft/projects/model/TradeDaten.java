package com.fsoft.projects.model;

import java.util.Map;

public class TradeDaten {
	
	private String id;
	Map<String, Object> daten;
	
	public TradeDaten() {
		
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getDaten() {
		return daten;
	}

	public void setDaten(Map<String, Object> daten) {
		this.daten = daten;
	}

}

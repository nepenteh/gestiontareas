package com.ejerciciosmesa.tareas.appdata;

import java.util.HashMap;

public class GeneralOption implements Comparable<GeneralOption> {

	private int order;
	private String key;
	private String name;
	private String link;
	private String mainScreenCode;
	
		
	private HashMap<String, String> screens; 
	private String emptyMessage;
	

	public GeneralOption(int order, String key, String name, String link, String mainScreenCode) {
		this.order = order;
		this.key = key;
		this.name = name;
		this.link = link;
		this.mainScreenCode = mainScreenCode;
		screens = new HashMap<>();
	}
	
	public String getKey() {return key;}
	public String getName() {return name;}
	public String getLink() {return link;}
	public String getMainScreenCode() {return mainScreenCode;}
	
	
	public void addScreen(String code, String name) {
		screens.put(code, name);
	}
	
	public String getScreen(String code) {
		return screens.get(code);
	}
	
	public String getMainScreenName() {
		return screens.get(mainScreenCode);
	}

	public String getEmptyMessage() {
		return emptyMessage;
	}
	
	public void setEmptyMessage(String emptyMessage) {
		this.emptyMessage = emptyMessage;
	}
	
	@Override
	public int compareTo(GeneralOption go) {
		return this.order-go.order;
	}
	
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/


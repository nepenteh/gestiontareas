package com.ejerciciosmesa.tareas.appdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import org.springframework.stereotype.Component;


@Component
public class AppDataImpl  implements AppData {

	private String name; 
	private String author; 
	private int year; 
	private String web; 
	private String webURL; 
	private TreeMap<String,GeneralOption> generalOptions; 
	private ArrayList<GeneralOption> sortedGeneralOptions;
			
	public AppDataImpl() {
		name = "Gestión de Tareas";
		author = "José Manuel Rosado";
		year = 2023;
		web = "ejerciciosmesa.com";
		webURL = "https://ejerciciosmesa.com";
		
		generalOptions = new TreeMap<>();
		sortedGeneralOptions = new ArrayList<>();
		int order=0;
		
		GeneralOption opTarea = new GeneralOption(order,"TAREA","Lista de Tareas","/tarea/list","LIST");

opTarea.addScreen("LIST","Tarea (List)");
opTarea.addScreen("CREATE","Tarea (Create)");
opTarea.addScreen("UPDATE","Tarea (Update)");
opTarea.addScreen("VIEW","Tarea (View)");
opTarea.addScreen("VIEWIMG","Tarea - View Image");

opTarea.setEmptyMessage("No data");

generalOptions.put("TAREA",opTarea);
sortedGeneralOptions.add(opTarea);

order++;


		
		Collections.sort(sortedGeneralOptions);
		
	}
	
	
	@Override
	public String getName() {
		return name;
	}
	
	
	@Override
	public String getAuthor() {
		return author;
	}

	
	@Override
	public int getYear() {
		return year;
	}

	
	@Override
	public String getWeb() {
		return web;
	}
	
	
	@Override
	public String getWebUrl() {
		return webURL;
	}

	
	@Override
	public String getAuthorship() {
		String authorship = "";
		if(!author.isBlank() || !web.isBlank()) {
			authorship += author+" "+year;
			if(!web.isBlank()) authorship += " - "+web;
		}		
		return authorship.trim();
	}
	
	
	@Override
	public TreeMap<String, GeneralOption> getGeneralOptions() {
		return generalOptions;
	}
	
	@Override
	public ArrayList<GeneralOption> getSortedGeneralOptions() {
		return sortedGeneralOptions;
	}
	
	@Override
	public boolean isMainScreen(String optionCode, String screenCode) {
		return generalOptions.get(optionCode).getMainScreenCode().equals(screenCode);
	}
	
	@Override
	public String getMainScreenName(String optionCode) {
		return generalOptions.get(optionCode).getMainScreenName();
	}
	
	@Override
	public String getMainScreenLink(String optionCode) {
		return generalOptions.get(optionCode).getLink();
	}
	
	@Override
	public String getScreenName(String optionCode, String screenCode) {
		return generalOptions.get(optionCode).getScreen(screenCode);
	}

	@Override
	public String getEmptyMessage(String optionCode) {
		return generalOptions.get(optionCode).getEmptyMessage();
	}
		
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/


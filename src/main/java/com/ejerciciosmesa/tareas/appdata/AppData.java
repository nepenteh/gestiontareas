package com.ejerciciosmesa.tareas.appdata;

import java.util.ArrayList;
import java.util.TreeMap;


public interface AppData {

	public String getName();
	public String getAuthor();
	public int getYear();
	public String getWeb();
	public String getWebUrl();
	public String getAuthorship();
	public TreeMap<String, GeneralOption> getGeneralOptions();
	public ArrayList<GeneralOption> getSortedGeneralOptions();
	public boolean isMainScreen(String optionCode, String screenCode);
	public String getMainScreenName(String optionCode);
	public String getMainScreenLink(String optionCode);
	public String getScreenName(String optionCode, String screenCode);
	public String getEmptyMessage(String optionCode);
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/


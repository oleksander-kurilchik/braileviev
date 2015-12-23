package com.sashaku.brailleview;

public interface IBFixHandler {
public 	void onFixButton(int id) throws Exception  ;
public 	void onUnFixButton(int id)throws Exception  ; 
public void setLanguage (String lang) ;
public String[] getAvailableLanguages();

}

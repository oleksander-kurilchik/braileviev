package com.sashaku.brailleview;

public interface IBPutHandler {
	public void onPutCode(byte code)throws Exception ;
	public void  SetLocale(String locale )throws Exception ;
	public void setLanguage (String lang) ;
	public String[] getAvailableLanguages();

}

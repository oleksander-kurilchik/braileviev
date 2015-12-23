package com.sashaku.brailleview;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

public class SimpleFixHandler implements IBFixHandler,OnInitListener {
	private TextToSpeech tts;

	public SimpleFixHandler(Context con) {
		// TODO Auto-generated constructor stub
		tts = new TextToSpeech(con, this);
		tts.setSpeechRate(0.6f);
	}

	@Override
	public void onFixButton(int id) {
		tts.speak("Button with ID   " +id + " Fixed.", TextToSpeech.QUEUE_FLUSH, null);

	}
	

	@Override
	public void onUnFixButton(int id) {
		// TODO Auto-generated method stub
		tts.speak("Button with ID   " +id + " unFixed." , TextToSpeech.QUEUE_FLUSH, null);


	}

	@Override
	public void onInit(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLanguage(String lang) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getAvailableLanguages() {
		// TODO Auto-generated method stub
		return null;
	}

}

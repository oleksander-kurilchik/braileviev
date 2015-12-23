package com.sashaku.brailleview;

import java.util.Hashtable;
import java.util.Map;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

public class SimplePutHandler implements IBPutHandler,OnInitListener {
	private TextToSpeech tts;
	private Map<Byte, String> map_value;
	{
		map_value = new Hashtable<Byte, String>();
		map_value.put((byte) 1,"A");
		map_value.put((byte)3,"B");
		map_value.put((byte)9,"C");
		map_value.put((byte)25,"D");
		map_value.put((byte)17,"E");
		map_value.put((byte)11,"F");
		map_value.put((byte)27,"G");
		map_value.put((byte)19,"H");
		map_value.put((byte)10,"I");
		map_value.put((byte)26,"J");
		map_value.put((byte)5,"K");
		map_value.put((byte)7,"L");
		map_value.put((byte)13,"M");
		map_value.put((byte)29,"N");
		map_value.put((byte)21,"0");
		map_value.put((byte)15,"P");
		map_value.put((byte)31,"Q");
		map_value.put((byte)23,"R");
		map_value.put((byte)14,"S");
		map_value.put((byte)30,"T");
		map_value.put((byte)37,"U");
		map_value.put((byte)39,"V");
		map_value.put((byte)58,"W");
		map_value.put((byte)45,"X");
		map_value.put((byte)61,"Y");
		map_value.put((byte)53,"Z");
		map_value.put((byte)32,"A");
		map_value.put((byte)60,"A");
		map_value.put((byte)50,"A");
		map_value.put((byte)2,",");
		map_value.put((byte)34,"?");
		map_value.put((byte)6,";");
		map_value.put((byte)22,"!");
		map_value.put((byte)38,"\"");
		map_value.put((byte)52,"\"");
		map_value.put((byte)54,"{");
		map_value.put((byte)34,"-");
		
	}

	public SimplePutHandler( Context con) {
		// TODO Auto-generated constructor stub
		tts = new TextToSpeech(con, this);
		tts.setPitch((float) 2);
		
	}
	

	@Override
	public void onPutCode(byte code) {
		// TODO Auto-generated method stub
		String alfa =map_value.get(code);
		if(alfa !=null)
		tts.speak("Entered alfa    ," +alfa , TextToSpeech.QUEUE_FLUSH, null);
		else tts.speak("Entered alfa with code    ," +code , TextToSpeech.QUEUE_FLUSH, null);
	}

	@Override
	public void SetLocale(String locale) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInit(int status) 
	{
		if(status == TextToSpeech.SUCCESS)
		{
			
			
			
		}
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

package com.sashaku.brailleview;

import java.io.File;

import android.R.id;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
Button button;
Button button1;
SimplePutHandler hhh;
SimplePutHandler hhh2;
SimplePutHandler sp_handler;
SimpleFixHandler sf_handler;
BrailleView b_view;
SimpleOutputHandler so_handler;
SimpleMoveHandler sm_handler;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sp_handler = new  SimplePutHandler(this);;
		sf_handler = new SimpleFixHandler(this);
		b_view =  (BrailleView)findViewById(R.id.brailleView1);
		b_view.setPutHandler(sp_handler);
		b_view.setFixHandler(sf_handler);
		
	
		File file = new File("/sdcard/test");
		try
		{
		so_handler = new SimpleOutputHandler(file);
		}
		catch(Exception e)
		{
			Log.e("my", e.getMessage());
		}
		b_view.setOutputHandler(so_handler);
		sm_handler = new SimpleMoveHandler();
		b_view.setMoveHandler(sm_handler);
		b_view.setFocusable(true);
		b_view.setCode(true, false, true, false, false, false);
		b_view.requestFocus();
		b_view.setTimeDoubleClick(300);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	


@Override
public boolean onKeyDown(int keyCode, KeyEvent keyEvent)
{
	switch(keyCode)
	{	
	case KeyEvent.KEYCODE_VOLUME_DOWN:

	
	case KeyEvent.KEYCODE_VOLUME_UP:
		//case KeyEvent.KEYCODE_S:
		{
			b_view.onKeyDown(keyCode, keyEvent);
			
		}
		break;
		default:{}
		break;		
	}	

    return false;
}
}
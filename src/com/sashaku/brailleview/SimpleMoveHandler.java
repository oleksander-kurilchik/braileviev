package com.sashaku.brailleview;

import java.util.concurrent.TimeUnit;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

public class SimpleMoveHandler implements IBMoveHandler {
	
	TreadAudio thread_audio;

	public SimpleMoveHandler() {
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void onButtonPressed(int id) {
		
		thread_audio = new TreadAudio(id);
		thread_audio.start();
		

	}

	@Override
	public void onButtonRelease(int id) {
		// TODO Auto-generated method stub
		//audiotrack.stop();
		thread_audio.stopAudio();
		
		

	}

	

}
class TreadAudio extends Thread
{
	int id;
	boolean is_run;
	private AudioTrack audiotrack;
	TreadAudio(int id)
	{
		this.id = id ;
		this.is_run=true;
		Log.i("my","TreadAudio costructed");
		
		
	}
	@Override
	public void run()
	{
		byte[] buffer = makeSinWave(44100,440,50,TimeUnit.MILLISECONDS);
		int s = buffer.length;
		 audiotrack = new AudioTrack(AudioManager.STREAM_MUSIC,
				44100, AudioFormat.CHANNEL_OUT_MONO,
				AudioFormat.ENCODING_PCM_8BIT, s,
				AudioTrack.MODE_STREAM);
		 audiotrack.play();
		while(is_run)
		{
			 buffer = makeSinWave(44100,30*(id+1),5,TimeUnit.MILLISECONDS);
			 
			 audiotrack.write(buffer, 0, buffer.length);
			
		}
		audiotrack.stop();
		audiotrack.flush();
		audiotrack.release();
		
		
	}
	public void stopAudio()
	{
		is_run = false;
		
	}
	private byte[] makeSinWave (double sampleRate, double frequency, long duration, TimeUnit timeUnit){
		byte[] buffer = new byte[(int) (timeUnit.toMillis(duration) * sampleRate/1000)];
		double period = sampleRate / frequency;
		for (int i = 0; i < buffer.length; i++) {
		double angle = 2d * Math.PI * i / period;
		buffer[i] = (byte) (Math.sin(angle) * 127d);
		}
		return buffer;
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		Log.i("TreadAudio","TreadAudio destructed");
	}
	

}

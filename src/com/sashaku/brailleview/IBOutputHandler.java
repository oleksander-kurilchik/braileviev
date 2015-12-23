package com.sashaku.brailleview;

public interface IBOutputHandler {
	public void open()throws Exception;
	public void write (byte code)throws Exception;
	public void close() throws Exception ;

}

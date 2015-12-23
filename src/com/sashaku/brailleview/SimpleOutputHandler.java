package com.sashaku.brailleview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class SimpleOutputHandler implements IBOutputHandler {

	private File file;
	
	FileWriter output;
	
	public SimpleOutputHandler(File file) throws FileNotFoundException
	{
		this.file = file;
		
	}

	@Override
	public void open() throws IOException
	{
		try
		{
		
		 
		 if(!file.exists()){
	            file.createNewFile();
	        }
	         output = new FileWriter(file,true);
		 
		}
		catch(FileNotFoundException e)
		{
			throw e;
			
		}
		finally
		{
			
		}

	}

	@Override
	public void write(byte code) throws IOException {
		output.write(" "+code+" ");

	}

	@Override
	public void close()  throws IOException {
		// TODO Auto-generated method stub
		output.flush();
		output.close();

	}

}

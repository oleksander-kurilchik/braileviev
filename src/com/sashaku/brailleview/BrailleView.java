package com.sashaku.brailleview;

import javax.crypto.ExemptionMechanism;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class BrailleView extends View {
	private IBMoveHandler move_handler;
	private IBOutputHandler output_handler;
	private IBFixHandler fix_handler;
	private IBPutHandler put_handler;
	
	private Rect []arr_rect;
	private Boolean []arr_flags;
	private Paint paint;
	private int item_current_touch;
	private long last_time_click;
	private long time_b_press;
	private int last_touch_item;
	

	public BrailleView(Context context) {
		super(context);
		initUI();
	}

	public BrailleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initUI();
	}

	public BrailleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);		
		initUI();		
	}
	private void initUI()
	{ 
		paint  = new  Paint();
		int x1,y1,x2,y2;
		arr_rect = new Rect[6];
		arr_flags = new Boolean[6];
		for(int i = 0;i<6;i++)
		{
			arr_flags[i]=new Boolean(false);
			arr_rect[i] = new Rect();
		}
		item_current_touch = -1;
		time_b_press = 500;
	}
	void clearCode()//очищую ведений символ
	{
		for(int i = 0;i<6;i++)
		{
			arr_flags[i]=false;
		}
		
	}
	void setTimeDoubleClick(int milisecond) // ввести час між подвійнм кліком
	{
		if(milisecond >0)
		{
		time_b_press = milisecond;
		}
	}
	void setCode(boolean one,boolean two,boolean tree,
			boolean four,boolean five,boolean six)//ввести символ
	{
		arr_flags[0] =one;
		arr_flags[1] =two;
		arr_flags[2] =tree;
		arr_flags[3] =four;
		arr_flags[4] =five;
		arr_flags[5] =six;
		
	}
	byte getCode() //отримати символ кожен біт відповідає номеру клітинки 
	{
		byte tmp_code =0;
		for(int i=0;i<6;i++)
		{
			int tmp_flag = arr_flags[i] ? 1 : 0;
			tmp_code =  (byte) (tmp_code | (byte)( (int)Math.pow(2, i)*tmp_flag ));
		
		}
		return tmp_code;
		
	}
	void setFixHandler(IBFixHandler handler)
	{
		this.fix_handler = handler;
	}
	void setMoveHandler(IBMoveHandler handler)
	{
		this.move_handler = handler;
	}
	void setPutHandler(IBPutHandler handler)
	{
		this.put_handler = handler;
	}
	void setOutputHandler(IBOutputHandler handler)
	{
		this.output_handler = handler;
	}
	
	@Override
	public void onDraw(Canvas canvas)  //тут малюеться вюшка
	{
		paint.setARGB(100, 255, 100, 100); 
		
		for(int i = 0;i<6;i++)
		{
			if (arr_flags[i]==true)
			{
			
			paint.setARGB(255, 255, 255, 100);
			canvas.drawRect(arr_rect[i], paint);
			paint.setARGB(100, 0, 0, 0);
			int radius;
			if(arr_rect[i].width()<arr_rect[i].height())
			{
				radius = (int) (0.3*arr_rect[i].width());
			}
			else 
				radius = (int) (0.3 * arr_rect[i].height());
			canvas.drawCircle(arr_rect[i].centerX() , arr_rect[i].centerY(), radius, paint);;
			;
			}
			else 
			{paint.setARGB(255, 255, 100, 100);
			canvas.drawRect(arr_rect[i], paint);
			}
			
			if (item_current_touch ==i)
			{
				paint.setARGB(100, 255, 0, 0);
				canvas.drawRect(arr_rect[i], paint);
			}
			
			
		}
		Log.d("my", "x1 y1 x2 y2 "+getWidth()+" " + getHeight());
	}
	
	private void onItemSelectedChange (int newid,int oldid )//коли кнопка нажата але ми потягли на іншу кнопку
	{
		try
		{
		move_handler.onButtonRelease(oldid);
		}
		catch(Exception e)
		{
			Log.e("brailleview", "Exeption onItemSelectedChange: class="+e.getClass().toString()+" Message = "+e.getMessage());
		}
		try
		{
		move_handler.onButtonPressed(newid);
		}
		catch(Exception e)
		{
			Log.e("brailleview", "Exeption onItemSelectedChange: class="+e.getClass().toString()+" Message = "+e.getMessage());
		}
	}
	private void onItemSelected (int id )//коли кнопка нажата
	{try
	{
		move_handler.onButtonPressed(id);	
	}
	catch(Exception e)
	{
		Log.e("brailleview", "Exeption onItemSelected: class="+e.getClass().toString()+" Message = "+e.getMessage());
	}
	}
	private void onItemUnSelected ()//коли кнопка стала не натиснутою
	{
		try
		{
		move_handler.onButtonRelease(0);
		}
		catch(Exception e)
		{
			Log.e("brailleview", "Exeption onItemUnSelected: class="+e.getClass().toString()+" Message = "+e.getMessage());
		}
	}
	
	private void onItemCheckChange(int id)//change check state
	{
		
		if(arr_flags[id] == false)
		{
			try
			{
			fix_handler.onFixButton(id+1);
		
		arr_flags[id] = true;
		
			}
			catch(Exception e)
			{
				Log.e("brailleview", "Exeption onItemCheckChange/onFixButton: class="+e.getClass().toString()+" Message = "+e.getMessage());
			}
		}
		else if(arr_flags[id] == true)
		{
			try
			{
			fix_handler.onUnFixButton(id+1);
		arr_flags[id] = false;
		
			}
			catch(Exception e)
			{
				Log.e("brailleview", "Exeption onItemCheckChange/onUnFixButton: class="+e.getClass().toString()+" Message = "+e.getMessage());
			}
		
		}
		
	}
	private void onCodePut() // коли символ введений і записуєтьсмяу файл іповідомляється про це 
	{
		try
		{
			output_handler.open();
		output_handler.write(getCode());
		output_handler.close();
		}
		catch(Exception e)
		{
			Log.e("brailleview", "Exeption onCodePut: class="+e.getClass().toString()+" Message = "+e.getMessage());
		}
		Log.i("my ", "code " +getCode());
		try
		{
		put_handler.onPutCode(getCode());
		}
		catch(Exception e)
		{
			Log.e("brailleview", "Exeption onCodePut: class="+e.getClass().toString()+" Message = "+e.getMessage());
		}
		clearCode();
	}
	@Override
	protected void onSizeChanged (int w, int h, int oldw, int oldh)//тут реагується на з-міну розмірів
	{
		int x1,y1,x2,y2;
		for(int i = 0;i<6;i++)
		{
				x1 = (i/3)* getWidth()/2 +2;
				y1 = ((i%3)* getHeight()/3)+2;
				y2 = (y1+ getHeight() /3)-2;
				x2 = (x1+ getWidth()/2)-2;
				arr_rect[i].set(x1,y1,x2,y2);				
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent keyEvent)
	{
		switch(keyCode)
		{	
		case KeyEvent.KEYCODE_VOLUME_DOWN:

		//case KeyEvent.KEYCODE_A:
			{
				if(item_current_touch!=-1)
				{
					onItemCheckChange(item_current_touch);
					return true;
				}
			}
			break;
		case KeyEvent.KEYCODE_VOLUME_UP:
			//case KeyEvent.KEYCODE_S:
			{
				if(item_current_touch!=-1)
				{
					onCodePut();
					return true;
				}
			}
			break;
			default:{}
			break;		
		}	
	
	    return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    int actionPerformed = event.getAction();	  
	    boolean return_flag=false;
	   switch(actionPerformed)
	   {
		   case MotionEvent.ACTION_MOVE:
		   {
			   if(event.getX()<0||event.getX()>getWidth()||event.getY()<0||event.getY()>getHeight() )
			   {
				   event.setAction(MotionEvent.ACTION_UP);;
				   this.onTouchEvent(event);
				   break;
			   }
			   for(int i = 0;i<6;i++)
				{
			    	if (arr_rect[i].contains((int)event.getX(),(int)event.getY()))
			    	{			    		
			    		if(item_current_touch !=i)
			    		{		    			
			    			 onItemSelectedChange (i, item_current_touch );
			    		}
			    		item_current_touch = i;
			    		break;
			    		}
				} 			   
			   return_flag=true;
			}
		   break;
		
	   case MotionEvent.ACTION_DOWN:		   
	   {
		   Log.i("myy", "curent time ACTION_DOWNE "+System.currentTimeMillis());
		   for(int i = 0;i<6;i++)
			{
		    	if (arr_rect[i].contains((int)event.getX(),(int)event.getY()))
		    	{ 
		    		onItemSelected(i);
		    		long temp_last_time_click =  System.currentTimeMillis();
		    				 if (last_time_click + time_b_press > System.currentTimeMillis()&&last_touch_item ==i)
		    				 {
		    					 onItemCheckChange(i);
		    					 temp_last_time_click= last_time_click - (time_b_press*2);
		    			}
		    		last_time_click =temp_last_time_click;
		    		item_current_touch = i;
		    	}	
			} 
		   return_flag=true;
		   }
	   
	   break;	   
	   case MotionEvent.ACTION_CANCEL:	   
	   case MotionEvent.ACTION_UP:
	   {		   
		   if(item_current_touch!=-1)
		   last_touch_item = item_current_touch;
		   item_current_touch = -1; 		  
		   return_flag=true;
		   onItemUnSelected();
	   }
	   break;
	   default:
	   {
		   return_flag =false;
	   }
	   break;
	   }	   
	   invalidate();
	    return return_flag;
	}
}

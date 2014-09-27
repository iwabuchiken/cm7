package app.main;

import java.io.File;
import java.io.IOException;

import cm7.main.R;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import app.items.WavFile;
import app.items.WavFileException;
import app.listeners.button.BO_CL;
import app.utils.CONS;
import app.utils.CONS_C;
import app.utils.Methods;
import app.utils.Methods_dlg;
import app.utils.Tags;

public class CanvasActv extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actv_canvas);
		
		// Log
		String msg_Log = "view => set";
		Log.d("CanvasActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		this.setTitle(this.getClass().getName());
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
//		CONS..mLocationManager.removeUpdates(this);
		
		// Log
		String msg_Log = "onDestroy";
		Log.d("CanvasActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
//		Methods.confirm_quit(this, keyCode);
		
//		this.onBackPressed();
		
//		this.finish();
//		
//		overridePendingTransition(0, 0);
//
//		
		// Log
		String msg_Log = "onKeyDown";
		Log.d("SensorsActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		////////////////////////////////

		// Finish actv

		////////////////////////////////
		this.finish();
		
		overridePendingTransition(0, 0);

		////////////////////////////////

		// onStop

		////////////////////////////////
		super.onStop();
		
	}//public void onBackPressed()

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		boolean res;
		
		String msg_Log;
		
		////////////////////////////////

		// Op id

		////////////////////////////////
		res = _Setup_Set_OpID();
		
		// validate
		if (res == false) {
			
			String msg = "Can't set => Op ID pref";
			Methods_dlg.dlg_ShowMessage(this, msg, R.color.red);
			
			return;
			
		}
		
		////////////////////////////////

		// listeners

		////////////////////////////////
		_Setup_SetListeners();
		
		////////////////////////////////

		// operations

		////////////////////////////////
		_op_DrawLines();
//		_test_DrawLine_2();

//		this._test_DrawLines();
		
		super.onStart();
		

	}//protected void onStart()

	private boolean 
	_Setup_Set_OpID() {
		// TODO Auto-generated method stub
		
		boolean res = Methods.set_Pref_Int(
						this, 
						CONS_C.Pref.pnake_Canvas, 
						CONS_C.Pref.pkey_Canvas_OpID, 
						CONS_C.Pref.OPID_DRAW_LINES);
		
		/******************************
			validate
		 ******************************/
		if (res == false) {
			
			// Log
			String msg_Log = "set pref => false";
			Log.e("CanvasActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return false;
			
		}
		
		return true;
		
	}//_Setup_Set_OpID

	private void 
	_test_DrawLine() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// paint

		////////////////////////////////
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
//		paint.setColor(0xFF4444FF);
		paint.setStyle(Paint.Style.FILL);
		paint.setStrokeWidth(30);
		
		////////////////////////////////

		// canvas view

		////////////////////////////////
		
		app.views.CanvasView_4 v_Canvas = 
				(app.views.CanvasView_4) findViewById(R.id.actv_canvas_canvas);
//		lm1.views.CanvasView_2 v_Canvas = 
//				(lm1.views.CanvasView_2) findViewById(R.id.actv_accelero_canvas);
//		lm1.views.CanvasView_2 v_Canvas = 
//				(lm1.views.CanvasView_2) findViewById(R.id.actv_accelero_canvas);
//		View v_Canvas = (View) findViewById(R.id.actv_accelero_canvas);
		
		int canvas_Height = v_Canvas.getHeight();
		int canvas_Width = v_Canvas.getWidth();
		
		// Log
		String msg_Log = String.format("h = %d, w = %d", canvas_Height, canvas_Width);
//		String msg_Log = "height => " + canvas_Height;
		Log.d("CanvasActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		Canvas c = new Canvas();
		
		c.drawLine(10, 10, 100, 100, paint);
		
		c.save();

//		c.drawlin
		
		v_Canvas.draw(c);
		
		v_Canvas.setBackgroundColor(Color.YELLOW);
		
//		v_Canvas.invalidate();
		
//		c.restore();
		
		// Log
		msg_Log = "canvas => line drawn";
		Log.d("CanvasActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}//_test_DrawLine()

	private void 
	_test_DrawLine_2() {
		// TODO Auto-generated method stub
		
		String msg_Log;
		
		////////////////////////////////
		
		// paint
		
		////////////////////////////////
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
//		paint.setColor(0xFF4444FF);
		paint.setStyle(Paint.Style.FILL);
		paint.setStrokeWidth(30);
		
		////////////////////////////////
		
		// canvas view
		
		////////////////////////////////
		
		app.views.CanvasView_4 c = 
				(app.views.CanvasView_4) findViewById(R.id.actv_canvas_canvas);
		
		int canvas_Height = c.getHeight();
		int canvas_Width = c.getWidth();
		
		// Log
		msg_Log = String.format("h = %d, w = %d", canvas_Height, canvas_Width);
//		String msg_Log = "height => " + canvas_Height;
		Log.d("CanvasActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		c._drawLine(50, 50, 200, 200, paint);
		
		// Log
		msg_Log = "canvas => line drawn";
		Log.d("CanvasActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}//_test_DrawLine()
	
	private void 
	_test_DrawLines() {
		// TODO Auto-generated method stub
		
		String msg_Log;
		
		////////////////////////////////
		
		// paint
		
		////////////////////////////////
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
//		paint.setColor(0xFF4444FF);
		paint.setStyle(Paint.Style.FILL);
		paint.setStrokeWidth(30);
		
		////////////////////////////////
		
		// canvas view
		
		////////////////////////////////
		
		app.views.CanvasView_4 c = 
				(app.views.CanvasView_4) findViewById(R.id.actv_canvas_canvas);
		
//		int canvas_Height = c.getHeight();
//		int canvas_Width = c.getWidth();
//		
//		// Log
//		msg_Log = String.format("h = %d, w = %d", canvas_Height, canvas_Width);
////		String msg_Log = "height => " + canvas_Height;
//		Log.d("CanvasActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		float[] data = new float[]{
				
				50, 50, 200, 50,
				50, 100, 200, 100,
				
		};
		
		c._drawLines(data, paint);
		
		// Log
		msg_Log = "canvas => line drawn";
		Log.d("CanvasActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}//_test_DrawLine()
	
	private void 
	_op_DrawLines() {
		// TODO Auto-generated method stub
		
		String msg_Log;
		
		////////////////////////////////
		
		// paint
		
		////////////////////////////////
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
//		paint.setColor(0xFF4444FF);
		paint.setStyle(Paint.Style.FILL);
		paint.setStrokeWidth(3);
		
		////////////////////////////////
		
		// canvas view
		
		////////////////////////////////
		
		app.views.CanvasView_4 c = 
				(app.views.CanvasView_4) findViewById(R.id.actv_canvas_canvas);

		////////////////////////////////

		// prep data

		////////////////////////////////
		
		double[] data_Raw = _op_DrawLines_PrepData();
		
		int len = data_Raw.length;
		
		// Log
		msg_Log = "data_Raw.length => " + len;
		Log.d("CanvasActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
//		int target_len = 50;
		int target_len = 500;
		
		float[] data_Tmp = new float[]{50, 100, 50, 250};
		
		float[] data_Final = new float[target_len * 4];
//		double[] data_Final = new double[target_len * 4];
		
		int magnifier = 250 * 1;
		
		for (int i = 0, j = 0, k = 0; j < target_len; i += 4, j ++, k += 2) {
			
			data_Final[i + 0] = 50;
//			data_Final[i + 1] = 50 + k;
			data_Final[i + 1] = 50 + j;
			
			if ((float)data_Raw[j] * 2000 < 1) {
				
				data_Final[i + 2] = 50;
//				data_Final[i + 2] = 50 + 10;
				
				// Log
				msg_Log = "less than 1: j => " + j;
				Log.d("CanvasActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			} else {

				data_Final[i + 2] = 50 + (float)data_Raw[j] * magnifier;
//				data_Final[i + 2] = 50 + (float)data_Raw[j] * 2000;
				
				// Log
				msg_Log = String.format("data_Final[%d + 2] => %f", i, data_Final[i + 2]);
				Log.d("CanvasActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
//			data_Final[i + 3] = 50 + k;
			data_Final[i + 3] = 50 + j;
//			data_Final[i + 1] = 50 + j;
			
			// Log
			if ((j % 50) == 0) {
				
				msg_Log = String.format(
						"data_Final[%d] => %f, %f, %f, %f", 
						j, 
						data_Final[i],
						data_Final[i + 1],
						data_Final[i + 2],
						data_Final[i + 3]
						);
				
				Log.d("CanvasActv.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);
				
			}
			
		}
		
//		float[] data = new float[]{
//				
//				50, 50, 200, 50,
//				50, 100, 200, 100,
//				
//		};
		
//		c._drawLines(data_Tmp, paint);
		c._drawLines(data_Final, paint);
//		
//		// Log
//		msg_Log = "canvas => line drawn";
//		Log.d("CanvasActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
	}//_op_DrawLines
	
	private double[] 
	_op_DrawLines_PrepData() {
		// TODO Auto-generated method stub
		
		String msg_Log;
		
		int buf_Size = 500;
//		int buf_Size = 100;
		
//		double[] data = new double[buf_Size];
		
		////////////////////////////////

		// file

		////////////////////////////////
		String fname = "cm7_test_wave_20140927_075254.wav";
//		String fname = "cm7_test_wave_20140927_075811.wav";
		
		File f = new File(CONS.DB.dPath_Lab, fname);
		
		double[] buffer = null;
		
		try {
			
			WavFile wavFile = WavFile.openWavFile(f);
			
			int numChannels = wavFile.getNumChannels();

			// Log
			msg_Log = "numChannels => " + numChannels;
			Log.d("CanvasActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			// Create a buffer of 100 frames
			buffer = new double[buf_Size * numChannels];
//			double[] buffer = new double[100 * numChannels];

			int framesRead;
			double min = Double.MAX_VALUE;
			double max = Double.MIN_VALUE;
			
			framesRead = wavFile.readFrames(buffer, buf_Size);
			
//			for (int i = 0; i < 10; i++) {
//				
//				// Log
//				msg_Log = String.format("%d => %f", i, buffer[i]);
//				Log.d("CanvasActv.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", msg_Log);
//				
//				data[i] = buffer[i];
//				
//			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WavFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		float[] data = new float[]{};
		
		return buffer;
//		return data;
		
	}//_op_DrawLines_PrepData

	private void _Setup_SetListeners() {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// Button: clear

		////////////////////////////////
		Button bt_Clear = (Button) findViewById(R.id.actv_canvas_bt_clear);
		
		bt_Clear.setTag(Tags.ButtonTags.ACTV_CANVAS_BT_CLEAR);
		
		bt_Clear.setOnClickListener(new BO_CL(this));
		
		////////////////////////////////
		
		// Button: go
		
		////////////////////////////////
		Button bt_Go = (Button) findViewById(R.id.actv_canvas_bt_go);
		
		bt_Go.setTag(Tags.ButtonTags.ACTV_CANVAS_BT_GO);
		
		bt_Go.setOnClickListener(new BO_CL(this));
		
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		// Log
		String msg_Log = "onStop";
		Log.d("CanvasActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		super.onStop();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actv_sensors, menu);
		return true;
	}
	
}//public class AcceleroActv extends Activity

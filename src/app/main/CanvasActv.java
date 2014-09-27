package app.main;

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
import app.listeners.button.BO_CL;
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
		
		_Setup_SetListeners();
		
//		_test_DrawLine_2();
		
		this._test_DrawLines();
		
		super.onStart();
		

	}//protected void onStart()

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
	
	private void _Setup_SetListeners() {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// Button: clear

		////////////////////////////////
		Button bt_Clear = (Button) findViewById(R.id.actv_canvas_bt_clear);
		
		bt_Clear.setTag(Tags.ButtonTags.ACTV_ACCELERO_BT_CLEAR);
		
		bt_Clear.setOnClickListener(new BO_CL(this));
		
		////////////////////////////////
		
		// Button: go
		
		////////////////////////////////
		Button bt_Go = (Button) findViewById(R.id.actv_canvas_bt_go);
		
		bt_Go.setTag(Tags.ButtonTags.ACTV_ACCELERO_BT_GO);
		
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

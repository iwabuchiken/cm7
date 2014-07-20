package app.adapters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;










import cm7.main.R;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import app.utils.CONS;
import app.utils.Methods;

public class Adp_ImpList extends ArrayAdapter<String> 
					implements OnTouchListener {

	/*--------------------------------------------------------
	 * Class fields
		--------------------------------------------------------*/
	// Context
	Context con;

	// Inflater
	LayoutInflater inflater;

	/*--------------------------------------------------------
	 * Constructor
		--------------------------------------------------------*/
	//
	public Adp_ImpList(Context con, int resourceId, List<String> items) {
		// Super
		super(con, resourceId, items);

		// Context
		this.con = con;

		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		

	}//public TIListAdapter(Context con, int resourceId, List<String> items)

	/*--------------------------------------------------------
	 * Methods
		--------------------------------------------------------*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	////////////////////////////////

		// View

		////////////////////////////////
    	View v = null;

    	if (convertView != null) {

    		v = convertView;
    		
		} else {//if (convertView != null)

			v = inflater.inflate(R.layout.list_row_actv_imp, null);

		}//if (convertView != null)
		
    	//test
    	String item = (String) getItem(position);
    	
    	_getView_SetVals(v, item);
    	
    	////////////////////////////////

		// File or folder

		////////////////////////////////
    	_getView_SetIcon(v, item);
    	
//		////////////////////////////////
//
//		// Get: view
//
//		////////////////////////////////
//		TextView tv_Main = (TextView) v.findViewById(R.id.list_row_actv_imp_tv);
//		
//		////////////////////////////////
//
//		// Set: text
//
//		////////////////////////////////
//		tv_Main.setText(item);
//		
//		////////////////////////////////
//
//		// Set: background
//
//		////////////////////////////////
////		int pref_CurrentPosition = Methods.get_Pref_Int(
////						(Activity)con, 
////						CONS.Pref.pname_MainActv, 
////						CONS.Pref.pkey_CurrentPosition, 
////						-1);
////
////		if (pref_CurrentPosition == position) {
////			
////			tv_Main.setBackgroundColor(
////					((Activity)con).getResources().getColor(R.color.blue1));
////			
////			tv_Main.setTextColor(
////					((Activity)con).getResources().getColor(R.color.white));
////			
//////			this.notifyDataSetChanged();
////			
////		} else {
////			
////			tv_Main.setBackgroundColor(
////					((Activity)con).getResources().getColor(R.color.white));
////			
////			tv_Main.setTextColor(
////					((Activity)con).getResources().getColor(R.color.black));
//////			this.notifyDataSetChanged();
////
////		}
		
		////////////////////////////////

		// Set: listener

		////////////////////////////////
//        v.setOnTouchListener(this);
		
		return v;
		
    }//public View getView(int position, View convertView, ViewGroup parent)

    private void 
    _getView_SetIcon(View v, String item) {
		// TODO Auto-generated method stub
    	////////////////////////////////

		// view: icon

		////////////////////////////////
		ImageView iv = (ImageView) v.findViewById(R.id.list_row_actv_imp_iv);
    	
    	////////////////////////////////

		// File

		////////////////////////////////
		File f = new File(CONS.ImpActv.currentPath, item);
		
		////////////////////////////////

		// set icon

		////////////////////////////////
		if (f.exists() && f.isFile()) {
			
			iv.setBackgroundDrawable(
					((Activity)con).getResources().getDrawable(R.drawable.file_48x48));
//			((Activity)con).getResources().getDrawable(R.drawable.file));
			
		} else if (f.exists() && f.isDirectory()) {

			iv.setBackgroundDrawable(
					((Activity)con).getResources().getDrawable(R.drawable.dir));
			
		} else {

			iv.setBackgroundDrawable(
				((Activity)con).getResources().getDrawable(R.drawable.ic_launcher));

		}
		
	}

	private void 
    _getView_SetVals(View v, String item) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// Get: view

		////////////////////////////////
		TextView tv_Main = (TextView) v.findViewById(R.id.list_row_actv_imp_tv);
		
		////////////////////////////////

		// Set: text

		////////////////////////////////
		tv_Main.setText(item);
		
		////////////////////////////////

		// Set: background

		////////////////////////////////
//		int pref_CurrentPosition = Methods.get_Pref_Int(
//						(Activity)con, 
//						CONS.Pref.pname_MainActv, 
//						CONS.Pref.pkey_CurrentPosition, 
//						-1);
//
//		if (pref_CurrentPosition == position) {
//			
//			tv_Main.setBackgroundColor(
//					((Activity)con).getResources().getColor(R.color.blue1));
//			
//			tv_Main.setTextColor(
//					((Activity)con).getResources().getColor(R.color.white));
//			
////			this.notifyDataSetChanged();
//			
//		} else {
//			
//			tv_Main.setBackgroundColor(
//					((Activity)con).getResources().getColor(R.color.white));
//			
//			tv_Main.setTextColor(
//					((Activity)con).getResources().getColor(R.color.black));
////			this.notifyDataSetChanged();
//
//		}

	}//_getView_SetVals(View v, String item)

	//REF http://nimroddayan.wordpress.com/2013/01/01/hooking-ontouchlistener-event-to-android-listviews-item/
    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        float currentX = event.getX();
//        TextView tv = (TextView)v;
        switch(event.getAction()) {
        
        case  MotionEvent.ACTION_DOWN:
        	
        	// Log
			String msg_log = "Down";
			Log.d("Adp_MainList.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
//            mLastX = currentX;
//            Log.v(TAG, String.format("onTouch: ACTION_DOWN for %s", tv.getText().toString()));
			
			v.setBackgroundColor(Color.BLUE);
			
            break;
            
        case MotionEvent.ACTION_OUTSIDE:
        	
        	// Log
			msg_log = "Outside";
			Log.d("Adp_MainList.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
        	
        	break;
        	
        case MotionEvent.ACTION_CANCEL:
        	
        	// Log
			msg_log = "Cancel";
			Log.d("Adp_MainList.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
//        case MotionEvent.ACTION_MOVE:
//            Log.v(TAG, "onTouch: ACTION_MOVE " + currentX);
            break;
            
        case MotionEvent.ACTION_UP:
        	
        	// Log
			msg_log = "Up";
			Log.d("Adp_MainList.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
//            if (currentX > mLastX + v.getWidth() / 6) {
//                Log.v(TAG, "Swiped!");
//                tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//            }
			
//			return false;
			
			v.setBackgroundColor(Color.BLACK);
			
            break;
            
        }
        
//        return false;
        return true;
        
    }//public boolean onTouch(View v, MotionEvent event)
    
}//public class TIListAdapter extends ArrayAdapter<TI>

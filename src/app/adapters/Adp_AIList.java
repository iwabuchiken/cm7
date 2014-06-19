package app.adapters;

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
import app.items.AI;
import app.utils.CONS;
import app.utils.Methods;

public class Adp_AIList extends ArrayAdapter<AI> implements OnTouchListener {

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
	public Adp_AIList(Context con, int resourceId, List<AI> items) {
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
    	/*----------------------------
		 * Steps
		 * 0. View
		 * 1. Set layout
		 * 2. Get view
		 * 3. Get item
		 * 4. Get bitmap
		 * 5. Get memo, or, file name
			----------------------------*/
    	/*----------------------------
		 * 0. View
			----------------------------*/
    	View v = null;

    	/*----------------------------
		 * 2.1. Set layout
			----------------------------*/
    	if (convertView != null) {

    		v = convertView;
    		
		} else {//if (convertView != null)

			v = inflater.inflate(R.layout.list_row_ai_list, null);
//			v = inflater.inflate(R.layout.list_row_simple_1, null);

		}//if (convertView != null)

//    	//debug
//    	// Log
//		String msg_Log = "position = " + position;
//		Log.d("Adp_AIList.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
    	
		////////////////////////////////

		// Set: display_TopPosition

		////////////////////////////////
		CONS.ALActv.display_TopPosition_Previous =
					CONS.ALActv.display_TopPosition_Current;
		
		CONS.ALActv.display_TopPosition_Current = position;
    	
    	////////////////////////////////

		// Get: AI

		////////////////////////////////
    	AI ai = (AI) this.getItem(position);
    	
    	////////////////////////////////

		// Set value: file name

		////////////////////////////////
    	_getView_Set_FileName(ai, v, position);
    	
    	////////////////////////////////

		// Set value: Title

		////////////////////////////////
    	_getView_Set_FileTitle(ai, v, position);
    	
    	////////////////////////////////
    	
    	// Set value: Length
    	
    	////////////////////////////////
    	_getView_Set_FileLength(ai, v, position);
    	
		
		return v;
		
    }//public View getView(int position, View convertView, ViewGroup parent)

    private void
    _getView_Set_FileLength
    (AI ai, View v, int position) {
		// TODO Auto-generated method stub
    	////////////////////////////////

		// View: length

		////////////////////////////////
    	TextView tv_Length = 
    			(TextView) v.findViewById(R.id.list_row_ai_list_tv_file_length);
    	
    	////////////////////////////////

		// Set: length

		////////////////////////////////
    	tv_Length.setText(ai.getLength());
    	
    	////////////////////////////////

		// Background

		////////////////////////////////
		tv_Length.setBackgroundColor(
				((Activity)con).getResources().getColor(R.color.black));

		tv_Length.setTextColor(
    			((Activity)con).getResources().getColor(R.color.white));
		
	}//_getView_Set_FileLength

	private void
	_getView_Set_FileTitle
    (AI ai, View v, int position) {
		// TODO Auto-generated method stub
    	////////////////////////////////

		// View: tv_Title

		////////////////////////////////
    	TextView tv_Title = 
    			(TextView) v.findViewById(R.id.list_row_ai_list_tv_title);
//    	(TextView) v.findViewById(R.id.list_row_ai_list_tv_file_name);

    	////////////////////////////////

		// Set: Title

		////////////////////////////////
    	tv_Title.setText(ai.getTitle());
    	
    	////////////////////////////////

		// Background

		////////////////////////////////
    	tv_Title.setBackgroundColor(
				((Activity)con).getResources().getColor(R.color.black));
    	
    	tv_Title.setTextColor(
    			((Activity)con).getResources().getColor(R.color.white));
//    	tv_Title.setTextColor((Activity)con).getResources().getColor(R.color.white)));
		
		
	}//private void _getView_Set_Others

	
    private void
    _getView_Set_FileName(AI ai, View v, int position) {
		// TODO Auto-generated method stub
    	////////////////////////////////

		// View: tv_FileName

		////////////////////////////////
    	TextView tv_FileName = 
    			(TextView) v.findViewById(R.id.list_row_ai_list_tv_file_name);

    	////////////////////////////////

		// Set: file name

		////////////////////////////////
    	String fileName = ai.getFile_name();
    	
    	int file_MaxLength = 20;
    	
    	if (fileName.length() > file_MaxLength) {
			
    		fileName = fileName.substring(0, file_MaxLength)
    					+ "...";
    		
		}
    	
    	
		tv_FileName.setText(fileName);
//		tv_FileName.setText(ai.getFile_name());
    	
//		// Log
//		String msg_Log = "tv_FileName => set";
//		Log.d("Adp_AIList.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
    	////////////////////////////////

		// Current position

		////////////////////////////////
    	int pref_CurrentPosition = 
    			Methods.get_Pref_Int(
    					(Activity)con, 
    					CONS.Pref.pname_ALActv, 
    					CONS.Pref.pkey_CurrentPosition_ALActv,
    					-1);
    	
		////////////////////////////////

		// Set: background

		////////////////////////////////
		if (pref_CurrentPosition == position) {
			
			tv_FileName.setBackgroundColor(
					((Activity)con).getResources().getColor(R.color.blue1));

			tv_FileName.setTextColor(
					((Activity)con).getResources().getColor(R.color.white));
			
//			this.notifyDataSetChanged();
			
		} else {
			
			tv_FileName.setBackgroundColor(
					((Activity)con).getResources().getColor(R.color.white));
			
			tv_FileName.setTextColor(
					((Activity)con).getResources().getColor(R.color.black));
//			this.notifyDataSetChanged();

		}
		
	}//_getView_Set_FileName(AI ai, View v, int position)

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
			
            break;
            
        }
        
        return false;
//        return true;
        
    }//public boolean onTouch(View v, MotionEvent event)
    
}//public class TIListAdapter extends ArrayAdapter<TI>

package app.adapters;

import java.util.ArrayList;
import java.util.List;

import cm7.main.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import app.items.WordPattern;

public class Adp_WordPatterns extends ArrayAdapter<WordPattern> {

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
	public Adp_WordPatterns(Context con, int resourceId, List<WordPattern> items) {
		// Super
		super(con, resourceId, items);

		// Context
		this.con = con;

		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		

	}//public TIListAdapter(Context con, int resourceId, List<TI> items)

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
    	////////////////////////////////

		// vars

		////////////////////////////////
    	String msg_Log;
    	
    	/*----------------------------
		 * 0. View
			----------------------------*/
    	View v = null;

    	if (convertView != null) {

    		v = convertView;
    		
		} else {//if (convertView != null)

			v = inflater.inflate(R.layout.list_row_gv, null);

		}//if (convertView != null)
		
//    	// Log
//		msg_Log = "view => initialized";
//		Log.d("Adp_WordPatterns.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//    	
    	////////////////////////////////

		// get: item

		////////////////////////////////
    	WordPattern wp = getItem(position);
    	
    	////////////////////////////////

		// view

		////////////////////////////////
//    	// Log
//		msg_Log = "getting a TextView...";
//		Log.d("Adp_WordPatterns.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
    	
    	TextView tv = (TextView) v.findViewById(R.id.list_row_gv_tv);
    	
//    	// Log
//		msg_Log = "setting text...";
//		Log.d("Adp_WordPatterns.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
    	
    	tv.setText(wp.getWord());
		
//    	return null;
		return v;
    }//public View getView(int position, View convertView, ViewGroup parent)

}//public class TIListAdapter extends ArrayAdapter<TI>

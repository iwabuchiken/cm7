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
import app.items.AI;
import app.listeners.LCL;
import app.listeners.button.BO_CL;
import app.utils.CONS;
import app.utils.Tags;

public class Adp_TIList_Move extends ArrayAdapter<AI> {

	/*--------------------------------------------------------
	 * Class fields
		--------------------------------------------------------*/
	// Context
	Context con;

	// Inflater
	LayoutInflater inflater;

	//
	CONS.Enums.MoveMode moveMode = null;
//	Methods.MoveMode moveMode = Methods.MoveMode.OFF;

//	public static ArrayList<Integer> checkedPositions;
	
	/*--------------------------------------------------------
	 * Constructor
		--------------------------------------------------------*/
	//
	public 
	Adp_TIList_Move
	(Context con, int resourceId, List<AI> items) {
		// Super
		super(con, resourceId, items);

		// Context
		this.con = con;

		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
		// Log
		String msg_Log = "Adp_TIList_Move => created";
		Log.d("Adp_TIList_Move.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

	}//public TIListAdapter(Context con, int resourceId, List<TI> items)


	public Adp_TIList_Move(Context con, int resourceId, List<AI> items, 
											CONS.Enums.MoveMode moveMode) {
		// Super
		super(con, resourceId, items);

		// Context
		this.con = con;
		this.moveMode = moveMode;

		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		

	}//public TIListAdapter(Context con, int resourceId, List<TI> items, CONS.MoveMode moveMode)

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

		// setup: positions

		////////////////////////////////
		CONS.ALActv.list_Pos_Prev = CONS.ALActv.list_Pos_Current;
		
		CONS.ALActv.list_Pos_Current = position;
    	
    	/*----------------------------
		 * 0. View
			----------------------------*/
    	View v = null;

		v = moveMode_on(v, position, convertView);
			
		return v;
    }//public View getView(int position, View convertView, ViewGroup parent)


	private View 
	moveMode_on
	(View v, int position, View convertView) {
		
    	/*----------------------------
		 * 2.1. Set layout
			----------------------------*/
    	if (convertView != null) {

    		v = convertView;
    		
		} else {//if (convertView != null)

			v = inflater.inflate(R.layout.list_row_ai_list_checkbox, null);

		}//if (convertView != null)

//    	/*----------------------------
//		 * 2.2. Get view
//			----------------------------*/
//    	ImageView iv = (ImageView) v.findViewById(R.id.list_row_checked_box_iv_thumbnail);

    	/*----------------------------
		 * 2.3. Get item
			----------------------------*/
    	AI ti = getItem(position);

//    	////////////////////////////////
//
//		// bitmap
//
//		////////////////////////////////
//    	ContentResolver cr = con.getContentResolver();
//    	
//    	// Bitmap
//    	Bitmap bmp = 
//				MediaStore.Images.Thumbnails.getThumbnail(
//							cr, 
//							ti.getFileId(), 
//							MediaStore.Images.Thumbnails.MICRO_KIND, 
//							null);
//    	
//    	/******************************
//			validate: null
//		 ******************************/
//		if (bmp == null) {
//			
//			//REF http://stackoverflow.com/questions/3035692/how-to-convert-a-drawable-to-a-bitmap answered Jun 14 '10 at 8:32
//			bmp = BitmapFactory.decodeResource(con.getResources(), R.drawable.ic_launcher);
//			
//		}
//    	
//    	// Set bitmap
//    	iv.setImageBitmap(bmp);
//    	
    	////////////////////////////////

		// memo, file name

		////////////////////////////////
		TextView tv_FileName = 
				(TextView) v.findViewById(R.id.list_row_ai_list_checkbox_tv_file_name);
		
		tv_FileName.setText(ti.getFile_name());

		tv_FileName.setClickable(true);

    	////////////////////////////////

		// View: File name
		// Set background: current position

		////////////////////////////////
//		int savedPosition = Methods.get_Pref_Int(
//								(Activity)con, 
//								CONS.Pref.pname_MainActv, 
//								CONS.Pref.pkey_CurrentPosition_TNActv, 
//								CONS.Pref.dflt_IntExtra_value);
		
//		if (savedPosition == position) {
//			
//			tv_FileName.setBackgroundResource(R.color.gold2);
//			tv_FileName.setTextColor(Color.BLACK);
//			
//		} else if (savedPosition == -1) {//if (savedPosition == position)
//			
//		} else {//if (savedPosition == position)
//			
//			tv_FileName.setBackgroundColor(Color.BLACK);
//			tv_FileName.setTextColor(Color.WHITE);
//			
//		}//if (savedPosition == position)

		// move_mode
		if (CONS.ALActv.checkedPositions.contains((Integer) position)) {
			
			tv_FileName.setTextColor(((Activity)con).getResources().getColor(R.color.white));
			tv_FileName.setBackgroundColor(Color.BLUE);
			
		} else {//if (ThumbnailActivity.move_mode == true)
				
			tv_FileName.setTextColor(((Activity)con).getResources().getColor(R.color.black));
			tv_FileName.setBackgroundColor(Color.WHITE);
				
		}
		
		////////////////////////////////

		// view: Length

		////////////////////////////////
		TextView tv_Length = 
				(TextView) v.findViewById(R.id.list_row_ai_list_checkbox_tv_file_length);
		
//		String memo = ti.getMemo();
		String file_LEn = ti.getLength();
		
		if (file_LEn != null) {
			tv_Length.setText(file_LEn);
			
		} else {//if (memo)

			tv_Length.setText("");
		}//if (memo)
		
		tv_Length.setTextColor(((Activity)con).getResources().getColor(R.color.white));
		tv_Length.setBackgroundColor(((Activity)con).getResources().getColor(R.color.black));
		
		////////////////////////////////

		// view: checkbox

		////////////////////////////////
		CheckBox cb = (CheckBox) v.findViewById(R.id.list_row_ai_list_checkbox_cb);
		
//		cb.setTag(Tags.ButtonTags.tilist_cb);
		cb.setTag(Tags.ButtonTags.ACTV_AI_LIST_MOVE_CB);
		
		if (CONS.ALActv.checkedPositions.contains((Integer) position)) {
			
			cb.setChecked(true);
			
		} else {//if (ThumbnailActivity.checkedPositions.contains((Integer) position)
			
			cb.setChecked(false);
			
		}//if (ThumbnailActivity.checkedPositions.contains((Integer) position)
		
//		cb.setBackgroundColor(((Activity)con).getResources().getColor(R.color.white));
		
		cb.setOnClickListener(new BO_CL((Activity) con, position));
		
		
		cb.setOnLongClickListener(new LCL((Activity) con, position));

		/*----------------------------
		 * 2.7. Return
			----------------------------*/
		return v;
		
	}//moveMode_on

}//public class TIListAdapter extends ArrayAdapter<TI>

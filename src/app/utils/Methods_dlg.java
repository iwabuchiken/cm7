package app.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;

import cm7.main.R;
import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import app.adapters.Adp_AIList;
import app.adapters.Adp_ListItems;
import app.adapters.Adp_WordPatterns;
import app.items.AI;
import app.items.BM;
import app.items.ListItem;
import app.items.WordPattern;
import app.listeners.LOI_LCL;
import app.listeners.dialog.DB_OCL;
import app.listeners.dialog.DB_OTL;
import app.listeners.dialog.DLOI_LCL;
import app.listeners.dialog.DOI_CL;
import app.utils.Tags.DialogTags;

public class Methods_dlg {

	public static void dlg_Db_Activity(Activity actv) {
		/****************************
		 * 1. Dialog
		 * 2. Prep => List
		 * 3. Adapter
		 * 4. Set adapter
		 * 
		 * 5. Set listener to list
		 * 6. Show dialog
			****************************/
		Dialog dlg = Methods_dlg.dlg_Template_Cancel(
							actv, R.layout.dlg_tmpl_list_cancel, 
							R.string.dlg_db_admin_title, 
							R.id.dlg_tmpl_list_cancel_bt_cancel, 
//									R.id.dlg_db_admin_bt_cancel, 
							Tags.DialogTags.GENERIC_DISMISS);
		
		/****************************
		 * 2. Prep => List
			****************************/
		List<ListItem> list = new ArrayList<ListItem>();
		
		list.add(new ListItem.Builder()
				.setText(actv.getString(
							R.string.dlg_db_admin_item_backup_db))
				.setIconID(R.drawable.menu_icon_admin_32x32)
				.setTextColor_ID(R.color.blue1)
				.build());
		list.add(new ListItem.Builder()
				.setText(actv.getString(
						R.string.dlg_db_admin_item_refresh_db))
				.setIconID(R.drawable.menu_icon_admin_32x32_brown)
				.setTextColor_ID(R.color.black)
				.build());
		list.add(new ListItem.Builder()
				.setText(actv.getString(
						R.string.dlg_db_admin_item_impfile))
						.setIconID(R.drawable.menu_icon_admin_32x32_purple)
						.setTextColor_ID(R.color.purple4)
						.build());
		list.add(new ListItem.Builder()
				.setText(actv.getString(
						R.string.dlg_db_admin_item_restore_db))
						.setIconID(R.drawable.menu_icon_admin_32x32_yellow)
						.setTextColor_ID(R.color.yellow_dark)
						.build());
		
		list.add(new ListItem.Builder()
				.setText(actv.getString(
						R.string.dlg_db_admin_item_operations))
						.setIconID(R.drawable.menu_icon_admin_32x32_green)
						.setTextColor_ID(R.color.darkgreen)
						.build());
		
//		String[] choices = {
//				actv.getString(R.string.dlg_db_admin_item_exec_sql),
//				
//				actv.getString(R.string.dlg_db_admin_item_backup_db),
//				actv.getString(R.string.dlg_db_admin_item_refresh_db),
//				actv.getString(R.string.dlg_db_admin_item_impfile),
//				actv.getString(R.string.dlg_db_admin_item_restore_db),
//				
//					};
//		
//		List<String> list = new ArrayList<String>();
//		
//		for (String item : choices) {
//			
//			list.add(item);
//			
//		}
		
		////////////////////////////////

		// Adapter

		////////////////////////////////
		CONS.MainActv.adp_ListItems_DB = new Adp_ListItems(
				actv,
//				R.layout.dlg_db_admin,
//				android.R.layout.simple_list_item_1,
				R.layout.list_row_simple_1,
				list
				);

		/******************************
			validate
		 ******************************/
		if (CONS.MainActv.adp_ListItems_DB == null) {
			
			String msg = "Can't build adapter";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			return;
			
		}
		
		
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//				actv,
////				R.layout.dlg_db_admin,
////				android.R.layout.simple_list_item_1,
//				R.layout.list_row_simple_1,
//				list
//				);

		/****************************
		 * 4. Set adapter
			****************************/
		ListView lv = (ListView) dlg.findViewById(R.id.dlg_tmpl_list_cancel_lv);
//		ListView lv = (ListView) dlg.findViewById(R.id.dlg_db_admin_lv);
		
		lv.setAdapter(CONS.MainActv.adp_ListItems_DB);
//		lv.setAdapter(adapter);
		
		/****************************
		 * 5. Set listener to list
			****************************/
		lv.setTag(Tags.DialogItemTags.DLG_DB_ADMIN_LV);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg));
		
		/****************************
		 * 6. Show dialog
			****************************/
		dlg.show();
		
		
	}//public static void dlg_db_activity(Activity actv)

	public static
	Dialog dlg_Template_Cancel
	(Activity actv, int layoutId, int titleStringId,
			int cancelButtonId, Tags.DialogTags cancelTag) {
		/****************************
		* Steps
		* 1. Set up
		* 2. Add listeners => OnTouch
		* 3. Add listeners => OnClick
		****************************/
		
		// 
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(layoutId);
		
		// Title
		dlg.setTitle(titleStringId);
		
		/****************************
		* 2. Add listeners => OnTouch
		****************************/
		//
		Button btn_cancel = (Button) dlg.findViewById(cancelButtonId);
		
		//
		btn_cancel.setTag(cancelTag);
		
		//
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg));
		
		/****************************
		* 3. Add listeners => OnClick
		****************************/
		//
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg));
		
		//
		//dlg.show();
		
		return dlg;
	
	}//public static Dialog dlg_template_okCancel()
	
	public static
	Dialog dlg_Template_Cancel_SecondDialog
	(Activity actv, Dialog dlg1,
		int layoutId, int titleStringId,
		int cancelButtonId, Tags.DialogTags cancelTag) {
		/****************************
		 * Steps
		 * 1. Set up
		 * 2. Add listeners => OnTouch
		 * 3. Add listeners => OnClick
		 ****************************/
		
		// 
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(layoutId);
		
		// Title
		dlg2.setTitle(titleStringId);
		
		/****************************
		 * 2. Add listeners => OnTouch
		 ****************************/
		//
		Button btn_cancel = (Button) dlg2.findViewById(cancelButtonId);
		
		//
		btn_cancel.setTag(cancelTag);
		
		//
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		/****************************
		 * 3. Add listeners => OnClick
		 ****************************/
		//
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		//
		//dlg.show();
		
		return dlg2;
		
	}//public static Dialog dlg_template_okCancel()

	public static void
	conf_DeleteBM(Activity actv, Dialog dlg1, BM bm) {
		// TODO Auto-generated method stub
//		Dialog dlg = Methods_dlg.dlg_Template_Cancel(
//				actv, R.layout.dlg_tmpl_list_cancel, 
//				R.string.dlg_db_admin_title, 
//				R.id.dlg_tmpl_list_cancel_bt_cancel, 
////				R.id.dlg_db_admin_bt_cancel, 
//				Tags.DialogTags.dlg_generic_dismiss);
		
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(R.layout.dlg_tmpl_confirm_simple);
//		dlg2.setContentView(R.layout.dlg_confirm_remove_folder);
		
		// Title
		dlg2.setTitle(R.string.generic_tv_confirm);

		////////////////////////////////

		// Set: Message

		////////////////////////////////
		TextView tv_Message = (TextView) dlg2.findViewById(
							R.id.dlg_tmpl_confirm_simple_tv_message);
		
		tv_Message.setText(actv.getString(R.string.dlg_conf_delete_bm_tv_message));

		////////////////////////////////

		// Set: BM position

		////////////////////////////////
		TextView tv_BM_Position = (TextView) dlg2.findViewById(
							R.id.dlg_tmpl_confirm_simple_tv_item_name);

		tv_BM_Position.setText(bm.getPosition());

		////////////////////////////////

		// Add listeners => OnTouch

		////////////////////////////////
		Button btn_ok = (Button) dlg2.findViewById(
								R.id.dlg_tmpl_confirm_simple_btn_ok);
		
		Button btn_cancel = (Button) dlg2.findViewById(
								R.id.dlg_tmpl_confirm_simple_btn_cancel);
		
		//
		btn_ok.setTag(Tags.DialogTags.dlg_conf_delete_BM_ok);
		btn_cancel.setTag(Tags.DialogTags.DLG_GENERIC_DISMISS_SECOND_DIALOG);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg2));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		/****************************
		 * 4. Add listeners => OnClick
			****************************/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, bm));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		/****************************
		 * 5. Show dialog
			****************************/
		dlg2.show();
		
		
		
	}//conf_DeleteBM(Activity actv, Dialog dlg1, BM bm)

	public static void edit_BM
	(Activity actv, Dialog dlg1, BM bm) {
		// TODO Auto-generated method stub
		
		Dialog dlg2 = Methods_dlg.dlg_template_okCancel_SecondDialog(
				actv,
				R.layout.dlg_edit_item,
				"Edit " + bm.getPosition(),
//				R.string.dlg_edit_item_title,
				
				R.id.dlg_edit_item_bt_ok, R.id.dlg_edit_item_bt_cancel,
				
//				Tags.DialogTags.dlg_edit_item_bt_ok,
				Tags.DialogTags.DLG_EDIT_ITEM_BT_OK,
//				Tags.DialogTags.dlg_generic_dismiss,
				Tags.DialogTags.GENERIC_DISMISS,
				
				dlg1, bm);

		/***************************************
		* Set: Layout params
		***************************************/
		//LinearLayout llRoot = (LinearLayout) dlg.findViewById(R.id.dlg_edit_item_ll_root);
		LinearLayout llData = 
				(LinearLayout) dlg2.findViewById(R.id.dlg_edit_item_ll_data);
		
		Display disp = actv.getWindowManager().getDefaultDisplay();
		
		int w = (int) (disp.getWidth() * CONS.Admin.DLG_WIDTH_RATIO);
		
		// Log
		Log.d("Methods_dlg.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ ":"
			+ Thread.currentThread().getStackTrace()[2].getMethodName()
			+ "]", "w=" + w);
		
		LinearLayout.LayoutParams params =
			new LinearLayout.LayoutParams(
							w,
							LayoutParams.WRAP_CONTENT);
		
		llData.setLayoutParams(params);
		
		/***************************************
		* Get data from the bm instance, then set the data
		***************************************/
		EditText etTitle = (EditText) dlg2.findViewById(R.id.dlg_edit_item_et_title);
		EditText etMemo = (EditText) dlg2.findViewById(R.id.dlg_edit_item_et_memo);
		
		etTitle.setText(bm.getTitle());
		etMemo.setText(bm.getMemo());
		
		/***************************************
		* Show dialog
		***************************************/
		dlg2.show();
		
	}//public static void edit_BM

	public static
	Dialog dlg_template_okCancel_SecondDialog
		(Activity actv, int layoutId, int titleStringId,
			int okButtonId, int cancelButtonId,
			Tags.DialogTags okTag, Tags.DialogTags cancelTag,
			
			Dialog dlg1, BM bm) {
		/****************************
		* Steps
		* 1. Set up
		* 2. Add listeners => OnTouch
		* 3. Add listeners => OnClick
		****************************/
		
		// 
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(layoutId);
		
		// Title
		dlg2.setTitle(titleStringId);
		
		/****************************
		* 2. Add listeners => OnTouch
		****************************/
		//
		Button btn_ok = (Button) dlg2.findViewById(okButtonId);
		Button btn_cancel = (Button) dlg2.findViewById(cancelButtonId);
		
		//
		btn_ok.setTag(okTag);
		btn_cancel.setTag(cancelTag);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg2));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		/****************************
		* 3. Add listeners => OnClick
		****************************/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, bm));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg2));
		
		//
		//dlg2.show();
		
		return dlg2;
	
	}//public static Dialog dlg_template_okCancel_SecondDialog()
	
	public static
	Dialog dlg_template_okCancel_ThirdDialog
	(Activity actv, 
		int layoutId, int titleStringId,
		
		int okButtonId, int cancelButtonId,
		Tags.DialogTags okTag, Tags.DialogTags cancelTag,
		
		Dialog dlg1, Dialog dlg2) {
		/****************************
		 * Steps
		 * 1. Set up
		 * 2. Add listeners => OnTouch
		 * 3. Add listeners => OnClick
		 ****************************/
		
		// 
		Dialog dlg3 = new Dialog(actv);
		
		//
		dlg3.setContentView(layoutId);
		
		// Title
		dlg3.setTitle(titleStringId);
		
		/****************************
		 * 2. Add listeners => OnTouch
		 ****************************/
		//
		Button btn_ok = (Button) dlg3.findViewById(okButtonId);
		Button btn_cancel = (Button) dlg3.findViewById(cancelButtonId);
		
		//
		btn_ok.setTag(okTag);
		btn_cancel.setTag(cancelTag);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg3));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg3));
		
		/****************************
		 * 3. Add listeners => OnClick
		 ****************************/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, dlg3));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, dlg3));
		
		
		return dlg3;
		
	}//public static Dialog dlg_template_okCancel_SecondDialog()
	
	public static
	Dialog dlg_template_okCancel_ThirdDialog
	(Activity actv, 
			Dialog dlg1, Dialog dlg2, WordPattern wp,
			
			int layoutId, int titleStringId,
			
			int okButtonId, 
			Tags.DialogTags okTag, 
			
			int cancelButtonId,
			Tags.DialogTags cancelTag
			
			) {
		/****************************
		 * Steps
		 * 1. Set up
		 * 2. Add listeners => OnTouch
		 * 3. Add listeners => OnClick
		 ****************************/
		
		// 
		Dialog dlg3 = new Dialog(actv);
		
		//
		dlg3.setContentView(layoutId);
		
		// Title
		dlg3.setTitle(titleStringId);
		
		/****************************
		 * 2. Add listeners => OnTouch
		 ****************************/
		//
		Button btn_ok = (Button) dlg3.findViewById(okButtonId);
		Button btn_cancel = (Button) dlg3.findViewById(cancelButtonId);
		
		//
		btn_ok.setTag(okTag);
		btn_cancel.setTag(cancelTag);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg1, dlg2, dlg3));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg1, dlg2, dlg3));
		
		/****************************
		 * 3. Add listeners => OnClick
		 ****************************/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, dlg3, wp));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, dlg3));
		
		
		return dlg3;
		
	}//public static Dialog dlg_template_okCancel_SecondDialog()
	
	public static
	Dialog dlg_template_okCancel_SecondDialog
	(Activity actv, int layoutId, String title,
			int okButtonId, int cancelButtonId,
			Tags.DialogTags okTag, Tags.DialogTags cancelTag,
			
			Dialog dlg1, BM bm) {
		/****************************
		 * Steps
		 * 1. Set up
		 * 2. Add listeners => OnTouch
		 * 3. Add listeners => OnClick
		 ****************************/
		
		// 
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(layoutId);
		
		// Title
		dlg2.setTitle(title);
//		dlg2.setTitle(titleStringId);
		
		/****************************
		 * 2. Add listeners => OnTouch
		 ****************************/
		//
		Button btn_ok = (Button) dlg2.findViewById(okButtonId);
		Button btn_cancel = (Button) dlg2.findViewById(cancelButtonId);
		
		//
		btn_ok.setTag(okTag);
		btn_cancel.setTag(cancelTag);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg2));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		/****************************
		 * 3. Add listeners => OnClick
		 ****************************/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, bm));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg2));
		
		//
		//dlg2.show();
		
		return dlg2;
		
	}//public static Dialog dlg_template_okCancel_SecondDialog()
	
	public static void
	dlg_PlayActv_EditTitle
	(Activity actv, AI ai, String currentTitle) {
		// TODO Auto-generated method stub
		
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(R.layout.dlg_edit_ai_title);
		
		// Title
//		dlg.setTitle(R.string.dlg_edit_title_title);
		dlg.setTitle(R.string.dlg_playactv_edit_ai_title_title);

		////////////////////////////////

		// Set: current data

		////////////////////////////////
		EditText et_Title = 
				(EditText) dlg.findViewById(R.id.dlg_edit_ai_title_et_content);
//		TextView tv_Title = 
//				(TextView) dlg.findViewById(R.id.dlg_edit_ai_title_et_content);
		
		et_Title.setText(currentTitle);
		
		et_Title.setSelection(currentTitle.length());
		
		////////////////////////////////

		// list view

		////////////////////////////////
		dlg = Methods_dlg._PlayActv_EditTitle_LVs(actv, dlg, ai);
		
		////////////////////////////////

		// Listeners

		////////////////////////////////
		/******************************
			OnTouch
		 ******************************/
		Button btn_Ok = (Button) dlg.findViewById(
									R.id.dlg_edit_ai_title_bt_add);
		
		Button btn_Cancel = (Button) dlg.findViewById(
									R.id.dlg_edit_ai_title_bt_cancel);
		
		//
//		btn_Ok.setTag(Tags.DialogTags.dlg_edit_title_bt_ok);
		btn_Ok.setTag(Tags.DialogTags.DLG_PLAYACTV_EDIT_TITLE_BT_OK);
		btn_Cancel.setTag(Tags.DialogTags.GENERIC_DISMISS);
		
		//
		btn_Ok.setOnTouchListener(new DB_OTL(actv, dlg));
		btn_Cancel.setOnTouchListener(new DB_OTL(actv, dlg));

		/******************************
			OnClick
		 ******************************/
		btn_Ok.setOnClickListener(new DB_OCL(actv, dlg, ai));
		btn_Cancel.setOnClickListener(new DB_OCL(actv, dlg));

		////////////////////////////////

		// GridView

		////////////////////////////////
		dlg = Methods_dlg._PlayActv_EditTitle_LVs(actv, dlg, ai);
		
		
		dlg.show();
		
	}//dlg_EditTitle

	private static Dialog
	_PlayActv_EditTitle_LVs
	(Activity actv, Dialog dlg, AI ai) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// views

		////////////////////////////////
		ListView lv_1 = 
				(ListView) dlg.findViewById(R.id.dlg_edit_ai_title_lv_1);
		
		ListView lv_2 = 
				(ListView) dlg.findViewById(R.id.dlg_edit_ai_title_lv_2);
		
		ListView lv_3 = 
				(ListView) dlg.findViewById(R.id.dlg_edit_ai_title_lv_3);
		
//		GridView gv_1 = (GridView) dlg.findViewById(R.id.dlg_edit_ai_title_gv_1);
//		GridView gv_2 = (GridView) dlg.findViewById(R.id.dlg_edit_ai_title_gv_2);
//		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		////////////////////////////////

		// Table: exists?

		////////////////////////////////
		String tableName = CONS.DB.tname_MemoPatterns;

		boolean res = dbu.tableExists(rdb, tableName);
		
		if (res == false) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exists: " + tableName);
			
			rdb.close();
			
			return dlg;
			
		}//if (res == false)

		////////////////////////////////

		// Get: patterns

		////////////////////////////////
		rdb = dbu.getReadableDatabase();
		
//		String sql = "SELECT * FROM " + tableName + " ORDER BY word ASC";
		
		String orderBy = CONS.DB.col_names_MemoPatterns_full[3] + " ASC"; 
		
		Cursor c = rdb.query(
						CONS.DB.tname_MemoPatterns,
						CONS.DB.col_names_MemoPatterns_full,
		//				CONS.DB.col_types_refresh_log_full,
						null, null,		// selection, args 
						null, 			// group by
						null, 		// having
						orderBy);

		
//		Cursor c = rdb.rawQuery(sql, null);

		////////////////////////////////

		// Entries?

		////////////////////////////////
		if (c.getCount() < 1) {
			
			// Log
			String msg_Log = "patterns => no entry";
			Log.e("Methods_dlg.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			rdb.close();
			
			return dlg;
			
		}
		
		////////////////////////////////

		// Get: entries

		////////////////////////////////
//		actv.startManagingCursor(c);
		
		c.moveToFirst();
		
		CONS.PlayActv.list_Dlg_EditTitle_LV_1 = new ArrayList<WordPattern>();
		CONS.PlayActv.list_Dlg_EditTitle_LV_2 = new ArrayList<WordPattern>();
		CONS.PlayActv.list_Dlg_EditTitle_LV_3 = new ArrayList<WordPattern>();
//		List<WordPattern> patternList = new ArrayList<WordPattern>();
//		List<String> patternList = new ArrayList<String>();
		
		if (c.getCount() > 0) {
			
			for (int i = 0; i < c.getCount(); i++) {
				
//				patternList.add(c.getString(3));	// "word"
				CONS.PlayActv.list_Dlg_EditTitle_LV_1.add(
//						patternList.add(
							new WordPattern.Builder()
								.setDb_Id(c.getLong(0))
								.setWord(c.getString(3))
							
								.build());	// "word"
				
				CONS.PlayActv.list_Dlg_EditTitle_LV_2.add(
//						patternList.add(
						new WordPattern.Builder()
						.setDb_Id(c.getLong(0))
						.setWord(c.getString(3))
						
						.build());	// "word"
				
				CONS.PlayActv.list_Dlg_EditTitle_LV_3.add(
//						patternList.add(
						new WordPattern.Builder()
						.setDb_Id(c.getLong(0))
						.setWord(c.getString(3))
						
						.build());	// "word"
				
				c.moveToNext();
				
			}//for (int i = 0; i < patternList.size(); i++)
			
		} else {//if (c.getCount() > 0)
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "!c.getCount() > 0");
			
		}//if (c.getCount() > 0)
		
		
//		Collections.sort(patternList);

		////////////////////////////////

		// Build: adapter

		////////////////////////////////
		CONS.PlayActv.adp_Patterns_GV_1 = new Adp_WordPatterns(
										actv,
										R.layout.add_memo_grid_view,
										CONS.PlayActv.list_Dlg_EditTitle_LV_1
//										patternList
										);
		
		CONS.PlayActv.adp_Patterns_GV_2 = new Adp_WordPatterns(
				actv,
				R.layout.add_memo_grid_view,
				CONS.PlayActv.list_Dlg_EditTitle_LV_2
//										patternList
				);
		
		CONS.PlayActv.adp_Patterns_GV_3 = new Adp_WordPatterns(
				actv,
				R.layout.add_memo_grid_view,
				CONS.PlayActv.list_Dlg_EditTitle_LV_3
//										patternList
				);
		
		/******************************
			validate
		 ******************************/
		if (CONS.PlayActv.adp_Patterns_GV_1 == null) {
			
			// Log
			String msg_Log = "CONS.PlayActv.adp_Patterns_GV => null";
			Log.e("Methods_dlg.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return dlg;
			
		}
		
		////////////////////////////////

		// set adapter

		////////////////////////////////
		lv_1.setAdapter(CONS.PlayActv.adp_Patterns_GV_1);
		
		lv_2.setAdapter(CONS.PlayActv.adp_Patterns_GV_2);
		
		lv_3.setAdapter(CONS.PlayActv.adp_Patterns_GV_3);
		
		////////////////////////////////

		// tags

		////////////////////////////////
		lv_1.setTag(Tags.DialogItemTags.DLG_ADD_MEMOS_GV_1);
		lv_2.setTag(Tags.DialogItemTags.DLG_ADD_MEMOS_GV_2);
		lv_3.setTag(Tags.DialogItemTags.DLG_ADD_MEMOS_GV_3);
		
		////////////////////////////////

		// listener

		////////////////////////////////
		////////////////////////////////

		// DOI_CL

		////////////////////////////////
		lv_1.setOnItemClickListener(new DOI_CL(actv, dlg));
		lv_2.setOnItemClickListener(new DOI_CL(actv, dlg));
		lv_3.setOnItemClickListener(new DOI_CL(actv, dlg));

		////////////////////////////////

		// DLOI_LCL

		////////////////////////////////
		lv_1.setOnItemLongClickListener(new DLOI_LCL(actv, dlg));
		lv_2.setOnItemLongClickListener(new DLOI_LCL(actv, dlg));
		lv_3.setOnItemLongClickListener(new DLOI_LCL(actv, dlg));
		
//		gv_1.setAdapstener(new DOI_CL(actv, dlg));
		
//		gv_2.setAdapter(CONS.PlayActv.adp_Patterns_GV_2);
//		
////		gv_1.setTag(Tags.DialogItemTags.dlg_add_memos_gv);
//		gv_2.setTag(Tags.DialogItemTags.DLG_ADD_MEMOS_GV_2);
//		
//		gv_2.setOnItemClickListener(new DOI_CL(actv, dlg));
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "GridView setup => Done");

		////////////////////////////////

		// Closing

		////////////////////////////////
		
		rdb.close();
		
		return dlg;
		
	}//dlg_EditTitle_GridView

	public static Dialog 
	dlg_Template_Cancel
	(Activity actv,
			int layoutId, String title, 
			int cancelButtonId, DialogTags cancelTag) {
		// TODO Auto-generated method stub
		
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(layoutId);
		
		// Title
		dlg.setTitle(title);
		
		/****************************
		* 2. Add listeners => OnTouch
		****************************/
		//
		Button btn_cancel = (Button) dlg.findViewById(cancelButtonId);
		
		//
		btn_cancel.setTag(cancelTag);
		
		//
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg));
		
		/****************************
		* 3. Add listeners => OnClick
		****************************/
		//
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg));
		
		//
		//dlg.show();
		
		return dlg;		
	}//dlg_Template_Cancel

	
	public static void 
	conf_DeleteAL
	(Activity actv, Dialog dlg1, AI ai, int alList_Position) {
		// TODO Auto-generated method stub

		Dialog dlg2 = new Dialog(actv);

		// layout
//		dlg2.setContentView(R.layout.dlg_tmpl_confirm_simple);
		dlg2.setContentView(R.layout.dlg_tmpl_confirm_simple_checkbox);
		
		// Title
		dlg2.setTitle(R.string.generic_tv_confirm);

		////////////////////////////////

		// Set: Message

		////////////////////////////////
		TextView tv_Message = (TextView) dlg2.findViewById(
//							R.id.dlg_tmpl_confirm_simple_tv_message);
							R.id.dlg_tmpl_confirm_simple_cb_tv_message);
		
//		tv_Message.setText(actv.getString(R.string.dlg_conf_delete_bm_tv_message));
		tv_Message.setText(actv.getString(R.string.dlg_conf_delete_ai_tv_message));

		////////////////////////////////

		// Set: BM position

		////////////////////////////////
		TextView tv_ItemName = (TextView) dlg2.findViewById(
							R.id.dlg_tmpl_confirm_simple_cb_tv_item_name);

		tv_ItemName.setText(ai.getFile_name());

		////////////////////////////////

		// Add listeners => OnTouch

		////////////////////////////////
		Button btn_ok = (Button) dlg2.findViewById(
								R.id.dlg_tmpl_confirm_simple_cb_btn_ok);
		
		Button btn_cancel = (Button) dlg2.findViewById(
								R.id.dlg_tmpl_confirm_simple_cb_btn_cancel);
		
		//
//		btn_ok.setTag(Tags.DialogTags.dlg_conf_delete_BM_ok);
		btn_ok.setTag(Tags.DialogTags.DLG_CONF_DELETE_AI_OK);
		btn_cancel.setTag(Tags.DialogTags.DLG_GENERIC_DISMISS_SECOND_DIALOG);
//		btn_cancel.setTag(Tags.DialogTags.dlg_generic_dismiss_second_dialog);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg2));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		/****************************
		 * 4. Add listeners => OnClick
			****************************/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, ai, alList_Position));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		/****************************
		 * 5. Show dialog
			****************************/
		dlg2.show();
		
	}//conf_DeleteAL

	public static void 
	edit_AI
	(Activity actv, 
			Dialog dlg1, AI ai,
			int aiList_Position) {
		// TODO Auto-generated method stub
		Dialog dlg2 = Methods_dlg.dlg_template_okCancel_SecondDialog(
				actv,
				R.layout.dlg_edit_ai,
				actv.getString(R.string.dlg_edit_ai_title)
				+ " : " 
				+ ai.getFile_name(),
//				R.string.dlg_edit_item_title,
				
				R.id.dlg_edit_ai_bt_ok, R.id.dlg_edit_ai_bt_cancel,
				
//				Tags.DialogTags.dlg_edit_item_bt_ok,
				Tags.DialogTags.DLG_EDIT_AI_BT_OK,
//				Tags.DialogTags.dlg_generic_dismiss,
				Tags.DialogTags.GENERIC_DISMISS,
				
				dlg1, ai);

		/***************************************
		* Set: Layout params
		***************************************/
		//LinearLayout llRoot = (LinearLayout) dlg.findViewById(R.id.dlg_edit_item_ll_root);
		LinearLayout llData = 
				(LinearLayout) dlg2.findViewById(R.id.dlg_edit_ai_ll_data);
		
		Display disp = actv.getWindowManager().getDefaultDisplay();
		
		int w = (int) (disp.getWidth() * CONS.Admin.DLG_WIDTH_RATIO);
		
		// Log
		Log.d("Methods_dlg.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ ":"
			+ Thread.currentThread().getStackTrace()[2].getMethodName()
			+ "]", "w=" + w);
		
		LinearLayout.LayoutParams params =
			new LinearLayout.LayoutParams(
							w,
							LayoutParams.WRAP_CONTENT);
		
		llData.setLayoutParams(params);
		
		/***************************************
		* Get data from the AI instance, then set the data
		***************************************/
		EditText et_FileName = 
				(EditText) dlg2.findViewById(R.id.dlg_edit_ai_et_file_name);
		EditText et_FilePath = 
				(EditText) dlg2.findViewById(R.id.dlg_edit_ai_et_file_path);
		
		EditText et_Title = 
				(EditText) dlg2.findViewById(R.id.dlg_edit_ai_et_title);
		EditText et_Memo = 
				(EditText) dlg2.findViewById(R.id.dlg_edit_ai_et_memo);
		
		et_FileName.setText(ai.getFile_name());
		et_FilePath.setText(ai.getFile_path());
		
		et_Title.setText(ai.getTitle());
		et_Memo.setText(ai.getMemo());
		
		/***************************************
		* Show dialog
		***************************************/
		dlg2.show();		
		
	}//edit_AI

	private static Dialog 
	dlg_template_okCancel_SecondDialog
	(Activity actv,
			int layoutId, String title, 
			int okButtonId, int cancelButtonId, 
			DialogTags okTag, DialogTags cancelTag, 
			Dialog dlg1, AI ai) {
		// TODO Auto-generated method stub
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(layoutId);
		
		// Title
		dlg2.setTitle(title);
//		dlg2.setTitle(titleStringId);
		
		/****************************
		 * 2. Add listeners => OnTouch
		 ****************************/
		//
		Button btn_ok = (Button) dlg2.findViewById(okButtonId);
		Button btn_cancel = (Button) dlg2.findViewById(cancelButtonId);
		
		//
		btn_ok.setTag(okTag);
		btn_cancel.setTag(cancelTag);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg2));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		/****************************
		 * 3. Add listeners => OnClick
		 ****************************/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, ai));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg2));
		
		//
		//dlg2.show();
		
		return dlg2;
		
	}//dlg_template_okCancel_SecondDialog

	public static void
	dlg_ShowMessage(Activity actv, String message) {
		
		Dialog dlg = Methods_dlg.dlg_Template_Cancel(
				actv, R.layout.dlg_tmpl_toast_ok, 
				R.string.generic_tv_confirm, 
				R.id.dlg_tmpl_toast_ok_bt_cancel, 
//				R.id.dlg_db_admin_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS);
		
		TextView tv_Message = 
				(TextView) dlg.findViewById(R.id.dlg_tmpl_toast_ok_tv_message);
		
		tv_Message.setText(message);
		
		dlg.show();
		
	}
	
	public static void
	dlg_ShowMessage
	(Activity actv, String message, Tags.DialogTags tag) {
		
		Dialog dlg = Methods_dlg.dlg_Template_Cancel(
				actv, R.layout.dlg_tmpl_toast_ok, 
				R.string.generic_tv_confirm, 
				R.id.dlg_tmpl_toast_ok_bt_cancel, 
//				R.id.dlg_db_admin_bt_cancel, 
				tag);
		
		TextView tv_Message = 
				(TextView) dlg.findViewById(R.id.dlg_tmpl_toast_ok_tv_message);
		
		tv_Message.setText(message);
		
		dlg.show();
		
	}//dlg_ShowMessage
	
	public static void
	dlg_ShowMessage_Duration
	(Activity actv, String message, int duration) {
		
		final Dialog dlg = Methods_dlg.dlg_Template_Cancel(
				actv, R.layout.dlg_tmpl_toast_ok, 
				R.string.generic_tv_confirm, 
				R.id.dlg_tmpl_toast_ok_bt_cancel, 
//				R.id.dlg_db_admin_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS);
		
		TextView tv_Message = 
				(TextView) dlg.findViewById(R.id.dlg_tmpl_toast_ok_tv_message);
		
		tv_Message.setText(message);
		
		dlg.show();
		
		//REF http://xjaphx.wordpress.com/2011/07/13/auto-close-dialog-after-a-specific-time/
		final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                dlg.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, duration); // after 2 second (or 2000 miliseconds), the task will be active.
		
	}

	public static void 
	impActv_OpList
	(Activity actv, String item_Name) {
		// TODO Auto-generated method stub
		
		String title = null;
		
		int title_Length = 20;
		
		if (item_Name.length() > title_Length) {
			
			title = item_Name.substring(0, title_Length)
					+ "...";
			
		} else {
			
			title = item_Name;

		}
		
		Dialog dlg = Methods_dlg.dlg_Template_Cancel(
						actv, R.layout.dlg_tmpl_list_cancel, 
						title, 
//						R.string.dlg_impactv_list_title, 
						R.id.dlg_tmpl_list_cancel_bt_cancel, 
		//				R.id.dlg_db_admin_bt_cancel, 
						Tags.DialogTags.GENERIC_DISMISS);
		
		/****************************
		* 2. Prep => List
		****************************/
		String[] choices = {
				
				actv.getString(R.string.dlg_impactv_list_item_import),
		
		};
		
		List<String> list = new ArrayList<String>();
		
		for (String item : choices) {
		
			list.add(item);
		
		}
		
		/****************************
		* 3. Adapter
		****************************/
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							actv,
							R.layout.list_row_simple_1,
							list
		);
		
		/****************************
		* 4. Set adapter
		****************************/
		ListView lv = (ListView) dlg.findViewById(R.id.dlg_tmpl_list_cancel_lv);
		
		lv.setAdapter(adapter);
		
		/****************************
		* 5. Set listener to list
		****************************/
		lv.setTag(Tags.DialogItemTags.DLG_IMPACTV_LIST);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg, item_Name));
		
		/****************************
		* 6. Show dialog
		****************************/
		dlg.show();
		
		
	}//impActv_OpList

	public static void 
	dlg_IsEmpty
	(Activity actv, Dialog dlg1) {
		// TODO Auto-generated method stub
		EditText et = (EditText) dlg1.findViewById(R.id.dlg_tmpl_edittext_simple_et);
//		EditText et = (EditText) dlg1.findViewById(R.id.dlg_create_folder_et);
		String folderName = et.getText().toString();
		
		//
		if (!folderName.equals("")) {
			/*----------------------------
			 * 2. If yes, go to Methods.createFolder()
				----------------------------*/
			Methods_dlg.dlg_CreateDir_Confirm(actv, dlg1);
			
			return;
			
		}//if (!folderName.equals(""))

		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(R.layout.dlg_input_empty);
		
		// Title
		dlg2.setTitle(R.string.generic_notice);
		
		/*----------------------------
		 * 2. Add listeners => OnTouch
			----------------------------*/
		//
		Button btn_ok = (Button) dlg2.findViewById(R.id.dlg_input_empty_btn_reenter);
//		Button btn_cancel = (Button) dlg2.findViewById(R.id.dlg_input_empty_btn_cancel);
		
		//
//		btn_ok.setTag(DialogTags.dlg_input_empty_reenter);
		btn_ok.setTag(DialogTags.DLG_GENERIC_DISMISS_SECOND_DIALOG);
//		btn_cancel.setTag(DialogTags.dlg_input_empty_cancel);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg2));
//		btn_cancel.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg2));
		
		/*----------------------------
		 * 3. Add listeners => OnClick
			----------------------------*/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
//		btn_cancel.setOnClickListener(new DialogButtonOnClickListener(actv, dlg, dlg2));
		
		//
		dlg2.show();
		
	}//dlg_IsEmpty

	private static void 
	dlg_CreateDir_Confirm
	(Activity actv, Dialog dlg1) {
		// TODO Auto-generated method stub
		
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(R.layout.dlg_confirm_create_folder);
		
		// Title
		dlg2.setTitle(R.string.generic_tv_confirm);
		
		/*----------------------------
		 * 2. Set folder name to text view
			----------------------------*/
		EditText et = (EditText) dlg1.findViewById(R.id.dlg_tmpl_edittext_simple_et);
//		EditText et = (EditText) dlg1.findViewById(R.id.dlg_create_folder_et);
		
		TextView tv = (TextView) dlg2.findViewById(
							R.id.dlg_confirm_create_folder_tv_table_name);
		
		// Log
		String msg_Log = "et => " + et;
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		tv.setText(et.getText().toString());
		
		/*----------------------------
		 * 3. Add listeners => OnTouch
			----------------------------*/
		//
		Button btn_ok = (Button) dlg2.findViewById(
							R.id.dlg_confirm_create_folder_btn_ok);
		
		Button btn_cancel = (Button) dlg2.findViewById(
							R.id.dlg_confirm_create_folder_btn_cancel);
		
		//
//		btn_ok.setTag(DialogTags.dlg_confirm_create_folder_ok);
		btn_ok.setTag(DialogTags.DLG_CONFIRM_CREATE_FOLDER_OK);
//		btn_cancel.setTag(DialogTags.dlg_confirm_create_folder_cancel);
//		btn_cancel.setTag(DialogTags.DLG_CONFIRM_CREATE_FOLDER_CANCEL);
//		btn_cancel.setTag(DialogTags.dlg_generic_dismiss_second_dialog);
		btn_cancel.setTag(DialogTags.DLG_GENERIC_DISMISS_SECOND_DIALOG);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg2));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		/*----------------------------
		 * 4. Add listeners => OnClick
			----------------------------*/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		/*----------------------------
		 * 5. Show dialog
			----------------------------*/
		dlg2.show();
		
	}//dlg_CreateDir_Confirmed

	public static void 
	dlg_ACTV_MAIN_LV
	(Activity actv, String item) {
		// TODO Auto-generated method stub
		
		Dialog dlg1 = Methods_dlg.dlg_Template_Cancel(
				actv,
				R.layout.dlg_tmpl_cancel_lv,
				item,
				
				R.id.dlg_tmpl_cancel_lv_bt_cancel,
				Tags.DialogTags.GENERIC_DISMISS);
		
		/****************************
		* 2. Prep => List
		****************************/
		String[] choices = {
					actv.getString(R.string.dlg_actvmain_lv_delete),
					};
		
		List<String> list = new ArrayList<String>();
		
		for (String choice : choices) {
		
			list.add(choice);
		
		}
		
		/****************************
		* 3. Adapter
		****************************/
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		actv,
		//R.layout.dlg_db_admin,
		R.layout.list_row_simple_1,
		//android.R.layout.simple_list_item_1,
		list
		);
		
		/****************************
		* 4. Set adapter
		****************************/
		ListView lv = (ListView) dlg1.findViewById(R.id.dlg_tmpl_cancel_lv_lv);
		
		lv.setAdapter(adapter);
		
		/****************************
		* 5. Set listener to list
		****************************/
		lv.setTag(Tags.DialogItemTags.DLG_ACTVMAIN_LONGCLICK);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg1, item));
		
		/***************************************
		* Modify the list view height
		***************************************/
		lv.setLayoutParams(
				new LinearLayout.LayoutParams(
						300,	//	Width
						LayoutParams.WRAP_CONTENT	//	Height
				));
		
		/***************************************
		* Modify: Button layout
		***************************************/
		LinearLayout llButton =
					(LinearLayout) dlg1.findViewById(R.id.dlg_tmpl_cancel_lv_ll_filepath);
//		(LinearLayout) dlg1.findViewById(R.id.actv_imp_ll_filepath);
		
		LinearLayout.LayoutParams params =
				new LinearLayout.LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT);
		
		params.gravity = Gravity.CENTER_HORIZONTAL;
		
		llButton.setLayoutParams(params);
		
		
		/****************************
		* 6. Show dialog
		****************************/
		dlg1.show();
		
	}//dlg_ACTV_MAIN_LV

	public static void 
	conf_DeleteFolder
	(Activity actv, Dialog dlg1,
			String folderName, String dlg1_Choice) {
		// TODO Auto-generated method stub
		
		Dialog dlg2 = new Dialog(actv);

		// layout
//		dlg2.setContentView(R.layout.dlg_tmpl_confirm_simple);
		dlg2.setContentView(R.layout.dlg_tmpl_confirm_simple);
		
		// Title
		dlg2.setTitle(R.string.generic_tv_confirm);

		////////////////////////////////

		// Set: Message

		////////////////////////////////
		TextView tv_Message = (TextView) dlg2.findViewById(
//							R.id.dlg_tmpl_confirm_simple_tv_message);
							R.id.dlg_tmpl_confirm_simple_tv_message);
//							R.id.dlg_tmpl_confirm_simple_cb_tv_message);
		
//		tv_Message.setText(actv.getString(R.string.dlg_conf_delete_bm_tv_message));
		tv_Message.setText(actv.getString(
								R.string.dlg_actvmain_lv_delete_confirm_message));

		////////////////////////////////

		// Set: Folder name

		////////////////////////////////
		TextView tv_ItemName = (TextView) dlg2.findViewById(
							R.id.dlg_tmpl_confirm_simple_tv_item_name);
//							R.id.dlg_tmpl_confirm_simple_cb_tv_item_name);

		tv_ItemName.setText(folderName);

		////////////////////////////////

		// Add listeners => OnTouch

		////////////////////////////////
		Button btn_ok = (Button) dlg2.findViewById(
								R.id.dlg_tmpl_confirm_simple_btn_ok);
		
		Button btn_cancel = (Button) dlg2.findViewById(
								R.id.dlg_tmpl_confirm_simple_btn_cancel);
		
		//
		btn_ok.setTag(Tags.DialogTags.DLG_DELETE_FOLDER_CONF_OK);
		btn_cancel.setTag(Tags.DialogTags.DLG_GENERIC_DISMISS_SECOND_DIALOG);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg2));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg2));
		
		/****************************
		 * 4. Add listeners => OnClick
			****************************/
		//
		btn_ok.setOnClickListener(
					new DB_OCL(actv, dlg1, dlg2, folderName));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2));
		
		/****************************
		 * 5. Show dialog
			****************************/
		dlg2.show();
		
	}//conf_DeleteFolder

	public static void 
	dlg_Create_Dir
	(Activity actv) {
		// TODO Auto-generated method stub
		
		Dialog dlg1 = new Dialog(actv);
		
		//
		dlg1.setContentView(R.layout.dlg_tmpl_edittext_simple);
		
		// Title
		dlg1.setTitle(R.string.dlg_create_dir_title);
//		dlg2.setTitle(titleStringId);
		
		////////////////////////////////

		// message

		////////////////////////////////
		TextView tv_Message = 
				(TextView) dlg1.findViewById(R.id.dlg_tmpl_edittext_simple_tv_message);
		
		tv_Message.setText(actv.getString(R.string.dlg_create_dir_message));
		
		/****************************
		 * 2. Add listeners => OnTouch
		 ****************************/
		//
		Button btn_ok = 
				(Button) dlg1.findViewById(R.id.dlg_tmpl_edittext_simple_btn_ok);
		Button btn_cancel = 
				(Button) dlg1.findViewById(R.id.dlg_tmpl_edittext_simple_btn_cancel);
		
		//
		btn_ok.setTag(Tags.DialogTags.DLG_CREATE_DIR_OK);
		btn_cancel.setTag(Tags.DialogTags.GENERIC_DISMISS);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg1));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg1));
		
		/****************************
		 * 3. Add listeners => OnClick
		 ****************************/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1));
		
		
		////////////////////////////////

		// show

		////////////////////////////////
		dlg1.show();
		
	}//dlg_Create_Folder

	public static void 
	dlg_Move_AI
	(Activity actv, Dialog dlg1, AI ai,
			int aiList_Position) {
		// TODO Auto-generated method stub

		Dialog dlg2 = Methods_dlg.dlg_Template_Cancel_SecondDialog(
					actv, dlg1,
					R.layout.dlg_tmpl_cancel_lv_2, 
					R.string.dlg_alactv_list_long_click_item_move, 
					
					R.id.dlg_tmpl_cancel_lv_2_bt_cancel, 
					Tags.DialogTags.DLG_GENERIC_DISMISS_SECOND_DIALOG);
		
		////////////////////////////////

		// build: dir list

		////////////////////////////////
		String currentPath = StringUtils.join(
						new String[]{
								CONS.Paths.dpath_Storage_Sdcard, 
								CONS.Paths.dname_Base},
						File.separator);
		
		if (currentPath == null) {
			
			String msg = "Can't get the current path: " + currentPath;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return;
			
		}
		
//		CONS.ALActv.dir_List = Methods.get_DirList(currentPath);
		List<String> dir_List = Methods.get_DirList(currentPath);
		CONS.ALActv.dir_List = new ArrayList<String>();
		
		for (String dirName : dir_List) {
//			for (String dirName : CONS.ALActv.dir_List) {
			
			CONS.ALActv.dir_List.add(CONS.DB.tname_CM7 + File.separator + dirName);
//			dirName = CONS.DB.tname_CM7 + File.separator + dirName;
			
		}
		
		// Log
		String msg_Log = "dir list => modified";
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		CONS.ALActv.dir_List.add(CONS.Admin.dirString_UpperDir);
		
		////////////////////////////////
		
		// set: adapter
		
		////////////////////////////////
		CONS.ALActv.adp_DirList = new ArrayAdapter<String>(
						actv,
						R.layout.list_row_simple_1,
						CONS.ALActv.dir_List
		);
		
		ListView lv = (ListView) dlg2.findViewById(R.id.dlg_tmpl_cancel_lv_2_lv);
		
		lv.setAdapter(CONS.ALActv.adp_DirList);
		
		////////////////////////////////
		
		// set: listener
		
		////////////////////////////////
		lv.setTag(Tags.DialogItemTags.DLG_ALACTV_LIST_MOVE_FILE);
		
		// Item click
		lv.setOnItemClickListener(
				new DOI_CL(actv, dlg1, dlg2, ai, aiList_Position));
		
		// long click
		lv.setOnItemLongClickListener(
				new LOI_LCL(actv, dlg1, dlg2, ai, aiList_Position));
		
		////////////////////////////////

		// set: pref: pkey_ALActv__CurPath_Move

		////////////////////////////////
		boolean res = Methods.set_Pref_String(
				actv, 
				CONS.Pref.pname_ALActv, 
				CONS.Pref.pkey_ALActv__CurPath_Move, 
				CONS.DB.tname_CM7);
		
		////////////////////////////////

		// show

		////////////////////////////////
		dlg2.show();
		
	}//dlg_Move_AI

	/******************************
		@param choice => this method doesn't conduct validation as to<br>
						whether the choice is the same as the current<br>
						ai.table_name
	 ******************************/
	public static void 
	conf_MoveAi
	(Activity actv, 
		Dialog dlg1, Dialog dlg2,
		AI ai, int aiList_Position, String choice) {
		// TODO Auto-generated method stub
		
		Dialog dlg3 = new Dialog(actv);
		
		//
		dlg3.setContentView(R.layout.dlg_tmpl_confirm_simple);
		
		// Title
		dlg3.setTitle(R.string.generic_notice);
		
		////////////////////////////////

		// display

		////////////////////////////////
		TextView tv_Message = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_message);
		
		TextView tv_Choice = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_item_name);

		tv_Message.setText(actv.getString(
						R.string.dlg_alactv_move_files_confirm_message));
		
		tv_Choice.setText(ai.getFile_name() + " => " + choice);
		
		/****************************
		 * 2. Add listeners => OnTouch
		 ****************************/
		//
		Button btn_ok = 
				(Button) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_btn_ok);
		Button btn_cancel = 
				(Button) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_btn_cancel);
		
		//
		btn_ok.setTag(Tags.DialogTags.DLG_ALACTV_MOVEFILE_CONF_OK);
//		btn_cancel.setTag(Tags.DialogTags.dlg_generic_dismiss_third_dialog);
		btn_cancel.setTag(Tags.DialogTags.DLG_GENERIC_DISMISS_THIRD_DIALOG);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg3));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg3));
		
		/****************************
		 * 3. Add listeners => OnClick
		 ****************************/
		//
		btn_ok.setOnClickListener(
					new DB_OCL(
							actv, dlg1, dlg2, dlg3,
							ai, aiList_Position, choice
					));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, dlg3));
		
		dlg3.show();
		
	}//conf_MoveAi

	public static void
	dlg_ShowMessage
	(Activity actv, String message, int colorId) {
		
		Dialog dlg = Methods_dlg.dlg_Template_Cancel(
				actv, R.layout.dlg_tmpl_toast_ok, 
				R.string.generic_tv_confirm, 
				R.id.dlg_tmpl_toast_ok_bt_cancel, 
//				R.id.dlg_db_admin_bt_cancel, 
				Tags.DialogTags.GENERIC_DISMISS);
		
		TextView tv_Message = 
				(TextView) dlg.findViewById(R.id.dlg_tmpl_toast_ok_tv_message);
		
		tv_Message.setText(message);
		
		////////////////////////////////

		// background

		////////////////////////////////
		tv_Message.setBackgroundColor(actv.getResources().getColor(colorId));
		
		dlg.show();
		
	}//dlg_ShowMessage

	public static void 
	dlg_Db_Operations
	(Activity actv, Dialog d1) {
		// TODO Auto-generated method stub
		
		Dialog d2 = Methods_dlg.dlg_Template_Cancel_SecondDialog(
				actv, d1,
				R.layout.dlg_tmpl_cancel_lv_2, 
				R.string.dlg_alactv_list_long_click_item_move, 
				
				R.id.dlg_tmpl_cancel_lv_2_bt_cancel, 
				Tags.DialogTags.DLG_GENERIC_DISMISS_SECOND_DIALOG);

		////////////////////////////////

		// Prep => List

		////////////////////////////////
		List<ListItem> list = new ArrayList<ListItem>();
		
		list.add(new ListItem.Builder()
				.setText(actv.getString(
							R.string.dlg_db_admin_item_op_imp_db))
				.setIconID(R.drawable.menu_icon_admin_32x32)
				.setTextColor_ID(R.color.blue1)
				.build());
		list.add(new ListItem.Builder()
				.setText(actv.getString(
						R.string.dlg_db_admin_item_op_imp_patterns))
				.setIconID(R.drawable.menu_icon_admin_32x32_brown)
				.setTextColor_ID(R.color.black)
				.build());
		
		////////////////////////////////

		// Adapter

		////////////////////////////////
		CONS.MainActv.adp_ListItems_Operations = new Adp_ListItems(
				actv,
//				R.layout.dlg_db_admin,
//				android.R.layout.simple_list_item_1,
				R.layout.list_row_simple_1,
				list
				);

		/******************************
			validate
		 ******************************/
		if (CONS.MainActv.adp_ListItems_Operations == null) {
			
			String msg = "Can't build adapter";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			return;
			
		}
		
		ListView lv = (ListView) d2.findViewById(R.id.dlg_tmpl_cancel_lv_2_lv);
		
		lv.setAdapter(CONS.MainActv.adp_ListItems_Operations);
		
		////////////////////////////////
		
		// set: listener
		
		////////////////////////////////
		lv.setTag(Tags.DialogItemTags.DLG_ACTV_MAIN_OPERATIONS);
		
		// Item click
		lv.setOnItemClickListener(
				new DOI_CL(actv, d1, d2));
		
		////////////////////////////////
	
		// show
	
		////////////////////////////////
		d2.show();		
		
	}//dlg_Db_Operations

	public static void 
	conf_Import_DB
	(Activity actv, Dialog d1, Dialog d2) {
		// TODO Auto-generated method stub
		
		Dialog dlg3 = 
				Methods_dlg.dlg_Tmpl_OkCancel_ThirdDialog(
						actv, 
						R.layout.dlg_tmpl_confirm_simple, 
						R.string.generic_tv_confirm, 
						
						R.id.dlg_tmpl_confirm_simple_btn_ok, 
						R.id.dlg_tmpl_confirm_simple_btn_cancel, 
						
						Tags.DialogTags.DLG_CONF_IMPORT_DB_OK, 
						Tags.DialogTags.DLG_GENERIC_DISMISS_THIRD_DIALOG, 
						
						d1, d2);
		
		////////////////////////////////

		// view: message

		////////////////////////////////
		TextView tv_Msg = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_message);
		
		tv_Msg.setText(actv.getString(
								R.string.dlg_db_admin_item_op_imp_db)
								+ "?");
		
		////////////////////////////////

		// view: item name

		////////////////////////////////
		TextView tv_ItemName = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_item_name);
//		dlg_tmpl_confirm_simple_tv_message
		
		tv_ItemName.setText(CONS.DB.dbName_Importing);
//		tv_ItemName.setText(actv.getString(R.string.commons_import_db_name));
		
		////////////////////////////////

		// show

		////////////////////////////////
		dlg3.show();
		
		
	}//conf_Import_DB

	public static
	Dialog dlg_Tmpl_OkCancel_ThirdDialog
	(Activity actv, 
		int layoutId, int titleStringId,
		
		int okButtonId, int cancelButtonId,
		Tags.DialogTags okTag, Tags.DialogTags cancelTag,
		
		Dialog dlg1, Dialog dlg2) {
		/****************************
		 * Steps
		 * 1. Set up
		 * 2. Add listeners => OnTouch
		 * 3. Add listeners => OnClick
		 ****************************/
		
		// 
		Dialog dlg3 = new Dialog(actv);
		
		//
		dlg3.setContentView(layoutId);
		
		// Title
		dlg3.setTitle(titleStringId);
		
		/****************************
		 * 2. Add listeners => OnTouch
		 ****************************/
		//
		Button btn_ok = (Button) dlg3.findViewById(okButtonId);
		Button btn_cancel = (Button) dlg3.findViewById(cancelButtonId);
		
		//
		btn_ok.setTag(okTag);
		btn_cancel.setTag(cancelTag);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, dlg3));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg3));
		
		/****************************
		 * 3. Add listeners => OnClick
		 ****************************/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, dlg3));
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, dlg3));
		
		
		return dlg3;
		
	}//public static Dialog dlg_template_okCancel_SecondDialog()

	public static void 
	conf_Import_Patterns
	(Activity actv, Dialog d1, Dialog d2) {
		// TODO Auto-generated method stub
		
		Dialog dlg3 = 
				Methods_dlg.dlg_Tmpl_OkCancel_ThirdDialog(
						actv, 
						R.layout.dlg_tmpl_confirm_simple, 
						R.string.generic_tv_confirm, 
						
						R.id.dlg_tmpl_confirm_simple_btn_ok, 
						R.id.dlg_tmpl_confirm_simple_btn_cancel, 
						
						Tags.DialogTags.DLG_CONF_IMPORT_PATTERNS_OK, 
						Tags.DialogTags.DLG_GENERIC_DISMISS_THIRD_DIALOG, 
						
						d1, d2);
		
		////////////////////////////////

		// view: message

		////////////////////////////////
		TextView tv_Msg = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_message);
		
		tv_Msg.setText(actv.getString(
								R.string.dlg_db_admin_item_op_imp_patterns)
								+ "?");
		
		////////////////////////////////

		// view: item name

		////////////////////////////////
		TextView tv_ItemName = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_item_name);
//		dlg_tmpl_confirm_simple_tv_message
		
		tv_ItemName.setText("From: " + CONS.DB.dbName_Importing);
//		tv_ItemName.setText("From: " + actv.getString(R.string.commons_import_db_name));
		
		////////////////////////////////

		// show

		////////////////////////////////
		dlg3.show();
		
	}//conf_Import_Patterns

	public static void 
	dlg_Admin_Patterns
	(Activity actv, Dialog d1, WordPattern wp) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// vars

		////////////////////////////////
		String msg_Log;
		
		////////////////////////////////

		// dlg

		////////////////////////////////
		Dialog d2 = Methods_dlg.dlg_Template_Cancel_SecondDialog(
						actv, d1,
						R.layout.dlg_tmpl_cancel_lv,
						R.string.dlg_playactv_edit_ai_add_patterns,
						
						R.id.dlg_tmpl_cancel_lv_bt_cancel,
						Tags.DialogTags.DLG_GENERIC_DISMISS_SECOND_DIALOG);
		
		/****************************
		* 2. Prep => List
		****************************/
//		String[] choices = {
//					actv.getString(R.string.dlg_actvmain_lv_delete),
//					};
		
		List<ListItem> list = new ArrayList<ListItem>();
//		List<String> list = new ArrayList<String>();
		
		list.add(new ListItem.Builder()
						.setText(actv.getString(
									R.string.generic_tv_edit))
						.setIconID(R.drawable.menu_icon_admin_32x32)
						.setTextColor_ID(R.color.blue1)
						.build());
		list.add(new ListItem.Builder()
						.setText(actv.getString(
								R.string.generic_tv_delete))
						.setIconID(R.drawable.menu_icon_admin_32x32_brown)
						.setTextColor_ID(R.color.black)
						.build());
		
		/****************************
		* 3. Adapter
		****************************/
		Adp_ListItems adapter = new Adp_ListItems(
							actv,
							R.layout.list_row_simple_iv_1,
							list
		);
		
		/****************************
		* 4. Set adapter
		****************************/
		ListView lv = (ListView) d2.findViewById(R.id.dlg_tmpl_cancel_lv_lv);
		
		lv.setAdapter(adapter);
		
		/****************************
		* 5. Set listener to list
		****************************/
		lv.setTag(Tags.DialogItemTags.ACTV_PLAY_PATTERNS_LONGCLICK_LV);
		
		lv.setOnItemClickListener(new DOI_CL(actv, d1, d2, wp));
		
		/***************************************
		* Modify: Button layout
		***************************************/
		LinearLayout llButton =
					(LinearLayout) d2.findViewById(R.id.dlg_tmpl_cancel_lv_ll_filepath);
//		(LinearLayout) dlg1.findViewById(R.id.actv_imp_ll_filepath);
		
		LinearLayout.LayoutParams params =
				new LinearLayout.LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT);
		
		params.gravity = Gravity.CENTER_HORIZONTAL;
		
		llButton.setLayoutParams(params);

		////////////////////////////////

		// get: screen size

		////////////////////////////////
		//REF size http://stackoverflow.com/questions/19155559/how-to-get-android-device-screen-size answered Oct 3 '13 at 10:00
		DisplayMetrics displayMetrics = actv.getResources()
                			.getDisplayMetrics();
		
		int w = displayMetrics.widthPixels;
		
		int dialog_Width = w * CONS.Admin.ratio_Dialog_to_Screen_W / 100;
		
		////////////////////////////////

		// linear layot: main

		////////////////////////////////
		LinearLayout ll_Main = 
					(LinearLayout) d2.findViewById(R.id.dlg_tmpl_cancel_lv_ll_main);
		
		//REF parent layout http://stackoverflow.com/questions/4631966/set-relativelayout-layout-params-programmatically-throws-classcastexception answered Jan 8 '11 at 5:42
//		08-21 11:30:45.434: E/AndroidRuntime(20722): java.lang.ClassCastException: android.widget.LinearLayout$LayoutParams
//		08-21 11:30:45.434: E/AndroidRuntime(20722): 	at android.widget.FrameLayout.onLayout(FrameLayout.java:293)
//		08-21 11:30:45.434: E/AndroidRuntime(20722): 	at android.view.View.layout(View.java:7184)

		FrameLayout.LayoutParams params2 =
				new FrameLayout.LayoutParams(
						dialog_Width,
						LayoutParams.WRAP_CONTENT);
		
		ll_Main.setLayoutParams(params2);
		
		/****************************
		* 6. Show dialog
		****************************/
		d2.show();

		
	}//dlg_Admin_Patterns

	public static void 
	dlg_Admin_Patterns_PlayActv_Option
	(Activity actv) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// vars
		
		////////////////////////////////
		String msg_Log;
		
		////////////////////////////////
		
		// dlg
		
		////////////////////////////////
		Dialog d1 = Methods_dlg.dlg_Template_Cancel(
				actv,
				R.layout.dlg_tmpl_cancel_lv,
				R.string.dlg_playactv_edit_ai_add_patterns,
				
				R.id.dlg_tmpl_cancel_lv_bt_cancel,
				Tags.DialogTags.GENERIC_DISMISS);
		
		/****************************
		 * 2. Prep => List
		 ****************************/
		List<ListItem> list = new ArrayList<ListItem>();
//		List<String> list = new ArrayList<String>();
		
		list.add(new ListItem.Builder()
				.setText(actv.getString(
						R.string.generic_tv_register))
						.setIconID(R.drawable.menu_icon_admin_32x32)
						.setTextColor_ID(R.color.blue1)
						.build());
		
		list.add(new ListItem.Builder()
		.setText(actv.getString(
				R.string.generic_tv_edit))
				.setIconID(R.drawable.menu_icon_admin_32x32_brown)
				.setTextColor_ID(R.color.black)
				.build());
		
		list.add(new ListItem.Builder()
				.setText(actv.getString(
						R.string.generic_tv_delete))
						.setIconID(R.drawable.menu_icon_admin_32x32_purple)
						.setTextColor_ID(R.color.purple4)
						.build());
		
		/****************************
		 * 3. Adapter
		 ****************************/
		Adp_ListItems adapter = new Adp_ListItems(
				actv,
				R.layout.list_row_simple_iv_1,
				list
				);
		
		/****************************
		 * 4. Set adapter
		 ****************************/
		ListView lv = (ListView) d1.findViewById(R.id.dlg_tmpl_cancel_lv_lv);
		
		lv.setAdapter(adapter);
		
		/****************************
		 * 5. Set listener to list
		 ****************************/
		lv.setTag(Tags.DialogItemTags.ACTV_PLAY_OPTION_ADMIN_PATTERNS);
		
		lv.setOnItemClickListener(new DOI_CL(actv, d1));
		
		/***************************************
		 * Modify: Button layout
		 ***************************************/
		LinearLayout llButton =
				(LinearLayout) d1.findViewById(R.id.dlg_tmpl_cancel_lv_ll_filepath);
//		(LinearLayout) dlg1.findViewById(R.id.actv_imp_ll_filepath);
		
		LinearLayout.LayoutParams params =
				new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
		
		params.gravity = Gravity.CENTER_HORIZONTAL;
		
		llButton.setLayoutParams(params);
		
		////////////////////////////////
		
		// get: screen size
		
		////////////////////////////////
		//REF size http://stackoverflow.com/questions/19155559/how-to-get-android-device-screen-size answered Oct 3 '13 at 10:00
		DisplayMetrics displayMetrics = actv.getResources()
				.getDisplayMetrics();
		
		int w = displayMetrics.widthPixels;
		
		int dialog_Width = w * CONS.Admin.ratio_Dialog_to_Screen_W / 100;
		
		////////////////////////////////
		
		// linear layot: main
		
		////////////////////////////////
		LinearLayout ll_Main = 
				(LinearLayout) d1.findViewById(R.id.dlg_tmpl_cancel_lv_ll_main);
		
		//REF parent layout http://stackoverflow.com/questions/4631966/set-relativelayout-layout-params-programmatically-throws-classcastexception answered Jan 8 '11 at 5:42
//		08-21 11:30:45.434: E/AndroidRuntime(20722): java.lang.ClassCastException: android.widget.LinearLayout$LayoutParams
//		08-21 11:30:45.434: E/AndroidRuntime(20722): 	at android.widget.FrameLayout.onLayout(FrameLayout.java:293)
//		08-21 11:30:45.434: E/AndroidRuntime(20722): 	at android.view.View.layout(View.java:7184)
		
		FrameLayout.LayoutParams params2 =
				new FrameLayout.LayoutParams(
						dialog_Width,
						LayoutParams.WRAP_CONTENT);
		
		ll_Main.setLayoutParams(params2);
		
		/****************************
		 * 6. Show dialog
		 ****************************/
		d1.show();
		
		
	}//dlg_Admin_Patterns_PlayActv_Option
	
	public static void 
	conf_Delete_Pattern
	(Activity actv, 
		Dialog d1, Dialog d2, WordPattern wp) {
		// TODO Auto-generated method stub
		
		Dialog d3 = 
				Methods_dlg.dlg_template_okCancel_ThirdDialog(
						actv, d1, d2, wp,
						R.layout.dlg_tmpl_confirm_simple, 
						R.string.generic_tv_confirm, 
						
						R.id.dlg_tmpl_confirm_simple_btn_ok, 
						Tags.DialogTags.DLG_CONF_DELETE_PATTERN_OK, 
						
						R.id.dlg_tmpl_confirm_simple_btn_cancel, 
						Tags.DialogTags.DLG_GENERIC_DISMISS_THIRD_DIALOG
				);
		
		////////////////////////////////

		// view: message

		////////////////////////////////
		TextView tv_Msg = 
				(TextView) d3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_message);
		
		tv_Msg.setText(actv.getString(
								R.string.generic_tv_delete)
								+ "?");
		
		////////////////////////////////

		// view: item name

		////////////////////////////////
		TextView tv_ItemName = 
				(TextView) d3.findViewById(R.id.dlg_tmpl_confirm_simple_tv_item_name);
//		dlg_tmpl_confirm_simple_tv_message
		
		tv_ItemName.setText(wp.getWord());
		
		////////////////////////////////

		// show

		////////////////////////////////
		d3.show();		
		
	}//conf_Delete_Pattern

	public static void 
	dlg_Register_Patterns
	(Activity actv, Dialog d1) {
		// TODO Auto-generated method stub
		Dialog d2 = 
				Methods_dlg.dlg_Template_OkCancel_SecondDialog(
						actv, d1,
						R.layout.dlg_tmpl_ok_cancel, 
						R.string.generic_tv_register, 
						
						R.id.dlg_tmpl_ok_cancel_bt_ok, 
						Tags.DialogTags.DLG_REGISTER_PATTERNS_OK, 
						
						R.id.dlg_tmpl_ok_cancel_bt_cancel, 
						Tags.DialogTags.DLG_GENERIC_DISMISS_SECOND_DIALOG
				);
		
		////////////////////////////////

		// show

		////////////////////////////////
		d2.show();
		
		
	}//register_Patterns

	private static Dialog 
	dlg_Template_OkCancel_SecondDialog
	(Activity actv, Dialog d1, 
		int id_Layout, int id_String_for_Title,
		
		int id_Button_Ok, DialogTags tag_Button_Ok,
		int id_Button_Cancel, DialogTags tag_Button_Cancel) {
		// TODO Auto-generated method stub
		
		/****************************
		* Steps
		* 1. Set up
		* 2. Add listeners => OnTouch
		* 3. Add listeners => OnClick
		****************************/
		
		// 
		Dialog d2 = new Dialog(actv);
		
		//
		d2.setContentView(id_Layout);
		
		// Title
		d2.setTitle(id_String_for_Title);
		
		/****************************
		* 2. Add listeners => OnTouch
		****************************/
		//
		Button btn_ok = (Button) d2.findViewById(id_Button_Ok);
		Button btn_cancel = (Button) d2.findViewById(id_Button_Cancel);
		
		//
		btn_ok.setTag(tag_Button_Ok);
		btn_cancel.setTag(tag_Button_Cancel);
		
		//
		btn_ok.setOnTouchListener(new DB_OTL(actv, d1, d2));
		btn_cancel.setOnTouchListener(new DB_OTL(actv, d1, d2));
		
		/****************************
		* 3. Add listeners => OnClick
		****************************/
		//
		btn_ok.setOnClickListener(new DB_OCL(actv, d1, d2));
		btn_cancel.setOnClickListener(new DB_OCL(actv, d1, d2));
		
		//
		//dlg2.show();
		
		return d2;
		
	}//dlg_Template_OkCancel_SecondDialog

	public static void
	dlg_ShowMessage_ThirdDialog
	(Activity actv, 
		String message, Dialog dlg1, Dialog dlg2) {
		
		Dialog dlg3 = Methods_dlg.dlg_Template_Cancel_ThirdDialog(
				actv, dlg1, dlg2,
				R.layout.dlg_tmpl_toast_ok, 
				R.string.generic_tv_confirm, 
				
				R.id.dlg_tmpl_toast_ok_bt_cancel, 
				Tags.DialogTags.DLG_GENERIC_DISMISS_THIRD_DIALOG);
		
		TextView tv_Message = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_toast_ok_tv_message);
		
		tv_Message.setText(message);
		
		dlg3.show();
		
	}

	public static void
	dlg_ShowMessage_ThirdDialog
	(Activity actv, 
			String message, Dialog dlg1, Dialog dlg2, int colorID) {
		
		Dialog dlg3 = Methods_dlg.dlg_Template_Cancel_ThirdDialog(
				actv, dlg1, dlg2,
				R.layout.dlg_tmpl_toast_ok, 
				R.string.generic_tv_confirm, 
				
				R.id.dlg_tmpl_toast_ok_bt_cancel, 
				Tags.DialogTags.DLG_GENERIC_DISMISS_THIRD_DIALOG);
		
		TextView tv_Message = 
				(TextView) dlg3.findViewById(R.id.dlg_tmpl_toast_ok_tv_message);
		
		tv_Message.setTextColor(colorID);
		
		tv_Message.setText(message);
		
		dlg3.show();
		
	}
	
	public static Dialog 
	dlg_Template_Cancel_ThirdDialog
	(Activity actv, Dialog dlg1, Dialog dlg2,
			int layoutId, int titleStringId,
			int cancelButtonId, Tags.DialogTags cancelTag) {
		/****************************
		 * Steps
		 * 1. Set up
		 * 2. Add listeners => OnTouch
		 * 3. Add listeners => OnClick
		 ****************************/
		
		// 
		Dialog dlg3 = new Dialog(actv);
		
		//
		dlg3.setContentView(layoutId);
		
		// Title
		dlg3.setTitle(titleStringId);
		
		/****************************
		 * 2. Add listeners => OnTouch
		 ****************************/
		//
		Button btn_cancel = (Button) dlg3.findViewById(cancelButtonId);
		
		//
		btn_cancel.setTag(cancelTag);
		
		//
		btn_cancel.setOnTouchListener(new DB_OTL(actv, dlg1, dlg2, dlg3));
		
		/****************************
		 * 3. Add listeners => OnClick
		 ****************************/
		//
		btn_cancel.setOnClickListener(new DB_OCL(actv, dlg1, dlg2, dlg3));
		
		//
		//dlg.show();
		
		return dlg3;
		
	}//public static Dialog dlg_template_okCancel()

}//public class Methods_dialog

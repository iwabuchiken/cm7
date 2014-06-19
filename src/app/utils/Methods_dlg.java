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

import org.apache.commons.lang.StringUtils;

import cm7.main.R;
import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import app.items.AI;
import app.items.BM;
import app.listeners.dialog.DB_OCL;
import app.listeners.dialog.DB_OTL;
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
									Tags.DialogTags.DLG_GENERIC_DISMISS);
		
		/****************************
		 * 2. Prep => List
			****************************/
		String[] choices = {
				actv.getString(R.string.dlg_db_admin_item_exec_sql),
				
				actv.getString(R.string.dlg_db_admin_item_backup_db),
				actv.getString(R.string.dlg_db_admin_item_refresh_db),
				
				actv.getString(R.string.dlg_db_admin_item_drop_table_cm7),
				actv.getString(R.string.dlg_db_admin_item_create_table_cm7),
				
				actv.getString(R.string.dlg_db_admin_item_drop_table_refresh_history),
				actv.getString(
						R.string.dlg_db_admin_item_create_table_refresh_history),
				
				actv.getString(R.string.dlg_db_admin_item_drop_table_bm),
				actv.getString(R.string.dlg_db_admin_item_create_table_bm),
				
				actv.getString(R.string.dlg_db_admin_item_drop_table_memo_patterns),
				actv.getString(R.string.dlg_db_admin_item_create_table_memo_patterns),
//					actv.getString(R.string.dlg_db_admin_item_refresh_db)
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
//				R.layout.dlg_db_admin,
//				android.R.layout.simple_list_item_1,
				R.layout.list_row_simple_1,
				list
				);

		/****************************
		 * 4. Set adapter
			****************************/
		ListView lv = (ListView) dlg.findViewById(R.id.dlg_tmpl_list_cancel_lv);
//		ListView lv = (ListView) dlg.findViewById(R.id.dlg_db_admin_lv);
		
		lv.setAdapter(adapter);
		
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
		btn_cancel.setTag(Tags.DialogTags.dlg_generic_dismiss_second_dialog);
		
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
				Tags.DialogTags.DLG_GENERIC_DISMISS,
				
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
		TextView tv_Title = 
				(TextView) dlg.findViewById(R.id.dlg_edit_ai_title_et_content);
		
		tv_Title.setText(currentTitle);
		
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
		btn_Cancel.setTag(Tags.DialogTags.DLG_GENERIC_DISMISS);
		
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
		dlg = Methods_dlg.dlg_EditTitle_GridView(actv, dlg, ai);
		
		
		dlg.show();
		
	}//dlg_EditTitle

	private static Dialog
	dlg_EditTitle_GridView
	(Activity actv, Dialog dlg, AI ai) {
		// TODO Auto-generated method stub
		
		GridView gv = (GridView) dlg.findViewById(R.id.dlg_edit_ai_title_gv);
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
		
		String sql = "SELECT * FROM " + tableName + " ORDER BY word ASC";
		
		Cursor c = rdb.rawQuery(sql, null);

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
		
		List<String> patternList = new ArrayList<String>();
		
		if (c.getCount() > 0) {
			
			for (int i = 0; i < c.getCount(); i++) {
				
				patternList.add(c.getString(3));	// "word"
				
				c.moveToNext();
				
			}//for (int i = 0; i < patternList.size(); i++)
			
		} else {//if (c.getCount() > 0)
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "!c.getCount() > 0");
			
		}//if (c.getCount() > 0)
		
		
		Collections.sort(patternList);

		////////////////////////////////

		// Build: adapter

		////////////////////////////////
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
										actv,
										R.layout.add_memo_grid_view,
										patternList
										);
		
		gv.setAdapter(adapter);

		gv.setTag(Tags.DialogItemTags.dlg_add_memos_gv);
		
		gv.setOnItemClickListener(new DOI_CL(actv, dlg));
		
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
		btn_cancel.setTag(Tags.DialogTags.dlg_generic_dismiss_second_dialog);
		
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
				Tags.DialogTags.DLG_GENERIC_DISMISS,
				
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
	
}//public class Methods_dialog

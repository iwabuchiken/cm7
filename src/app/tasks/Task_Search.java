package app.tasks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import cm7.main.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import app.main.ALActv;
import app.utils.CONS;
import app.utils.DBUtils;
import app.utils.Methods;
import app.utils.Methods_dlg;

public class Task_Search extends AsyncTask<String[], Integer, Integer>{
//	public class Task_Search extends AsyncTask<String[], Integer, String>{

	//
	Activity actv;
	
	//
	String[] search_words;

	//
	static long[] long_searchedItems;
	
	static String[] string_searchedItems_table_names;
	
	int search_mode;

	private int search_Type;
	
	public Task_Search(Activity actv, String[] search_words) {
		
		this.actv = actv;
		this.search_words = search_words;
		
	}//public SearchTask(Activity actv, String[] search_words)


	public Task_Search(Activity actv) {
		
		this.actv = actv;
		
		// Log
		Log.d("SearchTask.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Starts => SearchTask(Activity actv)");
		
	}//public SearchTask(Activity actv)


	public Task_Search(Activity actv, int search_mode) {
		
		this.actv = actv;
		
		this.search_mode = search_mode;
		
		string_searchedItems_table_names = null;

//		// Log
//		Log.d("SearchTask.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Starts => SearchTask(Activity actv, int search_mode)");

	}//public SearchTask(Activity actv2, int search_mode)
	


	public Task_Search
	(Activity actv, int search_mode, int search_Type) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		
		this.search_mode = search_mode;
		this.search_Type		= search_Type;
		
		string_searchedItems_table_names = null;

	}


	@Override
	protected Integer doInBackground(String[]... sw) {
		
		// Log
		Log.d("SearchTask.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "sw.length=" + sw.length);
		
		if(search_mode == 0) {
			
			// Log
			Log.d("SearchTask.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Calling => doInBackground_specific_table(sw)");
			
			return this.doInBackground_specific_table(sw);
			
		} else {

			// Log
			Log.d("SearchTask.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Calling => doInBackground_all_table(sw)");

			return this.doInBackground_all_table(sw);
			
		}//if(search_mode == 0)
		
	}//protected String doInBackground(String[]... sw)

	private Integer 
	doInBackground_all_table
	(String[][] sw) {
		/*----------------------------
		 * Steps
		 * 1. Get table names list
		 * 1-2. Get => Table names list
		 * 
		 * 2. Construct data			##Add ThumbnailItem to tiLIst
		 * 3. Close db
		 * 4. Set up intent
		 * 5. Return
			----------------------------*/
		
//		/*----------------------------
//		 * 1. Get table names list
//			----------------------------*/
//		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
//		
//		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		/*********************************
		 * 1-2. Get => Table names list
		 *********************************/
//		List<String> table_names = Methods.get_table_list(actv);
//		List<String> table_names = Methods.get_table_list(actv, "IFM9");
		
//		List<String> table_names = Methods.get_table_list(actv, "IFM10%");
		
		/*----------------------------
		 * 2. Construct data
		 * 		1. Table name
		 * 		1-2. Declare => List<Long> searchedItems
		 * 		2. Exec query
		 * 		3. Search
		 * 		4. List<Long> searchedItems => file id
		 * 		
		 * 		5. List<Long> searchedItems => to array
		 * 
		 * 		6. List<String> string_searchedItems_table_names => to array
		 * 
			----------------------------*/
//		String targetTable = sw[1][0];
		
		String[] keywords = sw[0];
		
		CONS.ALActv.searchedItems = _Search__AllTable(keywords);
//		List<Long> searchedItems = this._Search__AllTable(keywords);
//		List<Long> searchedItems = new ArrayList<Long>();
		
//		List<String> searchedItems_table_names = new ArrayList<String>();
		
//		/*----------------------------
//		 * 2.2. Exec query
//			----------------------------*/
//		for (String targetTable : table_names) {
//			
//			String sql = "SELECT * FROM " + targetTable;
//			
//			// Log
//			Log.d("SearchTask.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "targetTable: " + targetTable);
//			
//			
//			Cursor c = rdb.rawQuery(sql, null);
//			
//			actv.startManagingCursor(c);
//			
//			c.moveToFirst();
//	
//			
//			/*----------------------------
//			 * 2.3. Search
//				----------------------------*/
//			doInBackground_all_table_search(
//						c, sw[0], 
//						searchedItems, searchedItems_table_names,
//						targetTable);
//			
//		}//for (String targetTable : table_names)
		
		/*********************************
		 * 2.5. List<Long> searchedItems => to array
		 * 2.6. String[] searchedItems_table_names => to array
		 *********************************/
		int len;
		
		if (CONS.ALActv.searchedItems == null) {
			
			len = 0;
			
		} else {

			len = CONS.ALActv.searchedItems.size();
			
		}
		
		// Log
		String msg_Log = "searchedItems.size() => " + len;
		Log.d("SearchTask.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
////		long[] long_searchedItems = new long[len];
//		long_searchedItems = new long[len];
//		
//		string_searchedItems_table_names = new String[len];
//		
//		for (int i = 0; i < len; i++) {
//			
//			long_searchedItems[i] = searchedItems.get(i);
//			
//			string_searchedItems_table_names[i] = searchedItems_table_names.get(i);
//			
//		}//for (int i = 0; i < len; i++)
		
//		/*----------------------------
//		 * 3. Close db
//			----------------------------*/
//		rdb.close();
		
		/*----------------------------
		 * 5. Return
			----------------------------*/
		return len;
		
	}//private String doInBackground_all_table(String[][] sw)


	private Integer 
	doInBackground_specific_table
	(String[][] sw) {
		/*----------------------------
		 * Steps
		 * 1. Get table names list
		 * 2. Construct data			##Add ThumbnailItem to tiLIst
		 * 3. Close db
		 * 4. Set up intent
		 * 5. Return
			----------------------------*/
		/*----------------------------
		 * 1. Get table names list
			----------------------------*/
//		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
//		
//		SQLiteDatabase rdb = dbu.getReadableDatabase();

		
		String targetTable = sw[1][0];
		
		// Log
		Log.d("SearchTask.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "targetTable=" + targetTable);
		
		////////////////////////////////

		// search

		////////////////////////////////
		CONS.ALActv.searchedItems = _Search__SpecificTable(targetTable, sw[0]);
//		List<Long> searchedItems = _Search__SpecificTable(targetTable, sw[0]);
		
		/*----------------------------
		 * 2.5. List<Long> searchedItems => to array
			----------------------------*/
//		int len = CONS.ALActv.searchedItems.size();
		int len;
		
		if (CONS.ALActv.searchedItems == null) {
			
			len = 0;
			
		} else {

			len = CONS.ALActv.searchedItems.size();
			
		}
		
		// Log
		String msg_Log = "searchedItems.size() => " + len;
		Log.d("SearchTask.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		
////		long[] long_searchedItems = new long[len];
//		long_searchedItems = new long[len];
//		
//		for (int i = 0; i < len; i++) {
//			
//			long_searchedItems[i] = searchedItems.get(i);
//			
//		}//for (int i = 0; i < len; i++)
		
//		/*----------------------------
//		 * 3. Close db
//			----------------------------*/
//		rdb.close();
		
//		/*----------------------------
//		 * 4. Set up intent
//			----------------------------*/
//		// Log
//		Log.d("SearchTask.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "long_searchedItems.length => " + long_searchedItems.length);
//		
//		Log.d("SearchTask.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "long_searchedItems[0] => " + long_searchedItems[0]);
//		
//		Intent i = new Intent();
//		
//		i.setClass(actv, ThumbnailActivity.class);
//		
//		i.putExtra("long_searchedItems", long_searchedItems);
//		
//		actv.startActivity(i);
		
		/*----------------------------
		 * 5. Return
			----------------------------*/
		return len;
		
	}//private String doInBackground_specific_table(String[][] sw)
	

	/******************************
		@return null => 1. Query returned null<br>
						2. Query returned no entry<br>
				successful => returns a list of TI db-ids
	 ******************************/
	private List<Long> 
	_Search__SpecificTable
	(String targetTable, String[] keywords) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// setup: db

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		String msg_Log;
		
		////////////////////////////////

		// vars

		////////////////////////////////
		List<Long> searchedItems = new ArrayList<Long>();
		
		long tmp_Long;	// put the value of c.getLong(3)
		
//		Set<Long> searched_ITems_Set = new TreeSet<Long>();
//		
//		searched_ITems_Set.to
		
		////////////////////////////////

		// Exec query

		////////////////////////////////
//		android.provider.BaseColumns._ID,	// 0
//		"created_at", "modified_at",		// 1, 2
//		"file_name", "file_path",			// 3, 4
//		"title", "memo",					// 5, 6
//		"last_played_at",					// 7
//		"table_name",						// 8
//		"length",							// 9
//		"audio_created_at"					// 10
		
		Cursor c = null;
		
		String where = CONS.DB.col_names_CM7_full[8] + " = ?";
		String[] args = new String[]{
				
						targetTable
				
				};
		
		try {
			
			c = rdb.query(
					
					CONS.DB.tname_CM7,			// 1
					CONS.DB.col_names_CM7_full,	// 2
					where, args,		// 3,4
					null, null,		// 5,6
					null,			// 7
					null);
			
		} catch (Exception e) {

			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", e.toString());
			
			rdb.close();
			
			return null;
			
		}//try
		
		/***************************************
		 * Validate
		 * 	Cursor => Null?
		 * 	Entry => 0?
		 ***************************************/
		if (c == null) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Query failed");
			
			rdb.close();
			
			return null;
			
		} else if (c.getCount() < 1) {//if (c == null)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "No entry in the table");
			
			rdb.close();
			
			return null;
			
		}//if (c == null)
		
		// Log
		msg_Log = "c.getCount() => " + c.getCount();
		Log.d("Task_Search.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		// Log
		msg_Log = "this.search_Type => " + this.search_Type;
		Log.d("Task_Search.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		////////////////////////////////

		// Search

		////////////////////////////////
//		android.provider.BaseColumns._ID,	// 0
//		"created_at", "modified_at",		// 1, 2
//		"file_name", "file_path",			// 3, 4
//		"title", "memo",					// 5, 6
//		"last_played_at",					// 7
//		"table_name",						// 8
//		"length",							// 9
//		"audio_created_at"					// 10		
		
		// Move cursor
		c.moveToFirst();
		
		int i;	// counter for cursor iteration
		int j;	// counter for keywords iteration
		
		String target = null;
		
		for (i = 0; i < c.getCount(); i++) {
//			for (int i = 0; i < c.getCount(); i++) {
			
//			String memo = c.getString(6);
			if (this.search_Type == 1) {
				
				target = c.getString(3);	// file_name
				
			} else {

				target = c.getString(5);	// title
				
			}
			
			if (target == null) {

				c.moveToNext();
				
				continue;
				
			}//if (memo == null)
			
			for(j = 0; j < keywords.length; j ++) {
				
				if (target.matches(".*" + keywords[j] + ".*")) {
//					if (memo.matches(".*" + string + ".*")) {
					
					// Log
					Log.d("SearchTask.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", "memo => " + target);
					
				
					////////////////////////////////

					// List<Long> searchedItems => db id

					////////////////////////////////
//					searchedItems.add(c.getLong(1));
					
					tmp_Long = c.getLong(0);
//					tmp_Long = c.getLong(3);
					
					if (!searchedItems.contains(tmp_Long)) {
						
						searchedItems.add(tmp_Long);
//						searchedItems.add(c.getLong(3));
					}
					
					
					break;
					
				}//if (memo.matches(".*" + ))
				
			}//for(j = 0; j < keywords.length; j ++)
			
			c.moveToNext();
			
		}//for (int i = 0; i < c.getCount(); i++)

		////////////////////////////////

		// close

		////////////////////////////////
		rdb.close();
		
		////////////////////////////////

		// return

		////////////////////////////////
		return searchedItems;
		
	}//_Search__SpecificTable

	/******************************
		@return null => 1. Query returned null<br>
						2. Query returned no entry<br>
	 ******************************/
	private List<Long> 
	_Search__AllTable
	(String[] keywords) {
		// TODO Auto-generated method stub
		////////////////////////////////
		
		// setup: db
		
		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		////////////////////////////////
		
		// vars
		
		////////////////////////////////
		List<Long> searchedItems = new ArrayList<Long>();
		
		long tmp_Long;	// put the value of c.getLong(3)
		
//		Set<Long> searched_ITems_Set = new TreeSet<Long>();
//		
//		searched_ITems_Set.to
		
		////////////////////////////////
		
		// Exec query
		
		////////////////////////////////
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"file_id", "file_path", "file_name",	// 3,4,5
//		"date_added", "date_modified",			// 6,7
//		"memos", "tags",						// 8,9
//		"last_viewed_at",						// 10
//		"table_name"							// 11
		
		Cursor c = null;
		
//		String where = CONS.DB.col_names_IFM11[11] + " = ?";
//		String[] args = new String[]{
//				
//				targetTable
//				
//		};
		
		try {
			
			c = rdb.query(
					
					CONS.DB.tname_CM7,			// 1
					CONS.DB.col_names_CM7_full,	// 2
					null, null,		// 3,4
//					where, args,		// 3,4
					null, null,		// 5,6
					null,			// 7
					null);
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", e.toString());
			
			rdb.close();
			
			return null;
			
		}//try
		
		/***************************************
		 * Validate
		 * 	Cursor => Null?
		 * 	Entry => 0?
		 ***************************************/
		if (c == null) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Query failed");
			
			rdb.close();
			
			return null;
			
		} else if (c.getCount() < 1) {//if (c == null)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "No entry in the table");
			
			rdb.close();
			
			return null;
			
		}//if (c == null)
		
//		String sql = "SELECT * FROM " + targetTable;
//		
//		// Log
//		Log.d("SearchTask.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "targetTable: " + targetTable);
//		
//		
//		Cursor c = rdb.rawQuery(sql, null);
//		
		
		////////////////////////////////
		
		// Search
		
		////////////////////////////////
		// Move cursor
		c.moveToFirst();
		
		int i;	// counter for cursor iteration
		int j;	// counter for keywords iteration
		
		for (i = 0; i < c.getCount(); i++) {
//			for (int i = 0; i < c.getCount(); i++) {
			
//			String memo = c.getString(6);
			String memo = c.getString(8);	// memos
			
			if (memo == null) {
				
				c.moveToNext();
				
				continue;
				
			}//if (memo == null)
			
			for(j = 0; j < keywords.length; j ++) {
				
				if (memo.matches(".*" + keywords[j] + ".*")) {
//					if (memo.matches(".*" + string + ".*")) {
					
					// Log
					Log.d("SearchTask.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", "memo => " + memo);
					
					
					/*----------------------------
					 * 2.4. List<Long> searchedItems => file id
						----------------------------*/
//					searchedItems.add(c.getLong(1));
					
					tmp_Long = c.getLong(0);
					
					if (!searchedItems.contains(tmp_Long)) {
						
						searchedItems.add(tmp_Long);
//						searchedItems.add(c.getLong(3));
					}
					
					
					break;
					
				}//if (memo.matches(".*" + ))
				
			}//for(j = 0; j < keywords.length; j ++)
			
			c.moveToNext();
			
		}//for (int i = 0; i < c.getCount(); i++)
		
		////////////////////////////////
		
		// close
		
		////////////////////////////////
		rdb.close();
		
		////////////////////////////////
		
		// return
		
		////////////////////////////////
		return searchedItems;
		
	}//_Search__AllTable
	

	@Override
	protected void 
	onPostExecute
	(Integer result) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onPostExecute(result);

		////////////////////////////////

		// validate: any entry?

		////////////////////////////////
		if (result < 1) {
			
			String msg = "No entry";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.gold2);
			
			return;
			
		}
		
		// Log
		String msg_Log = "post executing...";
		Log.d("Task_Search.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// Set: searchDone => true

		////////////////////////////////
		CONS.ALActv.searchDone = true;
		
		////////////////////////////////

		// set: pref

		////////////////////////////////
		boolean res = Methods.set_Pref_String(
						actv, 
						CONS.Pref.pname_MainActv, 
						CONS.Pref.pkey_TNActv__ListType, 
						CONS.Enums.ListType.SEARCH.toString());
		
		////////////////////////////////

		// get pref

		////////////////////////////////
		String currentPath = Methods.get_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				null);

		if (currentPath == null) {
			
			currentPath = StringUtils.join(
							new String[]{
									CONS.Paths.dpath_Storage_Sdcard, 
									CONS.Paths.dname_Base},
							File.separator);
			
			Methods.set_Pref_String(
					actv, 
					CONS.Pref.pname_MainActv, 
					CONS.Pref.pkey_CurrentPath, 
					currentPath);
			
		}
		
		////////////////////////////////

		// clear pref: current position

		////////////////////////////////
    	int pref_CurrentPosition = 
    			Methods.get_Pref_Int(
    					actv, 
    					CONS.Pref.pname_ALActv, 
    					CONS.Pref.pkey_CurrentPosition_ALActv,
    					CONS.Pref.dflt_IntExtra_value);
    	
    	if (pref_CurrentPosition != CONS.Pref.dflt_IntExtra_value) {
			
    		Methods.set_Pref_Int(
    				actv,
    				CONS.Pref.pname_ALActv,
    				CONS.Pref.pkey_CurrentPosition_ALActv,
    				CONS.Pref.dflt_IntExtra_value);
    		
		}

		////////////////////////////////

		// start: TNActv

		////////////////////////////////
		Intent i = new Intent();
		
		i.setClass(actv, ALActv.class);

		i.putExtra(CONS.Intent.iKey_CurrentPath_MainActv, currentPath);
		
		i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		
		actv.startActivity(i);
		
//		// debug
////		Toast.makeText(actv, result, 2000).show();
//		// Log
//		Log.d("SearchTask.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", result);
//		
//
//		/*----------------------------
//		 * 1. Set up intent
//			----------------------------*/
//		// Log
//		Log.d("SearchTask.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "long_searchedItems.length => " + long_searchedItems.length);
//		
//		if(long_searchedItems.length > 0) {
//			
//			Log.d("SearchTask.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "long_searchedItems[0] => " + long_searchedItems[0]);
//			
//			Intent i = new Intent();
//			
//			i.setClass(actv, TNActv.class);
//			
//			i.putExtra("long_searchedItems", long_searchedItems);
//			
//			if (string_searchedItems_table_names != null &&
//					string_searchedItems_table_names.length > 0) {	
//				
//				i.putExtra(
////						"string_searchedItems_table_names",
//						MainActv.intent_label_searchedItems_table_names,
//						string_searchedItems_table_names);
//				
//			}//if (variable == condition)
////				i.putExtra("string_searchedItems_table_names", string_searchedItems_table_names);
//
//			// Log
//			if (string_searchedItems_table_names != null) {
//				
//				Log.d("SearchTask.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", 
//						"string_searchedItems_table_names.length="
//							+ string_searchedItems_table_names.length);
//				
//			} else {//if (string_searchedItems_table_names != null)
//
//				Log.d("SearchTask.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", 
//						"string_searchedItems_table_names => Null");
//
//			}//if (string_searchedItems_table_names != null)
//			
////			Log.d("SearchTask.java" + "["
////					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////					+ "]", 
////					"string_searchedItems_table_names.length="
////						+ string_searchedItems_table_names.length);
//			
//			/*----------------------------
//			 * 2. Start activity
//				----------------------------*/
//			actv.startActivity(i);
//			
//		} else {
//			
//			// debug
//			Toast.makeText(actv, "������܂���ł���", 2000).show();
//		}

	}//protected void onPostExecute(String result)
	
	
}//public class SearchTask

package app.utils;



import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
//import android.view
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import app.items.AI;
import app.items.BM;
import app.items.Refresh;

/****************************************
 * Copy & pasted from => C:\WORKS\WORKSPACES_ANDROID\ShoppingList\src\shoppinglist\main\DBUtils.java
 ****************************************/
public class DBUtils extends SQLiteOpenHelper{

	/*****************************************************************
	 * Class fields
	 *****************************************************************/
	 // DB name
	String dbName = null;
//	static String dbName = null;
	
	// Activity
	Activity activity;
	
	//
	Context context;

	/*********************************
	 * DB
	 *********************************/
	// Database
	SQLiteDatabase db = null;

	/*****************************************************************
	 * Constructor
	 *****************************************************************/
	public DBUtils(Context context, String dbName) {
		super(context, dbName, null, 1);
		
		// Initialize activity
		this.activity = (Activity) context;
		
		this.context = context;
		
		this.dbName = dbName;
		
	}//public DBUtils(Context context)

	/*******************************************************
	 * Methods
	 *******************************************************/
	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}//public void onCreate(SQLiteDatabase db)

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}

	/****************************************
	 * createTable_generic()
	 * 
	 * <Caller> 
	 * 1. 
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public boolean createTable
	(SQLiteDatabase db, String tableName, 
			String[] columns, String[] types) {
		/*----------------------------
		 * Steps
		 * 1. Table exists?
		 * 2. Build sql
		 * 3. Exec sql
			----------------------------*/
		
		//
//		if (!tableExists(db, tableName)) {
		if (tableExists(db, tableName)) {
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists => " + tableName);
			
			return false;
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		/*----------------------------
		 * 2. Build sql
			----------------------------*/
		//
		StringBuilder sb = new StringBuilder();
		
		sb.append("CREATE TABLE " + tableName + " (");
		sb.append(android.provider.BaseColumns._ID +
							" INTEGER PRIMARY KEY AUTOINCREMENT, ");
		
		// created_at, modified_at
		sb.append("created_at INTEGER, modified_at INTEGER, ");
		
		int i = 0;
		for (i = 0; i < columns.length - 1; i++) {
			sb.append(columns[i] + " " + types[i] + ", ");
		}//for (int i = 0; i < columns.length - 1; i++)
		
		sb.append(columns[i] + " " + types[i]);
		
		sb.append(");");
		
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "sql => " + sb.toString());
		
		/*----------------------------
		 * 3. Exec sql
			----------------------------*/
		//
		try {
//			db.execSQL(sql);
			db.execSQL(sb.toString());
			
			// Log
			Log.d(this.getClass().getName() + 
					"["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table created => " + tableName);
			
			
			return true;
		} catch (SQLException e) {
			// Log
			Log.e(this.getClass().getName() + 
					"[" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
					"Exception => " + e.toString());
			
			return false;
		}//try
		
	}//public boolean createTable(SQLiteDatabase db, String tableName)

	/******************************
		createTable()
		
		@param columns, types => use non-full version
		@return true => Table created or exists
	 ******************************/
	public boolean createTable
	(Activity actv, String tableName, String[] columns, String[] types)
	{
		/*----------------------------
		 * Steps
		 * 1. Table exists?
		 * 2. Build sql
		 * 3. Exec sql
			----------------------------*/
//		DBUtils dbu = new DBUtils(actv, dbName);
		
		//
		SQLiteDatabase wdb = this.getWritableDatabase();
//		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		//
		//if (!tableExists(db, tableName)) {
		if (tableExists(wdb, tableName)) {
			// Log
			Log.i("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists => " + tableName);
			
			// debug
			String msg_Toast = "Table exists => " + tableName;
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
			
			return true;
//			return false;
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		/*----------------------------
		 * 2. Build sql
			----------------------------*/
		//
		StringBuilder sb = new StringBuilder();
		
		sb.append("CREATE TABLE " + tableName + " (");
		sb.append(android.provider.BaseColumns._ID +
							" INTEGER PRIMARY KEY AUTOINCREMENT, ");
		
		// created_at, modified_at
		sb.append("created_at TEXT, modified_at TEXT, ");
//		sb.append("created_at INTEGER, modified_at INTEGER, ");
		
		int i = 0;
		for (i = 0; i < columns.length - 1; i++) {
			sb.append(columns[i] + " " + types[i] + ", ");
		}//for (int i = 0; i < columns.length - 1; i++)
		
		sb.append(columns[i] + " " + types[i]);
		
		sb.append(");");
		
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "sql => " + sb.toString());
		
		/*----------------------------
		 * 3. Exec sql
			----------------------------*/
		//
		try {
		//	db.execSQL(sql);
			wdb.execSQL(sb.toString());
			
			// Log
			Log.d(this.getClass().getName() + 
					"["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table created => " + tableName);
			
			wdb.close();
			
			return true;
			
		} catch (SQLException e) {
			
			// Log
			Log.e(this.getClass().getName() + 
					"[" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
					"Exception => " + e.toString());
			
			wdb.close();
			
			return false;
			
		}//try

	}//public boolean createTable(SQLiteDatabase db, String tableName)

	public boolean tableExists(SQLiteDatabase db, String tableName) {
		// The table exists?
		Cursor cursor = db.rawQuery(
									"SELECT * FROM sqlite_master WHERE tbl_name = '" + 
									tableName + "'", null);
		
		((Activity) context).startManagingCursor(cursor);
//		actv.startManagingCursor(cursor);
		
		// Judge
		if (cursor.getCount() > 0) {
			return true;
		} else {//if (cursor.getCount() > 0)
			return false;
		}//if (cursor.getCount() > 0)
	}//public boolean tableExists(String tableName)

	public boolean dropTable(Activity actv, SQLiteDatabase db, String tableName) {
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Starting: dropTable()");
		
		/*------------------------------
		 * The table exists?
		 *------------------------------*/
		// The table exists?
		boolean tempBool = tableExists(db, tableName);
		
		if (tempBool == true) {
		
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: " + tableName);

		} else {//if (tempBool == true)
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + tableName);

			return false;
		}//if (tempBool == true)

		/*------------------------------
		 * Drop the table
		 *------------------------------*/
		// Define the sql
        String sql 
             = "DROP TABLE " + tableName;
        
        // Execute
        try {
			db.execSQL(sql);
			
			// Vacuum
			db.execSQL("VACUUM");
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "The table dropped => " + tableName);
			
			// Return
			return true;
			
		} catch (SQLException e) {
			// TODO ?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½ê‚½ catch ?¿½u?¿½?¿½?¿½b?¿½N
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "DROP TABLE => failed (table=" + tableName + "): " + e.toString());
			
			// debug
			Toast.makeText(actv, 
						"DROP TABLE => failed(table=" + tableName, 
						3000).show();
			
			// Return
			return false;
		}//try

	}//public boolean dropTable(String tableName) 

	public boolean drop_table(Activity actv, SQLiteDatabase db, String tableName) {
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Starting: dropTable()");
		
		/*------------------------------
		 * The table exists?
		 *------------------------------*/
		// The table exists?
		boolean tempBool = tableExists(db, tableName);
		
		if (tempBool == true) {
		
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: " + tableName);

		} else {//if (tempBool == true)
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + tableName);

			return false;
		}//if (tempBool == true)

		/*------------------------------
		 * Drop the table
		 *------------------------------*/
		// Define the sql
        String sql 
             = "DROP TABLE " + tableName;
        
        // Execute
        try {
			db.execSQL(sql);
			
			// Vacuum
			db.execSQL("VACUUM");
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "The table dropped => " + tableName);
			
			// Return
			return true;
			
		} catch (SQLException e) {
			// TODO ?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½ê‚½ catch ?¿½u?¿½?¿½?¿½b?¿½N
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "DROP TABLE => failed (table=" + tableName + "): " + e.toString());
			
			// debug
			Toast.makeText(actv, 
						"DROP TABLE => failed(table=" + tableName, 
						3000).show();
			
			// Return
			return false;
		}//try

	}//public boolean dropTable(String tableName) 

	public boolean insertData(SQLiteDatabase db, String tableName, 
												String[] columnNames, String[] values) {
		
////		String sql = "SELECT * FROM TABLE " + DBUtils.table_name_memo_patterns;
//		String sql = "SELECT * FROM " + DBUtils.table_name_memo_patterns;
//		
//		Cursor c = db.rawQuery(sql, null);
//		
//		
//		
//		// Log
//		Log.d("DBUtils.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "c.getCount() => " + c.getCount() + " / " +
//				"c.getColumnCount() => " + c.getColumnCount());
//		
//		c.close();
		
		
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		try {
			// Start transaction
			db.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
			// Put values
			for (int i = 0; i < columnNames.length; i++) {
				val.put(columnNames[i], values[i]);
			}//for (int i = 0; i < columnNames.length; i++)
			
			// Insert data
			db.insert(tableName, null, val);
			
			// Set as successful
			db.setTransactionSuccessful();
			
			// End transaction
			db.endTransaction();
			
			// Log
//			Log.d("DBUtils.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + 
//				" / " + columnNames[3] + " => " + values[3] + ")");
			
			return true;
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Exception! => " + e.toString());
			
			return false;
		}//try
		
//		//debug
//		return false;
		
	}//public insertData(String tableName, String[] columnNames, String[] values)

	/******************************
		public boolean dropTable
		
		@return
			false	=> Table can't be dropped or doesn't exist
	 ******************************/
	public boolean dropTable
	(Activity actv, String tableName) {
		/***************************************
		 * Setup: DB
		 ***************************************/
		SQLiteDatabase wdb = this.getWritableDatabase();
		
		/*------------------------------
		 * The table exists?
		 *------------------------------*/
		// The table exists?
		boolean tempBool = tableExists(wdb, tableName);
		
		if (tempBool == true) {
		
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: " + tableName);

		} else {//if (tempBool == true)
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + tableName);

			return false;
		}//if (tempBool == true)

		/*------------------------------
		 * Drop the table
		 *------------------------------*/
		// Define the sql
        String sql 
             = "DROP TABLE " + tableName;
        
        // Execute
        try {
			wdb.execSQL(sql);
			
			// Vacuum
			wdb.execSQL("VACUUM");
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "The table dropped => " + tableName);
			
			// debug
			String msg_Toast = "The table dropped => " + tableName;
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
			
			wdb.close();
			
			// Return
			return true;
			
		} catch (SQLException e) {
			// TODO ?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½ê‚½ catch ?¿½u?¿½?¿½?¿½b?¿½N
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "DROP TABLE => failed (table=" + tableName + "): " + e.toString());
			
			// debug
			Toast.makeText(actv, 
						"DROP TABLE => failed(table=" + tableName, 
						Toast.LENGTH_LONG).show();
			
			wdb.close();
			
			// Return
			return false;
		}//try

	}//public boolean dropTable(String tableName) 

	public boolean insertData(SQLiteDatabase db, String tableName, 
											String[] columnNames, long[] values) {
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		try {
			// Start transaction
			db.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
			// Put values
			for (int i = 0; i < columnNames.length; i++) {
				val.put(columnNames[i], values[i]);
			}//for (int i = 0; i < columnNames.length; i++)
			
			// Insert data
			db.insert(tableName, null, val);
			
			// Set as successful
			db.setTransactionSuccessful();
			
			// End transaction
			db.endTransaction();
			
			// Log
			Log.d("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + "), and others");
			
			return true;
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Exception! => " + e.toString());
			
			return false;
		}//try
	}//public insertData(String tableName, String[] columnNames, String[] values)

	public boolean deleteData(Activity actv, SQLiteDatabase db, String tableName, long file_id) {
		/*----------------------------
		 * Steps
		 * 1. Item exists in db?
		 * 2. If yes, delete it
			----------------------------*/
		/*----------------------------
		 * 1. Item exists in db?
			----------------------------*/
		boolean result = isInDB_long(db, tableName, file_id);
		
		if (result == false) {		// Result is false ==> Meaning the target data doesn't exist
											//							in db; Hence, not executing delete op
			
			// debug
			Toast.makeText(actv, 
					"Data doesn't exist in db: " + String.valueOf(file_id), 
					2000).show();
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"Data doesn't exist in db => Delete the data (file_id = " + String.valueOf(file_id) + ")");
			
			return false;
			
		} else {//if (result == false)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"Data exists in db" + String.valueOf(file_id) + ")");
			
		}//if (result == false)
		
		
		String sql = 
						"DELETE FROM " + tableName + 
						" WHERE file_id = '" + String.valueOf(file_id) + "'";
		
		try {
			db.execSQL(sql);
			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Data deleted => file id = " + String.valueOf(file_id));
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Sql executed: " + sql);
			
			return true;
			
		} catch (SQLException e) {
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			return false;
			
		}//try
		
	}//public boolean deleteData(SQLiteDatabase db, String tableName, long file_id)

	public boolean deleteData_ai(Activity actv,
						SQLiteDatabase db, String tableName, long db_id) {
		/*----------------------------
		 * Steps
		 * 1. Item exists in db?
		 * 2. If yes, delete it
			----------------------------*/
		/*----------------------------
		 * 1. Item exists in db?
			----------------------------*/
		boolean result = DBUtils.isInDB_long_ai(db, tableName, db_id);
		
		if (result == false) {		// Result is false ==> Meaning the target data doesn't exist
											//							in db; Hence, not executing delete op
			
			// debug
			Toast.makeText(actv, 
					"Data doesn't exist in db: " + String.valueOf(db_id), 
					2000).show();
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"Data doesn't exist in db => Delete the data (db_id = " + String.valueOf(db_id) + ")");
			
			return false;
			
		} else {//if (result == false)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"Data exists in db" + String.valueOf(db_id) + ")");
			
		}//if (result == false)
		
		
		String sql = 
						"DELETE FROM " + tableName
						+ " WHERE " + android.provider.BaseColumns._ID
						+ " = "
						+ String.valueOf(db_id);
		
		try {
			db.execSQL(sql);
			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Data deleted => file id = " + String.valueOf(db_id));
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Sql executed: " + sql);
			
			return true;
			
		} catch (SQLException e) {
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "sql=" + sql);
			
			return false;
			
		}//try
		
	}//public boolean deleteData_ai(Activity actv, SQLiteDatabase db, String tableName, long db_id)

	/****************************************
	 *
	 * 
	 * <Caller> 
	 * 1. deleteData(Activity actv, SQLiteDatabase db, String tableName, long file_id)
	 * 
	 * <Desc> 
	 * 1. REF=> http://stackoverflow.com/questions/3369888/android-sqlite-insert-unique
	 * 
	 * <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static boolean isInDB_long(SQLiteDatabase db, String tableName, long file_id) {
		
		String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE file_id = '" +
						String.valueOf(file_id) + "'";
		
		long result = DatabaseUtils.longForQuery(db, sql, null);
		
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "result => " + String.valueOf(result));
		
		if (result > 0) {

			return true;
			
		} else {//if (result > 0)
			
			return false;
			
		}//if (result > 0)
		
//		return false;
		
	}//public boolean isInDB_long(SQLiteDatabase db, String tableName, long file_id)

	public static boolean isInDB_long_ai(
						SQLiteDatabase db, String tableName, long db_id) {
		
		String sql = "SELECT COUNT(*) FROM " + tableName
					+ " WHERE " + android.provider.BaseColumns._ID + " = "
					+ String.valueOf(db_id);
		
		long result = DatabaseUtils.longForQuery(db, sql, null);
		
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "result => " + String.valueOf(result));
		
		if (result > 0) {

			return true;
			
		} else {//if (result > 0)
			
			return false;
			
		}//if (result > 0)
		
//		return false;
		
	}//public static boolean isInDB_long_ai

	public boolean insert_data_refresh_history(SQLiteDatabase wdb,
			String tableName, long[] data) {
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
//			// Put values
//			for (int i = 0; i < columnNames.length; i++) {
//				val.put(columnNames[i], values[i]);
//			}//for (int i = 0; i < columnNames.length; i++)
			
//			"last_refreshed", "num_of_items_added"
			
			val.put("last_refreshed", data[0]);
			
			val.put("num_of_items_added", data[1]);
			
			// Insert data
			wdb.insert(tableName, null, val);
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
			// Log
//			Log.d("DBUtils.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + 
//				" / " + columnNames[3] + " => " + values[3] + ")");
			
			return true;
			
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Exception! => " + e.toString());
			
			return false;
			
		}//try
		
	}//public boolean insert_data_refresh_history

	
	public static boolean
	update_data_ai
	(Activity actv, String dbName, String tableName,
			long db_id, String col_name, String value) {
		/*********************************
		 * memo
		 *********************************/
		DBUtils dbu = new DBUtils(actv, dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		
//		String sql = "UPDATE " + CONS.tname_main + " SET " + 
	
		String sql = "UPDATE " + tableName + " SET " + 
//				"last_viewed_at='" + Methods.getMillSeconds_now() + "' " +

				col_name + " = '" + value + "' "
				+ " WHERE " + android.provider.BaseColumns._ID + " = '"
				+ db_id + "'";
		
		
		//			"file_id", 		"file_path", "file_name", "date_added", "date_modified"
		//static String[] cols = 
		//{"file_id", 		"file_path", "file_name", "date_added",
		//"date_modified", "memos", "tags"};
		
		
		try {
			
			wdb.execSQL(sql);
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "sql => Done: " + sql);
			
			//Methods.toastAndLog(actv, "Data updated", 2000);
			
			return true;
			
			
		} catch (SQLException e) {
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString() + " / " + "sql: " + sql);
			
			return false;
		}
		
	}//public static boolean update_data_ai()

	
	public void updateData_aiLength(Activity actv, String table_name,
			long db_id, int length) {
		
		DBUtils dbu = new DBUtils(actv, dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();

		
		String sql = "UPDATE " + table_name + " SET " + 
				"length" + " = " + length + " "
				+ " WHERE " + android.provider.BaseColumns._ID + " = '"
				+ db_id + "'";
				
		// Log
		Log.d("DBUtils.java" + "["
		+ Thread.currentThread().getStackTrace()[2].getLineNumber()
		+ "]", "sql=" + sql);

		try {
			
			wdb.execSQL(sql);
			
			// Log
			Log.d("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Exec sql => Done");
			
		} catch (SQLException e) {

			// Log
			Log.e("DBUtils.java" + "["
			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
			+ "]", "Exception => " + e.toString());

		}//try
		
		// Close
		wdb.close();
		
	}//public void updateData_aiLength

	public boolean
	updateData_bm
	(Activity actv, long dbId, String colName, String colValue) {

		/***************************************
		 * Setup: DB
		 ***************************************/
		SQLiteDatabase wdb = this.getWritableDatabase();
		
		/***************************************
		 * Build SQL
		 ***************************************/
		String sql = "UPDATE " + CONS.DB.tname_BM + " SET "
//				+ colName + "='" + colValue + "', "
				+ colName + "='" + colValue + "'"
				+ " WHERE " + android.provider.BaseColumns._ID + " = '" + dbId + "'";
				
		/***************************************
		 * Exec: Query
		 ***************************************/
		try {
			
			wdb.execSQL(sql);
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "sql => Done: " + sql);
			
		//	Methods.toastAndLog(actv, "Data updated", 2000);

			wdb.close();
			
			return true;
			
			
		} catch (SQLException e) {
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString() + " / " + "sql: " + sql);
			
			wdb.close();
			
			return false;
		}

	}//updateData_bm()

	public boolean
	updateData_generic
	(Activity actv, String tableName, long dbId, String colName, String colValue) {

		/***************************************
		 * Setup: DB
		 ***************************************/
		SQLiteDatabase wdb = this.getWritableDatabase();
		
		/***************************************
		 * Build SQL
		 ***************************************/
		String sql = "UPDATE " + tableName + " SET "
//				+ colName + "='" + colValue + "', "
				+ colName + "='" + colValue + "'"
				+ " WHERE " + android.provider.BaseColumns._ID + " = '" + dbId + "'";
				
		/***************************************
		 * Exec: Query
		 ***************************************/
		try {
			
			wdb.execSQL(sql);
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "sql => Done: " + sql);
			
		//	Methods.toastAndLog(actv, "Data updated", 2000);

			wdb.close();
			
			return true;
			
			
		} catch (SQLException e) {
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString() + " / " + "sql: " + sql);
			
			wdb.close();
			
			return false;
		}

	}//updateData_generic()

	public int
	getNumOfEntries(Activity actv, String table_name) {
		/*********************************
		 * memo
		 *********************************/
//		DBUtils dbu = new DBUtils(actv, CONS.dbName);
		
		SQLiteDatabase rdb = this.getReadableDatabase();

		String sql = "SELECT * FROM " + table_name;
		
		Cursor c = null;
		
		try {
			
			c = rdb.rawQuery(sql, null);
			
		} catch (Exception e) {
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			rdb.close();
			
			return -1;
		}
		
		int num_of_entries = c.getCount();
		
		rdb.close();

		return num_of_entries;
		
	}//public int getNumOfEntries(Activity actv, String table_name)

	public int
	getNumOfEntries_BM(Activity actv, String table_name, long aiDbId) {
		/*********************************
		 * memo
		 *********************************/
//		DBUtils dbu = new DBUtils(actv, CONS.dbName);
		
		SQLiteDatabase rdb = this.getReadableDatabase();

		String sql = "SELECT * FROM " + table_name
					+ " WHERE "
					+ "ai_id = "
					+ aiDbId;
		
		Cursor c = null;
		
		try {
			
			c = rdb.rawQuery(sql, null);
			
		} catch (Exception e) {
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			rdb.close();
			
			return -1;
		}
		
		int num_of_entries = c.getCount();
		
		rdb.close();

		return num_of_entries;
		
	}//public int getNumOfEntries_BM(Activity actv, String table_name, long aiDbId)

	
	public boolean deleteData_bm(Activity actv, long dbId) {
		/*----------------------------
		 * Steps
		 * 1. Item exists in db?
		 * 2. If yes, delete it
			----------------------------*/
		/***************************************
		 * 1. Item exists in db?
		 ***************************************/
		SQLiteDatabase wdb = this.getWritableDatabase();
		
//		boolean result = DBUtils.isInDB_long_ai(db, tableName, db_id);
		boolean result = this.isInDB_bm(wdb, dbId);
		
		if (result == false) {		// Result is false ==> Meaning the target data doesn't exist
											//							in db; Hence, not executing delete op
			
			// debug
			Toast.makeText(actv, 
					"Data doesn't exist in db: " + String.valueOf(dbId), 
					Toast.LENGTH_LONG).show();
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"Data doesn't exist in db => " + String.valueOf(dbId));
			
			return false;
			
		} else {//if (result == false)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"Data exists in db" + String.valueOf(dbId) + ")");
			
		}//if (result == false)
		

		/***************************************
		 * Delete data
		 ***************************************/
		String sql = 
						"DELETE FROM " + CONS.DB.tname_BM
						+ " WHERE "
						+ CONS.DB.col_names_BM_full[0] + " = '"
						+ String.valueOf(dbId) + "'";
		
		try {
			wdb.execSQL(sql);
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Sql executed: " + sql);

			wdb.close();
			
			return true;
			
		} catch (SQLException e) {
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "sql=" + sql);
			
			wdb.close();
			
			return false;
			
		}//try
		
	}//public boolean deleteData_bm(Activity actv, long dbId)

	private boolean
	isInDB_bm(SQLiteDatabase wdb, long dbId) {
		String sql = "SELECT COUNT(*) FROM "
					+ CONS.DB.tname_BM
					+ " WHERE "
					+ CONS.DB.col_names_BM_full[0] + " = '"
					+ String.valueOf(dbId) + "'";

//		long result = DatabaseUtils.longForQuery(db, sql, null);
		long result = DatabaseUtils.longForQuery(wdb, sql, null);
		
		// Log
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "result => " + String.valueOf(result));
		
		if (result > 0) {
		
			return true;
			
		} else {//if (result > 0)
			
			return false;
			
		}//if (result > 0)
		
	}//isInDB_bm(SQLiteDatabase wdb, long dbId)

	/******************************
		insertData_AI(Activity actv, AI ai)
		
		@return false => 1. transaction exception<br>
						2. Insertion failed
	 ******************************/
	public static boolean
	insertData_AI(Activity actv, AI ai)
	{
		// TODO Auto-generated method stub
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		////////////////////////////////

		// Build: data

		////////////////////////////////
//		"file_name", "file_path",	// 0, 1
//		"title", "memo",			// 2, 3
//		"last_played_at",			// 4
//		"table_name",				// 5
//		"length"					// 6
		Object[] values = new Object[]{
				
				ai.getFile_name(), ai.getFile_path(),
				null, null,
				0,
				ai.getTable_name(),
				ai.getLength()
				
		};
		
		////////////////////////////////

		// Insert

		////////////////////////////////
		/*----------------------------
		* 1. Insert data
		----------------------------*/
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();
			
//			col_names_CM7_full
//			android.provider.BaseColumns._ID,	// 0
//			"created_at", "modified_at",		// 1, 2
//			"file_name", "file_path",			// 3, 4
//			"title", "memo",					// 5, 6
//			"last_played_at",					// 7
//			"table_name",						// 8
//			"length",							// 9
//			"audio_created_at"					// 10
			
			val.put(CONS.DB.col_names_CM7_full[1], 
					Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
			val.put(CONS.DB.col_names_CM7_full[2], 
					Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
			
			val.put(CONS.DB.col_names_CM7_full[3], ai.getFile_name());
			val.put(CONS.DB.col_names_CM7_full[4], ai.getFile_path());
			val.put(CONS.DB.col_names_CM7_full[5], ai.getTitle());
			
			val.put(CONS.DB.col_names_CM7_full[6], ai.getMemo());
			val.put(CONS.DB.col_names_CM7_full[7], ai.getLast_played_at());
			val.put(CONS.DB.col_names_CM7_full[8], ai.getTable_name());
			
			val.put(CONS.DB.col_names_CM7_full[9], ai.getLength());
			val.put(CONS.DB.col_names_CM7_full[10], ai.getAudio_created_at());
			
//			val.put(CONS.DB.col_names_CM7[0], (String)values[0]);
//			val.put(CONS.DB.col_names_CM7[1], (String)values[1]);
//			val.put(CONS.DB.col_names_CM7[2], (String)values[2]);
//			val.put(CONS.DB.col_names_CM7[3], (String)values[3]);
//			val.put(CONS.DB.col_names_CM7[4], (Long)values[4]);
//			val.put(CONS.DB.col_names_CM7[5], (String)values[5]);
//			val.put(CONS.DB.col_names_CM7[6], (Long)values[6]);
//			for (int i = 0; i < CONS.DB.col_names_CM7.length; i++) {
//				
//				val.put(CONS.DB.col_names_CM7[i], values[i]);
//				
//			}//for (int i = 0; i < columnNames.length; i++)
			
			// Insert data
			long res = wdb.insert(CONS.DB.tname_CM7, null, val);
			
			if (res == -1) {
				
				// Log
				String msg_Log = "Insertion => failed: " + ai.getFile_name();
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				// End transaction
				wdb.endTransaction();
				
				wdb.close();
				
				return false;
				
			} else {

				wdb.setTransactionSuccessful();
				
				// Log
				String msg_Log = "Insertion => done: " + ai.getFile_name();
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				// End transaction
				wdb.endTransaction();
				
				wdb.close();
				
				return true;
				
			}
			// Set as successful
			
//			// End transaction
//			wdb.endTransaction();
//			
//			// Log
////			Log.d("DBUtils.java" + "["
////				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////				+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + 
////				" / " + columnNames[3] + " => " + values[3] + ")");
//			
//			return true;
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Exception! => " + e.toString());
			
			return false;
			
		}//try
		
	}//insertData_AI(Activity actv, AI ai)

	/******************************
		insertData_Refresh
		(Activity actv, Refresh refresh)
		
		@return false => 1. transaction exception<br>
						2. Insertion failed
	 ******************************/
	public static boolean
	insertData_Refresh
	(Activity actv, Refresh refresh) {
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		////////////////////////////////

		// Build: data

		////////////////////////////////
		
		////////////////////////////////

		// Insert

		////////////////////////////////
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();

//			col_names_RefreshHistory_full
//			android.provider.BaseColumns._ID,		// 0
//			"created_at", "modified_at",			// 1,2
//			"last_refreshed", "num_of_items_added"	// 3,4
			
			val.put(CONS.DB.col_names_RefreshHistory_full[1], 
					Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
			val.put(CONS.DB.col_names_RefreshHistory_full[2], 
					Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
			
			val.put(CONS.DB.col_names_RefreshHistory_full[3], 
										refresh.getLast_refreshed());
			val.put(CONS.DB.col_names_RefreshHistory_full[4], 
										refresh.getNum_ItemsAdded());
			
			// Insert data
			long res = wdb.insert(CONS.DB.tname_RefreshHistory, null, val);
			
			if (res == -1) {
				
				// Log
				String msg_Log = "Insertion => failed: " + refresh.getLast_refreshed();
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				// End transaction
				wdb.endTransaction();
				
				wdb.close();
				
				return false;
				
			} else {

				wdb.setTransactionSuccessful();
				
				// Log
				String msg_Log = "Insertion => done: " + refresh.getLast_refreshed();
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				// End transaction
				wdb.endTransaction();
				
				wdb.close();
				
				return true;
				
			}
			// Set as successful
			
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Exception! => " + e.toString());
			
			return false;
			
		}//try
		
	}//insertData_Refresh

	public static Refresh
	get_LatestEntry_Refresh(Activity actv) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		Cursor c = null;
		
		////////////////////////////////

		// Query

		////////////////////////////////
		try {
			
			c = rdb.query(
							CONS.DB.tname_RefreshHistory,			// 1
							CONS.DB.col_names_RefreshHistory_full,	// 2
							null, null,								// 3,4
							null, null,								// 5,6
							CONS.DB.col_names_RefreshHistory_full[3] + " desc",	// 7
							null);
//			null, null, null);
			
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
		
		/***************************************
		 * Build list
		 ***************************************/
		c.moveToFirst();

//		col_names_RefreshHistory_full
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"last_refreshed", "num_of_items_added"	// 3,4

		Refresh refresh = new Refresh.Builder()
						.setDb_id(c.getLong(0))
						.setCreated_at(c.getString(1))
						.setModified_at(c.getString(2))
						.setLast_refreshed(c.getString(3))
						.setNum_ItemsAdded(c.getInt(4))
						.build();
		
//		BM bm = new BM.Builder()
//			.setPosition(c.getLong(c.getColumnIndex("position")))
//			.setTitle(c.getString(c.getColumnIndex("title")))
//			.setMemo(c.getString(c.getColumnIndex("memo")))
//			.setAiId(c.getLong(c.getColumnIndex("ai_id")))
//			.setAiTableName(c.getString(c.getColumnIndex("aiTableName")))
//			.setDbId(c.getLong(c.getColumnIndex(CONS.DB.cols_bm_full[0])))
//			.build();

		rdb.close();
		
		return refresh;
//		return null;
	}

	public static List<AI>
	find_All_AI(Activity actv, String tableName) {
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		Cursor c = null;
		
		////////////////////////////////

		// Query

		////////////////////////////////
		try {
			
			c = rdb.query(
					tableName,			// 1
					CONS.DB.col_names_CM7_full,	// 2
					null, null,		// 3,4
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
		
		/***************************************
		 * Build list
		 ***************************************/
//		c.moveToFirst();

//		col_names_CM7_full
//		android.provider.BaseColumns._ID,	// 0
//		"created_at", "modified_at",		// 1, 2
//		"file_name", "file_path",			// 3, 4
//		"title", "memo",					// 5, 6
//		"last_played_at",					// 7
//		"table_name",						// 8
//		"length",							// 9
//		"audio_created_at"					// 10

		List<AI> ai_List = new ArrayList<AI>();
		
		while(c.moveToNext()) {
			
			AI ai = new AI.Builder()
			
					.setDb_id(c.getLong(0))
					.setCreated_at(c.getString(1))
					.setModified_at(c.getString(2))
					.setFile_name(c.getString(3))
					.setFile_path(c.getString(4))
					.setTitle(c.getString(5))
					.setMemo(c.getString(6))
					.setLast_played_at(c.getString(7))
					.setTable_name(c.getString(8))
					.setLength(c.getString(9))
					.setAudio_created_at(c.getString(10))
					
					.build();
			
			ai_List.add(ai);
			
		}

		rdb.close();
		
		return ai_List;
		
	}//find_All_AI(Activity actv, String tableName)
	
	public static AI
	find_AI_ById
	(Activity actv, long db_Id, String table_Name) {
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		Cursor c = null;
		
		////////////////////////////////
		
		// Query
		
		////////////////////////////////
		String where = CONS.DB.col_names_CM7_full[0] + " = ?";
		
		String[] args = new String[]{String.valueOf(db_Id)};
		
		try {
			
			c = rdb.query(
					table_Name,			// 1
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
		
		/***************************************
		 * Build list
		 ***************************************/
//		c.moveToFirst();
		
//		col_names_CM7_full
//		android.provider.BaseColumns._ID,	// 0
//		"created_at", "modified_at",		// 1, 2
//		"file_name", "file_path",			// 3, 4
//		"title", "memo",					// 5, 6
//		"last_played_at",					// 7
//		"table_name",						// 8
//		"length",							// 9
//		"audio_created_at"					// 10

		c.moveToFirst();
		
		AI ai = new AI.Builder()
		
		.setDb_id(c.getLong(0))
		.setCreated_at(c.getString(1))
		.setModified_at(c.getString(2))
		.setFile_name(c.getString(3))
		.setFile_path(c.getString(4))
		.setTitle(c.getString(5))
		.setMemo(c.getString(6))
		.setLast_played_at(c.getString(7))
		.setTable_name(c.getString(8))
		.setLength(c.getString(9))
		.setAudio_created_at(c.getString(10))
		
		.build();

		rdb.close();
		
		return ai;
		
	}//find_AI

	public boolean insertData_BM(Activity actv, BM bm) {
		// TODO Auto-generated method stub
		SQLiteDatabase wdb = this.getWritableDatabase();
		
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// ContentValues
			ContentValues val = new ContentValues();

//			"ai_id", "position", "title", "memo", "aiTableName"
//			val.put(android.provider.BaseColumns._ID, ai.getDb_id());
			
			val.put("created_at", 
					Methods.conv_MillSec_to_TimeLabel(
							Methods.getMillSeconds_now()));
			
			val.put("modified_at", 
					Methods.conv_MillSec_to_TimeLabel(
							Methods.getMillSeconds_now()));
//			val.put("created_at", Methods.getMillSeconds_now());
//			val.put("modified_at", Methods.getMillSeconds_now());
			
			val.put("ai_id", bm.getAiId());
			val.put("position", bm.getPosition());
			
			val.put("title", bm.getTitle());
			val.put("memo", bm.getMemo());
			
			val.put("aiTableName", bm.getAiTableName());
			
			// Insert data
			long res = wdb.insert(CONS.DB.tname_BM, null, val);
			
			// Set as successful
			if (res != -1) {
				
				wdb.setTransactionSuccessful();
				
			} else {

				// Log
				String msg_Log = "Insertion => failed: getAiId() = "
								+ bm.getAiId();
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
			// End transaction
			wdb.endTransaction();
			
			// Log
			Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]",
				"BM inserted: AiID = "
				+ bm.getAiId());
			
			return true;
			
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Exception! => " + e.toString());
			
			return false;
			
		}//try
		
	}//public boolean insertData_bm(Activity actv, BM bm)

}//public class DBUtils


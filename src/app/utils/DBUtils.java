package app.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cm7.main.R;
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
import app.items.BMStore;
import app.items.Refresh;
import app.items.WordPattern;

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
			// TODO ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ黷ｽ catch ?�ｽ�ｽu?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽb?�ｽ�ｽN
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
			// TODO ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ黷ｽ catch ?�ｽ�ｽu?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽb?�ｽ�ｽN
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

//	public boolean dropTable
	public int dropTable
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
			
			// debug
			String msg_Toast = "Table doesn't exist: " + tableName;
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();

			return -1;
			
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
			return 1;
			
		} catch (SQLException e) {
			// TODO ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽ黷ｽ catch ?�ｽ�ｽu?�ｽ�ｽ?�ｽ�ｽ?�ｽ�ｽb?�ｽ�ｽN
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "DROP TABLE => failed (table=" + tableName + "): " + e.toString());
			
			// debug
			Toast.makeText(actv, 
						"DROP TABLE => failed(table=" + tableName + ")", 
						Toast.LENGTH_LONG).show();
			
			wdb.close();
			
			// Return
			return -2;
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

	/******************************
		@return true => update successful
	 ******************************/
	public static boolean
	update_Data_AI
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

	
	public boolean deleteData_BM(Activity actv, long dbId) {
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
			
			if(ai.getCreated_at() == null
				|| ai.getCreated_at() == "") {
				
				val.put(CONS.DB.col_names_CM7_full[1], 
						Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
			
			}
			
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
		
		////////////////////////////////

		// validate: DB file exists?

		////////////////////////////////
		File dpath_DBFile = actv.getDatabasePath(CONS.DB.dbName);

		if (!dpath_DBFile.exists()) {
			
			String msg = "No DB file: " + CONS.DB.dbName;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return null;
			
		}
		
		////////////////////////////////

		// DB

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
//		////////////////////////////////
//		
//		// validate: table exists?
//		
//		////////////////////////////////
//		boolean res = dbu.tableExists(rdb, tableName);
//
//		if (res == false) {
//			
//			String msg = "No such table: " + tableName;
//			Methods_dlg.dlg_ShowMessage(actv, msg);
//			
//			return null;
//			
//		}

		////////////////////////////////
		
		// Query
		
		////////////////////////////////
		Cursor c = null;
		
		String where = CONS.DB.col_names_CM7_full[8] + " = ?";
		String[] args = new String[]{
				
							tableName
		};
		
		try {
			
			c = rdb.query(
					CONS.DB.tname_CM7,			// 1
					CONS.DB.col_names_CM7_full,	// 2
					where, args,		// 3,4
					null, null,		// 5,6
					null,			// 7
					null);
//			tableName,			// 1
//			CONS.DB.col_names_CM7_full,	// 2
//			null, null,		// 3,4
//			null, null,		// 5,6
//			null,			// 7
//			null);
			
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
					CONS.DB.tname_CM7,			// 1
//					table_Name,			// 1
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

	/*******************************
	 * @return
	 * 
	 * null	=> Query exception<br>
	 * 		=> Query returned null<br>
	 * 		=> No entries<br>
	 *******************************/
	public static AI
	find_AI_ById
	(Activity actv, long db_Id) {
		
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
					CONS.DB.tname_CM7,			// 1
//					table_Name,			// 1
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
	
	/******************************
		@return
			null => <br>
				1. No DB file<br>
				2. No such table<br>
				3. Query exception<br>
				4. Query returned null<br>
				5. No entry in the table<br>
	 ******************************/
	public static List<WordPattern>
	find_All_WordPatterns
	(Activity actv) {
		
		////////////////////////////////

		// validate: DB file exists?

		////////////////////////////////
		File dpath_DBFile = actv.getDatabasePath(CONS.DB.dbName);

		if (!dpath_DBFile.exists()) {
			
			String msg = "No DB file: " + CONS.DB.dbName;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return null;
			
		}
		
		////////////////////////////////

		// DB

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		String tname = CONS.DB.tname_MemoPatterns;
		
		////////////////////////////////
		
		// validate: table exists?
		
		////////////////////////////////
		boolean res = dbu.tableExists(rdb, tname);

		if (res == false) {
			
			String msg = "No such table: " + tname;
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			return null;
			
		}

		////////////////////////////////
		
		// Query
		
		////////////////////////////////
		Cursor c = null;
		
		try {
			
			c = rdb.query(
					CONS.DB.tname_MemoPatterns,			// 1
					CONS.DB.col_names_MemoPatterns_full,	// 2
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
		
		/***************************************
		 * Build list
		 ***************************************/
		c.moveToFirst();

//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"word"									// 3

		List<WordPattern> list_WPs = new ArrayList<WordPattern>();
		
		for (int i = 0; i < c.getCount(); i++) {
			
//			patternList.add(c.getString(3));	// "word"
			list_WPs.add(
//					patternList.add(
						new WordPattern.Builder()
							.setDb_Id(c.getLong(0))
							.setWord(c.getString(3))
						
							.build());	// "word"
			
			c.moveToNext();
			
		}//for (int i = 0; i < patternList.size(); i++)


		rdb.close();
		
		return list_WPs;
		
	}//find_All_WordPatterns
	

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
			
			wdb.close();
			
			return true;
			
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return false;
			
		}//try
		
	}//public boolean insertData_bm(Activity actv, BM bm)

	/*******************************
	 * @return
	 * 1	=> data inserted<br>
	 * -1	=> insertion failed<br>
	 * -2	=> insertion exception<br>
	 *******************************/
	public static int 
	insertData_BM_static(Activity actv, BM bm) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// ContentValues
			ContentValues val = insertData_BM_static__GetCV(actv, bm);
////			ContentValues val = new ContentValues();
//			
////			"ai_id", "position", "title", "memo", "aiTableName"
////			val.put(android.provider.BaseColumns._ID, ai.getDb_id());
//			
//			
//			val.put("created_at", 
//					Methods.conv_MillSec_to_TimeLabel(
//							Methods.getMillSeconds_now()));
//			
//			val.put("modified_at", 
//					Methods.conv_MillSec_to_TimeLabel(
//							Methods.getMillSeconds_now()));
////			val.put("created_at", Methods.getMillSeconds_now());
////			val.put("modified_at", Methods.getMillSeconds_now());
//			
//			val.put("ai_id", bm.getAiId());
//			val.put("position", bm.getPosition());
//			
//			val.put("title", bm.getTitle());
//			val.put("memo", bm.getMemo());
//			
//			val.put("aiTableName", bm.getAiTableName());
			
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
				

				// End transaction
				wdb.endTransaction();
				
				wdb.close();
				
				return -1;

			}
			
			// End transaction
			wdb.endTransaction();
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]",
					"BM inserted: AiID = "
							+ bm.getAiId());
			
			wdb.close();
			
			return 1;
			
		} catch (Exception e) {
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return -2;
			
		}//try
		
	}//public boolean insertData_bm(Activity actv, BM bm)
	
	private static ContentValues 
	insertData_BM_static__GetCV
	(Activity actv, BM bm) {
		// TODO Auto-generated method stub
		ContentValues val = new ContentValues();
		
//		"ai_id", "position", "title", "memo", "aiTableName"
//		val.put(android.provider.BaseColumns._ID, ai.getDb_id());

		///////////////////////////////////
		//
		// created_at
		//
		///////////////////////////////////
		String created_at = null;
		
		if (bm.getCreated_at() == null || bm.getCreated_at().equals("")) {
			
			created_at = Methods.conv_MillSec_to_TimeLabel(
					Methods.getMillSeconds_now());
			
		} else {//if (bm.getCreated_at() == null || bm.getCreated_at().equals(""))
			
			created_at = bm.getCreated_at();
			
		}//if (bm.getCreated_at() == null || bm.getCreated_at().equals(""))
		
		val.put("created_at", created_at);
//		Methods.conv_MillSec_to_TimeLabel(
//				Methods.getMillSeconds_now()));
		
		///////////////////////////////////
		//
		// modified_at
		//
		///////////////////////////////////
		String modified_at = null;
		
		if (bm.getCreated_at() == null || bm.getCreated_at().equals("")) {
			
			modified_at = Methods.conv_MillSec_to_TimeLabel(
					Methods.getMillSeconds_now());
			
		} else {//if (bm.getCreated_at() == null || bm.getCreated_at().equals(""))
			
			modified_at = bm.getModified_at();
			
		}//if (bm.getCreated_at() == null || bm.getCreated_at().equals(""))
		
		val.put("modified_at", modified_at);
//		Methods.conv_MillSec_to_TimeLabel(
//				Methods.getMillSeconds_now()));
		
//		val.put("modified_at", 
//				Methods.conv_MillSec_to_TimeLabel(
//						Methods.getMillSeconds_now()));
		
		///////////////////////////////////
		//
		// ai_id, position, title, ...
		//
		///////////////////////////////////
		val.put("ai_id", bm.getAiId());
		val.put("position", bm.getPosition());
		
		val.put("title", bm.getTitle());
		val.put("memo", bm.getMemo());
		
		val.put("aiTableName", bm.getAiTableName());

		return val;
		
	}//insertData_BM_static__GetCV

	public static boolean
	insertData_BMStore
	(Activity actv, SQLiteDatabase wdb, BMStore bmStore) {
		// TODO Auto-generated method stub

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
			
			val.put("ai_name", bmStore.getAi_name());
			val.put("position", bmStore.getPosition());
			
			val.put("title", bmStore.getTitle());
			val.put("memo", bmStore.getMemo());
			
			val.put("orig_created_at", 
						bmStore.getOrig_created_at());
			
			val.put("orig_modified_at", 
						bmStore.getOrig_modified_at());
			
			// Insert data
			long res = wdb.insert(CONS.DB.tname_BMStore, null, val);
			
			// Set as successful
			if (res != -1) {
				
				wdb.setTransactionSuccessful();
				
			} else {
				
				// Log
				String msg_Log;
				
				msg_Log = String.format(
								Locale.JAPAN,
								"Insertion => failed: title = %s "
								+ "(Name = %s)", 
								bmStore.getTitle(), bmStore.getAi_name());
						
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
			// End transaction
			wdb.endTransaction();
			
//			// Log
//			Log.d("DBUtils.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]",
//					"BM inserted: AiID = "
//							+ bm.getAiId());
			
//			wdb.close();
			
			return true;
			
		} catch (Exception e) {
			// Log
			String msg_Log;
			
			msg_Log = String.format(
							Locale.JAPAN,
							"Exception => title = %s "
							+ "(Name = %s)\n",
							bmStore.getTitle(), bmStore.getAi_name()
							);

			Log.d("DBUtils.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", msg_Log);

			e.printStackTrace();
			
//			wdb.close();
			
			return false;
			
		}//try
		
	}//insertData_BMStore(Activity actv, BMStore bmStore)
	
	/*******************************
	 * @return
	 * null	=> Query exception<br>
	 * 			Query returned null<br>
	 * 			no entry<br>
	 *******************************/
	public static List<BMStore> get_BMStoreList(Activity actv, String fname) {
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		Cursor c = null;

//		android.provider.BaseColumns._ID,	// 0
//		"created_at", "modified_at",		// 1,2
//		"ai_name", "position",				// 3,4
//		"title", "memo",					// 5,6
//		"orig_created_at", "orig_modified_at",	// 7,8

		try {
			
			c = rdb.query(
							CONS.DB.tname_BMStore,
							CONS.DB.col_names_BMStore_full,
//							CONS.DB.cols_bm_full,
//							CONS.DB.cols_bm[0], new String[]{String.valueOf(aiDbId)},
							CONS.DB.col_names_BMStore_full[3] + " = ?", 
//							CONS.DB.col_names_BM_full[0] + " = ?", 
							new String[]{fname},
							null, null, null);
			
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
		
		List<BMStore> list_BMStores = new ArrayList<BMStore>();
		
//		android.provider.BaseColumns._ID,	// 0
//		"created_at", "modified_at",		// 1,2
//		"ai_name", "position",				// 3,4
//		"title", "memo",					// 5,6
//		"orig_created_at", "orig_modified_at",	// 7,8
		
		for (int i = 0; i < c.getCount(); i++) {
//			"ai_id", "position", "title", "memo", "aiTableName"
			BMStore bmStore = new BMStore.Builder()
			
				.setDbId(c.getLong(0))
				.setCreated_at(c.getString(1))
				.setModified_at(c.getString(2))
				
				.setAi_name(c.getString(3))
				.setPosition(c.getString(4))
				
				.setTitle(c.getString(c.getColumnIndex("title")))
				.setMemo(c.getString(c.getColumnIndex("memo")))

				.setOrig_created_at(c.getString(7))
				.setOrig_modified_at(c.getString(8))

				.build();

			list_BMStores.add(bmStore);
			
			c.moveToNext();
			
		}//for (int i = 0; i < c.getCount(); i++)
		
		
		rdb.close();
		
		return list_BMStores;
		
	}//public List<BM> getBMList(Activity actv)

	/*******************************
	 * @return
	 * null	=> Query exception<br>
	 * 			Query returned null<br>
	 * 			no entry<br>
	 *******************************/
	public List<BM> get_BMList(Activity actv, long aiDbId) {
		
		SQLiteDatabase rdb = this.getReadableDatabase();
		
		Cursor c = null;
		
//		col_names_BM_full
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"ai_id", "position",					// 3,4
//		"title", "memo", "aiTableName"			// 5,6,7
		
		try {
			
			c = rdb.query(
					CONS.DB.tname_BM,
//							CONS.DBAdmin.col_purchaseSchedule,
//							CONS.DB.cols_bm,
					CONS.DB.col_names_BM_full,
//							CONS.DB.cols_bm_full,
//							CONS.DB.cols_bm[0], new String[]{String.valueOf(aiDbId)},
					CONS.DB.col_names_BM_full[3] + " = ?", 
//							CONS.DB.col_names_BM_full[0] + " = ?", 
					new String[]{String.valueOf(aiDbId)},
					null, null, null);
			
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
		
		List<BM> bmList = new ArrayList<BM>();
		
//		col_names_BM_full
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"ai_id", "position",					// 3,4
//		"title", "memo", "aiTableName"			// 5,6,7
		
		for (int i = 0; i < c.getCount(); i++) {
//			"ai_id", "position", "title", "memo", "aiTableName"
			BM bm = new BM.Builder()
			
			.setDbId(c.getLong(0))
			.setCreated_at(c.getString(1))
			.setModified_at(c.getString(2))
			
			.setPosition(c.getString(c.getColumnIndex("position")))
			.setTitle(c.getString(c.getColumnIndex("title")))
			.setMemo(c.getString(c.getColumnIndex("memo")))
			.setAiId(c.getLong(c.getColumnIndex("ai_id")))
			.setAiTableName(c.getString(c.getColumnIndex("aiTableName")))
			
			.setDbId(c.getLong(c.getColumnIndex(
					android.provider.BaseColumns._ID)))
//				.setDbId(c.getLong(c.getColumnIndex(CONS.DB.cols_bm_full[0])))
					.build();
			
			bmList.add(bm);
			
			c.moveToNext();
			
		}//for (int i = 0; i < c.getCount(); i++)
		
		
		rdb.close();
		
		return bmList;
		
	}//public List<BM> getBMList(Activity actv)
	
	/*******************************
	 * @return
	 * null	=> Query exception<br>
	 * 			Query returned null<br>
	 * 			no entry<br>
	 *******************************/
	public static List<BM> 
	find_BM_All_By_AIid(Activity actv, long aiDbId) {
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		Cursor c = null;
		
//		col_names_BM_full
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"ai_id", "position",					// 3,4
//		"title", "memo", "aiTableName"			// 5,6,7
		
		try {
			
			c = rdb.query(
					CONS.DB.tname_BM,
//							CONS.DBAdmin.col_purchaseSchedule,
//							CONS.DB.cols_bm,
					CONS.DB.col_names_BM_full,
//							CONS.DB.cols_bm_full,
//							CONS.DB.cols_bm[0], new String[]{String.valueOf(aiDbId)},
					CONS.DB.col_names_BM_full[3] + " = ?", 
//							CONS.DB.col_names_BM_full[0] + " = ?", 
					new String[]{String.valueOf(aiDbId)},
					null, null, null);
			
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
		
		List<BM> bmList = new ArrayList<BM>();
		
//		col_names_BM_full
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"ai_id", "position",					// 3,4
//		"title", "memo", "aiTableName"			// 5,6,7
		
		for (int i = 0; i < c.getCount(); i++) {
//			"ai_id", "position", "title", "memo", "aiTableName"
			BM bm = new BM.Builder()
			
			.setDbId(c.getLong(0))
			.setCreated_at(c.getString(1))
			.setModified_at(c.getString(2))
			
			.setPosition(c.getString(c.getColumnIndex("position")))
			.setTitle(c.getString(c.getColumnIndex("title")))
			.setMemo(c.getString(c.getColumnIndex("memo")))
			.setAiId(c.getLong(c.getColumnIndex("ai_id")))
			.setAiTableName(c.getString(c.getColumnIndex("aiTableName")))
			
			.setDbId(c.getLong(c.getColumnIndex(
					android.provider.BaseColumns._ID)))
//				.setDbId(c.getLong(c.getColumnIndex(CONS.DB.cols_bm_full[0])))
					.build();
			
			bmList.add(bm);
			
			c.moveToNext();
			
		}//for (int i = 0; i < c.getCount(); i++)
		
		
		rdb.close();
		
		return bmList;
		
	}//find_BM_All_By_AIid
	
	public static boolean
	updateData_BM_TitleAndMemo
	(Activity actv, long dbId, BM bm) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
//		col_names_BM_full
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"ai_id", "position",					// 3,4
//		"title", "memo", "aiTableName"			// 5,6,7
		
		String sql =
				"UPDATE " + CONS.DB.tname_BM + " SET " + 
				
				CONS.DB.col_names_BM_full[5] + "='" + bm.getTitle() + "', " + 
				CONS.DB.col_names_BM_full[6] + "='" + bm.getMemo() + "' " +
				
				" WHERE " + CONS.DB.col_names_BM_full[0] + " = '"
				+ String.valueOf(bm.getDbId()) + "'";
		
		// Log
		String msg_Log = "sql = " + sql;
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
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
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString() + " / " + "sql: " + sql);
			
			wdb.close();
			
			return false;
			
		}//try
		
	}//updateData_BM_TitleAndMemo

	public boolean
	deleteData_AI(Activity actv, long db_id) {
		// TODO Auto-generated method stub
		SQLiteDatabase wdb = this.getWritableDatabase();
		
//		boolean result = DBUtils.isInDB_long_ai(db, tableName, db_id);
		boolean result = this.isInDB_AI(wdb, db_id);
		
		if (result == false) {		// Result is false ==> Meaning the target data doesn't exist
											//							in db; Hence, not executing delete op
			
			// debug
			Toast.makeText(actv, 
					"Data doesn't exist in db: " + String.valueOf(db_id), 
					Toast.LENGTH_LONG).show();
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"Data doesn't exist in db => " + String.valueOf(db_id));
			
			return false;
			
		} else {//if (result == false)
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", 
					"Data exists in db" + String.valueOf(db_id) + ")");
			
		}//if (result == false)
		

		/***************************************
		 * Delete data
		 ***************************************/
		String sql = 
						"DELETE FROM " + CONS.DB.tname_CM7
						+ " WHERE "
//						+ CONS.DB.col_names_BM_full[0] + " = '"
						+ CONS.DB.col_names_CM7_full[0] + " = '"
						+ String.valueOf(db_id) + "'";
		
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
		
	}//deleteData_AI(Activity actv, long db_id)

	private boolean 
	isInDB_AI(SQLiteDatabase wdb, long db_id) {
		// TODO Auto-generated method stub
		String sql = "SELECT COUNT(*) FROM "
				+ CONS.DB.tname_CM7
				+ " WHERE "
				+ CONS.DB.col_names_CM7_full[0] + " = '"
				+ String.valueOf(db_id) + "'";

	//	long result = DatabaseUtils.longForQuery(db, sql, null);
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
	
	}//isInDB_AI(SQLiteDatabase wdb, long db_id)

	public static boolean 
	updateData_AI_All
	(Activity actv, long db_id, AI ai) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();

		String sql = DBUtils._updateData_AI_All__GetSql(actv, db_id, ai);
		
		// Log
		String msg_Log = "sql => " + sql;
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		try {
			
			wdb.execSQL(sql);
			
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "sql => Done");
			
			//Methods.toastAndLog(actv, "Data updated", 2000);
			
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
		
	}//updateData_AI_All

	private static String 
	_updateData_AI_All__GetSql
	(Activity actv, long db_id, AI ai) {
		// TODO Auto-generated method stub
//		android.provider.BaseColumns._ID,	// 0
//		"created_at", "modified_at",		// 1, 2
//		"file_name", "file_path",			// 3, 4
//		"title", "memo",					// 5, 6
//		"last_played_at",					// 7
//		"table_name",						// 8
//		"length",							// 9
//		"audio_created_at"					// 10

		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE " + CONS.DB.tname_CM7 + " SET ");
		
		// modified_at
		sb.append(CONS.DB.col_names_CM7_full[2] 
					+ "='" + Methods.conv_MillSec_to_TimeLabel(
								Methods.getMillSeconds_now())
					+ "'");
		
		sb.append(", ");
		
		// file_name
		sb.append(CONS.DB.col_names_CM7_full[3] 
					+ "='" + ai.getFile_name()
					+ "'");
		
		sb.append(", ");
		
		// file_path
		sb.append(CONS.DB.col_names_CM7_full[4] 
				+ "='" + ai.getFile_path()
				+ "'");
		
		sb.append(", ");
		
		// title
		sb.append(CONS.DB.col_names_CM7_full[5] 
				+ "='" + ai.getTitle()
				+ "'");
		
		sb.append(", ");
		
		// memo
		sb.append(CONS.DB.col_names_CM7_full[6] 
				+ "='" + ai.getMemo()
				+ "'");
		
		// table name
		sb.append(", ");
		
		sb.append(CONS.DB.col_names_CM7_full[8] 
				+ "='" + ai.getTable_name()
				+ "'");
		
		sb.append(" WHERE " 
					+ android.provider.BaseColumns._ID
					+ "='" + db_id + "'");
		
		return sb.toString();

//		return sql;
		
	}//_updateData_AI_All__GetSql

	/******************************
		@return false => 1. File doesn't exist
	 ******************************/
	public static boolean
	delete_AudioFile
	(Activity actv, AI ai) {
		// TODO Auto-generated method stub
		// Log
		String msg_Log = "external path => " 
					+ android.os.Environment.getExternalStorageDirectory().getPath();
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// get: file

		////////////////////////////////
		File audio_File = new File(ai.getFile_path(), ai.getFile_name());
		
		/******************************
			validate
		 ******************************/
		if (!audio_File.exists()) {
			
			// Log
			msg_Log = "File doesn't exist: " + audio_File.getAbsolutePath();
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return false;
			
			
		}
		
		////////////////////////////////

		// delete

		////////////////////////////////
		//REF http://stackoverflow.com/questions/1248292/how-to-delete-a-file-from-sd-card answered Aug 10 '09 at 9:14
		boolean res = audio_File.delete();
		
		if (res == true) {
			
			// Log
			msg_Log = "File => deleted: " + audio_File.getAbsolutePath();
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} else {

			// Log
			msg_Log = "File => can't be deleted: " 
							+ audio_File.getAbsolutePath();
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
//		// Log
//		String msg_Log = "external path => " 
//					+ android.os.Environment.getExternalStorageDirectory().getPath();
//		Log.d("DBUtils.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		return res;
		
	}//delete_AudioFile

	/******************************
		@return -1 => Table doesn't exist<br>
	 ******************************/
	public static int 
	insert_Data_Patterns
	(Activity actv, List<String> patterns_List) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		////////////////////////////////
	
		// validate: table exists
	
		////////////////////////////////
		String tname = CONS.DB.tname_MemoPatterns;
		
		if (!DBUtils.tableExists(
					actv, CONS.DB.dbName, tname)) {
			// Log
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist => " + tname);
			
			return -1;
			
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		////////////////////////////////
	
		// Iteration
	
		////////////////////////////////
		int counter = 0;
		
		ContentValues val = null;
	//	
		for (String pattern : patterns_List) {
			
			////////////////////////////////
			
			// prep: content values
			
			////////////////////////////////
			val = _insert_Data_Patterns__ContentValues(pattern);
			
			try {
				// Start transaction
				wdb.beginTransaction();
				
				// Insert data
				long res = wdb.insert(tname, null, val);
	//			long res = wdb.insert(CONS.DB.tname_RefreshLog, null, val);
			
				if (res == -1) {
					
					// Log
					String msg_Log = "insertion => failed: " + pattern;
					Log.e("DBUtils.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", msg_Log);
	
				} else {
					
					counter += 1;
					
					// Set as successful
					wdb.setTransactionSuccessful();
					
				}
				
				// End transaction
				wdb.endTransaction();
				
			} catch (Exception e) {
				
				// Log
				// Log
				String msg_Log = String.format(
									"Exception(%s) => %s", 
									pattern, e.toString());
				Log.e("DBUtils.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);
				
			}//try
			
		}//for (String pattern : patterns_List)
	
		////////////////////////////////
	
		// close
	
		////////////////////////////////
		wdb.close();
	
		////////////////////////////////
	
		// return
	
		////////////////////////////////
		return counter;
		
	}//insert_Data_Patterns

	public static boolean 
	tableExists
	(Activity actv, String dbName, String tableName) {
		// The table exists?
		DBUtils dbu = new DBUtils(actv, dbName);
		
		//
		SQLiteDatabase rdb = dbu.getReadableDatabase();

		Cursor cursor = rdb.rawQuery(
				"SELECT * FROM sqlite_master WHERE tbl_name = '" + 
						tableName + "'", null);
		
		actv.startManagingCursor(cursor);
//		actv.startManagingCursor(cursor);
		
		// Judge
		if (cursor.getCount() > 0) {
		
			rdb.close();
			return true;
			
		} else {//if (cursor.getCount() > 0)
			
			rdb.close();
			return false;
			
		}//if (cursor.getCount() > 0)
		
	}//public boolean tableExists(String tableName)

	private static ContentValues 
	_insert_Data_Patterns__ContentValues
	(String pattern) {
		// TODO Auto-generated method stub
		ContentValues val = new ContentValues();
		
//		android.provider.BaseColumns._ID,		// 0
//		"created_at", "modified_at",			// 1,2
//		"word",									// 3
		
		val.put(
				"created_at", 
				Methods.conv_MillSec_to_TimeLabel(
								Methods.getMillSeconds_now()));
		
		val.put(
				"modified_at", 
				Methods.conv_MillSec_to_TimeLabel(
						Methods.getMillSeconds_now()));
		
		val.put("word", pattern);
		
//		val.put("table_name", CONS.DB.tname_IFM11);

		return val;
		
	}//_insert_Data_Patterns__ContentValues

	/******************************
		@return
			-1	Table doesn't exist<br>
			-2	Deletion => failed<br>
			> 1	Deletion => done<br>
	 ******************************/
	public static int 
	delete_Pattern
	(Activity actv, WordPattern wp) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		////////////////////////////////
		
		// validate: table exists
		
		////////////////////////////////
		String tname = CONS.DB.tname_MemoPatterns;
				
		if (!DBUtils.tableExists(actv, CONS.DB.dbName, tname)) {
			// Log
			Log.i("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist => " + tname);
			
			wdb.close();
			
			return -1;
			
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		////////////////////////////////
		
		// delete
		
		////////////////////////////////
		////////////////////////////////
		
		// Query
		
		////////////////////////////////
		String where = CONS.DB.col_names_MemoPatterns_full[0] + " = ?";
	//	String where = CONS.DB.col_names_IFM11[1] + " = ?";
		
		String[] args = new String[]{
				
				String.valueOf(wp.getDb_Id())
				
		};
		
		int res_int = wdb.delete(tname, where, args);
		
		// Log
		String msg_Log = "res_int => " + res_int;
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////
		
		// report
		
		////////////////////////////////
		switch(res_int) {
		
		case 0:
			
			// Log
			msg_Log = "deletion => failed";
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			wdb.close();
			
			return -2;
			
		default:
			
			wdb.close();
			
			return res_int;
			
		}
		
	}//delete_Memo

	/******************************
		@return
			-1	insertion => failed<br>
			-2	Exception<br>
			-3	pattern already in DB<br>
			1	Inserted<br>
	 ******************************/
	public static int 
	save_Pattern
	(Activity actv, String new_Word) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		////////////////////////////////
	
		// validate: exists?
	
		////////////////////////////////
		int res_i = DBUtils.isInDB_Pattern(wdb, new_Word);
		
		if (res_i == 1) {
			
			// Log
			String msg_Log = "pattern already in DB => " + new_Word;
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			wdb.close();
			
			return -3;
			
		}
		
		////////////////////////////////
		
		// prep: content values
		
		////////////////////////////////
		ContentValues val = _build_Values__Pattern(actv, new_Word);
		
		try {
			// Start transaction
			wdb.beginTransaction();
			
			// Insert data
			long res = wdb.insert(CONS.DB.tname_MemoPatterns, null, val);
	//		long res = wdb.insert(CONS.DB.tname_RefreshLog, null, val);
			
			if (res == -1) {
				
				// Log
				String msg_Log = "insertion => failed";
				Log.e("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				wdb.endTransaction();
				wdb.close();
				
				return -1;
				
			} else {
				
				// Log
				String msg_Log = "insertion => done";
				Log.d("DBUtils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			}
			
			// Set as successful
			wdb.setTransactionSuccessful();
			
			// End transaction
			wdb.endTransaction();
			
	//		// Log
	//		Log.d("DBUtils.java" + "["
	//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
	//				+ "]", "Data inserted => " + "(" + columnNames[0] + " => " + values[0] + "), and others");
			
			wdb.close();
			
			return 1;
			
		} catch (Exception e) {
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception! => " + e.toString());
			
			wdb.close();
			
			return -2;
			
		}//try
		
	}//save_Pattern

	/******************************
		@return
			-1	Query exception<br>
			-2	Query => null<br>
			-3	No entry in the table<br>
			-4	Unknown result<br>
			1	Entry exists<br>
	 ******************************/
	public static int 
	isInDB_Pattern
	(SQLiteDatabase db, String word) {
		
		Cursor c = null;
		
		String where = CONS.DB.col_names_MemoPatterns[0] + " = ?";
		String[] args = new String[]{
				
							word
							
						};
		
		try {
			
			c = db.query(
					
					CONS.DB.tname_MemoPatterns,			// 1
					CONS.DB.col_names_MemoPatterns,	// 2
	//				null, null,		// 3,4
					where, args,		// 3,4
					null, null,		// 5,6
					null,			// 7
					null);
			
		} catch (Exception e) {
	
			// Log
			String msg_Log = "Exception";
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
	//		String msg = "Query exception";
	//		Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			return -1;
			
		}//try
		
		/***************************************
		 * Validate
		 * 	Cursor => Null?
		 * 	Entry => 0?
		 ***************************************/
		if (c == null) {
			
			String msg = "Query => null";
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", msg);
	
			return -2;
			
		} else if (c.getCount() < 1) {//if (c == null)
			
			String msg = "No entry in the table";
			
			// Log
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", msg);
	
			return -3;
			
		} else if (c.getCount() >= 1) {//if (c == null)
			
			// Log
			String msg_Log = "Entry exists => " + c.getCount();
			Log.d("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return 1;
			
		} else {//if (c == null)
			
			// Log
			String msg_Log = "Unknown result";
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return -4;
			
		}//if (c == null)
		
	//	String sql = "SELECT * FROM " + CONS.DB.tname_Patterns
	//			+ " WHERE " + CONS.DB.col_names_Patterns[0] + " = "
	//			+ "'" + word + "'";
	//	
	//	// Log
	//	String msg_Log = "sql => " + sql;
	//	Log.d("DBUtils.java" + "["
	//			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
	//			+ "]", msg_Log);
	//	
	//	long result = DatabaseUtils.longForQuery(db, sql, null);
	//	
	//	// Log
	//	Log.d("DBUtils.java" + "["
	//			+ Thread.currentThread().getStackTrace()[2].getLineNumber()
	//			+ "]", "result => " + String.valueOf(result));
	//	
	//	if (result > 0) {
	//		
	//		return true;
	//		
	//	} else {//if (result > 0)
	//		
	//		return false;
	//		
	//	}//if (result > 0)
		
	//	return false;
		
	}//public static boolean isInDB_long_ai

	private static ContentValues 
	_build_Values__Pattern
	(Activity actv, String word) {
		// TODO Auto-generated method stub
		ContentValues val = new ContentValues();
		
		
		val.put("created_at",
				Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
		val.put("modified_at",
				Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()));
		
		val.put("word", word);
		
		return val;
		
	}//_build_Values__TI

	/******************************
		@return
		number of items updated
	 ******************************/
	public static int 
	update_TI_All__TableName
	(Activity actv, List<AI> toMoveFiles) {
		// TODO Auto-generated method stub

		////////////////////////////////

		// vars

		////////////////////////////////
		int counter = 0;
		
		////////////////////////////////

		// setup db

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();

		String sql = null;
		
		ContentValues val = null;
		
		////////////////////////////////

		// update

		////////////////////////////////
		for (AI ti : toMoveFiles) {
			
			val = DBUtils.get_ContentValues__TI_TableName(actv, ti.getTable_name());
			
			String where = CONS.DB.col_names_CM7_full[0]
							+ " = ?";
			
			String[] args = new String[]{String.valueOf(ti.getDb_id())};
			
			try {
				// Start transaction
				wdb.beginTransaction();
				
				// Insert data
				long res = wdb.update(CONS.DB.tname_CM7, val, where, args);
//				long res = wdb.insert(CONS.DB.tname_RefreshLog, null, val);
				
				if (res < 1) {
//					if (res == -1) {
					
					// Log
					String msg_Log = String.format(
										"insertion => failed: file name = %s" +
										" (result = %d)"
										, ti.getFile_name(), res);

					// Log
					Log.d("DBUtils.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", msg_Log);
					
					wdb.endTransaction();
			
					continue;
					
				} else {
					
					// Log
					String msg_Log = String.format(
									"insertion => done (file name = %s)"
									, ti.getFile_name());
					Log.d("DBUtils.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", msg_Log);
					
				}
				
				// Set as successful
				wdb.setTransactionSuccessful();
				
				// End transaction
				wdb.endTransaction();
				
				// count
				counter += 1;
				
			} catch (Exception e) {
				
				String msg_Log = String.format(
							"Exception (%s) => " + e.toString(), 
							ti.getFile_name());
				// Log
				Log.e("DBUtils.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);

				continue;
				
			}//try					
			
		}//for (TI ti : toMoveFiles)

		////////////////////////////////

		// close db

		////////////////////////////////
		wdb.close();
		
		
		return counter;
		
	}//update_TI_All__TableName

	private static ContentValues 
	get_ContentValues__TI_TableName
	(Activity actv, String tableName) {
		// TODO Auto-generated method stub
		ContentValues val = new ContentValues();
		
//		android.provider.BaseColumns._ID,	// 0
//		"created_at", "modified_at",		// 1, 2
//		"file_name", "file_path",			// 3, 4
//		"title", "memo",					// 5, 6
//		"last_played_at",					// 7
//		"table_name",						// 8
//		"length",							// 9
//		"audio_created_at"					// 10		
		
		val.put(
				CONS.DB.col_names_CM7_full[2],		// modified_at 
				Methods.conv_MillSec_to_TimeLabel(
						Methods.getMillSeconds_now()));
		
		val.put(
				CONS.DB.col_names_CM7_full[8],		// memos
				tableName);
		
		return val;
		
	}//_insert_Data_Patterns__ContentValues

	public static List<AI> 
	find_All_AI__Search
	(Activity actv) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// validate: DB file exists?

		////////////////////////////////
		File dpath_DBFile = actv.getDatabasePath(CONS.DB.dbName);

		if (!dpath_DBFile.exists()) {
			
			String msg = "No DB file: " + CONS.DB.dbName;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return null;
			
		}
		
		////////////////////////////////

		// DB

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		////////////////////////////////

		// iteration: searched AI ids

		////////////////////////////////
		AI ti = null;
		
		List<AI> ai_List = new ArrayList<AI>();
		
		for (Long id : CONS.ALActv.searchedItems) {
			
			////////////////////////////////

			// get: AI

			////////////////////////////////
			ti = DBUtils.find_AI_ById(actv, id.longValue());
//			ti = DBUtils.get_AI_From_DbId(actv, id.longValue());
			
			ai_List.add(ti);
			
		}//for (Long id : CONS.TNActv.searchedItems)

		////////////////////////////////

		// close: db

		////////////////////////////////
		rdb.close();		
		
		////////////////////////////////

		// return

		////////////////////////////////
		return ai_List;
		
	}//find_All_TI__Search

	public static int 
	save_BMStores
	(Activity actv, List<BMStore> list_bmStores) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// DB

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		BMStore bms = null;
		
		int counter = 0;
		boolean res = false;

		for (int i = 0; i < list_bmStores.size(); i++) {
			
			bms = list_bmStores.get(i);

			res = DBUtils.insertData_BMStore(actv, wdb, bms);
			
			if (res == true) {
				
				counter ++;
				res = false;
				
			}
			
		}//for (int i = 0; i < list_bmStores.size(); i++)

		///////////////////////////////////
		//
		// close
		//
		///////////////////////////////////
		wdb.close();
		
		return counter;
		
	}//save_BMStores

	/******************************
		@param aiFileName 
	 * @return
			-1	Table doesn't exist<br>
			-2	Deletion => failed<br>
			1	Deletion => done<br>
	 ******************************/
	public static int 
	delete_BMStores__AIFileName(Activity actv, String aiFileName) {
		// TODO Auto-generated method stub
		
		String dbName = CONS.DB.dbName;
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		////////////////////////////////
		
		// validate: table exists
		
		////////////////////////////////
		String tname = CONS.DB.tname_BMStore;
		
//		String dbName = CONS.DB.dbName;
				
		if (!DBUtils.tableExists(actv, dbName, tname)) {
			// Log
			Log.i("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist => " + tname);
			
			wdb.close();
			
			return -1;
			
		}//if (!tableExists(SQLiteDatabase db, String tableName))
		
		////////////////////////////////
		
		// delete
		
		////////////////////////////////
		////////////////////////////////
		
		// Query
		
		////////////////////////////////
//		"ai_name", "position",				// 0 1
//		"title", "memo",					// 2 3
//		"orig_created_at", "orig_modified_at",	// 4 5
		String where = CONS.DB.col_names_BMStore[0] + " = ?";
		
		String[] args = new String[]{
				
				aiFileName
				
		};
		
		int res_int = wdb.delete(tname, where, args);
		
		// Log
		String msg_Log = "res_int => " + res_int;
		Log.d("DBUtils.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////
		
		// report
		
		////////////////////////////////
		switch(res_int) {
		
		case 0:
			
			// Log
			msg_Log = "deletion => failed";
			Log.e("DBUtils.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			wdb.close();
			
			return -2;
			
		default:
			
			wdb.close();
			
			return res_int;
			
		}

	}//delete_BMs

	public static int 
	save_BMs(Activity actv, List<BM> list_BMs) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// DB

		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		BM bm = null;
		
		int counter = 0;
		int res_i;

		for (int i = 0; i < list_BMs.size(); i++) {
			
			bm = list_BMs.get(i);

			res_i = DBUtils.insertData_BM_static(actv, bm);
			
			if (res_i == 1) {
				
				counter ++;
				
			}
			
		}//for (int i = 0; i < list_bmStores.size(); i++)

		///////////////////////////////////
		//
		// close
		//
		///////////////////////////////////
		wdb.close();
		
		return counter;

	}//save_BMs(Activity actv, List<BM> list_BMs)


}//public class DBUtils



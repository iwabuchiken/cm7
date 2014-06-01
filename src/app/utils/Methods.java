package app.utils;


import cm7.main.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;
import app.items.AI;
import app.items.Refresh;
import app.listeners.dialog.DL;

// Apache


// REF=> http://commons.apache.org/net/download_net.cgi
//REF=> http://www.searchman.info/tips/2640.html

//import org.apache.commons.net.ftp.FTPReply;

public class Methods {

	static int counter;		// Used => sortFileList()
	
	
	/****************************************
	 * Vars
	 ****************************************/
	public static final int vibLength_click = 35;

	static int tempRecordNum = 20;

	/****************************************
	 * Methods
	 ****************************************/
	public static void sort_list_files(File[] files) {
		// REF=> http://android-coding.blogspot.jp/2011/10/sort-file-list-in-order-by-implementing.html
		/****************************
		 * 1. Prep => Comparator
		 * 2. Sort
			****************************/
		
		/****************************
		 * 1. Prep => Comparator
			****************************/
		Comparator<? super File> filecomparator = new Comparator<File>(){
			
			public int compare(File file1, File file2) {
				/****************************
				 * 1. Prep => Directory
				 * 2. Calculate
				 * 3. Return
					****************************/
				
				int pad1=0;
				int pad2=0;
				
				if(file1.isDirectory())pad1=-65536;
				if(file2.isDirectory())pad2=-65536;
				
				int res = pad2-pad1+file1.getName().compareToIgnoreCase(file2.getName());
				
				return res;
			} 
		 };//Comparator<? super File> filecomparator = new Comparator<File>()
		 
		/****************************
		 * 2. Sort
			****************************/
		Arrays.sort(files, filecomparator);

	}//public static void sort_list_files(File[] files)

	public static boolean
	clearPref (Activity actv,String prefName) {

		SharedPreferences prefs = 
						actv.getSharedPreferences(
								prefName,
								actv.MODE_PRIVATE);
		
		/****************************
		* 2. Get editor
		****************************/
		SharedPreferences.Editor editor = prefs.edit();
		
		/****************************
		* 3. Clear
		****************************/
		try {
		
			editor.clear();
			editor.commit();
			
			// Log
			Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Prefs cleared");
			
			return true;
		
		} catch (Exception e) {
		
			// Log
			Log.e("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Excption: " + e.toString());
			
			return false;
		}

	}//public static boolean clear_prefs_current_path(Activity actv, Strin newPath)

	
	public static void confirm_quit(Activity actv, int keyCode) {
		
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			
			AlertDialog.Builder dialog=new AlertDialog.Builder(actv);
			
			dialog.setTitle(actv.getString(R.string.generic_tv_confirm));
	        dialog.setMessage(actv.getString(R.string.generic_message_quit));
	        
	        dialog.setPositiveButton(
	        				actv.getString(R.string.generic_bt_quit),
	        				new DL(actv, dialog, 0));
	        
	        dialog.setNegativeButton(
	        				actv.getString(R.string.generic_bt_cancel),
	        				new DL(actv, dialog, 1));
	        
	        dialog.create();
	        dialog.show();
			
		}//if (keyCode==KeyEvent.KEYCODE_BACK)
		
	}//public static void confirm_quit(Activity actv, int keyCode)

	/****************************
	 * deleteDirectory(File target)()
	 * 
	 * 1. REF=> http://www.rgagnon.com/javadetails/java-0483.html
		****************************/
	public static boolean deleteDirectory(File target) {
		
		if(target.exists()) {
			//
			File[] files = target.listFiles();
			
			//
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					
					deleteDirectory(files[i]);
					
				} else {//if (files[i].isDirectory())
					
					String path = files[i].getAbsolutePath();
					
					files[i].delete();
					
					// Log
					Log.d("Methods.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", "Removed => " + path);
					
					
				}//if (files[i].isDirectory())
				
			}//for (int i = 0; i < files.length; i++)
			
		}//if(target.exists())
		
		return (target.delete());
	}//public static boolean deleteDirectory(File target)

	public static long getMillSeconds(int year, int month, int date) {
		// Calendar
		Calendar cal = Calendar.getInstance();
		
		// Set time
		cal.set(year, month, date);
		
		// Date
		Date d = cal.getTime();
		
		return d.getTime();
		
	}//private long getMillSeconds(int year, int month, int date)

	/****************************************
	 *	getMillSeconds_now()
	 * 
	 * <Caller> 
	 * 1. ButtonOnClickListener # case main_bt_start
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static long getMillSeconds_now() {
		
		Calendar cal = Calendar.getInstance();
		
		return cal.getTime().getTime();
		
	}//private long getMillSeconds_now(int year, int month, int date)

	public static String get_TimeLabel(long millSec) {
		
		 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
		 
		return sdf1.format(new Date(millSec));
		
	}//public static String get_TimeLabel(long millSec)

	/*********************************
	 * <Notes>
	 * 1. File names => Sorted alphabetico-ascendantly
	 * 
	 * @return
	 * null		=> 1. File "dpath" doesn't exist<br>
	 * 				2. listFiles() => null returned<br>
	 *********************************/
	public static List<String> get_FileList(File dpath) {
		/*********************************
		 * 1. Directory exists?
		 * 2. Build list
		 * 2-1. Sort list
		 * 
		 * 3. Return
		 *********************************/
		////////////////////////////////

		// Directory exists?

		////////////////////////////////
				
		if (!dpath.exists()) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Dir doesn't exist");
			
			return null;
			
		} else {//if (!dpath.exists() == condition)
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Dir exists: " + dpath.getAbsolutePath());
			
		}//if (!dpath.exists() == condition)

		////////////////////////////////

		// Get: File list

		////////////////////////////////
		
		List<String> list_Dir = new ArrayList<String>();
		
		File[] files_list = dpath.listFiles();
		
		if (files_list == null) {
			
			// Log
			String msg_log = "listFiles() => returned null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return null;
			
		}

		////////////////////////////////

		// Sort list

		////////////////////////////////
		
		Methods.sort_list_files(files_list);
		
		for (File f : files_list) {
			
			list_Dir.add(f.getName());
			
		}//for (File f : files_list)
		
		/*********************************
		 * 3. Return
		 *********************************/
		return list_Dir;
		
	}//public static List<String> get_file_list(File dpath)

	public static String convert_intSec2Digits(int t) {
		
		int sec = t % 60;
		
		if (t / 60 < 1) {
			
//			return "00:00:" + String.valueOf(sec);
			return "00:00:" + Methods.convert_sec2digits(sec, 2);
			
		}//if (t / 60 < 1)
		
//		int min = (t - sec) % 60;
		int min = ((t - sec) % (60 * 60)) / 60;
		
		if ((t - sec) / (60 * 60) < 1) {
			
//			return "00:" + String.valueOf(min) + ":" + String.valueOf(sec);
			return "00:"
				+ Methods.convert_sec2digits(min, 2) + ":"
				+ Methods.convert_sec2digits(sec, 2);
			
		}//if (variable == condition)
		
//		int hour = (t - min) / 60;
		int hour = (t - sec) / (60 * 60);
				
//		return String.valueOf(hour) + ":"
//				+ String.valueOf(min) + ":"
//				+ String.valueOf(sec);

		return Methods.convert_sec2digits(min, 2) + ":"
		+ Methods.convert_sec2digits(min, 2) + ":"
		+ Methods.convert_sec2digits(sec, 2);

		
	}//public static String convert_intSec2Digits(int time)

	/***************************************
	 * 20130320_120437<br>
	 * @param t ... Value in seconds, <i>not</i> in mill seconds
	 ***************************************/
	public static String convert_intSec2Digits_lessThanHour(int t) {
		
		int sec = t % 60;
		
		if (t / 60 < 1) {
			
//			return "00:00:" + String.valueOf(sec);
//			return "00:00:" + Methods.convert_sec2digits(sec, 2);
			return "00:" + Methods.convert_sec2digits(sec, 2);
			
		}//if (t / 60 < 1)
		
//		int min = (t - sec) % 60;
		int min = ((t - sec) % (60 * 60)) / 60;
		
		return Methods.convert_sec2digits(min, 2) + ":"
			+ Methods.convert_sec2digits(sec, 2);
			
	}//public static String convert_intSec2Digits(int time)

	private static String convert_sec2digits(int sec, int i) {
		
		int current_len = String.valueOf(sec).length();
		
		if (current_len < i) {
			
			StringBuilder sb = new StringBuilder();
			
			for (int j = 0; j < i - current_len; j++) {
				
				sb.append("0");
			}
			
			sb.append(String.valueOf(sec));
			
			return sb.toString();
			
		}//if (current_len == condition)
		
		return String.valueOf(sec);
		
	}//private static String convert_sec2digits(int sec, int i)

	public static int getArrayIndex(String[] targetArray, String targetString) {
		int index = -1;
		
		for (int i = 0; i < targetArray.length; i++) {
			
			if (targetArray[i].equals(targetString)) {
				
				index = i;
				
				break;
				
			}//if (targetArray[i] == )
			
		}//for (int i = 0; i < targetArray.length; i++)
		
		return index;
	}//public static int getArrayIndex(String[] targetArray, String targetString)

	/******************************
		@return true => pref set
	 ******************************/
	public static boolean set_Pref_Int
	(Activity actv, String pref_name, String pref_key, int value) {
		SharedPreferences prefs = 
				actv.getSharedPreferences(pref_name, Context.MODE_PRIVATE);

		/****************************
		 * 2. Get editor
			****************************/
		SharedPreferences.Editor editor = prefs.edit();

		/****************************
		 * 3. Set value
			****************************/
		editor.putInt(pref_key, value);
		
		try {
			editor.commit();
			
			return true;
			
		} catch (Exception e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Excption: " + e.toString());
			
			return false;
		}

	}//public static boolean set_pref(String pref_name, String value)

	public static int get_Pref_Int
	(Activity actv, String pref_name, String pref_key, int defValue) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(pref_name, Context.MODE_PRIVATE);

		/****************************
		 * Return
			****************************/
		return prefs.getInt(pref_key, defValue);

	}//public static boolean set_pref(String pref_name, String value)

	public static void exec_Sql(Activity actv) {
		// TODO Auto-generated method stub
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		boolean res = dbu.createTable(actv, 
							CONS.DB.tname_RefreshHistory, CONS.DB.col_names_RefreshHistory, 
							CONS.DB.col_types_RefreshHistory);
//		CONS.DB.col_types_RefresHistory);
//		CONS.DB.tname_CM7, CONS.DB.col_names_CM7, 
//		CONS.DB.col_types_CM7);
		
		// Log
		String msg_log = "Create table => done: " + CONS.DB.tname_CM7;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_log);
		
	}

	public static boolean db_Backup(Activity actv)
	{
		/****************************
		 * 1. Prep => File names
		 * 2. Prep => Files
		 * 2-2. Folder exists?
		 * 
		 * 2-3. Dst folder => Files within the limit?
		 * 3. Copy
			****************************/
		String time_label = Methods.get_TimeLabel(Methods.getMillSeconds_now());
		
		String db_Src = StringUtils.join(
					new String[]{
							actv.getDatabasePath(CONS.DB.dbName).getPath()},
//							CONS.fname_db},
					File.separator);
		
		String db_Dst_Folder = StringUtils.join(
					new String[]{
							CONS.DB.dPath_dbFile_backup,
							CONS.DB.fname_DB_Backup_Trunk},
//							CONS.dpath_db_backup,
//							CONS.fname_db_backup_trunk},
					File.separator);
		
		String db_Dst = db_Dst_Folder + "_"
				+ time_label
//				+ MainActv.fileName_db_backup_ext;
				+ CONS.DB.fname_DB_Backup_ext;
//		+ CONS.fname_db_backup_ext;
//				+ MainActv.fname_db_backup_trunk;

		// Log
		String msg_log = "db_Src = " + db_Src
						+ " / "
						+ "db_Dst = " + db_Dst;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_log);
		
		/****************************
		 * 2. Prep => Files
			****************************/
		File src = new File(db_Src);
		File dst = new File(db_Dst);
		
		/****************************
		 * 2-2. Folder exists?
			****************************/
		File db_Backup = new File(CONS.DB.dPath_dbFile_backup);
//		File db_backup = new File(CONS.dpath_db_backup);
		
		if (!db_Backup.exists()) {
			
			try {
				db_Backup.mkdir();
				
				// Log
				Log.d("Methods.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "Folder created: " + db_Backup.getAbsolutePath());
			} catch (Exception e) {
				
				// Log
				Log.e("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Create folder => Failed");
				
				return false;
				
			}
			
		} else {//if (!db_backup.exists())
			
			// Log
			Log.i("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Folder exists: ");
			
		}//if (!db_backup.exists())
		
		/*********************************
		 * 2-3. Dst folder => Files within the limit?
		 *********************************/
		File[] files_dst_folder = new File(CONS.DB.dPath_dbFile_backup).listFiles();
//		File[] files_dst_folder = new File(CONS.dpath_db_backup).listFiles();
		
		int num_of_files = files_dst_folder.length;
		
		// Log
		Log.i("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "num of backup files = " + num_of_files);
		
		/****************************
		 * 3. Copy
			****************************/
		try {
			FileChannel iChannel = new FileInputStream(src).getChannel();
			FileChannel oChannel = new FileOutputStream(dst).getChannel();
			iChannel.transferTo(0, iChannel.size(), oChannel);
			iChannel.close();
			oChannel.close();
			
			// Log
			Log.i("ThumbnailActivity.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "DB file copied");
			
			// debug
			Toast.makeText(actv, "DB backup => Done", Toast.LENGTH_LONG).show();

		} catch (FileNotFoundException e) {
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
			return false;
			
		} catch (IOException e) {
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
			return false;
			
		}//try

		return true;
		
	}//public static boolean db_backup(Activity actv)

	public static String
	conv_CurrentPath_to_TableName(String currentPath)
	{
		String full = currentPath;
//		String full = CONS.Paths.dpath_Storage_Sdcard + CONS.Paths.dname_Base;
		
		////////////////////////////////

		// Get: raw strings

		////////////////////////////////
		String head = CONS.Paths.dpath_Storage_Sdcard;
		
		int len = head.length();
		
		String target = full.substring(len + 1);

		// Log
		String msg_log = "full = " + full
						+ " // "
						+ "target = " + target;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_log);

		////////////////////////////////

		// Split: target

		////////////////////////////////
		// Log
		msg_log = "File.separator = " + File.separator;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_log);
		
		String[] tokens = target.split(File.separator);
		
		////////////////////////////////

		// Build: table name

		////////////////////////////////
		if (tokens == null) {
			
			// Log
			msg_log = "Split => returned null";
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return null;
			
		} else if (tokens.length == 1) {
			
			return target;
			
		} else {

			return StringUtils.join(tokens, CONS.DB.jointString_TableName);
			
		}
		
	}//conv_CurrentPath_to_TableName(String currentPath)

	public static int refresh_MainDB(Activity actv)
	{
		// TODO Auto-generated method stub
		/****************************
		 * Steps
		 * 1. Set up DB(writable)
		 * 2. Table exists?
		 * 2-1. If no, then create one
		 * 3. Execute query for image files

		 * 4. Insert data into db
		 * 5. Update table "refresh_log"
		 * 
		 * 9. Close db
		 * 10. Return
			****************************/
		/****************************
		 * 1. Set up DB(writable)
			****************************/
		//
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();

		/****************************
		 * 2. Table exists?
		 * 2-1. If no, then create one
		 * 		1. baseDirName
		 * 		2. backupTableName
			****************************/
		boolean res = _refresh_MainDB__Setup_Table(actv, wdb, dbu);
//		boolean res = refreshMainDB_1_set_up_table(wdb, dbu);

		if (res == false) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Can't  create table");
			
			wdb.close();
			
			return CONS.Retval.CantCreateTable;
			
		}//if (res == false)
		
		////////////////////////////////

		// Execute query for image files

		////////////////////////////////
		File[] audioFile_list_Filtered = _refresh_MainDB__GetFiles(actv, wdb, dbu);
//		Cursor c = refreshMainDB_2_exec_query(actv, wdb, dbu);

		
		/******************************
			Validate: Any new files?
		 ******************************/
		if (audioFile_list_Filtered.length < 1) {
			
			wdb.close();
			
			// Log
			String msg_log = "New files => none";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			// debug
			Toast.makeText(actv, msg_log, Toast.LENGTH_SHORT).show();
			
			return CONS.Retval.NoNewFiles;
			
		}
		
		////////////////////////////////

		// Insert data into db

		////////////////////////////////
		int numOfItemsAdded = _refresh_MainDB__InsertData(
						actv, wdb, dbu, audioFile_list_Filtered);
//		int numOfItemsAdded = refreshMainDB_3_insert_data(actv, wdb, dbu, c);
		
		// Log
		String msg_Log = "numOfItemsAdded = " + numOfItemsAdded;
		Log.i("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		/****************************
		 * 9. Close db
			****************************/
		wdb.close();
		
		////////////////////////////////

		// refresh history

		////////////////////////////////
		_refresh_MainDB__RefreshHistory(actv, numOfItemsAdded);
		
		/****************************
		 * 10. Return
			****************************/
		return numOfItemsAdded;
		
//		return -1;
				
	}//public static void refresh_MainDB(Activity actv)

	private static void
	_refresh_MainDB__RefreshHistory
	(Activity actv, int numOfItemsAdded) {
		// TODO Auto-generated method stub
		
		Refresh refresh = new Refresh.Builder()
						.setLast_refreshed(Methods.conv_MillSec_to_TimeLabel(Methods.getMillSeconds_now()))
						.setNum_ItemsAdded(numOfItemsAdded)
						.build();
		
		boolean res = DBUtils.insertData_Refresh(actv, refresh);
		
		
	}//_refresh_MainDB__RefreshHistory

	private static int
	_refresh_MainDB__InsertData
	(Activity actv, SQLiteDatabase wdb,
			DBUtils dbu, File[] audioFile_list)
	{
		int numOfItemsAdded = 0;
		
		boolean res;
		
		List<AI> ai_List = new ArrayList<AI>();
		
		for (File file : audioFile_list) {
			
			AI ai = new AI.Builder()
					.setFile_name(file.getName())
					
					//REF http://www.xinotes.net/notes/note/774/
					.setFile_path(file.getParent())
//					.setFile_path(file.getPath())
					
					.setTable_name(CONS.DB.tname_CM7)
					.setLength(Methods.conv_MillSec_to_ClockLabel(file.length()))
//					.setLength(file.length())
					.setAudio_created_at(
							Methods.conv_MillSec_to_TimeLabel(file.lastModified()))
					.build();
			
			//debug
			// Log
			String msg_Log = "file.lastModified() = " + file.lastModified()
						+ " // "
						+ "setAudio_created_at = "
						+ Methods.conv_MillSec_to_TimeLabel(file.lastModified());
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			// Log
			msg_Log = "ai.getAudio_created_at() = " + ai.getAudio_created_at();
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			res = DBUtils.insertData_AI(actv, ai);
			
			if (res == true) {
				
				numOfItemsAdded ++;
				
			}
//			ai_List.add(ai);
			
			
//			insertDataIntoDB(actv, CONS.dpath_base, c);
		}
		
		// Log
		String msg_Log = "ai_List.size() = " + ai_List.size();
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
//		int numOfItemsAdded = -1;
		
//		/****************************
//		 * 5. Update table "refresh_log"
//			****************************/
//		c.moveToPrevious();
//		
//		long lastItemDate = c.getLong(3);
//		
//		updateRefreshLog(actv, wdb, dbu, lastItemDate, numOfItemsAdded);
//		
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "c.getLong(3) => " + c.getLong(3));
		

		return numOfItemsAdded;
		
	}//_refresh_MainDB__InsertData
	

	private static File[]
	_refresh_MainDB__GetFiles
	(Activity actv, SQLiteDatabase wdb, DBUtils dbu)
	{
		////////////////////////////////

		// Refresh: Range?

		////////////////////////////////
		final int pastXDays = -10;

		////////////////////////////////

		// Get: file list

		////////////////////////////////
		String[] tokens = new String[]{
				
				CONS.Paths.dpath_Storage_Internal,
				CONS.DB.dname_TapeATalk_Sdcard
				
		};
		
		String dpath_TapeATalk_Internal = 
					StringUtils.join(tokens, File.separator);
		
		File dir_TapeATalk = new File(dpath_TapeATalk_Internal);
		
		File[] audioFile_list = dir_TapeATalk.listFiles();

		////////////////////////////////

		// Filter: Files created in the last 1 week

		////////////////////////////////
		File[] audioFile_list_Filtered = 
				dir_TapeATalk.listFiles(new FileFilter(){
			
						@Override
						public boolean accept(File file) {
							
							//REF http://stackoverflow.com/questions/4348525/get-date-as-of-4-hours-ago answered Dec 3 '10 at 18:19
							Calendar calendar = Calendar.getInstance();
							calendar.add(Calendar.DAY_OF_MONTH, pastXDays);
			//				calendar.add(Calendar.HOUR_OF_DAY, -4);
							Date date = calendar.getTime();
							
							long time_4DaysAgo = date.getTime();
							
							// TODO Auto-generated method stub
							return file.lastModified() > time_4DaysAgo;
							
						}
			
		});
		
		/******************************
			Validate: Filtering done?
		 ******************************/
		if (audioFile_list_Filtered == null) {
			
			// Log
			String msg_log = "Filtering => can't be done";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return null;
			
		}
		
		// Log
		String msg_log = "audioFile_list => " + audioFile_list.length
						+ " // "
						+ "audioFile_list_Filtered => "
						+ audioFile_list_Filtered.length;
		
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_log);
		
//		for (File file : audioFile_list_Filtered) {
//			
//			// Log
//			msg_log = "file = " + file.getAbsolutePath();
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_log);
//			
//		}
		
		return audioFile_list_Filtered;
		
	}//_refresh_MainDB__Exec_Query

	private static boolean
	_refresh_MainDB__Setup_Table
	(Activity actv, SQLiteDatabase wdb, app.utils.DBUtils dbu)
	{
		/****************************
		 * 2-1.1. baseDirName
			****************************/
		String tableName = CONS.DB.tname_CM7;
		
		boolean result = dbu.tableExists(wdb, tableName);
		
		// If the table doesn't exist, create one
		if (result == false) {

			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + tableName);
			
			result = 
					dbu.createTable(actv, 
							CONS.DB.tname_CM7, CONS.DB.col_names_CM7, 
							CONS.DB.col_types_CM7);
			
			if (result == false) {

				Log.e("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Can't create a table: "+ tableName);
				
				return false;
				
			} else {//if (result == false)
				
				Log.i("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Table created: "+ tableName);
				
				return true;
				
			}//if (result == false)

		} else {//if (result == false)
			
			Log.i("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: "+ tableName);

			return true;
			
		}//if (result == false)
		
	}//_refresh_MainDB__Setup_Table

	public static void create_Table(Activity actv, String tname) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		boolean res;
		
		////////////////////////////////

		// Dispatch

		////////////////////////////////
		if (tname.equals(CONS.DB.tname_CM7)) {
			
			res = dbu.createTable(actv, 
					CONS.DB.tname_CM7, CONS.DB.col_names_CM7, 
					CONS.DB.col_types_CM7);
			
			if (res == true) {
				
				// debug
				String msg_Toast = "Table => created: " + CONS.DB.tname_CM7;
				Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
				
				
			} else {

				// debug
				String msg_Toast = "Table => can't create: " + CONS.DB.tname_CM7;
				Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
				
			}
			
		} else if (tname.equals(CONS.DB.tname_RefreshHistory)) {
			
			res = dbu.createTable(actv, 
					CONS.DB.tname_RefreshHistory, CONS.DB.col_names_RefreshHistory, 
					CONS.DB.col_types_RefreshHistory);
			
			if (res == true) {
				
				// debug
				String msg_Toast = "Table => created: " + CONS.DB.tname_RefreshHistory;
				Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
				
				
			} else {

				// debug
				String msg_Toast = "Table => can't create: " 
								+ CONS.DB.tname_RefreshHistory;
				Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
				
			}
			
		} else {
			

		}//if (tname.equals(CONS.DB.tname_CM7))
		
//		res = dbu.createTable(actv, 
//							CONS.DB.tname_RefresHistory, CONS.DB.col_names_RefreshHistory, 
//							CONS.DB.col_types_RefreshHistory);
//		CONS.DB.col_types_RefresHistory);
//		CONS.DB.tname_CM7, CONS.DB.col_names_CM7, 
//		CONS.DB.col_types_CM7);
		
	}

	public static void drop_Table
	(Activity actv, String tname) {
		// TODO Auto-generated method stub

		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		boolean res = dbu.dropTable(actv, tname);
		
	}
	
	/******************************
		Format => yyyy/MM/dd hh:mm:ss.SSS
	 ******************************/
	public static String
	conv_MillSec_to_TimeLabel(long millSec)
	{
		//REF http://stackoverflow.com/questions/7953725/how-to-convert-milliseconds-to-date-format-in-android answered Oct 31 '11 at 12:59
		String dateFormat = CONS.Admin.format_Date;
//		String dateFormat = "yyyy/MM/dd hh:mm:ss.SSS";
		
		DateFormat formatter = new SimpleDateFormat(dateFormat);

		// Create a calendar object that will convert the date and time value in milliseconds to date. 
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTimeInMillis(millSec);
		
		return formatter.format(calendar.getTime());
		
	}//conv_MillSec_to_TimeLabel(long millSec)

	public static long
	conv_TimeLabel_to_MillSec(String timeLabel)
//	conv_MillSec_to_TimeLabel(long millSec)
	{
//		String input = "Sat Feb 17 2012";
		Date date;
		try {
			date = new SimpleDateFormat(
						CONS.Admin.format_Date, Locale.JAPAN).parse(timeLabel);
			
			return date.getTime();
//			long milliseconds = date.getTime();
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			// Log
			String msg_Log = "Exception: " + e.toString();
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return -1;
			
		}
		
//		Locale.ENGLISH).parse(input);
		
//		Date date = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH).parse(input);
//		long milliseconds = date.getTime();
		
	}//conv_TimeLabel_to_MillSec(String timeLabel)
	
	/******************************
		REF http://stackoverflow.com/questions/625433/how-to-convert-milliseconds-to-x-mins-x-seconds-in-java answered Mar 9 '09 at 10:01
	 ******************************/
	public static String
	conv_MillSec_to_ClockLabel(long millSec)
	{
		return String.format(
			Locale.JAPAN,
			CONS.Admin.format_Clock, 
//			"%02d:%02d", 
			TimeUnit.MILLISECONDS.toMinutes(millSec),
			TimeUnit.MILLISECONDS.toSeconds(millSec) - 
			TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millSec))
		);
		
	}//conv_MillSec_to_ClockLabel(long millSec)
	
	public static long
	conv_ClockLabel_to_MillSec(String clockLabel)
	{
		
		String[] tokens = clockLabel.split(":");
		
		/******************************
			Validate
		 ******************************/
		if (tokens == null || tokens.length != 2) {

			// Log
			String msg_Log = "Label format => unknown: " + clockLabel;
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return -1;
			
		}
		
		/******************************
			Build: long number
		 ******************************/
		long mill_Min = Integer.parseInt(tokens[0]) * (60 * 1000);
		long mill_Sec = Integer.parseInt(tokens[1]) * (1000);
		
		return mill_Min + mill_Sec;
		
//		SimpleDateFormat formatter = 
//				new SimpleDateFormat("mm:ss"); // I assume d-M, you may refer to M-d for month-day instead.
////		new SimpleDateFormat("d-M-yyyy hh:mm"); // I assume d-M, you may refer to M-d for month-day instead.
//		Date date;
//		try {
//			
//			date = formatter.parse(clockLabel);
//			return date.getTime();
//			
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			// Log
//			String msg_Log = "Exception: " + e.toString();
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			return -1;
//			
//		} // You will need try/catch around this
//		long millis = date.getTime();
		
	}//conv_ClockLabel_to_MillSec(String clockLabel)
	
}//public class Methods


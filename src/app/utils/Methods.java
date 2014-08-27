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
import android.app.ProgressDialog;
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
import app.items.BM;
import app.items.Refresh;
import app.listeners.MP_OCmpL;
import app.listeners.dialog.DL;
import app.main.ALActv;
import app.main.ImpActv;
import app.main.PlayActv;
import app.main.PrefActv;
import app.services.Service_ShowProgress;

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
	
	/******************************
		@return null => 1. dpath_Target ==> Dir doesn't exist<br>
						2. listFiles ==> returned null
	 ******************************/
	public static List<String> get_DirList(String dpath_Target) {
		/*********************************
		 * 1. Directory exists?
		 * 2. Build list
		 * 2-1. Sort list
		 * 
		 * 3. Return
		 *********************************/
		File dir_Target = new File(dpath_Target);
		
		////////////////////////////////
		
		// Directory exists?
		
		////////////////////////////////
		
		if (!dir_Target.exists()) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Dir doesn't exist");
			
			return null;
			
		} else {//if (!dpath.exists() == condition)
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Dir exists: " + dir_Target.getAbsolutePath());
			
		}//if (!dpath.exists() == condition)
		
		////////////////////////////////
		
		// Get: Dir list (Directories only)
		
		////////////////////////////////
		List<String> list_Dir = new ArrayList<String>();
		
		File[] files_list = dir_Target.listFiles(new FileFilter(){

			@Override
			public boolean accept(File f) {
				
				return f.isDirectory();
				
			}
			
		});
		
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
	
	public static List<String> 
	get_FileList_Sorted(File dpath) {
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
	
	/******************************
		@return true => pref set
	 ******************************/
	public static boolean 
	set_Pref_Boolean
	(Activity actv, 
		String pref_name, String pref_key, boolean value) {
		SharedPreferences prefs = 
				actv.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
		
		/****************************
		 * 2. Get editor
		 ****************************/
		SharedPreferences.Editor editor = prefs.edit();
		
		/****************************
		 * 3. Set value
		 ****************************/
		editor.putBoolean(pref_key, value);
		
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
		
	}//set_Pref_Boolean

	public static int get_Pref_Int
	(Activity actv, String pref_name, String pref_key, int defValue) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(pref_name, Context.MODE_PRIVATE);

		/****************************
		 * Return
			****************************/
		return prefs.getInt(pref_key, defValue);

	}//public static boolean set_pref(String pref_name, String value)
	
	public static boolean
	get_Pref_Boolean
	(Activity actv, 
		String pref_name, String pref_key, boolean defValue) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
		
		/****************************
		 * Return
		 ****************************/
		return prefs.getBoolean(pref_key, defValue);
		
	}//get_Pref_Boolean
	
	public static long getPref_Long
	(Activity actv, String pref_name, String pref_key, long defValue) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
		
		/****************************
		 * Return
		 ****************************/
		return prefs.getLong(pref_key, defValue);
		
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

//		// Log
//		String msg_log = "full = " + full
//						+ " // "
//						+ "target = " + target;
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_log);

		////////////////////////////////

		// Split: target

		////////////////////////////////
//		// Log
//		String msg_log = "File.separator = " + File.separator;
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_log);
		
		String[] tokens = target.split(File.separator);
		
		////////////////////////////////

		// Build: table name

		////////////////////////////////
		if (tokens == null) {
			
			// Log
			String msg_log = "Split => returned null";
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

//	public static int refresh_MainDB(Activity actv)
//	{
//		// TODO Auto-generated method stub
//		/****************************
//		 * Steps
//		 * 1. Set up DB(writable)
//		 * 2. Table exists?
//		 * 2-1. If no, then create one
//		 * 3. Execute query for image files
//
//		 * 4. Insert data into db
//		 * 5. Update table "refresh_log"
//		 * 
//		 * 9. Close db
//		 * 10. Return
//			****************************/
//		/****************************
//		 * 1. Set up DB(writable)
//			****************************/
//		//
//		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
//		
//		//
//		SQLiteDatabase wdb = dbu.getWritableDatabase();
//
//		/****************************
//		 * 2. Table exists?
//		 * 2-1. If no, then create one
//		 * 		1. baseDirName
//		 * 		2. backupTableName
//			****************************/
//		boolean res = _refresh_MainDB__Setup_Table(actv, wdb, dbu);
////		boolean res = refreshMainDB_1_set_up_table(wdb, dbu);
//		
//		if (res == false) {
//			
//			// Log
//			Log.e("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Can't  create table");
//			
//			wdb.close();
//			
//			return CONS.Retval.CantCreateTable;
//			
//		}//if (res == false)
//		
//		////////////////////////////////
//
//		// Execute query for image files
//
//		////////////////////////////////
//		File[] audioFile_list_Filtered = _refresh_MainDB__GetFiles(actv, wdb, dbu);
////		Cursor c = refreshMainDB_2_exec_query(actv, wdb, dbu);
//
//		
//		/******************************
//			Validate: Any new files?
//		 ******************************/
//		if (audioFile_list_Filtered.length < 1) {
//			
//			wdb.close();
//			
////			// Log
////			String msg_log = "New files => none";
////			Log.d("Methods.java" + "["
////					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
////					+ "]", msg_log);
////			
////			// debug
////			Toast.makeText(actv, msg_log, Toast.LENGTH_SHORT).show();
//			
//			return CONS.Retval.NoNewFiles;
//			
//		}
//		
//		////////////////////////////////
//
//		// Insert data into db
//
//		////////////////////////////////
//		int numOfItemsAdded = _refresh_MainDB__InsertData(
//						actv, wdb, dbu, audioFile_list_Filtered);
////		int numOfItemsAdded = refreshMainDB_3_insert_data(actv, wdb, dbu, c);
//		
//		// Log
//		String msg_Log = "numOfItemsAdded = " + numOfItemsAdded;
//		Log.i("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		/****************************
//		 * 9. Close db
//			****************************/
//		wdb.close();
//		
//		////////////////////////////////
//
//		// report
//
//		////////////////////////////////
////		// debug
////		String msg_Toast = "New items => " + numOfItemsAdded;
////		Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
//		
//		
//		////////////////////////////////
//
//		// refresh history
//
//		////////////////////////////////
//		_refresh_MainDB__RefreshHistory(actv, numOfItemsAdded);
//		
//		/****************************
//		 * 10. Return
//			****************************/
//		return numOfItemsAdded;
//		
////		return -1;
//				
//	}//public static void refresh_MainDB(Activity actv)

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

			////////////////////////////////

			// Get: File length (audio length)

			////////////////////////////////
			long audioLength = Methods.get_AudioLength(file.getAbsolutePath());
			
			////////////////////////////////

			// Build: AI

			////////////////////////////////
			AI ai = new AI.Builder()
					.setFile_name(file.getName())
					
					//REF http://www.xinotes.net/notes/note/774/
					.setFile_path(file.getParent())
//					.setFile_path(file.getPath())
					
					.setTable_name(CONS.DB.tname_CM7)
					.setLength(Methods.conv_MillSec_to_ClockLabel(audioLength))
//					.setLength(Methods.conv_MillSec_to_ClockLabel(file.length()))
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
				dir_TapeATalk.listFiles(new FFRefresh(actv, pastXDays));
//					dir_TapeATalk.listFiles(new FileFilter(){
			
//						@Override
//						public boolean accept(File file) {
//							
//							////////////////////////////////
//
//							// Get: history
//
//							////////////////////////////////
//							String last_history = DBUtils.get_Latest_Entry(
//											actv, CONS.DB.tname_RefreshHistory);
//							
//							//REF http://stackoverflow.com/questions/4348525/get-date-as-of-4-hours-ago answered Dec 3 '10 at 18:19
//							Calendar calendar = Calendar.getInstance();
//							calendar.add(Calendar.DAY_OF_MONTH, pastXDays);
//			//				calendar.add(Calendar.HOUR_OF_DAY, -4);
//							Date date = calendar.getTime();
//							
//							long time_4DaysAgo = date.getTime();
//							
//							// TODO Auto-generated method stub
//							return file.lastModified() > time_4DaysAgo;
//							
//						}
//			
//		});
		
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

	/******************************
		Setup a table for audio items
	 ******************************/
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
			
		} else if (tname.equals(CONS.DB.tname_BM)) {
			
			res = dbu.createTable(actv, 
					CONS.DB.tname_BM, CONS.DB.col_names_BM, 
					CONS.DB.col_types_BM);
			
			if (res == true) {
				
				// debug
				String msg_Toast = "Table => created: " + CONS.DB.tname_BM;
				Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
				
				
			} else {
				
				// debug
				String msg_Toast = "Table => can't create: " 
						+ CONS.DB.tname_BM;
				Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
				
			}
			
		} else if (tname.equals(CONS.DB.tname_MemoPatterns)) {
			
			res = dbu.createTable(actv, 
					CONS.DB.tname_MemoPatterns, CONS.DB.col_names_MemoPatterns, 
					CONS.DB.col_types_MemoPatterns);
			
			if (res == true) {
				
				// debug
				String msg_Toast = "Table => created: " + CONS.DB.tname_MemoPatterns;
				Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
				
				
			} else {
				
				// debug
				String msg_Toast = "Table => can't create: " 
						+ CONS.DB.tname_MemoPatterns;
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
	
	public static void 
	create_Table_Audio
	(Activity actv, String tname) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		boolean res;
		
		////////////////////////////////
		
		// Dispatch
		
		////////////////////////////////
		res = dbu.createTable(actv, 
				tname, CONS.DB.col_names_CM7, 
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
			
	}//create_Table_Audio

	public static boolean drop_Table
	(Activity actv, String tname) {
		// TODO Auto-generated method stub

		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		return dbu.dropTable(actv, tname);
		
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

	public static void
	start_Activity_ALActv
	(Activity actv, String currentPath) {

		Intent i = new Intent();
		
		i.setClass(actv, ALActv.class);
		
		i.putExtra(CONS.Intent.iKey_CurrentPath_MainActv, currentPath);
		
		i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		
		actv.startActivity(i);
		
	}//start_Activity_ALActv

	public static String get_Pref_String
	(Activity actv, String pref_name,
			String pref_key, String defValue) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(
						pref_name, Context.MODE_PRIVATE);

		/****************************
		 * Return
			****************************/
		return prefs.getString(pref_key, defValue);

	}//public static String get_Pref_String
	
	public static int get_Pref_String
	(Activity actv, String pref_name,
			String pref_key, int defValue) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(
						pref_name, Context.MODE_PRIVATE);
		
		/****************************
		 * Return
		 ****************************/
		return prefs.getInt(pref_key, defValue);
		
	}//public static String get_Pref_String
	
	public static void
	sort_List_ai_List
	(List<AI> ai_List, 
		final CONS.Enums.SortType sortType, 
		final CONS.Enums.SortOrder sortOrder) {
		
		Comparator_AI aiComp = new Comparator_AI(ai_List, sortType, sortOrder);
		
		Collections.sort(ai_List, aiComp);

	}//sort_List_ai_List
	
	public static void sort_List_BM_List
	(List<BM> bm_List, 
			final CONS.Enums.SortType sortType, 
			final CONS.Enums.SortOrder sortOrder) {
		
		Comp_BM bmComp = new Comp_BM(bm_List, sortType, sortOrder);
		
		Collections.sort(bm_List, bmComp);
		
	}//sort_List_ai_List

	public static void
	start_Activity_PlayActv(Activity actv, AI ai) {
		
		////////////////////////////////

		// Prep: infos

		////////////////////////////////
		String ai_FilePath_Full = StringUtils.join(
							new String[]{
								
								ai.getFile_path(),
								ai.getFile_name()
									
							},
							File.separator);
		
		Intent i = new Intent();
		
		i.setClass(actv, PlayActv.class);
		
		// Put extras
		i.putExtra(CONS.Intent.iKey_AI_FilePath_Full, ai_FilePath_Full);
		
		i.putExtra(CONS.Intent.iKey_AI_Db_Id, ai.getDb_id());
		
		i.putExtra(CONS.Intent.iKey_AI_TableName, ai.getTable_name());
		
		// Flags
		i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		
		// Start
		actv.startActivity(i);
		
	}//start_Activity_PlayActv(Activity actv, AI ai)

	public static void
	stop_Player(Activity actv) {
		// TODO Auto-generated method stub
		if (CONS.PlayActv.mp != null && CONS.PlayActv.mp.isPlaying()) {

			int player_Pos = CONS.PlayActv.mp.getCurrentPosition();
			
			CONS.PlayActv.mp.stop();
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Player => Stopped");
			
			/***************************************
			 * Stop: Service
			 ***************************************/
			Intent i = new Intent((Context) actv, Service_ShowProgress.class);

			//
//			i.putExtra("counter", timeLeft);

			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "Stopping service...");

			//
//			actv.startService(i);
			actv.stopService(i);

			////////////////////////////////

			// pref: stop position

			////////////////////////////////
			boolean res = Methods.get_Pref_Boolean(
					actv, 
					CONS.Pref.pname_MainActv, 
					actv.getString(R.string.prefactv_key_resume_position), 
					false);
			
			if (res == true) {
				
				res = Methods.setPref_Long(
							actv,
							CONS.Pref.pname_PlayActv,
	//						CONS.Pref.pkey_PlayActv_position,
							CONS.Pref.pkey_PlayActv_CurrentPosition,
	//						CONS.Pref.pkey_CurrentPosition,
							player_Pos);
				
				if (res == true) {
					
					// Log
					String msg_Log = "Position => set: " 
								+ Methods.conv_MillSec_to_ClockLabel(player_Pos);
					Log.d("Methods.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", msg_Log);
					
				} else {

					// Log
					String msg_Log = "Position => not set: " 
								+ Methods.conv_MillSec_to_ClockLabel(player_Pos);
					Log.d("Methods.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", msg_Log);
					
				}//if (res == true)
				
			}//if (res == true)

			
		} else if (CONS.PlayActv.mp == null) {//if (mp.isPlaying())

			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "CONS.PlayActv.mp != null");

		} else if (!CONS.PlayActv.mp.isPlaying()) {//if (mp.isPlaying())

			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "CONS.PlayActv.mp => Not playing");

		}//if (mp.isPlaying())	
		
	}//stop_Player(Activity actv)

	public static void
	play_File(Activity actv, AI ai) {
		// TODO Auto-generated method stub
		/*********************************
		 * 1. Media player is playing?
		 *********************************/
		if (CONS.PlayActv.mp != null && CONS.PlayActv.mp.isPlaying()) {

			CONS.PlayActv.mp.stop();
			
		}//if (mp.isPlaying())

//		CONS.PlayActv.mp.
		
		/*********************************
		 * 2. OnCompletionListener
		 *********************************/
//		CONS.PlayActv.mp = new MediaPlayer();
		
		CONS.PlayActv.mp.setOnCompletionListener(new MP_OCmpL(actv));

		/*********************************
		 * 3. Set data source
		 *********************************/
		CONS.PlayActv.mp.reset();
		
		// Log
		String msg_Log = "mp => reset()";
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		String file_full_path = StringUtils.join(
				new String[]{ai.getFile_path(), ai.getFile_name()},
				File.separator);

		// Log
		msg_Log = "file_full_path = " + file_full_path;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		try {

			CONS.PlayActv.mp.setDataSource(file_full_path);
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Data source => Set");
			
		} catch (IllegalArgumentException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
			
		} catch (IllegalStateException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());

		} catch (IOException e) {

			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());

		}//try

		/*********************************
		 * 4. Prepare mp
		 *********************************/
		try {

			CONS.PlayActv.mp.prepare();
			
		} catch (IllegalStateException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());

		} catch (IOException e) {
			
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());

		}//try

		//debug
		// Log
		msg_Log = "getDuration() = " + CONS.PlayActv.mp.getDuration();
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		
		/***************************************
		 * Position set in the preference?
		 ***************************************/
		long prefPosition = 
				Methods.getPref_Long(
						actv,
						CONS.Pref.pname_PlayActv,
						CONS.Pref.pkey_PlayActv_CurrentPosition,
						CONS.Pref.dflt_LongExtra_value);
		
		// Log
		msg_Log = "prefPosition = " + prefPosition;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		//debug
		// Log
		msg_Log = "getDuration() = " + CONS.PlayActv.mp.getDuration();
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		if (prefPosition >= 0) {
			
			CONS.PlayActv.mp.seekTo((int) prefPosition);
			
			// Log
			msg_Log = "seekTo() => done";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}//if (prefPosition == condition)
		
		/***************************************
		 * Prepare: Service
		 ***************************************/
		Intent i = new Intent((Context) actv, Service_ShowProgress.class);

		i.putExtra(
				CONS.Intent.iKey_PlayActv_TaskPeriod, 
				CONS.PlayActv.playActv_task_Period);
		
		//
//		i.putExtra("counter", timeLeft);
		
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "Starting service...");
		//
		actv.startService(i);

		
		/*********************************
		 * 5. Start
		 *********************************/
		CONS.PlayActv.mp.start();
		
		//debug
		// Log
		msg_Log = "getAudioSessionId() = " 
						+ CONS.PlayActv.mp.getAudioSessionId();
		
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}//play_File(Activity actv, AI ai)

	public static boolean
	setPref_Long
	(Activity actv, String pName, String pKey, long value) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(pName, Context.MODE_PRIVATE);

		/****************************
		 * 2. Get editor
			****************************/
		SharedPreferences.Editor editor = prefs.edit();

		/****************************
		 * 3. Set value
			****************************/
		editor.putLong(pKey, value);
		
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

	}//public static boolean setPref_long(Activity actv, String pref_name, String pref_key, long value)
	
	public static boolean
	set_Pref_String
	(Activity actv, String pName, String pKey, String value) {
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(pName, Context.MODE_PRIVATE);
		
		/****************************
		 * 2. Get editor
		 ****************************/
		SharedPreferences.Editor editor = prefs.edit();
		
		/****************************
		 * 3. Set value
		 ****************************/
		editor.putString(pKey, value);
		
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
		
	}//public static boolean setPref_long(Activity actv, String pref_name, String pref_key, long value)


	static long get_AudioLength(String fileFullPath) {
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Methods: " + Thread.currentThread().getStackTrace()[2].getMethodName());
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "File path=" + fileFullPath);
		
		MediaPlayer mp = new MediaPlayer();
		
//		int len = 0;
		long len = 0;
		
		try {
			mp.setDataSource(fileFullPath);
			
			mp.prepare();
			
//			len = mp.getDuration() / 1000;
			len = mp.getDuration();
			
			// REF=> http://stackoverflow.com/questions/9609479/android-mediaplayer-went-away-with-unhandled-events
			mp.reset();
			
			// REF=> http://stackoverflow.com/questions/3761305/android-mediaplayer-throwing-prepare-failed-status-0x1-on-2-1-works-on-2-2
			mp.release();
			
		} catch (IllegalArgumentException e) {
			
			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "Exception=" + e.toString());
			
		} catch (IllegalStateException e) {
			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "Exception=" + e.toString());

		} catch (IOException e) {
			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "Exception=" + e.toString());
		}//try
		
		return len;
	}//private static long getFileLength(String fileFullPath)

	public static void update_ProgressLable() {
		// TODO Auto-generated method stub
		int currentPosition = CONS.PlayActv.mp.getCurrentPosition();
		
//		TextView tvCurrentPosition = (TextView) this.findViewById(R.id.actv_play_tv_current_position);
		if (CONS.PlayActv.tvCurrentPosition == null) {
			
			// Log
			String msg_Log = "CONS.PlayActv.tvCurrentPosition => null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);

			CONS.PlayActv.tvCurrentPosition = 
					(TextView) (new PlayActv()).findViewById(R.id.actv_play_tv_current_position);
//			(TextView) this.findViewById(R.id.actv_play_tv_current_position);
			
		}//if (CONS.PlayActv.tvCurrentPosition == null)
//		CONS.PlayActv.tvCurrentPosition = (TextView) this.findViewById(R.id.actv_play_tv_current_position);
		
		CONS.PlayActv.tvCurrentPosition.setText(
					Methods.conv_MillSec_to_ClockLabel(currentPosition));
//		Methods.convert_intSec2Digits_lessThanHour((int)currentPosition / 1000));

	}

	public static void 
	delete_AI
	(Activity actv, Dialog dlg1, Dialog dlg2, AI ai, int alList_Position) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		boolean res = dbu.deleteData_AI(actv, ai.getDb_id());
		
		////////////////////////////////

		// Delete: from AI list

		////////////////////////////////
		if (res == true) {
			
//			CONS.BMActv.bmList.remove(bm);
			CONS.ALActv.list_AI.remove(ai);
			
			CONS.ALActv.adp_AIList.notifyDataSetChanged();
			
		} else {

			dlg2.dismiss();
			
			// debug
			String msg_Toast = "Can't remove AI from DB: " + ai.getFile_name();
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
			return;

		}

		////////////////////////////////

		// Delete: pref position

		////////////////////////////////
		int pref_Position = Methods.get_Pref_Int(
						actv, 
						CONS.Pref.pname_ALActv, 
						CONS.Pref.pkey_CurrentPosition_ALActv, 
						CONS.Pref.dflt_IntExtra_value);
		
		if (alList_Position == pref_Position) {
			
			res = Methods.set_Pref_Int(
					actv, 
					CONS.Pref.pname_ALActv, 
					CONS.Pref.pkey_CurrentPosition_ALActv, 
					CONS.Pref.dflt_IntExtra_value);
			
			// Log
			String msg_Log = "Pref: current position => set to default: "
							+ CONS.Pref.dflt_IntExtra_value;
			
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		////////////////////////////////

		// delete: BM

		////////////////////////////////
		_delete_AI__Delete_BM(actv, ai);
		
		////////////////////////////////

		// delete: audio file

		////////////////////////////////
		CheckBox cb = (CheckBox) dlg2.findViewById(
				R.id.dlg_tmpl_confirm_simple_cb_cb);
		
		if (cb.isChecked()) {
			
			_delete_AI__Delete_File(actv, ai);
			
		}
		
		
		
		////////////////////////////////

		// Close dialog

		////////////////////////////////
		dlg2.dismiss();
		
		dlg1.dismiss();
		
		// debug
		String msg_Toast = "AI => deleted: " + ai.getFile_name();
		Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();		
		
	}//delete_AI

	private static void 
	_delete_AI__Delete_File
	(Activity actv, AI ai) {
		// TODO Auto-generated method stub
		
		boolean res = DBUtils.delete_AudioFile(actv, ai);
		
		////////////////////////////////

		// report

		////////////////////////////////
		if (res == true) {
			
			// debug
			String msg_Toast = "File => deleted";
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
		} else {

			// debug
			String msg_Toast = "File => can't be deleted";
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
		}
		
	}//_delete_AI__Delete_File

	private static void 
	_delete_AI__Delete_BM
	(Activity actv, AI ai) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		////////////////////////////////

		// get: BM list

		////////////////////////////////
		List<BM> bm_List = dbu.get_BMList(actv, ai.getDb_id());

		/******************************
			validate
		 ******************************/
		if (bm_List == null) {
			
			// Log
			String msg_Log = "BM list => null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		}
		
		// any entry?
		if (bm_List.size() < 1) {
			
			// Log
			String msg_Log = "BM list => no entry";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		}
		
		////////////////////////////////

		// delete

		////////////////////////////////
		int count = 0;
		
		for (BM bm : bm_List) {
			
			boolean res = dbu.deleteData_BM(actv, bm.getDbId());
			
			if (res == true) {
				
				count ++;
				
			}
			
		}
		
		// Log
		String msg_Log = "BM list size = " + bm_List.size()
						+ " / "
						+ "deleted = " + count;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		return;
		
	}//_delete_AI__Delete_BM

	public static void 
	edit_AI_Ok
	(Activity actv, Dialog dlg1, Dialog dlg2, AI ai) {
		// TODO Auto-generated method stub
		/***************************************
		 * Get data
		 ***************************************/
		EditText et_FileName = 
				(EditText) dlg2.findViewById(R.id.dlg_edit_ai_et_file_name);
		EditText et_FilePath = 
				(EditText) dlg2.findViewById(R.id.dlg_edit_ai_et_file_path);
		
		EditText et_Title = 
				(EditText) dlg2.findViewById(R.id.dlg_edit_ai_et_title);
		EditText et_Memo = 
				(EditText) dlg2.findViewById(R.id.dlg_edit_ai_et_memo);
		
		////////////////////////////////

		// set: data

		////////////////////////////////
		ai.setFile_name(et_FileName.getText().toString());
		ai.setFile_path(et_FilePath.getText().toString());
		
		ai.setTitle(et_Title.getText().toString());
		ai.setMemo(et_Memo.getText().toString());
		
		////////////////////////////////

		// Store data to db

		////////////////////////////////
//		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		DBUtils.updateData_AI_All(actv,
									ai.getDb_id(),
									ai);
		
		////////////////////////////////

		// update: list

		////////////////////////////////
		CONS.ALActv.adp_AIList.notifyDataSetChanged();
		
		/***************************************
		 * If successful, close dialog
		 ***************************************/
		dlg2.dismiss();
		dlg1.dismiss();

	}//edit_AI_Ok

	public static void 
	create_Dir
	(Activity actv, Dialog dlg1, Dialog dlg2) {
		// TODO Auto-generated method stub
		
		/******************************
			1. Create dir	=> Dir exists?
			2. Create a table	=> table exists?
			3. Re-build the listview
			4. Notify the adapter
		 ******************************/
		////////////////////////////////

		// get: dir name

		////////////////////////////////
		TextView tv = (TextView) dlg2.findViewById(
							R.id.dlg_confirm_create_folder_tv_table_name);
		
		String dname_New = tv.getText().toString();
		
		////////////////////////////////

		// validate: pref

		////////////////////////////////
		String currentPath = Methods.get_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				null);
		
		if (currentPath == null) {
//			if (CONS.MainActv.prefval_CurrentPath == null) {
			
			// Log
			String msg_Log = "currentPath == null";
//			String msg_Log = "CONS.MainActv.prefval_CurrentPath == null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
//			String path = StringUtils.join(
			currentPath = StringUtils.join(
								new String[]{
										CONS.Paths.dpath_Storage_Sdcard, 
										CONS.Paths.dname_Base
								},
								File.separator);
			
			Methods.set_Pref_String(
					actv, 
					CONS.Pref.pname_MainActv, 
					CONS.Pref.pkey_CurrentPath, 
					currentPath);
			
//			CONS.MainActv.prefval_CurrentPath = path;
			
		}

		////////////////////////////////
		
		// build: file path
		
		////////////////////////////////
		File newDir = new File(currentPath, dname_New);
//		File newDir = new File(CONS.MainActv.prefval_CurrentPath, dname_New);
		
		// Log
		String msg_Log = "new dir path => " + newDir.getAbsolutePath();
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		

		////////////////////////////////

		// new dir: exists?

		////////////////////////////////
		boolean dirExists = newDir.exists();

		if (dirExists == false) {
			
			boolean tmp_b = newDir.mkdir();
			
			if (tmp_b == true) {
				
				// Log
				msg_Log = "new dir created: " + newDir.getAbsolutePath();
				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			} else {
				
				dlg2.dismiss();
				
				Dialog dlg3 = Methods_dlg.dlg_Template_Cancel(
									actv, 
									R.layout.dlg_tmpl_message_simple, 
									R.string.generic_notice, 
									R.id.dlg_tmpl_message_simple_btn_ok, 
									Tags.DialogTags.GENERIC_DISMISS);
				
				TextView tv_Message = (TextView) dlg3.findViewById(
								R.id.dlg_tmpl_message_simple_tv_message);
				
				tv_Message.setText("Can't create dir: " + newDir.getName());
				
				dlg3.show();
				
				return;

			}
			
		} else {
			
			// Log
			msg_Log = "Dir exists => " + newDir.getAbsolutePath();
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
//			dlg2.dismiss();
//			
//			return;
			
		}

		////////////////////////////////

		// created: list.txt

		////////////////////////////////
		File listFile = new File(
							newDir.getAbsolutePath(), 
							CONS.Admin.fname_List);

		boolean fileExists = listFile.exists();
		
		if (fileExists == true) {
			
			// Log
			msg_Log = "list.txt => exists";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} else {
			
			try {
				
				listFile.createNewFile();
				
				// Log
				msg_Log = "list.txt => Created";
				Log.d("Methods.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				dlg2.dismiss();
				
				Dialog dlg3 = Methods_dlg.dlg_Template_Cancel(
									actv, 
									R.layout.dlg_tmpl_message_simple, 
									R.string.generic_notice, 
									R.id.dlg_tmpl_message_simple_btn_ok, 
									Tags.DialogTags.GENERIC_DISMISS);
				
				TextView tv_Message = (TextView) dlg3.findViewById(
								R.id.dlg_tmpl_message_simple_tv_message);
				
				tv_Message.setText("Can't create list.txt");
				
				dlg3.show();
				
				return;
//				e.printStackTrace();
				
			}//try

		}//if (fileExists == true)
		
//		////////////////////////////////
//
//		// create: table
//
//		////////////////////////////////
//		String tname_New = 
//				Methods.conv_CurrentPath_to_TableName(newDir.getAbsolutePath());
//		
//		// Log
//		msg_Log = "tname_New => " + tname_New;
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		Methods.create_Table_Audio(actv, tname_New);
////		Methods.create_Table(actv, tname_New);
		
		////////////////////////////////

		// rebuild: listview

		////////////////////////////////
		File currentDir = new File(currentPath);
		CONS.MainActv.list_RootDir.clear();
		
		List<String> tmp_List = Methods.get_FileList(currentDir);
//		Collections.sort(tmp_List);
		
		CONS.MainActv.list_RootDir.addAll(tmp_List);
		
		CONS.MainActv.aAdapter.notifyDataSetChanged();

		////////////////////////////////

		// dismiss

		////////////////////////////////
		dlg1.dismiss();
		dlg2.dismiss();

	}//create_Dir(Activity actv)

	public static void 
	start_Activity_ImpActv
	(Activity actv) {
		// TODO Auto-generated method stub
		Intent i = new Intent();
		
		i.setClass(actv, ImpActv.class);
		
		i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		
		actv.startActivity(i);
		
	}//start_Activity_ImpActv
	
	public static void 
	start_Activity_PrefActv
	(Activity actv) {
		// TODO Auto-generated method stub
		Intent i = new Intent();
		
		i.setClass(actv, PrefActv.class);
		
		i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		
		actv.startActivity(i);
		
	}//start_Activity_ImpActv

	public static boolean
	restore_DB(Activity actv) {
    	
    	// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Starting: restore_DB()");

		/*********************************
		 * Get the absolute path of the latest backup file
		 *********************************/
		// Get the most recently-created db file
		String src_dir = CONS.DB.dPath_dbFile_backup;
//		String src_dir = "/mnt/sdcard-ext/IFM9_backup";
		
		File f_dir = new File(src_dir);
		
		File[] src_dir_files = f_dir.listFiles();
		
		// If no files in the src dir, quit the method
		if (src_dir_files.length < 1) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread()
						.getStackTrace()[2].getLineNumber()
					+ "]", "No files in the dir: " + src_dir);
			
			return false;
			
		}//if (src_dir_files.length == condition)
		
		// Latest file
		File f_src_latest = src_dir_files[0];
		
		
		for (File file : src_dir_files) {
			
			if (f_src_latest.lastModified() < file.lastModified()) {
						
				f_src_latest = file;
				
			}//if (variable == condition)
			
		}//for (File file : src_dir_files)
		
		// Show the path of the latest file
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "f_src_latest=" + f_src_latest.getAbsolutePath());
		
		/*********************************
		 * Restore file
		 *********************************/
		String src = f_src_latest.getAbsolutePath();
		String dst = StringUtils.join(
				new String[]{
						//REF http://stackoverflow.com/questions/9810430/get-database-path answered Jan 23 at 11:24
						actv.getDatabasePath(CONS.DB.dbName).getPath()
				},
//						actv.getFilesDir().getPath() , 
//						CONS.DB.dbName},
				File.separator);
		
		boolean res = Methods.restore_DB(
							actv, 
							CONS.DB.dbName, 
							src, dst);
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "res=" + res);
		
		////////////////////////////////

		// return

		////////////////////////////////
		return res;
		
	}//private void restore_DB()
	
	/*********************************
	 * @return true => File copied(i.e. restored)<br>
	 * 			false => Copying failed
	 *********************************/
	public static boolean
	restore_DB
	(Activity actv, String dbName, 
			String src, String dst) {
		/*********************************
		 * 1. Setup db
		 * 2. Setup: File paths
		 * 3. Setup: File objects
		 * 4. Copy file
		 * 
		 *********************************/
		// Setup db
		DBUtils dbu = new DBUtils(actv, dbName);
		
		SQLiteDatabase wdb = dbu.getWritableDatabase();
	
		wdb.close();
	
		/*********************************
		 * 2. Setup: File paths
	
		/*********************************
		 * 3. Setup: File objects
		 *********************************/
	
		/*********************************
		 * 4. Copy file
		 *********************************/
		FileChannel iChannel = null;
		FileChannel oChannel = null;
		
		try {
			iChannel = new FileInputStream(src).getChannel();
			oChannel = new FileOutputStream(dst).getChannel();
			iChannel.transferTo(0, iChannel.size(), oChannel);
			
			iChannel.close();
			oChannel.close();
			
			// Log
			Log.d("ThumbnailActivity.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "File copied: " + src);
			
			// debug
			Toast.makeText(actv, "DB restoration => Done", Toast.LENGTH_LONG).show();
			
			return true;
	
		} catch (FileNotFoundException e) {
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			if (iChannel != null) {
				
				try {
					
					iChannel.close();
					
				} catch (IOException e1) {
					
					// Log
					Log.e("Methods.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "Exception: " + e.toString());
	
				}
				
			}
			
			if (iChannel != null) {
				
				try {
					
					iChannel.close();
					
				} catch (IOException e1) {
					
					// Log
					Log.e("Methods.java" + "["
							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
							+ "]", "Exception: " + e.toString());
					
				}
				
			}
			
			if (oChannel != null) {
				
				try {
					oChannel.close();
				} catch (IOException e1) {
					
					// Log
					Log.e("Methods.java" + "["
							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
							+ "]", "Exception: " + e.toString());
					
				}
				
			}
	
			return false;
			
		} catch (IOException e) {
			// Log
			Log.e("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
			if (iChannel != null) {
				
				try {
					
					iChannel.close();
					
				} catch (IOException e1) {
					
					// Log
					Log.e("Methods.java" + "["
							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
							+ "]", "Exception: " + e.toString());
					
				}
				
			}
			
			if (oChannel != null) {
				
				try {
					oChannel.close();
				} catch (IOException e1) {
					
					// Log
					Log.e("Methods.java" + "["
							+ Thread.currentThread().getStackTrace()[2].getLineNumber()
							+ "]", "Exception: " + e.toString());
					
				}
				
			}
	
			
			return false;
			
		}//try
		
	}//restore_DB

	public static CharSequence conv_CurrentPath_to_DisplayPath(String path) {
		// TODO Auto-generated method stub
		
		String head = CONS.Paths.dpath_Storage_Sdcard;
		
		int len = head.length();
		
		return path.substring(len + 1);
		
	}

	public static String 
	conv_CurrentPathMove_to_TableName
	(String choice) {
		// TODO Auto-generated method stub
		
		String[] tokens = choice.split(File.separator);
		
		/******************************
			validate: null
		 ******************************/
		if (tokens == null) {
			
			// Log
			String msg_Log = "tokens => null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return choice;
			
		}
		
		////////////////////////////////

		// size => 1

		////////////////////////////////
		if (tokens.length == 1) {
			
			return tokens[0];
			
		}

		////////////////////////////////

		// size > 1

		////////////////////////////////
		return StringUtils.join(tokens, CONS.DB.jointString_TableName);
		
	}//conv_CurrentPathMove_to_TableName

	public static void 
	move_Files
	(Activity actv, 
			Dialog dlg1, Dialog dlg2, Dialog dlg3, 
			AI ai, int alList_Position, String choice) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// update: table name

		////////////////////////////////
		String tname_New = Methods.conv_CurrentPathMove_to_TableName(choice);

		ai.setTable_name(tname_New);
		
		boolean res = DBUtils.updateData_AI_All(actv, ai.getDb_id(), ai);
		
		/******************************
			validate: updated?
		 ******************************/
		if (res == false) {
			
			String msg = "DB update => failed";
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			dlg3.dismiss();
			
			return;
			
		}
		
		////////////////////////////////

		// remove: from listview

		////////////////////////////////
		CONS.ALActv.list_AI.remove(ai);
		
		////////////////////////////////

		// notify

		////////////////////////////////
		CONS.ALActv.adp_AIList.notifyDataSetChanged();
		
		////////////////////////////////

		// dismiss

		////////////////////////////////
		dlg3.dismiss();
		dlg2.dismiss();
		dlg1.dismiss();
		
		////////////////////////////////

		// report

		////////////////////////////////
		String msg = "Move file => done";
		Methods_dlg.dlg_ShowMessage(actv, msg);
		
		
	}//move_Files

	public static void 
	update_MoveFilesList
	(Activity actv, 
			Dialog dlg1, Dialog dlg2, 
			AI ai, int aiList_Position, 
			String choice) {
		// TODO Auto-generated method stub
		
		// Log
		String msg_Log = "choice => " + choice;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
				// choice => cm7/sq
		
		String currentPath = Methods.get_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				null);
				
				// current path => /mnt/sdcard-ext/cm7
		
				

		// Log
		msg_Log = "current path => " + currentPath;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// build: paths

		////////////////////////////////
		String new_DirPath = StringUtils.join(
				new String[]{
		
						CONS.Paths.dpath_Storage_Sdcard,
						choice
				},
				File.separator);
		
		// Log
		msg_Log = "new_DirPath => " + new_DirPath;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		////////////////////////////////

		// build: list

		////////////////////////////////
		List<String> dir_List = Methods.get_DirList(new_DirPath);
		
		CONS.ALActv.dir_List.clear();
		
		for (String dirName : dir_List) {
//			for (String dirName : CONS.ALActv.dir_List) {
			
			CONS.ALActv.dir_List.add(choice + File.separator + dirName);
//			CONS.ALActv.dir_List.add(CONS.DB.tname_CM7 + File.separator + dirName);
//			dirName = CONS.DB.tname_CM7 + File.separator + dirName;
			
		}
		
		// Log
		msg_Log = "dir list => modified";
		Log.d("Methods_dlg.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		CONS.ALActv.dir_List.add(CONS.Admin.dirString_UpperDir);

		////////////////////////////////

		// notify

		////////////////////////////////
		CONS.ALActv.adp_DirList.notifyDataSetChanged();
		
		// Log
		msg_Log = "adapter => notified";
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}//update_MoveFilesList

	private static String conv_CurrentPathMove_to_DirPath(String choice) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String 
	conv_CurrentPathMove_to_CurrentPathMove_New
	(String curPath_Move) {
		// TODO Auto-generated method stub
		
		String[] tokens = curPath_Move.split(File.separator);
		
		////////////////////////////////

		// tokens == 1

		////////////////////////////////
		if (tokens == null) {
			
			return curPath_Move;
			
		} else if (tokens.length == 1) {
			
			return curPath_Move;
			
		}
		
		////////////////////////////////

		// tokens > 1

		////////////////////////////////
		int len = tokens.length;
		
		String[] tokens_New = Arrays.copyOfRange(tokens, 0, len - 1);
//		String[] tokens_New = Arrays.copyOfRange(tokens, 0, len - 2);
		
		return StringUtils.join(tokens_New, File.separator);
		
	}//conv_CurrentPathMove_to_CurrentPathMove_New

	/******************************
		@return
			-1	No db file<br>
			-2	Copying db file => failed<br>
			1	db file => copied<br>
	 ******************************/
	public static int 
	import_DB
	(Activity actv, 
		Dialog d1, Dialog d2, Dialog d3) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
	
		// setup: src, dst
	
		////////////////////////////////
		// IFM10
		String src_dir = CONS.DB.dPath_dbFile_backup_CM6;
//		String src_dir = CONS.DB.dPath_dbFile_backup_TWT;
		
		File f_dir = new File(src_dir);
		
		File[] src_dir_files = f_dir.listFiles();
		
		// If no files in the src dir, quit the method
		if (src_dir_files.length < 1) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread()
						.getStackTrace()[2].getLineNumber()
					+ "]", "No files in the dir: " + src_dir);
			
			return -1;
			
		}//if (src_dir_files.length == condition)
		
		// Latest file
		File f_src_latest = src_dir_files[0];
		
		for (File file : src_dir_files) {
			
			if (f_src_latest.lastModified() < file.lastModified()) {
						
				f_src_latest = file;
				
			}//if (variable == condition)
			
		}//for (File file : src_dir_files)
		
		// Show the path of the latest file
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "f_src_latest=" + f_src_latest.getAbsolutePath());
		
		////////////////////////////////
	
		// Restore file
	
		////////////////////////////////
		String src = f_src_latest.getAbsolutePath();
		
		String dst = StringUtils.join(
				new String[]{
						//REF http://stackoverflow.com/questions/9810430/get-database-path answered Jan 23 at 11:24
						actv.getDatabasePath(CONS.DB.dbName).getPath()
				},
	//					actv.getFilesDir().getPath() , 
	//					CONS.DB.dbName},
				File.separator);
		
		// Log
		String msg_Log = "db path => " 
					+ actv.getDatabasePath(CONS.DB.dbName).getPath();
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////
	
		// build: db file path (dst)
	
		////////////////////////////////
		String tmp_str = Methods.get_Dirname(actv, dst);
		
		String dst_New = StringUtils.join(
					new String[]{
							
							tmp_str,
							CONS.DB.dbName_Importing
	//						CONS.DB.dbName_IFM11
							
					}, 
					File.separator);
		
		// Log
		msg_Log = String.format(
							"src = %s // dst = %s", 
							src, dst_New);
		
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////
	
		// import (using restoration-related method)
	
		////////////////////////////////
		boolean res = Methods.restore_DB(
							actv, 
							CONS.DB.dbName, 
							src, dst_New);
		
//		//test
//		boolean res = false;
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "res=" + res);
	
		
	//	//debug
	//	boolean res = true;
		
		/******************************
			validate
		 ******************************/
		if (res == false) {		// copying db file => failed
			
			String msg = "Copying file => failed";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			d3.dismiss();
			
			return -2;
			
		}
		
		////////////////////////////////
	
		// dismiss: dlg
	
		////////////////////////////////
		d3.dismiss();
		d2.dismiss();
		d1.dismiss();
		
		////////////////////////////////
	
		// report
	
		////////////////////////////////
		String msg = "DB => Imported\n" + CONS.DB.dbName_Importing;
		Methods_dlg.dlg_ShowMessage(actv, msg);
		
		
		////////////////////////////////
	
		// return
	
		////////////////////////////////
		return 1;
	
	}//import_DB

	public static String 
	get_Dirname
	(Activity actv, String target) {

		String[] tokens = target.split(File.separator);
	
		////////////////////////////////
		
		// tokens => null
		
		////////////////////////////////
		if (tokens == null) {
			
			// Log
			String msg_Log = "tokens => null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return target;
			
		}
		
		////////////////////////////////

		// tokens => 1

		////////////////////////////////
		if (tokens.length == 1) {
			
			return target;
			
		}
		
		////////////////////////////////

		// tokens > 1

		////////////////////////////////
		String[] tokens_New = Arrays.copyOfRange(tokens, 0, tokens.length - 1);
		
		return StringUtils.join(tokens_New, File.separator);
	
	}//get_Dirname

	/******************************
		@return
			>1	Number of patterns saved<br>
			0	No patterns saved<br>
			-1	Table 'patterns' => not exist<br>
			-2	Can't build list<br>
			-3	Unknown result<br>
	 ******************************/
	public static int 
	import_Patterns
	(Activity actv) {
		// TODO Auto-generated method stub
	
		////////////////////////////////
	
		// get: patterns list
	
		////////////////////////////////
		List<String> patterns_List = _import_Patterns__Get_PatternsList(actv);
		
		/******************************
			validate: null
		 ******************************/
		// Log
		if (patterns_List == null) {
			
			// Log
			String msg_Log = "patterns_List => null";
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return -2;
			
		}
		
		String msg_Log = "patterns_List => " + patterns_List.size();
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////
	
		// insert patterns
	
		////////////////////////////////
		int res = Methods._import_Patterns__SavePatterns(actv, patterns_List);
		
//		//test
//		int res = -1;
		
		// Log
		msg_Log = "save pattern: res => " + res;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////
	
		// dismiss
	
		////////////////////////////////
		if (res == -1) {
			
	//		String msg = "Table 'patterns' => doesn't exist";
	//		Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
	//		d3.dismiss();
	
			return -1;
			
		} else if (res == 0) {
			
			return 0;
			
	//		String msg = "No patterns saved";
	//		Methods_dlg.dlg_ShowMessage(actv, msg, R.color.gold2);
	//		
	//		d3.dismiss();
			
		} else if (res > 0) {
			
			return res;
			
	//		String msg = "Patterns saved => " + res;
	//		Methods_dlg.dlg_ShowMessage(actv, msg, R.color.green4);
			
	//		d3.dismiss();
	//		d2.dismiss();
	//		d1.dismiss();
			
		} else {
			
			// Log
			msg_Log = "Unknown result => " + res;
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return -3;
			
	//		String msg = "Unknown result => " + res;
	//		Methods_dlg.dlg_ShowMessage(actv, msg, R.color.yello);
	//		
	//		d3.dismiss();
			
		}
		
	}//import_Patterns

	/******************************
		@return null => 1. No such table<br>
						2. Cursor => null<br>
						3. Cursor < 1 <br>
	 ******************************/
	private static List<String> 
	_import_Patterns__Get_PatternsList
	(Activity actv) {
		// TODO Auto-generated method stub
		////////////////////////////////
	
		// db
	
		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName_CM6);
	//	DBUtils dbu = new DBUtils(actv, CONS.DB.dbName_IFM11);
		
		SQLiteDatabase rdb = dbu.getReadableDatabase();
	
		////////////////////////////////
	
		// Table exists?
	
		////////////////////////////////
		String tableName = CONS.DB.tname_MemoPatterns_CM6;
	//	String tableName = CONS.DB.tname_MemoPatterns_IFM11;
	
		boolean res = dbu.tableExists(rdb, tableName);
		
		if (res == true) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: " + tableName);
			
		} else {//if (res == false)
			////////////////////////////////
	
			// no table => return
	
			////////////////////////////////
			String msg = "Table doesn't exist: " + tableName;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			rdb.close();
			
			return null;
			
		}//if (res == false)
		
		
		////////////////////////////////
	
		// Get cursor
	
		////////////////////////////////
		// "word"
		String orderBy = CONS.DB.cols_memo_patterns_CM6[0] + " ASC"; 
	//	String orderBy = CONS.DB.col_names_MemoPatterns_IFM11[0] + " ASC"; 
		
		Cursor c = rdb.query(
						CONS.DB.tname_MemoPatterns_CM6,
						CONS.DB.cols_memo_patterns_CM6,
	//					CONS.DB.tname_MemoPatterns_IFM11,
	//					CONS.DB.col_names_MemoPatterns_IFM11,
		//				CONS.DB.col_types_refresh_log_full,
						null, null,		// selection, args 
						null, 			// group by
						null, 		// having
						orderBy);
	
		/******************************
			validate: null
		 ******************************/
		if (c == null) {
	
			String msg = "query => null";
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			rdb.close();
			
			return null;
			
		}
		
		/******************************
			validate: any entry?
		 ******************************/
		if (c.getCount() < 1) {
	
			String msg = "entry => < 1";
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			rdb.close();
			
			return null;
			
		}
	
		////////////////////////////////
	
		// cursor: move to first
	
		////////////////////////////////
		c.moveToFirst();
		
		////////////////////////////////
	
		// Get list
	
		////////////////////////////////
		List<String> patternList = new ArrayList<String>();
		
		if (c.getCount() > 0) {
			
			for (int i = 0; i < c.getCount(); i++) {
				
				patternList.add(c.getString(0));	// 0 => "word"
				
				c.moveToNext();
				
			}//for (int i = 0; i < patternList.size(); i++)
			
		} else {//if (c.getCount() > 0)
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "!c.getCount() > 0");
			
		}//if (c.getCount() > 0)
		
		
		Collections.sort(patternList);
	
		////////////////////////////////
	
		// return
	
		////////////////////////////////
		return patternList;
		
	}//_import_Patterns__Get_PatternsList

	private static int 
	_import_Patterns__SavePatterns
	(Activity actv, List<String> patterns_List) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// insert

		////////////////////////////////
		boolean res;
		
		int counter = DBUtils.insert_Data_Patterns(actv, patterns_List);
			
		return counter;
		
	}//_import_Patterns__SavePatterns

	
}//public class Methods


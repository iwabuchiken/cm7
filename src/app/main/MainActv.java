package app.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import app.adapters.Adp_MainList;
import app.items.Refresh;
import app.listeners.LOI_LCL;
import app.listeners.button.BO_CL;
import app.utils.CONS;
import app.utils.DBUtils;
import app.utils.Methods;
import app.utils.Methods_dlg;
import app.utils.Ops;
import app.utils.Tags;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import org.apache.commons.lang.StringUtils;

import cm7.main.R;

//import app.main.R;


public class MainActv extends ListActivity {
	
	public static Vibrator vib;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	/*----------------------------
		 * 1. super
		 * 2. Set content
		 * 2-2. Set title
		 * 3. Initialize => vib
		 * 
		 *  4. Set list
		 *  5. Set listener => Image buttons
		 *  6. Set path label
		 *  
		 *  7. Initialize preferences
		 *  
		 *  8. Refresh DB
			----------------------------*/
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*----------------------------
		 * 2-2. Set title
			----------------------------*/
		this.setTitle(this.getClass().getName());
        
        vib = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);

//		/*********************************
//		 * Debugs
//		 *********************************/
//		do_debug();
        
    }//public void onCreate(Bundle savedInstanceState)

    private void do_debug() {
    	
//    	_do_debug_Clear_CurrentPath();
    	
//    	_do_debug_DB();
    	
//    	_do_debug_Prefs();
//    	_do_debug_Get_LastEntry_Refresh();
//    	_do_debug_MillSec_to_ClockLabel();
//    	_do_debug_MillSec_to_TimeLabel();
    	
//    	_do_debug__Conv_Path2Tname();
    	
	}

	private void _do_debug_Clear_CurrentPath() {
		// TODO Auto-generated method stub
		
		CONS.Pref.prefs_MainActv = 
				this.getSharedPreferences(
						CONS.Pref.pname_MainActv,
						MODE_PRIVATE);
		
//		////////////////////////////////
//
//		// Prefs set already?
//
//		////////////////////////////////
//		String temp = CONS.Pref.prefs_MainActv
//				.getString(CONS.Pref.pkey_CurrentPath, null);
//		
//		if (temp != null) {
////			if (temp != null && !temp.equals("IFM8")) {
//			
//			// Log
//			String msg_log = "Current path => " + temp;
//			Log.d("MainActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_log);
//			
//			return;
//			
//		}//if (temp == null)
		
		////////////////////////////////

		// Set: base current path

		////////////////////////////////
		SharedPreferences.Editor editor = CONS.Pref.prefs_MainActv.edit();
		
		// New path
		String base_path = StringUtils.join(
				new String[]{
						CONS.Paths.dpath_Storage_Sdcard, CONS.Paths.dname_Base
				},
				File.separator);
		
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "base_path=" + base_path);

		// Commit
		editor.putString(CONS.Pref.pkey_CurrentPath, base_path);
		
		editor.commit();
		
		// Log
		String msg_log = "Bae path => Set: " + base_path;
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_log);
		
//		boolean res = Methods.set_Pref_String(
//				this, 
//				CONS.Pref.pname_MainActv, 
//				CONS.Pref.pkey_CurrentPath, 
//				null);
		
//		// Log
//		String msg_Log = "current path => set null";
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
	}

	private void _do_debug_DB() {
		// TODO Auto-generated method stub
		
		File f = new File(this.getFilesDir().getPath());
		
		String[] fnames = f.list();
		
		// Log
		String msg_Log = "dir => " + f.getAbsolutePath();
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		for (String name : fnames) {
			
			// Log
			msg_Log = "name = " + name;
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
		}
		
		String dbPath = this.getDatabasePath(CONS.DB.dbName).getPath();
		
		// Log
		msg_Log = "dbPath = " + dbPath;
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}

	private void _do_debug_Prefs() {
		// TODO Auto-generated method stub
		boolean res = Methods.get_Pref_Boolean(
				this, 
				CONS.Pref.pname_MainActv, 
				this.getString(R.string.prefactv_key_resume_position), 
				false);
		
		// Log
		String msg_Log = "prefactv_key_resume_position => "
						+ res;
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		
	}

	private void _do_debug_Get_LastEntry_Refresh() {
		// TODO Auto-generated method stub
		Refresh last_history = DBUtils.get_LatestEntry_Refresh(this);
		
		String last = last_history.getLast_refreshed();
		
		long last_refreshed = 
				Methods.conv_TimeLabel_to_MillSec(last);
		
		// Log
		String msg_Log = "last = " + last
						+ " // "
						+ "refreshed = " + last_refreshed;
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// 4 days ago

		////////////////////////////////
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -4);
//				calendar.add(Calendar.HOUR_OF_DAY, -4);
		Date date = calendar.getTime();
		
		long time_4DaysAgo = date.getTime();
		
		// Log
		msg_Log = "4 days ago = " + time_4DaysAgo
				+ " // "
				+ "(time_4DaysAgo > last_refreshed) => "
				+ (time_4DaysAgo > last_refreshed);
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}

	private void _do_debug_MillSec_to_ClockLabel() {
		// TODO Auto-generated method stub
		
		String label = "09:45";
		
		long millSec = Methods.conv_ClockLabel_to_MillSec(label);
		
		// Log
		String msg_Log = "label = " + label
						+ " // "
						+ "millSec = " + millSec;
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// Appendix

		////////////////////////////////
//		String s = "05";
//		
//		int num = Integer.parseInt(s);
//		
//		// Log
//		msg_Log = "s = " + s
//						+ " // "
//						+ "num = " + num;
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
	}

	private void _do_debug_MillSec_to_TimeLabel() {
		// TODO Auto-generated method stub
		long now = Methods.getMillSeconds_now();
		
		String label = Methods.conv_MillSec_to_TimeLabel(now);
		
		// Log
		String msg_Log = "now = " + now
						+ " // "
						+ "label = " + label;
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// Conv: millsec to label

		////////////////////////////////
		long now_2 = Methods.conv_TimeLabel_to_MillSec(label);
		
		// Log
		msg_Log = "now_2 = " + now_2
						+ " // ";
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		
	}

	private void _do_debug__Conv_Path2Tname() {

		String currentPath = CONS.Pref.prefs_MainActv
				.getString(CONS.Pref.pkey_CurrentPath, null);
		
		if (currentPath == null) {
			
			// Log
			String msg_log = "currentPath => null";
			Log.e("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return;
			
		}
		
		String tname = Methods.conv_CurrentPath_to_TableName(currentPath);
		
		// Log
		String msg_log = "currentPath = " + currentPath
						+ " // "
						+ "tname = " + tname;
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_log);
		
		
		// TODO Auto-generated method stub
//		String s = CONS.Paths.dpath_Storage_Sdcard + CONS.Paths.dname_Base;
//		
//		// Log
//		String msg_log = "s = " + s;
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_log);
//		
//		String s1 = CONS.Paths.dpath_Storage_Sdcard;
//		
//		int len_s1 = s1.length();
//		
//		String s_new = s.substring(len_s1);
//		
//		// Log
//		msg_log = "s1 = " + s1
//					+ " / "
//					+ "s_new = " + s_new;
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_log);
		
	}

	private void _Setup_SetListeners() {
		////////////////////////////////

		// listview => long click

		////////////////////////////////
    	ListView lv = this.getListView();
		
//    	lv.setTag(Tags.ListTags.actv_main_lv);
		lv.setTag(Tags.ListTags.ACTV_MAIN_LV);
		
		lv.setOnItemLongClickListener(new LOI_LCL(this));
		
		////////////////////////////////

		// imagebutton: up

		////////////////////////////////
		ImageButton bt_Up = (ImageButton) this.findViewById(R.id.main_bt_up);
		
		bt_Up.setTag(Tags.ButtonTags.ib_up);
		bt_Up.setOnClickListener(new BO_CL(this));
		
	}

	/******************************

		set_DirList()
		
		From: set_initial_dir_list_part1() : cm5
		
		@return
		false	=> 1. root_Dir => can't be created<br>
					2. files list => can't be created<br>

	 ******************************/
    private boolean _Setup_DirList() {
		// TODO Auto-generated method stub
    	////////////////////////////////

		// 1. Create root dir

		////////////////////////////////
    	File root_Dir = _create_RootDir();
    	
    	if (root_Dir == null) {
			Log.e("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "root_Dir == null");
			
			return false;
			
		}//if (root_dir == null)
    	
    	////////////////////////////////

		// Create "list.txt"

		////////////////////////////////
		
    	boolean res = _create_ListFile(root_Dir);
		
		if (res == false) {
			Log.e("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "res == false");
			
			return false;
		}//if (res == false)
    	
		////////////////////////////////

		// Set: Prefs

		////////////////////////////////
		initPrefs_CurrentPath();

		////////////////////////////////

		// Get file list

		////////////////////////////////
		// Get: current path
		String currentPath = Methods.get_Pref_String(
				this, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				null);
		
		if (currentPath == null) {
			
			currentPath = root_Dir.getAbsolutePath();
			
			Methods.set_Pref_String(
					this, 
					CONS.Pref.pname_MainActv, 
					CONS.Pref.pkey_CurrentPath, 
					currentPath);
			
		}
		
		// Build: files list
		CONS.MainActv.list_RootDir = Methods.get_FileList(new File(currentPath));
		
		if (CONS.MainActv.list_RootDir == null) {
			
			CONS.MainActv.list_RootDir = Methods.get_FileList(root_Dir);
			
			if (CONS.MainActv.list_RootDir == null) {
				
				// Log
				String msg_log = "CONS.MainActv.list_RootDir => can't build";
				Log.d("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_log);
				
				return false;
				
			}
				
		}
		
//		if (CONS.MainActv.list_RootDir == null) {
//			
//			CONS.MainActv.list_RootDir = Methods.get_FileList(root_Dir);
//
//			if (CONS.MainActv.list_RootDir == null) {
//				
//				// Log
//				String msg_log = "CONS.MainActv.list_RootDir => can't build";
//				Log.d("MainActv.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", msg_log);
//				
//				return false;
//				
//			}
//			
//		} else {//if (this.CONS.MainActv.list_RootDir == null)
//			
//			String currentPath = Methods.get_Pref_String(
//					this, 
//					CONS.Pref.pname_MainActv, 
//					CONS.Pref.pkey_CurrentPath, 
//					null);
//			
//			if (currentPath == null) {
//				
//				currentPath = root_Dir.getAbsolutePath();
//				
//				Methods.set_Pref_String(
//						this, 
//						CONS.Pref.pname_MainActv, 
//						CONS.Pref.pkey_CurrentPath, 
//						currentPath);
//				
//			}
//			
//			// Log
//			String msg_Log = "currentPath => " + currentPath;
//			Log.d("MainActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//			CONS.MainActv.list_RootDir = Methods.get_FileList(new File(currentPath));
//			
//			if (CONS.MainActv.list_RootDir == null) {
//				
//				// Log
//				String msg_log = "CONS.MainActv.list_RootDir => can't build";
//				Log.d("MainActv.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", msg_log);
//				
//				return false;
//				
//			}
//
//		}//if (this.CONS.MainActv.list_RootDir == null)

		////////////////////////////////

		// Set list to adapter

		////////////////////////////////
		res = set_ListToAdapter();
		
		if (res == false) {
			
			// Log
			String msg_log = "Can't set the list to adapter";
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			// debug
			Toast.makeText(this, msg_log, Toast.LENGTH_SHORT).show();
			
			return false;
			
		}
		
		
		////////////////////////////////

		// Return

		////////////////////////////////
		return true;
		
	}//private boolean set_DirList()

	private boolean set_ListToAdapter() {
		CONS.MainActv.adp_dir_list = new ArrayAdapter<String>(
				this,
				R.layout.list_row_simple_1,
//				android.R.layout.simple_list_item_1,
				CONS.MainActv.list_RootDir
				);
		
//		Adp_MainList aAdapter = new Adp_MainList(
		CONS.MainActv.aAdapter = new Adp_MainList(
				this,
				R.layout.list_row_simple_1,
//				android.R.layout.simple_list_item_1,
				CONS.MainActv.list_RootDir
				);

		
		if (CONS.MainActv.adp_dir_list == null) {
			
			// Log
			String msg_log = "CONS.MainActv.adp_dir_list == null";
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return false;
			
		}//if (adapter == null)

		this.setListAdapter(CONS.MainActv.aAdapter);
//		this.setListAdapter(aAdapter);
//		this.setListAdapter(CONS.MainActv.adp_dir_list);
		
		return true;
		
	}//private boolean set_list_to_adapter()

	private void initPrefs_CurrentPath() {
		// TODO Auto-generated method stub
//		////////////////////////////////
//
//		// Get: Pref
//
//		////////////////////////////////
//		CONS.Pref.prefs_MainActv = 
//				this.getSharedPreferences(
//						CONS.Pref.pname_MainActv,
//						MODE_PRIVATE);
//		
//		////////////////////////////////
//
//		// Prefs set already?
//
//		////////////////////////////////
//		String temp = CONS.Pref.prefs_MainActv
//				.getString(CONS.Pref.pkey_CurrentPath, null);
		
		String temp = Methods.get_Pref_String(
					this, CONS.Pref.pname_MainActv, CONS.Pref.pkey_CurrentPath, null);
		
		if (temp != null) {
//			if (temp != null && !temp.equals("IFM8")) {
			
			// Log
			String msg_log = "Current path => " + temp;
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return;
			
		}//if (temp == null)
		
		////////////////////////////////

		// Set: base current path

		////////////////////////////////
		// New path
		String base_path = StringUtils.join(
				new String[]{
						CONS.Paths.dpath_Storage_Sdcard, CONS.Paths.dname_Base
				},
				File.separator);
		
		boolean res = Methods.set_Pref_String(
							this, 
							CONS.Pref.pname_MainActv, 
							CONS.Pref.pkey_CurrentPath, 
							base_path);
		
//		SharedPreferences.Editor editor = CONS.Pref.prefs_MainActv.edit();
//		
//		// Log
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "base_path=" + base_path);
//
//		// Commit
//		editor.putString(CONS.Pref.pkey_CurrentPath, base_path);
//		
//		editor.commit();
		
		String msg_log = null;
		
		if (res == true) {
			
			// Log
			msg_log = "Base path => Set: " + base_path;
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
		} else {
			
			// Log
			msg_log = "Base path => Not set: " + base_path;
			Log.e("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);

		}
		
	}//private void initPrefs_CurrentPath()

	private boolean _create_ListFile(File root_Dir) {
		// TODO Auto-generated method stub
		File list_File = new File(root_Dir, CONS.Admin.fname_List);
		
		if (list_File.exists()) {
			
			// Log
			String msg_log = "list.txt exists in: " + root_Dir.getAbsolutePath();
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return true;
			
		} else {//if (list_File.exists())
			
			try {
//				
				list_File.createNewFile();
				
				// Log
				Log.d("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]",
						"list.txt created => " + list_File.getAbsolutePath());
				
				return true;
				
			} catch (IOException e) {
				
				// Log
				Log.e("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]",
						"Create file => Failed: " + list_File.getAbsolutePath()
						+ "(" + e.toString() + ")");
				
				return false;
			}//try
			
		}//if (list_File.exists())
		
	}//private boolean create_ListFile(File root_Dir)

	/******************************
		@return file => top directory
	 ******************************/
	private File _create_RootDir() {
		// TODO Auto-generated method stub
		String dpath_base = StringUtils.join(
				new String[]{
						CONS.Paths.dpath_Storage_Sdcard, CONS.Paths.dname_Base},
				File.separator);

//		// Log
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "dpath_base=" + dpath_base);
		
		File file = new File(dpath_base);
		
		if (!file.exists()) {
			try {
				file.mkdir();
				
				// Log
				Log.d("MainActv.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
						.getLineNumber() + "]", "Dir created => " + file.getAbsolutePath());
				
				return file;
				
			} catch (Exception e) {
				// Log
				Log.e("MainActv.java"
				+ "["
				+ Thread.currentThread().getStackTrace()[2]
						.getLineNumber() + "]", "Exception => " + e.toString());
				
				return null;
			}
			
		} else {//if (file.exists())
			
			return file;
			
		}//if (file.exists())		
		
	}//private File create_RootDir()

	private void init_prefs() {
    	/*********************************
		 * 1. history_mode
		 *********************************/
//		int current_history_mode = Methods.get_pref(
//				this, 
//				MainActv.pname_mainActv, 
//				MainActv.pname_mainActv_history_mode,
//				-1);
//
//		// Log
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "onCreate: current_history_mode=" + current_history_mode);
		
//		boolean res = Methods.set_pref(
//				this, 
//				CONS.pname_mainActv, 
//				CONS.pname_mainActv_history_mode,
//				CONS.HISTORY_MODE_OFF);
//
//		// Log
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", 
//				"history_mode set => MainActv.HISTORY_MODE_OFF"
//				+ "(" + CONS.HISTORY_MODE_OFF + ")");
		
	}//private void init_prefs()

	@Override
	protected void onListItemClick(ListView lv, View v, int position, long id) {
		
//		super.onListItemClick(lv, v, position, id);
		
		/*********************************
		 * 0. Vibrate
		 * 
		 * 1. Get item name
		 * 2. Get file object
		 * 2-2. File object exists?
		 * 
		 * 3. Is a directory?
		 * 		=> If yes, update the current path
		 * 
		 * 4. Is a "list.txt"?
		 *********************************/
		//
		vib.vibrate(Methods.vibLength_click);
		
		/*********************************
		 * 1. Get item name
		 *********************************/
//		String item = _ItemClick_GetItem(lv, position);
		
		String item = (String) lv.getItemAtPosition(position);
		
		if (item != null) {
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "item=" + item);
			
		} else {//if (item_)
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "item == null");
			
		}//if (item_)
		
		/******************************
			Set pref: Current position
		 ******************************/
		_ItemClick_SetPref_CurrentPosition(position);

		////////////////////////////////

		// Get file object

		////////////////////////////////
//		SharedPreferences prefs = 
//				this.getSharedPreferences(
//						CONS.Pref.pname_MainActv, MODE_PRIVATE);
//		
//		String currentPath = prefs.getString(CONS.Pref.pkey_CurrentPath, null);
		String currentPath = Methods.get_Pref_String(
						this, 
						CONS.Pref.pname_MainActv, 
						CONS.Pref.pkey_CurrentPath, 
						null);
		
		// Log
		String msg_Log = "currentPath = " + currentPath;
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		/******************************
			Validate: Current path => null?
		 ******************************/
		if (currentPath == null) {
			
			// Log
			msg_Log = "currentPath => null";
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		}
		
		////////////////////////////////

		// File path

		////////////////////////////////
		File f = new File(currentPath, item);
		
		// Log
		msg_Log = "File path = " + f.getAbsolutePath();
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		
		boolean res = f.exists();
		
		/******************************
			validate: file exists?
		 ******************************/
		if (res == false) {
			
			// Log
			msg_Log = "File => doesnt exist: " + f.getAbsolutePath();
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			// debug
			Toast.makeText(this, msg_Log, Toast.LENGTH_SHORT).show();
			
			return;
			
		}

		////////////////////////////////

		// Is file?

		////////////////////////////////
		if (f.isFile()) {
			
			////////////////////////////////

			// list.txt?

			////////////////////////////////
			if (item.equals(CONS.Admin.fname_List)) {
				
				Methods.start_Activity_ALActv(this, currentPath);
				
			} else {
				
				// Log
				msg_Log = "File => not list.txt";
				Log.d("MainActv.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				
				// debug
				Toast.makeText(this, msg_Log, Toast.LENGTH_SHORT).show();
				
				return;

			}
			
//			// Log
//			msg_Log = "File => is a file";
//			Log.d("MainActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
			
		} else if (f.isDirectory()) {
			
			Ops.go_Down_Dir(this, item);
			
//			// Log
//			msg_Log = "File => is a directory";
//			Log.d("MainActv.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
			
		} else {
			
			// Log
			msg_Log = "File => unknown type";
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		}
		
//		super.onListItemClick(lv, v, position, id);
		
	}//protected void onListItemClick(ListView l, View v, int position, long id)

	private void
	_ItemClick_SetPref_CurrentPosition(int position) {
		// TODO Auto-generated method stub
		Methods.set_Pref_Int(
				this,
				CONS.Pref.pname_MainActv,
				CONS.Pref.pkey_CurrentPosition_MainActv,
//				CONS.Pref.pkey_CurrentPosition,
				position);
		
		// Log
//		String msg_log = "Pref: " + CONS.Pref.pkey_CurrentPosition
		String msg_log = "Pref: " + CONS.Pref.pkey_CurrentPosition_MainActv
						+ " => "
						+ "Set to: " + position;
		
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_log);
		
		CONS.MainActv.aAdapter.notifyDataSetChanged();

	}

	private String _ItemClick_GetItem(ListView lv, int position) {
		// TODO Auto-generated method stub
		String item = (String) lv.getItemAtPosition(position);
		
		if (item != null) {
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "item=" + item);
			
		} else {//if (item_)
			
			// Log
			Log.d("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "item == null");
			
		}//if (item_)

		return item;
		
	}

	@Override
	protected void onDestroy() {
		/*----------------------------
		 * 1. Reconfirm if the user means to quit
		 * 2. super
		 * 3. Clear => prefs_main
		 * 4. Clear => list_root_dir
			----------------------------*/
		// Log
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onDestroy()");
		
		super.onDestroy();
		
	}//protected void onDestroy()

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		Methods.confirm_quit(this, keyCode);
		
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.menu_main, menu);
//		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.opt_menu_main_db://----------------------------------
			
			Methods_dlg.dlg_Db_Activity(this);
			
			break;// case R.id.main_opt_menu_create_folder
			
		case R.id.opt_menu_main_create_dir://----------------------------------
			
//			Methods.create_Dir(this);
			Methods_dlg.dlg_Create_Dir(this);
			
			break;// case R.id.main_opt_menu_create_folder
			
		case R.id.opt_menu_main_settings://----------------------------------
			
			Methods.start_Activity_PrefActv(this);
			
			break;// case R.id.main_opt_menu_create_folder
			
		case R.id.opt_menu_main_search://----------------------------------

			Methods_dlg.dlg_SeratchItem(this);
			
			break;// case R.id.main_opt_menu_create_folder
			
		}//switch (item.getItemId())
		
		return super.onOptionsItemSelected(item);
		
	}//public boolean onOptionsItemSelected(MenuItem item)

	@Override
	protected void onPause() {
		
		super.onPause();

		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onPause()");

//		// Log
//		Log.d("MainActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "prefs_main: " + Methods.get_currentPath_from_prefs(this));
		
		
	}

	@Override
	protected void onResume() {
		/*********************************
		 * 1. super
		 * 2. Set enables
		 *********************************/
		super.onResume();

		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onResume()");

		
		/*********************************
		 * 2. Set enables
		 *********************************/
//		ImageButton ib_up = (ImageButton) findViewById(R.id.v1_bt_up);
//		
//		String curDirPath = Methods.get_currentPath_from_prefs(this);
//		
//		if (curDirPath.equals(dname_base)) {
//			
//			ib_up.setEnabled(false);
//			
//			ib_up.setImageResource(R.drawable.ifm8_up_disenabled);
//			
//		} else {//if (this.currentDirPath == this.dpath_base)
//		
//			ib_up.setEnabled(true);
//
//			
//			ib_up.setImageResource(R.drawable.ifm8_up);
//		
//		}//if (this.currentDirPath == this.dpath_base)
		
	}//protected void onResume()

	@Override
	protected void onStart() {

//        ////////////////////////////////
//
//		// debug
//
//		////////////////////////////////
//		this.do_debug();

		///////////////////////////////////
		//
		// re-install
		//
		///////////////////////////////////
		reinstall_App();
		
        ////////////////////////////////

		// Set dir list

		////////////////////////////////
        boolean res_b = _Setup_DirList();
		
        if (res_b == false) {
			
        	// debug
        	String msg_log = "Dir list => Can't be set";
			Toast.makeText(this, msg_log, Toast.LENGTH_SHORT).show();
        	
        	// Log
			Log.e("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return;
        	
		}

        ////////////////////////////////

		// Set: listeners

		////////////////////////////////
		_Setup_SetListeners();
		
		////////////////////////////////

		// Display: path

		////////////////////////////////
		_Setup_DisplayPath();
		
		////////////////////////////////

		// UI: Imagebutton: Up

		////////////////////////////////
		_Setup_UI_IB_Up();
		
		super.onStart();
	}//protected void onStart()

	private void reinstall_App() {
		// TODO Auto-generated method stub
		
        // Log
		String msg_Log;
		
		//REF model name http://stackoverflow.com/questions/7071281/get-android-device-name answered Aug 15 '11 at 22:07
		msg_Log = String.format(
				Locale.JAPAN,
				"model name => %s", android.os.Build.MODEL
				);
		
		Log.d("MainActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		///////////////////////////////////
		//
		// edit: paths
		//
		///////////////////////////////////
		///////////////////////////////////
		//
		// adjust: adjust paths by model names
		//
		///////////////////////////////////
		// data root path
		if (android.os.Build.MODEL.equals(CONS.DB.modelname_IS13SH)) {

			CONS.DB.dPath_Data_Root = "/mnt/sdcard/cm7_data";
			
			CONS.DB.dPath_dbFile_backup 
									= CONS.DB.dPath_Data_Root + "/backup";
//			= "/mnt/sdcard-ext/cm7_backup";
			
			CONS.DB.dPath_dbFile_backup_CM6 = 
										"/mnt/sdcard/cm5_backup";
			
			CONS.DB.dPath_Lab 
									= CONS.DB.dPath_Data_Root + "/lab";
//			"/mnt/sdcard-ext/cm7_data/lab";
//			cm7_test_wave_20140927_075811.wav
			
			CONS.DB.dPath_Log 
									= CONS.DB.dPath_Data_Root + "/log";

			CONS.Paths.dpath_Storage_Sdcard = "/mnt/sdcard";
			
			
			// Log
//			String msg_Log;
			
			msg_Log = String.format(
					Locale.JAPAN,
					"paths => modified"
					);
			
			Log.i("MainActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
	}//reinstall_App

	private void _Setup_UI_IB_Up() {
		// TODO Auto-generated method stub
		
		String root_DirPath = StringUtils.join(
				new String[]{
						CONS.Paths.dpath_Storage_Sdcard, 
						CONS.Paths.dname_Base},
				File.separator);
		
		String currentPath = Methods.get_Pref_String(
						this, 
						CONS.Pref.pname_MainActv, 
						CONS.Pref.pkey_CurrentPath, 
						null);
		
		// If the current path is not the root dir,
		//		then, enaable the "Up" button
		if (currentPath != null
				&& !currentPath.equals(root_DirPath)) {
			
			ImageButton bt_Up = (ImageButton) this.findViewById(R.id.main_bt_up);
			
			bt_Up.setEnabled(true);
			
			bt_Up.setImageDrawable(
					this.getResources().getDrawable(R.drawable.main_up));
			
			// Log
			String msg_Log = "button => enabled";
			Log.d("Ops.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
	}

	private void _Setup_DisplayPath() {
		// TODO Auto-generated method stub
		
		String currentPath = Methods.get_Pref_String(
				this, 
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
					this, 
					CONS.Pref.pname_MainActv, 
					CONS.Pref.pkey_CurrentPath, 
					currentPath);
			
		}
		
		TextView tv_Path = (TextView) this.findViewById(R.id.main_tv_dir_path);
		
		tv_Path.setText(Methods.conv_CurrentPath_to_DisplayPath(currentPath));
		
	}

	

}//public class MainActv extends Activity

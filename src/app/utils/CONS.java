package app.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.adapters.Adp_MainList;

public class CONS {

	// Sort order
	public static enum SORT_ORDER {
			ASC, DEC,
			CREATED_AT,
	};

	

	// Table => show_history
//	public static String tname_show_history = "show_history";
	
	public static enum MoveMode {
		// TIListAdapter.java
		ON, OFF,
		
	}//public static enum MoveMode

	public static class Intent {
		
		public static String bmactv_key_ai_id = "bmactv_key_ai_id";
		
		public static String bmactv_key_table_name = "bmactv_key_table_name";
		
		public static String bmactv_key_position = "bmactv_key_position";
		
		/***************************************
		 * Request codes
		 ***************************************/
		public final static int REQUEST_CODE_SEE_BOOKMARKS = 0;
		
		public final static int REQUEST_CODE_HISTORY = 1;
		
		/***************************************
		 * Result code
		 ***************************************/
		public final static int RESULT_CODE_SEE_BOOKMARKS_OK = 1;
		
		public final static int RESULT_CODE_SEE_BOOKMARKS_CANCEL = 0;
		
	}//public static class Intent
	
	public static class DB {
		
		public static String dbName = "cm5.db";
		
		public static String dPath_dbFile = "/data/data/cm5.main/databases";
		
		public static String dPath_dbFile_backup = "/mnt/sdcard-ext/cm5_backup";
		
//		public static String dPath_dbFile = 
//							Methods.get_DirPath(new MainActv().getFilesDir().getPath());
		
		public static final String tname_BM = "bm";

		public static final String[] cols_bm = {
			"ai_id", "position", "title", "memo", "aiTableName"
		};
		
		public static final String[] cols_bm_full = {
			android.provider.BaseColumns._ID,
			"created_at", "modified_at",
			"ai_id", "position", "title", "memo", "aiTableName"
		};

		public static final String[] col_types_bm = {
			"INTEGER", "INTEGER", "TEXT", "TEXT", "TEXT"
		};
		
	}//public static class DB

	public static class Pref {
		
		public static final String pname_PlayActv = "pname_PlayActv";
		
		public static final String pkey_PlayActv_position = "prefKey_PlayActv_position";
		
		////////////////////////////////

		// MainActv.java

		////////////////////////////////
		
		public static SharedPreferences prefs_MainActv;
		
		public static String pname_MainActv = "main_activity";
//		public static String pname_CurrentPath = "current_path";
		
		public static String pkey_CurrentPath = "pkey_CurrentPath";
		
		public static String pkey_CurrentPosition = "pkey_CurrentPosition";
		
	}

	public static class MainActv {
		
		public static List<String> list_RootDir = null;
		
		public static ArrayAdapter<String> adp_dir_list = null;
		
		public static Adp_MainList aAdapter;
		
	}
	
	public static class PlayActv {
	
		public static TextView tvCurrentPosition;
		
	}
	
	
	public static class Admin {
		
		public static final float DLG_WIDTH_RATIO = 0.8f;
		
		public static final String dName_backup = "cm5_backup";
		
		////////////////////////////////

		// MainActv.java

		////////////////////////////////
		public static String fname_List = "list.txt";
		
		
	}

	public static class Paths {
		////////////////////////////////

		// MainActv.java

		////////////////////////////////
		
		public static String dpath_Storage_Sdcard = "/mnt/sdcard-ext";
		
		public static String dpath_Storage_Internal = "/mnt/sdcard";
		
		public static String  dname_Base = "cm7";
		
	}
}//public class CONS

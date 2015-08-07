package app.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;




import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.preference.EditTextPreference;
import android.provider.MediaStore;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;
import app.adapters.Adp_AIList;
import app.adapters.Adp_BMList;
import app.adapters.Adp_ImpList;
import app.adapters.Adp_ListItems;
import app.adapters.Adp_LogFileList;
import app.adapters.Adp_MainList;
import app.adapters.Adp_AIList_Move;
import app.adapters.Adp_ShowLogFile_List;
import app.adapters.Adp_WordPatterns;
import app.items.AI;
import app.items.BM;
import app.items.LogItem;
import app.items.WordPattern;

public class CONS {

//	// Sort order
//	public static enum SORT_ORDER {
//			ASC, DEC,
//			CREATED_AT,
//	};

	
	

	// Table => show_history
//	public static String tname_show_history = "show_history";
	
	public static enum MoveMode {
		// TIListAdapter.java
		ON, OFF,
		
	}//public static enum MoveMode

	public static class Intent {
		
		////////////////////////////////

		// commons

		////////////////////////////////
		public final static long dflt_LongExtra_value = -1;
		
		public final static int dflt_IntExtra_value = -1;
		
		
		////////////////////////////////
		
		// PlayActv
		
		////////////////////////////////
		// Used in Service_ShowProgress
		public static String iKey_PlayActv_TaskPeriod
								= "iKey_PlayActv_TaskPeriod";
		
		
		////////////////////////////////

		// MainActv

		////////////////////////////////
		public static String iKey_CurrentPath_MainActv = "current_path";

		
		////////////////////////////////

		// ALActv

		////////////////////////////////
		public static String iKey_AI_FilePath_Full = "iKey_AI_FilePath_Full";
		
		public static String iKey_AI_Db_Id = "iKey_AI_Db_Id";
		
		public static String iKey_AI_TableName = "iKey_AI_TableName";
		
		////////////////////////////////

		// BMActv

		////////////////////////////////
		public static String iKey_BMActv_AI_Id = "bmactv_key_ai_id";
//		public static String bmactv_key_ai_id = "bmactv_key_ai_id";
		
		public static String iKey_BMActv_TableName = "bmactv_key_table_name";
		
		public static String iKey_BMActv_Position = "bmactv_key_position";
//		public static String bmactv_key_position = "bmactv_key_position";
		
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
		
		////////////////////////////////

		// ShowLogActv

		////////////////////////////////
		public static final String iKey_LogActv_LogFileName =
													"iKey_LogActv_LogFileName";

	}//public static class Intent
	
	public static class DB {
		////////////////////////////////

		// Paths and names

		////////////////////////////////
		public static String dbName = "cm7.db";
		public static String dbName_CM6 = "cm6.db";
		
		public static String dbName_Importing = dbName_CM6;
		
		public static String dPath_dbFile;
//		public static String dPath_dbFile = "/data/data/cm7.main/databases";
		//		Do not hardcode "/data/"; use Context.getFilesDir().getPath() instead
		

		public static String dPath_Data_Root = "/mnt/sdcard-ext/cm7_data";
		
		public static String dPath_dbFile_backup 
								= dPath_Data_Root + "/backup";
//		= "/mnt/sdcard-ext/cm7_backup";
		
		public static String dPath_dbFile_backup_CM6 = 
									"/mnt/sdcard-ext/cm5_backup";
		
		public static String dPath_Lab 
								= dPath_Data_Root + "/lab";
//		"/mnt/sdcard-ext/cm7_data/lab";
//		cm7_test_wave_20140927_075811.wav
		
		public static String dPath_Log 
								= dPath_Data_Root + "/log";
		
//		public static String dPath_dbFile = 
//							Methods.get_DirPath(new MainActv().getFilesDir().getPath());
		
		public static String fname_DB_Backup_Trunk = "cm7_backup";
		
		public static String fname_DB_Backup_ext = ".bk";
		
		public static String dname_TapeATalk_Sdcard
								= "tapeatalk_records";
		
		public final static String fname_Log = "log.txt";

		public final static String fname_Log_Trunk = "log";
		
		public final static String fname_Log_ext = ".txt";

		////////////////////////////////
		
		// Table: memo_patterns (cm6)
		
		////////////////////////////////
		public static final String tname_MemoPatterns_CM6	= "memo_patterns";
		
		static String[] cols_memo_patterns_CM6 = {"word", "table_name"};
		
		////////////////////////////////
		
		// Table: cm7
		
		////////////////////////////////
		public static final String tname_CM7 = "cm7";

		public static final String[] col_names_CM7 = {
			
			"file_name", "file_path",	// 0, 1
			"title", "memo",			// 2, 3
			"last_played_at",			// 4
			"table_name",				// 5
			"length",					// 6
			"audio_created_at"			// 7
			
		};
		
		public static final String[] col_names_CM7_full = {
			
			android.provider.BaseColumns._ID,	// 0
			"created_at", "modified_at",		// 1, 2
			"file_name", "file_path",			// 3, 4
			"title", "memo",					// 5, 6
			"last_played_at",					// 7
			"table_name",						// 8
			"length",							// 9
			"audio_created_at"					// 10
			
		};

		public static final String[] col_types_CM7 = {
			"TEXT", "TEXT",			// 0, 1
			"TEXT", "TEXT",			// 2,3
			"TEXT",					// 4
			"TEXT",					// 5
			"TEXT",				// 6
//			"INTEGER",				// 6
			"TEXT"					// 7
		};
		
		
		////////////////////////////////

		// Table: BM (bookmark)

		////////////////////////////////
		public static final String tname_BM = "bm";

		public static final String[] col_names_BM = {
			"ai_id", "position", 			// 0,1
			"title", "memo", "aiTableName"	// 2,3,4
		};
		
		public static final String[] col_names_BM_full = {
			android.provider.BaseColumns._ID,		// 0
			"created_at", "modified_at",			// 1,2
			"ai_id", "position",					// 3,4
			"title", "memo", "aiTableName"			// 5,6,7
		};

		public static final String[] col_types_BM = {
//			"INTEGER", "INTEGER",			// 0,1
			"INTEGER", "TEXT",			// 0,1
			"TEXT", "TEXT", "TEXT"			// 2,3,4
		};

		////////////////////////////////
		
		// Table: BMStore (bookmark)
		
		////////////////////////////////
		public static final String tname_BMStore = "bmstore";
		
		public static final String[] col_names_BMStore = {
			"ai_name", "position",				// 0 1
			"title", "memo",					// 2 3
			"orig_created_at", "orig_modified_at",	// 4 5
		};
		
		public static final String[] col_names_BMStore_full = {
			android.provider.BaseColumns._ID,	// 0
			"created_at", "modified_at",		// 1,2
			"ai_name", "position",				// 3,4
			"title", "memo",					// 5,6
			"orig_created_at", "orig_modified_at",	// 7,8
		};
		
		public static final String[] col_types_BMStore = {
			"TEXT", "TEXT",				// 0 1
			"TEXT", "TEXT",					// 2 3
			"TEXT", "TEXT",	// 4 5
//			"INTEGER", "TEXT",			// 0,1
//			"TEXT", "TEXT", "TEXT"			// 2,3,4
		};
		
		////////////////////////////////
		
		// Table: refresh_history
		
		////////////////////////////////
		public static String tname_RefreshHistory = "refresh_history";
		
		public static String[] col_names_RefreshHistory = {
			"last_refreshed", "num_of_items_added"
		};
		
		public static String[] col_names_RefreshHistory_full = {
			android.provider.BaseColumns._ID,		// 0
			"created_at", "modified_at",			// 1,2
			"last_refreshed", "num_of_items_added"	// 3,4
		};
		
		public static String[] col_types_RefreshHistory = {
			"TEXT", 			"INTEGER"
//			"INTEGER", 			"INTEGER"
		};

		////////////////////////////////
		
		// Table: memo_patterns
		
		////////////////////////////////
		public static String tname_MemoPatterns = "memo_patterns";
		
		public static String[] col_names_MemoPatterns = {
			"word"
		};
		
		public static String[] col_names_MemoPatterns_full = {
			android.provider.BaseColumns._ID,		// 0
			"created_at", "modified_at",			// 1,2
			"word"									// 3
		};
		
		public static String[] col_types_MemoPatterns = {
			"TEXT"
//			"INTEGER", 			"INTEGER"
		};
		

		////////////////////////////////

		// Others

		////////////////////////////////
		public static String jointString_TableName = "__";
		
		public static int pastXDays		= -10;
		
		public static Object modelname_IS13SH	= "IS13SH";

		////////////////////////////////

		// FileFilter

		////////////////////////////////
		public static enum FFType {
			
			RefreshHistory
		}
	
		////////////////////////////////

		// others

		////////////////////////////////
		public static final long logFile_MaxSize = 40000;
		
	}//public static class DB

	public static class Pref {
		////////////////////////////////

		// Commons

		////////////////////////////////
		public static long dflt_LongExtra_value = -1;
		
		public static int dflt_IntExtra_value = -1;
		
//		public static final String pkey_CurrentPath =
//							"pkey_CurrentPath";
		
		////////////////////////////////

		// PlayActv.java

		////////////////////////////////
		public static final String pname_PlayActv = "pname_PlayActv";
		
		public static final String pkey_PlayActv_CurrentPosition = 
							"pkey_PlayActv_CurrentPosition";
		
		public static final String pkey_PlayActv_CurrentFileName = 
							"pkey_PlayActv_CurrentFileName";
		
		////////////////////////////////

		// MainActv.java

		////////////////////////////////
		
		public static SharedPreferences prefs_MainActv;
		
		public static String pname_MainActv = "main_activity";
//		public static String pname_CurrentPath = "current_path";
		
		public static final String pkey_CurrentPath = "pkey_CurrentPath";
		
		public static String pkey_CurrentPosition_MainActv = 
									"pkey_CurrentPosition_MainActv";
		
		////////////////////////////////

		// ALActv

		////////////////////////////////
		public static SharedPreferences prefs_ALActv;
		
		public static String pname_ALActv = "al_activity";
		
		public static String pkey_CurrentPosition_ALActv
									= "pkey_CurrentPosition_ALActv";
		
		public static String pkey_ALActv__CurPath_Move
									= "pkey_ALActv__CurPath_Move";
		
		// standard, search, history, a.o.
		public static final String pkey_TNActv__ListType
									= "pkey_TNActv__ListType";
		
		////////////////////////////////
		
		// BMActv
		
		////////////////////////////////
		public static SharedPreferences prefs_BMActv;
		
		public static String pname_BMActv = "bm_activity";
		
		public static String pkey_CurrentPosition_BMActv
							= "pkey_CurrentPosition_BMActv";
		
		public static String pkey_LastVisiblePosition_BMActv
							= "pkey_LastVisiblePosition_BMActv";

		////////////////////////////////
		
		// ImpActv
		
		////////////////////////////////
		public static String pname_ImpActv = "pname_ImpActv";
		
		public static String pkey_ImpActv_CurrentPath
										= "pkey_ImpActv_CurrentPath";

		////////////////////////////////

		// LogActv

		////////////////////////////////
		public static String pkey_CurrentPosition_LogActv = 
									"pkey_CurrentPosition_LogActv";
		
	}//public static class Pref

	public static class MainActv {
		
		public static List<String> list_RootDir = null;
		
		public static ArrayAdapter<String> adp_dir_list = null;
		
		public static Adp_MainList aAdapter;
		
		public static Adp_ListItems adp_ListItems_DB;
		
		public static Adp_ListItems adp_ListItems_Operations;
		
		public static String prefval_CurrentPath;
		
	}

	public static class BMActv {
		
		public static AI ai;
		
		public static List<BM> bmList;
		
		public static enum SortOrder {
			
			POSITION,
			
		}
		
		public static Adp_BMList aAdapter;
	}
	
	public static class ImpActv {
		
		public static final String top_ImpDir = 
							CONS.Paths.dpath_Storage_Sdcard;
	
		public static Adp_ImpList adp_Imp;
		
		public static String currentPath;
		
		public final static String upDir = "..";
		
		public static List<String> dir_List;
		
	}
	
	public static class ALActv {
		
		public static String currentPath;
		
		public static List<AI> list_AI;
		
		public static Adp_AIList adp_AIList;
		
		public static Adp_AIList_Move adp_TNActv_Main_Move;
		
		public static int display_TopPosition_Current = -1;
		public static int display_TopPosition_Previous = -1;
		
		public final static int TITLE_MAX_LENGTH	= 15;
//		public final static int TITLE_MAX_LENGTH	= 12;

		// ALActv, long click, move files
		public static ArrayAdapter<String> adp_DirList;
		
		// ALActv, long click, move files
		public static List<String> dir_List;
		
		public static boolean moveMode;
		
		public static Menu menu;
		
		public static List<Integer> checkedPositions = new ArrayList<Integer>();
		
		// MainActv: option "Search"
		public static List<Long> searchedItems;
		
		public static int list_Pos_Current = -1;
		public static int list_Pos_Prev = -1;
		
		public static boolean searchDone	= false;

		///////////////////////////////////
		//
		// enums
		//
		///////////////////////////////////
		public static enum SaveLoadBMs {
			
			SaveBM, LoadBM,
			
		}
		
	}//public static class ALActv

	public static class PlayActv {
	
		public static TextView tvCurrentPosition;
		
		public static AI ai;
		
		public static MediaPlayer mp = null;
		
		public static SeekBar sb;
		
		public static Adp_WordPatterns adp_Patterns_GV_1;
		
		public static Adp_WordPatterns adp_Patterns_GV_2;
		
		public static Adp_WordPatterns adp_Patterns_GV_3;
		
		public static Adp_WordPatterns adp_Dlg_EditTitle_LV_1;
		
		public static Adp_WordPatterns adp_Dlg_EditTitle_LV_2;
		
		public static Adp_WordPatterns adp_Dlg_EditTitle_LV_3;
		
		public static List<WordPattern> list_Dlg_EditTitle_LV_1;
		
		public static List<WordPattern> list_Dlg_EditTitle_LV_2;
		
		public static List<WordPattern> list_Dlg_EditTitle_LV_3;
		
		////////////////////////////////

		// Commons

		////////////////////////////////
//		public static int playActv_task_Period = 3000;
		public static int playActv_task_Period = 1000;
		
		public static long playActv_InitialPosition = 0; 
		
		// Step value => file length / value
		//		=> forward length per click
		public static int stepValue;
//		public static int stepValue	= 60;
		
		// Default step value for forward/backward
		//	=> unit = mill seconds
		public static int dflt_StepValue = 60000;	// 1 minute
		
		/******************************
			Intent value receiver
		 ******************************/
		public static String ai_FilePath_Full;
		
		public static long ai_Db_Id;
		
		public static String ai_TableName;
		
		
	}
	
	
	public static class Admin {
		////////////////////////////////

		// commons

		////////////////////////////////
		public static final float DLG_WIDTH_RATIO = 0.8f;
		
		public static final String dName_backup = "cm5_backup";
		
		public static final String dirString_UpperDir	= "..";
		
		public static final String char_Space_Half	= " ";
		
		public static final String char_Space_Whole	= "　";
		
		////////////////////////////////

		// MainActv.java

		////////////////////////////////
		public static String fname_List = "list.txt";
		
		////////////////////////////////

		// Utilities

		////////////////////////////////
		public static Vibrator vib;

		public static int ratio_Dialog_to_Screen_W	= 60;	// X out of 100
		
		public static final int vibLength_click = 35;
		
		public static final String format_Date = "yyyy/MM/dd HH:mm:ss.SSS";
//		public static final String date_Format = "yyyy/MM/dd hh:mm:ss.SSS";
		
		public static final String format_Clock = "%02d:%02d";
		
	}//public static class Admin

	public static class Paths {
		////////////////////////////////

		// MainActv.java

		////////////////////////////////
		
		public static String dpath_Storage_Sdcard = "/mnt/sdcard-ext";
		
		public static String dpath_Storage_Internal = "/mnt/sdcard";
		
		public static String  dname_Base = "cm7";
		
	}
	
	public static class Retval {
		////////////////////////////////

		// Errors

		////////////////////////////////
		/******************************
			Refresh DB
		 ******************************/
		public static int CantCreateTable =		-10;
		
		public static int CantRefreshAudioDir=	-11;
		
		public static int NoNewFiles =			-12;
		
		
		
	}

	public static class Enums {
		
		public static enum SortType {
			
			FileName, POSITION, WORD, CREATED_AT,
			
		}

		// Sort order
		public static enum SortOrder {
				ASC, DEC,
				CREATED_AT, DESC,
		};

		public static enum ListType {
			
			STANDARD, SEARCH, HISTORY, ANY,
			
		}

		public static enum MoveMode {
			// TIListAdapter.java
			ON, OFF,
			
		}//public static enum MoveMode

	}

	public static class PrefActv {
		
		public static EditTextPreference prefEditText;
		
	}

	public static class LogActv {
		
		public static List<String> list_LogFiles = null;
		
//		public static ArrayAdapter<String> adp_LogFile_List = null;
		
		public static Adp_LogFileList adp_LogFile_List;
		
	}

	public static class ShowLogActv {
		
		public static List<LogItem> list_ShowLog_Files = null;
		
//		public static ArrayAdapter<String> adp_LogFile_List = null;
		
		public static Adp_ShowLogFile_List adp_ShowLog_File_List;
		
		public static String fname_Target_LogFile = null;
		
		public static List<String> list_RawLines = null;
		
	}

}//public class CONS

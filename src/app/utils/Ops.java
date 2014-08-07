package app.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cm7.main.R;

import android.app.Activity;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import app.items.AI;
import app.items.Refresh;

public class Ops {

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
			Log.e("Ops.java" + "["
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
			
//			// Log
//			String msg_log = "New files => none";
//			Log.d("Ops.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_log);
//			
//			// debug
//			Toast.makeText(actv, msg_log, Toast.LENGTH_SHORT).show();
			
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
		Log.i("Ops.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		/****************************
		 * 9. Close db
			****************************/
		wdb.close();
		
		////////////////////////////////

		// report

		////////////////////////////////
//		// debug
//		String msg_Toast = "New items => " + numOfItemsAdded;
//		Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
		
		
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
			Log.d("Ops.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			// Log
			msg_Log = "ai.getAudio_created_at() = " + ai.getAudio_created_at();
			Log.d("Ops.java" + "["
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
		Log.d("Ops.java" + "["
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
//		Log.d("Ops.java" + "["
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
			Log.d("Ops.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_log);
			
			return null;
			
		}
		
		// Log
		String msg_log = "audioFile_list => " + audioFile_list.length
						+ " // "
						+ "audioFile_list_Filtered => "
						+ audioFile_list_Filtered.length;
		
		Log.d("Ops.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_log);
		
//		for (File file : audioFile_list_Filtered) {
//			
//			// Log
//			msg_log = "file = " + file.getAbsolutePath();
//			Log.d("Ops.java" + "["
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

			Log.e("Ops.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table doesn't exist: " + tableName);
			
			result = 
					dbu.createTable(actv, 
							CONS.DB.tname_CM7, CONS.DB.col_names_CM7, 
							CONS.DB.col_types_CM7);
			
			if (result == false) {

				Log.e("Ops.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Can't create a table: "+ tableName);
				
				return false;
				
			} else {//if (result == false)
				
				Log.i("Ops.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Table created: "+ tableName);
				
				return true;
				
			}//if (result == false)

		} else {//if (result == false)
			
			Log.i("Ops.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Table exists: "+ tableName);

			return true;
			
		}//if (result == false)
		
	}//_refresh_MainDB__Setup_Table

	public static boolean
	insert_FileToTable
	(Activity actv, 
		String currentPath, String file_Name) {
		// TODO Auto-generated method stub
		
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();

		////////////////////////////////

		// Table exists?

		////////////////////////////////
		boolean res = _refresh_MainDB__Setup_Table(actv, wdb, dbu);
//		boolean res = refreshMainDB_1_set_up_table(wdb, dbu);
		
		if (res == false) {
			
			// Log
			Log.e("Ops.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Can't  create table");
			
			wdb.close();
			
			return false;
			
		}//if (res == false)		

		////////////////////////////////

		// prep

		////////////////////////////////
		File file = new File(currentPath, file_Name);
		
		long audioLength = Methods.get_AudioLength(file.getAbsolutePath());
		
		////////////////////////////////

		// Build: AI

		////////////////////////////////
		AI ai = new AI.Builder()
				.setFile_name(file.getName())
				
				//REF http://www.xinotes.net/notes/note/774/
				.setFile_path(file.getParent())
//				.setFile_path(file.getPath())
				
				.setTable_name(CONS.DB.tname_CM7)
				.setLength(Methods.conv_MillSec_to_ClockLabel(audioLength))
//				.setLength(Methods.conv_MillSec_to_ClockLabel(file.length()))
//				.setLength(file.length())
				.setAudio_created_at(
						Methods.conv_MillSec_to_TimeLabel(file.lastModified()))
				.build();
		
		//debug
		
		res = DBUtils.insertData_AI(actv, ai);
		
		if (res == true) {
			
			// Log
			String msg_Log = "data => inserted";
			Log.d("Ops.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
//			numOfItemsAdded ++;
			
		} else {
			
			// Log
			String msg_Log = "data => not inserted";
			Log.d("Ops.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
//		ai_List.add(ai);
		
		//debug
		wdb.close();
		
//		// Log
//		String msg_Log = "insert => done";
//		Log.d("Ops.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);

		////////////////////////////////

		// report

		////////////////////////////////
		if (res == true) {
			
			String msg = "Data => Inserted: " + ai.getFile_name();
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
		} else {
			
			String msg = "Data => Not inserted: " + ai.getFile_name();
			Methods_dlg.dlg_ShowMessage(actv, msg);

		}

		// null
		ai = null;
		
		////////////////////////////////

		// return

		////////////////////////////////
		return res;
		
	}//insert_FileToTable

	public static void 
	del_Folder
	(Activity actv, Dialog dlg1, Dialog dlg2,
			String folderName) {
		// TODO Auto-generated method stub
		
		String currentPath = Methods.get_Pref_String(
									actv, 
									CONS.Pref.pname_MainActv, 
									CONS.Pref.pkey_CurrentPath, 
									null);
		
		// Log
		String msg_Log = "currentPath => " + currentPath;
		Log.d("Ops.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		File target_Dir = new File(currentPath, folderName);
		
		/******************************
			validate: exists?
		 ******************************/
		if (!target_Dir.exists()) {
			
			String msg = "Folder doesn't exist: " + folderName;
			Toast.makeText(actv, msg, Toast.LENGTH_SHORT).show();
//			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			dlg2.dismiss();
			
			return;
			
		}
		
		////////////////////////////////

		// del: folder

		////////////////////////////////
		boolean res = Methods.deleteDirectory(target_Dir);
		
		if (res == false) {
			
			String msg = "delete dir => not done";
			Toast.makeText(actv, msg, Toast.LENGTH_SHORT).show();
//			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			dlg2.dismiss();
			
			return;
			
		}
		
		////////////////////////////////

		// del: table

		////////////////////////////////
		String tname = Methods.conv_CurrentPath_to_TableName(target_Dir.getAbsolutePath());
		
		res = Methods.drop_Table(actv, tname);
		
		if (res == false) {
			
			String msg = "Table => not dropped: " + tname;
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
		}
		
		////////////////////////////////

		// del: list item

		////////////////////////////////
		CONS.MainActv.list_RootDir.remove(folderName);
//		CONS.MainActv.list_RootDir.clear();
//		
//		CONS.MainActv.list_RootDir.addAll(
//						Methods.get_FileList(new File(currentPath)));
		
		////////////////////////////////

		// notify

		////////////////////////////////
		CONS.MainActv.aAdapter.notifyDataSetChanged();
//		CONS.MainActv.adp_dir_list.notifyDataSetChanged();
		
		// Log
		msg_Log = "adapter => notified";
		Log.d("Ops.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////
		
		// dismiss
		
		////////////////////////////////
		dlg2.dismiss();
		dlg1.dismiss();
		
		
	}//del_Folders

	/******************************
		@param dirName => The function doesn't validate if the dir exists
	 ******************************/
	public static void 
	go_Down_Dir
	(Activity actv, String dirName) {
		// TODO Auto-generated method stub

		////////////////////////////////

		// new file

		////////////////////////////////
		String currentPath = Methods.get_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				null);
		
		File dpath_New = new File(currentPath, dirName);
		
		String newPath = dpath_New.getAbsolutePath();
		
		// Log
		String msg_Log = "new path => " + dpath_New.getAbsolutePath();
		Log.d("Ops.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		String tname_New = 
				Methods.conv_CurrentPath_to_TableName(dpath_New.getAbsolutePath());
		
		msg_Log = "tname_New =>" +
				" " + tname_New;
		Log.d("Ops.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// list_RootDir

		////////////////////////////////
		CONS.MainActv.list_RootDir.clear();
		
		CONS.MainActv.list_RootDir.addAll(
							Methods.get_FileList(new File(newPath)));
		
		////////////////////////////////

		// notify

		////////////////////////////////
		CONS.MainActv.aAdapter.notifyDataSetChanged();
		
		////////////////////////////////

		// pref

		////////////////////////////////
		boolean res = Methods.set_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				newPath);
		
		////////////////////////////////

		// Button: up

		////////////////////////////////
		ImageButton bt_Up = (ImageButton) actv.findViewById(R.id.main_bt_up);
		
		bt_Up.setEnabled(true);
		
//		bt_Up.setBackgroundResource(R.drawable.main_up);
		
		bt_Up.setImageDrawable(actv.getResources().getDrawable(R.drawable.main_up));
		
		// Log
		msg_Log = "button => enabled";
		Log.d("Ops.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// Show path

		////////////////////////////////
		TextView tv_Path = (TextView) actv.findViewById(R.id.main_tv_dir_path);
		
		tv_Path.setText(Methods.conv_CurrentPath_to_DisplayPath(newPath));
		
		
	}//go_Down_Dir
	
	public static void 
	go_Up_Dir
	(Activity actv) {
		// TODO Auto-generated method stub
		
		////////////////////////////////
		
		// new file
		
		////////////////////////////////
		String currentPath = Methods.get_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				null);
		
		File dir_New = new File(currentPath).getParentFile();
		
		String newPath = dir_New.getAbsolutePath();
		
		String tname_New = 
				Methods.conv_CurrentPath_to_TableName(newPath);
		
		String msg_Log = "tname_New =>" +
				" " + tname_New;
		Log.d("Ops.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////
		
		// list_RootDir
		
		////////////////////////////////
		CONS.MainActv.list_RootDir.clear();
		
		CONS.MainActv.list_RootDir.addAll(
				Methods.get_FileList(new File(newPath)));
		
		////////////////////////////////
		
		// notify
		
		////////////////////////////////
		CONS.MainActv.aAdapter.notifyDataSetChanged();
		
		////////////////////////////////
		
		// pref
		
		////////////////////////////////
		boolean res = Methods.set_Pref_String(
				actv, 
				CONS.Pref.pname_MainActv, 
				CONS.Pref.pkey_CurrentPath, 
				newPath);
		
		////////////////////////////////
		
		// Button: up
		
		////////////////////////////////
		String root_DirPath = StringUtils.join(
				new String[]{
						CONS.Paths.dpath_Storage_Sdcard, 
						CONS.Paths.dname_Base},
				File.separator);
		
		// If the new dir is the root dir,
		//		then, disable the "Up" button
		if (newPath.equals(root_DirPath)) {
			
			ImageButton bt_Up = (ImageButton) actv.findViewById(R.id.main_bt_up);
			
			bt_Up.setEnabled(false);
			
			bt_Up.setImageDrawable(
					actv.getResources().getDrawable(R.drawable.main_up_disenabled));
			
			// Log
			msg_Log = "button => disabled";
			Log.d("Ops.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		////////////////////////////////
		
		// Show path
		
		////////////////////////////////
		TextView tv_Path = (TextView) actv.findViewById(R.id.main_tv_dir_path);
		
		tv_Path.setText(Methods.conv_CurrentPath_to_DisplayPath(newPath));
		
	}//go_Down_Dir

}

package app.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cm7.main.R;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import app.adapters.Adp_ImpList;
import app.adapters.Adp_MainList;
import app.listeners.LV_OTL;
import app.utils.CONS;
import app.utils.Methods;
import app.utils.Methods_dlg;

public class ImpActv extends ListActivity {

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
//        setContentView(R.layout.actv_imp);
        
        /*----------------------------
		 * 2-2. Set title
			----------------------------*/
		this.setTitle(this.getClass().getName());
        
        
    }//public void onCreate(Bundle savedInstanceState)

	@Override
	public void onBackPressed() {
		/****************************
		 * memo
			****************************/
		this.finish();
		
		overridePendingTransition(0, 0);
		
	}//public void onBackPressed()

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// item

		////////////////////////////////
		String item = (String) l.getItemAtPosition(position);
		
		////////////////////////////////

		// File

		////////////////////////////////
		File dpath_Current = new File(CONS.ImpActv.currentPath, item);
		
		////////////////////////////////

		// click

		////////////////////////////////
		if (dpath_Current.exists() && dpath_Current.isFile()) {

			// debug
			String msg_Toast = "File";
			Toast.makeText(this, msg_Toast, Toast.LENGTH_SHORT).show();
			
		} else if (dpath_Current.exists() && dpath_Current.isDirectory()) {

			_onListItemClick__IsDir(item, dpath_Current);
			
		} else {

			Methods_dlg.dlg_ShowMessage(
						this, "Unknown file: " + dpath_Current.getAbsolutePath());

		}
		
		
		super.onListItemClick(l, v, position, id);
	}

	private void 
	_onListItemClick__IsDir
	(String item, File dpath_Current) {
		
		////////////////////////////////

		// dispatch

		////////////////////////////////
		if (item.equals(CONS.ImpActv.upDir)) {
			
			// if already in the top dir
			if (CONS.ImpActv.currentPath.equals(CONS.ImpActv.top_ImpDir)) {
				
				Methods_dlg.dlg_ShowMessage(this, "Already in the top dir");
				
				return;
				
			}
			
			_onListItemClick__UpDir();
			
		} else {
			
			_onListItemClick__DownDir(item, dpath_Current);

		}
		
	}//_onListItemClick__IsDir

	private void 
	_onListItemClick__DownDir
	(String item, File dpath_New) {
//		(String item, File dpath_Current) {
		// TODO Auto-generated method stub
		
//		File dpath_New = dpath_Current;
//		File dpath_New = new File(dpath_Current, item);
		
		// Log
		String msg_Log = "dpath_New => " + dpath_New;
		Log.d("ImpActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		
//		File parent = dpath_New.getParentFile();
		
		////////////////////////////////

		// get: list

		////////////////////////////////
		String[] fnames = dpath_New.list();
		
//		CONS.ImpActv.dir_List = new ArrayList<String>();
//		List<String> dir_List = new ArrayList<String>();
		CONS.ImpActv.dir_List.clear();
		
		CONS.ImpActv.dir_List.add(CONS.ImpActv.upDir);
		
		for (String name : fnames) {
			
			CONS.ImpActv.dir_List.add(name);
			
		}
		
		Collections.sort(CONS.ImpActv.dir_List);
		
		CONS.ImpActv.adp_Imp.notifyDataSetChanged();
		
		////////////////////////////////

		// set: pref

		////////////////////////////////
		CONS.ImpActv.currentPath = dpath_New.getAbsolutePath();
		
		Methods.set_Pref_String(
				this, 
				CONS.Pref.pname_ImpActv, 
				CONS.Pref.pkey_ImpActv_CurrentPath, 
				CONS.ImpActv.currentPath);

		// Log
		msg_Log = "current path => set: " + CONS.ImpActv.currentPath;
//		String msg_Log = "current path => set: " + CONS.ImpActv.currentPath;
		Log.d("ImpActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}//_onListItemClick__DownDir

	private void 
	_onListItemClick__UpDir() {
		// TODO Auto-generated method stub
		File f = new File(CONS.ImpActv.currentPath);
		
		File parent = f.getParentFile();
		
		////////////////////////////////

		// get: list

		////////////////////////////////
		String[] fnames = parent.list();
		
//		CONS.ImpActv.dir_List = new ArrayList<String>();
//		List<String> dir_List = new ArrayList<String>();
		CONS.ImpActv.dir_List.clear();
		
		CONS.ImpActv.dir_List.add(CONS.ImpActv.upDir);
		
		for (String name : fnames) {
			
			CONS.ImpActv.dir_List.add(name);
			
		}
		
		Collections.sort(CONS.ImpActv.dir_List);
		
		CONS.ImpActv.adp_Imp.notifyDataSetChanged();
		
		////////////////////////////////

		// set: pref

		////////////////////////////////
		CONS.ImpActv.currentPath = parent.getAbsolutePath();
		
		Methods.set_Pref_String(
				this, 
				CONS.Pref.pname_ImpActv, 
				CONS.Pref.pkey_ImpActv_CurrentPath, 
				CONS.ImpActv.currentPath);

		// Log
		String msg_Log = "current path => set: " + CONS.ImpActv.currentPath;
		Log.d("ImpActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}//_onListItemClick__UpDir()

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// general

		////////////////////////////////
		_Setup_General();
		
		
		////////////////////////////////

		// setup: dir list

		////////////////////////////////
		_Setup_DirList();
		
		////////////////////////////////

		// listeners

		////////////////////////////////
		_Setup_SetListeners();
		
		super.onStart();
	}

	private void _Setup_General() {
		// TODO Auto-generated method stub
		
		CONS.ImpActv.currentPath = Methods.get_Pref_String(
						this, 
						CONS.Pref.pname_ImpActv, 
						CONS.Pref.pkey_ImpActv_CurrentPath, 
						null);
		
		if (CONS.ImpActv.currentPath == null) {
					
			CONS.ImpActv.currentPath = CONS.ImpActv.top_ImpDir;
					
			Methods.set_Pref_String(
					this, 
					CONS.Pref.pname_ImpActv, 
					CONS.Pref.pkey_ImpActv_CurrentPath, 
					CONS.ImpActv.top_ImpDir);
					
		}
		
	}//private void _Setup_General()

	private void 
	_Setup_SetListeners() {
		// TODO Auto-generated method stub
	
//		ListView lv = this.getListView();
//		
//		lv.setOnTouchListener(new LV_OTL(this));
		
	}//_Setup_SetListeners
	

	private void 
	_Setup_DirList() {
		// TODO Auto-generated method stub
		////////////////////////////////
		
		// set: pref => current path
		
		////////////////////////////////
		String current_Path = Methods.get_Pref_String(
				this, 
				CONS.Pref.pname_ImpActv, 
				CONS.Pref.pkey_ImpActv_CurrentPath, 
				null);
		
		if (current_Path == null) {
			
			current_Path = CONS.ImpActv.top_ImpDir;
			
			Methods.set_Pref_String(
					this, 
					CONS.Pref.pname_ImpActv, 
					CONS.Pref.pkey_ImpActv_CurrentPath, 
					CONS.ImpActv.top_ImpDir);
			
		}
		
		////////////////////////////////

		// file

		////////////////////////////////
		File cur_Dir = new File(current_Path);
//		File top_Dir = new File(CONS.ImpActv.top_ImpDir);
		
		if (cur_Dir.exists() == false) {
			
			String msg = "Path doesn't exist => " + current_Path;
			Methods_dlg.dlg_ShowMessage(this, msg);
			
			return;
			
		}
		
		////////////////////////////////

		// get: list

		////////////////////////////////
		String[] fnames = cur_Dir.list();
		
		CONS.ImpActv.dir_List = new ArrayList<String>();
//		List<String> dir_List = new ArrayList<String>();
		
		CONS.ImpActv.dir_List.add(CONS.ImpActv.upDir);
		
		for (String name : fnames) {
			
			CONS.ImpActv.dir_List.add(name);
			
		}
		
		Collections.sort(CONS.ImpActv.dir_List);
		
		////////////////////////////////

		// adapter

		////////////////////////////////
		CONS.ImpActv.adp_Imp = new Adp_ImpList(
				this,
				R.layout.list_row_actv_imp,
//				android.R.layout.simple_list_item_1,
				CONS.ImpActv.dir_List
				);

		this.setListAdapter(CONS.ImpActv.adp_Imp);
		
	}//_Setup_DirList()
	

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}

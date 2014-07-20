package app.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cm7.main.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
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
		super.onListItemClick(l, v, position, id);
	}

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
					
			Methods.setPref_String(
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
			
			Methods.setPref_String(
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
		
		List<String> dir_List = new ArrayList<String>();
		
		dir_List.add(CONS.ImpActv.upDir);
		
		for (String name : fnames) {
			
			dir_List.add(name);
			
		}
		
		Collections.sort(dir_List);
		
		////////////////////////////////

		// adapter

		////////////////////////////////
		CONS.ImpActv.adp_Imp = new Adp_ImpList(
				this,
				R.layout.list_row_actv_imp,
//				android.R.layout.simple_list_item_1,
				dir_List
				);

		this.setListAdapter(CONS.ImpActv.adp_Imp);
		
	}//_Setup_DirList()
	

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}

package app.listeners.dialog;


import cm7.main.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import app.items.AI;
import app.items.BM;
import app.items.ListItem;
import app.items.WordPattern;
import app.tasks.Task_RefreshDB;
import app.utils.CONS;
import app.utils.Methods;
import app.utils.Methods_dlg;
import app.utils.Ops;
import app.utils.Tags;

public class DOI_CL implements OnItemClickListener {

	//
	Activity actv;
	Dialog d1;
	Dialog d2;
	
	//
	Vibrator vib;
	
	BM bm;
	
	AI ai;
	int aiList_Position;

	String file_Name;	// ImpActv list
	private WordPattern wp;
	
	//
//	Methods.DialogTags dlgTag = null;

	public DOI_CL(Activity actv, Dialog dlg, AI ai, int alList_Position) {
		// 
		this.actv = actv;
		this.d1 = dlg;
		
		this.ai		= ai;
		this.aiList_Position = alList_Position;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)

	public DOI_CL(Activity actv, Dialog dlg, Dialog dlg2) {
		// 
		this.actv = actv;
		this.d1 = dlg;
		this.d2 = dlg2;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)


	public DOI_CL(Activity actv, Dialog dlg, BM bm) {
		// TODO Auto-generated constructor stub
		this.actv	= actv;
		this.d1	= dlg;
		this.bm		= bm;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

	public DOI_CL(Activity actv, Dialog dlg1, AI ai) {
		// TODO Auto-generated constructor stub
		
		this.actv	= actv;
		this.d1	= dlg1;
		this.ai		= ai;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

	public DOI_CL(Activity actv, Dialog dlg1) {
		// TODO Auto-generated constructor stub
		
		this.actv	= actv;
		this.d1	= dlg1;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

	public DOI_CL(Activity actv, Dialog dlg, String file_Name) {
		// TODO Auto-generated constructor stub
		this.actv	= actv;
		this.d1	= dlg;
		this.file_Name	= file_Name;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}

	public 
	DOI_CL
	(Activity actv, 
		Dialog dlg1, Dialog dlg2, 
		AI ai, int aiList_Position) {
		// TODO Auto-generated constructor stub
		this.actv	= actv;
		this.d1	= dlg1;
		this.d2	= dlg2;
		
		this.ai		= ai;
		this.aiList_Position	= aiList_Position;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}

	public DOI_CL
	(Activity actv, Dialog d1, Dialog d2, WordPattern wp) {
		// TODO Auto-generated constructor stub
		
		this.actv	= actv;
		this.d1	= d1;
		this.d2	= d2;

		this.wp	= wp;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

	//	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		/*----------------------------
		 * Steps
		 * 1. Get tag
		 * 2. Vibrate
		 * 3. Switching
			----------------------------*/
		
		Tags.DialogItemTags tag = (Tags.DialogItemTags) parent.getTag();
//		
		vib.vibrate(CONS.Admin.vibLength_click);
		
		String item;
//		String item = (String) parent.getItemAtPosition(position);
		
		ListItem li;
		
		WordPattern wp;
		
		/*----------------------------
		 * 3. Switching
			----------------------------*/
		switch (tag) {
		
//		case dlg_db_admin_lv://----------------------------------------------
		case DLG_DB_ADMIN_LV://----------------------------------------------
			
			li = (ListItem) parent.getItemAtPosition(position);
			
			case_DLG_DB_ADMIN_LV(li);
			
			break;// case dlg_add_memos_gv

//		case dlg_bmactv_list_long_click://----------------------------------------------
		case DLG_BMACTV_LIST_LONGCLICK://----------------------------------------------
			
			item = (String) parent.getItemAtPosition(position);
			
			case_Dlg_BMActv_LongClick(item);
			
			break;// case dlg_bmactv_list_long_click
			
		case DLG_ALACTV_LIST_LONGCLICK://----------------------------------------------
			
			item = (String) parent.getItemAtPosition(position);
			
			case_Dlg_ALActv_LongClick(item);
			
			break;// case dlg_bmactv_list_long_click
			
		case DLG_IMPACTV_LIST://----------------------------------------------
			
			item = (String) parent.getItemAtPosition(position);
			
			case_DLG_IMPACTV_LIST(item);
			
			break;// case dlg_bmactv_list_long_click
			
		case DLG_ACTVMAIN_LONGCLICK://----------------------------------------------
			
			String choice = (String) parent.getItemAtPosition(position);
			
			case_DLG_ACTVMAIN_LONGCLICK(choice);
			
			break;// case dlg_bmactv_list_long_click
			
		case DLG_ALACTV_LIST_MOVE_FILE://----------------------------------------------
			
			choice = (String) parent.getItemAtPosition(position);
			
			case_DLG_ALACTV_LIST_MOVE_FILE(choice);
			
			break;// case dlg_bmactv_list_long_click
			
		case DLG_ACTV_MAIN_OPERATIONS://----------------------------------------------
			
			li = (ListItem) parent.getItemAtPosition(position);
			
			case_DLG_ACTV_MAIN_OPERATIONS(li);
			
			break;// case dlg_bmactv_list_long_click
			
		case DLG_ADD_MEMOS_GV_1://----------------------------------------------
		case DLG_ADD_MEMOS_GV_2://----------------------------------------------
		case DLG_ADD_MEMOS_GV_3://----------------------------------------------
			
			wp = (WordPattern) parent.getItemAtPosition(position);
			
			case_DLG_ADD_MEMOS_GV_1(wp);
			
			break;// case dlg_bmactv_list_long_click
			
		case ACTV_PLAY_PATTERNS_LONGCLICK_LV://----------------------------------------------
			
			li = (ListItem) parent.getItemAtPosition(position);
			
			case_ACTV_PLAY_PATTERNS_LONGCLICK_LV(li);
			
			break;// case dlg_bmactv_list_long_click
			
		default:
			break;
		}//switch (tag)
		
	}//public void onItemClick(AdapterView<?> parent, View v, int position, long id)

	private void 
	case_ACTV_PLAY_PATTERNS_LONGCLICK_LV
	(ListItem li) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// Dispatch

		////////////////////////////////
		if (li.getText().equals(actv.getString(
				R.string.generic_tv_edit))) {

//			Methods_dlg.conf_Import_DB(actv, d1, d2);
			
		} else if (li.getText().equals(actv.getString(
				R.string.generic_tv_delete))) {
			
			Methods_dlg.conf_Delete_Pattern(actv, d1, d2, wp);
			
		} else  {

			String msg = "Unknown choice => " + li.getText();
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.gold2);
			
			return;

		}		
		
	}//case_ACTV_PLAY_PATTERNS_LONGCLICK_LV

	private void 
	case_DLG_ADD_MEMOS_GV_1
	(WordPattern wp) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// view

		////////////////////////////////
		EditText et = (EditText) d1.findViewById(R.id.dlg_edit_ai_title_et_content);
		
		////////////////////////////////

		// build: text

		////////////////////////////////
		String tmp = et.getText().toString();
		
		if (tmp == null || tmp == "") {
			
			tmp = wp.getWord() + " ";
			
		} else {

			tmp = tmp + " " + wp.getWord() + " ";
			
		}
		
		////////////////////////////////

		// set

		////////////////////////////////
		et.setText(tmp);
		
		et.setSelection(tmp.length());

		
//		// Log
//		String msg_Log = "pattern => " + wp.getWord();
//		Log.d("DOI_CL.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
	}

	private void 
	case_DLG_ACTV_MAIN_OPERATIONS
	(ListItem li) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// Dispatch

		////////////////////////////////
		if (li.getText().equals(actv.getString(
				R.string.dlg_db_admin_item_op_imp_db))) {

			Methods_dlg.conf_Import_DB(actv, d1, d2);
			
		} else if (li.getText().equals(actv.getString(
				R.string.dlg_db_admin_item_op_imp_patterns))) {
			
			Methods_dlg.conf_Import_Patterns(actv, d1, d2);
			
		} else  {

			String msg = "Unknown choice => " + li.getText();
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.gold2);
			
			return;

		}		
		
		
	}//case_DLG_ACTV_MAIN_OPERATIONS

	private void 
	case_DLG_ALACTV_LIST_MOVE_FILE
	(String choice) {
		// TODO Auto-generated method stub

		/******************************
			validate: table name and the choice => same?
		 ******************************/
		String tname_New = Methods.conv_CurrentPathMove_to_TableName(choice);

		if (ai.getTable_name().equals(tname_New)) {
			
			String msg = "You are choosing the same table as the current one";
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
			return;
			
		}
		
		////////////////////////////////

		// choice => Upper dir? ("..")

		////////////////////////////////
		if (choice.equals(CONS.Admin.dirString_UpperDir)) {
			
			String msg_Toa = "Upper dir";
			Toast.makeText(actv, msg_Toa, Toast.LENGTH_SHORT).show();
			
			return;
			
		}

		
		Methods_dlg.conf_MoveAi(
						actv, d1, d2, 
						ai, aiList_Position, choice);
		
	}//case_DLG_ALACTV_LIST_MOVE_FILE

	private void 
	case_DLG_ACTVMAIN_LONGCLICK
	(String choice) {
		// TODO Auto-generated method stub

		if (choice.equals(actv.getString(
				R.string.dlg_actvmain_lv_delete))) {	// Edit

			Methods_dlg.conf_DeleteFolder(actv, d1, file_Name, choice);
			
		} else {//if (item.equals(actv.getString(R.string.generic_tv_edit)))
			
			
		}
		
	}//case_DLG_ACTVMAIN_LONGCLICK

	private void 
	case_DLG_IMPACTV_LIST(String item) {
		// TODO Auto-generated method stub

		if (item.equals(actv.getString(
					R.string.dlg_impactv_list_item_import))) {	// Edit
			
			boolean res = Ops.insert_FileToTable(
					actv, CONS.ImpActv.currentPath, file_Name);
			
			if (res == true) {
				
				d1.dismiss();
				
			}
			
		} else {//if (item.equals(actv.getString(R.string.generic_tv_edit)))
			
			
		}
		
		
	}//case_DLG_IMPACTV_LIST(String item)

	private void 
	case_Dlg_ALActv_LongClick(String item) {
		// TODO Auto-generated method stub
		if (item.equals(actv.getString(R.string.generic_tv_edit))) {	// Edit
			
			Methods_dlg.edit_AI(actv, d1, ai, aiList_Position);
			
		} else if (item.equals(actv.getString(R.string.generic_tv_delete))) {
	
			Methods_dlg.conf_DeleteAL(actv, d1, ai, aiList_Position);
			
		} else if (item.equals(actv.getString(
						R.string.dlg_alactv_list_long_click_item_move))) {
			
			Methods_dlg.dlg_Move_AI(actv, d1, ai, aiList_Position);
			
		}//if (item.equals(actv.getString(R.string.generic_tv_edit)))
		
	}//case_Dlg_ALActv_LongClick(String item)

	private void
//	case_Dlg_Db_Admin_lv(String item) {
	case_DLG_DB_ADMIN_LV
	(ListItem li) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// Dispatch

		////////////////////////////////
		if (li.getText().equals(actv.getString(
				R.string.dlg_db_admin_item_exec_sql))) {
			
			Methods.exec_Sql(actv);
			
		} else if (li.getText().equals(actv.getString(
				R.string.dlg_db_admin_item_backup_db))) {
			
			Methods.db_Backup(actv);
			
		} else if (li.getText().equals(actv.getString(		// Refresh DB
				R.string.dlg_db_admin_item_refresh_db))) {
			
			case_DLG_DB_ADMIN_LV__RefreshDB(actv);
			
		} else if (li.getText().equals(actv.getString(		// Refresh DB
				R.string.dlg_db_admin_item_impfile))) {
			
			case_DLG_DB_ADMIN_LV__ImpFile(actv);
			
		} else if (li.getText().equals(actv.getString(		// Refresh DB
				R.string.dlg_db_admin_item_restore_db))) {
			
			case_DLG_DB_ADMIN_LV__RestoreDB(actv);
			
		} else if (li.getText().equals(actv.getString(		// Refresh DB
				R.string.dlg_db_admin_item_operations))) {
			
			case_DLG_DB_ADMIN_LV__Operations(actv);
			
			return;

		} else {

		}
	
		d1.dismiss();
		
	}//case_Dlg_Db_Admin_lv(String item)

	private void 
	case_DLG_DB_ADMIN_LV__Operations
	(Activity actv) {
		// TODO Auto-generated method stub
		
		Methods_dlg.dlg_Db_Operations(actv, d1);
		
	}//case_DLG_DB_ADMIN_LV__Operations

	private void 
	case_DLG_DB_ADMIN_LV__RestoreDB
	(Activity actv) {
		// TODO Auto-generated method stub
		
		boolean res = Methods.restore_DB(actv);
		
		if (res == true) {
			
			d1.dismiss();
			
			String msg = "DB => Restored";
			Methods_dlg.dlg_ShowMessage(actv, msg);
			
		} else {
			
			String msg = "DB => Can't be restored";
			Methods_dlg.dlg_ShowMessage(actv, msg);

		}
		
	}//case_DLG_DB_ADMIN_LV__RestoreDB

	private void 
	case_DLG_DB_ADMIN_LV__ImpFile
	(Activity actv) {
		// TODO Auto-generated method stub
		
		Methods.start_Activity_ImpActv(actv);
		
		
	}//case_DLG_DB_ADMIN_LV__ImpFile

	private void case_DLG_DB_ADMIN_LV__RefreshDB(Activity actv) {
		// TODO Auto-generated method stub
		Task_RefreshDB task = new Task_RefreshDB(actv);
		
		task.execute("Start");
		
	}

	private void
//		case_dlg_bmactv_list_long_click(String item) {
	case_Dlg_BMActv_LongClick(String item) {
		// TODO Auto-generated method stub
		if (item.equals(actv.getString(R.string.generic_tv_edit))) {	// Edit
			
//			// debug
//			Toast.makeText(actv, "Edit", Toast.LENGTH_LONG).show();
			Methods_dlg.edit_BM(actv, d1, bm);
//			bmactv_editItem(bm);
			
		} else if (item.equals(actv.getString(R.string.generic_tv_delete))) {
	
			Methods_dlg.conf_DeleteBM(actv, d1, bm);
//			bmactv_deleteItem(bm);
//			CONS.BMActv.bmList.remove(bm);
//			
//			CONS.BMActv.adpBML.notifyDataSetChanged();
			
		}//if (item.equals(actv.getString(R.string.generic_tv_edit)))
		
		
	}//case_Dlg_BMActv_LongClick(String item)

}//public class DialogOnItemClickListener implements OnItemClickListener

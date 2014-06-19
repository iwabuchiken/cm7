package app.listeners.dialog;


import cm7.main.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import app.items.AI;
import app.items.BM;
import app.utils.CONS;
import app.utils.Methods;
import app.utils.Methods_dlg;
import app.utils.Tags;

public class DOI_CL implements OnItemClickListener {

	//
	Activity actv;
	Dialog dlg1;
	Dialog dlg2;
	
	//
	Vibrator vib;
	
	BM bm;
	
	AI ai;
	int aiList_Position;
	
	//
//	Methods.DialogTags dlgTag = null;

	public DOI_CL(Activity actv, Dialog dlg, AI ai, int alList_Position) {
		// 
		this.actv = actv;
		this.dlg1 = dlg;
		
		this.ai		= ai;
		this.aiList_Position = alList_Position;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)

	public DOI_CL(Activity actv, Dialog dlg, Dialog dlg2) {
		// 
		this.actv = actv;
		this.dlg1 = dlg;
		this.dlg2 = dlg2;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)


	public DOI_CL(Activity actv, Dialog dlg, BM bm) {
		// TODO Auto-generated constructor stub
		this.actv	= actv;
		this.dlg1	= dlg;
		this.bm		= bm;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

	public DOI_CL(Activity actv, Dialog dlg1, AI ai) {
		// TODO Auto-generated constructor stub
		
		this.actv	= actv;
		this.dlg1	= dlg1;
		this.ai		= ai;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

	public DOI_CL(Activity actv, Dialog dlg1) {
		// TODO Auto-generated constructor stub
		
		this.actv	= actv;
		this.dlg1	= dlg1;
		
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
		
		String item = (String) parent.getItemAtPosition(position);
		
		/*----------------------------
		 * 3. Switching
			----------------------------*/
		switch (tag) {
		
//		case dlg_db_admin_lv://----------------------------------------------
		case DLG_DB_ADMIN_LV://----------------------------------------------
			
			case_Dlg_Db_Admin_lv(item);
			
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
			
		case dlg_add_memos_gv://----------------------------------------------
			
			String word = (String) parent.getItemAtPosition(position);
			
//			Methods.add_pattern_to_text(dlg1, position, word);
			
			break;
			
		default:
			break;
		}//switch (tag)
		
	}//public void onItemClick(AdapterView<?> parent, View v, int position, long id)

	private void 
	case_Dlg_ALActv_LongClick(String item) {
		// TODO Auto-generated method stub
		if (item.equals(actv.getString(R.string.generic_tv_edit))) {	// Edit
			
			Methods_dlg.edit_AI(actv, dlg1, ai, aiList_Position);
			
		} else if (item.equals(actv.getString(R.string.generic_tv_delete))) {
	
			Methods_dlg.conf_DeleteAL(actv, dlg1, ai, aiList_Position);
			
		}//if (item.equals(actv.getString(R.string.generic_tv_edit)))
		
	}//case_Dlg_ALActv_LongClick(String item)

	private void
	case_Dlg_Db_Admin_lv(String item) {
		// TODO Auto-generated method stub
		////////////////////////////////

		// Dispatch

		////////////////////////////////
		if (item.equals(actv.getString(
				R.string.dlg_db_admin_item_exec_sql))) {
			
			Methods.exec_Sql(actv);
			
		} else if (item.equals(actv.getString(
				R.string.dlg_db_admin_item_backup_db))) {
			
			Methods.db_Backup(actv);
			
		} else if (item.equals(actv.getString(		// Refresh DB
				R.string.dlg_db_admin_item_refresh_db))) {
			
			Methods.refresh_MainDB(actv);
			
		} else if (item.equals(actv.getString(		// Create table: cm7
				R.string.dlg_db_admin_item_create_table_cm7))) {
			
			Methods.create_Table(actv, CONS.DB.tname_CM7);
			
		} else if (item.equals(actv.getString(		// Drop table: cm7
				R.string.dlg_db_admin_item_drop_table_cm7))) {
			
			Methods.drop_Table(actv, CONS.DB.tname_CM7);
			
		} else if (item.equals(actv.getString(		// Create table: refresh_history
				R.string.dlg_db_admin_item_create_table_refresh_history))) {
			
			Methods.create_Table(actv, CONS.DB.tname_RefreshHistory);
			
		} else if (item.equals(actv.getString(		// Drop table: refresh_history
				R.string.dlg_db_admin_item_drop_table_refresh_history))) {
			
			Methods.drop_Table(actv, CONS.DB.tname_RefreshHistory);
			
		} else if (item.equals(actv.getString(		// Create table: refresh_history
				R.string.dlg_db_admin_item_create_table_bm))) {
			
			Methods.create_Table(actv, CONS.DB.tname_BM);
			
		} else if (item.equals(actv.getString(		// Drop table: refresh_history
				R.string.dlg_db_admin_item_drop_table_bm))) {
			
			Methods.drop_Table(actv, CONS.DB.tname_BM);
			
		} else if (item.equals(actv.getString(		// Create table: refresh_history
				R.string.dlg_db_admin_item_create_table_memo_patterns))) {
			
			Methods.create_Table(actv, CONS.DB.tname_MemoPatterns);
			
		} else if (item.equals(actv.getString(		// Drop table: refresh_history
				R.string.dlg_db_admin_item_drop_table_memo_patterns))) {
			
			Methods.drop_Table(actv, CONS.DB.tname_MemoPatterns);
			
		} else {

		}
	
		
		dlg1.dismiss();
		
	}//case_Dlg_Db_Admin_lv(String item)

	private void
//		case_dlg_bmactv_list_long_click(String item) {
	case_Dlg_BMActv_LongClick(String item) {
		// TODO Auto-generated method stub
		if (item.equals(actv.getString(R.string.generic_tv_edit))) {	// Edit
			
//			// debug
//			Toast.makeText(actv, "Edit", Toast.LENGTH_LONG).show();
			Methods_dlg.edit_BM(actv, dlg1, bm);
//			bmactv_editItem(bm);
			
		} else if (item.equals(actv.getString(R.string.generic_tv_delete))) {
	
			Methods_dlg.conf_DeleteBM(actv, dlg1, bm);
//			bmactv_deleteItem(bm);
//			CONS.BMActv.bmList.remove(bm);
//			
//			CONS.BMActv.adpBML.notifyDataSetChanged();
			
		}//if (item.equals(actv.getString(R.string.generic_tv_edit)))
		
		
	}//case_Dlg_BMActv_LongClick(String item)

}//public class DialogOnItemClickListener implements OnItemClickListener

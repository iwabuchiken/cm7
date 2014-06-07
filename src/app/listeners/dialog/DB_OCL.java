package app.listeners.dialog;

import cm7.main.R;
import android.app.Activity;
import android.app.Dialog;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import app.items.AI;
import app.items.BM;
import app.utils.CONS;
import app.utils.DBUtils;
import app.utils.Methods;
import app.utils.Tags;

public class DB_OCL implements OnClickListener {
	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;
	Dialog dlg1;
	Dialog dlg2;		//=> Used in dlg_input_empty_btn_XXX
	Dialog dlg3;

	//
	Vibrator vib;
	
	// Used in => Methods.dlg_addMemo(Activity actv, long file_id, String tableName)
	long file_id;
	String tableName;
	
	BM bm;	// used in: dlg_Conf_Delete_BM_Ok
	
	AI ai;
	
	public DB_OCL(Activity actv, Dialog dlg1) {
		//
		this.actv = actv;
		this.dlg1 = dlg1;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public DB_OCL(Activity actv, Dialog dlg1,
			Dialog dlg2) {
		//
		this.actv = actv;
		this.dlg1 = dlg1;
		this.dlg2 = dlg2;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public DB_OCL(Activity actv, Dialog dlg1,
			Dialog dlg2, Dialog dlg3) {
		//
		this.actv = actv;
		this.dlg1 = dlg1;
		this.dlg2 = dlg2;
		this.dlg3 = dlg3;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public DB_OCL(Activity actv, Dialog dlg1, long file_id, String tableName) {
		// 
		this.actv = actv;
		this.dlg1 = dlg1;
		
		this.tableName = tableName;
		
		this.file_id = file_id;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
	}//public DialogButtonOnClickListener(Activity actv, Dialog dlg1, long file_id, String tableName)


	public DB_OCL(Activity actv, Dialog dlg1, Dialog dlg2, BM bm) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		
		this.dlg1 = dlg1;
		this.dlg2 = dlg2;
		
		this.bm		= bm;
		
	}

	public DB_OCL(Activity actv, Dialog dlg, AI ai) {
		// TODO Auto-generated constructor stub
		this.actv	= actv;
		
		this.dlg1	= dlg;
		
		this.ai		= ai;
		
	}

	public void onClick(View v) {
		//
		Tags.DialogTags tag_name = (Tags.DialogTags) v.getTag();

		// Log
		Log.d("DialogButtonOnClickListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "tag_name.name()=" + tag_name.name());
		
		//
		switch (tag_name) {
		
		case DLG_GENERIC_DISMISS://------------------------------------------------
			
			CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
			
			dlg1.dismiss();
			
			break;

		case dlg_generic_dismiss_second_dialog: // ----------------------------------------------------
			
			CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
			
			dlg2.dismiss();
			
			break;// case dlg_generic_dismiss_second_dialog

		case dlg_generic_dismiss_third_dialog://------------------------------------------------
			
			CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
			
			dlg3.dismiss();
			
			break;

		case dlg_conf_delete_BM_ok://------------------------------------------------
			
			CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);

			dlg_Conf_Delete_BM_Ok();
			
			break;
			
		case DLG_EDIT_ITEM_BT_OK://------------------------------------------------
			
			CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
			
			dlg_Edit_BM_Ok();
			
			break;
			
		case DLG_PLAYACTV_EDIT_TITLE_BT_OK://------------------------------------------------
			
			CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
			
			dlg_PlayActv_Edit_AI_Title_Ok();
			
			break;
			
			
		default: // ----------------------------------------------------
			break;
		}//switch (tag_name)
	}//public void onClick(View v)

	private void dlg_PlayActv_Edit_AI_Title_Ok() {
		// TODO Auto-generated method stub
		// - Update: TextView
		// - Update: DB entry
		////////////////////////////////

		// TextView

		////////////////////////////////
		TextView tv_Title = (TextView) actv.findViewById(R.id.actv_play_tv_title);

		EditText et_Title = (EditText) dlg1.findViewById(
					R.id.dlg_edit_ai_title_et_content);
		
		tv_Title.setText(et_Title.getText().toString());
		
		// Log
		String msg_Log = "New text => set";
		Log.d("DB_OCL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// DB

		////////////////////////////////
		ai.setTitle(tv_Title.getText().toString());
		
//		col_names_CM7
//		"file_name", "file_path",	// 0, 1
//		"title", "memo",			// 2, 3
//		"last_played_at",			// 4
//		"table_name",				// 5
//		"length",					// 6
//		"audio_created_at"			// 7
		
		boolean res = DBUtils.update_Data_AI(actv, 
								CONS.DB.dbName, 
								ai.getTable_name(), 
								ai.getDb_id(), 
								CONS.DB.col_names_CM7[2], 
								ai.getTitle());
		
		if (res == true) {
			
			// debug
			String msg_Toast = "Title => updated";
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
			////////////////////////////////
			
			// Dialog
			
			////////////////////////////////
			dlg1.dismiss();
			
		} else {
			
			// debug
			String msg_Toast = "Title => can't be updated";
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();

		}
		
	}//private void dlg_PlayActv_Edit_AI_Title_Ok()

	private void dlg_Edit_BM_Ok() {
		// TODO Auto-generated method stub
		/***************************************
		 * Get data
		 ***************************************/
		EditText etTitle = (EditText) dlg2.findViewById(R.id.dlg_edit_item_et_title);
		EditText etMemo = (EditText) dlg2.findViewById(R.id.dlg_edit_item_et_memo);

		/***************************************
		 * Set: New data to bm instance
		 ***************************************/
		bm.setTitle(etTitle.getText().toString());
		bm.setMemo(etMemo.getText().toString());
		
		////////////////////////////////

		// Store data to db

		////////////////////////////////
//		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
////		boolean res = dbu.deleteData_bm(actv, bm.getDbId());
		boolean res = DBUtils.updateData_BM_TitleAndMemo(
							actv,
							bm.getDbId(),
							bm);
		
		/***************************************
		 * Update: bmList
		 * 1. Remove the original bm element, using bm.getDbId()
		 * 2. Add to the list the new bm element
		 ***************************************/
		/***************************************
		 * 1. Remove the original bm element, using bm.getDbId()
		 ***************************************/
		for (int i = 0; i < CONS.BMActv.bmList.size(); i++) {
			
			BM b = CONS.BMActv.bmList.get(i);
			
			if (b.getDbId() == bm.getDbId()) {
				
				CONS.BMActv.bmList.remove(b);
				
				CONS.BMActv.bmList.add(bm);

//				Methods.sortList_BM(
				Methods.sort_List_BM_List(
								CONS.BMActv.bmList, 
								CONS.Enums.SortType.POSITION,
								CONS.Enums.SortOrder.ASC);
//							CONS.BMActv.bmList, 
//							CONS.BMActv.SortOrder.POSITION);
				
				break;
				
			}//if (b.getDbId() == bm.getDbId())
			
		}//for (int i = 0; i < CONS.BMActv.bmList.size(); i++)
		
		CONS.BMActv.aAdapter.notifyDataSetChanged();
//		CONS.BMActv.adpBML.notifyDataSetChanged();
		
		/***************************************
		 * If successful, close dialog
		 ***************************************/
		dlg2.dismiss();
		dlg1.dismiss();
		
	}//private void dlg_Edit_BM_Ok()

	private void dlg_Conf_Delete_BM_Ok() {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
		////////////////////////////////
		
		// Delete from: Database
		
		////////////////////////////////
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		boolean res = dbu.deleteData_BM(actv, bm.getDbId());
		
		////////////////////////////////

		// Delete: from BM list

		////////////////////////////////
		if (res == true) {
			
			CONS.BMActv.bmList.remove(bm);
			
			CONS.BMActv.aAdapter.notifyDataSetChanged();
			
		} else {

			dlg2.dismiss();
			
			// debug
			String msg_Toast = "Can't remove BM from DB: " + bm.getPosition();
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
			return;

		}

		////////////////////////////////

		// Close dialog

		////////////////////////////////
		dlg2.dismiss();
		
		dlg1.dismiss();
		
		// debug
		String msg_Toast = "BM => deleted: " + bm.getPosition();
		Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
		////////////////////////////////

		// Reset: current positin in PlayActv

		////////////////////////////////
		String bm_Position = bm.getPosition();
		
		long pref_Position_Long = Methods.getPref_Long(actv, 
						CONS.Pref.pname_PlayActv, 
						CONS.Pref.pkey_PlayActv_CurrentPosition,
						CONS.Pref.dflt_LongExtra_value);
		
		// Log
		String msg_Log = "pref_Position_Long => now: "
				+ pref_Position_Long;
		Log.d("DB_OCL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		// Recalibrate: pref_Position_Long
		int tmp = (int) pref_Position_Long % 1000;
		
		if (tmp > 0) {
			
			pref_Position_Long = (pref_Position_Long / 1000) * 1000 + 1000;
			
		}
		
		// Log
		msg_Log = "pref_Position_Long => recalibrated: "
						+ pref_Position_Long;
		Log.d("DB_OCL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		// Log
		msg_Log = "Methods.conv_MillSec_to_ClockLabel(pref_Position_Long) = "
						+ Methods.conv_MillSec_to_ClockLabel(pref_Position_Long)
						+ " // "
						+ "bm_Position = " + bm_Position;
		
		Log.d("DB_OCL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		// Reset: current position (Long)
		if (!(pref_Position_Long == CONS.Pref.dflt_LongExtra_value)
				&& Methods.conv_MillSec_to_ClockLabel(
						pref_Position_Long).equals(bm_Position)) {
//			pref_Position_Long) == bm_Position) {
//			pref_Position_Long) != bm_Position) {

			Methods.setPref_Long(actv, 
					CONS.Pref.pname_PlayActv, 
					CONS.Pref.pkey_PlayActv_CurrentPosition, 
					CONS.PlayActv.playActv_InitialPosition);
			
			// Log
			msg_Log = "Current position => Reset to: " 
							+ CONS.PlayActv.playActv_InitialPosition
							+ "("
							+ Methods.conv_ClockLabel_to_MillSec(bm_Position)
							+ ")";
			Log.d("DB_OCL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} else {
			
			// Log
			msg_Log = "Current position => remains same: "
							+ Methods.conv_MillSec_to_ClockLabel(pref_Position_Long);
			Log.d("DB_OCL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
	}//private void dlg_Conf_Delete_BM_Ok()

}//DialogButtonOnClickListener

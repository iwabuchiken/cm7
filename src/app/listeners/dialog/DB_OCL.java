package app.listeners.dialog;

import cm7.main.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import app.items.AI;
import app.items.BM;
import app.items.WordPattern;
import app.utils.CONS;
import app.utils.DBUtils;
import app.utils.Methods;
import app.utils.Methods_dlg;
import app.utils.Ops;
import app.utils.Tags;

public class DB_OCL implements OnClickListener {
	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;
	Dialog d1;
	Dialog d2;		//=> Used in dlg_input_empty_btn_XXX
	Dialog d3;

	//
	Vibrator vib;
	
	// Used in => Methods.dlg_addMemo(Activity actv, long file_id, String tableName)
	long file_id;
	String tableName;
	
	BM bm;	// used in: dlg_Conf_Delete_BM_Ok
	
	AI ai;
	int alList_Position;
	
	String folderName;	// MainActv, long click, delete folder, conf OK
	private String choice;
	private WordPattern wp;
	
	public DB_OCL(Activity actv, Dialog dlg1) {
		//
		this.actv = actv;
		this.d1 = dlg1;
		
		//
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
	}

	public DB_OCL(Activity actv, Dialog dlg1,
			Dialog dlg2) {
		//
		this.actv = actv;
		this.d1 = dlg1;
		this.d2 = dlg2;
		
		//
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
	}

	public DB_OCL(Activity actv, Dialog dlg1,
			Dialog dlg2, Dialog dlg3) {
		//
		this.actv = actv;
		this.d1 = dlg1;
		this.d2 = dlg2;
		this.d3 = dlg3;
		
		//
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
	}

	public DB_OCL(Activity actv, Dialog dlg1, long file_id, String tableName) {
		// 
		this.actv = actv;
		this.d1 = dlg1;
		
		this.tableName = tableName;
		
		this.file_id = file_id;
		
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogButtonOnClickListener(Activity actv, Dialog dlg1, long file_id, String tableName)


	public DB_OCL(Activity actv, Dialog dlg1, Dialog dlg2, BM bm) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		
		this.d1 = dlg1;
		this.d2 = dlg2;
		
		this.bm		= bm;
		
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}

	public DB_OCL(Activity actv, Dialog dlg, AI ai) {
		// TODO Auto-generated constructor stub
		this.actv	= actv;
		
		this.d1	= dlg;
		
		this.ai		= ai;
		
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}

	public DB_OCL
	(Activity actv, Dialog dlg1, Dialog dlg2, AI ai, int alList_Position) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		
		this.d1 = dlg1;
		this.d2 = dlg2;
		
		this.ai		= ai;
		this.alList_Position	= alList_Position;
	
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

	public DB_OCL
	(Activity actv, Dialog dlg1, Dialog dlg2, AI ai) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		
		this.d1 = dlg1;
		this.d2 = dlg2;
		
		this.ai		= ai;
		
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

	public 
	DB_OCL
	(Activity actv, Dialog dlg1, Dialog dlg2, String folderName) {
		// TODO Auto-generated constructor stub
		
		this.actv = actv;
		
		this.d1 = dlg1;
		this.d2 = dlg2;

		this.folderName	= folderName;
		
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

	public 
	DB_OCL
	(Activity actv, 
			Dialog dlg1, Dialog dlg2, Dialog dlg3,
			AI ai, int aiList_Position, String choice) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		
		this.d1 = dlg1;
		this.d2 = dlg2;
		this.d3 = dlg3;
		
		this.ai		= ai;
		this.alList_Position	= aiList_Position;
	
		this.choice	= choice;
		
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

	public DB_OCL
	(Activity actv, 
		Dialog d1, Dialog d2, Dialog d3, WordPattern wp) {
		// TODO Auto-generated constructor stub
		
		this.actv = actv;
		
		this.d1 = d1;
		this.d2 = d2;
		this.d3 = d3;

		this.wp	= wp;
		
		CONS.Admin.vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);

	}

	public void onClick(View v) {
		//
		Tags.DialogTags tag_name = (Tags.DialogTags) v.getTag();

		// Log
		Log.d("DialogButtonOnClickListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "tag_name.name()=" + tag_name.name());
		
//		CONS.Admin.vib = 
//					(Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
		CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
		
		//
		switch (tag_name) {
		
		case GENERIC_DISMISS://------------------------------------------------
			
			d1.dismiss();
			
			break;

		case DLG_GENERIC_DISMISS_SECOND_DIALOG: // ----------------------------------------------------
			
			d2.dismiss();
			
			break;// case dlg_generic_dismiss_second_dialog

		case DLG_GENERIC_DISMISS_THIRD_DIALOG://------------------------------------------------
			
			d3.dismiss();
			
			break;

		case dlg_conf_delete_BM_ok://------------------------------------------------
			
			dlg_Conf_Delete_BM_Ok();
			
			break;
			
		case DLG_EDIT_ITEM_BT_OK://------------------------------------------------
			
			dlg_Edit_BM_Ok();
			
			break;
			
		case DLG_PLAYACTV_EDIT_TITLE_BT_OK://------------------------------------------------
			
			dlg_PlayActv_Edit_AI_Title_Ok();
			
			break;
			
		case DLG_CONF_DELETE_AI_OK://------------------------------------------------
			
			dlg_ALActv_Delete_AI_Ok();
			
			break;
			
		case DLG_CREATE_FOLDER_OK://------------------------------------------------
			
			dlg_DLG_CREATE_FOLDER_OK();
			
			break;
			
		case DLG_EDIT_AI_BT_OK://------------------------------------------------
			
			dlg_ALActv_edit_AI_Ok();
			
			break;
			
		case DLG_CONFIRM_CREATE_FOLDER_OK://------------------------------------------------
			
			dlg_CONFIRM_CREATE_FOLDER_OK();
			
			break;
			
		case DLG_DELETE_FOLDER_CONF_OK://------------------------------------------------
			
			dlg_DLG_DELETE_FOLDER_CONF_OK();
			
			break;
			
		case DLG_CREATE_DIR_OK://------------------------------------------------
			
			dlg_DLG_CREATE_DIR_OK();
			
			break;
			
		case DLG_ALACTV_MOVEFILE_CONF_OK://------------------------------------------------
			
			dlg_DLG_ALACTV_MOVEFILE_CONF_OK();
			
			break;
			
		case DLG_CONF_IMPORT_DB_OK://------------------------------------------------
			
			dlg_DLG_CONF_IMPORT_DB_OK();
			
			break;
			
		case DLG_CONF_IMPORT_PATTERNS_OK://------------------------------------------------
			
			dlg_DLG_CONF_IMPORT_PATTERNS_OK();
			
			break;
			
		case DLG_CONF_DELETE_PATTERN_OK://------------------------------------------------
			
			dlg_DLG_CONF_DELETE_PATTERN_OK();
			
			break;
			
		case DLG_REGISTER_PATTERNS_OK://------------------------------------------------
			
			dlg_DLG_REGISTER_PATTERNS_OK();
			
			break;
			
		case DLG_CONF_MOVE_FILES_FOLDER_OK://------------------------------------------------
			
			dlg_DLG_CONF_MOVE_FILES_FOLDER_OK();
			
			break;
			
		case DLG_CONF_MOVE_FILES_FOLDER_TOP_OK://------------------------------------------------
			
			dlg_DLG_CONF_MOVE_FILES_FOLDER_OK();
			
			break;
			
		default: // ----------------------------------------------------
			break;
		}//switch (tag_name)
	}//public void onClick(View v)

	private void 
	dlg_DLG_CONF_MOVE_FILES_FOLDER_OK() {
		// TODO Auto-generated method stub

		Methods.move_Files(actv, d1, d2, d3);
		
		
	}//dlg_DLG_CONF_MOVE_FILES_FOLDER_OK

	private void 
	dlg_DLG_REGISTER_PATTERNS_OK() {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// view

		////////////////////////////////
		EditText et = (EditText) d2.findViewById(R.id.dlg_tmpl_ok_cancel_et);
		
		////////////////////////////////

		// get: value

		////////////////////////////////
		String new_Word = et.getText().toString();
		
		////////////////////////////////

		// validate: any input

		////////////////////////////////
		if (new_Word == null ) {
			
			String msg = "Input => null";
			Methods_dlg.dlg_ShowMessage_ThirdDialog(actv, msg, d1, d2, R.color.gold2);
			
			return;
			
		}
		
		if (new_Word.length() < 1) {
			
			String msg = "No input";
			Methods_dlg.dlg_ShowMessage(actv, msg, R.color.red);
			
			return;
			
		}
		
		////////////////////////////////

		// save word

		////////////////////////////////
		int res = DBUtils.save_Pattern(actv, new_Word);
		
		////////////////////////////////

		// report

		////////////////////////////////
		String msg = null;
		int colorID = 0;
		
//		-1 insertion => failed
//		-2 Exception
//		-3 pattern already in DB
//		1 Inserted

		
		switch(res) {
		
		case -1: 
			
			msg = "insertion => failed";
			colorID = R.color.red;

			break;
		
		case -2: 
			
			msg = "Exception";
			colorID = R.color.red;
			
			break;
			
		case -3: 
			
			msg = "pattern already in DB";
			colorID = R.color.red;
			
			break;
			
		case 1: 
			
			msg = "Inserted => " + new_Word;
			colorID = R.color.green4;
			
			d2.dismiss();
			d1.dismiss();
			
			break;
			
		default:
			
			msg = "Unknown result => " + res;
			colorID = R.color.gold2;
			
			d2.dismiss();
			
			break;
			
		}
		
		Methods_dlg.dlg_ShowMessage(
//				Methods_dlg.dlg_ShowMessage_Duration(
				actv, 
				msg,
				colorID);
		
		
	}//dlg_DLG_REGISTER_PATTERNS_OK

	private void 
	dlg_DLG_CONF_DELETE_PATTERN_OK() {
		// TODO Auto-generated method stub

		Methods.delete_Pattern(actv, d1, d2, d3, wp);
		
	}

	private void 
	dlg_DLG_CONF_IMPORT_PATTERNS_OK() {
		// TODO Auto-generated method stub
		
		int res = Methods.import_Patterns(actv);

		////////////////////////////////

		// report

		////////////////////////////////
		String msg = null;
		int colorID = 0;
		
//		>1 Number of patterns saved
//		0 No patterns saved
//		-1 Table 'patterns' => not exist
//		-2 Can't build list
//		-3 Unknown result
		
		switch(res) {
		
		case -1: 
			
			msg = "Table 'patterns' => not exist";
			colorID = R.color.red;
			
			d3.dismiss();
			
			break;
		
		case -2: 
			
			msg = "Can't build list";
			colorID = R.color.red;
			
			d3.dismiss();
			
			break;
			
		case -3: 
			
			msg = "Unknown result";
			colorID = R.color.gold2;
			
			d3.dismiss();
			
			break;
			
		case 0: 
			
			msg = "No patterns saved";
			colorID = R.color.gold2;

			d3.dismiss();
			
			break;
			
		default:
			
			msg = "Patterns imported => " + res;
			colorID = R.color.green4;
			
			d3.dismiss();
			d2.dismiss();
			d1.dismiss();
			
			break;
			
		}
		
		Methods_dlg.dlg_ShowMessage(
//				Methods_dlg.dlg_ShowMessage_Duration(
				actv, 
				msg,
				colorID);
		
	}

	private void 
	dlg_DLG_CONF_IMPORT_DB_OK() {
		// TODO Auto-generated method stub
	
		Methods.import_DB(actv, d1, d2, d3);
		
	}

	private void 
	dlg_DLG_ALACTV_MOVEFILE_CONF_OK() {
		// TODO Auto-generated method stub

//		String tname_New = Methods.conv_CurrentPathMove_to_TableName(choice);

		Methods.move_Files(actv, 
				d1, d2, d3,
				ai, alList_Position, choice);
		
	}

	private void dlg_DLG_CREATE_DIR_OK() {
		// TODO Auto-generated method stub
		
		Methods_dlg.dlg_IsEmpty(actv, d1);
		
	}

	private void dlg_DLG_DELETE_FOLDER_CONF_OK() {
		// TODO Auto-generated method stub

		// Log
		String msg_Log = "delete folder => confirmed";
		Log.d("DB_OCL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		Ops.del_Folder(actv, d1, d2, folderName);
		
	}

	private void 
	dlg_CONFIRM_CREATE_FOLDER_OK() {
		// TODO Auto-generated method stub
		
//		Methods.create_Dir(actv, dlg1, dlg2);
		Methods.create_Dir(actv, d1, d2);
		
	}//dlg_CONFIRM_CREATE_FOLDER_OK

	private void dlg_DLG_CREATE_FOLDER_OK() {
		// TODO Auto-generated method stub
		
		Methods_dlg.dlg_IsEmpty(actv, d1);
		
	}

	private void 
	dlg_ALActv_edit_AI_Ok() {
		
		Methods.edit_AI_Ok(actv, d1, d2, ai);
		// TODO Auto-generated method stub

	}//dlg_ALActv_edit_AI_Ok()

	private void dlg_ALActv_Delete_AI_Ok() {
		// TODO Auto-generated method stub
		
		Methods.delete_AI(actv, d1, d2, ai, alList_Position);
		
	}

	private void dlg_PlayActv_Edit_AI_Title_Ok() {
		// TODO Auto-generated method stub
		// - Update: TextView
		// - Update: DB entry
		////////////////////////////////

		// TextView

		////////////////////////////////
		TextView tv_Title = (TextView) actv.findViewById(R.id.actv_play_tv_title);

		EditText et_Title = (EditText) d1.findViewById(
					R.id.dlg_edit_ai_title_et_content);
		
		tv_Title.setText(et_Title.getText().toString());
		
		// Log
		String msg_Log = "New text => set";
		Log.d("DB_OCL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// ALActv list

		////////////////////////////////
		if (CONS.ALActv.list_AI != null
				&& CONS.ALActv.adp_AIList != null) {
			
			CONS.ALActv.adp_AIList.notifyDataSetChanged();
			
			// Log
			msg_Log = "CONS.ALActv.adp_AIList => notified";
			Log.d("DB_OCL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		
		
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
			d1.dismiss();
			
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
		EditText etTitle = (EditText) d2.findViewById(R.id.dlg_edit_item_et_title);
		EditText etMemo = (EditText) d2.findViewById(R.id.dlg_edit_item_et_memo);

		/***************************************
		 * Set: New data to bm instance
		 ***************************************/
		bm.setTitle(etTitle.getText().toString());
		bm.setMemo(etMemo.getText().toString());
		
		////////////////////////////////

		// Store data to db

		////////////////////////////////
//		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		DBUtils.updateData_BM_TitleAndMemo(
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
		d2.dismiss();
		d1.dismiss();
		
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

			d2.dismiss();
			
			// debug
			String msg_Toast = "Can't remove BM from DB: " + bm.getPosition();
			Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
			
			return;

		}

		////////////////////////////////

		// Close dialog

		////////////////////////////////
		d2.dismiss();
		
		d1.dismiss();
		
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

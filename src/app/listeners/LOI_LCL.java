package app.listeners;

import java.util.ArrayList;
import java.util.List;

import cm7.main.R;
import android.app.Activity;
import android.app.Dialog;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;
import app.items.AI;
import app.items.BM;
import app.listeners.dialog.DOI_CL;
import app.utils.CONS;
import app.utils.Methods;
import app.utils.Methods_dlg;
import app.utils.Ops;
import app.utils.Tags;

// ListOnItemLongClickListener
public class
LOI_LCL implements OnItemLongClickListener {

	Activity actv;

	Dialog dlg1;
	Dialog dlg2; 
	
	AI ai;
	int aiList_Position;
	
	Vibrator vib;
	
	public LOI_LCL(Activity actv) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
	}

	public 
	LOI_LCL
	(Activity actv, Dialog dlg1, Dialog dlg2, 
			AI ai, int aiList_Position) {
		// TODO Auto-generated constructor stub
		
		this.actv	= actv;
		this.dlg1	= dlg1;
		this.dlg2	= dlg2;
		
		this.ai		= ai;
		this.aiList_Position	= aiList_Position;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);

	}

	public boolean
	onItemLongClick
	(AdapterView<?> parent, View v, int position, long id) {
		
		////////////////////////////////

		// dispatch: tag

		////////////////////////////////
		Class tmp_class = parent.getTag().getClass();
		
		Tags.ListTags tag = null;
		
		if (tmp_class == Tags.ListTags.class) {
			
			tag = (Tags.ListTags) parent.getTag();
			
		} else if (tmp_class == Tags.DialogItemTags.class) {

			onItemLongClick_DialogItemTags(parent, v, position, id);
			
			return true;
			
		}
		
//		Tags.ListTags tag = (Tags.ListTags) parent.getTag();

		// Log
		
//		String msg_Log = "tmp_class == Tags.ListTags.class => " 
//					+ (tmp_class == Tags.ListTags.class);
//		
//		Log.d("LOI_LCL.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
//		// Log
//		String msg_Log = "tag: class name => " + parent.getTag().getClass().getName();
//		Log.d("LOI_LCL.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		// Log
//		msg_Log = "ListTags? => " + (parent.getTag() instanceof Tags.ListTags);
//		Log.d("LOI_LCL.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
		vib.vibrate(CONS.Admin.vibLength_click);
		

		
		switch (tag) {
			
//		case actv_bm_lv://----------------------------------------------------
		case ACTV_BM_LV://----------------------------------------------------

			BM bm = (BM) parent.getItemAtPosition(position);
			
			case_ACTV_BM_LV(bm, position);
			
			break;// case actv_bm_lv
			
		case actv_ALActv_lv://----------------------------------------------------
			
//			BM bm = (BM) parent.getItemAtPosition(position);
			AI ai = (AI) parent.getItemAtPosition(position);
			
			case_ALActv_lv(ai, position);
			
			break;// case actv_bm_lv
			
		case ACTV_MAIN_LV://----------------------------------------------------

			String item = (String) parent.getItemAtPosition(position);
			
			case_ACTV_MAIN_LV(item);
			
			break;// case actv_bm_lv
			
		default:
			break;
		
		}//switch (tag)
		
		return true;
		
	}//onItemLongClick (AdapterView<?> parent, View v, int position, long id)

	/******************************
		This method is used when a long click on an item <br>
			in a list whose tag is of DialogItemTags<bt>
			instead of ListTags
	 ******************************/
	private void 
	onItemLongClick_DialogItemTags
	(AdapterView<?> parent, View v,
			int position, long id) {
		// TODO Auto-generated method stub
		
		Tags.DialogItemTags tag = (Tags.DialogItemTags) parent.getTag();
		
		vib.vibrate(CONS.Admin.vibLength_click);
		
		switch (tag) {
		
		case DLG_ALACTV_LIST_MOVE_FILE://----------------------------------------------------

			String item = (String) parent.getItemAtPosition(position);
			
			case_DLG_ALACTV_LIST_MOVE_FILE(item);
			
			break;// case actv_bm_lv
		
		default:
			break;
		
		}//switch (tag)
		
	}//onItemLongClick_DialogItemTags

	private void 
	case_DLG_ALACTV_LIST_MOVE_FILE
	(String item) {
		// TODO Auto-generated method stub

		////////////////////////////////

		// choice => Upper dir? ("..")

		////////////////////////////////
		String curPath_Move = Methods.get_Pref_String(
				actv, 
				CONS.Pref.pname_ALActv, 
				CONS.Pref.pkey_ALActv__CurPath_Move, 
				null);

		// Log
		String msg_Log = "curPath_Move => " + curPath_Move;
		Log.d("LOI_LCL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		// Upper dir
		if (item.equals(CONS.Admin.dirString_UpperDir)) {
			
			// top dir("cm7") ?
			if (curPath_Move.equals(CONS.DB.tname_CM7)) {
				
				Methods_dlg.conf_MoveAi(
							actv, dlg1, dlg2, 
							ai, aiList_Position,
							CONS.DB.tname_CM7);
				
//				String msg = "Move to cm7?";
//				Methods_dlg.dlg_ShowMessage(actv, msg);
				
				return;
				
			} else {
				// not the top dir
//				String newPath_Move = 
//						Methods.conv_CurrentPathMove_to_CurrentPathMove_New(curPath_Move);
//				
//				// Log
//				msg_Log = "newPath_Move => " + newPath_Move;
//				Log.d("LOI_LCL.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", msg_Log);
				
//				String msg_Toa = "Upper dir";
//				Toast.makeText(actv, msg_Toa, Toast.LENGTH_SHORT).show();
				
				Ops.go_Up_Dir_Move(actv);
				
				return;
				
			}
			
		}

		////////////////////////////////

		// set: pref: pkey_ALActv__CurPath_Move

		////////////////////////////////
		boolean res = Methods.set_Pref_String(
				actv, 
				CONS.Pref.pname_ALActv, 
				CONS.Pref.pkey_ALActv__CurPath_Move, 
				item);
		
		if (res == true) {
			
			// Log
			msg_Log = "pref set => " + item;
			Log.d("LOI_LCL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}

		////////////////////////////////

		// update: list

		////////////////////////////////
		Methods.update_MoveFilesList(actv, dlg1, dlg2, ai, aiList_Position, item);
		
	}//case_DLG_ALACTV_LIST_MOVE_FILE

	private void 
	case_ACTV_MAIN_LV(String item) {
		// TODO Auto-generated method stub
		
		Methods_dlg.dlg_ACTV_MAIN_LV(actv, item);
		
	}

	private void 
	case_ALActv_lv(AI ai, int alList_Position) {
		// TODO Auto-generated method stub
		
//		int title_Length = 12;
//		int title_Length = 12;
		
		int len;
		
		if (ai.getFile_name().length() > CONS.ALActv.TITLE_MAX_LENGTH) {
			
			len = ai.getFile_name().length();
			
		} else {
			
			len = CONS.ALActv.TITLE_MAX_LENGTH;

		}
		
		String title = actv.getString(
							R.string.dlg_alactv_list_long_click_title)
						+ ": "
						+ ai.getFile_name().substring(
								0, CONS.ALActv.TITLE_MAX_LENGTH)
						;
		
		Dialog dlg1 = Methods_dlg.dlg_Template_Cancel(
				actv,
				R.layout.dlg_tmpl_cancel_lv,
				title,
				
				R.id.dlg_tmpl_cancel_lv_bt_cancel,
				Tags.DialogTags.GENERIC_DISMISS);

		/****************************
		* 2. Prep => List
		****************************/
		String[] choices = {
					actv.getString(R.string.generic_tv_delete),
					actv.getString(R.string.generic_tv_edit),
					actv.getString(R.string.dlg_alactv_list_long_click_item_move),
					};
		
		List<String> list = new ArrayList<String>();
		
		for (String item : choices) {
		
			list.add(item);
		
		}
		
		/****************************
		* 3. Adapter
		****************************/
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		actv,
		//R.layout.dlg_db_admin,
		R.layout.list_row_simple_1,
		//android.R.layout.simple_list_item_1,
		list
		);
		
		/****************************
		* 4. Set adapter
		****************************/
		ListView lv = (ListView) dlg1.findViewById(R.id.dlg_tmpl_cancel_lv_lv);
		
		lv.setAdapter(adapter);
		
		/****************************
		* 5. Set listener to list
		****************************/
		lv.setTag(Tags.DialogItemTags.DLG_ALACTV_LIST_LONGCLICK);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg1, ai, alList_Position));
		
		/***************************************
		* Modify the list view height
		***************************************/
		lv.setLayoutParams(
				new LinearLayout.LayoutParams(
						300,	//	Width
						LayoutParams.WRAP_CONTENT	//	Height
				));
		
		/***************************************
		* Modify: Button layout
		***************************************/
		LinearLayout llButton =
		(LinearLayout) dlg1.findViewById(R.id.dlg_tmpl_cancel_lv_ll_filepath);
//		(LinearLayout) dlg1.findViewById(R.id.actv_imp_ll_filepath);
		
		LinearLayout.LayoutParams params =
				new LinearLayout.LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT);
		
		params.gravity = Gravity.CENTER_HORIZONTAL;
		
		llButton.setLayoutParams(params);
		
		
		/****************************
		* 6. Show dialog
		****************************/
		dlg1.show();
		
		
	}//case_BMActv_lv(AI ai)

	private void case_ACTV_BM_LV(BM bm, int lv_Position) {
		/***************************************
		 * Show dialog
		 ***************************************/
		Dialog dlg = Methods_dlg.dlg_Template_Cancel(
								actv,
								R.layout.dlg_tmpl_cancel_lv,
								R.string.dlg_bmactv_list_long_click_title,
								
								R.id.dlg_tmpl_cancel_lv_bt_cancel,
								Tags.DialogTags.GENERIC_DISMISS);
		
		/****************************
		 * 2. Prep => List
			****************************/
		String[] choices = {
							actv.getString(R.string.generic_tv_delete),
							actv.getString(R.string.generic_tv_edit)
							};
		
		List<String> list = new ArrayList<String>();
		
		for (String item : choices) {
			
			list.add(item);
			
		}
		
		/****************************
		 * 3. Adapter
			****************************/
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				actv,
//				R.layout.dlg_db_admin,
				R.layout.list_row_simple_1,
//				android.R.layout.simple_list_item_1,
				list
				);

		/****************************
		 * 4. Set adapter
			****************************/
		ListView lv = (ListView) dlg.findViewById(R.id.dlg_tmpl_cancel_lv_lv);
		
		lv.setAdapter(adapter);
		
		/****************************
		 * 5. Set listener to list
			****************************/
		lv.setTag(Tags.DialogItemTags.DLG_BMACTV_LIST_LONGCLICK);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg, bm, lv_Position));

		/***************************************
		 * Modify the list view height
		 ***************************************/
		lv.setLayoutParams(new LinearLayout.LayoutParams(
				300,	//	Width
				LayoutParams.WRAP_CONTENT	//	Height
				));
		
		/***************************************
		 * Modify: Button layout
		 ***************************************/
		LinearLayout llButton =
				(LinearLayout) dlg.findViewById(R.id.dlg_tmpl_cancel_lv_ll_filepath);
//		(LinearLayout) dlg.findViewById(R.id.actv_imp_ll_filepath);
		
		LinearLayout.LayoutParams params =
						new LinearLayout.LayoutParams(
										LayoutParams.WRAP_CONTENT,
										LayoutParams.WRAP_CONTENT);
		
		params.gravity = Gravity.CENTER_HORIZONTAL;
		
		llButton.setLayoutParams(params);
		

		/****************************
		 * 6. Show dialog
			****************************/
		dlg.show();
		
		/***************************************
		 * Delete bm from: bmList
		 ***************************************/
	}//private void case_actv_bm_lv(BM bm)

}//public class ListOnItemLongClickListener implements OnItemLongClickListener

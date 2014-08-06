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
import app.items.AI;
import app.items.BM;
import app.listeners.dialog.DOI_CL;
import app.utils.CONS;
import app.utils.Methods;
import app.utils.Methods_dlg;
import app.utils.Tags;

// ListOnItemLongClickListener
public class
LOI_LCL implements OnItemLongClickListener {

	Activity actv;
	
	Vibrator vib;
	
	public LOI_LCL(Activity actv) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
	}

	public boolean
	onItemLongClick
	(AdapterView<?> parent, View v, int position, long id) {
		
		Tags.ListTags tag = (Tags.ListTags) parent.getTag();
		
		vib.vibrate(CONS.Admin.vibLength_click);
		

		
		switch (tag) {
			
		case actv_bm_lv://----------------------------------------------------

			BM bm = (BM) parent.getItemAtPosition(position);
			
			case_BMActv_lv(bm);
			
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
				Tags.DialogTags.DLG_GENERIC_DISMISS);

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
		//R.layout.dlg_db_admin,
		R.layout.list_row_simple_1,
		//android.R.layout.simple_list_item_1,
		list
		);
		
		/****************************
		* 4. Set adapter
		****************************/
		ListView lv = (ListView) dlg1.findViewById(R.id.actv_imp_lv);
		
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
		(LinearLayout) dlg1.findViewById(R.id.actv_imp_ll_filepath);
		
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

	private void case_BMActv_lv(BM bm) {
		/***************************************
		 * Show dialog
		 ***************************************/
		Dialog dlg = Methods_dlg.dlg_Template_Cancel(
								actv,
								R.layout.dlg_tmpl_cancel_lv,
								R.string.dlg_bmactv_list_long_click_title,
								
								R.id.dlg_tmpl_cancel_lv_bt_cancel,
								Tags.DialogTags.DLG_GENERIC_DISMISS);
		
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
		ListView lv = (ListView) dlg.findViewById(R.id.actv_imp_lv);
		
		lv.setAdapter(adapter);
		
		/****************************
		 * 5. Set listener to list
			****************************/
		lv.setTag(Tags.DialogItemTags.DLG_BMACTV_LIST_LONGCLICK);
		
		lv.setOnItemClickListener(new DOI_CL(actv, dlg, bm));

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
				(LinearLayout) dlg.findViewById(R.id.actv_imp_ll_filepath);
		
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

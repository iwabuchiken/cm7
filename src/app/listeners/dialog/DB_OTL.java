package app.listeners.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import app.utils.Tags;

public class DB_OTL implements OnTouchListener {

	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;
	Dialog dlg;
	
	public DB_OTL(Activity actv, Dialog dlg) {
		//
		this.actv = actv;
		this.dlg = dlg;
	}
	
	public DB_OTL(Activity actv) {
		//
		this.actv = actv;
	}

//	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		Tags.DialogTags tag_name = (Tags.DialogTags) v.getTag();
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
				switch (tag_name) {
				
				case DLG_GENERIC_DISMISS:
				case DLG_GENERIC_DISMISS_THIRD_DIALOG:
				case DLG_GENERIC_DISMISS_SECOND_DIALOG:
					
				case DLG_CREATE_FOLDER_OK:
				case dlg_create_folder_cancel:
				
				case dlg_input_empty_cancel:
				case dlg_input_empty_reenter:

				case DLG_CONFIRM_CREATE_FOLDER_OK:
				case DLG_CONFIRM_CREATE_FOLDER_CANCEL:
					
				case dlg_confirm_remove_folder_cancel:
				case dlg_confirm_remove_folder_ok:

				case dlg_confirm_move_files_ok:

				case dlg_search_ok:
					
				case dlg_register_patterns_register:

				case dlg_confirm_delete_patterns_ok:
					
				case DLG_PLAYACTV_EDIT_TITLE_BT_OK:
					
				case DLG_EDIT_ITEM_BT_OK:
					
				case dlg_conf_delete_BM_ok:
					
				case DLG_EDIT_AI_BT_OK:
					
				case DLG_DELETE_FOLDER_CONF_OK:
					
					//
					v.setBackgroundColor(Color.GRAY);
					
					break;
				}//switch (tag_name)
		
			break;//case MotionEvent.ACTION_DOWN:
			
		case MotionEvent.ACTION_UP:
			switch (tag_name) {

			case DLG_GENERIC_DISMISS:
			case DLG_GENERIC_DISMISS_SECOND_DIALOG:
			case DLG_GENERIC_DISMISS_THIRD_DIALOG:

			case DLG_CREATE_FOLDER_OK:
			case dlg_create_folder_cancel:

			case dlg_input_empty_cancel:
			case dlg_input_empty_reenter:

			case DLG_CONFIRM_CREATE_FOLDER_OK:
			case DLG_CONFIRM_CREATE_FOLDER_CANCEL:

			case dlg_confirm_remove_folder_cancel:
			case dlg_confirm_remove_folder_ok:

			case dlg_confirm_move_files_ok:
				
			case dlg_search_ok:
				
			case dlg_register_patterns_register:
				
			case dlg_confirm_delete_patterns_ok:
				
			case DLG_PLAYACTV_EDIT_TITLE_BT_OK:
				
			case DLG_EDIT_ITEM_BT_OK:
				
			case dlg_conf_delete_BM_ok:
				
			case DLG_EDIT_AI_BT_OK:
				
			case DLG_DELETE_FOLDER_CONF_OK:
				//
					v.setBackgroundColor(Color.WHITE);
					
					break;
				}//switch (tag_name)
		
			break;//case MotionEvent.ACTION_UP:
		
		}//switch (event.getActionMasked())
		
		return false;
		
	}//public boolean onTouch(View v, MotionEvent event)

}//public class DB_OTL implements OnTouchListener

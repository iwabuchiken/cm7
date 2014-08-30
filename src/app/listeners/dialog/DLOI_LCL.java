package app.listeners.dialog;

import java.util.ArrayList;
import java.util.List;

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
import app.items.WordPattern;
import app.utils.CONS;
import app.utils.Methods;
import app.utils.Methods_dlg;
import app.utils.Tags;

// ListOnItemLongClickListener
// Dialog List On Item Long Click Listener
public class
DLOI_LCL implements OnItemLongClickListener {

	Activity actv;

	Dialog d1;
	Dialog d2; 
	
	Vibrator vib;
	
	public DLOI_LCL(Activity actv) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		
		CONS.Admin.vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
	}

	public DLOI_LCL(Activity actv, Dialog d1) {
		// TODO Auto-generated constructor stub
		
		this.actv = actv;
		this.d1	= d1;
		
		CONS.Admin.vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);

	}

	public 
	DLOI_LCL
	(Activity actv, Dialog d1, Dialog d2) {
		// TODO Auto-generated constructor stub
		
		this.actv = actv;
		this.d1	= d1;
		this.d2	= d2;
		
		CONS.Admin.vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);

	}

	public boolean
	onItemLongClick
	(AdapterView<?> parent, View v, int position, long id) {
		
		Tags.DialogItemTags tag = null;
		
			
		tag = (Tags.DialogItemTags) parent.getTag();
		
		CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
		
		WordPattern wp;
		
		switch (tag) {
			
		case DLG_ADD_MEMOS_GV_1://----------------------------------------------------
		case DLG_ADD_MEMOS_GV_2://----------------------------------------------------
		case DLG_ADD_MEMOS_GV_3://----------------------------------------------------
//		case ACTV_MEMO_LV_3://----------------------------------------------------

			wp = (WordPattern) parent.getItemAtPosition(position);
			
			case_ACTV_MEMO_LV_3(wp);
			
			break;// case actv_bm_lv
			
		case DLG_MOVE_FILES_FOLDER://----------------------------------------------------

			String item = (String) parent.getItemAtPosition(position);
			
			case_DLG_MOVE_FILES_FOLDER(item);
			
			break;// case actv_bm_lv

		default:
			break;
		
		}//switch (tag)
		
		return true;
		
	}//onItemLongClick (AdapterView<?> parent, View v, int position, long id)

	private void 
	case_DLG_MOVE_FILES_FOLDER
	(String item) {
		// TODO Auto-generated method stub
		
		// Log
		String msg_Log = "item => " + item;
		Log.d("DLOI_LCL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// get: current path-move

		////////////////////////////////
		String curPath_Move = Methods.get_Pref_String(
						actv, 
						CONS.Pref.pname_MainActv, 
						CONS.Pref.pkey_ALActv__CurPath_Move, 
						CONS.DB.tname_CM7);

		////////////////////////////////

		// case: upper dir

		////////////////////////////////
		if (item.equals(CONS.Admin.dirString_UpperDir)) {
			
			// top dir("cm7") ?
			if (curPath_Move.equals(CONS.DB.tname_CM7)) {
				
				Methods_dlg.conf_MoveFiles__Folder_Top(actv, d1, d2);
				
				return;
				
			} else {
				// not the top dir
				
				// Log
				msg_Log = "not the top dir";
				Log.d("DLOI_LCL.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", msg_Log);
				Methods.go_Up_Dir_Move(actv);
//				Ops.go_Up_Dir_Move(actv);
				
				return;
				
			}
			
		}//if (item.equals(CONS.Admin.dirString_UpperDir))
		
		////////////////////////////////

		// lower dir

		////////////////////////////////
		// Log
		msg_Log = "lower dir => " + item;
		Log.d("DLOI_LCL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		Methods.go_Down_Dir_Move(actv, item);
		
		return;
		
//		////////////////////////////////
//
//		// set: pref: pkey_ALActv__CurPath_Move
//
//		////////////////////////////////
//		boolean res = Methods.set_Pref_String(
//				actv, 
//				CONS.Pref.pname_ALActv, 
//				CONS.Pref.pkey_ALActv__CurPath_Move, 
//				item);
//		
//		if (res == true) {
//			
//			// Log
//			msg_Log = "pref set => " + item;
//			Log.d("LOI_LCL.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
//			
//		}
//
//		////////////////////////////////
//
//		// update: list
//
//		////////////////////////////////
//		Methods.update_MoveFilesList(actv, dlg1, dlg2, ai, aiList_Position, item);

		
	}//case_DLG_MOVE_FILES_FOLDER

	private void 
	case_ACTV_MEMO_LV_3(WordPattern wp) {
		// TODO Auto-generated method stub
		
		Methods_dlg.dlg_Admin_Patterns(actv, d1, wp);
		
	}

}//public class ListOnItemLongClickListener implements OnItemLongClickListener

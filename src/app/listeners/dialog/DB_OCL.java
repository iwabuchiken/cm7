package app.listeners.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import app.items.BM;
import app.utils.CONS;
import app.utils.DBUtils;
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

	public void onClick(View v) {
		//
		Tags.DialogTags tag_name = (Tags.DialogTags) v.getTag();

		// Log
		Log.d("DialogButtonOnClickListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "tag_name.name()=" + tag_name.name());
		
		//
		switch (tag_name) {
		
		case dlg_generic_dismiss://------------------------------------------------
			
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
			
			
		default: // ----------------------------------------------------
			break;
		}//switch (tag_name)
	}//public void onClick(View v)

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
			
	}//private void dlg_Conf_Delete_BM_Ok()

}//DialogButtonOnClickListener

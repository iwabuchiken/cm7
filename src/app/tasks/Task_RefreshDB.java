package app.tasks;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import app.utils.CONS;
import app.utils.Methods;
import app.utils.Ops;

public class Task_RefreshDB extends AsyncTask<String, Integer, String> {

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		
		// debug
		String msg_Toast = "Refreshing...";
		Toast.makeText(actv, msg_Toast, Toast.LENGTH_SHORT).show();
		
		
		super.onPreExecute();
		
	}

	//
	Activity actv;
	Dialog dlg;
	
	int num_of_new_items;
	
	public Task_RefreshDB(Activity actv) {
		
		this.actv = actv;
		
	}//public RefreshDBTask(Activity actv)
	
	@Override
	protected String doInBackground(String... params) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
		int result = Ops.refresh_MainDB(actv);
//		int result = Methods.refresh_MainDB(actv);
		
		// Log
		Log.d("RefreshDBTask.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "result => " + result);
		
		
		if (result > 0) {
			
			num_of_new_items = result;
			
			return "DB refreshed";
			
		} else if (result == CONS.Retval.CantCreateTable){//if (result == true)

			return "Can't create table";
			
		} else if (result == CONS.Retval.NoNewFiles){//if (result == true)
			
			return "No new audio files";

		} else {//if (result == true)
			
			return "Unknown result: " + result;
			
		}//if (result == true)
		
//		return null;
	}//protected String doInBackground(String... params)

	@Override
	protected void onPostExecute(String result) {
		/*********************************
		 * 1. super
		 * 2. Save history
		 * 3. Toast
		 *********************************/
		super.onPostExecute(result);

		
		// debug
		// debug
		Toast.makeText(actv, result, Toast.LENGTH_SHORT).show();
		
	}//protected void onPostExecute(String result)

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
//		super.onProgressUpdate(values);
		
	}//protected void onProgressUpdate(Integer... values)

}

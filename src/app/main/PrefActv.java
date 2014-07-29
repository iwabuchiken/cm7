package app.main;

import cm7.main.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import app.utils.CONS;

public class PrefActv extends PreferenceActivity 
					implements OnSharedPreferenceChangeListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		/*----------------------------
		 * Steps
		 * 1. Super
		 * 2. Set content
		 * 
		 * 3. Set preferences
		----------------------------*/
		super.onCreate(savedInstanceState);

		//
		setContentView(R.layout.main_pref);
//		setContentView(R.layout.main_pref);

		this.setTitle(this.getClass().getName());
		
		/*----------------------------
		 * 3. Set preferences
			----------------------------*/
		getPreferenceManager()
				.setSharedPreferencesName(CONS.Pref.pname_MainActv);
//		this.getString(R.string.prefs_shared_prefs_name));
		
//		addPreferencesFromResource(R.xm);
		addPreferencesFromResource(R.xml.preferences);
		
	}//public void onCreate(Bundle savedInstanceState)


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ黷ｽ�ｿｽ�ｿｽ�ｿｽ\�ｿｽb�ｿｽh�ｿｽE�ｿｽX�ｿｽ^�ｿｽu
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ黷ｽ�ｿｽ�ｿｽ�ｿｽ\�ｿｽb�ｿｽh�ｿｽE�ｿｽX�ｿｽ^�ｿｽu
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// TODO �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ黷ｽ�ｿｽ�ｿｽ�ｿｽ\�ｿｽb�ｿｽh�ｿｽE�ｿｽX�ｿｽ^�ｿｽu
		super.onPause();
		
		getPreferenceScreen().getSharedPreferences()
        	.unregisterOnSharedPreferenceChangeListener(this);
		
	}

	@Override
	protected void onResume() {
		// TODO �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ黷ｽ�ｿｽ�ｿｽ�ｿｽ\�ｿｽb�ｿｽh�ｿｽE�ｿｽX�ｿｽ^�ｿｽu
		super.onResume();
	}

	@Override
	protected void onStart() {
		
//		EditTextPreference prefEditText = 
		CONS.PrefActv.prefEditText = 
				(EditTextPreference) findPreference(
						this.getString(R.string.pkey_prefactv_step_length));
//		this.getString(R.string.prefs_history_size_key));
		
		CONS.PrefActv.prefEditText.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);

		_Setup_SetSummary(CONS.PrefActv.prefEditText);
		
//		_do_debug(prefEditText);
		
		getPreferenceScreen().getSharedPreferences()
        	.registerOnSharedPreferenceChangeListener(this);
		
		super.onStart();
		
	}//protected void onStart()

	private void 
	_Setup_SetSummary
	(EditTextPreference prefEditText) {
		// TODO Auto-generated method stub
		String pref_Text = prefEditText.getText();
		
		if (pref_Text == null || pref_Text.equals("")) {
			
			// Log
			String msg_Log = "pref_Text == null || pref_Text.equals(\"\")";
			Log.d("PrefActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		}
		
		String summary = this.getString(
							R.string.prefactv_summary_step_length);
//		String summary = prefEditText.getSummary().toString();
	
		summary = summary + "\nCurrent = " + pref_Text;
		
		prefEditText.setSummary(summary);
		
		
	}


	
	private void 
	_do_debug(EditTextPreference prefEditText) {
		// TODO Auto-generated method stub
		
		_debug_DialogPreference(prefEditText);
		
//		String text = prefEditText.getEditText().getText().toString();
//		
//		// Log
//		String msg_Log = "text => " + text;
//		Log.d("PrefActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		// Log
//		msg_Log = "pref text => " + prefEditText.getText();
//		Log.d("PrefActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//
//		// Log
//		msg_Log = "message text => " + prefEditText.getSummary().toString();
//		Log.d("PrefActv.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
	}


	private void 
	_debug_DialogPreference
	(EditTextPreference prefEditText) {
		// TODO Auto-generated method stub
		
		
		
		//REF http://stackoverflow.com/questions/10931638/get-positive-button-in-dialogpreference answered Jun 7 '12 at 14:29
		Dialog dlg = prefEditText.getDialog();
		
		if (dlg == null) {
			
			// Log
			String msg_Log = "dlg => null";
			Log.d("PrefActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			return;
			
		}
		
		Button bt = 
				(Button)((AlertDialog)dlg).getButton(
									AlertDialog.BUTTON_POSITIVE);
		
		String label = bt.getText().toString();
		
		// Log
		String msg_Log = "label => " + label;
		Log.d("PrefActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}//_debug_DialogPreference
	


	@Override
	protected void onStop() {
		// TODO �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ黷ｽ�ｿｽ�ｿｽ�ｿｽ\�ｿｽb�ｿｽh�ｿｽE�ｿｽX�ｿｽ^�ｿｽu
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ黷ｽ�ｿｽ�ｿｽ�ｿｽ\�ｿｽb�ｿｽh�ｿｽE�ｿｽX�ｿｽ^�ｿｽu
		super.onDestroy();
	}

	private void _test() {
		
		SharedPreferences prefs = 
				this.getSharedPreferences(
						CONS.Pref.pname_MainActv, 
						Context.MODE_PRIVATE);
		

	}


	
	//REF http://stackoverflow.com/questions/5456993/how-to-open-alertdialog-from-preference-screen answered Mar 28 '11 at 9:58
	@Override
	public void 
	onSharedPreferenceChanged
	(SharedPreferences sharedPref, String key) {
		// TODO Auto-generated method stub
		
		_Setup_SetSummary(CONS.PrefActv.prefEditText);
		
//		// Log
//		String msg_Log = "pref changed => " + key;
//		Log.d("PrefActv.java" + "["
// 				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
	}

}//public class PrefActv extends ListActivity

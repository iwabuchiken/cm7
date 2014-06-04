package app.listeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;
import android.widget.Toast;
import app.services.Service_ShowProgress;
import app.utils.CONS;

// From: CM5::MPOnCompletionListener
public class
MP_OCmpL implements OnCompletionListener {	// "MP" => MediaPlayer

	//
	Activity actv;
	
	public MP_OCmpL(Activity actv) {
		
		this.actv = actv;
		
	}
	
	//@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO ?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½?¿½ê‚½?¿½?¿½?¿½\?¿½b?¿½h?¿½E?¿½X?¿½^?¿½u
//		Methods.stopPlayer(actv);
		/***************************************
		 * Stop: Player
		 ***************************************/
		CONS.PlayActv.mp.stop();
		
		/***************************************
		 * Stop: Service
		 ***************************************/
		Intent i = new Intent((Context) actv, Service_ShowProgress.class);

		//
//		i.putExtra("counter", timeLeft);

		// Log
		Log.d("DialogOnItemClickListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "Stopping service...");

		//
//		actv.startService(i);
		actv.stopService(i);

		
		// debug
		Toast.makeText(actv, "Complete", Toast.LENGTH_LONG).show();
		
	}//public void onCompletion(MediaPlayer mp)

}//MPOnCompletionListener implements OnCompletionListener {

package app.listeners;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;
import app.items.AI;
import app.main.BMActv;
import app.utils.CONS;
import app.utils.DBUtils;
import app.utils.Methods;
import app.utils.Tags;

//REF http://stackoverflow.com/questions/4139288/android-how-to-handle-right-to-left-swipe-gestures answered Oct 17 '12 at 16:19
public class STL implements OnTouchListener {

	Activity actv;
	
	Tags.SwipeTags tag;

	private final GestureDetector gestureDetector;
	
	AI ai;		// swipe: playactv, left

	public STL (Context ctx){
		gestureDetector = new GestureDetector(ctx, new GestureListener());
		
		this.actv	= (Activity) ctx;
		
		// Log
		String msg_Log = "detector => initialized";
		Log.d("STL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}

	public STL(Context ctx, AI ai) {
		// TODO Auto-generated constructor stub
		gestureDetector = new GestureDetector(ctx, new GestureListener());
		
		this.actv	= (Activity) ctx;
		this.ai		= ai;
		
		// Log
		String msg_Log = "detector => initialized";
		Log.d("STL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

	}

	private final class 
	GestureListener 
	extends SimpleOnGestureListener {

		private static final int SWIPE_THRESHOLD = 100;
		private static final int SWIPE_VELOCITY_THRESHOLD = 100;

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean 
		onFling
		(MotionEvent e1, MotionEvent e2, 
				float velocityX, float velocityY) {
			
			// Log
			String msg_Log = "onFling";
			Log.d("STL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			
			boolean result = false;
			try {
				float diffY = e2.getY() - e1.getY();
				float diffX = e2.getX() - e1.getX();
				if (Math.abs(diffX) > Math.abs(diffY)) {
					if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
						if (diffX > 0) {
							
							onSwipeRight();
							
						} else {
							
							onSwipeLeft();
							
						}
					}
				} else {
					if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
						if (diffY > 0) {
							onSwipeBottom();
						} else {
							onSwipeTop();
						}
					}
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			
			return true;
//			return result;
			
		}//onFling
		
	}//GestureListener

	public void 
	onSwipeRight() {
		
		// Log
		String msg_Log = "Swipe right";
		Log.d("STL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		switch(this.tag) {
		
		case ACTV_BM:
		
			case_ACTV_BM_Right();
			
			break;
			
		case ACTV_PLAY:
			
			case_ACTV_PLAY_Right();
			
			break;
			
		default:
			
			// Log
			msg_Log = "unknown tag => " + this.tag.toString();
			Log.d("STL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			break;
			
		}//switch(this.tag)

	}//onSwipeRight()

	private void case_ACTV_PLAY_Right() {
		// TODO Auto-generated method stub
		Methods.stop_Player(actv);
		
		actv.finish();
		
		actv.overridePendingTransition(0, 0);

	}

	private void 
	case_ACTV_BM_Right() {
		// TODO Auto-generated method stub
		////////////////////////////////

		// Set: last position

		////////////////////////////////
		int lastPosition = ((ListActivity) actv).getListView().getLastVisiblePosition();
		
		boolean res = Methods.set_Pref_Int(actv, 
				CONS.Pref.pname_BMActv, 
				CONS.Pref.pkey_LastVisiblePosition_BMActv, 
				lastPosition);
		
		if (res == true) {
			
			// Log
			String msg_Log = String.format(
					"Pref => set: %s = %d", 
					CONS.Pref.pkey_LastVisiblePosition_BMActv, 
					lastPosition);
			
			Log.d("BMActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		} else {
			
			// Log
			String msg_Log = "Pref => can't be set: " 
					+ CONS.Pref.pkey_LastVisiblePosition_BMActv;
			
			Log.d("BMActv.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		
		////////////////////////////////

		// intent

		////////////////////////////////
		actv.setResult(CONS.Intent.RESULT_CODE_SEE_BOOKMARKS_CANCEL);
		
		actv.finish();
		
		actv.overridePendingTransition(0, 0);
		
	}//case_ACTV_BM_Right()

	private void 
	case_Right_SWIPE_ACTV_SHOWLIST() {
		// TODO Auto-generated method stub
		actv.finish();
		
		actv.overridePendingTransition(0, 0);

	}

	public void 
	onSwipeLeft() {
		
		// Log
		String msg_Log = "Swipe left";
		Log.d("STL.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		switch(this.tag) {
		
		case ACTV_PLAY:
			
//			Methods.start_Actv_BM(actv);
			case_ACTV_PLAY_Left();
			
			break;
			
		default:
			
			// Log
			msg_Log = "unknown tag => " + this.tag.toString();
			Log.d("STL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			break;
			
		}//switch(this.tag)
		
		
	}//onSwipeLeft()

	private void 
	case_ACTV_PLAY_Left() {
		// TODO Auto-generated method stub
		/***************************************
		 * Validate: Any bookmarks?
		 ***************************************/
		DBUtils dbu = new DBUtils(actv, CONS.DB.dbName);
		
		long numOfBM = dbu.getNumOfEntries_BM(
					actv, CONS.DB.tname_BM, ai.getDb_id());
		
		if (numOfBM < 1) {
			
			// Log
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "numOfBM < 1");
			
			// debug
			Toast.makeText(actv, "No bookmarks", Toast.LENGTH_LONG).show();
			
			return;
			
		} else {//if (numOfBM == condition)
			
			// Log
			Log.d("BO_CL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ ":"
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ "]", "numOfBM=" + numOfBM);
			
		}//if (numOfBM == condition)
		
		/***************************************
		 * Intent
		 ***************************************/
		Intent i = new Intent();
		
		i.setClass(actv, BMActv.class);
		
		i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		
//		i.putExtra("ai_dbId", ai.getDb_id());
		i.putExtra(CONS.Intent.iKey_BMActv_AI_Id, ai.getDb_id());
//		i.putExtra(CONS.Intent.bmactv_key_ai_id, ai.getDb_id());
		
		i.putExtra(CONS.Intent.iKey_BMActv_TableName, ai.getTable_name());
//		i.putExtra(CONS.Intent.bmactv_key_table_name, ai.getTable_name());
		
//		actv.startActivity(i);
		actv.startActivityForResult(i, CONS.Intent.REQUEST_CODE_SEE_BOOKMARKS);

	}//case_ACTV_PLAY_Left()

	public void onSwipeTop() {
	}

	public void onSwipeBottom() {
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		Tags.SwipeTags tag = (Tags.SwipeTags) v.getTag();
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			
			// Log
			String msg_Log = "tag => " + tag.toString();
			Log.d("STL.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			this.tag = tag;
			
			break;
			
		default:
			
//			// Log
//			msg_Log = "(default) tag => " + tag.toString();
//			Log.d("STL.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", msg_Log);
			break;
			
		}
		
		
		//REF http://stackoverflow.com/questions/4139288/android-how-to-handle-right-to-left-swipe-gestures answered Oct 21 '13 at 22:33
		return gestureDetector.onTouchEvent(event);
		
//		return false;
	}

		
}
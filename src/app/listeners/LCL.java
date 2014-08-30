package app.listeners;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnLongClickListener;
import app.utils.CONS;
import app.utils.Tags;

public class LCL implements OnLongClickListener {

	Activity actv;
	int position;
	
	public LCL(Activity actv, int position) {
		// TODO Auto-generated constructor stub
		
		this.actv		= actv;
		this.position	= position;
		
		CONS.Admin.vib	= (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}

	@Override
	public boolean onLongClick(View v) {
		
		CONS.Admin.vib.vibrate(CONS.Admin.vibLength_click);
		
		Tags.ButtonTags tag = (Tags.ButtonTags) v.getTag();
		
		switch (tag) {
		
		case ACTV_AI_LIST_MOVE_CB:
			
			case_ACTV_AI_LIST_MOVE_CB();	
			
			break;
			
		default:
			break;
			
		}
//		TILIST_CB
		
		return true;
//		return false;
	}

	private void 
	case_ACTV_AI_LIST_MOVE_CB() {
		// TODO Auto-generated method stub
		
		if (CONS.ALActv.checkedPositions.contains((Integer) position)) {

			CONS.ALActv.checkedPositions.clear();
			
//			TNActv.aAdapter.notifyDataSetChanged();
			CONS.ALActv.adp_TNActv_Main_Move.notifyDataSetChanged();
			
		/*----------------------------
		 * 2. If not
			----------------------------*/
		} else {//if (CONS.TNActv.checkedPositions.contains((Integer) position))
			
			CONS.ALActv.checkedPositions.clear();
			
			for (int i = 0; i < CONS.ALActv.list_AI.size(); i++) {
				
				CONS.ALActv.checkedPositions.add((Integer) i);
				
			}//for (int i = 0; i < TNActv.tiList.size(); i++)
			
//			TNActv.aAdapter.notifyDataSetChanged();
			CONS.ALActv.adp_TNActv_Main_Move.notifyDataSetChanged();
			
		}//if (CONS.TNActv.checkedPositions.contains((Integer) position))	
	}

}

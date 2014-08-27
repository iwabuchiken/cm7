package app.listeners.text;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.TextView;
import app.items.AI;
import app.utils.Methods_dlg;
import app.utils.Tags;
import app.utils.Tags.ItemTags;
import app.utils.Tags.TVTags;

// TextView_LongClickListener
public class TV_LCL implements OnLongClickListener {

	//
	Activity actv;
	
	//
	int position;
	
	AI ai;
	
	//
//	ItemTags itemTag;
	TVTags tvTag;
	
	public TV_LCL(Activity actv, int position) {
		
		this.actv = actv;
		this.position = position;
		
	}
	
	public TV_LCL(Activity actv) {
		// TODO Auto-generated constructor stub
		
		this.actv = actv;
		
	}

	public TV_LCL(Activity actv, AI ai) {
		// TODO Auto-generated constructor stub
		
		this.actv	= actv;
		this.ai		= ai;
		
	}

	//	@Override
	public boolean onLongClick(View v) {
		
		tvTag = (Tags.TVTags) v.getTag();
		
		switch (tvTag) {
		
		case PLAYACTV_TITLE:
			
			case_PlayActv_Title(v);
			
			break;
			
		default:
			break;
		
		}//switch (tag)
			
		return true;
	}//public boolean onLongClick(View arg0)

	private void
	case_PlayActv_Title(View v) {
		// TODO Auto-generated method stub
		
//		EditText tv_Title = (EditText) v;
		TextView tv_Title = (TextView) v;
		
		String currentTitle = tv_Title.getText().toString();
		
		Methods_dlg.dlg_PlayActv_EditTitle(actv, ai, currentTitle);
		
//		// Log
//		String msg_Log = "currentTitle" + currentTitle;
//		Log.d("TV_LCL.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
		
	}//case_PlayActv_Title(View v)

}

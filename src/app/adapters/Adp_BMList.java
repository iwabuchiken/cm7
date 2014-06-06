package app.adapters;

import java.util.List;

import cm7.main.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.items.BM;
import app.utils.Methods;

public class Adp_BMList extends ArrayAdapter<BM> {

	private Context con;
	private LayoutInflater inflater;

	public Adp_BMList(Context con, int resourceId, List<BM> bmList) {
		// Super
		super(con, resourceId, bmList);

		// Context
		this.con = con;

		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}//public BMLAdapter(Context con, int resourceId, List<BM> bmList)

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	/****************************
		 * 0. View
		 * 
		 * 1. Set layout
		 * 2. Get view
		 * 
		 * 3. Get item
		 * 
		 * 4. Set file name
		 * 
		 * 5. Set title
		 * 
		 * 9. Return view
			****************************/
    	/****************************
		 * 0. View
			****************************/
    	View v = null;

    	if (convertView != null) {
    		
			v = convertView;
			
		} else {//if (convertView != null)

			v = inflater.inflate(R.layout.listrow_actv_bm, null);
			
		}//if (convertView != null)

    	/***************************************
		 * Get item
		 ***************************************/
    	BM bm = (BM) getItem(position);
    	
    	/***************************************
		 * Set: Position
		 ***************************************/
    	TextView tvPosition = (TextView) v.findViewById(R.id.listrow_actv_bm_tv_point);
    	
    	tvPosition.setText(bm.getPosition());//
//    	Methods.convert_intSec2Digits_lessThanHour((int) (bm.getPosition() / 1000)));//

    	/***************************************
		 * Set: Title
		 ***************************************/
    	TextView tvTitle = (TextView) v.findViewById(R.id.listrow_actv_bm_tv_title);
    	
    	tvTitle.setText(bm.getTitle());//
    	
    	/***************************************
		 * Set: Memo
		 ***************************************/
    	TextView tvMemo = (TextView) v.findViewById(R.id.listrow_actv_bm_tv_memo);
    	
    	tvMemo.setText(bm.getMemo());//
    	
    	
		return v;
		
    }//public View getView(int position, View convertView, ViewGroup parent)

	
}//public class BMLAdapter extends ArrayAdapter<BM>

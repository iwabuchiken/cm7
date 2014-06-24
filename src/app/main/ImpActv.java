package app.main;

import cm7.main.R;
import android.app.ListActivity;
import android.os.Bundle;

public class ImpActv extends ListActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	/*----------------------------
		 * 1. super
		 * 2. Set content
		 * 2-2. Set title
		 * 3. Initialize => vib
		 * 
		 *  4. Set list
		 *  5. Set listener => Image buttons
		 *  6. Set path label
		 *  
		 *  7. Initialize preferences
		 *  
		 *  8. Refresh DB
			----------------------------*/
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actv_imp);
        
        /*----------------------------
		 * 2-2. Set title
			----------------------------*/
		this.setTitle(this.getClass().getName());
        
        
    }//public void onCreate(Bundle savedInstanceState)

	@Override
	public void onBackPressed() {
		/****************************
		 * memo
			****************************/
		this.finish();
		
		overridePendingTransition(0, 0);
		
	}//public void onBackPressed()

}

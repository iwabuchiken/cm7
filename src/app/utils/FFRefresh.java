package app.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import app.items.Refresh;

public class FFRefresh implements FileFilter {

	Activity actv;
	int pastXDays;
	
	Refresh last_history;
	
	public FFRefresh(Activity actv, int pastXDays) {
		// TODO Auto-generated constructor stub
		
		this.actv	= actv;
		this.pastXDays	= pastXDays;
		
		this.last_history = DBUtils.get_LatestEntry_Refresh(actv);
		
	}

	@Override
	public boolean accept(File file) {
		// TODO Auto-generated method stub
		
		long last_refreshed;
		
		//REF http://stackoverflow.com/questions/4348525/get-date-as-of-4-hours-ago answered Dec 3 '10 at 18:19
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, pastXDays);
//				calendar.add(Calendar.HOUR_OF_DAY, -4);
		Date date = calendar.getTime();
		
		long time_XDaysAgo = date.getTime();

		/******************************
			validate
		 ******************************/
		if (last_history == null) {
			
			last_refreshed = time_XDaysAgo;
			
//			return true;
			
		} else {
			
			long last_record = 
					Methods.conv_TimeLabel_to_MillSec(last_history.getLast_refreshed());
			
			last_refreshed = 
					(last_record > time_XDaysAgo) ? last_record : time_XDaysAgo;
			
		}
		
//		long last_refreshed = 
//				Methods.conv_TimeLabel_to_MillSec(last_history.getLast_refreshed());
		
//		//REF http://stackoverflow.com/questions/4348525/get-date-as-of-4-hours-ago answered Dec 3 '10 at 18:19
//		Calendar calendar = Calendar.getInstance();
//		calendar.add(Calendar.DAY_OF_MONTH, pastXDays);
////				calendar.add(Calendar.HOUR_OF_DAY, -4);
//		Date date = calendar.getTime();
//		
//		long time_4DaysAgo = date.getTime();
		
		// TODO Auto-generated method stub
		return (file.lastModified() > last_refreshed);
//		return (file.lastModified() > time_4DaysAgo)
//				&& (file.lastModified() > last_refreshed);
//		return file.lastModified() > time_4DaysAgo;

	}

}

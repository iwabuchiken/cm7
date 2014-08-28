package app.comps;


import java.util.Comparator;
import java.util.List;

import app.items.WordPattern;
import app.utils.CONS.Enums.SortOrder;
import app.utils.CONS.Enums.SortType;
import app.utils.Methods;

public class Comp_WP implements Comparator<WordPattern> {

	List<WordPattern> ti_List;
	SortType sortType;
	SortOrder sortOrder;
	
	
	public Comp_WP
	(List<WordPattern>
		ti_List, SortType sortType,
		SortOrder sortOrder) {
		
		this.ti_List	= ti_List;
		
		this.sortType	= sortType;
		
		this.sortOrder	= sortOrder;
		
	}

	public Comp_WP
	(SortType sortType, SortOrder sortOrder) {
		// TODO Auto-generated constructor stub
		this.sortType	= sortType;
		
		this.sortOrder	= sortOrder;
		
	}

	@Override
	public int compare(WordPattern a1, WordPattern a2) {
		// TODO Auto-generated method stub
		int res;
		
		switch(sortType) {
		
//		case FileName:
		case WORD:
			
			res = _compare_Word(a1, a2);
			
			break;
			
		case CREATED_AT:
			
			res = _compare_CREATED_AT(a1, a2);
			
			break;
			
		default:
			
			res = 1;
		
		}
		
		return res;
		
	}//public int compare(AI a1, AI a2)

	private int _compare_Word(WordPattern t1, WordPattern t2) {
		// TODO Auto-generated method stub
		
		int res;
		
		switch (sortOrder) {
		
		case ASC:
			
//				res = (int) (a1.getCreated_at() - a2.getCreated_at());
			res = t1.getWord().compareTo(t2.getWord());
			
			break;
			
		case DESC:
			
//				res = (int) -(a1.getCreated_at() - a2.getCreated_at());
			res = t2.getWord().compareTo(t1.getWord());
			
			break;
			
		default:
			
			res = 1;
			
			break;
		}
		
		return res;

	}//private int _compare_FileName(AI a1, AI a2)
	
	private int _compare_CREATED_AT(WordPattern t1, WordPattern t2) {
		// TODO Auto-generated method stub
		
		int res;
		
		long time1 = Methods.conv_TimeLabel_to_MillSec(t1.getCreated_at());
		long time2 = Methods.conv_TimeLabel_to_MillSec(t2.getCreated_at());
		
		switch (sortOrder) {
		
		case ASC:
			
			res = (time1 < time2) ? 1 : 0;
			
			break;
			
		case DESC:

			res = (time1 > time2) ? 1 : 0;
			
			break;
			
		default:
			
			res = 1;
			
			break;
		}
		
		return res;
		
	}//private int _compare_FileName(AI a1, AI a2)

}//public class AIComparator implements Comparator<AI>

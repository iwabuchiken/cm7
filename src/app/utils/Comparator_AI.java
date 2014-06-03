package app.utils;

import java.util.Comparator;
import java.util.List;

import app.items.AI;
import app.utils.CONS.Enums.SortType;
import app.utils.CONS.Enums.SortOrder;

public class Comparator_AI implements Comparator<AI> {

	List<AI> ai_List;
	SortType sortType;
	SortOrder sortOrder;
	
	
	public Comparator_AI
	(List<AI>
		ai_List, SortType sortType,
		SortOrder sortOrder) {
		
		this.ai_List	= ai_List;
		
		this.sortType	= sortType;
		
		this.sortOrder	= sortOrder;
		
	}

	@Override
	public int compare(AI a1, AI a2) {
		// TODO Auto-generated method stub
		int res;
		
		switch(sortType) {
		
		case FileName:
			
			res = _compare_FileName(a1, a2);
			
			break;
			
		default:
			
			res = 1;
		
		}
		
//			switch (sortOrder) {
//			
//			case ASC:
//				
////					res = (int) (a1.getCreated_at() - a2.getCreated_at());
//				res = a1.getFile_name().compareTo(a2.getFile_name());
//				
//				break;
//				
//			case DEC:
//				
////					res = (int) -(a1.getCreated_at() - a2.getCreated_at());
//				res = a2.getFile_name().compareTo(a1.getFile_name());
//				
//				break;
//				
//			default:
//				
//				res = 1;
//				
//				break;
//				
//			}
		
		return res;
		
	}//public int compare(AI a1, AI a2)

	private int _compare_FileName(AI a1, AI a2) {
		// TODO Auto-generated method stub
		
		int res;
		
		switch (sortOrder) {
		
		case ASC:
			
//				res = (int) (a1.getCreated_at() - a2.getCreated_at());
			res = a1.getFile_name().compareTo(a2.getFile_name());
			
			break;
			
		case DEC:
			
//				res = (int) -(a1.getCreated_at() - a2.getCreated_at());
			res = a2.getFile_name().compareTo(a1.getFile_name());
			
			break;
			
		default:
			
			res = 1;
			
			break;
		}
		
		return res;

	}//private int _compare_FileName(AI a1, AI a2)

}//public class AIComparator implements Comparator<AI>

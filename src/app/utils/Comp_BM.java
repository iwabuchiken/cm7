package app.utils;

import java.util.Comparator;
import java.util.List;

import app.items.AI;
import app.items.BM;
import app.utils.CONS.Enums.SortType;
import app.utils.CONS.Enums.SortOrder;

public class Comp_BM implements Comparator<BM> {

	List<BM> ai_List;
	SortType sortType;
	SortOrder sortOrder;
	
	
	public Comp_BM
	(List<BM>
		ai_List, SortType sortType,
		SortOrder sortOrder) {
		
		this.ai_List	= ai_List;
		
		this.sortType	= sortType;
		
		this.sortOrder	= sortOrder;
		
	}

	@Override
	public int compare(BM b1, BM b2) {
		// TODO Auto-generated method stub
		int res;
		
		switch(sortType) {
		
		case POSITION:
			
			res = _compare_Position(b1, b2);
			
			break;
			
		default:
			
			res = 1;
		
		}
		
//			switch (sortOrder) {
//			
//			case ASC:
//				
////					res = (int) (b1.getCreated_at() - b2.getCreated_at());
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
		
	}//public int compare(BM b1, BM b2)

	private int 
	_compare_Position(BM b1, BM b2) {
		// TODO Auto-generated method stub
		int res;
		
		String pos_B1 = b1.getPosition();
		String pos_B2 = b2.getPosition();
		
		switch (sortOrder) {
		
		case ASC:
			
//				res = (int) (a1.getCreated_at() - a2.getCreated_at());
//			String pos_B1 = b1.getPosition();
//			String pos_B2 = b2.getPosition();
			
			res = (Methods.conv_ClockLabel_to_MillSec(pos_B1)
						> Methods.conv_ClockLabel_to_MillSec(pos_B2))
						? 1 : -1;
			
			break;
			
		case DEC:
			
			res = (Methods.conv_ClockLabel_to_MillSec(pos_B1)
					< Methods.conv_ClockLabel_to_MillSec(pos_B2))
					? 1 : -1;
			
//				res = (int) -(b1.getCreated_at() - a2.getCreated_at());
//			res = b2.getFile_name().compareTo(b1.getFile_name());
			
			break;
			
		default:
			
			res = 1;
			
			break;
		}
		
		return res;
		
	}//_compare_Position(BM b1, BM b2)

}//public class BMComparator implements Comparator<BM>

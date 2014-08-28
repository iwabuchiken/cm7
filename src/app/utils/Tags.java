package app.utils;

public class Tags {

	public static enum DialogTags {
		// Generics
		GENERIC_DISMISS, DLG_GENERIC_DISMISS_SECOND_DIALOG,
		DLG_GENERIC_DISMISS_THIRD_DIALOG,
		
		
		// dlg_create_folder.xml
		DLG_CREATE_FOLDER_OK, dlg_create_folder_cancel,

		// dlg_input_empty.xml
		dlg_input_empty_reenter, dlg_input_empty_cancel,
		
		// dlg_confirm_create_folder.xml
		DLG_CONFIRM_CREATE_FOLDER_OK, DLG_CONFIRM_CREATE_FOLDER_CANCEL,

		// dlg_confirm_remove_folder.xml
		dlg_confirm_remove_folder_ok, dlg_confirm_remove_folder_cancel,

		// dlg_drop_table.xml
		dlg_drop_table_btn_cancel, dlg_drop_table,
		
		// dlg_confirm_drop.xml
		dlg_confirm_drop_table_btn_ok, dlg_confirm_drop_table_btn_cancel,
		
		// dlg_add_memos.xml
		dlg_add_memos_bt_add, dlg_add_memos_bt_cancel, dlg_add_memos_bt_patterns,
		dlg_add_memos_gv,

		// dlg_move_files.xml
		dlg_move_files_move, dlg_move_files,
		
		// dlg_confirm_move_files.xml	=> ok, cancel, dlg tag
		dlg_confirm_move_files_ok, dlg_confirm_move_files_cancel, dlg_confirm_move_files,

		// dlg_item_menu.xml
		dlg_item_menu_bt_cancel, dlg_item_menu,

		// dlg_create_table.xml
		dlg_create_table_bt_create,

		// dlg_memo_patterns.xml
		dlg_memo_patterns,
		
		// dlg_register_patterns.xml
		dlg_register_patterns_register,

		// dlg_search.xml
		dlg_search, dlg_search_ok,

		// dlg_admin_patterns.xml

		// dlg_confirm_delete_patterns.xml
		dlg_confirm_delete_patterns_ok,
		
		// dlg_edit_title.xml
		DLG_PLAYACTV_EDIT_TITLE_BT_OK,
		
		// dlg_edit_title.xml(Dialog: egit memo)
		dlg_edit_memo_bt_ok,

		// dlg_confirm_move_files_search	=> ok, cancel, dlg tag
		dlg_confirm_move_files_search_ok,
		
		// dlg_edit_item.xml
		DLG_EDIT_ITEM_BT_OK,
		
		// dlg: confirm delete BM
		dlg_conf_delete_BM_ok,
		
		// dlg: confirm delete AI
//		dlg_conf_delete_BM_ok,
		DLG_CONF_DELETE_AI_OK,
		
		// dlg_edit_item.xml
		DLG_EDIT_AI_BT_OK,
		
		// dlg: delete folder
		DLG_DELETE_FOLDER_CONF_OK,

		// dlg: create dir
		DLG_CREATE_DIR_OK, DLG_CREATE_DIR_CONF_OK,

		// dlg: move file
		DLG_ALACTV_MOVEFILE_CONF_OK,
		
		DLG_CONF_IMPORT_DB_OK,
		
		DLG_CONF_IMPORT_PATTERNS_OK, DLG_CONF_DELETE_PATTERN_OK, DLG_REGISTER_PATTERNS_OK,
		
	}//public static enum DialogTags
	
	public static enum DialogItemTags {
		// dlg_moveFiles(Activity actv)
		dlg_move_files,
		
		// dlg_add_memos.xml
		DLG_ADD_MEMOS_GV_1, DLG_ADD_MEMOS_GV_2, DLG_ADD_MEMOS_GV_3,

		// dlg_db_admin.xml
		DLG_DB_ADMIN_LV,

		// dlg_admin_patterns.xml
		dlg_admin_patterns_lv,

		// dlg_delete_patterns.xml
		dlg_delete_patterns_gv,

		// dlg_moveFiles_search(Activity actv)
		dlg_move_files_search,

		// main_opt_menu_admin
		main_opt_menu_admin,
		
		// dlg_bmactv_list_long_click
		DLG_BMACTV_LIST_LONGCLICK,
		
		// dlg_alactv_list_long_click
		DLG_ALACTV_LIST_LONGCLICK,
		
		// dlg_alactv_list_move_file
		DLG_ALACTV_LIST_MOVE_FILE,
		
		// dlg_ImpActv
		DLG_IMPACTV_LIST,
		
		// dlg: MainActv, long click
		DLG_ACTVMAIN_LONGCLICK, 
		
		DLG_ACTV_MAIN_OPERATIONS, ACTV_PLAY_PATTERNS_LONGCLICK_LV, ACTV_PLAY_OPTION_ADMIN_PATTERNS,
		
	}//public static enum DialogItemTags
	
	
	public static enum ButtonTags {
		// MainActivity.java
		ib_up,
		
		// DBAdminActivity.java
		db_manager_activity_create_table, db_manager_activity_drop_table, 
		db_manager_activity_register_patterns,
		
		// thumb_activity.xml
		thumb_activity_ib_back, thumb_activity_ib_bottom, thumb_activity_ib_top,
		
		// image_activity.xml
		image_activity_back, image_activity_prev, image_activity_next,
		
		// AILAdapter.java
		ailist_cb,
		
		// actv_play.xml
		actv_play_bt_play, actv_play_bt_stop, actv_play_bt_back,
		actv_play_tv_title, actv_play_tv_memo,
		ACTV_PLAY_BT_BACKWARD, ACTV_PLAY_BT_FORWARD,
		
		actv_play_bt_see_bm, actv_play_bt_add_bm,
		
		// SearchActv.java
		actv_search_ib_bottom, actv_search__ib_top,

		// AILAdapter_move.java
		ailist_cb_search,
		
		// actv_bm.xml
		actv_bm_bt_back,
		actv_bm_ib_top, actv_bm_ib_bottom,
		actv_bm_ib_up, actv_bm_ib_down,
		actv_bm_ib_back,
		
		// actv_hist.xml
		actv_hist_ib_back, actv_hist_ib_bottom, actv_hist_ib_top,

		
		
	}//public static enum ButtonTags
	
	public static enum ItemTags {
		
		// MainActivity.java
		dir_list,
		
		// ThumbnailActivity.java
		dir_list_thumb_actv,
		
		// Methods.java
		dir_list_move_files,
		
		// TIListAdapter.java
		tilist_checkbox,
		
		// SearchActv.java
		dir_list_actv_search,
		
		// actv_hist.xml
		dir_list_actv_hist,
		
	}//public static enum ItemTags

//	public static enum MoveMode {
//		// TIListAdapter.java
//		ON, OFF,
//		
//	}//public static enum MoveMode

	public static enum PrefenceLabels {
		
		CURRENT_PATH,
		
		thumb_actv,
		
		chosen_list_item,
		
	}//public static enum PrefenceLabels

	public static enum ListTags {
		// MainActivity.java
		ACTV_MAIN_LV,
		
		// BMActv.java
		actv_bm_lv,
		
		// ALActv.java
		actv_ALActv_lv,
		
	}//public static enum ListTags

	public static enum TVTags {
		
		// PlayActv
		PLAYACTV_TITLE,
	}

	public static enum SwipeTags {
		
		ACTV_SHOWLIST, ACTV_BM,
		
		ACTV_PLAY,
		
	}
	
}//public class Tags

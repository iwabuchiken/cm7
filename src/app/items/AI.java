package app.items;


public class AI {
	/*----------------------------
	 * Class fields
		----------------------------*/
	long	db_id;
	String	created_at;
	String	modified_at;
	
	String	audio_created_at;
	
	String file_name;
	String file_path;
	
	String title;
	String memo;
	
	String last_played_at;
	
	String table_name;

	long length;

	////////////////////////////////

	// Constructor

	////////////////////////////////
	
	public AI(Builder builder) {
		super();
		
		this.db_id		= builder.db_id;
		this.created_at	= builder.created_at;
		this.modified_at	= builder.modified_at;
		
		this.file_name	= builder.file_name;
		this.file_path	= builder.file_path;
		
		this.title		= builder.title;
		this.memo		= builder.memo;
		
		this.last_played_at	= builder.last_played_at;
		
		this.table_name	= builder.table_name;

		this.length		= builder.length;

		
//		this.id				= builder.id;
//		this.created_at		= builder.created_at;
//		this.modified_at	= builder.modified_at;
//		this.longitude		= builder.longitude;
//		this.latitude		= builder.latitude;
//		this.memo			= builder.memo;
//		this.uploaded_at	= builder.uploaded_at;
	}

	
	
	
//	
//	
//	public AI(
//			String file_name, String file_path,
//			String title, String memo,
//			
//			long last_played_at,
//			
//			String table_name, long created_at) {
//
//		this.file_name = file_name;
//		this.file_path = file_path;
//		
//		this.title = title;
//		this.memo = memo;
//		
//		this.last_played_at = last_played_at;
//		
//		this.table_name = table_name;
//		
//		this.created_at = created_at;
//		
//	}//public ThumbnailItem(long fileId, String file_path, long date_added, long date_modified)
//
//	public AI(
//			String file_name, String file_path,
//			String title, String memo,
//			
//			long last_played_at,
//			
//			String table_name,
//			
//			long length,
//			
//			long created_at) {
//
//		this.file_name = file_name;
//		this.file_path = file_path;
//		
//		this.title = title;
//		this.memo = memo;
//		
//		this.last_played_at = last_played_at;
//		
//		this.table_name = table_name;
//		
//		this.length = length;
//		
//		this.created_at = created_at;
//		
//	}//public ThumbnailItem(long fileId, String file_path, long date_added, long date_modified)

	/*********************************
	 * Data in DB into an AI object
	 *********************************/
//	public AI(
//			String file_name, String file_path,
//			String title, String memo,
//			
//			long last_played_at,
//			
//			String table_name,
//			
//			long db_id, long created_at, long modified_at) {
//
//		this.file_name = file_name;
//		this.file_path = file_path;
//		
//		this.title = title;
//		this.memo = memo;
//		
//		this.last_played_at = last_played_at;
//		
//		this.table_name = table_name;
//		
//		this.db_id = db_id;
//		this.created_at = created_at;
//		this.modified_at = modified_at;
//		
//	}//public ThumbnailItem(long fileId, String file_path, long date_added, long date_modified)

//	public AI(String file_name, String file_path,
//			String title, String memo,
//			
//			long last_played_at,
//			
//			String table_name, long length,
//			
//			long db_id, long created_at, long modified_at) {
//		// TODO Auto-generated constructor stub
//		this.file_name = file_name;
//		this.file_path = file_path;
//		
//		this.title = title;
//		this.memo = memo;
//		
//		this.last_played_at = last_played_at;
//		
//		this.table_name = table_name;
//
//		this.length = length;
//		
//		this.db_id = db_id;
//		this.created_at = created_at;
//		this.modified_at = modified_at;
//
//	}

	public long getDb_id() {
		return db_id;
	}




	public String getCreated_at() {
		return created_at;
	}




	public String getModified_at() {
		return modified_at;
	}




	public String getAudio_created_at() {
		return audio_created_at;
	}




	public String getFile_name() {
		return file_name;
	}




	public String getFile_path() {
		return file_path;
	}




	public String getTitle() {
		return title;
	}




	public String getMemo() {
		return memo;
	}




	public String getLast_played_at() {
		return last_played_at;
	}




	public String getTable_name() {
		return table_name;
	}




	public long getLength() {
		return length;
	}




	public void setDb_id(long db_id) {
		this.db_id = db_id;
	}




	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}




	public void setModified_at(String modified_at) {
		this.modified_at = modified_at;
	}




	public void setAudio_created_at(String audio_created_at) {
		this.audio_created_at = audio_created_at;
	}




	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}




	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}




	public void setTitle(String title) {
		this.title = title;
	}




	public void setMemo(String memo) {
		this.memo = memo;
	}




	public void setLast_played_at(String last_played_at) {
		this.last_played_at = last_played_at;
	}




	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}




	public void setLength(long length) {
		this.length = length;
	}




	/*********************************
	 * Class: Builder
	 *********************************/
	public static class Builder {
		
		long	db_id;
		String	created_at;
		String	modified_at;
		
		String	audio_created_at;
		
		String file_name;
		String file_path;
		
		String title;
		String memo;
		
		String last_played_at;
		
		String table_name;

		long length;

		////////////////////////////////

		// Constructor

		////////////////////////////////
		
		public AI build() {
			
			return new AI(this);
			
		}

		public Builder setDb_id(long db_id) {
			this.db_id = db_id; return this;
		}

		public Builder setCreated_at(String created_at) {
			this.created_at = created_at; return this;
		}

		public Builder setModified_at(String modified_at) {
			this.modified_at = modified_at; return this;
		}

		public Builder setAudio_created_at(String audio_created_at) {
			this.audio_created_at = audio_created_at; return this;
		}

		public Builder setFile_name(String file_name) {
			this.file_name = file_name; return this;
		}

		public Builder setFile_path(String file_path) {
			this.file_path = file_path; return this;
		}

		public Builder setTitle(String title) {
			this.title = title; return this;
		}

		public Builder setMemo(String memo) {
			this.memo = memo; return this;
		}

		public Builder setLast_played_at(String last_played_at) {
			this.last_played_at = last_played_at; return this;
		}

		public Builder setTable_name(String table_name) {
			this.table_name = table_name; return this;
		}

		public Builder setLength(long length) {
			this.length = length; return this;
		}

		
//		public Builder setDb_id(long db_id) {
//			this.db_id = db_id; return this;
//		}
//
//		public Builder setCreated_at(long created_at) {
//			this.created_at = created_at; return this;
//		}
//
//		public Builder setFile_name(String file_name) {
//			this.file_name = file_name; return this;
//		}
//
//		public Builder setFile_path(String file_path) {
//			this.file_path = file_path; return this;
//		}
//
//		public Builder setMemo(String memo) {
//			this.memo = memo; return this;
//		}
//
//		public Builder setLast_played_at(long last_played_at) {
//			this.last_played_at = last_played_at; return this;
//		}
//
//		public Builder setLength(long length) {
//			this.length = length; return this;
//		}
//
//		public Builder setModified_at(long modified_at) {
//			this.modified_at = modified_at; return this;
//		}
//
//		public Builder setTitle(String title) {
//			this.title = title; return this;
//		}
//
//		public Builder setTable_name(String table_name) {
//			this.table_name = table_name; return this;
//		}

		

	}//public static class Builder

	
	/*----------------------------
	 * Methods
		----------------------------*/
	
}//public class ThumbnailItem

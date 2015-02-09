package app.items;

public class BMStrore {

//	android.provider.BaseColumns._ID,	// 0
//	"created_at", "modified_at",		// 1,2
//	"ai_name", "position",				// 3,4
//	"title", "memo",					// 5,6
//	"orig_created_at", "orig_modified_at",	// 7,8

	long	dbId;
	
	String	created_at;
	String	modified_at;
	
	String	ai_name;
	
	String	position;
	String	title;
	String	memo;
	
	String	orig_created_at;
	String	orig_modified_at;
	
	public BMStrore() {
		
	}


	
	public BMStrore(Builder builder) {
		// TODO Auto-generated constructor stub
		dbId = builder.dbId;

		this.created_at	= builder.created_at;
		this.modified_at	= builder.modified_at;
		
		this.ai_name	= builder.ai_name;

		position = builder.position;
		title = builder.title;
		memo = builder.memo;
		
		this.orig_created_at	= builder.orig_created_at;
		this.orig_modified_at	= builder.orig_modified_at;

	}//public BM(Builder builder)



	public String getCreated_at() {
		return created_at;
	}



	public String getModified_at() {
		return modified_at;
	}



	public String getAi_name() {
		return ai_name;
	}



	public String getOrig_created_at() {
		return orig_created_at;
	}



	public String getOrig_modified_at() {
		return orig_modified_at;
	}



	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}



	public void setModified_at(String modified_at) {
		this.modified_at = modified_at;
	}



	public void setAi_name(String ai_name) {
		this.ai_name = ai_name;
	}



	public void setOrig_created_at(String orig_created_at) {
		this.orig_created_at = orig_created_at;
	}



	public void setOrig_modified_at(String orig_modified_at) {
		this.orig_modified_at = orig_modified_at;
	}



	public String getPosition() {
		return position;
	}

	public String getTitle() {
		return title;
	}

	public String getMemo() {
		return memo;
	}

	public void setPosition(String point) {
		this.position = point;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public static class Builder {

		private long	dbId;
		
		private String	created_at;
		private String	modified_at;
		
		private String	ai_name;
		
		private String	position;
		private String	title;
		private String	memo;
		
		private String	orig_created_at;
		private String	orig_modified_at;
		
		
		public BMStrore build() {
			return new BMStrore(this);
		}



		public long getDbId() {
			return dbId;
		}



		public String getCreated_at() {
			return created_at;
		}



		public String getModified_at() {
			return modified_at;
		}



		public String getAi_name() {
			return ai_name;
		}



		public String getPosition() {
			return position;
		}



		public String getTitle() {
			return title;
		}



		public String getMemo() {
			return memo;
		}



		public String getOrig_created_at() {
			return orig_created_at;
		}



		public String getOrig_modified_at() {
			return orig_modified_at;
		}



		public Builder setCreated_at(String created_at) {
			this.created_at = created_at; return this;
		}



		public Builder setModified_at(String modified_at) {
			this.modified_at = modified_at; return this;
		}



		public Builder setAi_name(String ai_name) {
			this.ai_name = ai_name; return this;
		}



		public Builder setOrig_created_at(String orig_created_at) {
			this.orig_created_at = orig_created_at; return this;
		}



		public Builder setOrig_modified_at(String orig_modified_at) {
			this.orig_modified_at = orig_modified_at; return this;
		}



		public Builder setPosition(String val) {
			position = val;	return this;
		}



		public Builder setTitle(String val) {
			title = val;	return this;
		}



		public Builder setMemo(String val) {
			memo = val;	return this;
		}



		public Builder setDbId(long val) {
			this.dbId = val;	return this;
		}

		
	}//public static class Builder

	public long getDbId() {
		return dbId;
	}



	public void setDbId(long dbId) {
		this.dbId = dbId;
	}
	
	
}//public class BM

package app.items;

public class BM {

	long	dbId;

	String	created_at;
	String	modified_at;

//	long	position;
	String	position;
	String	title;
	String	memo;
	
	long	aiId;
	String	aiTableName;
	
	public BM() {
		
	}


	
	public BM(Builder builder) {
		// TODO Auto-generated constructor stub
		dbId = builder.dbId;
		this.created_at = builder.created_at;
		this.modified_at = builder.modified_at;
		
		position = builder.position;
		title = builder.title;
		memo = builder.memo;
		aiId = builder.aiId;
		aiTableName = builder.aiTableName;
		
	}//public BM(Builder builder)



	public String getCreated_at() {
		return created_at;
	}



	public String getModified_at() {
		return modified_at;
	}



	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}



	public void setModified_at(String modified_at) {
		this.modified_at = modified_at;
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

	public long getAiId() {
		return aiId;
	}

	public String getAiTableName() {
		return aiTableName;
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

	public void setAiId(long aiId) {
		this.aiId = aiId;
	}

	public void setAiTableName(String aiTableName) {
		this.aiTableName = aiTableName;
	}
	
	public static class Builder {

		private long	dbId;
		String	created_at;
		String	modified_at;

		private String	position;
		private String	title;
		private String	memo;
		private long	aiId;
		private String	aiTableName;
		
		
		public BM build() {
			return new BM(this);
		}



		public Builder setCreated_at(String created_at) {
			this.created_at = created_at; return this;
		}



		public Builder setModified_at(String modified_at) {
			this.modified_at = modified_at; return this;
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



		public Builder setAiId(long val) {
			aiId = val;	return this;
		}



		public Builder setAiTableName(String val) {
			this.aiTableName = val;	return this;
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

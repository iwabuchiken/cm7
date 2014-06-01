package app.items;

public class Refresh {

	long	db_id;
	String	created_at;
	String	modified_at;

	String	last_refreshed;
	
	int num_ItemsAdded;
	
	public Refresh(Builder builder) {
		// TODO Auto-generated constructor stub
		this.db_id = builder.db_id;
		this.created_at = builder.created_at;
		this.modified_at = builder.modified_at;

		this.last_refreshed = builder.last_refreshed;
		
		this.num_ItemsAdded = builder.num_ItemsAdded;

	}

	
	////////////////////////////////

	// Methods

	////////////////////////////////
	public long getDb_id() {
		return db_id;
	}



	public String getCreated_at() {
		return created_at;
	}



	public String getModified_at() {
		return modified_at;
	}



	public String getLast_refreshed() {
		return last_refreshed;
	}



	public int getNum_ItemsAdded() {
		return num_ItemsAdded;
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



	public void setLast_refreshed(String last_refreshed) {
		this.last_refreshed = last_refreshed;
	}



	public void setNum_ItemsAdded(int num_ItemsAdded) {
		this.num_ItemsAdded = num_ItemsAdded;
	}


	////////////////////////////////

	// Builder

	////////////////////////////////
	public static class Builder {

		long	db_id;
		String	created_at;
		String	modified_at;

		String	last_refreshed;
		
		int num_ItemsAdded;
		
		public Refresh build() {
			
			return new Refresh(this);
			
		}

		////////////////////////////////

		// Methods

		////////////////////////////////
		public Builder setDb_id(long db_id) {
			this.db_id = db_id; return this;
		}

		public Builder setCreated_at(String created_at) {
			this.created_at = created_at; return this;
		}

		public Builder setModified_at(String modified_at) {
			this.modified_at = modified_at; return this;
		}

		public Builder setLast_refreshed(String last_refreshed) {
			this.last_refreshed = last_refreshed; return this;
		}

		public Builder setNum_ItemsAdded(int num_ItemsAdded) {
			this.num_ItemsAdded = num_ItemsAdded; return this;
		}

	}//public static class Builder
	
}//public class Refresh

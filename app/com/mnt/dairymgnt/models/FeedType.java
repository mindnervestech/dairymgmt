package com.mnt.dairymgnt.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
@Entity
public class FeedType extends Model
{
	@Id
	public Long feedTypeId;
	public String feedType;
	public Long getFeedTypeId() {
		return feedTypeId;
	}
	public void setFeedTypeId(Long feedTypeId) {
		this.feedTypeId = feedTypeId;
	}
	public String getFeedType() {
		return feedType;
	}
	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}
	 public static Finder <Long,FeedType> find=  new Finder<Long,FeedType>(long.class,FeedType.class);
	 
	 public static List <FeedType> getAllFeedType()
	 {
		return find.all(); 
	 }
	
}

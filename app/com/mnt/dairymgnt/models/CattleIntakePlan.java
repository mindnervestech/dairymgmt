package com.mnt.dairymgnt.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class CattleIntakePlan extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	public Long feedPlanId;
	public String feedPlanName;
	public String feedPlanStartDate;
	public String feedPlanEndDate;
	public float quantity;
	public int feedMasterId;
	
	public int getFeedMasterId() {
		return feedMasterId;
	}
	public void setFeedMasterId(int feedMasterId) {
		this.feedMasterId = feedMasterId;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public Long getFeedPlanId() {
		return feedPlanId;
	}
	public void setFeedPlanId(Long feedPlanId) {
		this.feedPlanId = feedPlanId;
	}
	public String getFeedPlanName() {
		return feedPlanName;
	}
	public void setFeedPlanName(String feedPlanName) {
		this.feedPlanName = feedPlanName;
	}
	public String getFeedPlanStartDate() {
		return feedPlanStartDate;
	}
	public void setFeedPlanStartDate(String feedPlanStartDate) {
		this.feedPlanStartDate = feedPlanStartDate;
	}
	public String getFeedPlanEndDate() {
		return feedPlanEndDate;
	}
	public void setFeedPlanEndDate(String feedPlanEndDate) {
		this.feedPlanEndDate = feedPlanEndDate;
	}
	
	public static Finder<Long, CattleIntakePlan> find = new Finder<Long, CattleIntakePlan>(
			Long.class, CattleIntakePlan.class);
	
	public static List <CattleIntakePlan> getCatleFeedsById(long id){
		return find.where().eq("feedMasterId",id).findList();
	
	}
	
	
}

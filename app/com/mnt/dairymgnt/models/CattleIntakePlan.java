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
	public String quantity;
	public String feedName;
	public String feedType;
	public String skuId;
	public String actualQuantity;
	public String expectedQuantity;
    public int id;
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getActualQuantity() {
		return actualQuantity;
	}
	public void setActualQuantity(String actualQuantity) {
		this.actualQuantity = actualQuantity;
	}
	public String getExpectedQuantity() {
		return expectedQuantity;
	}
	public void setExpectedQuantity(String expectedQuantity) {
		this.expectedQuantity = expectedQuantity;
	}
	public String getFeedName() {
		return feedName;
	}
	public void setFeedName(String feedName) {
		this.feedName = feedName;
	}
	public String getFeedType() {
		return feedType;
	}
	public void setFeedType(String feedType) {
		feedType = feedType;
	}

	public int feedMasterId;
	
	public int getFeedMasterId() {
		return feedMasterId;
	}
	public void setFeedMasterId(int feedMasterId) {
		this.feedMasterId = feedMasterId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
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

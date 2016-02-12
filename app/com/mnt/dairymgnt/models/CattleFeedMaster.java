package com.mnt.dairymgnt.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.annotation.JsonIgnore;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;


@Entity
public class CattleFeedMaster extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int feedId;
	public Date lastUpdateDateTime;
	public Date feedPlanStartDate;
	public Date feedPlanEndDate;
	public String feedPlanName;
	public String SubBreed;
	public String 	Stage;
	public String  feedName;
	public String  feedType;
	public String  SKUId;
	public int  quantity;
	public String  MealType;
	public CattleFeedMaster() {

	}
	
	
	
	public Date getFeedPlanStartDate() {
		return feedPlanStartDate;
	}


	public void setFeedPlanStartDate(Date feedPlanStartDate) {
		this.feedPlanStartDate = feedPlanStartDate;
	}


	public Date getFeedPlanEndDate() {
		return feedPlanEndDate;
	}


	public void setFeedPlanEndDate(Date feedPlanEndDate) {
		this.feedPlanEndDate = feedPlanEndDate;
	}


	public String getFeedPlanName() {
		return feedPlanName;
	}


	public void setFeedPlanName(String feedPlanName) {
		this.feedPlanName = feedPlanName;
	}


	public int getFeedId() {
		return feedId;
	}

	public void setFeedId(int feedId) {
		this.feedId = feedId;
	}

	public Date getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}

	public void setLastUpdateDateTime(Date lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}

	public String Breed;
	public String getBreed() {
		return Breed;
	}


	public void setBreed(String breed) {
		Breed = breed;
	}


	public String getSubBreed() {
		return SubBreed;
	}


	public void setSubBreed(String subBreed) {
		SubBreed = subBreed;
	}


	public String getStage() {
		return Stage;
	}


	public void setStage(String stage) {
		Stage = stage;
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
		this.feedType = feedType;
	}


	public String getSKUId() {
		return SKUId;
	}


	public void setSKUId(String sKUId) {
		SKUId = sKUId;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	

	public String getMealType() {
		return MealType;
	}


	public void setMealType(String mealType) {
		MealType = mealType;
	}


	

	

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	public Oraganization oraganization;
	
	
	
	public Oraganization getOraganization() {
		return oraganization;
	}

	public void setOraganization(Oraganization oraganization) {
		this.oraganization = oraganization;
	}

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	public Users users;
	
	
	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	public CattleFeeds cattleFeeds;
	
	
	public CattleFeeds getCattleFeeds() {
		return cattleFeeds;
	}


	public void setCattleFeeds(CattleFeeds cattleFeeds) {
		this.cattleFeeds = cattleFeeds;
	}

	public static Finder<Long, CattleFeedMaster> find = new Finder<Long, CattleFeedMaster>(
			Long.class, CattleFeedMaster.class);
	
	
	public static void update(CattleFeedMaster ud) {
		Ebean.update(ud);
	}
	
	public static CattleFeedMaster getUserByfeedId(int feedId){
		return find.where().eq("feedId", feedId).findUnique();
		
	}

	@JsonIgnore
	public static List<CattleFeedMaster> getAllOnlyFeedCattleMaster(){
		return find.all();
	}

	@JsonIgnore
	public static List<CattleFeedMaster> getAllFeedCattleMaster(int pageNumber,int rowperpage){
		return find.setFirstRow(pageNumber * 10).setMaxRows(rowperpage)
				.findList();
	}
	
	@JsonIgnore
	public static int getAllFeedCattleMasterCount(int pageNumber) {
		// TODO Auto-generated method stub
		return find.setFirstRow(pageNumber * 10)
				.setMaxRows(CattleFeedMaster.find.findRowCount()).findList().size();
	}
	
}

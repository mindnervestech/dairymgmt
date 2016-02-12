package com.mnt.dairymgnt.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
@Entity
public class CattleFeeds extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	public long id;
	public long feedId;
	public String skuId;
	public String feedProtine;
	public String quantityofProtine;
	public String feedWaterContent;
	public String feedFiber;
	public String feedName;
	public String feedType;
	public String quantity;
	public String getFeedType() {
		return feedType;
	}

	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}
	
	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getFeedName() {
		return feedName;
	}

	public void setFeedName(String feedName) {
		this.feedName = feedName;
	}

	public CattleFeeds() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getFeedId() {
		return feedId;
	}

	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getFeedProtine() {
		return feedProtine;
	}

	public void setFeedProtine(String feedProtine) {
		this.feedProtine = feedProtine;
	}

	public String getQuantityofProtine() {
		return quantityofProtine;
	}

	public void setQuantityofProtine(String quantityofProtine) {
		this.quantityofProtine = quantityofProtine;
	}

	public String getFeedWaterContent() {
		return feedWaterContent;
	}

	public void setFeedWaterContent(String feedWaterContent) {
		this.feedWaterContent = feedWaterContent;
	}

	public String getFeedFiber() {
		return feedFiber;
	}

	public void setFeedFiber(String feedFiber) {
		this.feedFiber = feedFiber;
	}
	
	
	public static Finder<Long, CattleFeeds> find = new Finder<Long, CattleFeeds>(
			Long.class, CattleFeeds.class);
	
	public static List<CattleFeeds> getAllCattleFeeds(){
		return find.all();
	}
	
	public static List<CattleFeeds> getCatleFeedsById(long feedId){
		return find.where().eq("feedId", feedId).findList();
	}
	
	public static CattleFeeds getCattleFeedsByName(String breeeName){
		return find.where().eq("breedName", breeeName).findUnique();
		 
	}
	@JsonIgnore
	@ManyToOne
	public CattleFeedMaster cattleFeedMaster;

	public CattleFeedMaster getCattleFeedMaster() {
		return cattleFeedMaster;
	}
	
	public static List <CattleFeeds> findByFeedPlanId(long Id){
		return find.where().eq("feedId", Id).findList();
		
	}
	
	public void setCattleFeedMaster(CattleFeedMaster cattleFeedMaster) {
		this.cattleFeedMaster = cattleFeedMaster;
	}
	
	
	
}

package com.mnt.dairymgnt.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.annotation.JsonIgnore;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class FeedMaster extends Model 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long feedId;
	public  String feedName;
	public String skuId;
	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
	public String feedProtine;
	public String quantityofProtine;
	public String feedWaterContent;
	public String quantityofWater;
	public String feedFiber;
	public String quantityofFiber;
	public String feedVitamins;
	public String quantityofVitamins;
	public Date lastUpdateDateTime;
	public Float price;
	public String feedType;
	
	public String getFeedType() {
		return feedType;
	}

	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
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
	public static void update(FeedMaster ud) {
		Ebean.update(ud);
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

	public String getFeedName() {
		return feedName;
	}
	public void setFeedName(String feedName) {
		this.feedName = feedName;
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
	public String getQuantityofWater() {
		return quantityofWater;
	}
	public void setQuantityofWater(String quantityofWater) {
		this.quantityofWater = quantityofWater;
	}
	public String getFeedFiber() {
		return feedFiber;
	}
	public void setFeedFiber(String feedFiber) {
		this.feedFiber = feedFiber;
	}
	public String getQuantityofFiber() {
		return quantityofFiber;
	}
	public void setQuantityofFiber(String quantityofFiber) {
		this.quantityofFiber = quantityofFiber;
	}
	public String getFeedVitamins() {
		return feedVitamins;
	}
	public void setFeedVitamins(String feedVitamins) {
		this.feedVitamins = feedVitamins;
	}
	public String getQuantityofVitamins() {
		return quantityofVitamins;
	}
	public void setQuantityofVitamins(String quantityofVitamins) {
		this.quantityofVitamins = quantityofVitamins;
	}

	public static Finder<Long, FeedMaster> find = new Finder<Long, FeedMaster>(
			Long.class, FeedMaster.class);
	
	public static List<FeedMaster> getAllFeedMaster(int pageNumber, int rowperpage) {
		// TODO Auto-generated method stub
		return find.setFirstRow(pageNumber * 10).setMaxRows(rowperpage)
				.findList();
	}
    
	public static FeedMaster getUserByfeedId(long id){
			return find.where().eq("feedId", id).findUnique();
	}
    public static List<FeedMaster> getAllOnlyFeedMaster(){
    	return find.all();
    }	
}

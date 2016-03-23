package com.mnt.dairymgnt.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class CattleFeedInventory extends Model
{
	@Id
	public long cattleFeedInventoryId;
	



	public String feedName;
	public String feedType;
	public String stockBalance;
	public String stockInQuantity;
	public String remark;
	public String stockPerviousBalance;
	public String stockOutQuantity;
	public Date stockInDate;
	public Date stockOutDate;
	
	public Date lastUpdateDateTime;
	public Date getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}
	public void setLastUpdateDateTime(Date lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}
	
	public long getCattleFeedInventoryId() {
		return cattleFeedInventoryId;
	}
	public void setCattleFeedInventoryId(long cattleFeedInventoryId) {
		this.cattleFeedInventoryId = cattleFeedInventoryId;
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
	public String getStockBalance() {
		return stockBalance;
	}
	public void setStockBalance(String stockBalance) {
		this.stockBalance = stockBalance;
	}
	public String getStockInQuantity() {
		return stockInQuantity;
	}
	public void setStockInQuantity(String stockInQuantity) {
		this.stockInQuantity = stockInQuantity;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStockPerviousBalance() {
		return stockPerviousBalance;
	}
	public void setStockPerviousBalance(String stockPerviousBalance) {
		this.stockPerviousBalance = stockPerviousBalance;
	}
	public String getStockOutQuantity() {
		return stockOutQuantity;
	}
	public void setStockOutQuantity(String stockOutQuantity) {
		this.stockOutQuantity = stockOutQuantity;
	}
	public Date getStockInDate() {
		return stockInDate;
	}
	public void setStockInDate(Date stockInDate) {
		this.stockInDate = stockInDate;
	}
	public Date getStockOutDate() {
		return stockOutDate;
	}
	public void setStockOutDate(Date stockOutDate) {
		this.stockOutDate = stockOutDate;
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
	@OneToOne(cascade = CascadeType.ALL)
	public CattleMaster CattleMaster;
	
	public CattleMaster getCattleMaster() {
		return CattleMaster;
	}

	public void setCattleMaster(CattleMaster cattleMaster) {
		CattleMaster = cattleMaster;
	}
	
	public static Finder<Long, CattleFeedInventory> find = new  Finder< Long, CattleFeedInventory>(Long.class, CattleFeedInventory.class);
	
	public static List<CattleFeedInventory> getAllCattleFeedInventory(int pageNumber, int rowperpage) {
		// TODO Auto-generated method stub
		return find.setFirstRow(pageNumber * 10).setMaxRows(rowperpage)
				.findList();
	}
	@JsonIgnore
	public static int getAllCattleHealthMasterCount(int pageNumber) {
		// TODO Auto-generated method stub
		return find.setFirstRow(pageNumber * 10)
				.setMaxRows(CattleHealthMaster.find.findRowCount()).findList().size();
	}
	
	
	public static CattleFeedInventory getAllByCattleFeedInventoryId(long id){
		return find.where().eq("cattleFeedInventoryId", id).findUnique();
		
	}
	@JsonIgnore
	public static List<CattleFeedInventory> getAllOnlyCattleFeedInventory(){
		return find.all();
	}
	
}

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
public class CattleIntake  extends Model{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int cattleId;	
	
	public Date lastUpdateDateTime;
	public String date;
	public int quantity; 
	public String  deviceID;
	public String actualFeedType;
	public String actualFeedName;
	public String expectedFeedType;
	public String expectedFeedName;
	public String expectedFeedQuantity;
	
	//public Date  dob;
	
	public String getActualFeedType() {
		return actualFeedType;
	}

	public void setActualFeedType(String actualFeedType) {
		this.actualFeedType = actualFeedType;
	}

	public String getActualFeedName() {
		return actualFeedName;
	}

	public void setActualFeedName(String actualFeedName) {
		this.actualFeedName = actualFeedName;
	}

	public String getExpectedFeedType() {
		return expectedFeedType;
	}

	public void setExpectedFeedType(String expectedFeedType) {
		this.expectedFeedType = expectedFeedType;
	}

	public String getExpectedFeedName() {
		return expectedFeedName;
	}

	public void setExpectedFeedName(String expectedFeedName) {
		this.expectedFeedName = expectedFeedName;
	}

	public String getExpectedFeedQuantity() {
		return expectedFeedQuantity;
	}

	public void setExpectedFeedQuantity(String expectedFeedQuantity) {
		this.expectedFeedQuantity = expectedFeedQuantity;
	}

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	public PregnantCattle pregnantCattle;
	
	
	public PregnantCattle getPregnantCattle() {
		return pregnantCattle;
	}

	public void setPregnantCattle(PregnantCattle pregnantCattle) {
		this.pregnantCattle = pregnantCattle;
	}
	
	public int getCattleId() {
		return cattleId;
	}

	public void setCattleId(int cattleId) {
		this.cattleId = cattleId;
	}

	public Date getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}

	public void setLastUpdateDateTime(Date lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
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
	
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	public CattleFeedMaster CattleFeedMaster;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	public CattleMaster CattleMaster;
	
	public CattleMaster getCattleMaster() {
		return CattleMaster;
	}

	public void setCattleMaster(CattleMaster cattleMaster) {
		CattleMaster = cattleMaster;
	}

	public CattleFeedMaster getCattleFeedMaster() {
		return CattleFeedMaster;
	}

	public void setCattleFeedMaster(CattleFeedMaster cattleFeedMaster) {
		CattleFeedMaster = cattleFeedMaster;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	
	public static Finder<Long, CattleIntake> find = new Finder<Long, CattleIntake>(
			Long.class, CattleIntake.class);
	
	public static List<CattleIntake> getAllCattleIntake(){
		return find.all();
	}

	public static CattleIntake getUserByCattleId(int cattleId){
		return find.where().eq("cattleId", cattleId).findUnique();
		
	}

	public static void update(CattleIntake ud) {
		Ebean.update(ud);
	}
	
	
}

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
	public Date date;
	public int quantity; 
	public String  deviceID;
	
	
	
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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

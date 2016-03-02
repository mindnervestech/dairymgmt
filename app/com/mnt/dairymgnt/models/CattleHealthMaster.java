package com.mnt.dairymgnt.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.annotation.JsonIgnore;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
@Entity
public class CattleHealthMaster extends Model
{
	@Id

	public long healthPlanId;
	public String medicationName;
	public String medicationType;
	public Date lastUpdateDateTime;
	public Date medicationStartDate;
	public Date medicationEndDate;
	public Date medicationNextDate;
	public String frequency;
	public String duration;
	
	public long getHealthPlanId() {
		return healthPlanId;
	}
	public void setHealthPlanId(long healthPlanId) {
		this.healthPlanId = healthPlanId;
	}
	public String getMedicationName() {
		return medicationName;
	}
	public void setMedicationName(String medicationName) {
		this.medicationName = medicationName;
	}
	public String getMedicationType() {
		return medicationType;
	}
	public void setMedicationType(String medicationType) {
		this.medicationType = medicationType;
	}
	public Date getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}
	public void setLastUpdateDateTime(Date lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}
	public Date getMedicationStartDate() {
		return medicationStartDate;
	}
	public void setMedicationStartDate(Date medicationStartDate) {
		this.medicationStartDate = medicationStartDate;
	}
	public Date getMedicationEndDate() {
		return medicationEndDate;
	}
	public void setMedicationEndDate(Date medicationEndDate) {
		this.medicationEndDate = medicationEndDate;
	}
	public Date getMedicationNextDate() {
		return medicationNextDate;
	}
	public void setMedicationNextDate(Date medicationNextDate) {
		this.medicationNextDate = medicationNextDate;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
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
	public static Finder<Long, CattleHealthMaster> find = new Finder<Long, CattleHealthMaster>(
			Long.class, CattleHealthMaster.class);
	
	
	
	
	public static CattleHealthMaster getUserByhealthPlanId(long healthPlanId){
		return find.where().eq("healthPlanId", healthPlanId).findUnique();
		
	}
	public static List<CattleHealthMaster> getAllCattleHealthMaster(int pageNumber, int rowperpage) {
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
	
	@JsonIgnore
	public static List<CattleHealthMaster> getAllOnlyCattleHealthMaster(){
		return find.all();
	}
	
}

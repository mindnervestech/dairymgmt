package com.mnt.dairymgnt.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.annotation.JsonIgnore;

import play.db.ebean.Model;

@Entity
public class PregnantCattle extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int pregnancyId;
	
	public Date lastDeliveryDate;
	
	public Date firstInseminationDate;
	public Date secondInseminationDate;
	public Date thirdInseminationDate;
	public Date forthInseminationDate;
	public Date plannedFirstInseminationDate;
	public Date plannedSecondInseminationDate;
	public Date plannedThirdInseminationDate;
	public Date plannedForthInseminationDate;
	public Date pregnancyDate;
	public Date dueDate;
	public Date successDate;
	


	
	public int cattleId;
	
	public int getCattleId() {
		return cattleId;
	}
	
	public void setCattleId(int cattleId) {
		this.cattleId = cattleId;
	}

	public int getPregnancyId() {
		return pregnancyId;
	}

	public void setPregnancyId(int pregnancyId) {
		this.pregnancyId = pregnancyId;
	}

	public Date getLastDeliveryDate() {
		return lastDeliveryDate;
	}

	public void setLastDeliveryDate(Date lastDeliveryDate) {
		this.lastDeliveryDate = lastDeliveryDate;
	}

	

	

	public Date getFirstInseminationDate() {
		return firstInseminationDate;
	}

	public void setFirstInseminationDate(Date firstInseminationDate) {
		this.firstInseminationDate = firstInseminationDate;
	}

	public Date getSecondInseminationDate() {
		return secondInseminationDate;
	}

	public void setSecondInseminationDate(Date secondInseminationDate) {
		this.secondInseminationDate = secondInseminationDate;
	}

	public Date getThirdInseminationDate() {
		return thirdInseminationDate;
	}

	public void setThirdInseminationDate(Date thirdInseminationDate) {
		this.thirdInseminationDate = thirdInseminationDate;
	}

	public Date getForthInseminationDate() {
		return forthInseminationDate;
	}

	public void setForthInseminationDate(Date forthInseminationDate) {
		this.forthInseminationDate = forthInseminationDate;
	}

	public Date getPlannedFirstInseminationDate() {
		return plannedFirstInseminationDate;
	}

	public void setPlannedFirstInseminationDate(Date plannedFirstInseminationDate) {
		this.plannedFirstInseminationDate = plannedFirstInseminationDate;
	}

	public Date getPlannedSecondInseminationDate() {
		return plannedSecondInseminationDate;
	}

	public void setPlannedSecondInseminationDate(Date plannedSecondInseminationDate) {
		this.plannedSecondInseminationDate = plannedSecondInseminationDate;
	}

	public Date getPlannedThirdInseminationDate() {
		return plannedThirdInseminationDate;
	}

	public void setPlannedThirdInseminationDate(Date plannedThirdInseminationDate) {
		this.plannedThirdInseminationDate = plannedThirdInseminationDate;
	}

	public Date getPlannedForthInseminationDate() {
		return plannedForthInseminationDate;
	}

	public void setPlannedForthInseminationDate(Date plannedForthInseminationDate) {
		this.plannedForthInseminationDate = plannedForthInseminationDate;
	}

	public Date getPregnancyDate() {
		return pregnancyDate;
	}

	public void setPregnancyDate(Date pregnancyDate) {
		this.pregnancyDate = pregnancyDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getSuccessDate() {
		return successDate;
	}

	public void setSuccessDate(Date successDate) {
		this.successDate = successDate;
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

	public PregnantCattle() {

	}
	
	
	public static Finder<Long, PregnantCattle> find = new Finder<Long, PregnantCattle>(
			Long.class, PregnantCattle.class);
	
	public static List<PregnantCattle> getAllPregnantCattle(int cattleId ,int pageNumber ,int rowperpage){
		if(cattleId == 0){
			//return find.all();
			return find.setFirstRow(pageNumber * 10).setMaxRows(rowperpage)
					.findList();
			
		}else{
			//return find.where().eq("cattleId", cattleId).findList();
			return find.where().eq("cattleId", cattleId).setFirstRow(pageNumber * 10).setMaxRows(rowperpage)
					.findList();
		}
		
	}
	
	@JsonIgnore
	public static int getAllPregnantCattleCount(int pageNumber) {
		// TODO Auto-generated method stub
		return find.setFirstRow(pageNumber * 10)
				.setMaxRows(PregnantCattle.find.findRowCount()).findList().size();
	}

	public static PregnantCattle getPregnantCattleByCattleId(int pregnancyId){
		return find.where().eq("pregnancyId", pregnancyId).findUnique();
	}
	

	public static void update(PregnantCattle ud) {
		Ebean.update(ud);
	}
	
	public static PregnantCattle getRecoredByPermissionsName(String permissionName){
		return find.where().eq("permissionName",permissionName.trim()).findUnique();
	}
	
	@JsonIgnore
	public static List<PregnantCattle> getAllPregnantCattleById(int cattleId) {
		// TODO Auto-generated method stub
		if(cattleId == 0){
			return find.all();
		}else{
			return find.where().eq("cattleId", cattleId).findList();	
		}
		
	}
	@JsonIgnore
	public static List<PregnantCattle> getAllOnlyPregnantCattle(int cattleId) {
		// TODO Auto-generated method stub
		if(cattleId == 0){
			return find.all();
		}else{
			return find.where().eq("cattleId", cattleId).findList();	
		}
		
	}
	
	
	
	
}

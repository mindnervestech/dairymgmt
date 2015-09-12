package com.mnt.dairymgnt.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	
	public String lastDeliveryDate;
	public  String expectedPregnancyDate;
	public String firstInseminationDate;
	public String secondInseminationDate;
	public String thirdInseminationDate;
	public String actualPregnancyDate;
	public String expectedDeliveryDate;
	public String milkingStoppingDate;
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

	public String getLastDeliveryDate() {
		return lastDeliveryDate;
	}

	public void setLastDeliveryDate(String lastDeliveryDate) {
		this.lastDeliveryDate = lastDeliveryDate;
	}

	public String getExpectedPregnancyDate() {
		return expectedPregnancyDate;
	}

	public void setExpectedPregnancyDate(String expectedPregnancyDate) {
		this.expectedPregnancyDate = expectedPregnancyDate;
	}

	public String getFirstInseminationDate() {
		return firstInseminationDate;
	}

	public void setFirstInseminationDate(String firstInseminationDate) {
		this.firstInseminationDate = firstInseminationDate;
	}

	public String getSecondInseminationDate() {
		return secondInseminationDate;
	}

	public void setSecondInseminationDate(String secondInseminationDate) {
		this.secondInseminationDate = secondInseminationDate;
	}

	public String getThirdInseminationDate() {
		return thirdInseminationDate;
	}

	public void setThirdInseminationDate(String thirdInseminationDate) {
		this.thirdInseminationDate = thirdInseminationDate;
	}

	public String getActualPregnancyDate() {
		return actualPregnancyDate;
	}

	public void setActualPregnancyDate(String actualPregnancyDate) {
		this.actualPregnancyDate = actualPregnancyDate;
	}

	public String getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}

	public void setExpectedDeliveryDate(String expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public String getMilkingStoppingDate() {
		return milkingStoppingDate;
	}

	public void setMilkingStoppingDate(String milkingStoppingDate) {
		this.milkingStoppingDate = milkingStoppingDate;
	}


	

	public PregnantCattle() {

	}
	
	
	public static Finder<Long, PregnantCattle> find = new Finder<Long, PregnantCattle>(
			Long.class, PregnantCattle.class);
	
	public static List<PregnantCattle> getAllPregnantCattle(int cattleId){
		if(cattleId != 0){
			return find.where().eq("cattleId", cattleId).findList();
		}else{
			return find.all();	
		}
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

	public static List<PregnantCattle> getAllPregnantCattleById(int cattleId) {
		// TODO Auto-generated method stub
		if(cattleId == 0){
			return find.all();
		}else{
			return find.where().eq("cattleId", cattleId).findList();	
		}
		
	}
	
}

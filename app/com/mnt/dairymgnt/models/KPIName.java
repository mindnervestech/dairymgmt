package com.mnt.dairymgnt.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class KPIName  extends Model{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	public int noOfClavesPerYear;
	public int MonthsOfMilkingPerCattleBreedwiseafterDelivery;
	public int TotalNoOfCalvesPerCattle;
	public int DaysOfPregnancy;
	public int CycleDaysBetweenInsemination;
	public int DaysbyWhenCalvsCanbeInseminated;
	public int DaysbyWhenCalvscanProduceMilk;
	public int DaysofStopMilkingBreedWisePreDelivery;
	public int ReadyforInseminationDaysAfterDelivery;
	public int KPIMaterId;
	
	public int getKPIMaterId() {
		return KPIMaterId;
	}
	public void setKPIMaterId(int kPIMaterId) {
		KPIMaterId = kPIMaterId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNoOfClavesPerYear() {
		return noOfClavesPerYear;
	}
	public void setNoOfClavesPerYear(int noOfClavesPerYear) {
		this.noOfClavesPerYear = noOfClavesPerYear;
	}
	public int getMonthsOfMilkingPerCattleBreedwiseafterDelivery() {
		return MonthsOfMilkingPerCattleBreedwiseafterDelivery;
	}
	public void setMonthsOfMilkingPerCattleBreedwiseafterDelivery(
			int monthsOfMilkingPerCattleBreedwiseafterDelivery) {
		MonthsOfMilkingPerCattleBreedwiseafterDelivery = monthsOfMilkingPerCattleBreedwiseafterDelivery;
	}
	public int getTotalNoOfCalvesPerCattle() {
		return TotalNoOfCalvesPerCattle;
	}
	public void setTotalNoOfCalvesPerCattle(int totalNoOfCalvesPerCattle) {
		TotalNoOfCalvesPerCattle = totalNoOfCalvesPerCattle;
	}
	public int getDaysOfPregnancy() {
		return DaysOfPregnancy;
	}
	public void setDaysOfPregnancy(int daysOfPregnancy) {
		DaysOfPregnancy = daysOfPregnancy;
	}
	public int getCycleDaysBetweenInsemination() {
		return CycleDaysBetweenInsemination;
	}
	public void setCycleDaysBetweenInsemination(int cycleDaysBetweenInsemination) {
		CycleDaysBetweenInsemination = cycleDaysBetweenInsemination;
	}
	public int getDaysbyWhenCalvsCanbeInseminated() {
		return DaysbyWhenCalvsCanbeInseminated;
	}
	public void setDaysbyWhenCalvsCanbeInseminated(
			int daysbyWhenCalvsCanbeInseminated) {
		DaysbyWhenCalvsCanbeInseminated = daysbyWhenCalvsCanbeInseminated;
	}
	public int getDaysbyWhenCalvscanProduceMilk() {
		return DaysbyWhenCalvscanProduceMilk;
	}
	public void setDaysbyWhenCalvscanProduceMilk(int daysbyWhenCalvscanProduceMilk) {
		DaysbyWhenCalvscanProduceMilk = daysbyWhenCalvscanProduceMilk;
	}
	public int getDaysofStopMilkingBreedWisePreDelivery() {
		return DaysofStopMilkingBreedWisePreDelivery;
	}
	public void setDaysofStopMilkingBreedWisePreDelivery(
			int daysofStopMilkingBreedWisePreDelivery) {
		DaysofStopMilkingBreedWisePreDelivery = daysofStopMilkingBreedWisePreDelivery;
	}
	public int getReadyforInseminationDaysAfterDelivery() {
		return ReadyforInseminationDaysAfterDelivery;
	}
	public void setReadyforInseminationDaysAfterDelivery(
			int readyforInseminationDaysAfterDelivery) {
		ReadyforInseminationDaysAfterDelivery = readyforInseminationDaysAfterDelivery;
	}
	
	
	public static Finder<Long, KPIName> find = new Finder<Long, KPIName>(
			Long.class, KPIName.class);
	
	public static List<KPIName> getAllKPIName(){
		return find.all();
	}
	
	

}

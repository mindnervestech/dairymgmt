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
public class VaccinationPlan extends Model
{
	
@Id

	public long vaccinationPlanId;
	public String vaccineName;
	public String vaccineType;
	public String breed;
	public String subBreed;
	public String doctorName;
	public String companyName;
	public String elapsedDays;



	public Date lastUpdateDateTime;
	public long getVaccinationPlanId() {
		return vaccinationPlanId;
	}
	public void setVaccinationPlanId(long vaccinationPlanId) {
		this.vaccinationPlanId = vaccinationPlanId;
	}
	public String getVaccineName() {
		return vaccineName;
	}
	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}
	public String getVaccineType() {
		return vaccineType;
	}
	public void setVaccineType(String vaccineType) {
		this.vaccineType = vaccineType;
	}
	public String getSubBreed() {
		return subBreed;
	}
	public void setSubBreed(String subBreed) {
		this.subBreed = subBreed;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getElapsedDays() {
		return elapsedDays;
	}
	public void setElapsedDays(String elapsedDays) {
		this.elapsedDays = elapsedDays;
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
	public static Finder<Long, VaccinationPlan> find = new Finder<Long, VaccinationPlan>(
			Long.class, VaccinationPlan.class);
	
	public static VaccinationPlan getUserByfeedId(long vaccinationId){
		return find.where().eq("vaccinationPlanId", vaccinationId).findUnique();
		
	}
	
	public static List<VaccinationPlan> getAllVaccinationPlan(int pageNumber, int rowperpage) {
		// TODO Auto-generated method stub
		return find.setFirstRow(pageNumber * 10).setMaxRows(rowperpage)
				.findList();
	}
	
	public String getBreed() {
		return breed;
	}
	public void setBreed(String breed) {
		this.breed = breed;
	}
	public static List<VaccinationPlan> getAllOnlyVaccinationPlan(){
    	return find.all();
    }

}

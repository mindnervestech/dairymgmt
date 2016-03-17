package com.mnt.dairymgnt.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CattleHealth  extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int cattleId;	
	public Date lastUpdateDateTime;
    public String medicationType;
    public String medicationName;
    public String medicationStartDate;
    public String medicationEndDate;
    public boolean  pregnant;
    public String pregnancyDate;
    public String   lastDelivaerydate;
	public String duedate;
	public String attrib1;
	public String attrib2;
	public String attrib3;
	public String attrib4;
	public String attrib5;
	
	public String vaccinationType;
	public String vaccinationName;
	public String vaccinationPlannedDate;
	public String vaccinationActualDate;
	public String breed;
	public String subBreed;
	public String healthType;
	
	
    
   

	public String getHealthType() {
		return healthType;
	}

	public void setHealthType(String healthType) {
		this.healthType = healthType;
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

	public String getMedicationType() {
		return medicationType;
	}

	public void setMedicationType(String medicationType) {
		this.medicationType = medicationType;
	}

	public String getMedicationName() {
		return medicationName;
	}

	public void setMedicationName(String medicationName) {
		this.medicationName = medicationName;
	}

	public String getMedicationStartDate() {
		return medicationStartDate;
	}

	public void setMedicationStartDate(String medicationStartDate) {
		this.medicationStartDate = medicationStartDate;
	}

	public String getMedicationEndDate() {
		return medicationEndDate;
	}

	public void setMedicationEndDate(String medicationEndDate) {
		this.medicationEndDate = medicationEndDate;
	}

	public boolean isPregnant() {
		return pregnant;
	}

	public void setPregnant(boolean pregnant) {
		this.pregnant = pregnant;
	}

	public String getPregnancyDate() {
		return pregnancyDate;
	}

	public void setPregnancyDate(String pregnancyDate) {
		this.pregnancyDate = pregnancyDate;
	}

	public String getLastDelivaerydate() {
		return lastDelivaerydate;
	}

	public void setLastDelivaerydate(String lastDelivaerydate) {
		this.lastDelivaerydate = lastDelivaerydate;
	}

	public String getDuedate() {
		return duedate;
	}

	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}

	public String getAttrib1() {
		return attrib1;
	}

	public void setAttrib1(String attrib1) {
		this.attrib1 = attrib1;
	}

	public String getAttrib2() {
		return attrib2;
	}

	public void setAttrib2(String attrib2) {
		this.attrib2 = attrib2;
	}

	public String getAttrib3() {
		return attrib3;
	}

	public void setAttrib3(String attrib3) {
		this.attrib3 = attrib3;
	}

	public String getAttrib4() {
		return attrib4;
	}

	public void setAttrib4(String attrib4) {
		this.attrib4 = attrib4;
	}

	public String getAttrib5() {
		return attrib5;
	}

	public void setAttrib5(String attrib5) {
		this.attrib5 = attrib5;
	}

    

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	public Oraganization oraganization;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	public Users users;
	
	
	public Oraganization getOraganization() {
		return oraganization;
	}

	public void setOraganization(Oraganization oraganization) {
		this.oraganization = oraganization;
	}


	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
	public String getVaccinationType() {
		return vaccinationType;
	}

	public void setVaccinationType(String vaccinationType) {
		this.vaccinationType = vaccinationType;
	}

	public String getVaccinationName() {
		return vaccinationName;
	}

	public void setVaccinationName(String vaccinationName) {
		this.vaccinationName = vaccinationName;
	}

	public String getVaccinationPlannedDate() {
		return vaccinationPlannedDate;
	}

	public void setVaccinationPlannedDate(String vaccinationPlannedDate) {
		this.vaccinationPlannedDate = vaccinationPlannedDate;
	}

	public String getVaccinationActualDate() {
		return vaccinationActualDate;
	}

	public void setVaccinationActualDate(String vaccinationActualDate) {
		this.vaccinationActualDate = vaccinationActualDate;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	



	public String getSubBreed() {
		return subBreed;
	}

	public void setSubBreed(String subBreed) {
		this.subBreed = subBreed;
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
	
	public static Finder<Long, CattleHealth> find = new Finder<Long, CattleHealth>(
			Long.class, CattleHealth.class);
	
	public static List<CattleHealth> getAllCattleOutput(){
		return find.all();
	}

	@JsonIgnore
	public static List<CattleHealth> getAllCattleOutput(int pageNumber,int rowperpage){
		return find.setFirstRow(pageNumber * 10).setMaxRows(rowperpage)
				.findList();
	}
	
	@JsonIgnore
	public static int getAllCattleHealthCount(int pageNumber) {
		// TODO Auto-generated method stub
		return find.setFirstRow(pageNumber * 10)
				.setMaxRows(CattleHealth.find.findRowCount()).findList().size();
	}
	
	public static CattleHealth getUserByCattleId(int cattleId){
		return find.where().eq("cattleId", cattleId).findUnique();
		
	}

	public static void update(CattleHealth ud) {
		Ebean.update(ud);
	}
	
}

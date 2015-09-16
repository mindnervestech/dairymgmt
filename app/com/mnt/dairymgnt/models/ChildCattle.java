package com.mnt.dairymgnt.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;



import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.annotation.JsonIgnore;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class ChildCattle extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int cattlechildId;
	//public int RFID;
	public Date lastUpdateDateTime;
	public  String name;
	public String breed;
	public String  dateofBirth;
	public String  gender;
	public String cattleIdentificationMark;
	public String attrib1;
	public String attrib2;
	public String attrib3;
	public String attrib4;
	public String attrib5;
	
	
	public ChildCattle() {

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
	@ManyToOne(cascade = CascadeType.ALL)
	public CattleMaster cattleMaster;
	
	
	public CattleMaster getCattleMaster() {
		return cattleMaster;
	}

	public void setCattleMaster(CattleMaster cattleMaster) {
		this.cattleMaster = cattleMaster;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	
	public int getCattlechildId() {
		return cattlechildId;
	}

	public void setCattlechildId(int cattlechildId) {
		this.cattlechildId = cattlechildId;
	}
	
	/*public int getRFID() {
		return RFID;
	}

	public void setRFID(int rFID) {
		RFID = rFID;
	}*/

	public Date getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}

	public void setLastUpdateDateTime(Date lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public String getDateofBirth() {
		return dateofBirth;
	}

	public void setDateofBirth(String dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCattleIdentificationMark() {
		return cattleIdentificationMark;
	}

	public void setCattleIdentificationMark(String cattleIdentificationMark) {
		this.cattleIdentificationMark = cattleIdentificationMark;
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

	public static Finder<Long, ChildCattle> find = new Finder<Long, ChildCattle>(
			Long.class, ChildCattle.class);
	
	public static List<ChildCattle> getAllCattleMaster(){
		return find.all();
	}

	public static ChildCattle getUserByCattleId(int cattleId){
		return find.where().eq("cattlechildId", cattleId).findUnique();
	}
	
	public static void update(ChildCattle ud) {
		Ebean.update(ud);
	}
	
	public static ChildCattle getRecoredByPermissionsName(String permissionName){
		return find.where().eq("permissionName",permissionName.trim()).findUnique();
	}
	
	
	@JsonIgnore
	public static int getAllPregnantCattleCount(int pageNumber) {
		// TODO Auto-generated method stub
		return find.setFirstRow(pageNumber * 10)
				.setMaxRows(PregnantCattle.find.findRowCount()).findList().size();
	}
	
	
}

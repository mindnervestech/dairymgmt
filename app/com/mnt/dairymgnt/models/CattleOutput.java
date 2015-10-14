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
public class CattleOutput extends Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int cattleId;	
	
   	public Date LastUpdateDateTime;
	public String date;
	
	public int quantity;
	public int fatContent;
	public int SNFContent;
	public String deviceID;
	public String attrib1;
	public String attrib2;
	public String attrib3;
	public String attrib4;
	public String attrib5;
	public String expectedMilkQuantity; 
	
	
	public String getExpectedMilkQuantity() {
		return expectedMilkQuantity;
	}

	public void setExpectedMilkQuantity(String expectedMilkQuantity) {
		this.expectedMilkQuantity = expectedMilkQuantity;
	}

	public Date getLastUpdateDateTime() {
		return LastUpdateDateTime;
	}

	public void setLastUpdateDateTime(Date lastUpdateDateTime) {
		LastUpdateDateTime = lastUpdateDateTime;
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

	public int getFatContent() {
		return fatContent;
	}

	public void setFatContent(int fatContent) {
		this.fatContent = fatContent;
	}

	public int getSNFContent() {
		return SNFContent;
	}

	public void setSNFContent(int sNFContent) {
		SNFContent = sNFContent;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
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
	
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	public CattleMaster CattleMaster;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	public PregnantCattle pregnantCattle;
	
	
	public PregnantCattle getPregnantCattle() {
		return pregnantCattle;
	}

	public void setPregnantCattle(PregnantCattle pregnantCattle) {
		this.pregnantCattle = pregnantCattle;
	}

	public CattleMaster getCattleMaster() {
		return CattleMaster;
	}

	public void setCattleMaster(CattleMaster cattleMaster) {
		CattleMaster = cattleMaster;
	}

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
	
	public static Finder<Long, CattleOutput> find = new Finder<Long, CattleOutput>(
			Long.class, CattleOutput.class);
	
	/*public static List<CattleOutput> getAllCattleOutput(){
		return find.all();
	}*/

	public static CattleOutput getUserByCattleId(int cattleId){
		return find.where().eq("cattleId", cattleId).findUnique();
	}


	@JsonIgnore
	public static List<CattleOutput> getAllCattleOutput(int pageNumber,int rowperpage){
		return find.setFirstRow(pageNumber * 10).setMaxRows(rowperpage)
				.findList();
	}
	
	@JsonIgnore
	public static int getAllCattleOutputCount(int pageNumber) {
		// TODO Auto-generated method stub
		return find.setFirstRow(pageNumber * 10)
				.setMaxRows(CattleOutput.find.findRowCount()).findList().size();
	}
	
	
	public static void update(CattleOutput ud) {
		Ebean.update(ud);
	}
	
	  
}


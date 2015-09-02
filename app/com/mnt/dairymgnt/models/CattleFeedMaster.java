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
public class CattleFeedMaster extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int feedId;
	public Date lastUpdateDateTime;
	public String feedType;
	public String	feedprotine;
	public String feedWaterContent;
	public String feedFiber;
	public String feedVitamins;
	public String attrib1;
	public String attrib2;
	public String attrib3;
	public String attrib4;
	public String attrib5;
	
	public CattleFeedMaster() {

	}
	
	
	public int getFeedId() {
		return feedId;
	}

	public void setFeedId(int feedId) {
		this.feedId = feedId;
	}

	public Date getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}

	public void setLastUpdateDateTime(Date lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}

	public String getFeedType() {
		return feedType;
	}

	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}

	public String getFeedprotine() {
		return feedprotine;
	}

	public void setFeedprotine(String feedprotine) {
		this.feedprotine = feedprotine;
	}

	public String getFeedWaterContent() {
		return feedWaterContent;
	}

	public void setFeedWaterContent(String feedWaterContent) {
		this.feedWaterContent = feedWaterContent;
	}

	public String getFeedFiber() {
		return feedFiber;
	}

	public void setFeedFiber(String feedFiber) {
		this.feedFiber = feedFiber;
	}

	public String getFeedVitamins() {
		return feedVitamins;
	}

	public void setFeedVitamins(String feedVitamins) {
		this.feedVitamins = feedVitamins;
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

	
	public static Finder<Long, CattleFeedMaster> find = new Finder<Long, CattleFeedMaster>(
			Long.class, CattleFeedMaster.class);
	
	public static List<CattleFeedMaster> getAllFeedCattleMaster(){
		return find.all();
	}

	public static CattleFeedMaster getUserByfeedId(int feedId){
		return find.where().eq("feedId", feedId).findUnique();
		
	}

	public static void update(CattleFeedMaster ud) {
		Ebean.update(ud);
	}
	
	/*public static CattleFeedMaster getRecoredByPermissionsName(String permissionName){
		return find.where().eq("permissionName",permissionName.trim()).findUnique();
	}*/
	
}

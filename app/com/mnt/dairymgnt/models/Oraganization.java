package com.mnt.dairymgnt.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mnt.dairymgnt.VM.OrgnizationVM;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Oraganization extends Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3361368567111797581L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public int orgId;
	public String name;
	public String addressLine1;
	public String addressLine2;
	public Date lastUpdatedtime;
	public String city;
	public String state;
	public int pincode;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public Date getLastUpdatedtime() {
		return lastUpdatedtime;
	}
	public void setLastUpdatedtime(Date lastUpdatedtime) {
		this.lastUpdatedtime = lastUpdatedtime;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getPincode() {
		return pincode;
	}
	public void setPincode(int pincode) {
		this.pincode = pincode;
	}
	
	@JsonIgnore
	@OneToMany
	public Users users;
	
	public static Finder<Long, Oraganization> find = new Finder<Long, Oraganization>(
			Long.class, Oraganization.class);
	
	public static List<Oraganization> getAllOrgs(){
		return find.all();
	}
	public static Oraganization getOrgById(int orgId2) {
		// TODO Auto-generated method stub
		
		return find.where().eq("orgId", orgId2).findUnique();
	}
	
}

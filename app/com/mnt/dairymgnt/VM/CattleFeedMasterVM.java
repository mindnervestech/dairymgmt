package com.mnt.dairymgnt.VM;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mnt.dairymgnt.models.Oraganization;
import com.mnt.dairymgnt.models.Users;

public class CattleFeedMasterVM {

	
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
	
	public Oraganization oraganization;
	public Users users;
	
	

}

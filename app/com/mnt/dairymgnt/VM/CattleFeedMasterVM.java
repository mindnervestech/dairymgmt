package com.mnt.dairymgnt.VM;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mnt.dairymgnt.models.CattleFeeds;
import com.mnt.dairymgnt.models.Oraganization;
import com.mnt.dairymgnt.models.Users;

public class CattleFeedMasterVM {

	
	public int feedId;
	public Date lastUpdateDateTime;
	
	public String 	Stage;
	public String  feedName;
	public String  feedType;
	public String  SKUId;
	public int  quantity;
	public String feedName1;
	public String  MealType;
	public String  feedType1;
	public String  SKUId1;
	public int  quantity1;
	public String  feedType2;
	public String  SKUId2;
	public int  quantity2;
	public String feedName2;
	public String Breed;
	public String SubBreed;
	public String feedPlanStartDateVM;
	public String feedPlanEndDateVM;
	public String feedPlanName;
	
	
	

	public Oraganization oraganization;
	public Users users;
	public List<CattleFeeds> cattleFeeds;
	
	

}

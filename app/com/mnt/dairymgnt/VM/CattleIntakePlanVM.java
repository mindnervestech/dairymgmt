package com.mnt.dairymgnt.VM;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mnt.dairymgnt.models.Oraganization;
import com.mnt.dairymgnt.models.Users;

public class CattleIntakePlanVM {
	/*public String Id;
	public String feedProtine;
	public String quantityofProtine;
	public String feedWaterContent;
	public String feedFiber;
	public Long feedPlanId;
	public String feedPlanName;
	public String feedPlanStartDate;
	public String feedPlanEndDate;
	public float quantity;
	public String feedName;
	public String feedType;
	public String skuId;
	public String actualQuantity;
	public String expectedQuantity;
    public int id;
*/
	
	public long id;
	public long feedId;
	public String skuId;
	public String feedProtine;
	public String quantityofProtine;
	public String feedWaterContent;
	public String feedFiber;
	public String feedName;
	public String feedType;
	public List<CattleFeedsVM> cattleFeeds;
	public String quantity;
	
	// public int quantity;
}


package com.mnt.dairymgnt.VM;

import java.util.Date;

import com.mnt.dairymgnt.models.Oraganization;
import com.mnt.dairymgnt.models.Users;

public class FeedMasterVM {

	public long feedId;
	public  String feedName;
	public String SSS;
	public String feedProtine;
	public String quantityofProtine;
	public String feedWaterContent;
	public String quantityofWater;
	public String feedFiber;
	public String quantityofFiber;
	public String feedVitamins;
	public String quantityofVitamins;
	public Date lastUpdateDateTime;
	public Oraganization oraganization;
	public Users users;
	public String skuId;
	public Float price;
}

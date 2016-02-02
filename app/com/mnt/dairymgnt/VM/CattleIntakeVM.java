package com.mnt.dairymgnt.VM;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mnt.dairymgnt.models.CattleFeedMaster;
import com.mnt.dairymgnt.models.CattleMaster;
import com.mnt.dairymgnt.models.Oraganization;
import com.mnt.dairymgnt.models.PregnantCattle;
import com.mnt.dairymgnt.models.Users;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CattleIntakeVM {

	public int cattleId;	
	public Date lastUpdateDateTime;
	public String date;
	public int quantity; 
	public String  deviceID;
	public Oraganization oraganization;
	public Users users;
	public CattleFeedMaster cattleFeedMaster;
	public PregnantCattle pregnantCattle;
	public CattleMaster cattleMaster;
//	public String actualFeedType;
//	public String actualFeedName;
//	public String expectedFeedType;
//	public String expectedFeedName;
//	public String expectedFeedQuantity;
}

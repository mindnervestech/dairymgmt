package com.mnt.dairymgnt.VM;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mnt.dairymgnt.models.CattleFeedMaster;
import com.mnt.dairymgnt.models.CattleIntake;
import com.mnt.dairymgnt.models.CattleIntakePlan;
import com.mnt.dairymgnt.models.CattleMaster;
import com.mnt.dairymgnt.models.Oraganization;
import com.mnt.dairymgnt.models.PregnantCattle;
import com.mnt.dairymgnt.models.Users;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CattleIntakeVM {

	public static final String System = null;
	public int cattleId;	
	public Date lastUpdateDateTime;
	public String dateOfBirth;
	public int quantity; 
	public String  deviceID;
	public Oraganization oraganization;
	public Users users;
	public CattleFeedMaster cattleFeedMaster;
//	public PregnantCattle pregnantCattle;
	public CattleMaster cattleMaster;
	public Long FeedPlanId;
	public Long feedId;
	public List<CattleIntakePlan> cattleIntakeVM;
//	public String actualFeedType;
//	public String actualFeedName;
//	public String expectedFeedType;
//	public String expectedFeedName;
//	public String expectedFeedQuantity;
}

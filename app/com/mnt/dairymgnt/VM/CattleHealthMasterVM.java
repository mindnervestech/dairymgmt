package com.mnt.dairymgnt.VM;

import java.util.Date;

import com.mnt.dairymgnt.models.Oraganization;
import com.mnt.dairymgnt.models.Users;

public class CattleHealthMasterVM 
{
	public long healthPlanId;
	public String medicationName;
	public String medicationType;
	public Date lastUpdateDateTime;
	public String medicationStartDateVM;
	public String medicationEndDateVM;
	public String medicationNextDateVM;
	public String frequency;
	public String frequencyType;
	public String duration;
	public Oraganization oraganization;
	public Users users;
}

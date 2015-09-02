package com.mnt.dairymgnt.VM;

import java.util.Date;
import com.mnt.dairymgnt.models.CattleFeedMaster;
import com.mnt.dairymgnt.models.Oraganization;
import com.mnt.dairymgnt.models.Users;

public class CattleIntakeVM {

	public int cattleId;	
	public Date lastUpdateDateTime;
	public Date date;
	public int quantity; 
	public String  deviceID;
	public Oraganization oraganization;
	public Users users;
	public CattleFeedMaster cattleFeedMaster;
	
	
	
}

package com.mnt.dairymgnt.VM;

import java.util.Date;

import com.mnt.dairymgnt.models.CattleMaster;
import com.mnt.dairymgnt.models.Oraganization;
import com.mnt.dairymgnt.models.Users;

public class CattleFeedInventoryVM 
{
	public long cattleFeedInventoryId;
	public String feedName;
	public String feedType;
	public int stockBalance;
	public String stockInQuantity;
	public String remark;
	public int stockPerviousBalance;
	public String stockOutQuantity;
	public String stockInDate;
	public String stockOutDate;
	public CattleMaster cattleMaster;
	public Oraganization oraganization;
	public Users users;

}

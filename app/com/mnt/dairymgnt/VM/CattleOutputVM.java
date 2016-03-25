package com.mnt.dairymgnt.VM;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mnt.dairymgnt.models.CattleMaster;
import com.mnt.dairymgnt.models.Oraganization;
import com.mnt.dairymgnt.models.PregnantCattle;
import com.mnt.dairymgnt.models.Users;

public class CattleOutputVM {

	public int cattleId;	
   	public Date lastUpdateDateTime;
	public String date;
	public String time;
	public int quantity;
	public int fatContent;
	public int SNFContent;
	public String deviceID;
	public String attrib1;
	public String attrib2;
	public String attrib3;
	public String attrib4;
	public String attrib5;
	public String expectedMilkQuantity; 
	public Oraganization oraganization;
	public Users users;
	public CattleMaster cattleMaster;
	/*public PregnantCattle pregnantCattle;*/
	
	
}

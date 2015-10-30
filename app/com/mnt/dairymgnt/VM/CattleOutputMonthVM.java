package com.mnt.dairymgnt.VM;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mnt.dairymgnt.models.CattleMaster;
import com.mnt.dairymgnt.models.Oraganization;
import com.mnt.dairymgnt.models.PregnantCattle;
import com.mnt.dairymgnt.models.Users;

public class CattleOutputMonthVM {

	public int cattleId;	
   	public Date lastUpdateDateTime;
	public String date;
	public int quantity;
	public String breed;
	public Date expectedDelivery;
	public String cattleName;
	//public String cattleName;
	//public String expectedMilkQuantity; 
	
}

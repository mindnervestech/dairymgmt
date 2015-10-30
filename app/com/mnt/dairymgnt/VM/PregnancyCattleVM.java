package com.mnt.dairymgnt.VM;

import java.util.Date;

import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mnt.dairymgnt.models.CattleMaster;

public class PregnancyCattleVM {
	public int pregnancyId;
	public String lastDeliveryDate;
	public  String expectedPregnancyDate;
	public String firstInseminationDate;
	public String secondInseminationDate;
	public String thirdInseminationDate;
	public String actualPregnancyDate;
	public String expectedDeliveryDateVM;
	public Date expectedDeliveryDate;
	public String milkingStoppingDate;
	public int cattleId;
	public  String name;
	public String dateofBirth;
}

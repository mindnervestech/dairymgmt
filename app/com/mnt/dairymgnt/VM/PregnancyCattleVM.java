package com.mnt.dairymgnt.VM;

import java.util.Date;

import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mnt.dairymgnt.models.CattleMaster;
import com.mnt.dairymgnt.models.Oraganization;
import com.mnt.dairymgnt.models.Users;

public class PregnancyCattleVM {
	public int pregnancyId;
	public String lastDeliveryDate;
	public String firstInseminationDate;
	public String secondInseminationDate;
	public String thirdInseminationDate;
	public String forthInseminationDate;
	public String plannedFirstInseminationDate;
	public String plannedSecondInseminationDate;
	public String plannedThirdInseminationDate;
	public String plannedForthInseminationDate;
	public String pregnacyDate;
	public String dueDate;
	public String successDate;
	public int cattleId;
	public  String name;
	public String dateofBirthVM;
	public Date dateofBirth;
	public Users users;
	public Oraganization oraganization;
}

package com.mnt.dairymgnt.VM;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mnt.dairymgnt.models.CattleMaster;
import com.mnt.dairymgnt.models.Oraganization;
import com.mnt.dairymgnt.models.Users;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CattleHealthVM {

	
	public int cattleId;	
	public Date lastUpdateDateTime;
    public String medicationType;
    public String medicationName;
    public String medicationStartDate;
    public String medicationEndDate;
    public boolean  pregnant;
    public String pregnancyDate;
    public String   lastDelivaerydate;
	public String duedate;
	public String attrib1;
	public String attrib2;
	public String attrib3;
	public String attrib4;
	public String attrib5;
	
	public Oraganization oraganization;
	public Users users;
	public CattleMaster cattleMaster;
	
	public String vaccinationType;
	public String vaccinationName;
	public String vaccinationPlannedDate;
	public String vaccinationActualDate;
	public String breed;
	public String subbreed;
	public String vaccination;

	
}

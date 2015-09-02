package com.mnt.dairymgnt.VM;

import java.util.Date;

import com.mnt.dairymgnt.models.Oraganization;
import com.mnt.dairymgnt.models.Users;

public class CattleHealthVM {

	
	public int cattleId;	
	public Date lastUpdateDateTime;
    public String medicationType;
    public String medicationName;
    public Date medicationStartDate;
    public Date medicationEnddate;
    public boolean  pregnant;
    public Date pregnancyDate;
    public Date   lastDelivaerydate;
	public Date duedate;
	public String attrib1;
	public String attrib2;
	public String attrib3;
	public String attrib4;
	public String attrib5;
	
	public Oraganization oraganization;
	public Users users;

	
}

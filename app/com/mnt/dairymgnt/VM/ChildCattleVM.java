package com.mnt.dairymgnt.VM;

import java.util.Date;

import com.mnt.dairymgnt.models.CattleMaster;
import com.mnt.dairymgnt.models.ChildCattle;
import com.mnt.dairymgnt.models.Oraganization;
import com.mnt.dairymgnt.models.Users;

public class ChildCattleVM {
	
	public int cattlechildId;
	public int RFID;
	public Date lastUpdateDateTime;
	public  String name;
	public String breed;
	public String dateofBirth;
	public String  gender;
	public String cattleIdentificationMark;
	public String attrib1;
	public String attrib2;
	public String attrib3;
	public String attrib4;
	public String attrib5;
	public Oraganization oraganization;
	public Users users;
	public CattleMaster cattleMaster;
	
}

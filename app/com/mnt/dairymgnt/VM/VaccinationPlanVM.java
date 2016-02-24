package com.mnt.dairymgnt.VM;

import java.util.Date;

import com.mnt.dairymgnt.models.CattleMaster;

public class VaccinationPlanVM {
	public long vaccinationPlanId;
	public String vaccineName;
	public String vaccineType;
	public String breed;
	public String subBreed;
	public String doctorName;
	public String companyName;
	public String elapsedDays;
	public CattleMaster cattleMaster;

	public Date lastUpdateDateTime;

}

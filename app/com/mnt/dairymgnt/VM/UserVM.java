package com.mnt.dairymgnt.VM;

import java.util.Date;
import java.util.List;

import com.mnt.dairymgnt.models.Oraganization;
import com.mnt.dairymgnt.models.Permissions;

public class UserVM {
	
	
	public String userId;
	public String password;
	public String firstname;
	public String lastname;
	public boolean accessRead;
	public boolean accessAdd;
	public boolean accessWrite;
	public Date lastlogin;
	public  Date lastLogout;
	public Date lastUpdatedatetime;	
	public List<Permissions> permissions;
	public Oraganization oraganization;
}

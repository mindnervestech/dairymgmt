package com.mnt.dairymgnt.models;

import javax.persistence.Entity;



import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.ConcurrencyMode;
import com.avaje.ebean.annotation.EntityConcurrencyMode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;
import play.db.ebean.Model;

@Entity
@EntityConcurrencyMode(ConcurrencyMode.NONE)
public class Users extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public String userId;
	public String password;
	public String firstname;
	public String lastname;
	//public boolean accessRead;
	public boolean accessAdd;
	public boolean accessWrite;
	public Date lastlogin;
	public  Date lastLogout;
	public Date lastUpdatedatetime;	
	public String userType;
	public Users() {

	}
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	public List<Permissions> permissions;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	public Oraganization oraganization;
	
	
	public Oraganization getOraganization() {
		return oraganization;
	}

	public void setOraganization(Oraganization oraganization) {
		this.oraganization = oraganization;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public Date getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}

	public Date getLastLogout() {
		return lastLogout;
	}

	public void setLastLogout(Date lastLogout) {
		this.lastLogout = lastLogout;
	}

	public Date getLastUpdatedatetime() {
		return lastUpdatedatetime;
	}

	public void setLastUpdatedatetime(Date lastUpdatedatetime) {
		this.lastUpdatedatetime = lastUpdatedatetime;
	}

	
	public static Finder<Long, Users> find = new Finder<Long, Users>(
			Long.class, Users.class);

	@JsonIgnore
	public static Users getUserByEmail(String uname) {
		return find.where().eq("userId", uname).findUnique();
	}
	
	@JsonIgnore
	public static Users checkForAdminByEmail(String uname) {
		return find.where().eq("userId", uname).eq("userType", "admin".trim()).findUnique();
	}

	public static void update(Users ud) {
		Ebean.update(ud);
	}

	@JsonIgnore
	public static Users isUser(String uname, String pass) {
		return find.where().eq("userId", uname).eq("password", pass)
				.findUnique();
	}

	@JsonIgnore
	public static Users getPassword(String email) {
		return find.where().eq("userId", email).findUnique();
	}
		
	@JsonIgnore
	public static List<Users> getAllUsers(int pageNumber, int rowperpage) {
		return find.setFirstRow(pageNumber * 10).setMaxRows(rowperpage)
				.findList();
	}
	
	@JsonIgnore
	public static int getAllUsersCount(int pageNumber) {
		// TODO Auto-generated method stub
		return find.setFirstRow(pageNumber * 10)
				.setMaxRows(Users.find.findRowCount()).findList().size();
	}
	
	@JsonIgnore
	public static List<Users> getAllOnlyUsers(){
		return find.all();
	} 
	
	
}

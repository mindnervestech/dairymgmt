package com.mnt.dairymgnt.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avaje.ebean.annotation.ConcurrencyMode;
import com.avaje.ebean.annotation.EntityConcurrencyMode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;


@Entity
@EntityConcurrencyMode(ConcurrencyMode.NONE)
public class Permissions  extends Model{
	
	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	public String permissionName;
	public String access;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public static Finder<Long, Permissions> find = new Finder<Long, Permissions>(
			Long.class, Permissions.class);
	
	public static List<Permissions> getAllPermissions(){
		return find.all();
	}

	public static Permissions getRecoredByPermissionsName(String permissionName){
		return find.where().eq("permissionName",permissionName.trim()).findUnique();
	}

	public static List<Permissions> getPermissionsByUserEmail(String userId) {
		// TODO Auto-generated method stub
		return find.where().eq("users_user_id", userId.trim()).findList();
	}
	
	@JsonIgnore
	@ManyToOne
	public Users users;
	
}

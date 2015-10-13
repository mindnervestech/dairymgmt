package com.mnt.dairymgnt.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import play.db.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class KPIMaster  extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int kpiId;
	public String  KPIName;
	public String  KPIDesc;
	public int kpiValue;
	
	public int getKpiValue() {
		return kpiValue;
	}

	public void setKpiValue(int kpiValue) {
		this.kpiValue = kpiValue;
	}

	public int getKpiId() {
		return kpiId;
	}

	public void setKpiId(int kpiId) {
		this.kpiId = kpiId;
	}
	
	public String getKPIName() {
		return KPIName;
	}

	public void setKPIName(String kPIName) {
		KPIName = kPIName;
	}

	public String getKPIDesc() {
		return KPIDesc;
	}

	public void setKPIDesc(String kPIDesc) {
		KPIDesc = kPIDesc;
	}

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	public Oraganization oraganization;
	
	public Oraganization getOraganization() {
		return oraganization;
	}

	public void setOraganization(Oraganization oraganization) {
		this.oraganization = oraganization;
	}

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	public Users users;

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
	
	public static Finder<Long, KPIMaster> find = new Finder<Long, KPIMaster>(
			Long.class, KPIMaster.class);
	
	public static List<KPIMaster> getAllKPIMaster(){
		return find.all();
	}
	
}

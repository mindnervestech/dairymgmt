package com.mnt.dairymgnt.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import com.avaje.ebean.SqlRow;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mnt.dairymgnt.VM.CattleOutputVMs;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class CattleOutput extends Model {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int cattleId;	
	
   	public Date LastUpdateDateTime;
	public String date;
	public Date time;
	
	
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}


	public int quantity;
	public int fatContent;
	public int SNFContent;
	public String deviceID;
	public String attrib1;
	public String attrib2;
	public String attrib3;
	public String attrib4;
	public String attrib5;
	public String expectedMilkQuantity; 
	
	
	public String getExpectedMilkQuantity() {
		return expectedMilkQuantity;
	}

	public void setExpectedMilkQuantity(String expectedMilkQuantity) {
		this.expectedMilkQuantity = expectedMilkQuantity;
	}

	public Date getLastUpdateDateTime() {
		return LastUpdateDateTime;
	}

	public void setLastUpdateDateTime(Date lastUpdateDateTime) {
		LastUpdateDateTime = lastUpdateDateTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}


	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getFatContent() {
		return fatContent;
	}

	public void setFatContent(int fatContent) {
		this.fatContent = fatContent;
	}

	public int getSNFContent() {
		return SNFContent;
	}

	public void setSNFContent(int sNFContent) {
		SNFContent = sNFContent;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getAttrib1() {
		return attrib1;
	}

	public void setAttrib1(String attrib1) {
		this.attrib1 = attrib1;
	}

	public String getAttrib2() {
		return attrib2;
	}

	public void setAttrib2(String attrib2) {
		this.attrib2 = attrib2;
	}

	public String getAttrib3() {
		return attrib3;
	}

	public void setAttrib3(String attrib3) {
		this.attrib3 = attrib3;
	}

	public String getAttrib4() {
		return attrib4;
	}

	public void setAttrib4(String attrib4) {
		this.attrib4 = attrib4;
	}

	public String getAttrib5() {
		return attrib5;
	}

	public void setAttrib5(String attrib5) {
		this.attrib5 = attrib5;
	}

	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	public Oraganization oraganization;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	public Users users;
	
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	public CattleMaster CattleMaster;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	public PregnantCattle pregnantCattle;
	
	
	public PregnantCattle getPregnantCattle() {
		return pregnantCattle;
	}

	public void setPregnantCattle(PregnantCattle pregnantCattle) {
		this.pregnantCattle = pregnantCattle;
	}

	public CattleMaster getCattleMaster() {
		return CattleMaster;
	}

	public void setCattleMaster(CattleMaster cattleMaster) {
		CattleMaster = cattleMaster;
	}

	public Oraganization getOraganization() {
		return oraganization;
	}

	public void setOraganization(Oraganization oraganization) {
		this.oraganization = oraganization;
	}


	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
	public static Finder<Long, CattleOutput> find = new Finder<Long, CattleOutput>(
			Long.class, CattleOutput.class);
	
	/*public static List<CattleOutput> getAllCattleOutput(){
		return find.all();
	}*/

	public static CattleOutput getUserByCattleId(int cattleId){
		return find.where().eq("cattleId", cattleId).findUnique();
	}

	@JsonIgnore
	public static List<CattleOutput> getAllCattleOutput(int pageNumber,int rowperpage){
		return find.setFirstRow(pageNumber * 10).setMaxRows(rowperpage)
				.findList();
	}
	
	@JsonIgnore
	public static List<CattleOutputVMs> getAllCattleOutputReport(int pageNumber,int rowperpage){
		 String sql = "select sum(co.quantity) as quantity, sum(co.expected_milk_quantity) as expectedQuntity, cm.breed as breed  from cattle_output as co inner join cattle_master as cm on  co.cattle_master_cattle_id = cm.cattle_id  group by cm.breed;";
		 RawSql rawSql = RawSqlBuilder.parse(sql).create();
		 List<SqlRow> rawSqls = Ebean.createSqlQuery(sql).findList();
		 ArrayList<CattleOutputVMs> cmvm = new ArrayList<>();
			
		 for(SqlRow row:rawSqls) {
		      CattleOutputVMs cfvm = new CattleOutputVMs();
			  cfvm.CattleName = row.getString("breed");
					//cfvm.deviceID = u.deviceID;
			  cfvm.actualMilkQuantity = row.getInteger("quantity");
			  cfvm.expectedMilkQuantity = row.getInteger("expectedQuntity"); 
					//cfvm.SNFContent = u.SNFContent;
					//cfvm.fatContent = u.fatContent;
					//cfvm.lastUpdateDateTime =  u.LastUpdateDateTime;
				    //cfvm.users = u.users;
			    	//cfvm.oraganization = u.oraganization;
					//cfvm.cattleId = u.CattleMaster.getBreed();
					//cfvm.CattleName = u.CattleMaster.getName();
				    //cfvm.cattleMaster = u.CattleMaster;
					//cfvm.pregnantCattle = u.pregnantCattle;
			 cmvm.add(cfvm);
		 }
		return  cmvm;
	}
	
	@JsonIgnore
	public static List<CattleOutputVMs> getAllCattleOutputReportBreed(){
		 String sql = "select co.quantity as quantity, co.expected_milk_quantity as expectedQuntity, cm.breed as breed,cm.name as cattlename  from cattle_output as co inner join cattle_master as cm on  co.cattle_master_cattle_id = cm.cattle_id;";
		 RawSql rawSql = RawSqlBuilder.parse(sql).create();
		 List<SqlRow> rawSqls = Ebean.createSqlQuery(sql).findList();
		 ArrayList<CattleOutputVMs> cmvm = new ArrayList<>();
			
		 for(SqlRow row:rawSqls) {
		      CattleOutputVMs cfvm = new CattleOutputVMs();
			  cfvm.breed = row.getString("breed");
					//cfvm.deviceID = u.deviceID;
			  cfvm.actualMilkQuantity = row.getInteger("quantity");
			  cfvm.expectedMilkQuantity = row.getInteger("expectedQuntity"); 
			  cfvm.CattleName = row.getString("cattlename");
					//cfvm.SNFContent = u.SNFContent;
					//cfvm.fatContent = u.fatContent;
					//cfvm.lastUpdateDateTime =  u.LastUpdateDateTime;
				    //cfvm.users = u.users;
			    	//cfvm.oraganization = u.oraganization;
					//cfvm.cattleId = u.CattleMaster.getBreed();
					//cfvm.CattleName = u.CattleMaster.getName();
				    //cfvm.cattleMaster = u.CattleMaster;
					//cfvm.pregnantCattle = u.pregnantCattle;
			 cmvm.add(cfvm);
		 }
		return  cmvm;
	}
	
	
	
	@JsonIgnore
	public static int getAllCattleOutputCount(int pageNumber) {
		// TODO Auto-generated method stub
		return find.setFirstRow(pageNumber * 10)
				.setMaxRows(CattleOutput.find.findRowCount()).findList().size();
	}
	
	public static void update(CattleOutput ud) {
		Ebean.update(ud);
	}
	
	@JsonIgnore
	public static List<CattleOutput> getAllCattleOutputReportbyBreed(Date startdate,Date enddate,String breed){
		String brd =  breed.replaceAll("\"", "");
		//System.out.println(brd);
			
		return find.where().eq("CattleMaster.breed", brd).between("LastUpdateDateTime", startdate, enddate).findList();
	}
	
	@JsonIgnore
	public static List<SqlRow> getAllCattleOutputReportbyMonth(Date startdate,Date enddate,String breed, String cattleName){
		String brd =  breed.replaceAll("\"", "");
		
			//SQL query.
			String sql = "select co.cattle_master_cattle_id, co.last_update_date_time, cm.name, cm.breed, co.quantity, co.expected_milk_quantity from cattle_output as co inner join cattle_master as cm on co.cattle_master_cattle_id = cm.cattle_id where cm.breed = :brd and cm.name = :cat and cm.last_delivery BETWEEN :startdate and :enddate";
			List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).setParameter("brd", brd).setParameter("startdate", startdate).setParameter("enddate", enddate).setParameter("cat", cattleName).findList();
		
		return sqlRows;
	}

	public static List<SqlRow> getAllCattleDeliveryReportbyYear(Date startDate, Date endDate, String breedName) {
		System.out.println("start "+startDate+" enddate "+endDate+" breed "+breedName);
		String sql = "select pc.cattle_id, cm.name, cm.breed, pc.expected_delivery_date from pregnant_cattle as pc inner join cattle_master as cm on pc.cattle_id = cm.cattle_id where cm.breed = :brd and pc.expected_delivery_date BETWEEN :startdate and :enddate";
		
		List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).setParameter("brd", breedName).setParameter("startdate", startDate).setParameter("enddate", endDate).findList();
		System.out.println("result "+sqlRows);
		return sqlRows;
	}

	public static List<SqlRow> getMonthlyCattleOutputReport(Date firstDeliveryDate, Date lastDeliveryDate, String breedName, String cattleName, Date firstBirthDate, Date lastBirthDate) {
		//System.out.println("start1 "+firstBirthDate+" end1 "+lastBirthDate + "breed "+breedName);
		String sql = "select name, breed, last_delivery, dateof_birth from cattle_master where breed = :brd and name = :cat and is_pregnant = \"No\" and ((last_delivery BETWEEN :startdate and :enddate) OR (dateof_birth BETWEEN :startbirthdate and :endbirthdate))";
		//String sql = "select name, breed, last_delivery, dateof_birth from cattle_master where breed = :brd and name = :cat and is_pregnant = \"No\" and last_delivery BETWEEN :startdate and :enddate";
		List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).setParameter("brd", breedName).setParameter("startdate", lastDeliveryDate).setParameter("enddate", firstDeliveryDate).setParameter("cat", cattleName).setParameter("startbirthdate", lastBirthDate).setParameter("endbirthdate", firstBirthDate).findList();
	
		return sqlRows;
	}

	
}


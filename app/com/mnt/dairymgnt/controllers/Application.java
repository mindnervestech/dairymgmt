package com.mnt.dairymgnt.controllers;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;




import play.Play;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.register;
import views.html.registrationredirect;
import views.html.sharejobdetails;
import views.html.signin;


import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlRow;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.mnt.dairymgnt.VM.CattleFeedMasterVM;
import com.mnt.dairymgnt.VM.CattleFeedsVM;
import com.mnt.dairymgnt.VM.CattleHealthVM;
import com.mnt.dairymgnt.VM.CattleIntakeVM;
import com.mnt.dairymgnt.VM.CattleMasterVM;
import com.mnt.dairymgnt.VM.CattleMasterVMs;
import com.mnt.dairymgnt.VM.CattleOutputMonthVM;
import com.mnt.dairymgnt.VM.CattleOutputVM;
import com.mnt.dairymgnt.VM.CattleOutputVMs;
import com.mnt.dairymgnt.VM.ChildCattleVM;
import com.mnt.dairymgnt.VM.FeedMasterVM;
import com.mnt.dairymgnt.VM.KPIMasterVM;
import com.mnt.dairymgnt.VM.OrgnizationVM;
import com.mnt.dairymgnt.VM.PregnancyCattleVM;
import com.mnt.dairymgnt.VM.UserVM;
import com.mnt.dairymgnt.models.Breeds;
import com.mnt.dairymgnt.models.CattleFeedMaster;
import com.mnt.dairymgnt.models.CattleFeeds;
import com.mnt.dairymgnt.models.CattleHealth;
import com.mnt.dairymgnt.models.CattleIntake;
import com.mnt.dairymgnt.models.CattleMaster;
import com.mnt.dairymgnt.models.CattleOutput;
import com.mnt.dairymgnt.models.ChildCattle;
import com.mnt.dairymgnt.models.Entities;
import com.mnt.dairymgnt.models.FeedMaster;
import com.mnt.dairymgnt.models.KPIMaster;
import com.mnt.dairymgnt.models.KPIName;
import com.mnt.dairymgnt.models.Oraganization;
import com.mnt.dairymgnt.models.Permissions;
import com.mnt.dairymgnt.models.PregnantCattle;
import com.mnt.dairymgnt.models.Subbreed;
//import com.itextpdf.text.List;;
import com.mnt.dairymgnt.models.Users;


public class Application extends Controller {
	public static Result index() {
		return ok();
	}

	public static Result login() {
		return ok(signin.render());
	}

	public static Result signup() {
		return ok(register.render());
	}
	
	public static Result registrationRedirect() {
		return ok(registrationredirect.render());
	}

	public static Result shareJobDetails(){
		return ok(sharejobdetails.render());
		
	}
	
	public static Result dashBoard() {
		return ok(index.render());
	}

	public static Result logOut() {

		try {
			String email = session().get("email");
		    
			Users u = Users.getUserByEmail(email);
			u.setLastLogout(new Date());
			u.setLastUpdatedatetime(new Date());
			u.update();
			
			session().clear();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session().clear();
		}
		return ok(signin.render());
	}

	public static Result getAllOrgs( int pageNo){
		List<OrgnizationVM> orgs = new ArrayList<>();
		List<Oraganization> os = Oraganization.getAllOrgs(pageNo,10);
		int count  = 0;
		count = Oraganization.getAllOrgsCount(pageNo);
		for(Oraganization or:os){
			OrgnizationVM o = new OrgnizationVM();
			o.name = or.name;
			o.orgId = or.orgId;
			o.city =  or.city;
			o.addressLine1 = or.addressLine1;
			o.addressLine2 = or.addressLine2;
			o.state  = or.state;
			o.pincode = or.pincode;
			o.lastUpdatedtime = or.lastUpdatedtime;
		     	
			orgs.add(o);
		}
		
		HashMap  <String ,Object> hm = new HashMap();
		hm.put("orgs", orgs);
		hm.put("userCount",count);
		return ok(Json.toJson(hm));
		
	}
	
	
	public static Result getAllOnlyOrgs(){
		List<OrgnizationVM> orgs = new ArrayList<>();
		List<Oraganization> os = Oraganization.getAllOnlyOrg();
		for(Oraganization or:os){
			OrgnizationVM o = new OrgnizationVM();
			o.name = or.name;
			o.orgId = or.orgId;
			o.city =  or.city;
			o.addressLine1 = or.addressLine1;
			o.addressLine2 = or.addressLine2;
			o.state  = or.state;
			o.pincode = or.pincode;
			o.lastUpdatedtime = or.lastUpdatedtime;
			orgs.add(o);
		}
		
		HashMap  <String ,Object> hm = new HashMap();
		hm.put("orgs", orgs);
		return ok(Json.toJson(hm));
		
	}
	
	
	
	
	
	
	public  static  Result getAllOnlyUsers(){
		List<Users> users = Users.getAllOnlyUsers();
        ArrayList<UserVM> uvm = new ArrayList<>();
		
		for(Users u : users){
			UserVM uv = new UserVM();
			uv.firstname = u.firstname;
			uv.lastname = u.lastname;
			uv.password = u.password;
			uv.userId = u.userId;
			uv.permissions  = u.permissions;
			uv.lastUpdatedatetime = u.lastUpdatedatetime;
			uv.oraganization = u.oraganization;
			uvm.add(uv);
		}

		HashMap  <String ,Object> hm = new HashMap();
		hm.put("users", uvm);
		return ok(Json.toJson(hm));
	} 

	public static Result getAllUsers(int pageNo){
		int count = 0;
		List<Users> users = Users.getAllUsers(pageNo,10);
		count  = Users.getAllUsersCount(pageNo);
        ArrayList<UserVM> uvm = new ArrayList<>();
		
		for(Users u : users){
			UserVM uv = new UserVM();
			uv.firstname = u.firstname;
			uv.lastname = u.lastname;
			uv.password = u.password;
			uv.userId = u.userId;
			uv.permissions  = u.permissions;
			uv.lastUpdatedatetime = u.lastUpdatedatetime;
			uv.oraganization = u.oraganization;
			uvm.add(uv);
		}

		HashMap  <String ,Object> hm = new HashMap();
		hm.put("users", uvm);
		hm.put("userCount", count);
		return ok(Json.toJson(hm));
	}
	
	public static Result getAllCattleMaster(int pageNo){
		int count  = 0;
		List<CattleMaster> cattleMasters = CattleMaster.getAllCattleMaster(pageNo,10);
        ArrayList<CattleMasterVM> cmvm = new ArrayList<>();
		count = CattleMaster.getAllCattleMasterCount(pageNo);
		
		for(CattleMaster u : cattleMasters){
			CattleMasterVM cvm = new CattleMasterVM();
			cvm.breed = u.breed;
			cvm.cattleId = u.cattleId;
			cvm.cattleIdentificationMark = u.cattleIdentificationMark;
			//cvm.dateofBirth = u.dateofBirth;
			if(u.dateofBirth != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.dateofBirth);
				cvm.dateofBirthVM = date.toString();
			}else{
				cvm.dateofBirthVM = "";
			}
			
			cvm.gender  = u.gender;
			cvm.name = u.name;
			cvm.users = u.users;
			cvm.oraganization = u.oraganization;
			cvm.RFID = u.RFID;
			cvm.parentId  =u.parentId; 
			//cvm.lastDelivery = u.lastDelivery;
			if(u.lastDelivery != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.lastDelivery);
				cvm.lastDeliveryVM = date.toString();
			}else{
				cvm.lastDeliveryVM = "";
			}

			if(u.diedonDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.diedonDate);
				cvm.diedonDateVM = date.toString();
			}else{
				cvm.diedonDateVM = "";
			}
			cvm.subbreed = u.subBreed;
			cvm.isPregnant = u.isPregnant;
			cvm.lastUpdateDateTime = u.lastUpdateDateTime;
			cmvm.add(cvm);
		}

		HashMap  <String ,Object> hm = new HashMap();
		hm.put("caters",cmvm);
		hm.put("userCount", count);
		
		return ok(Json.toJson(hm));
	}
	public static Result getAllFeedMaster(int pageNo){
		int count  = 0;
		List<FeedMaster> feedMasters = FeedMaster.getAllFeedMaster(pageNo,10);
        ArrayList<FeedMasterVM> cmvm = new ArrayList<>();
		count = CattleMaster.getAllCattleMasterCount(pageNo);
		
		for(FeedMaster u : feedMasters){
			FeedMasterVM cvm = new FeedMasterVM();
			cvm.feedId = u.feedId;
			cvm.feedName = u.feedName;
			cvm.feedProtine = u.feedProtine;
			cvm.quantityofProtine = u.quantityofProtine;
			cvm.feedWaterContent = u.feedWaterContent;
			cvm.quantityofWater = u.quantityofWater;
			cvm.feedFiber = u.feedFiber;
			cvm.quantityofFiber = u.quantityofFiber;
			cvm.feedVitamins = u.feedVitamins;
			cvm.quantityofProtine = u.quantityofProtine;
			cvm.quantityofVitamins = u.quantityofVitamins;
			cvm.skuId = u.skuId;
			cmvm.add(cvm);
			//cvm.dateofBirth = u.dateofBirth;
		}	
		HashMap  <String ,Object> hm = new HashMap();
		hm.put("caters",cmvm);
		hm.put("userCount", count);
		
		return  ok(Json.toJson(hm));
		
	}
	
	public static Result getAllOnlyFeedMaster(){
		int count  = 0;
		List<FeedMaster> feedMasters = FeedMaster.getAllOnlyFeedMaster();
        ArrayList<FeedMasterVM> cmvm = new ArrayList<>();
		
		for(FeedMaster u : feedMasters){
			FeedMasterVM cvm = new FeedMasterVM();
			cvm.feedId = u.feedId;
			cvm.feedName = u.feedName;
			cvm.feedProtine = u.feedProtine;
			cvm.quantityofProtine = u.quantityofProtine;
			cvm.feedWaterContent = u.feedWaterContent;
			cvm.quantityofWater = u.quantityofWater;
			cvm.feedFiber = u.feedFiber;
			cvm.quantityofFiber = u.quantityofFiber;
			cvm.feedVitamins = u.feedVitamins;
			cvm.quantityofProtine = u.quantityofProtine;
			cvm.skuId = u.skuId;
			cmvm.add(cvm);
			//cvm.dateofBirth = u.dateofBirth;
		}	
		HashMap  <String ,Object> hm = new HashMap();
		hm.put("caters",cmvm);
		return  ok(Json.toJson(hm));
	}
	
	public static Result getAllCattleMasterReport(int pageNo){
		int count  = 0;
		List<CattleMaster> cattleMasters = CattleMaster.getAllCattleMasterReport(pageNo,10);
        ArrayList<CattleMasterVMs> cmvm = new ArrayList<>();
		count = CattleMaster.getAllCattleMasterCount(pageNo);
		
		for(CattleMaster u : cattleMasters){
			
			CattleMasterVMs cvm = new CattleMasterVMs();
			cvm.breed = u.breed;
			cvm.cattleId = u.cattleId;
			cvm.cattleIdentificationMark =    u.cattleIdentificationMark;
			DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
			
			String birthDate = df.format(u.dateofBirth);
			cvm.dateofBirth = birthDate;
			
			cvm.gender  = u.gender;
			cvm.name = u.name;
			
			//	cvm.users = u.users;
			//cvm.oraganization = u.oraganization;
			//cvm.RFID = u.RFID;
			//cvm.parentId  =u.parentId; 
			//cvm.lastUpdateDateTime = u.lastUpdateDateTime;

			SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
			Date d1 = null;
			String d2 = null;
 			Date d3  = null;
 			long diff = 0;
 			long diffDays = 0;
			try {
				
			if(u.dateofBirth !=null){
				DateFormat df1 = new SimpleDateFormat("dd-MMMM-yyyy");
				String birthDate1 = df1.format(u.dateofBirth);
				
				d1 = format.parse(birthDate1);

				d2 = format.format(new Date());
				d3 = format.parse(d2);
				diff = d3.getTime() - d1.getTime();
				diffDays = diff / (24 * 60 * 60 * 1000);
				System.out.print(diffDays + " days, ");
			}	
				//in milliseconds
			} catch (Exception e) {
				e.printStackTrace();
			}

            if(diffDays > 480){
            	cmvm.add(cvm);	
            }
			
		}

		HashMap  <String ,Object> hm = new HashMap();
		hm.put("caters",cmvm);
		hm.put("userCount", count);
		
		return ok(Json.toJson(hm));
	}
	
	
	
	
	public static Result getAllOnlyCattleMaster(){
		List<CattleMaster> cattleMasters = CattleMaster.getAllOnlyCattleMaster();
        ArrayList<CattleMasterVM> cmvm = new ArrayList<>();
		
		for(CattleMaster u : cattleMasters){
			CattleMasterVM cvm = new CattleMasterVM();
			cvm.breed = u.breed;
			cvm.cattleId = u.cattleId;
			cvm.cattleIdentificationMark =    u.cattleIdentificationMark;
			cvm.dateofBirth = u.dateofBirth;
			cvm.gender  = u.gender;
			cvm.name = u.name;
			cvm.users = u.users;
			cvm.oraganization = u.oraganization;
			cvm.RFID = u.RFID;
			cvm.parentId  =u.parentId; 
			
			cvm.lastUpdateDateTime = u.lastUpdateDateTime;
			cmvm.add(cvm);
		}

		HashMap  <String ,Object> hm = new HashMap();
		hm.put("caters",cmvm);
		return ok(Json.toJson(hm));
	}
	
	
	
	public static Result getAllChildCattleMaster(int parentId,int pageno){
		List<CattleMaster> cattleMasters = CattleMaster.getAllCattleMasterByParentId(parentId,pageno,10);
        int count = 0;
        count  = CattleMaster.getAllCattleMasterCount(pageno);
        
		ArrayList<CattleMasterVM> cmvm = new ArrayList<>();
		
		for(CattleMaster u : cattleMasters){
			CattleMasterVM cvm = new CattleMasterVM();
			/*cvm.breed = u.breed;
			cvm.cattlechildId = u.cattlechildId;
			cvm.RFID = u.RFID;
			cvm.cattleIdentificationMark =    u.cattleIdentificationMark;
			cvm.dateofBirth = u.dateofBirth;
			cvm.gender  = u.gender;
			cvm.name = u.name;
			cvm.users = u.users;
			cvm.oraganization = u.oraganization;
			cvm.cattleMaster = u.cattleMaster;
			*/
			
			cvm.breed = u.breed;
			cvm.cattleId = u.cattleId;
			cvm.cattleIdentificationMark =    u.cattleIdentificationMark;
			cvm.dateofBirth = u.dateofBirth;
			cvm.gender  = u.gender;
			cvm.name = u.name;
			cvm.users = u.users;
			cvm.oraganization = u.oraganization;
			cvm.RFID = u.RFID;
			cvm.parentId  =u.parentId; 
			cvm.lastUpdateDateTime = u.lastUpdateDateTime;
			cmvm.add(cvm);
			
		}

		HashMap  <String ,Object> hm = new HashMap();
		hm.put("caters",cmvm);
		hm.put("userCount", count);
		return ok(Json.toJson(hm));
	}
	
	public static Result getAllFeedCattleMaster(int pageno){
		List<CattleFeedMaster> cattleFeedMasters = CattleFeedMaster.getAllFeedCattleMaster(pageno,10);
        int count  = 0;
        count  = CattleFeedMaster.getAllFeedCattleMasterCount(pageno);
        
		ArrayList<CattleFeedMasterVM> cmvm = new ArrayList<>();
		
		for(CattleFeedMaster u : cattleFeedMasters){
			CattleFeedMasterVM cfvm = new CattleFeedMasterVM();
			cfvm.feedName = u.feedName;
			cfvm.feedId = u.feedId;
			cfvm.feedType =    u.feedType;
			//cfvm.feedType = u.feedType;
			cfvm.MealType = u.MealType;
			cfvm.quantity = u.quantity;
			cfvm.Breed = u.Breed;
			cfvm.SubBreed = u.SubBreed;
			cfvm.lastUpdateDateTime =  u.lastUpdateDateTime;
			cfvm.users = u.users;
			cfvm.SKUId = u.SKUId;
			cfvm.Stage = u.Stage;
			cfvm.oraganization = u.oraganization;
			
			List <CattleFeeds> cfs = CattleFeeds.getCatleFeedsById(cfvm.feedId);
			cfvm.cattleFeeds = cfs;
			cmvm.add(cfvm);
		}
		HashMap  <String ,Object> hm = new HashMap();
		hm.put("feedcaters",cmvm);
		hm.put("userCount", count);
		return ok(Json.toJson(hm));
	}
	
	
	public static Result getDailyMilkReport(int pageno){
		Date strdate  = null;
		Calendar cal = Calendar.getInstance();
	    int year = cal.get(cal.YEAR);
	    String month = new SimpleDateFormat("MMM").format(cal.getTime());
	    
		String sdate = ("01-"+month+"-"+year).replace("\"", "");
		
		SimpleDateFormat  format = new SimpleDateFormat("dd-MMM-yyyy");  
		try{
			 strdate = format.parse(sdate);
			 
		}catch(Exception e){
			e.printStackTrace();
		}
		
		List<CattleMaster> cattleMasters = CattleMaster.getDailyMilkReport(pageno,20);
        int count  = 0;
        count  = CattleMaster.getDailyMilkReportCount(pageno);
        
		ArrayList<CattleMasterVM> cmvm = new ArrayList<>();
		List<KPIName> kpi  = KPIName.getAllKPIName();
		int cattleInsimination = 0;
		
		for(KPIName u : kpi){
			cattleInsimination = u.DaysbyWhenCalvsCanbeInseminated;
		}
		
		for(CattleMaster u : cattleMasters){
			Date d1 = null;
			Date d2 = null;

			try {
				d1 = u.lastDelivery;
				d2 = strdate;

				//in milliseconds
				long diff = d2.getTime() - d1.getTime();
				long diffDays = diff / (24 * 60 * 60 * 1000);


				if(cattleInsimination >= diffDays){
					System.out.println("days======" + diffDays + "cattleInsimination--------"+ cattleInsimination);
					System.out.println("u.lastDelivery------" + d1 + "strdate---------" + d2);
					CattleMasterVM cfvm = new CattleMasterVM();
					cfvm.cattleId = u.cattleId;
					cfvm.breed = u.breed;
					cfvm.name = u.name;
					cfvm.lastDelivery = u.lastDelivery;
					cmvm.add(cfvm);
				}

				

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		HashMap  <String ,Object> hm = new HashMap();
		hm.put("dailyMilkReport",cmvm);
		hm.put("userCount", count);
		return ok(Json.toJson(hm));
	}
	
	
	
	public static Result getAllOnlyFeedCattleMaster(){
		List<CattleFeedMaster> cattleFeedMasters = CattleFeedMaster.getAllOnlyFeedCattleMaster();
        
		ArrayList<CattleFeedMasterVM> cmvm = new ArrayList<>();
		
		for(CattleFeedMaster u : cattleFeedMasters){
			CattleFeedMasterVM cfvm = new CattleFeedMasterVM();
			cfvm.feedName = u.feedName;
			cfvm.feedId = u.feedId;
			cfvm.feedType =    u.feedType;
			//cfvm.feedType = u.feedType;
			cfvm.MealType = u.MealType;
			cfvm.quantity = u.quantity;
			cfvm.Breed = u.Breed;
			cfvm.SubBreed = u.SubBreed;
			cfvm.lastUpdateDateTime =  u.lastUpdateDateTime;
			cfvm.users = u.users;
			cfvm.SKUId = u.SKUId;
			cfvm.Stage = u.Stage;
			cfvm.oraganization = u.oraganization;
			cmvm.add(cfvm);
		}
		
		HashMap  <String ,Object> hm = new HashMap();
		hm.put("feedcaters",cmvm);
		return ok(Json.toJson(hm));
	}
		
	
	public static Result getAllPregnantCattle(int pregnant,int pageno){
		List<PregnantCattle> pregnantCattle = PregnantCattle.getAllPregnantCattle(pregnant,pageno,10);
        int count = 0;
        count  = PregnantCattle.getAllPregnantCattleCount(pageno);
		ArrayList<PregnancyCattleVM> cmvm = new ArrayList<>();
        CattleMaster cm = new CattleMaster();
		for(PregnantCattle u : pregnantCattle){
			PregnancyCattleVM cfvm = new PregnancyCattleVM();
			
			cfvm.pregnancyId =  u.pregnancyId;
			if(u.cattleId != 0 ){
		     CattleMaster c = cm.getUserByCattleId(u.cattleId);;
		     cfvm.cattleId = c.cattleId;
		     
		     cfvm.dateofBirth = c.dateofBirth;
		     cfvm.name = c.name;
			}
			
			cfvm.lastDeliveryDate =u.lastDeliveryDate ;
			cfvm.expectedPregnancyDate = u.expectedPregnancyDate;
			cfvm.firstInseminationDate = u.firstInseminationDate;
			cfvm.secondInseminationDate = u.secondInseminationDate;
			cfvm.thirdInseminationDate= u.thirdInseminationDate ;
			cfvm.actualPregnancyDate = u.actualPregnancyDate;
			cfvm.expectedDeliveryDateVM  = u.expectedDeliveryDate.toString();
			cfvm.milkingStoppingDate = u.milkingStoppingDate;
			cmvm.add(cfvm);
		}

		HashMap  <String ,Object> hm = new HashMap();
		hm.put("caters",cmvm);
		hm.put("userCount", count);
		
		return ok(Json.toJson(hm));
	}
		
	public static Result getAllOnlyPregnantCattle(int pregnant){
		List<PregnantCattle> pregnantCattle = PregnantCattle.getAllOnlyPregnantCattle(pregnant);

		ArrayList<PregnancyCattleVM> cmvm = new ArrayList<>();
        CattleMaster cm = new CattleMaster();
		for(PregnantCattle u : pregnantCattle){
			PregnancyCattleVM cfvm = new PregnancyCattleVM();
			
			cfvm.pregnancyId =  u.pregnancyId;
			if(u.cattleId != 0 ){
		     CattleMaster c = cm.getUserByCattleId(u.cattleId);;
		     cfvm.cattleId = c.cattleId;
		     cfvm.dateofBirth = c.dateofBirth;
		     cfvm.name = c.name;
			}
			
			cfvm.lastDeliveryDate =u.lastDeliveryDate ;
			cfvm.expectedPregnancyDate = u.expectedPregnancyDate;
			cfvm.firstInseminationDate = u.firstInseminationDate;
			cfvm.secondInseminationDate = u.secondInseminationDate;
			cfvm.thirdInseminationDate= u.thirdInseminationDate ;
			cfvm.actualPregnancyDate = u.actualPregnancyDate;
			cfvm.expectedDeliveryDate  = u.expectedDeliveryDate;
			cfvm.milkingStoppingDate = u.milkingStoppingDate;
			cmvm.add(cfvm);
		}

		HashMap  <String ,Object> hm = new HashMap();
		hm.put("caters",cmvm);
		return ok(Json.toJson(hm));
	}
	

	
	public static Result getAllCattleIntake(int pageno){
		List<CattleIntake> cattleIntake = CattleIntake.getAllCattleIntake(pageno,10);
       int count = 0;
       count = CattleIntake.getAllCattleIntakeCount(pageno);
		ArrayList<CattleIntakeVM> cmvm = new ArrayList<>();
		
		for(CattleIntake u : cattleIntake){
			CattleIntakeVM cfvm = new CattleIntakeVM();
			cfvm.date = u.date;
			cfvm.deviceID = u.deviceID;
			cfvm.quantity = u.quantity;
			cfvm.lastUpdateDateTime =  u.lastUpdateDateTime;
			cfvm.users = u.users;
			cfvm.oraganization = u.oraganization;
			cfvm.cattleFeedMaster = u.CattleFeedMaster;
			cfvm.cattleId = u.cattleId;
			cfvm.cattleMaster = u.CattleMaster;
			cfvm.date = u.date;
			cfvm.pregnantCattle = u.pregnantCattle;
//			cfvm.actualFeedType =  u.actualFeedType;
//			cfvm.actualFeedName  =  u.actualFeedName;
//			cfvm.expectedFeedType = u.expectedFeedType;
//			cfvm.expectedFeedName = u.expectedFeedName;
//			cfvm.expectedFeedQuantity = u.expectedFeedQuantity;
			cmvm.add(cfvm);
		}

		HashMap  <String ,Object> hm = new HashMap();
		hm.put("catersintake",cmvm);
		hm.put("userCount", count);
		return ok(Json.toJson(hm));
	}
	
	
	public static Result getAllOnlyCattleIntake(){
		List<CattleIntake> cattleIntake = CattleIntake.getAllOnlyCattleIntake();
        ArrayList<CattleIntakeVM> cmvm = new ArrayList<>();
		
		for(CattleIntake u : cattleIntake){
			CattleIntakeVM cfvm = new CattleIntakeVM();
			cfvm.date = u.date;
			cfvm.deviceID = u.deviceID;
			cfvm.quantity = u.quantity;
			cfvm.lastUpdateDateTime =  u.lastUpdateDateTime;
			cfvm.users = u.users;
			cfvm.oraganization = u.oraganization;
			cfvm.cattleFeedMaster = u.CattleFeedMaster;
			cfvm.cattleId = u.cattleId;
			cfvm.cattleMaster = u.CattleMaster;
			cfvm.date = u.date;
			cfvm.pregnantCattle = u.pregnantCattle;
//			cfvm.actualFeedType =  u.actualFeedType;
//			cfvm.actualFeedName  =  u.actualFeedName;
//			cfvm.expectedFeedType = u.expectedFeedType;
//			cfvm.expectedFeedName = u.expectedFeedName;
//			cfvm.expectedFeedQuantity = u.expectedFeedQuantity;
			cmvm.add(cfvm);
		}

		HashMap  <String ,Object> hm = new HashMap();
		hm.put("catersintake",cmvm);
		return ok(Json.toJson(hm));
	}
		
	public static Result getAllCattleOutput(int pageno){
		List<CattleOutput> cattleOutput = CattleOutput.getAllCattleOutput(pageno,10);
		int count =0;
		count  = CattleOutput.getAllCattleOutputCount(pageno);
		
		ArrayList<CattleOutputVM> cmvm = new ArrayList<>();
		
		for(CattleOutput u : cattleOutput){
			CattleOutputVM cfvm = new CattleOutputVM();
			cfvm.date = u.date;
			cfvm.deviceID = u.deviceID;
			cfvm.quantity = u.quantity;
			cfvm.lastUpdateDateTime =  u.LastUpdateDateTime;
			cfvm.users = u.users;
			cfvm.oraganization = u.oraganization;
			cfvm.cattleId = u.cattleId;
			cfvm.SNFContent = u.SNFContent;
			cfvm.fatContent = u.fatContent;
			cfvm.cattleMaster = u.CattleMaster;
			cfvm.pregnantCattle = u.pregnantCattle;
			cfvm.expectedMilkQuantity = u.expectedMilkQuantity; 
			cmvm.add(cfvm);
		}

		HashMap  <String ,Object> hm = new HashMap();
		hm.put("catersoutput",cmvm);
		hm.put("userCount", count);
		return ok(Json.toJson(hm));
	}
	
	
	
	public static Result getAllCattleOutputReportbyBreed(){
		
		JsonNode json = request().body().asJson();
		JsonNode startdate = json.get("startdate");
		JsonNode enddate = json.get("enddate");
		JsonNode  breed = json.get("breed");

		Date endate = null;
		Date strdate  = null;
		String sd = startdate.toString().replaceAll("\"", "");
		String ed = enddate.toString().replaceAll("\"", "");
		
		SimpleDateFormat  format = new SimpleDateFormat("dd-MMM-yyyy");  
		try{
			 endate = format.parse(ed);  
			 strdate = format.parse(sd);  
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println(endate +"new date: "+new Date());  
		List<CattleOutput> cattleOutput = CattleOutput.getAllCattleOutputReportbyBreed(strdate,endate,breed.toString());
		int count =0;
		//count  = CattleOutput.getAllCattleOutputCount(pageno);
		
		ArrayList<CattleOutputVMs> cmvm = new ArrayList<>();
		
		for(CattleOutput u : cattleOutput){
			CattleOutputVMs cfvm = new CattleOutputVMs();
			cfvm.date = u.date;
			//cfvm.deviceID = u.deviceID;
			cfvm.actualMilkQuantity = u.quantity;
			cfvm.expectedMilkQuantity = Integer.parseInt(u.expectedMilkQuantity); 
			cfvm.SNFContent = u.SNFContent;
			cfvm.fatContent = u.fatContent;
			//cfvm.lastUpdateDateTime =  u.LastUpdateDateTime;
		    //cfvm.users = u.users;
	    	//cfvm.oraganization = u.oraganization;
			cfvm.breed = u.CattleMaster.getBreed();
			cfvm.CattleName = u.CattleMaster.getName();
		    //cfvm.cattleMaster = u.CattleMaster;
			//cfvm.pregnantCattle = u.pregnantCattle;
			cmvm.add(cfvm);
		}

		HashMap  <String ,Object> hm = new HashMap();
		hm.put("catersoutput",cmvm);
		hm.put("userCount", count);
		return ok(Json.toJson(hm));
	}
	
	
	public static Result getAllCattleOutputReportbyMonth(){
		
		JsonNode json = request().body().asJson();
		JsonNode month = json.get("month");
		JsonNode year = json.get("year");
		JsonNode  breed = json.get("breed");
		
		List<SqlRow> sqlRows;
		ArrayList<CattleOutputVMs> cmvm = new ArrayList<>();
		
		if((breed.toString().replace("\"", "")).equalsIgnoreCase("all")){
			
			String sql = "select cattle_id, name, breed from cattle_master";
			sqlRows = Ebean.createSqlQuery(sql).findList();
		}else {
			String sql = "select cattle_id, name, breed from cattle_master where breed = :brd";
			sqlRows = Ebean.createSqlQuery(sql).setParameter("brd", (breed.toString().replace("\"", ""))).findList();
		}
		
		for(SqlRow dataRows: sqlRows){
			Date enddate = null;
			Date strdate  = null;
			
			String cattleName = dataRows.getString("name");
			int cattleId = dataRows.getInteger("cattle_id");
			String breedName = dataRows.getString("breed");
			
			String sdate = ("01-"+month+"-"+year).replace("\"", "");
			
			SimpleDateFormat  format = new SimpleDateFormat("dd-MMM-yyyy");  
			try{
				 strdate = format.parse(sdate);
				 
			}catch(Exception e){
				e.printStackTrace();
			}
			
			SimpleDateFormat  newformat = new SimpleDateFormat("dd-MM-yyyy");  
			try{
				Calendar now = Calendar.getInstance();    
			     now.setTime(strdate);
			     
			     now.add(Calendar.MONTH, 10);
			     String end = now.get(Calendar.DATE)+"-" + (now.get(Calendar.MONTH) + 1) + "-"+ now.get(Calendar.YEAR);
			     enddate = newformat.parse(end);

			}catch(Exception e){
				e.printStackTrace();
			}

			
			List<SqlRow> cattleOutput = (List<SqlRow>) CattleOutput.getAllCattleOutputReportbyMonth(strdate,enddate,breedName,cattleName);
		
			//System.out.println(cattleName);
			//System.out.println(cattleOutput.size());
			
			ArrayList<CattleOutputMonthVM> monthList = new ArrayList<>();
			for(SqlRow u : cattleOutput){
				CattleOutputMonthVM comv = new CattleOutputMonthVM();
				//comv.date = u.getString("last_update_date_time");
				comv.quantity = u.getInteger("quantity");
				comv.lastUpdateDateTime = u.getDate("last_update_date_time");
				comv.cattleId = u.getInteger("cattle_master_cattle_id");
				monthList.add(comv);
				/**/
			}
			
			CattleOutputVMs cfvm = new CattleOutputVMs();
			cfvm.breed = breedName;
			cfvm.CattleName = cattleName;
		    cfvm.cattleId = cattleId;
		    cfvm.cattleData = monthList; 
		    cfvm.M1 = 0;
		    cfvm.M2 = 0;
		    cfvm.M3 = 0;
		    cfvm.M4 = 0;
		    cfvm.M5 = 0;
		    cfvm.M6 = 0;
		    cfvm.M7 = 0;
		    cfvm.M8 = 0;
		    cfvm.M9 = 0;
		    cfvm.M10 = 0;
		    if(!monthList.isEmpty()){
				for(int i=0; i<10; i++) {
					Date starting = strdate;
					Date ending = getEndDate(starting, 1);
					for(CattleOutputMonthVM u : monthList){
						
						if(u.lastUpdateDateTime.before(ending) && u.lastUpdateDateTime.after(starting)){
						switch(i+1){
						   case 1:  cfvm.M1 += u.quantity;
									break;
				           case 2:  cfvm.M2 += u.quantity;
				                    break;
				           case 3:  cfvm.M3 += u.quantity;
				                    break;
				           case 4:  cfvm.M4 += u.quantity;
				                    break;
				           case 5:  cfvm.M5 += u.quantity;
				                    break;
				           case 6:  cfvm.M6 += u.quantity;
				                    break;
				           case 7:  cfvm.M7 += u.quantity;
				                    break;
				           case 8:  cfvm.M8 += u.quantity;
				                    break;
				           case 9:  cfvm.M9 += u.quantity;
				                    break;
				           case 10: cfvm.M10 += u.quantity;
				                    break;
						}
						}
					}
					strdate = ending;
				}
				
				cmvm.add(cfvm);
		    }

		}
		
		
		int count =0;
		//count  = CattleOutput.getAllCattleOutputCount(pageno);
		
		HashMap  <String ,Object> hm = new HashMap();
		hm.put("catersoutput",cmvm);
		hm.put("userCount", count);
		return ok(Json.toJson(hm));
	}
	
	public static Date getEndDate(Date myDate, int month){
		Date strdate = myDate;
		Date enddate = null;
		SimpleDateFormat  newformat = new SimpleDateFormat("dd-MM-yyyy");  
		try{
			Calendar now = Calendar.getInstance();    
		     now.setTime(strdate);
		     
		     now.add(Calendar.MONTH, month);
		     String end = now.get(Calendar.DATE)+"-" + (now.get(Calendar.MONTH) + 1) + "-"+ now.get(Calendar.YEAR);
		     enddate = newformat.parse(end);
		    
		}catch(Exception e){
			e.printStackTrace();
		}
		 return enddate;
	}
	
	public static Result getAllCattleDeliveryReportbyYear(){
		
		JsonNode json = request().body().asJson();
		JsonNode  breed = json.get("breed");
		
		List<SqlRow> sqlRows;
		ArrayList<CattleOutputVMs> cmvm = new ArrayList<>();
		
		if((breed.toString().replace("\"", "")).equalsIgnoreCase("all")){
			
			String sql = "select id, breed_name from breeds";
			sqlRows = Ebean.createSqlQuery(sql).findList();
		}else {
			String sql = "select id, breed_name from breeds where breed_name = :brd";
			sqlRows = Ebean.createSqlQuery(sql).setParameter("brd", (breed.toString().replace("\"", ""))).findList();
		}
		
		for(SqlRow dataRows: sqlRows){
			
			Date enddate = null;
			Date strdate  = null;
			
			String breedName = dataRows.getString("breed_name");
			
			Calendar cal = Calendar.getInstance();
		    int year = cal.get(cal.YEAR);
		    String month = new SimpleDateFormat("MMM").format(cal.getTime());
		    
			String sdate = ("01-"+month+"-"+year).replace("\"", "");
			
			SimpleDateFormat  format = new SimpleDateFormat("dd-MMM-yyyy");  
			try{
				 strdate = format.parse(sdate);
				 
			}catch(Exception e){
				e.printStackTrace();
			}
			
			enddate = getEndDate(strdate, 12);
			
			List<SqlRow> cattleOutput = (List<SqlRow>) CattleOutput.getAllCattleDeliveryReportbyYear(strdate,enddate,breedName);
			
			ArrayList<CattleOutputMonthVM> monthList = new ArrayList<>();
			for(SqlRow u : cattleOutput){
				CattleOutputMonthVM comv = new CattleOutputMonthVM();
				
				comv.cattleId = u.getInteger("cattle_id");
				comv.expectedDelivery = u.getDate("expected_delivery_date");
				comv.cattleName = u.getString("name");
				comv.breed = breedName;
				monthList.add(comv);
			}
			
			CattleOutputVMs cfvm = new CattleOutputVMs();
			cfvm.breed = breedName;
		    cfvm.cattleData = monthList; 
		    cfvm.M1 = 0;
		    
		    cfvm.M2 = 0;
		    cfvm.M3 = 0;
		    cfvm.M4 = 0;
		    cfvm.M5 = 0;
		    cfvm.M6 = 0;
		    cfvm.M7 = 0;
		    cfvm.M8 = 0;
		    cfvm.M9 = 0;
		    cfvm.M10 = 0;
		    cfvm.M11 = 0;
		    cfvm.M12 = 0;
			for(int i=0; i<12; i++) {
				Date starting = strdate;
				Date ending = getEndDate(starting, 1);
				for(CattleOutputMonthVM u : monthList){
					CattleOutputMonthVM covmm = new CattleOutputMonthVM();
					covmm.cattleId = u.cattleId;
					covmm.expectedDelivery = u.expectedDelivery;
					covmm.cattleName = u.cattleName;
					covmm.breed = u.breed;		
					if(u.expectedDelivery.before(ending) && u.expectedDelivery.after(starting)){
					switch(i+1){
					   case 1:  cfvm.M1++;
					   			cfvm.cattleDataM1.add(covmm);
								break;
					   case 2:  cfvm.M2++;
								cfvm.cattleDataM2.add(covmm);
			                    break;
			           case 3:  cfvm.M3++;
			           			cfvm.cattleDataM3.add(covmm);
			                    break;
			           case 4:  cfvm.M4++;
			           			cfvm.cattleDataM4.add(covmm);
			                    break;
			           case 5:  cfvm.M5++;
			           			cfvm.cattleDataM5.add(covmm);
			                    break;
			           case 6:  cfvm.M6++;
			           			cfvm.cattleDataM6.add(covmm);
			                    break;
			           case 7:  cfvm.M7++;
			           			cfvm.cattleDataM7.add(covmm);
			                    break;
			           case 8:  cfvm.M8++;
			           			cfvm.cattleDataM8.add(covmm);
			                    break;
			           case 9:  cfvm.M9++;
			           			cfvm.cattleDataM9.add(covmm);
			                    break;
			           case 10: cfvm.M10++;
			           			cfvm.cattleDataM10.add(covmm);
			                    break;
			           case 11: cfvm.M11++;
			           			cfvm.cattleDataM11.add(covmm);
	                    		break;
			           case 12: cfvm.M12++;
			           			cfvm.cattleDataM12.add(covmm);
	                    		break;
					}
					}
				}
				strdate = ending;
			}
			
			cmvm.add(cfvm);
		}
		
		
		int count =0;
		//count  = CattleOutput.getAllCattleOutputCount(pageno);
		
		HashMap  <String ,Object> hm = new HashMap();
		hm.put("catersoutput",cmvm);
		hm.put("userCount", count);
		return ok(Json.toJson(hm));
		
		}
	
	
public static Result getMonthlyCattleOutputReport(){
		
		JsonNode json = request().body().asJson();
		JsonNode month = json.get("month");
		JsonNode  breed = json.get("breed");
		
		String sql1 = "select cycle_days_between_insemination, daysby_when_calvs_canbe_inseminated from kpiname";
		SqlRow sqlRows1 = Ebean.createSqlQuery(sql1).findUnique();
		int deliveryDateData = sqlRows1.getInteger("cycle_days_between_insemination");
		int birthDateData = sqlRows1.getInteger("daysby_when_calvs_canbe_inseminated");
		
		List<SqlRow> sqlRows;
		ArrayList<CattleOutputVMs> cmvm = new ArrayList<>();
		
		if((breed.toString().replace("\"", "")).equalsIgnoreCase("all")){
			
			String sql = "select cattle_id, name, breed from cattle_master";
			sqlRows = Ebean.createSqlQuery(sql).findList();
		}else {
			String sql = "select cattle_id, name, breed from cattle_master where breed = :brd";
			sqlRows = Ebean.createSqlQuery(sql).setParameter("brd", (breed.toString().replace("\"", ""))).findList();
		}
		
		for(SqlRow dataRows: sqlRows){
			//Date enddate = null;
			Date strdate  = null;
			
			String cattleName = dataRows.getString("name");
			//int cattleId = dataRows.getInteger("cattle_id");
			String breedName = dataRows.getString("breed");
			
			int year = 0;
			String month1 = null;
			Calendar cal = Calendar.getInstance();
			
			if((month.toString().replace("\"", "")).equalsIgnoreCase("M1")){
			    year = cal.get(Calendar.YEAR);
			    month1 = new SimpleDateFormat("MMM").format(cal.getTime());
			}else if((month.toString().replace("\"", "")).equalsIgnoreCase("M2")){
				cal.add(Calendar.MONTH ,+1);
				year = cal.get(Calendar.YEAR);
			    month1 = new SimpleDateFormat("MMM").format(cal.getTime());
			}
			
			String sdate = ("01-"+month1+"-"+year).replace("\"", "");
			
			SimpleDateFormat  format = new SimpleDateFormat("dd-MMM-yyyy");  
			try{
				 strdate = format.parse(sdate);
				 
			}catch(Exception e){
				e.printStackTrace();
			}
			
			int mon = deliveryDateData/30;
			Date firstDeliveryDate = strdate;
			Date lastDeliveryDate = getEndDate(firstDeliveryDate, -(mon+2));
			
			int mon1 = birthDateData/30;
			Date firstBirthDate = getEndDate(strdate, (-mon1));
			Date lastBirthDate = getEndDate(firstBirthDate, -2);
			
			List<SqlRow> cattleOutput = (List<SqlRow>) CattleOutput.getMonthlyCattleOutputReport(firstDeliveryDate,lastDeliveryDate,breedName,cattleName,firstBirthDate,lastBirthDate);
		
			ArrayList<CattleOutputMonthVM> monthList = new ArrayList<>();
			for(SqlRow u : cattleOutput){
				CattleOutputMonthVM comv = new CattleOutputMonthVM();
				/*//comv.date = u.getString("last_update_date_time");
				comv.quantity = u.getInteger("quantity");
				comv.lastUpdateDateTime = u.getDate("last_update_date_time");
				comv.cattleId = u.getInteger("cattle_master_cattle_id");*/
				comv.cattleName = u.getString("name");
				comv.lastDelivery = u.getDate("last_delivery");
				//comv.birthDate = u.getDate("dateof_birth");
				monthList.add(comv);
				
			}
			
			CattleOutputVMs cfvm = new CattleOutputVMs();
			cfvm.breed = breedName;
			cfvm.CattleName = cattleName;
		    //cfvm.cattleId = cattleId;
		    cfvm.cattleData = monthList; 
		    cfvm.M1 = 0;
		    cfvm.M2 = 0;
		    
			
			
			cmvm.add(cfvm);
		}
		
		
		int count =0;
		//count  = CattleOutput.getAllCattleOutputCount(pageno);
		
		HashMap  <String ,Object> hm = new HashMap<String, Object>();
		hm.put("catersoutput",cmvm);
		hm.put("userCount", count);
		return ok(Json.toJson(hm));
	}

	
	public static Result getAllCattleOutputReport(int pageno){
		List<CattleOutputVMs> cattleOutput = CattleOutput.getAllCattleOutputReport(pageno,10);
		List<CattleOutputVMs>cattleMasterVMs = CattleOutput.getAllCattleOutputReportBreed();
		//System.out.println(cattleOutput.size());
		//System.out.println(cattleMasterVMs.size());
		
		int count =0;
		List<CattleOutputVMs> CattleOutputVMss = new ArrayList<>();
		for(CattleOutputVMs co :cattleOutput){
			CattleOutputVMs c = new CattleOutputVMs();
			c.breed = co.CattleName;
			for(CattleOutputVMs cos :cattleMasterVMs){
				if(cos.breed.equalsIgnoreCase(co.CattleName)){
					CattleOutputVMs cc = new CattleOutputVMs();
					c.actualMilkQuantity += cos.actualMilkQuantity;
					//c.CattleName += cos.CattleName;
					c.expectedMilkQuantity += cos.expectedMilkQuantity;
					//CattleOutputVMss.add(cc);
				}
			}
			
			CattleOutputVMss.add(c);
			for(CattleOutputVMs cos :cattleMasterVMs){
				if(cos.breed.equalsIgnoreCase(co.CattleName)){
					CattleOutputVMs cc = new CattleOutputVMs();
					cc.actualMilkQuantity = cos.actualMilkQuantity;
					cc.CattleName = cos.CattleName;
					cc.expectedMilkQuantity = cos.expectedMilkQuantity;
					CattleOutputVMss.add(cc);
				}
			}
		}
		
		count  = CattleOutput.getAllCattleOutputCount(pageno);
		HashMap  <String ,Object> hm = new HashMap();
		hm.put("catersoutput",CattleOutputVMss);
		hm.put("userCount", count);
		return ok(Json.toJson(hm));
	}
	
	
	
	public static Result getAllCattleHealth(int pageno){
		List<CattleHealth> cattleHealth = CattleHealth.getAllCattleOutput(pageno,10);
        int count  = 0;
        count  = CattleHealth.getAllCattleHealthCount(pageno);
		ArrayList<CattleHealthVM> cmvm = new ArrayList<>();
		
		for(CattleHealth u : cattleHealth){
			CattleHealthVM cfvm = new CattleHealthVM();
			cfvm.duedate = u.duedate;
			cfvm.lastDelivaerydate = u.lastDelivaerydate;
			cfvm.medicationEnddate = u.medicationEnddate;
			cfvm.medicationStartDate  = u.medicationStartDate;
			cfvm.medicationName = u.medicationName;
			cfvm.medicationType = u.medicationType;
			cfvm.pregnancyDate = u.pregnancyDate;
			cfvm.pregnant = u.pregnant;
			cfvm.lastUpdateDateTime =  u.lastUpdateDateTime;
			cfvm.users = u.users;
			cfvm.oraganization = u.oraganization;
			cfvm.cattleId = u.cattleId;
			cmvm.add(cfvm);
		}

		HashMap  <String ,Object> hm = new HashMap();
		hm.put("catershealth",cmvm);
		return ok(Json.toJson(hm));
	}
	
	
	public static Result SignIn() {
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		String uname = dynamicForm.get("username");
		String pass = dynamicForm.get("password");
			
		Date d  = new Date();
		System.out.println(d);
		
		 Date curDate = new Date();
		 
		 SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		 String DateToStr = format.format(curDate);
		 System.out.println(DateToStr);
		 DateToStr = DateToStr.replaceAll("/", "");
		 System.out.println(DateToStr);
		 
		 int key =  getencryptKey(Integer.parseInt(DateToStr));
		 System.out.println("key :" +key);
		 
		 int getKeyfromfile  = getLicencekeyformFile();
		System.out.println("getKeyfromfile: "+getKeyfromfile);
		 
		 Users ud = Users.isUser(uname, pass);
			if (ud != null) {
				session().clear();
				session().put("email", ud.userId);
			    ud.lastlogin = new Date();
			    ud.update();
			    return redirect("/dashboard#/allUsers");
			}

			else {
				flash().put("error", "Invalid  credentials ");
				return ok(signin.render());
			}

	}
	
	private static int getLicencekeyformFile() {
		// TODO Auto-generated method stub
		String sCurrentLine = new String();
		String key  = new String();
		    	  //Specify the file name and path here
		    	  File f_ = new File(Play.application().path().getAbsolutePath() + "/public/encrypt/encryptedfile.txt");
		    	  
			       //BufferedReader br = null;
					try {
					
						FileInputStream fis = new FileInputStream(f_);
						 
						//Construct BufferedReader from InputStreamReader
						BufferedReader br = new BufferedReader(new InputStreamReader(fis));
					 
						String line = null;
						while ((line = br.readLine()) != null) {
						
							if(line.contains("Encrypt")){
								String keys[] = line.split(" ");
								System.out.println("Keys Length : "+keys.length);
								for(String s:keys){
									System.out.println("S key from file: "+s);
									key = keys[2];
								}
							}
						}
					 
						br.close();

					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						
					}
			System.out.println("key:" +key);
			key = "8989";
					 return Integer.parseInt(key);
	}

	private static int getencryptKey(int parseInt) {
		// TODO Auto-generated method stub
		
		int firstDigit, secondDigit, thirdDigit, fourthDigit,fifthDigit,sixthDigit,  temp;
		int number = parseInt;
		
		firstDigit = number / 1000; 
		secondDigit = number / 100 % 10; 
		thirdDigit = number / 10 % 10; 
		fourthDigit = number % 10;   
		firstDigit = (firstDigit + 7) % 10; 
		secondDigit = (secondDigit + 7) % 10; 
		thirdDigit = (thirdDigit + 7) % 10; 
		fourthDigit = (fourthDigit + 7) % 10;   
		temp = firstDigit; 
		firstDigit = thirdDigit; 
		thirdDigit = temp;   
		temp = secondDigit; 
		secondDigit = fourthDigit; 
		fourthDigit = temp;   

		return Integer.parseInt(firstDigit +""+secondDigit+""+ thirdDigit+""+ fourthDigit);	
		
	}



	public static Result updateUserProfileByAdmin(){
		JsonNode json = request().body().asJson();
		JsonNode userJson = json.get("user");
		System.out.println(json);
		
		//JsonNode userDet = json.path("userInfo");
		ObjectMapper userinfoMapper = new ObjectMapper();
        
		try {
			UserVM uvm = userinfoMapper.readValue(userJson.traverse(),UserVM.class);
			Users u = Users.getUserByEmail(uvm.userId);
			if(u !=  null){
				List<Permissions> permissionsList = Permissions
						.getPermissionsByUserEmail(uvm.userId);
				if (permissionsList != null) {
					for(Permissions pdtails : permissionsList){
						pdtails.delete();
					}
				}
				
				JsonNode permissionJson = json.path("permList");
				JsonNode org = json.path("org");
				System.out.println(org);
				if(org.toString() != ""){
					Oraganization    orgni = Oraganization.getOrgById(Integer.parseInt(org.toString().replace("\"", "")));
					if(orgni != null){
						u.setOraganization(orgni);
					}
					
				}
				if(json.path("permList")!= null && json.path("permList").size() != 0){
					 permissionJson = json.path("permList");
					if(permissionJson != null){
						ArrayNode perms = (ArrayNode) permissionJson;
						for (int j = 0; j < perms.size(); j++) {
							String position = perms.get(j).asText();
						    Permissions p = new Permissions();
						    p.access = "W";
						    p.permissionName = position;
						    p.users = Users.getUserByEmail(uvm.userId);
						    p.save();
						    u.permissions.add(p);
						}
					}
				}
				

				u.setFirstname(uvm.firstname);
				u.lastname =   uvm.lastname;
				u.setUserId(uvm.userId);
				u.setPassword(uvm.password);
				u.userType = "";
				
				u.setLastUpdatedatetime(new Date());
				u.update();
			}else{
				
				u  = new Users();
				u.firstname = uvm.firstname;
				u.lastname = uvm.lastname;
				u.userId = uvm.userId;
				u.setPassword(uvm.password);
				u.userType = "";
				//u.oraganization = (uvm.oraganization) ;
				u.setLastUpdatedatetime(new Date());
				u.save();
				
				JsonNode org = json.path("org");
				System.out.println(org);
				if(org.toString() != ""){
					Oraganization    orgni = Oraganization.getOrgById(Integer.parseInt(org.toString().replace("\"", "")));
					if(orgni != null){
						u.setOraganization(orgni);
					}
				}
				//u.oraganization.update(org);
				
				JsonNode permissionJson = json.path("permList");
				if(permissionJson != null){
					ArrayNode perms = (ArrayNode) permissionJson;
					for (int j = 0; j < perms.size(); j++) {
						String position = perms.get(j).asText();
					    Permissions p = new Permissions();
					    p.access = "W";
					    p.permissionName = position;
					    p.users = Users.getUserByEmail(uvm.userId);
					    p.save();
					    
					    if(p != null){
					    	 u.permissions.add(p);
					    }
				u.update();
			}
				}
			}
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return ok("");
	}
	
	
	public static Result updateCattleProfileByAdmin(){
		JsonNode json = request().body().asJson();
		JsonNode catJson = json.get("cat");
		System.out.println(json);
		ObjectMapper userinfoMapper = new ObjectMapper();
		try {
			CattleMasterVM uvm = userinfoMapper.readValue(catJson.traverse(),CattleMasterVM.class);
			CattleMaster u = CattleMaster.getUserByCattleId(uvm.cattleId);
			if(u !=  null){
				JsonNode org = json.path("org");
				System.out.println(org);
				if(org.toString() != ""){
					Oraganization    orgni = Oraganization.getOrgById(Integer.parseInt(org.toString().replace("\"", "")));
					if(orgni != null){
						u.setOraganization(orgni);
					}
				}
				JsonNode userId = json.path("userId");
				System.out.println(userId);
				if(userId.toString() != ""){
					Users    uId = null;	
					uId = Users.getUserByEmail(userId.toString().replace("\"", ""));
					if(uId != null){
						u.setUsers(uId);
					}
					
				}
				if(json.path("parentId") != null){
					JsonNode parentId = json.path("parentId");
					System.out.println(parentId);
					if(parentId.toString() != ""){
						CattleMaster    uId = null;	
						uId = CattleMaster.getUserByCattleId(Integer.parseInt(parentId.toString().replace("\"", "")));
						if(uId != null){
							u.setParentId(uId.cattleId);
						}
					}
				}
				
				if(uvm.breed != null){
					if(uvm.breed.matches("[\\d+]")){
						Breeds b = Breeds.getBreedsById(uvm.breed);
						u.setBreed(b.breedName);
					}else{
						u.setBreed(uvm.breed);
					}
					
				}else{
					//Breeds b = Breeds.getBreedsById(uvm.breed);
					u.setBreed("");
				}
				
				if(uvm.subbreed != null){
					if(uvm.subbreed.matches("[\\d+]")){
						 List<Subbreed> subreeds = Subbreed.getSunBreedById(uvm.subbreed);	
						 u.setSubBreed(subreeds.get(0).sub_breed);
					}else{
						 u.setSubBreed(uvm.subbreed);
					}
				}else{
					 u.setSubBreed("");
				}
				
			   
				u.setCattleIdentificationMark(uvm.cattleIdentificationMark);
				u.setGender(uvm.gender);
				u.setLastUpdateDateTime(new Date());
				u.setName(uvm.name);
				
				SimpleDateFormat format1 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d2 = null;
				try {
					if(uvm.dateofBirthVM !=null){
						if(uvm.dateofBirthVM.contains("\"")){
							d2 = format1.parse(uvm.dateofBirthVM.replaceAll("\"", ""));
						}else{
							d2 = format1.parse(uvm.dateofBirthVM);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setDateofBirth(d2);
				
				u.setIsPregnant(uvm.isPregnant);
				
				// Died on date
				SimpleDateFormat format2 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date diedOnDate = null;
				try {
					if(uvm.diedonDateVM !=null){
						if(uvm.diedonDateVM.contains("\"")){
							diedOnDate = format1.parse(uvm.diedonDateVM.replaceAll("\"", ""));
						}else{
							diedOnDate = format1.parse(uvm.diedonDateVM);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setDiedonDate(diedOnDate);
				
				u.setIsPregnant(uvm.isPregnant);
				
				
				SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d1 = null;
				try {
					if(uvm.lastDeliveryVM !=null){
						if(uvm.lastDeliveryVM.contains("\"")){
							d1 = format.parse(uvm.lastDeliveryVM.replaceAll("\"", ""));
						}else{
							d1 = format.parse(uvm.lastDeliveryVM);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				u.setLastDelivery(d1);
				u.update(u);

			}else{
				
				u  = new CattleMaster();

				if(uvm.breed != null){
					Breeds b = Breeds.getBreedsById(uvm.breed);
					u.setBreed(b.breedName);
				}else{
					//Breeds b = Breeds.getBreedsById(uvm.breed);
					u.setBreed("");
				}
				
				if(uvm.subbreed != null){
					 List<Subbreed> subreeds = Subbreed.getSunBreedById(uvm.subbreed);	
					 u.setSubBreed(subreeds.get(0).sub_breed);
				}else{
					 u.setSubBreed("");
				}
				
				u.setCattleIdentificationMark(uvm.cattleIdentificationMark);
				u.setGender(uvm.gender);
				u.setLastUpdateDateTime(new Date());
				u.setName(uvm.name);
				
				SimpleDateFormat format1 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d2 = null;
				try {
					if(uvm.dateofBirthVM !=null){
						if(uvm.dateofBirthVM.contains("\"")){
							d2 = format1.parse(uvm.dateofBirthVM.replaceAll("\"", ""));
						}else{
							d2 = format1.parse(uvm.dateofBirthVM);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setDateofBirth(d2);
				
				u.setIsPregnant(uvm.isPregnant);
				
				SimpleDateFormat format2 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d3 = null;
				try {
					if(uvm.diedonDateVM !=null){
						if(uvm.diedonDateVM.contains("\"")){
							d3 = format2.parse(uvm.diedonDateVM.replaceAll("\"", ""));
						}else{
							d3 = format1.parse(uvm.diedonDateVM);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setDiedonDate(d3);
				
				u.setIsPregnant(uvm.isPregnant);
				
				SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d1 = null;
				try {
					if(uvm.lastDeliveryVM !=null){
						if(uvm.lastDeliveryVM.contains("\"")){
							d1 = format.parse(uvm.lastDeliveryVM.replaceAll("\"", ""));
						}else{
							d1 = format.parse(uvm.lastDeliveryVM);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				u.setLastDelivery(d1);
				//u.setLastDelivery(uvm.lastDelivery);
				
				u.save();


				
				JsonNode org = json.path("org");
				System.out.println(org);
				if(org.toString() != ""){
					Oraganization    orgni = Oraganization.getOrgById(Integer.parseInt(org.toString().replace("\"", "")));
					if(orgni != null){
						u.setOraganization(orgni);
					}
					
				}
				
				JsonNode userId = json.path("userId");
				System.out.println(userId);
				if (userId.toString() != "") {
					Users uId = null;
					uId = Users.getUserByEmail(userId.toString().replace("\"",
							""));
					if (uId != null) {
						u.setUsers(uId);
					}
					
				}
				
				if(json.path("parentId") != null){
					JsonNode parentId = json.path("parentId");
					System.out.println(parentId);
					if(parentId.toString() != ""){
						CattleMaster    uId = null;	
						uId = CattleMaster.getUserByCattleId(Integer.parseInt(parentId.toString().replace("\"", "")));
						if(uId != null){
							u.setParentId(uId.cattleId);
						}
					}
				}
				
				u.update(u);
			}
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return ok("");
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static Result updateFeedProfileByAdmin(){
		
		JsonNode json = request().body().asJson();
		JsonNode feedJson = json.get("feed");
		System.out.println(feedJson);
		System.out.println(json);
		ObjectMapper userinfoMapper = new ObjectMapper();
		try {
			FeedMasterVM uvm = userinfoMapper.readValue(feedJson.traverse(),FeedMasterVM.class);
			FeedMaster u = FeedMaster.getUserByfeedId(uvm.feedId);
			if(u !=  null){
				
				JsonNode org = json.path("org");
				System.out.println(org);
				if(org.toString() != ""){
					Oraganization    orgni = Oraganization.getOrgById(Integer.parseInt(org.toString().replace("\"", "")));
					if(orgni != null){
						u.setOraganization(orgni);
					}
				}
				
				JsonNode userId = json.path("userId");
				System.out.println(userId);
				if(userId.toString() != ""){
					Users    uId = null;	
					uId = Users.getUserByEmail(userId.toString().replace("\"", ""));
					if(uId != null){
						u.setUsers(uId);
					}
					
				}
				
				u.setFeedFiber(uvm.feedFiber);
				u.setFeedName(uvm.feedName);
				u.setFeedProtine(uvm.feedProtine);
				u.setFeedVitamins(uvm.feedVitamins);
				u.setFeedWaterContent(uvm.feedWaterContent);
				u.setQuantityofFiber(uvm.quantityofFiber);
				u.setQuantityofProtine(uvm.quantityofProtine);
				u.setQuantityofVitamins(uvm.quantityofVitamins);
				u.setQuantityofWater(uvm.quantityofWater);
				u.lastUpdateDateTime = new Date();
				//u.skuId = uvm.skuId;
				u.setSkuId(uvm.skuId);
				u.update(u);

			}else{
				
				u  = new FeedMaster();
				u.setFeedFiber(uvm.feedFiber);
				u.setFeedName(uvm.feedName);
				u.setFeedProtine(uvm.feedProtine);
				u.setFeedVitamins(uvm.feedVitamins);
				u.setFeedWaterContent(uvm.feedWaterContent);
				u.setQuantityofFiber(uvm.quantityofFiber);
				u.setQuantityofProtine(uvm.quantityofProtine);
				u.setQuantityofVitamins(uvm.quantityofVitamins);
				u.setQuantityofWater(uvm.quantityofWater);
				u.lastUpdateDateTime = new Date();
				//u.setLastDelivery(uvm.lastDelivery);
				u.setSkuId(uvm.skuId);
				u.save();

				JsonNode org = json.path("org");
				System.out.println(org);
				if(org.toString() != ""){
					Oraganization    orgni = Oraganization.getOrgById(Integer.parseInt(org.toString().replace("\"", "")));
					if(orgni != null){
						u.setOraganization(orgni);
					}
					
				}
				
				JsonNode userId = json.path("userId");
				System.out.println(userId);
				if (userId.toString() != "") {
					Users uId = null;
					uId = Users.getUserByEmail(userId.toString().replace("\"",
							""));
					if (uId != null) {
						u.setUsers(uId);
					}
					
				}
				
				u.update(u);
			}
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return ok("");
	}
	
	
	public  static Result  getuserId(){
		String userId = session().get("email");
		return ok(userId);
	}
	
	public static Result updateCattlePregnancyProfileByAdmin(){
		JsonNode json = request().body().asJson();
		JsonNode catJson = json.get("cat");
		System.out.println(json);
		ObjectMapper userinfoMapper = new ObjectMapper();
		try {
			PregnancyCattleVM uvm = userinfoMapper.readValue(catJson.traverse(),PregnancyCattleVM.class);
			PregnantCattle u = PregnantCattle.getPregnantCattleByCattleId(uvm.pregnancyId);
			if(u !=  null){
				u.setActualPregnancyDate(uvm.actualPregnancyDate);
				u.setCattleId(uvm.cattleId);
				
				SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d1 = null;
				//System.out.println(" dfgfd gdfgd fgf"+uvm.expectedDeliveryDate);
				try {
					
				if(uvm.expectedDeliveryDateVM !=null){
					if(uvm.expectedDeliveryDateVM.contains("\"")){
						d1 = format.parse(uvm.expectedDeliveryDateVM.replaceAll("\"", ""));
					}else{
						d1 = format.parse(uvm.expectedDeliveryDateVM);
					}
					
				}	
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//System.out.println("d1" +d1);
				
				u.setExpectedDeliveryDate(d1);
				u.setFirstInseminationDate(uvm.firstInseminationDate);
				u.setSecondInseminationDate(uvm.secondInseminationDate);
				u.setLastDeliveryDate(uvm.lastDeliveryDate);
				u.setMilkingStoppingDate(uvm.milkingStoppingDate);
				u.setPregnancyId(uvm.pregnancyId);
				u.setSecondInseminationDate(uvm.secondInseminationDate);
				u.setThirdInseminationDate(uvm.thirdInseminationDate);
			
				u.update(u);

			}else{
				
				u  = new PregnantCattle();
				u.setActualPregnancyDate(uvm.actualPregnancyDate);
				u.setCattleId(uvm.cattleId);
				
				
				
				SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d1 = null;
				//System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+uvm.expectedDeliveryDate);
				try {
					
					if(uvm.expectedDeliveryDateVM !=null){
						if(uvm.expectedDeliveryDateVM.contains("\"")){
							d1 = format.parse(uvm.expectedDeliveryDateVM.replaceAll("\"", ""));
						}else{
							d1 = format.parse(uvm.expectedDeliveryDateVM);
						}
						
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa" +d1);
				u.setExpectedDeliveryDate(d1);
				
			     //u.setExpectedDeliveryDate(uvm.expectedDeliveryDate);
				u.setFirstInseminationDate(uvm.firstInseminationDate);
				u.setSecondInseminationDate(uvm.secondInseminationDate);
				u.setLastDeliveryDate(uvm.lastDeliveryDate);
				u.setMilkingStoppingDate(uvm.milkingStoppingDate);
				u.setSecondInseminationDate(uvm.secondInseminationDate);
				u.setThirdInseminationDate(uvm.thirdInseminationDate);
				u.save();

				
			}
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return ok("");
	}
	
	
	
	public static Result updateCattleChildProfileByAdmin(){
		JsonNode json = request().body().asJson();
		JsonNode catJson = json.get("cat");
		System.out.println(json);
		ObjectMapper userinfoMapper = new ObjectMapper();
		try {
			ChildCattleVM uvm = userinfoMapper.readValue(catJson.traverse(),ChildCattleVM.class);
			ChildCattle u = ChildCattle.getUserByCattleId(uvm.cattlechildId);
			if(u !=  null){
				JsonNode org = json.path("org");
				System.out.println(org);
				if(org.toString() != ""){
					Oraganization    orgni = Oraganization.getOrgById(Integer.parseInt(org.toString().replace("\"", "")));
					if(orgni != null){
						u.setOraganization(orgni);
					}
				}
				
				JsonNode userId = json.path("userId");
				System.out.println(userId);
				if(userId.toString() != ""){
					Users    uId = null;	
					uId = Users.getUserByEmail(userId.toString().replace("\"", ""));
					if(uId != null){
						u.setUsers(uId);
					}
					
				}
				
				JsonNode parentId = json.path("parentId");
				System.out.println(parentId);
				if(parentId.toString() != ""){
					CattleMaster    uId = null;	
					uId = CattleMaster.getUserByCattleId(Integer.parseInt(parentId.toString().replace("\"", "")));
					if(uId != null){
						u.setCattleMaster(uId);
					}
				}
				
				u.setBreed(uvm.breed);
				u.setCattleIdentificationMark(uvm.cattleIdentificationMark);
				u.setGender(uvm.gender);
				u.setLastUpdateDateTime(new Date());
				u.setName(uvm.name);
				u.setDateofBirth(uvm.dateofBirth);
				u.update(u);

			}else{
				
				u  = new ChildCattle();

				u.setBreed(uvm.breed);
				u.setCattleIdentificationMark(uvm.cattleIdentificationMark);
				u.setGender(uvm.gender);
				u.setLastUpdateDateTime(new Date());
				u.setName(uvm.name);
				u.save();

				JsonNode org = json.path("org");
				System.out.println(org);
				if(org.toString() != ""){
					Oraganization    orgni = Oraganization.getOrgById(Integer.parseInt(org.toString().replace("\"", "")));
					if(orgni != null){
						u.setOraganization(orgni);
					}
					
				}
				
				JsonNode userId = json.path("userId");
				System.out.println(userId);
				if(userId.toString() != ""){
					Users    uId = null;
				    uId = Users.getUserByEmail(userId.toString().replace("\"", ""));
					if(uId != null){
						u.setUsers(uId);
					}
					
				}
				
				JsonNode parentId = json.path("parentId");
				System.out.println(parentId);
				if(parentId.toString() != ""){
					CattleMaster    uId = null;	
					uId = CattleMaster.getUserByCattleId(Integer.parseInt(parentId.toString().replace("\"", "")));
					if(uId != null){
						u.setCattleMaster(uId);
					}
				}

				u.setDateofBirth(uvm.dateofBirth);

				u.update(u);
			}
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return ok("");
	}
	
	public static Result updateCattleHealthProfileByAdmin(){
		JsonNode json = request().body().asJson();
		
		JsonNode catJson = json.get("cat");
		ObjectMapper userinfoMapper = new ObjectMapper();
		try {
			CattleHealthVM uvm = userinfoMapper.readValue(catJson.traverse(),CattleHealthVM.class);
			CattleHealth u = CattleHealth.getUserByCattleId(uvm.cattleId);
			if(u !=  null){
				JsonNode org = json.path("org");
				if(org.toString() != ""){
					Oraganization    orgni = Oraganization.getOrgById(Integer.parseInt(org.toString().replace("\"", "")));
					if(orgni != null){
						u.setOraganization(orgni);
					}
				}
				JsonNode userId = json.path("userId");
				System.out.println(userId);
				if(userId.toString() != ""){
					Users    uId = null;	
					uId = Users.getUserByEmail(userId.toString().replace("\"", ""));
					if(uId != null){
						u.setUsers(uId);
					}
					
				}
				
				u.setDuedate(uvm.duedate);
				u.setLastDelivaerydate(uvm.lastDelivaerydate);
				u.setMedicationEnddate(uvm.medicationEnddate);
				u.setMedicationName(uvm.medicationName);
				u.setMedicationStartDate(uvm.medicationStartDate);
				u.setMedicationType(uvm.medicationType);
				u.setPregnancyDate(uvm.pregnancyDate);
				u.setPregnant(uvm.pregnant);
				
				u.setLastUpdateDateTime(new Date());
				
				u.update(u);

			}else{
				
				u  = new CattleHealth();
				u.setDuedate(uvm.duedate);
				u.setLastDelivaerydate(uvm.lastDelivaerydate);
				u.setMedicationEnddate(uvm.medicationEnddate);
				u.setMedicationName(uvm.medicationName);
				u.setMedicationStartDate(uvm.medicationStartDate);
				u.setMedicationType(uvm.medicationType);
				u.setPregnancyDate(uvm.pregnancyDate);
				u.setPregnant(uvm.pregnant);
				
				u.setLastUpdateDateTime(new Date());
				u.save();

				JsonNode org = json.path("org");
				System.out.println(org);
				if(org.toString() != ""){
					Oraganization    orgni = Oraganization.getOrgById(Integer.parseInt(org.toString().replace("\"", "")));
					if(orgni != null){
						u.setOraganization(orgni);
					}
					
				}
				
				JsonNode userId = json.path("userId");
				System.out.println(userId);
				if(userId.toString() != ""){
					Users    uId = null;
				    uId = Users.getUserByEmail(userId.toString().replace("\"", ""));
					if(uId != null){
						u.setUsers(uId);
					}
				}
			u.update(u);
			}
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return ok("");
	}
	
	
	
	public static Result updateCattleOutputProfileByAdmin(){
		JsonNode json = request().body().asJson();
		System.out.println("com.mnt.dairymgnt.controllers.Application.");
		
		JsonNode catJson = json.get("cat");
		System.out.println(json);
		ObjectMapper userinfoMapper = new ObjectMapper();
		try {
			CattleOutputVM uvm = userinfoMapper.readValue(catJson.traverse(),CattleOutputVM.class);
			CattleOutput u = CattleOutput.getUserByCattleId(uvm.cattleId);
			if(u !=  null){
				JsonNode org = json.path("org");
				System.out.println(org);
				if(org.toString() != ""){
					Oraganization    orgni = Oraganization.getOrgById(Integer.parseInt(org.toString().replace("\"", "")));
					if(orgni != null){
						u.setOraganization(orgni);
					}
				}
				JsonNode userId = json.path("userId");
				System.out.println(userId);
				if(userId.toString() != ""){
					Users    uId = null;	
					uId = Users.getUserByEmail(userId.toString().replace("\"", ""));
					if(uId != null){
						u.setUsers(uId);
					}
					
				}
			
				JsonNode catersIds = json.path("catersIds");
				System.out.println(catersIds);
				if(catersIds.toString() != ""){
					CattleMaster    uId = null;
				    uId = CattleMaster.getUserByCattleId(Integer.parseInt(catersIds.toString().replace("\"", "")));
					if(uId != null){
						u.setCattleMaster(uId);
					}
				}

				JsonNode pregIds = json.path("pregIds");
				System.out.println(pregIds);
				if(pregIds.toString() != ""){
					PregnantCattle    uId = null;
				    uId = PregnantCattle.getPregnantCattleByCattleId((Integer.parseInt(pregIds.toString().replace("\"", ""))));
					if(uId != null){
						u.setPregnantCattle(uId);
					}
				}
				
				u.setDate(uvm.date);
				u.setFatContent(uvm.fatContent);
				u.setSNFContent(uvm.SNFContent);
				u.setDeviceID(uvm.deviceID);
				u.setLastUpdateDateTime(new Date());
				u.setQuantity(uvm.quantity);
				u.setExpectedMilkQuantity(uvm.expectedMilkQuantity);
				
				u.update(u);

			}else{
				
				u  = new CattleOutput();
				u.setDate(uvm.date);
				u.setFatContent(uvm.fatContent);
				u.setSNFContent(uvm.SNFContent);
				u.setDeviceID(uvm.deviceID);
				u.setLastUpdateDateTime(new Date());
				u.setQuantity(uvm.quantity);
				u.setExpectedMilkQuantity(uvm.expectedMilkQuantity);
				u.save();

				JsonNode pregIds = json.path("pregIds");
				System.out.println(pregIds);
				if(pregIds.toString() != ""){
					PregnantCattle    uId = null;
				    uId = PregnantCattle.getPregnantCattleByCattleId((Integer.parseInt(pregIds.toString().replace("\"", ""))));
					if(uId != null){
						u.setPregnantCattle(uId);
					}
				}
				
				JsonNode org = json.path("org");
				System.out.println(org);
				if(org.toString() != ""){
					Oraganization    orgni = Oraganization.getOrgById(Integer.parseInt(org.toString().replace("\"", "")));
					if(orgni != null){
						u.setOraganization(orgni);
					}
					
				}
				
				JsonNode userId = json.path("userId");
				System.out.println(userId);
				if(userId.toString() != ""){
					Users    uId = null;
				    uId = Users.getUserByEmail(userId.toString().replace("\"", ""));
					if(uId != null){
						u.setUsers(uId);
					}
					
				}
				
				JsonNode catersIds = json.path("catersIds");
				System.out.println(catersIds);
				if(catersIds.toString() != ""){
					CattleMaster    uId = null;
				    uId = CattleMaster.getUserByCattleId(Integer.parseInt(catersIds.toString().replace("\"", "")));
					if(uId != null){
						u.setCattleMaster(uId);
					}
				}
			u.update(u);

			}
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return ok("");
	}
	
	
	
	
	
	public static Result updatefeedCattleProfileByAdmin(){
		JsonNode json = request().body().asJson();
		System.out.println("com.mnt.dairymgnt.controllers.Application.");
		
		JsonNode catJson = json.get("cat");
		System.out.println(json);
		ObjectMapper userinfoMapper = new ObjectMapper();
		try {
			CattleFeedMasterVM uvm = userinfoMapper.readValue(catJson.traverse(),CattleFeedMasterVM.class);
			CattleFeedMaster u = CattleFeedMaster.getUserByfeedId(uvm.feedId);
			if(u !=  null){
				JsonNode org = json.path("org");
				System.out.println(org);
				if(org.toString() != ""){
					Oraganization    orgni = Oraganization.getOrgById(Integer.parseInt(org.toString().replace("\"", "")));
					if(orgni != null){
						u.setOraganization(orgni);
					}
				}
				JsonNode userId = json.path("userId");
				System.out.println(userId);
				if(userId.toString() != ""){
					Users    uId = null;	
					uId = Users.getUserByEmail(userId.toString().replace("\"", ""));
					if(uId != null){
						u.setUsers(uId);
					}
					
				}
				
				//u  = new CattleFeedMaster();
                u.setQuantity(uvm.quantity);
				u.setFeedName(uvm.feedName);
				u.setFeedType(uvm.feedType);
				if(uvm.Breed.matches("\\d+")){
					Breeds b = Breeds.getBreedsById(uvm.Breed);
					u.setBreed(b.breedName);
				}else{
					Breeds b = Breeds.getBreedsByName(uvm.Breed);
					u.setBreed(b.breedName);	
				}
				
				
			
				u.setStage(uvm.Stage);
				u.setSKUId(uvm.SKUId);
				u.setSubBreed(uvm.SubBreed);
				u.setMealType(uvm.MealType);
				u.setLastUpdateDateTime(new Date());
				//u.setLastUpdateDateTime(new Date());
			
				JsonNode feeds = json.path("feeds");
				System.out.println(feeds);
				if(feeds.toString() != null){
					List<CattleFeeds> cc = CattleFeeds.getCatleFeedsById(uvm.feedId);
					if(cc.size() > 0 ){
						for(CattleFeeds c: cc){
							c.delete();
						}
					}

					CattleFeedMaster    cfm = null;	
					cfm = CattleFeedMaster.getUserByfeedId(u.feedId);
					if(cfm != null){
						ObjectMapper mapper = new ObjectMapper();
						List<CattleFeedsVM> uv  = mapper.convertValue(feeds, mapper.getTypeFactory()
						.constructCollectionType(List.class, CattleFeedsVM.class));
						 System.out.println("uv :"+uv.size());
						if(uv.size() != 0){
							for(int i=0 ;i <uv.size();i++){
								CattleFeeds c =  new CattleFeeds();
						        c.feedProtine = uv.get(i).feedProtine;
						        c.skuId = uv.get(i).skuId;
						        c.feedWaterContent = uv.get(i).feedWaterContent;
						        c.feedFiber = 	uv.get(i).feedFiber;
						        c.feedName = uv.get(i).feedName;
						        c.feedId = uvm.feedId;
						        System.out.println("save ");
						        c.save();
								//u.setCattleFeeds(c);		
							}
						}
					}
				}
				
				u.update(u);

			}else{
				
				u  = new CattleFeedMaster();
				u.setQuantity(uvm.quantity);
				u.setFeedName(uvm.feedName);
				u.setFeedType(uvm.feedType);
				//Breeds b = Breeds.getBreedsById(uvm.Breed);
				//u.setBreed(b.breedName);
				//u.setBreed(uvm.Breed);
				
				u.setStage(uvm.Stage);
				u.setSKUId(uvm.SKUId);
				u.setSubBreed(uvm.SubBreed);
				u.setMealType(uvm.MealType);
				u.setLastUpdateDateTime(new Date());
				
				u.save();
				JsonNode feeds = json.path("feeds");
				System.out.println(feeds);
				if(feeds.toString() != null){
					CattleFeedMaster    cfm = null;	
					cfm = CattleFeedMaster.getUserByfeedId(u.feedId);
					if(cfm != null){
						ObjectMapper mapper = new ObjectMapper();
						List<CattleFeedsVM> uv  = mapper.convertValue(feeds, mapper.getTypeFactory()
						.constructCollectionType(List.class, CattleFeedsVM.class));
						 System.out.println("uv :"+uv.size());
						if(uv.size() != 0){
							for(int i=0 ;i <uv.size();i++){
								CattleFeeds c =  new CattleFeeds();
						        c.feedProtine = uv.get(i).feedProtine;
						        c.skuId = uv.get(i).skuId;
						        c.feedWaterContent = uv.get(i).feedWaterContent;
						        c.feedFiber = 	uv.get(i).feedFiber;
						        c.feedName = uv.get(i).feedName;
						        c.feedId = u.feedId;
						        System.out.println("save ");
						        c.save();
								//u.setCattleFeeds(c);		
							}
						}
					}
					
				}
				
				
				JsonNode org = json.path("org");
				System.out.println(org);
				if(org.toString() != ""){
					Oraganization    orgni = Oraganization.getOrgById(Integer.parseInt(org.toString().replace("\"", "")));
					if(orgni != null){
						u.setOraganization(orgni);
					}
					
				}
				
				JsonNode userId = json.path("userId");
				System.out.println(userId);
				if(userId.toString() != ""){
					Users    uId = null;
				    uId = Users.getUserByEmail(userId.toString().replace("\"", ""));
					if(uId != null){
						u.setUsers(uId);
					}
					
				}
			u.update(u);

			}
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return ok("");
	}
	
	
	// called to check the usertype and also when page is refreshed
		public static Result checkForadmin() {
			String email = session().get("email");

			Users ad = Users.checkForAdminByEmail(email);
			if (ad == null) {
				return ok("notAdmin");
			} else {
				return ok((Json.stringify(Json.toJson(ad))));
			}
		}
	
	public static Result updateCattleIntakeProfileByAdmin(){
		JsonNode json = request().body().asJson();
		System.out.println("com.mnt.dairymgnt.controllers.Application.");
		
		JsonNode catJson = json.get("cat");
		System.out.println(json);
		ObjectMapper userinfoMapper = new ObjectMapper();
		try {
			CattleIntakeVM uvm = userinfoMapper.readValue(catJson.traverse(),CattleIntakeVM.class);
			CattleIntake u = CattleIntake.getUserByCattleId(uvm.cattleId);
			if(u !=  null){
				JsonNode org = json.path("org");
				System.out.println(org);
				if(org.toString() != ""){
					Oraganization    orgni = Oraganization.getOrgById(Integer.parseInt(org.toString().replace("\"", "")));
					if(orgni != null){
						u.setOraganization(orgni);
					}
				}
				JsonNode userId = json.path("userId");
				System.out.println(userId);
				if(userId.toString() != ""){
					Users    uId = null;	
					uId = Users.getUserByEmail(userId.toString().replace("\"", ""));
					if(uId != null){
						u.setUsers(uId);
					}
					
				}
			
				JsonNode feedId = json.path("feedId");
				System.out.println(feedId);
				if(feedId.toString() != ""){
					CattleFeedMaster    uId = null;
				    uId = CattleFeedMaster.getUserByfeedId(Integer.parseInt(feedId.toString().replace("\"", "")));
					if(uId != null){
						u.setCattleFeedMaster(uId);
					}
				}
				
				JsonNode catersIds = json.path("catersIds");
				System.out.println("catersIds: "+catersIds);
				if(catersIds.toString() != ""){
					CattleMaster    uId = null;
				    uId = CattleMaster.getUserByCattleId(Integer.parseInt(catersIds.toString().replace("\"", "")));
					if(uId != null){
						u.setCattleMaster(uId);
					}
				}
				
				JsonNode pregIds = json.path("pregIds");
				System.out.println(pregIds);
				if(pregIds.toString() != ""){
					PregnantCattle    uId = null;
				    uId = PregnantCattle.getPregnantCattleByCattleId((Integer.parseInt(pregIds.toString().replace("\"", ""))));
					if(uId != null){
						u.setPregnantCattle(uId);
					}
				}
				
				u.setDate(uvm.date);
				u.setDeviceID(uvm.deviceID);
				u.setLastUpdateDateTime(new Date());
				u.setQuantity(uvm.quantity);
//				u.setActualFeedName(uvm.actualFeedName);
//				u.setActualFeedType(uvm.actualFeedType);
//				u.setExpectedFeedName(uvm.expectedFeedName);
//				u.setExpectedFeedQuantity(uvm.expectedFeedQuantity);
//				u.setExpectedFeedType(uvm.expectedFeedType);
//				
				u.update(u);

			}else{
				
				u  = new CattleIntake();

				u.setDate(uvm.date);
				u.setDeviceID(uvm.deviceID);
				u.setLastUpdateDateTime(new Date());
				u.setQuantity(uvm.quantity);
//				u.setActualFeedName(uvm.actualFeedName);
//				u.setActualFeedType(uvm.actualFeedType);
//				u.setExpectedFeedName(uvm.expectedFeedName);
//				u.setExpectedFeedQuantity(uvm.expectedFeedQuantity);
//				u.setExpectedFeedType(uvm.expectedFeedType);
				u.save();

				JsonNode org = json.path("org");
				System.out.println(org);
				if(org.toString() != ""){
					Oraganization    orgni = Oraganization.getOrgById(Integer.parseInt(org.toString().replace("\"", "")));
					if(orgni != null){
						u.setOraganization(orgni);
					}
					
				}
				
				JsonNode userId = json.path("userId");
				System.out.println(userId);
				if(userId.toString() != ""){
					Users    uId = null;
				    uId = Users.getUserByEmail(userId.toString().replace("\"", ""));
					if(uId != null){
						u.setUsers(uId);
					}
					
				}
				
				JsonNode pregIds = json.path("pregIds");
				System.out.println(pregIds);
				if(pregIds.toString() != ""){
					PregnantCattle    uId = null;
				    uId = PregnantCattle.getPregnantCattleByCattleId((Integer.parseInt(pregIds.toString().replace("\"", ""))));
					if(uId != null){
						u.setPregnantCattle(uId);
					}
				}
					
				JsonNode feedId = json.path("feedId");
				System.out.println(feedId);
				if(feedId.toString() != ""){
					CattleFeedMaster    uId = null;
				    uId = CattleFeedMaster.getUserByfeedId(Integer.parseInt(feedId.toString().replace("\"", "")));
					if(uId != null){
						u.setCattleFeedMaster(uId);
					}
				}
				
				JsonNode catersIds = json.path("catersIds");
				System.out.println(catersIds);
				if(catersIds.toString() != ""){
					CattleMaster    uId = null;
				    uId = CattleMaster.getUserByCattleId(Integer.parseInt(catersIds.toString().replace("\"", "")));
					if(uId != null){
						u.setCattleMaster(uId);
					}
				}
				
			u.update(u);

			}
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return ok("");
	}
	
	
	public static Result updateOrgByAdmin(){
		JsonNode json = request().body().asJson();
		JsonNode orgJson = json.get("org");
		System.out.println(json);
		
		//JsonNode userDet = json.path("userInfo");
		ObjectMapper userinfoMapper = new ObjectMapper();
        
		try {
			OrgnizationVM ovm = userinfoMapper.readValue(orgJson.traverse(),OrgnizationVM.class);
			Oraganization org = Oraganization.getOrgById(ovm.orgId);		
			
			if(org != null){
				
				System.out.println("object ");
				org.setName(ovm.name);
				org.setAddressLine1(ovm.addressLine1);
				org.setAddressLine2(ovm.addressLine2);
				org.setCity(ovm.city);
				org.setPincode(ovm.pincode);
				org.setState(ovm.state);
				org.setLastUpdatedtime(new Date());
				org.update();
				
			}else{
				
				org = new Oraganization();
				org.setName(ovm.name);
				org.setAddressLine1(ovm.addressLine1);
				org.setAddressLine2(ovm.addressLine2);
				org.setCity(ovm.city);
				org.setPincode(ovm.pincode);
				org.setState(ovm.state);
				org.setLastUpdatedtime(new Date());
				org.save();
			}
			
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return ok("");
	}
	
	
	public static Result  getAllEntities(){
		List<Entities> entities = Entities.getAllEntities();
		HashMap< String, Object> hm = new HashMap<>();
		hm.put("entities", entities);
		return ok(Json.toJson(hm));
	}
	
	public static Result  getAllBreeds(){
		List<Breeds> breeds = Breeds.getAllBreeds();
		HashMap< String, Object> hm = new HashMap<>();
		hm.put("breeds", breeds);
		return ok(Json.toJson(hm));
	}
	
	
	public static  Result getAllSubBreedsByName(){
		DynamicForm df=Form.form().bindFromRequest();
    	System.out.println(df.get("breed"));
    	Breeds b = Breeds.getBreedIdByName(df.get("breed"));
    	long id= b.id;
    	List<Subbreed> list= Subbreed.findBySubbreedId(id);
    	return ok(Json.toJson(list));
		
	}
	
	public static Result checkUserExits(String userId){
		Users u  = Users.getUserByEmail(userId);
		if(u == null){
			return ok("available");
		}else{
			return ok("");
		}
	}
	
	
	
	public static Result getsubBreedlist()
    {
    	DynamicForm df=Form.form().bindFromRequest();
    	System.out.println(df.get("id"));
    	Long id=Long.parseLong(df.get("id"));
    	List<Subbreed> list= Subbreed.findBySubbreedId(id);
    	return ok(Json.toJson(list));
    	
    
    }
	
	public static Result getUserPermissions(){
		HashMap<String,Object>  map  = new HashMap<>(); 
		String  email = 	session().get("email");
	    Users u = Users.getUserByEmail(email);
    	UserVM uv = new UserVM();
    	if(!u.userType.trim().equals("admin")){

	  		uv.firstname = u.firstname;
	  		uv.lastname = u.lastname;
	  		uv.password = u.password;
	  		uv.userId = u.userId;
	  		uv.permissions  = u.permissions;
	  		uv.lastUpdatedatetime = u.lastUpdatedatetime;
	  		uv.oraganization = u.oraganization;
	    }else{
	    	
	    	List<Permissions> permissions = new ArrayList<>();
	  		uv.firstname = u.firstname;
	  		uv.lastname = u.lastname;
	  		uv.password = u.password;
	  		uv.userId = u.userId;
	  		uv.lastUpdatedatetime = u.lastUpdatedatetime;
	  		List<Entities> e = Entities.getAllEntities();
	  		for(int i = 0;i<e.size(); i++){
	  			Permissions p  = new Permissions();
	  			p.id = i;
	  			p.permissionName = e.get(i).entityName;
	  			p.access  = "W";
	  			permissions.add(p);
	  		}
	  		u.permissions = permissions;
	  		
	  		uv.permissions  = u.permissions;
	  		uv.oraganization = u.oraganization;
	    	
	    }
	  
	    
		map.put("user", uv);
		return ok(Json.toJson(map));
	}
	
	public static Result getAllKPIMaster(){
		HashMap<String,Object>  map  = new HashMap<>();
		
		KPIMasterVM kpvm = new KPIMasterVM();
		
		List<KPIMaster> KPIMasters = KPIMaster.getAllKPIMaster();
		
  		List<KPIName>  kpiname = KPIName.getAllKPIName();

  	      for(KPIMaster  kp:KPIMasters ){
  	    	KPIMasterVM kpm = new KPIMasterVM();
  	    	kpm.kpiId = kp.kpiId;
  	    	kpm.KPIDesc = kp.KPIDesc;
  	    	kpm.kpiValue = kp.kpiValue;
  	    	kpm.KPIName = kp.KPIName;
  	    	
  	    	kpm.CycleDaysBetweenInsemination = kpiname.get(0).CycleDaysBetweenInsemination;
  	    	kpm.DaysbyWhenCalvsCanbeInseminated = kpiname.get(0).DaysbyWhenCalvsCanbeInseminated;
  	    	
  	    	kpm.DaysbyWhenCalvscanProduceMilk = kpiname.get(0).DaysbyWhenCalvscanProduceMilk;
  	    	
  	    	kpm.DaysOfPregnancy = kpiname.get(0).DaysOfPregnancy;
  	    	
  	    	kpm.DaysofStopMilkingBreedWisePreDelivery = kpiname.get(0).DaysofStopMilkingBreedWisePreDelivery;
  	    	
  	    	kpm.MonthsOfMilkingPerCattleBreedwiseafterDelivery = kpiname.get(0).MonthsOfMilkingPerCattleBreedwiseafterDelivery;
  	    	
  	    	kpm.noOfClavesPerYear = kpiname.get(0).noOfClavesPerYear;
  	    	kpm.ReadyforInseminationDaysAfterDelivery = kpiname.get(0).ReadyforInseminationDaysAfterDelivery;
  	    	kpm.TotalNoOfCalvesPerCattle = kpiname.get(0).TotalNoOfCalvesPerCattle;
  	    	kpvm = kpm;
  	      }
	map.put("kpvm", kpvm);
	return ok(Json.toJson(map));
	} 
	
	public static Result updateandsaveKPIMaster(){
		List<KPIMaster> KPIMasters = KPIMaster.getAllKPIMaster();

		for(KPIMaster k: KPIMasters){
			k.delete();
		}
		
  		List<KPIName>  kpiname1 = KPIName.getAllKPIName();

  		 for(KPIName k: kpiname1){
		 	k.delete();
		 }
  		
  		JsonNode json = request().body().asJson();
  		System.out.println(json);
		
  		JsonNode kpvmJson = json.get("kpvm12");
		try{
		ObjectMapper userinfoMapper = new ObjectMapper();
		ObjectMapper mapper = new ObjectMapper();
        
         		KPIMasterVM   kp = userinfoMapper.readValue(kpvmJson.traverse(),KPIMasterVM.class);
				KPIMaster kpm = new KPIMaster();
	  	    	
				kpm.kpiId = kp.kpiId;
	  	    	kpm.KPIDesc = kp.KPIDesc;
	  	    	kpm.kpiValue = kp.kpiValue;
	  	    	kpm.KPIName = kp.KPIName;
	  	    	kpm.save();

	  	    	KPIName kpiname = new KPIName();
	  	    	kpiname.CycleDaysBetweenInsemination = kp.CycleDaysBetweenInsemination;
	  	    	kpiname.DaysbyWhenCalvsCanbeInseminated = kp.DaysbyWhenCalvsCanbeInseminated;
	  	    	kpiname.DaysbyWhenCalvscanProduceMilk = kp.DaysbyWhenCalvscanProduceMilk;
	  	    	kpiname.DaysOfPregnancy = kp.DaysOfPregnancy;
	  	    	kpiname.DaysofStopMilkingBreedWisePreDelivery = kp.DaysofStopMilkingBreedWisePreDelivery;
	  	    	kpiname.MonthsOfMilkingPerCattleBreedwiseafterDelivery = kp.MonthsOfMilkingPerCattleBreedwiseafterDelivery;
	  	    	kpiname.noOfClavesPerYear = kp.noOfClavesPerYear;
	  	    	kpiname.ReadyforInseminationDaysAfterDelivery = kp.ReadyforInseminationDaysAfterDelivery;
	  	    	kpiname.TotalNoOfCalvesPerCattle = kp.TotalNoOfCalvesPerCattle;
	  	    	kpiname.save();
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	  	    	return ok("");
	}
}
	


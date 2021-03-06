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
//import com.gargoylesoftware.htmlunit.javascript.host.Console;
//import com.gargoylesoftware.htmlunit.javascript.host.Console;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.mnt.dairymgnt.VM.CattleFeedInventoryVM;
import com.mnt.dairymgnt.VM.CattleFeedMasterVM;
import com.mnt.dairymgnt.VM.CattleFeedsVM;
import com.mnt.dairymgnt.VM.CattleHealthMasterVM;
import com.mnt.dairymgnt.VM.CattleHealthVM;
import com.mnt.dairymgnt.VM.CattleIntakePlanVM;
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
import com.mnt.dairymgnt.VM.VaccinationPlanVM;

import com.mnt.dairymgnt.models.Breeds;
import com.mnt.dairymgnt.models.CattleFeedInventory;
import com.mnt.dairymgnt.models.CattleFeedMaster;
import com.mnt.dairymgnt.models.CattleFeeds;
import com.mnt.dairymgnt.models.CattleHealth;
import com.mnt.dairymgnt.models.CattleHealthMaster;
import com.mnt.dairymgnt.models.CattleIntake;
import com.mnt.dairymgnt.models.CattleIntakePlan;
import com.mnt.dairymgnt.models.CattleMaster;
import com.mnt.dairymgnt.models.CattleOutput;
import com.mnt.dairymgnt.models.ChildCattle;
import com.mnt.dairymgnt.models.Entities;
import com.mnt.dairymgnt.models.FeedMaster;
import com.mnt.dairymgnt.models.FeedType;
import com.mnt.dairymgnt.models.KPIMaster;
import com.mnt.dairymgnt.models.KPIName;
import com.mnt.dairymgnt.models.MealType;
import com.mnt.dairymgnt.models.Oraganization;
import com.mnt.dairymgnt.models.Permissions;
import com.mnt.dairymgnt.models.PregnantCattle;
import com.mnt.dairymgnt.models.Stage;
import com.mnt.dairymgnt.models.Subbreed;
import com.mnt.dairymgnt.models.VaccinationPlan;
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
			cvm.price = u.price;
		    cvm.feedType = u.feedType;
			cmvm.add(cvm);
			//cvm.dateofBirth = u.dateofBirth;
		}	
		HashMap  <String ,Object> hm = new HashMap();
		hm.put("caters",cmvm);
		hm.put("userCount", count);
		
		return  ok(Json.toJson(hm));
		
	}
	
	public static Result getAllVaccinationPlan(int pageNo){
		int count  = 0;
		List<VaccinationPlan> vaccinationPlans = VaccinationPlan.getAllVaccinationPlan(pageNo,10);
        ArrayList<VaccinationPlanVM> cmvm = new ArrayList<>();
		count = CattleMaster.getAllCattleMasterCount(pageNo);
		
		for(VaccinationPlan u : vaccinationPlans){
			VaccinationPlanVM cvm = new VaccinationPlanVM();
			cvm.companyName=u.companyName;
			cvm.doctorName=u.doctorName;
			cvm.elapsedDays=u.elapsedDays;
			cvm.vaccineName=u.vaccineName;
			cvm.vaccineType=u.vaccineType;
			cvm.vaccinationPlanId = u.vaccinationPlanId;;
			cvm.breed=u.breed;
			cvm.subBreed=u.subBreed;
			cvm.vaccinationPlanId=u.vaccinationPlanId;
			cvm.cattleMaster=u.CattleMaster;
			
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
			 cvm.feedType = u.feedType;
			 cvm.quantityofWater = u.quantityofWater;
			
			cmvm.add(cvm);
			//cvm.dateofBirth = u.dateofBirth;
		}	
		HashMap  <String ,Object> hm = new HashMap();
		hm.put("caters",cmvm);
		return  ok(Json.toJson(hm));
	}
	public static Result getAllOnlyVaccinationPlan(){
		int count  = 0;
		List<VaccinationPlan> vaccinationPlans = VaccinationPlan.getAllOnlyVaccinationPlan();
        ArrayList<VaccinationPlanVM> cmvm = new ArrayList<>();
		
		for(VaccinationPlan u : vaccinationPlans){
			VaccinationPlanVM cvm = new VaccinationPlanVM();
			//cvm.feedId = u.feedId;
			cvm.companyName=u.companyName;
			cvm.doctorName=u.doctorName;
			cvm.elapsedDays=u.elapsedDays;
			cvm.vaccineType=u.vaccineType;
			cvm.vaccineName=u.vaccineName;
			cvm.vaccinationPlanId=u.vaccinationPlanId;
			cvm.breed=u.breed;
			cvm.subBreed=u.subBreed;
			cvm.cattleMaster=u.CattleMaster;
			
			
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
			cvm.subbreed=u.subBreed;
			cvm.cattleId = u.cattleId;
			cvm.cattleIdentificationMark =    u.cattleIdentificationMark;
			//cvm.dateofBirth = u.dateofBirth;
			cvm.gender  = u.gender;
			cvm.name = u.name;
			cvm.users = u.users;
			cvm.oraganization = u.oraganization;
			cvm.RFID = u.RFID;
			cvm.parentId  =u.parentId; 
			
			cvm.lastUpdateDateTime = u.lastUpdateDateTime;

			if(u.dateofBirth != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.dateofBirth);
				cvm.dateofBirthVM = date;
			}else{
				cvm.dateofBirthVM = "";
			}
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
			cfvm.Breed = u.breed;
			cfvm.SubBreed = u.SubBreed;
			cfvm.lastUpdateDateTime =  u.lastUpdateDateTime;
			cfvm.users = u.users;
			cfvm.SKUId = u.SKUId;
			cfvm.Stage = u.Stage;
			cfvm.oraganization = u.oraganization;
			cfvm.feedPlanName = u.feedPlanName;
			
			if(u.feedPlanStartDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.feedPlanStartDate);
				cfvm.feedPlanStartDateVM = date;
			}else{
				cfvm.feedPlanStartDateVM = "";
			}
			if(u.feedPlanEndDate != null){
				SimpleDateFormat DATE_FORMAT1 = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT1.format(u.feedPlanEndDate);
				cfvm.feedPlanEndDateVM = date;
			}else{
				cfvm.feedPlanEndDateVM = "";
			}


			List <CattleFeeds> cfs = CattleFeeds.getCatleFeedsById(cfvm.feedId);
			cfvm.cattleFeeds = cfs;
			cmvm.add(cfvm);
		}
		HashMap  <String ,Object> hm = new HashMap();
		hm.put("feedcaters",cmvm);
		hm.put("userCount", count);
		return ok(Json.toJson(hm));
	}
	public static Result getAllCattleHealthMaster(int pageno){
		List<CattleHealthMaster> cattleHealthMasters = CattleHealthMaster.getAllCattleHealthMaster(pageno,10);
        int count  = 0;
       // count  = CattleFeedMaster.getAllFeedCattleMasterCount(pageno);
        count = CattleMaster.getAllCattleMasterCount(pageno);
		ArrayList<CattleHealthMasterVM> cmvm = new ArrayList<>();
		
		for(CattleHealthMaster u : cattleHealthMasters){
			CattleHealthMasterVM cfvm = new CattleHealthMasterVM();
			cfvm.duration = u.duration;
			cfvm.frequency = u.frequency;
			cfvm.frequencyType=u.frequencyType;
			cfvm.medicationName =    u.medicationName;
			cfvm.medicationType = u.medicationType;
			cfvm.healthPlanId = u.healthPlanId;
			cfvm.oraganization=u.oraganization;
			cfvm.users=u.users;
			if(u.medicationEndDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.medicationEndDate);
				cfvm.medicationEndDateVM = date;
			}else{
				cfvm.medicationEndDateVM = "";
			}
			if(u.medicationStartDate != null){
				SimpleDateFormat DATE_FORMAT1 = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT1.format(u.medicationStartDate);
				cfvm.medicationStartDateVM = date;
			}else{
				cfvm.medicationStartDateVM = "";
			}
			if(u.medicationNextDate != null){
				SimpleDateFormat DATE_FORMAT1 = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT1.format(u.medicationNextDate);
				cfvm.medicationNextDateVM = date;
			}else{
				cfvm.medicationNextDateVM = "";
			}

			/*List <CattleFeeds> cfs = CattleFeeds.getCatleFeedsById(cfvm.feedId);
			cfvm.cattleFeeds = cfs;*/
			cmvm.add(cfvm);
		}
		HashMap  <String ,Object> hm = new HashMap();
		hm.put("feedcaters",cmvm);
		//hm.put("userCount", count);
		return ok(Json.toJson(hm));
	}

	public static Result getAllCattleFeedInventory(int pageno){
		List<CattleFeedInventory> cattleFeedInventories = CattleFeedInventory.getAllCattleFeedInventory(pageno,10);
        int count  = 0;
       // count  = CattleFeedMaster.getAllFeedCattleMasterCount(pageno);
        count = CattleMaster.getAllCattleMasterCount(pageno);
		ArrayList<CattleFeedInventoryVM> cmvm = new ArrayList<>();
		
		for(CattleFeedInventory u : cattleFeedInventories){
			CattleFeedInventoryVM cfvm = new CattleFeedInventoryVM();
			cfvm.cattleFeedInventoryId=u.cattleFeedInventoryId;
			cfvm.feedName = u.feedName;
			cfvm.feedType = u.feedType;
			cfvm.remark=u.remark;
			cfvm.stockBalance = u.stockBalance;
			cfvm.stockInQuantity = u.stockInQuantity;
			cfvm.stockOutQuantity = u.stockOutQuantity;
			cfvm.oraganization=u.oraganization;
			cfvm.users=u.users;
			cfvm.cattleMaster=u.CattleMaster;
			cfvm.stockPerviousBalance=u.stockPerviousBalance;

			if(u.stockInDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.stockInDate);
				cfvm.stockInDate = date;
			}else{
				cfvm.stockInDate = "";
			}
			if(u.stockOutDate != null){
				SimpleDateFormat DATE_FORMAT1 = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT1.format(u.stockOutDate);
				cfvm.stockOutDate = date;
			}else{
				cfvm.stockOutDate = "";
			}
			
			/*List <CattleFeeds> cfs = CattleFeeds.getCatleFeedsById(cfvm.feedId);
			cfvm.cattleFeeds = cfs;*/
			cmvm.add(cfvm);
		}
		HashMap  <String ,Object> hm = new HashMap();
		hm.put("feedcaters",cmvm);
		//hm.put("userCount", count);
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
			cfvm.Breed = u.breed;
			cfvm.SubBreed = u.SubBreed;
			cfvm.lastUpdateDateTime =  u.lastUpdateDateTime;
			cfvm.users = u.users;
			cfvm.SKUId = u.SKUId;
			cfvm.Stage = u.Stage;
			cfvm.feedPlanName = u.feedPlanName;
			cfvm.oraganization = u.oraganization;
			cmvm.add(cfvm);
		}
		
		HashMap  <String ,Object> hm = new HashMap();
		hm.put("feedcaters",cmvm);
		return ok(Json.toJson(hm));
	}
	public static Result getAllOnlyCattleHealthMaster(){
		List<CattleHealthMaster> cattleHealthMasters = CattleHealthMaster.getAllOnlyCattleHealthMaster();
        
		ArrayList<CattleHealthMasterVM> cmvm = new ArrayList<>();
		
		for(CattleHealthMaster u : cattleHealthMasters){
			CattleHealthMasterVM cfvm = new CattleHealthMasterVM();
			cfvm.healthPlanId = u.healthPlanId;
			cfvm.medicationName = u.medicationName;
			cfvm.medicationType =    u.medicationType;
			cfvm.frequency = u.frequency;
			cfvm.duration = u.duration;
			cfvm.users = u.users;
			cfvm.oraganization = u.oraganization;
			cfvm.frequencyType=u.frequencyType;
			cfvm.lastUpdateDateTime =  u.lastUpdateDateTime;
			cmvm.add(cfvm);
		}
		
		HashMap  <String ,Object> hm = new HashMap();
		hm.put("feedcaters",cmvm);
		return ok(Json.toJson(hm));
	}
	
	public static Result getAllOnlyCattleFeedInventory(){
		List<CattleFeedInventory> cattleFeedInventories = CattleFeedInventory.getAllOnlyCattleFeedInventory();
        
		ArrayList<CattleFeedInventoryVM> cmvm = new ArrayList<>();
		
		for(CattleFeedInventory u : cattleFeedInventories){
			CattleFeedInventoryVM cfvm = new CattleFeedInventoryVM();
			cfvm.cattleFeedInventoryId=u.cattleFeedInventoryId;
			cfvm.feedName = u.feedName;
			cfvm.feedType = u.feedType;
			cfvm.remark=u.remark;
			cfvm.stockBalance = u.stockBalance;
			cfvm.stockInQuantity = u.stockInQuantity;
			cfvm.stockOutQuantity = u.stockOutQuantity;
			cfvm.oraganization=u.oraganization;
			cfvm.users=u.users;
			cfvm.cattleMaster=u.CattleMaster;
			cfvm.stockPerviousBalance=u.stockPerviousBalance;

			if(u.stockInDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.stockInDate);
				cfvm.stockInDate = date;
			}else{
				cfvm.stockInDate = "";
			}
			if(u.stockOutDate != null){
				SimpleDateFormat DATE_FORMAT1 = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT1.format(u.stockOutDate);
				cfvm.stockOutDate = date;
			}else{
				cfvm.stockOutDate = "";
			}
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
			//due date
			if(u.dueDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.dueDate);
				cfvm.dueDate = date;
			}else{
				cfvm.dueDate = "";
			}
			
			//success date
			if(u.successDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.successDate);
				cfvm.successDate = date;
			}else{
				cfvm.successDate = "";
			}
			
			//last delilery date
			if(u.lastDeliveryDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.lastDeliveryDate);
				cfvm.lastDeliveryDate = date;
			}else{
				cfvm.lastDeliveryDate = "";
			}
			
			//pregnancy date
			if(u.pregnancyDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.pregnancyDate);
				cfvm.pregnancyDate = date;
			}else{
				cfvm.pregnancyDate = "";
			}
			
			//first insemination date
			if(u.firstInseminationDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.firstInseminationDate);
				cfvm.firstInseminationDate = date;
			}else{
				cfvm.firstInseminationDate = "";
			}
			
			//second insemination date
			if(u.secondInseminationDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.secondInseminationDate);
				cfvm.secondInseminationDate = date;
			}else{
				cfvm.secondInseminationDate = "";
			}
			
			//third insemination date
			if(u.thirdInseminationDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.thirdInseminationDate);
				cfvm.thirdInseminationDate = date;
			}else{
				cfvm.thirdInseminationDate = "";
			}
			
			//forth insemination date
			if(u.forthInseminationDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.forthInseminationDate);
				cfvm.forthInseminationDate = date;
			}else{
				cfvm.forthInseminationDate = "";
			}
			
			// planned first insemination date
			if(u.plannedFirstInseminationDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.plannedFirstInseminationDate);
				cfvm.plannedFirstInseminationDate = date;
			}else{
				cfvm.plannedFirstInseminationDate = "";
			}
			
			// planned second insemination date
			if(u.plannedSecondInseminationDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
				String date = DATE_FORMAT.format(u.plannedSecondInseminationDate);
				cfvm.plannedSecondInseminationDate = date;
			}else{
				cfvm.plannedSecondInseminationDate = "";
			}
			
			// planned third insemination date
			if(u.plannedThirdInseminationDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.plannedThirdInseminationDate);
		        cfvm.plannedThirdInseminationDate = date;
			}else{
				cfvm.plannedThirdInseminationDate = "";
			}
			
			// planned forth insemination date
			if(u.plannedForthInseminationDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.plannedForthInseminationDate);
				cfvm.plannedForthInseminationDate = date;
			}else{
				cfvm.plannedForthInseminationDate = "";
			}
			
			cfvm.users = u.users;
			cfvm.oraganization = u.oraganization;
			
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
			
			//cfvm.lastDeliveryDate =u.lastDeliveryDate ;
			//due date
			if(u.dueDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.dueDate);
				cfvm.dueDate = date;
			}else{
				cfvm.dueDate = "";
			}
			
			//success date
			if(u.successDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.successDate);
				cfvm.successDate = date;
			}else{
				cfvm.successDate = "";
			}
			
			//last delilery date
			if(u.lastDeliveryDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.lastDeliveryDate);
				cfvm.lastDeliveryDate = date;
			}else{
				cfvm.lastDeliveryDate = "";
			}
			
			//pregnancy date
			if(u.pregnancyDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.pregnancyDate);
				cfvm.pregnancyDate = date;
			}else{
				cfvm.pregnancyDate = "";
			}
			
			//first insemination date
			if(u.firstInseminationDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.firstInseminationDate);
				cfvm.firstInseminationDate = date;
			}else{
				cfvm.firstInseminationDate = "";
			}
			
			//second insemination date
			if(u.secondInseminationDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.secondInseminationDate);
				cfvm.secondInseminationDate = date;
			}else{
				cfvm.secondInseminationDate = "";
			}
			
			//third insemination date
			if(u.thirdInseminationDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.thirdInseminationDate);
				cfvm.thirdInseminationDate = date;
			}else{
				cfvm.thirdInseminationDate = "";
			}
			
			//forth insemination date
			if(u.forthInseminationDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.forthInseminationDate);
				cfvm.forthInseminationDate = date;
			}else{
				cfvm.forthInseminationDate = "";
			}
			
			// planned first insemination date
			if(u.plannedFirstInseminationDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.plannedFirstInseminationDate);
				cfvm.plannedFirstInseminationDate = date;
			}else{
				cfvm.plannedFirstInseminationDate = "";
			}
			
			// planned second insemination date
			if(u.plannedSecondInseminationDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
				String date = DATE_FORMAT.format(u.plannedSecondInseminationDate);
				cfvm.plannedSecondInseminationDate = date;
			}else{
				cfvm.plannedSecondInseminationDate = "";
			}
			
			// planned third insemination date
			if(u.plannedThirdInseminationDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.plannedThirdInseminationDate);
		        cfvm.plannedThirdInseminationDate = date;
			}else{
				cfvm.plannedThirdInseminationDate = "";
			}
			
			// planned forth insemination date
			if(u.plannedForthInseminationDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.plannedForthInseminationDate);
				cfvm.plannedForthInseminationDate = date;
			}else{
				cfvm.plannedForthInseminationDate = "";
			}
			
			cfvm.users = u.users;
			cfvm.oraganization = u.oraganization;
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
			cfvm.dateOfBirth = u.dateOfBirth;		
			cfvm.deviceID = u.deviceID;
			cfvm.quantity = u.quantity;
			cfvm.lastUpdateDateTime =  u.lastUpdateDateTime;
			cfvm.users = u.users;
			cfvm.oraganization = u.oraganization;
			cfvm.cattleFeedMaster = u.cattleFeedMaster;
			cfvm.cattleId = u.cattleId;
			cfvm.cattleMaster = u.CattleMaster;
			cfvm.dateOfBirth = u.dateOfBirth;
			//cfvm.pregnantCattle = u.pregnantCattle;
			
			List <CattleIntakePlan> cfs = CattleIntakePlan.getCatleFeedsById(cfvm.cattleId);
			cfvm.cattleIntakeVM = cfs;
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
			cfvm.dateOfBirth = u.dateOfBirth;
			System.out.println(u.dateOfBirth);
			cfvm.deviceID = u.deviceID;
			cfvm.quantity = u.quantity;
			cfvm.lastUpdateDateTime =  u.lastUpdateDateTime;
			cfvm.users = u.users;
			cfvm.oraganization = u.oraganization;
			//cfvm.cattleFeedMaster = u.cattleFeedMaster;
			cfvm.cattleId = u.cattleId;
			cfvm.cattleMaster = u.CattleMaster;
			cfvm.dateOfBirth = u.dateOfBirth;
			//cfvm.pregnantCattle = u.pregnantCattle;
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
			if(u.date != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.date);
				cfvm.date = date;
			}else{
				cfvm.date = "";
			}
			//cfvm.date = u.date;
			/*if(u.time != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(u.time);
				cfvm.time = date;
			}else{
				cfvm.time = "";
			}*/
			cfvm.time= u.time;
			cfvm.deviceID = u.deviceID;
			cfvm.quantity = u.quantity;
			cfvm.lastUpdateDateTime =  u.LastUpdateDateTime;
			cfvm.users = u.users;
			cfvm.oraganization = u.oraganization;
			cfvm.cattleId = u.cattleId;
			cfvm.SNFContent = u.SNFContent;
			cfvm.fatContent = u.fatContent;
			cfvm.cattleMaster = u.CattleMaster;
			/*cfvm.pregnantCattle = u.pregnantCattle;*/
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
			//cfvm.duedate = u.duedate;
			//cfvm.lastDelivaerydate = u.lastDelivaerydate;
			cfvm.medicationEndDate = u.medicationEndDate;
			cfvm.medicationStartDate  = u.medicationStartDate;
			cfvm.medicationName = u.medicationName;
			cfvm.medicationType = u.medicationType;
			
			//cfvm.pregnancyDate = u.pregnancyDate;
			//cfvm.pregnant = u.pregnant;
			cfvm.lastUpdateDateTime =  u.lastUpdateDateTime;
			cfvm.users = u.users;
			cfvm.oraganization = u.oraganization;
			cfvm.cattleId = u.cattleId;
			cfvm.healthType=u.healthType;
			cfvm.vaccinationActualDate=u.vaccinationActualDate;
			cfvm.vaccinationName=u.vaccinationName;
			cfvm.vaccinationPlannedDate=u.vaccinationPlannedDate;
			cfvm.vaccinationType=u.vaccinationType;
			cfvm.breed=u.breed;
			cfvm.subBreed=u.subBreed;
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
				
			     }
				}
				
				u.update();
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
				u.setRFID(uvm.RFID);
				
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
				u.setRFID(uvm.RFID);
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
	public static Result updateVaccinationPlanProfileByAdmin(){
		
		JsonNode json = request().body().asJson();
		JsonNode feedJson = json.get("cat");
		System.out.println(feedJson);
		System.out.println(json);
		ObjectMapper userinfoMapper = new ObjectMapper();
		try {
			VaccinationPlanVM uvm = userinfoMapper.readValue(feedJson.traverse(),VaccinationPlanVM.class);
			System.out.println(json.get("companyName"));
			VaccinationPlan u = VaccinationPlan.getUserByfeedId(uvm.vaccinationPlanId);
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
			
				/*JsonNode catersIds = json.path("catersIds");
				System.out.println(catersIds);
				if(catersIds.toString() != ""){
					CattleMaster    uId = null;
				    uId = CattleMaster.getUserByCattleId(Integer.parseInt(catersIds.toString().replace("\"", "")));
					if(uId != null){
						u.setCattleMaster(uId);
					}
				}
				*/
				
				
				u.setCompanyName(uvm.companyName);
				u.setDoctorName( uvm.doctorName);
				u.setElapsedDays(uvm.elapsedDays);
				u.setVaccineName(uvm.vaccineName);
				u.setVaccineType(uvm.vaccineType);
				u.lastUpdateDateTime = new Date();
				
				/*if(uvm.breed.matches("\\d+")){
					Breeds b = Breeds.getBreedsById(uvm.breed);
					u.setBreed(b.breedName);
				}else{
					Breeds b = Breeds.getBreedsByName(uvm.breed);
					u.setBreed(b.breedName);	
				}
				u.setSubBreed(uvm.subBreed);*/
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
				
				if(uvm.subBreed != null){
					if(uvm.subBreed.matches("[\\d+]")){
						 List<Subbreed> subreeds = Subbreed.getSunBreedById(uvm.subBreed);	
						 u.setSubBreed(subreeds.get(0).sub_breed);
					}else{
						 u.setSubBreed(uvm.subBreed);
					}
				}else{
					 u.setSubBreed("");
				}
				u.update();
			

			}else{
					
				
				
				u  = new VaccinationPlan();
				
				u.setCompanyName(uvm.companyName);
				u.setDoctorName(uvm.doctorName);
				u.setElapsedDays(uvm.elapsedDays);
				u.setVaccineName(uvm.vaccineName);
				u.setVaccineType(uvm.vaccineType);
				u.lastUpdateDateTime = new Date();
				
				/*if(uvm.breed.matches("\\d+")){
					Breeds b = Breeds.getBreedsById(uvm.breed);
					u.setBreed(b.breedName);
				}else{
					Breeds b = Breeds.getBreedsByName(uvm.breed);
					u.setBreed(b.breedName);	
				}
				u.setSubBreed(uvm.subBreed);;*/
				if(uvm.breed != null){
					Breeds b = Breeds.getBreedsById(uvm.breed);
					u.setBreed(b.breedName);
				}else{
					//Breeds b = Breeds.getBreedsById(uvm.breed);
					u.setBreed("");
				}
				
				if(uvm.subBreed != null){
					 List<Subbreed> subreeds = Subbreed.getSunBreedById(uvm.subBreed);	
					 u.setSubBreed(subreeds.get(0).sub_breed);
				}else{
					 u.setSubBreed("");
				}
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
				
				JsonNode catersIds = json.path("catersIds");
				System.out.println(catersIds);
				if(catersIds.toString() != ""){
					CattleMaster    uId = null;
				    uId = CattleMaster.getUserByCattleId(Integer.parseInt(catersIds.toString().replace("\"", "")));
					if(uId != null){
						u.setCattleMaster(uId);
					}
				}
				u.update();
				//u.update(u);
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
				u.setFeedType(uvm.feedType);
				u.setPrice(uvm.price);
				
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
				u.setPrice(uvm.price);
				u.lastUpdateDateTime = new Date();
				//u.setLastDelivery(uvm.lastDelivery);
				u.setFeedType(uvm.feedType);
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
			/*PregnancyCattleVM uvm = userinfoMapper.readValue(catJson.traverse(),PregnancyCattleVM.class);
			PregnantCattle u = PregnantCattle.getPregnantCattleByCattleId(uvm.pregnancyId);*/
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
				
				u.setCattleId(uvm.cattleId);
				
				//due date
				SimpleDateFormat format1 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d1 = null;
				try {
					if(uvm.dueDate !=null){
						if(uvm.dueDate.contains("\"")){
							d1 = format1.parse(uvm.dueDate.replaceAll("\"", ""));
						}else{
							d1 = format1.parse(uvm.dueDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setDueDate(d1);
				
				//first Insemination date
				SimpleDateFormat format2 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d2 = null;
				try {
					if(uvm.firstInseminationDate !=null){
						if(uvm.firstInseminationDate.contains("\"")){
							d2 = format2.parse(uvm.firstInseminationDate.replaceAll("\"", ""));
						}else{
							d2 = format2.parse(uvm.firstInseminationDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setFirstInseminationDate(d2);
				
				//Second Insemination date
				SimpleDateFormat format3 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d3 = null;
				try {
					if(uvm.secondInseminationDate !=null){
						if(uvm.secondInseminationDate.contains("\"")){
							d3 = format3.parse(uvm.secondInseminationDate.replaceAll("\"", ""));
						}else{
							d3 = format3.parse(uvm.secondInseminationDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setSecondInseminationDate(d3);
				
				//third Insemination date
				SimpleDateFormat format4 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d4 = null;
				try {
					if(uvm.thirdInseminationDate !=null){
						if(uvm.thirdInseminationDate.contains("\"")){
							d4 = format4.parse(uvm.thirdInseminationDate.replaceAll("\"", ""));
						}else{
							d4 = format4.parse(uvm.thirdInseminationDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setThirdInseminationDate(d4);
				
				//forth Insemination date
				SimpleDateFormat format5 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d5 = null;
				try {
					if(uvm.forthInseminationDate !=null){
						if(uvm.forthInseminationDate.contains("\"")){
							d5 = format5.parse(uvm.forthInseminationDate.replaceAll("\"", ""));
						}else{
							d5 = format5.parse(uvm.forthInseminationDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setForthInseminationDate(d5);
				
				//planned first Insemination date
				SimpleDateFormat format6 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d6 = null;
				try {
					if(uvm.plannedFirstInseminationDate !=null){
						if(uvm.plannedFirstInseminationDate.contains("\"")){
							d6 = format6.parse(uvm.plannedFirstInseminationDate.replaceAll("\"", ""));
						}else{
							d6 = format6.parse(uvm.plannedFirstInseminationDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setPlannedFirstInseminationDate(d6);
				
				//planned second Insemination date
				SimpleDateFormat format7 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d7 = null;
				try {
					if(uvm.plannedSecondInseminationDate !=null){
						if(uvm.plannedSecondInseminationDate.contains("\"")){
							d7 = format7.parse(uvm.plannedSecondInseminationDate.replaceAll("\"", ""));
						}else{
							d7 = format7.parse(uvm.plannedSecondInseminationDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setPlannedSecondInseminationDate(d7);
				
				
				//planned third Insemination date
				SimpleDateFormat format8 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d8 = null;
				try {
					if(uvm.plannedThirdInseminationDate !=null){
						if(uvm.plannedThirdInseminationDate.contains("\"")){
							d8 = format8.parse(uvm.plannedThirdInseminationDate.replaceAll("\"", ""));
						}else{
							d8 = format8.parse(uvm.plannedThirdInseminationDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setPlannedThirdInseminationDate(d8);
				
				//planned Forth Insemination date
				SimpleDateFormat format9 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d9 = null;
				try {
					if(uvm.plannedForthInseminationDate !=null){
						if(uvm.plannedForthInseminationDate.contains("\"")){
							d9 = format9.parse(uvm.plannedForthInseminationDate.replaceAll("\"", ""));
						}else{
							d9 = format9.parse(uvm.plannedForthInseminationDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setPlannedForthInseminationDate(d9);
				
				//success date
				SimpleDateFormat format10 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d10 = null;
				try {
					if(uvm.successDate !=null){
						if(uvm.successDate.contains("\"")){
							d10 = format10.parse(uvm.successDate.replaceAll("\"", ""));
						}else{
							d10 = format10.parse(uvm.successDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setSuccessDate(d10);
				
				//pregnancy Date
				SimpleDateFormat format11 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d11 = null;
				try {
					if(uvm.pregnancyDate !=null){
						if(uvm.pregnancyDate.contains("\"")){
							d11 = format11.parse(uvm.pregnancyDate.replaceAll("\"", ""));
						}else{
							d11 = format11.parse(uvm.pregnancyDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setPregnancyDate(d11);
				
				
				//last Delivery date
				SimpleDateFormat format12 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d12 = null;
				try {
					if(uvm.lastDeliveryDate !=null){
						if(uvm.lastDeliveryDate.contains("\"")){
							d12 = format12.parse(uvm.lastDeliveryDate.replaceAll("\"", ""));
						}else{
							d12 = format12.parse(uvm.lastDeliveryDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setLastDeliveryDate(d12);
				
				
				
				
				u.update(u);

			}else{
				
				u  = new PregnantCattle();
				
				u.setCattleId(uvm.cattleId);
				
				//due date
				SimpleDateFormat format1 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d1 = null;
				try {
					if(uvm.dueDate !=null){
						if(uvm.dueDate.contains("\"")){
							d1 = format1.parse(uvm.dueDate.replaceAll("\"", ""));
						}else{
							d1 = format1.parse(uvm.dueDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setDueDate(d1);
				
				//first Insemination date
				SimpleDateFormat format2 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d2 = null;
				try {
					if(uvm.firstInseminationDate !=null){
						if(uvm.firstInseminationDate.contains("\"")){
							d2 = format2.parse(uvm.firstInseminationDate.replaceAll("\"", ""));
						}else{
							d2 = format2.parse(uvm.firstInseminationDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setFirstInseminationDate(d2);
				
				//Second Insemination date
				SimpleDateFormat format3 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d3 = null;
				try {
					if(uvm.secondInseminationDate !=null){
						if(uvm.secondInseminationDate.contains("\"")){
							d3 = format3.parse(uvm.secondInseminationDate.replaceAll("\"", ""));
						}else{
							d3 = format3.parse(uvm.secondInseminationDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setSecondInseminationDate(d3);
				
				//third Insemination date
				SimpleDateFormat format4 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d4 = null;
				try {
					if(uvm.thirdInseminationDate !=null){
						if(uvm.thirdInseminationDate.contains("\"")){
							d4 = format4.parse(uvm.thirdInseminationDate.replaceAll("\"", ""));
						}else{
							d4 = format4.parse(uvm.thirdInseminationDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setThirdInseminationDate(d4);
				
				//forth Insemination date
				SimpleDateFormat format5 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d5 = null;
				try {
					if(uvm.forthInseminationDate !=null){
						if(uvm.forthInseminationDate.contains("\"")){
							d5 = format5.parse(uvm.forthInseminationDate.replaceAll("\"", ""));
						}else{
							d5 = format5.parse(uvm.forthInseminationDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setForthInseminationDate(d5);
				
				//planned first Insemination date
				SimpleDateFormat format6 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d6 = null;
				try {
					if(uvm.plannedFirstInseminationDate !=null){
						if(uvm.plannedFirstInseminationDate.contains("\"")){
							d6 = format6.parse(uvm.plannedFirstInseminationDate.replaceAll("\"", ""));
						}else{
							d6 = format6.parse(uvm.plannedFirstInseminationDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setPlannedFirstInseminationDate(d6);
				
				//planned second Insemination date
				SimpleDateFormat format7 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d7 = null;
				try {
					if(uvm.plannedSecondInseminationDate !=null){
						if(uvm.plannedSecondInseminationDate.contains("\"")){
							d7 = format7.parse(uvm.plannedSecondInseminationDate.replaceAll("\"", ""));
						}else{
							d7 = format7.parse(uvm.plannedSecondInseminationDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setPlannedSecondInseminationDate(d7);
				
				
				//planned third Insemination date
				SimpleDateFormat format8 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d8 = null;
				try {
					if(uvm.plannedThirdInseminationDate !=null){
						if(uvm.plannedThirdInseminationDate.contains("\"")){
							d8 = format8.parse(uvm.plannedThirdInseminationDate.replaceAll("\"", ""));
						}else{
							d8 = format8.parse(uvm.plannedThirdInseminationDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setPlannedThirdInseminationDate(d8);
				
				//planned Forth Insemination date
				SimpleDateFormat format9 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d9 = null;
				try {
					if(uvm.plannedForthInseminationDate !=null){
						if(uvm.plannedForthInseminationDate.contains("\"")){
							d9 = format9.parse(uvm.plannedForthInseminationDate.replaceAll("\"", ""));
						}else{
							d9 = format9.parse(uvm.plannedForthInseminationDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setPlannedForthInseminationDate(d9);
				
				//success date
				SimpleDateFormat format10 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d10 = null;
				try {
					if(uvm.successDate !=null){
						if(uvm.successDate.contains("\"")){
							d10 = format10.parse(uvm.successDate.replaceAll("\"", ""));
						}else{
							d10 = format10.parse(uvm.successDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setSuccessDate(d10);
				
				//pregnancy Date
				SimpleDateFormat format11 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d11 = null;
				try {
					if(uvm.pregnancyDate !=null){
						if(uvm.pregnancyDate.contains("\"")){
							d11 = format11.parse(uvm.pregnancyDate.replaceAll("\"", ""));
						}else{
							d11 = format11.parse(uvm.pregnancyDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setPregnancyDate(d11);
				
				
				//last Delivery date
				SimpleDateFormat format12 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d12 = null;
				try {
					if(uvm.lastDeliveryDate !=null){
						if(uvm.lastDeliveryDate.contains("\"")){
							d12 = format12.parse(uvm.lastDeliveryDate.replaceAll("\"", ""));
						}else{
							d12 = format12.parse(uvm.lastDeliveryDate);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setLastDeliveryDate(d12);
				
			     //u.setExpectedDeliveryDate(uvm.expectedDeliveryDate);
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
				
				u.update();
				
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
		System.out.println("function is called");
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
				JsonNode catersIds = json.path("catersIds");
				System.out.println("catersIds: "+catersIds);
				if(catersIds.toString() != ""){
					CattleMaster    uId = null;
				    uId = CattleMaster.getUserByCattleId(Integer.parseInt(catersIds.toString().replace("\"", "")));
					if(uId != null){
						u.setCattleMaster(uId);
					}
				}
				
				//u.setDuedate(uvm.duedate);
				//u.setLastDelivaerydate(uvm.lastDelivaerydate);
				u.setMedicationEndDate(uvm.medicationEndDate);
				u.setMedicationStartDate(uvm.medicationStartDate);
				u.setMedicationName(uvm.medicationName);
				u.setMedicationType(uvm.medicationType);
				
				u.setBreed(uvm.breed);
				u.setSubBreed(uvm.subBreed);
				u.setHealthType(uvm.healthType);
				
				u.setVaccinationActualDate(uvm.vaccinationActualDate);
				u.setVaccinationName(uvm.vaccinationName);
				u.setVaccinationPlannedDate(uvm.vaccinationPlannedDate);
				u.setVaccinationType(uvm.vaccinationType);
				//u.setMedicationStartDate(uvm.medicationStartDate);
				
				u.setPregnancyDate(uvm.pregnancyDate);
				u.setPregnant(uvm.pregnant);
				
				u.setLastUpdateDateTime(new Date());
				
				u.update(u);

			}else{
				
				u  = new CattleHealth();
				//u.setDuedate(uvm.duedate);
				//u.setLastDelivaerydate(uvm.lastDelivaerydate);
				u.setMedicationEndDate(uvm.medicationEndDate);
				u.setMedicationStartDate(uvm.medicationStartDate);
				u.setMedicationType(uvm.medicationType);
				u.setMedicationName(uvm.medicationName);
			
				u.setBreed(uvm.breed);
				u.setSubBreed(uvm.subBreed);
				u.setHealthType(uvm.healthType);
				
				u.setVaccinationType(uvm.vaccinationType);
				u.setVaccinationName(uvm.vaccinationName);
				u.setVaccinationActualDate(uvm.vaccinationActualDate);
				u.setVaccinationPlannedDate(uvm.vaccinationPlannedDate);
				
				//u.setMedicationStartDate(uvm.medicationStartDate);
				
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
				JsonNode catersIds = json.path("catersIds");
				System.out.println("catersIds: "+catersIds);
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
				
				
				SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d1 = null;
				try {
					if(uvm.date !=null){
						if(uvm.date.contains("\"")){
							d1 = format.parse(uvm.date.replaceAll("\"", ""));
						}else{
							d1 = format.parse(uvm.date);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setDate(d1);
				/*SimpleDateFormat format1 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d2 = null;
				try {
					if(uvm.time != null){
						if(uvm.time.contains("\"")){
							d2 = format1.parse(uvm.time.replaceAll("\"", ""));
						}else{
							d2 = format1.parse(uvm.time);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				u.setTime(d2);
				*/
				u.setTime(uvm.time);
				
				u.setFatContent(uvm.fatContent);
				u.setSNFContent(uvm.SNFContent);
				u.setDeviceID(uvm.deviceID);
				u.setLastUpdateDateTime(new Date());
				u.setQuantity(uvm.quantity);
				u.setExpectedMilkQuantity(uvm.expectedMilkQuantity);
				
				u.update(u);

			}else{
				
				u  = new CattleOutput();
				SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d1 = null;
				try {
					if(uvm.date !=null){
						if(uvm.date.contains("\"")){
							d1 = format.parse(uvm.date.replaceAll("\"", ""));
						}else{
							d1 = format.parse(uvm.date);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				u.setDate(d1);
				/*SimpleDateFormat format1 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d2 = null;
				try {
					if(uvm.time != null){
						if(uvm.time.contains("\"")){
							d2 = format1.parse(uvm.time.replaceAll("\"", ""));
						}else{
							d2 = format1.parse(uvm.time);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				
				//u.setTime(d2);
				u.setTime(uvm.time);
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
	
	public static Result updateCattleHealthMasterProfileByAdmin(){
		JsonNode json = request().body().asJson();
		System.out.println("com.mnt.dairymgnt.controllers.Application.");
		
		JsonNode catJson = json.get("cat");
		System.out.println(json);
		ObjectMapper userinfoMapper = new ObjectMapper();
		try {
			CattleHealthMasterVM uvm = userinfoMapper.readValue(catJson.traverse(),CattleHealthMasterVM.class);
			CattleHealthMaster u = CattleHealthMaster.getUserByhealthPlanId(uvm.healthPlanId);
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
               
				
				
				
				u.setDuration(uvm.duration);
				u.setFrequency(uvm.frequency);
				u.setMedicationName(uvm.medicationName);
				u.setMedicationType(uvm.medicationType);
				u.setFrequencyType(uvm.frequencyType);
			
				
					u.setLastUpdateDateTime(new Date());
			

				SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d1 = null;
				try {
					if(uvm.medicationStartDateVM !=null){
						if(uvm.medicationStartDateVM.contains("\"")){
							d1 = format.parse(uvm.medicationStartDateVM.replaceAll("\"", ""));
						}else{
							d1 = format.parse(uvm.medicationStartDateVM);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				u.setMedicationStartDate(d1);
				//end date
				SimpleDateFormat format1 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d2 = null;
				try {
					if(uvm.medicationEndDateVM != null){
						if(uvm.medicationEndDateVM.contains("\"")){
							d2 = format.parse(uvm.medicationEndDateVM.replaceAll("\"", ""));
						}else{
							d2 = format.parse(uvm.medicationEndDateVM);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				u.setMedicationEndDate(d2);
				//next date
				SimpleDateFormat format2 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d3 = null;
				try {
					if(uvm.medicationNextDateVM !=null){
						if(uvm.medicationNextDateVM.contains("\"")){
							d3 = format2.parse(uvm.medicationNextDateVM.replaceAll("\"", ""));
						}else{
							d3 = format2.parse(uvm.medicationNextDateVM);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				u.setMedicationNextDate(d3);
				
				u.update();

			}else{
				
				u  = new CattleHealthMaster();
				
				//Breeds b = Breeds.getBreedsById(uvm.Breed);
				//u.setBreed(b.breedName);
				//u.setBreed(uvm.Breed);
				
				
				u.setDuration(uvm.duration);
				u.setFrequency(uvm.frequency);
				u.setFrequencyType(uvm.frequencyType);
				u.setMedicationName(uvm.medicationName);
				u.setMedicationType(uvm.medicationType);
				u.setLastUpdateDateTime(new Date());
				
				SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d1 = null;
				try {
					if(uvm.medicationStartDateVM !=null){
						if(uvm.medicationStartDateVM.contains("\"")){
							d1 = format.parse(uvm.medicationStartDateVM.replaceAll("\"", ""));
						}else{
							d1 = format.parse(uvm.medicationStartDateVM);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				u.setMedicationStartDate(d1);
				//end date
				SimpleDateFormat format1 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d2 = null;
				try {
					if(uvm.medicationEndDateVM != null){
						if(uvm.medicationEndDateVM.contains("\"")){
							d2 = format.parse(uvm.medicationEndDateVM.replaceAll("\"", ""));
						}else{
							d2 = format.parse(uvm.medicationEndDateVM);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				u.setMedicationEndDate(d2);
				//next date
				SimpleDateFormat format2 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d3 = null;
				try {
					if(uvm.medicationNextDateVM !=null){
						if(uvm.medicationNextDateVM.contains("\"")){
							d3 = format2.parse(uvm.medicationNextDateVM.replaceAll("\"", ""));
						}else{
							d3 = format2.parse(uvm.medicationNextDateVM);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				u.setMedicationNextDate(d3);

				
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
			u.update();

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
	
	
	
	
	public static Result updateCattleFeedInventoryProfileByAdmin(){
		JsonNode json = request().body().asJson();
		System.out.println("com.mnt.dairymgnt.controllers.Application.");
		
		JsonNode catJson = json.get("cat");
		System.out.println(json);
		ObjectMapper userinfoMapper = new ObjectMapper();
		try {
			CattleFeedInventoryVM uvm = userinfoMapper.readValue(catJson.traverse(),CattleFeedInventoryVM.class);
			CattleFeedInventory u = CattleFeedInventory.getAllByCattleFeedInventoryId(uvm.cattleFeedInventoryId);
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
               
				
				
				u.setFeedName(uvm.feedName);
				u.setFeedType(uvm.feedType);
				u.setRemark(uvm.remark);
				u.setStockBalance(uvm.stockBalance);
				u.setStockInQuantity(uvm.stockInQuantity);
				u.setStockOutQuantity(uvm.stockOutQuantity);
				u.setStockPerviousBalance(uvm.stockPerviousBalance+ uvm.stockBalance);
				
			
				
					u.setLastUpdateDateTime(new Date());
			

				SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d1 = null;
				try {
					if(uvm.stockInDate !=null){
						if(uvm.stockInDate.contains("\"")){
							d1 = format.parse(uvm.stockInDate.replaceAll("\"", ""));
						}else{
							d1 = format.parse(uvm.stockInDate);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				u.setStockInDate(d1);
				//end date
				SimpleDateFormat format1 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d2 = null;
				try {
					if(uvm.stockOutDate != null){
						if(uvm.stockOutDate.contains("\"")){
							d2 = format.parse(uvm.stockOutDate.replaceAll("\"", ""));
						}else{
							d2 = format.parse(uvm.stockOutDate);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				u.setStockOutDate(d2);
				//next date
				
				u.update();

			}else{
				
				u  = new CattleFeedInventory();
				
				//Breeds b = Breeds.getBreedsById(uvm.Breed);
				//u.setBreed(b.breedName);
				//u.setBreed(uvm.Breed);
				
				
				u.setFeedName(uvm.feedName);
				u.setFeedType(uvm.feedType);
				u.setRemark(uvm.remark);
				u.setStockBalance(uvm.stockBalance);
				u.setStockInQuantity(uvm.stockInQuantity);
				u.setStockOutQuantity(uvm.stockOutQuantity);
				u.setStockPerviousBalance(uvm.stockPerviousBalance);
				
			
				
					u.setLastUpdateDateTime(new Date());
			

				SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d1 = null;
				try {
					if(uvm.stockInDate !=null){
						if(uvm.stockInDate.contains("\"")){
							d1 = format.parse(uvm.stockInDate.replaceAll("\"", ""));
						}else{
							d1 = format.parse(uvm.stockInDate);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				u.setStockInDate(d1);
				//end date
				SimpleDateFormat format1 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d2 = null;
				try {
					if(uvm.stockOutDate != null){
						if(uvm.stockOutDate.contains("\"")){
							d2 = format.parse(uvm.stockOutDate.replaceAll("\"", ""));
						}else{
							d2 = format.parse(uvm.stockOutDate);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				u.setStockOutDate(d2);
				//next date
				
				
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
			u.update();

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
               
				if(uvm.Breed.matches("\\d+")){
					Breeds b = Breeds.getBreedsById(uvm.Breed);
					u.setBreed(b.breedName);
				}else{
					Breeds b = Breeds.getBreedsByName(uvm.Breed);
					u.setBreed(b.breedName);	
				}
				
				
				u.setFeedType(uvm.feedType);
				u.setFeedName(uvm.feedName);
				u.setSKUId(uvm.SKUId);
				u.setQuantity(uvm.quantity);
				u.setStage(uvm.Stage);
				
				u.setSubBreed(uvm.SubBreed);
				u.setMealType(uvm.MealType);
				u.setFeedPlanName(uvm.feedPlanName);
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
						        c.quantity = uv.get(i).quantity;
						        c.feedFiber = 	uv.get(i).feedFiber;
						        c.feedType	=	uv.get(i).feedType;
						        c.feedName = uv.get(i).feedName;
						        c.feedId = uvm.feedId;
						        System.out.println("save ");
						        

										        c.save();
								//u.setCattleFeeds(c);		
							}
						}
					}
				}
				SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d1 = null;
				try {
					if(uvm.feedPlanStartDateVM !=null){
						if(uvm.feedPlanStartDateVM.contains("\"")){
							d1 = format.parse(uvm.feedPlanStartDateVM.replaceAll("\"", ""));
						}else{
							d1 = format.parse(uvm.feedPlanStartDateVM);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				u.setFeedPlanStartDate(d1);
				//end date
				SimpleDateFormat format1 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d2 = null;
				try {
					if(uvm.feedPlanEndDateVM != null){
						if(uvm.feedPlanEndDateVM.contains("\"")){
							d2 = format.parse(uvm.feedPlanEndDateVM.replaceAll("\"", ""));
						}else{
							d2 = format.parse(uvm.feedPlanEndDateVM);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				u.setFeedPlanEndDate(d2);
				u.update(u);

			}else{
				
				u  = new CattleFeedMaster();
				
				//Breeds b = Breeds.getBreedsById(uvm.Breed);
				//u.setBreed(b.breedName);
				//u.setBreed(uvm.Breed);
				if(uvm.Breed.matches("\\d+")){
					Breeds b = Breeds.getBreedsById(uvm.Breed);
					u.setBreed(b.breedName);
				}else{
					Breeds b = Breeds.getBreedsByName(uvm.Breed);
					u.setBreed(b.breedName);	
				}
				
				u.setStage(uvm.Stage);
				u.setFeedType(uvm.feedType);
				u.setFeedName(uvm.feedName);
				u.setSKUId(uvm.SKUId);
				u.setQuantity(uvm.quantity);
				u.setSubBreed(uvm.SubBreed);
				u.setFeedPlanName(uvm.feedPlanName);
				u.setMealType(uvm.MealType);
				u.setLastUpdateDateTime(new Date());
				
				SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d1 = null;
				try {
					if(uvm.feedPlanStartDateVM !=null){
						if(uvm.feedPlanStartDateVM.contains("\"")){
							d1 = format.parse(uvm.feedPlanStartDateVM.replaceAll("\"", ""));
						}else{
							d1 = format.parse(uvm.feedPlanStartDateVM);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				u.setFeedPlanStartDate(d1);
				//end date
				SimpleDateFormat format1 = new SimpleDateFormat("dd-MMMM-yyyy");
				Date d2 = null;
				try {
					if(uvm.feedPlanEndDateVM !=null){
						if(uvm.feedPlanEndDateVM.contains("\"")){
							d2 = format.parse(uvm.feedPlanEndDateVM.replaceAll("\"", ""));
						}else{
							d2 = format.parse(uvm.feedPlanEndDateVM);
						}
					}	
							
					//in milliseconds
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				u.setFeedPlanEndDate(d2);
			
				
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
						        c.quantity = uv.get(i).quantity;
						        c.feedFiber = 	uv.get(i).feedFiber;
						        c.feedName = uv.get(i).feedName;
						        c.feedType = uv.get(i).feedType;
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
	
	@JsonIgnoreProperties(ignoreUnknown = true)
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
				
				u.setDate(uvm.dateOfBirth);
				u.setDeviceID(uvm.deviceID);
				u.setLastUpdateDateTime(new Date());
				u.setQuantity(uvm.quantity);

				JsonNode feeds = json.path("cattleIntakefeedsPlan");
				System.out.println(feeds);
				if(feeds.toString() != null){
					List<CattleIntakePlan> cc = CattleIntakePlan.getCatleFeedsById(u.cattleId);
					if(cc.size() > 0 ){
						for(CattleIntakePlan c: cc){
							c.delete();
						}
					}
						
						ObjectMapper mapper = new ObjectMapper();
						List<CattleIntakePlanVM> uv  = mapper.convertValue(feeds, mapper.getTypeFactory()
						.constructCollectionType(List.class, CattleIntakePlanVM.class));
						 System.out.println("uv :"+uv.size());
						if(uv.size() != 0){
							for(int i=0 ;i <uv.size();i++){
								CattleIntakePlan c =  new CattleIntakePlan();
						        //c.feedPlanEndDate = uv.get(i).feedPlanEndDate;
						        //c.feedPlanStartDate = uv.get(i).feedPlanStartDate;
						       // c.feedPlanName = 	uv.get(i).feedPlanName;
						       // c.quantity = uv.get(i).quantity;
						        c.actualQuantity=uv.get(i).quantity;
						        c.expectedQuantity=uv.get(i).quantity;
						      //  c.actualQuantity=uv.get(i).actualQuantity;
						       // c.expectedQuantity=uv.get(i).expectedQuantity;
						        c.skuId=uv.get(i).skuId;
						        c.feedName=uv.get(i).feedName;
						        c.feedType=uv.get(i).feedType;
						        c.MealType=uv.get(i).MealType;
						       // c.quantity=uv.get(i).quantity;
						        c.feedMasterId = u.cattleId;
						        System.out.println("save ");
						        c.save();
							}
						}
					}
					
				u.update(u);

			}else{
				
				u  = new CattleIntake();

				u.setDate(uvm.dateOfBirth);
				u.setDeviceID(uvm.deviceID);
				u.setLastUpdateDateTime(new Date());
				u.setQuantity(uvm.quantity);
//				u.setActualFeedName(uvm.actualFeedName);
//				u.setActualFeedType(uvm.actualFeedType);
//				u.setExpectedFeedName(uvm.expectedFeedName);
//				u.setExpectedFeedQuantity(uvm.expectedFeedQuantity);
//				u.setExpectedFeedType(uvm.expectedFeedType);
				u.save();
				JsonNode feeds = json.path("cattleIntakefeedsPlan");
				System.out.println(feeds);
				if(feeds.toString() != null){
					List<CattleIntakePlan> cc = CattleIntakePlan.getCatleFeedsById(u.cattleId);
					if(cc.size() > 0 ){
						for(CattleIntakePlan c: cc){
							c.delete();
						}
					}

						ObjectMapper mapper = new ObjectMapper();
						List<CattleIntakePlanVM> uv  = mapper.convertValue(feeds, mapper.getTypeFactory()
						.constructCollectionType(List.class, CattleIntakePlanVM.class));
						 System.out.println("uv :"+uv.size());
						if(uv.size() != 0){
							for(int i=0 ;i <uv.size();i++){
								CattleIntakePlan c =  new CattleIntakePlan();
						       //c.feedPlanEndDate = uv.get(i).feedPlanEndDate;
						       // c.feedPlanStartDate = uv.get(i).feedPlanStartDate;
						       // c.feedPlanName = 	uv.get(i).feedPlanName;
						        c.feedMasterId = u.cattleId;
						       // c.quantity = 	uv.get(i).quantity;
						        c.actualQuantity=uv.get(i).quantity;
						        c.expectedQuantity=uv.get(i).quantity;
						        c.skuId=uv.get(i).skuId;
						        c.feedName=uv.get(i).feedName;
						        c.feedType=uv.get(i).feedType;
						        c.MealType=uv.get(i).MealType;
						       // c.quantity=uv.get(i).quantity;
						        System.out.println("save ");
						        c.save();
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
	
	public static Result getAllFeedType(){
		List<FeedType> feedTypes=FeedType.getAllFeedType();
		HashMap<String, Object>hm=new HashMap<>();
		hm.put("feedTypes", feedTypes);
		return ok(Json.toJson(hm));
	}
	public static Result getAllMealType(){
		List<MealType> mealTypes = MealType.gatAllMealType();
		HashMap<String, Object> hm= new HashMap<>();
		hm.put("mealTypes", mealTypes);
		return ok(Json.toJson(hm));
	}
	
	public static Result getAllStage(){
		List<Stage> stages= Stage.getAllStage();
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("stages", stages);
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
	
	public static Result getPlanDetails()
    {
    	DynamicForm df=Form.form().bindFromRequest();
    	System.out.println(df.get("id"));
    	Long id = Long.parseLong(df.get("id"));
    	
    	CattleIntakePlan plan = CattleIntakePlan.findById(id);
    	System.out.println("i m here");
    	CattleFeedMaster feedMaster = CattleFeedMaster.getUserByfeedId(plan.getFeedMasterId());
    	System.out.println(plan);
    	List<CattleFeeds> list= CattleFeeds.findByFeedPlanId(id);
    	List<CattleFeedsVM> feedsVMs = new ArrayList<CattleFeedsVM>(list.size());
    	for(CattleFeeds cattleFeed : list) {
    		CattleFeedsVM feedsVM = new CattleFeedsVM();
    		feedsVM.feedName=cattleFeed.feedName;
    		feedsVM.feedType=cattleFeed.feedType;
    		feedsVM.skuId=cattleFeed.skuId;
    		feedsVM.quantity=cattleFeed.quantity;
    		if(feedMaster!=null)
    		{
    			feedsVM.MealType=feedMaster.getMealType();
    		}
    		
    		
    		feedsVMs.add(feedsVM);
    	}
    	return ok(Json.toJson(feedsVMs));
    	
    
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
	
	public static Result  checkDuplicateFeedName(){
		JsonNode json = request().body().asJson();
		System.out.println("com.mnt.dairymgnt.controllers.Application.");
		JsonNode feedPlanName = json.get("cat");
		System.out.println("cat"+feedPlanName.toString());
    	
      	System.out.println("feedPlanName ");
    
      	
    
		System.out.println("i am in function");
		List<CattleFeedMaster> cattleFeedMasters = CattleFeedMaster.getAllOnlyFeedCattleMaster();
		for(CattleFeedMaster cfvm : cattleFeedMasters){
			System.out.println(cfvm.getFeedPlanName());
			if(cfvm.getFeedPlanName().equalsIgnoreCase(feedPlanName.asText())){
				return ok("error");
			}
		}
				return ok("success");
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
	
	
	public static Result getAllOnlyFedPlanId(){
		
	    ArrayList<CattleIntakePlanVM> cattleIntakePlans = new ArrayList<>();
	
		List<CattleFeedMaster> cfm =  CattleFeedMaster.getAllOnlyFeedCattleMaster();
	    for(CattleFeedMaster c: cfm){
	    	
	    	CattleIntakePlanVM v = new CattleIntakePlanVM();
	    	//v.feedPlanName = c.feedPlanName;
	    	
	    	if(c.feedPlanStartDate != null){
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT.format(c.feedPlanStartDate);
				//v.feedPlanStartDate = date;
			}else{
				//v.feedPlanStartDate = "";
			}
			if(c.feedPlanEndDate != null){
				SimpleDateFormat DATE_FORMAT1 = new SimpleDateFormat("dd-MMMM-yyyy");
		        String date = DATE_FORMAT1.format(c.feedPlanEndDate);
			//	v.feedPlanEndDate = date;
			}else{
		//		v.feedPlanEndDate = "";
			}
	 //   	v.quantity = c.quantity;
	    	cattleIntakePlans.add(v);
	    	
	    }
		
		return ok(Json.toJson(cattleIntakePlans));
	}
	public static  Result getFeedCattleMasterDetails()
	{

    	DynamicForm df=Form.form().bindFromRequest();
    	System.out.println(df.get("id"));
    	Long id = Long.parseLong(df.get("id"));
    	CattleMaster obj = CattleMaster.findById(id);
    	String breed = obj.getBreed();
    	String subBreed = obj.getSubBreed();
    	Date crDate = new Date();
    	List<CattleFeedMasterVM> vmList = new ArrayList<>();
    	if(obj !=null){
    		List<CattleFeedMaster> objList = CattleFeedMaster.findByBreeAndSubBreed(obj.getBreed(), obj.getSubBreed());
    		for (CattleFeedMaster ob : objList) {
    			if((crDate.after(ob.feedPlanStartDate) && crDate.before(ob.feedPlanEndDate)) || crDate.equals(ob.feedPlanStartDate) || crDate.equals(ob.feedPlanEndDate)){
    				CattleFeedMasterVM vm = new CattleFeedMasterVM();
    				vm.feedId = ob.getFeedId();
    				vm.feedPlanName = ob.getFeedPlanName();
    				vmList.add(vm);
    			}
    		}
    	}
    	
    	
    	/*CattleMaster details = CattleMaster.findById(id);
		List <CattleFeedMaster> list = CattleFeedMaster.
		CattleFeedMaster details1 = (CattleFeedMaster) CattleFeedMaster.getAllOnlyFeedCattleMaster();
    	System.out.println("i m here");
    	System.out.println("i m here");
    	
    	if(details.breed==details1.Breed & details.subBreed == details1.SubBreed ){
    		System.out.println("working");
    		
    		
    	}
    	
    	return ok(Json.toJson(feedsVMs));*/
		return ok(Json.toJson(vmList));
	}
	
	
}
	



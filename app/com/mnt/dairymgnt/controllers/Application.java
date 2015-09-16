package com.mnt.dairymgnt.controllers;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;



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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.mnt.dairymgnt.VM.CattleFeedMasterVM;
import com.mnt.dairymgnt.VM.CattleHealthVM;
import com.mnt.dairymgnt.VM.CattleIntakeVM;
import com.mnt.dairymgnt.VM.CattleMasterVM;
import com.mnt.dairymgnt.VM.CattleOutputVM;
import com.mnt.dairymgnt.VM.ChildCattleVM;
import com.mnt.dairymgnt.VM.OrgnizationVM;
import com.mnt.dairymgnt.VM.PregnancyCattleVM;
import com.mnt.dairymgnt.VM.UserVM;
import com.mnt.dairymgnt.models.Breeds;
import com.mnt.dairymgnt.models.CattleFeedMaster;
import com.mnt.dairymgnt.models.CattleHealth;
import com.mnt.dairymgnt.models.CattleIntake;
import com.mnt.dairymgnt.models.CattleMaster;
import com.mnt.dairymgnt.models.CattleOutput;
import com.mnt.dairymgnt.models.ChildCattle;
import com.mnt.dairymgnt.models.Entities;
import com.mnt.dairymgnt.models.Oraganization;
import com.mnt.dairymgnt.models.Permissions;
import com.mnt.dairymgnt.models.PregnantCattle;
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
			cfvm.feedFiber = u.feedFiber;
			cfvm.feedId = u.feedId;
			cfvm.feedprotine =    u.feedprotine;
			cfvm.feedType = u.feedType;
			cfvm.feedVitamins = u.feedVitamins;
			cfvm.feedWaterContent = u.feedWaterContent;
			cfvm.lastUpdateDateTime =  u.lastUpdateDateTime;
			cfvm.users = u.users;
			cfvm.oraganization = u.oraganization;
			cmvm.add(cfvm);
		}
		HashMap  <String ,Object> hm = new HashMap();
		hm.put("feedcaters",cmvm);
		hm.put("userCount", count);
		return ok(Json.toJson(hm));
	}
	
	
	public static Result getAllOnlyFeedCattleMaster(){
		List<CattleFeedMaster> cattleFeedMasters = CattleFeedMaster.getAllOnlyFeedCattleMaster();
        
		ArrayList<CattleFeedMasterVM> cmvm = new ArrayList<>();
		
		for(CattleFeedMaster u : cattleFeedMasters){
			CattleFeedMasterVM cfvm = new CattleFeedMasterVM();
			cfvm.feedFiber = u.feedFiber;
			cfvm.feedId = u.feedId;
			cfvm.feedprotine =    u.feedprotine;
			cfvm.feedType = u.feedType;
			cfvm.feedVitamins = u.feedVitamins;
			cfvm.feedWaterContent = u.feedWaterContent;
			cfvm.lastUpdateDateTime =  u.lastUpdateDateTime;
			cfvm.users = u.users;
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
			cfvm.expectedDeliveryDate  = u.expectedDeliveryDate;
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
			cfvm.actualFeedType =  u.actualFeedType;
			cfvm.actualFeedName  =  u.actualFeedName;
			cfvm.expectedFeedType = u.expectedFeedType;
			cfvm.expectedFeedName = u.expectedFeedName;
			cfvm.expectedFeedQuantity = u.expectedFeedQuantity;
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
			cfvm.actualFeedType =  u.actualFeedType;
			cfvm.actualFeedName  =  u.actualFeedName;
			cfvm.expectedFeedType = u.expectedFeedType;
			cfvm.expectedFeedName = u.expectedFeedName;
			cfvm.expectedFeedQuantity = u.expectedFeedQuantity;
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
					    u.permissions.add(p);
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

				u.setBreed(uvm.breed);
				u.setCattleIdentificationMark(uvm.cattleIdentificationMark);
				u.setGender(uvm.gender);
				u.setLastUpdateDateTime(new Date());
				u.setName(uvm.name);
				u.update(u);

			}else{
				
				u  = new CattleMaster();

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
				u.setExpectedDeliveryDate(uvm.expectedDeliveryDate);
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
				u.setExpectedDeliveryDate(uvm.expectedDeliveryDate);
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
				
				u.setFeedFiber(uvm.feedFiber);
				u.setFeedprotine(uvm.feedprotine);
				u.setFeedType(uvm.feedType);
				u.setFeedVitamins(uvm.feedVitamins);
				u.setFeedWaterContent(uvm.feedWaterContent);
				u.setLastUpdateDateTime(new Date());
				u.update(u);

			}else{
				
				u  = new CattleFeedMaster();

				u.setFeedFiber(uvm.feedFiber);
				u.setFeedprotine(uvm.feedprotine);
				u.setFeedType(uvm.feedType);
				u.setFeedVitamins(uvm.feedVitamins);
				u.setFeedWaterContent(uvm.feedWaterContent);
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
				u.setActualFeedName(uvm.actualFeedName);
				u.setActualFeedType(uvm.actualFeedType);
				u.setExpectedFeedName(uvm.expectedFeedName);
				u.setExpectedFeedQuantity(uvm.expectedFeedQuantity);
				u.setExpectedFeedType(uvm.expectedFeedType);
				
				u.update(u);

			}else{
				
				u  = new CattleIntake();

				u.setDate(uvm.date);
				u.setDeviceID(uvm.deviceID);
				u.setLastUpdateDateTime(new Date());
				u.setQuantity(uvm.quantity);
				u.setActualFeedName(uvm.actualFeedName);
				u.setActualFeedType(uvm.actualFeedType);
				u.setExpectedFeedName(uvm.expectedFeedName);
				u.setExpectedFeedQuantity(uvm.expectedFeedQuantity);
				u.setExpectedFeedType(uvm.expectedFeedType);
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
	
	
	
	public static Result checkUserExits(String userId){
		Users u  = Users.getUserByEmail(userId);
		if(u == null){
			return ok("available");
		}else{
			return ok("");
		}
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
}
	


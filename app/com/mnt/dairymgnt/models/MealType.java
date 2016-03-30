package com.mnt.dairymgnt.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class MealType extends Model 
{
	@Id
	public Long mealTypeId;
	public String mealType;
	public Long getMealTypeId() {
		return mealTypeId;
	}
	public void setMealTypeId(Long mealTypeId) {
		this.mealTypeId = mealTypeId;
	}
	public String getMealType() {
		return mealType;
	}
	public void setMealType(String mealType) {
		this.mealType = mealType;
	}
	
	public static Finder<Long, MealType> find= new Finder<Long,MealType>(Long.class, MealType.class);
	
	public static List<MealType> gatAllMealType(){
		return find.all();
	}
	

}

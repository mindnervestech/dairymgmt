package com.mnt.dairymgnt.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Breeds extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	public String breedName;
	
	@OneToMany
	private List<Subbreed> sunbreed;

	public static Finder<Long, Breeds> find = new Finder<Long, Breeds>(
			Long.class, Breeds.class);
	
	public static List<Breeds> getAllBreeds(){
		return find.all();
	}
	
	public static Breeds getBreedsByName(String name){
		return find.where().eq("breedName", name).findUnique();
	}
	public static Breeds getBreedsById(String id){
		long idss = Long.parseLong(id);
		return find.where().eq("id", idss).findUnique();
	}
	
	public static Breeds getBreedIdByName(String breeeName){
		return find.where().eq("breedName", breeeName).findUnique();
		 
	}
}

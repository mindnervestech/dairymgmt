package com.mnt.dairymgnt.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	
	
	public static Finder<Long, Breeds> find = new Finder<Long, Breeds>(
			Long.class, Breeds.class);
	
	public static List<Breeds> getAllBreeds(){
		return find.all();
	}
}

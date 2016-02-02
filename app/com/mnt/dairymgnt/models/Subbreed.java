package com.mnt.dairymgnt.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Subbreed extends Model {

	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long Id;
	public String sub_breed;

	
	@ManyToOne
	private Breeds breeds;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getSub_breed() {
		return sub_breed;
	}

	public void setSub_breed(String sub_breed) {
		this.sub_breed = sub_breed;
	}

	
	public static Finder<Long, Subbreed> find = new Finder<Long, Subbreed>(
			Long.class, Subbreed.class);
    
	public static List<Subbreed> findBySubbreedId(Long id) {
		System.out.println("ID: "+id);
		return find.where().eq("breeds_id", id).findList();
	}
	public static List<Subbreed> getSunBreedById(String id){
		long ids  = Long.parseLong(id);
		return find.where().eq("Id", ids).findList();
	}

}

	package com.mnt.dairymgnt.models;

	import java.util.List;

	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;

	import play.db.ebean.Model;
	import play.db.ebean.Model.Finder;


	@Entity
	public class Entities  extends Model{
		
		/**
		 * 
		 */
		
		private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		public int id;
		public String entityName;
		
		
		public static Finder<Long, Entities> find = new Finder<Long, Entities>(
				Long.class, Entities.class);
		
		public static List<Entities> getAllEntities(){
			return find.all();
		}
		
	
	}

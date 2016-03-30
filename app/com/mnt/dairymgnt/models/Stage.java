package com.mnt.dairymgnt.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Stage extends Model
{
	@Id
	public Long stageId;
	public String stage;
	public Long getStageId() {
		return stageId;
	}
	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public static Finder<Long, Stage> find = new Finder<Long,Stage>(Long.class, Stage.class);
	
	public static List <Stage> getAllStage(){
		return find.all();
	}
}

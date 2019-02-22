package com.zens.unify.model;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zens.unify.utils.StringUtil;
import com.zens.unify.utils.TimeUtil;

public class CPTask {
	private static final String tableName = " unify_cp_task ";
	
	public Record getTaskByID(long id){
		return Db.findById(tableName, "ID", id);
	}
	
	public List<Record> getTasksByID(String ids){
		return Db.find("select * from " + tableName + " where ID in (" + ids + ")");
	}
	
	public List<Record> getTasksByType(int taskType){
		return Db.find("select * from " + tableName + " where taskType = " + taskType);
	}
	
	public Page<Record> getTasksByPagin(int page, int pageSize, int taskType, int interTag, int deleted, String orderCase){
		return Db.paginate(page, pageSize, "select * ", "from " + tableName + " where intertag = " + interTag + " and " + ( taskType > 0 ? ( "tasktype = "  + taskType + " and ") : "") + "deleted = ? " + (orderCase.equals("") ? "" : ("order by " + orderCase)), deleted);
	}
	
	public boolean insertTask(Map<String, String[]> map){
		Record record = new Record();
		
		String key = null, value = null;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			key = entry.getKey();
			value = entry.getValue()[0];
			
			if(key.equals("CreateTime")){
				record.set("CreateTime", TimeUtil.getCurrTime());
			}
			if( !key.equals("ID") && StringUtil.isAvailable(value) ){
				record.set(key, value);
			}
		}
		
		return Db.save(tableName, "ID", record);
	}
	
	public boolean updateTask(Record record){
		return Db.update(tableName, "ID", record);
	}
	
	/**
	 * 表单传递参数
	 * @param map 表单参数
	 * @return
	 */
	public boolean updateTask(Map<String, String[]> map){
		Record record = new Record();
		
		String key = null, value = null;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			key = entry.getKey();
			value = entry.getValue()[0];
			if(key.equals("UpdateTime")){
				record.set("UpdateTime", TimeUtil.getCurrTime());
			}
			
			if( StringUtil.isAvailable(value) ){
				record.set(key, value);
			}
		}
		return Db.update(tableName, "ID", record);
	}

	public boolean removeTasks(String ids){
		if(ids.isEmpty()){
			return false;
		}
		
		return Db.update("update " + tableName + " set deleted = 1 where id in (" + ids + ")") > 0;
	}
	
	public boolean deleteTasks(String ids){
		if(ids.isEmpty()){
			return false;
		}
			
		return Db.update("delete from " + tableName + " where id in (" + ids + ")") > 0;
	}
}

package com.zens.unify.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zens.unify.utils.StringUtil;
import com.zens.unify.utils.TimeUtil;

public class CPData {
	private static final String tableName = " unify_cp_data ";
	
	public Record getDataByTaskID(long taskID){
		return Db.findFirst("select * from " + tableName + " where TaskID = " + taskID);
	}
	
	public Page<Record> getDatasByPagin(int page, int pageSize, int deleted, String orderCase){
		return Db.paginate(page, pageSize, "select * ", "from " + tableName + " where deleted = ? " + (orderCase.equals("") ? "" : ("order by " + orderCase)), deleted);
	}
	
	public boolean insertData(Map<String, String[]> map){
		Record record = new Record();
		
		String key = null, value = null;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			key = entry.getKey();
			value = entry.getValue()[0];
			
			if( !key.equals("ID") && StringUtil.isAvailable(value) ){
				record.set(key, value);
			}
		}
		
		return Db.save(tableName, "ID", record);
	}
	
	public boolean updateData(Record record){
		return Db.update(tableName, "ID", record);
	}
	
	public boolean updateData(Map<String, String[]> map){
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

	public boolean removeDatas(String ids){
		if(ids.isEmpty()){
			return false;
		}
			
		return Db.update("update " + tableName + " set deleted = 1 where id in (" + ids + ")") > 0;
	}
	
	public boolean deleteDatas(String ids){
		if(ids.isEmpty()){
			return false;
		}
			
		return Db.update("delete from " + tableName + " where id in (" + ids + ")") > 0;
	}
}

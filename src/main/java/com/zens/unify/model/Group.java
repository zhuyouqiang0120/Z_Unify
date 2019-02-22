package com.zens.unify.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zens.unify.utils.GUIDGenerator;
import com.zens.unify.utils.StringUtil;
import com.zens.unify.utils.TimeUtil;

public class Group {
	private static final String tableName = "unify_group";
	
	public Page<Record> getGroupsByPagin(int page, int pageSize, int deleted, String orderCase){
		return Db.paginate(page, pageSize, "select * ", "from " + tableName + " where deleted = ? " + (orderCase.equals("") ? "" : ("order by " + orderCase)), deleted);
	}
	
	public boolean insertGroup(Map<String, String[]> map){
		Record record = new Record();
		
		String key = null, value = null;
		//设置唯一  GID
		record.set("GID", GUIDGenerator.guid());
		
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
	
	public boolean updateGroup(Map<String, String[]> map){
		Record record = new Record();
		
		String key = null, value = null;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			key = entry.getKey();
			value = entry.getValue()[0];
			
			if(key.equals("UpdateTime")){
				record.set("UpdateTime", TimeUtil.getCurrTime());
			}
			
			if( !key.equals("Creator") && StringUtil.isAvailable(value) ){
				record.set(key, value);
			}
		}
		return Db.update(tableName, "ID", record);
	}

	public boolean removeGroups(String ids){
		if(ids.isEmpty()){
			return false;
		}
		return Db.update("update " + tableName + " set deleted = 1 where id in (" + ids + ")") > 0;
	}
	
	public boolean deleteGroups(String ids){
		if(ids.isEmpty()){
			return false;
		}
	
		return Db.update("delete from " + tableName + " where id in (" + ids + ")") > 0;
	}
}

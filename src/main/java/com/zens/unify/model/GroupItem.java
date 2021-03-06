package com.zens.unify.model;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zens.unify.utils.StringUtil;
import com.zens.unify.utils.TimeUtil;

public class GroupItem {
	
	private static final String tableName = "unify_group_item";
	
	public List<Record> getGroupItems(int freezed, int deleted){
		return Db.find("select * from " + tableName + " where freezed = '" + freezed + "' and deleted = " + deleted + " order by CreateTime desc");
	}
	
	public Page<Record> getGroupItemsByPagin(int page, int pageSize, int deleted, String GID, String orderCase){
		return Db.paginate(page, pageSize, "select * ", "from " + tableName + " where GID = '" + GID + "' and deleted = " + deleted + (orderCase.equals("") ? " " : (" order by " + orderCase)));
	}
	
	public boolean insertGroupItem(Map<String, String[]> map){
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
	
	public boolean updateGroupItem(long id, String vote){
		Record record = new Record();
		record.set("Vote", vote);
		return Db.update(tableName, "ID", record);
	}
	
	public boolean updateGroupItem(Map<String, String[]> map){
		Record record = new Record();
		
		String key = null, value = null;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			key = entry.getKey();
			value = entry.getValue()[0];
			
			if(key.equals("UpdateTime")){
				record.set("UpdateTime", TimeUtil.getCurrTime());
			}
			
			if( StringUtil.isAvailable(value) ){
				record.set(key, entry.getValue()[0]);
			}
		}
		return Db.update(tableName, "ID", record);
	}

	public boolean freezeGroupItem(String ids, int freezed) {
		if(!StringUtil.isAvailable(ids)){
			return false;
		}
		return Db.update("update " + tableName + " set Freezed = " + freezed + ", UpdateTime = '" + TimeUtil.getCurrTime() + "' where ID in (" + ids + ")") > 0;
	}
	
	public boolean removeGroupItems(String ids){
		if(ids.isEmpty()){
			return false;
		}
		return Db.update("update " + tableName + " set deleted = 1 where id in (" + ids + ")") > 0;
	}
	
	public boolean deleteGroupItems(String ids){
		if(ids.isEmpty()){
			return false;
		}
	
		return Db.update("delete from " + tableName + " where id in (" + ids + ")") > 0;
	}
}

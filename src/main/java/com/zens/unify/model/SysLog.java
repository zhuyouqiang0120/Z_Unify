package com.zens.unify.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zens.unify.utils.StringUtil;
import com.zens.unify.utils.TimeUtil;

public class SysLog {
	private static final String tableName = " unify_sys_log ";
	
	public Page<Record> getSysLogsByPagin(int page, int pageSize, int deleted, String orderCase){
		return Db.paginate(page, pageSize, "select * ", "from " + tableName + " where deleted = ? " + (orderCase.equals("") ? "" : ("order by " + orderCase)), deleted);
	}
	
	public boolean insertSysLog(Map<String, String[]> map){
		Record record = new Record();
		
		String key = null, value = null;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			key = entry.getKey();
			value = entry.getValue()[0];
			
			if(key.equals("CurrTime")){
				record.set("CurrTime", TimeUtil.getCurrTime());
			}
			if( !key.equals("ID") && StringUtil.isAvailable(value) ){
				record.set(key, value);
			}
		}
		
		return Db.save(tableName, "ID", record);
	}
	
	public boolean updateSysLog(Map<String, String[]> map){
		Record record = new Record();
		
		String key = null, value = null;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			key = entry.getKey();
			value = entry.getValue()[0];
			
			if( StringUtil.isAvailable(value) ){
				record.set(key, value);
			}
		}
		return Db.update(tableName, "ID", record);
	}

	public boolean removeSysLogs(String ids){
		if(ids.isEmpty()){
			return false;
		}
		
		return Db.update("update " + tableName + " set deleted = 1 where id in (" + ids + ")") > 0;
	}
	
	public boolean deleteSysLogs(String ids){
		if(ids.isEmpty()){
			return false;
		}

		return Db.update("delete from " + tableName + " where id in (" + ids + ")") > 0;
	}
}

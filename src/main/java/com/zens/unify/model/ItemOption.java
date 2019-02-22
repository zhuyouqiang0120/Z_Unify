package com.zens.unify.model;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zens.unify.utils.StringUtil;
import com.zens.unify.utils.TimeUtil;

public class ItemOption {
private static final String tableName = "unify_item_option";
	
	public Page<Record> getItemOptionsByPagin(int page, int pageSize, int deleted, String orderCase){
		return Db.paginate(page, pageSize, "select * ", "from " + tableName + " where deleted = " + deleted + (orderCase.equals("") ? " " : (" order by " + orderCase)));
	}
	
	public List<Record> isExist(Map<String, String[]> map){
		String GID = map.get("GID")[0];
		String ItemID = map.get("ItemID")[0]; 
		String MacAddr = map.get("MacAddr")[0]; //单个机顶盒只能投一次
		
		return Db.find("select * from " + tableName + " where GID = '" + GID + "' and ItemID = " + ItemID + " and MacAddr = '" + MacAddr + "'");
	}
	
	public boolean insertItemOption(Map<String, String[]> map) throws UnsupportedEncodingException{
		Record record = new Record();
		String key = null, value = null;

		record.set("CreateTime", TimeUtil.getCurrTime());
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			key = entry.getKey();
			value = entry.getValue()[0];
			
			if( key.equals("Title") ){
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
			}
			if( !key.equals("ID") && StringUtil.isAvailable(value) ){
				record.set(key, value);
			}
		}
		
		String[] optionIDs = map.get("OptionID");
		String[] options = map.get("Option");
		boolean flag = false;
		for(int i = 0; i < optionIDs.length; ++ i){
			record.set("OptionID", Long.parseLong(optionIDs[i]));
			record.set("Option", new String(options[i].getBytes("ISO-8859-1"), "UTF-8"));
			flag = Db.save(tableName, "ID", record);
		}
		return flag;
	}
	
	public boolean updateItemOption(Map<String, String[]> map){
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
	
	public boolean updateItemOption(Record record){
		record.set("UpdateTime", TimeUtil.getCurrTime());
		return Db.update(tableName, "ID", record);
	}
	
	public List<Record> countVoteResult(String GID, long ItemID){
		System.out.println("select ItemID, OptionID, count(OptionID) as Vote from " + tableName + " where GID = '" + GID  + "' and ItemID = " + ItemID + " group by OptionID order by Vote desc");
		return Db.find("select ItemID, OptionID, count(OptionID) as Vote from " + tableName + " where GID = '" + GID  + "' and ItemID = " + ItemID + " group by OptionID order by Vote desc" );
	}
	
	public boolean freezeItemOption(String ids, int freezed) {
		if(!StringUtil.isAvailable(ids)){
			return false;
		}
		return Db.update("update " + tableName + " set Freezed = " + freezed + ", UpdateTime = '" + TimeUtil.getCurrTime() + "' where ID in (" + ids + ")") > 0;
	}
	
	public boolean removeItemOptions(String ids){
		if(ids.isEmpty()){
			return false;
		}
		return Db.update("update " + tableName + " set deleted = 1 where id in (" + ids + ")") > 0;
	}
	
	public boolean deleteItemOptions(String ids){
		if(ids.isEmpty()){
			return false;
		}
	
		return Db.update("delete from " + tableName + " where id in (" + ids + ")") > 0;
	}
}

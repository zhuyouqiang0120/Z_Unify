package com.zens.unify.controller;

import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zens.unify.model.GroupItem;
import com.zens.unify.service.SystemLogger;

public class GroupItemController extends Controller {

	private GroupItem item = new GroupItem();
	
	/**
	 * 对外接口
	 * 提供页面调取数据
	 */
	public void getVoteItems(){
		renderJson( item.getGroupItems(1, 0));
	}
	
	public void getGroupItems(){
		int currPage = getParaToInt("currPage");
		int pageSize = getParaToInt("pageSize");
		int deleted = getParaToInt("deleted");
		String GID = getPara("GID");
		String orderCase = getPara("orderCase");
		
		Page<Record> records = item.getGroupItemsByPagin(currPage, pageSize, deleted, GID, orderCase);
		renderJson(records);
	}
	
	public void insertGroupItem(){
		Map<String, String[]> map = getParaMap();
		
		boolean flag = false;
		try{
			flag = item.insertGroupItem(map);
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("添加组条目异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		} finally {
			SystemLogger.warn("添加组条目", "添加组条目 " + map.get("Title") + " " + ( flag ? "成功" : "失败"), (String) getSessionAttr("loginUser"));
		}
		
		renderJson("{\"success\":" + flag + "}");
	}
	
	public void updateGroupItem(){
		Map<String, String[]> map = getParaMap();
		
		boolean flag = false;
		try{
			flag = item.updateGroupItem(map);
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("更新组条目异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		} finally {
			SystemLogger.warn("更新组条目", "更新组条目 " + map.get("Title") + " " + ( flag ? "成功" : "失败"), (String) getSessionAttr("loginUser"));
		}
		
		renderJson("{\"success\":" + flag + "}");
	}
	
	public void freezeGroupItem(){
		int freezed = getParaToInt("freezed");
		String ids = getPara("ids"); 
		
		boolean flag = false;
		try {
			flag = item.freezeGroupItem(ids, freezed);
			SystemLogger.warn(freezed == 1 ? "组条目发布" : "组条目锁定", (freezed == 1 ? "发布" : "锁定") + "组条目" + ( flag ? "成功" : "失败") + ", ID集合为(" + ids + ")", (String)getSessionAttr("loginUser"));
		} catch(Exception e) {
			flag = false;
			SystemLogger.error(freezed == 1 ? "组条目发布异常" : "组条目锁定异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		}
		renderJson("{\"success\":" + flag + "}");
	}
	
	public void removeGroupItem(){
		String ids = getPara("ids");
		boolean flag = false;
		try{
			flag = item.removeGroupItems(ids);
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("移除组条目", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		} finally {
			SystemLogger.warn("移除组条目", "移除组条目 " + ( flag ? "成功" : "失败") + "，ID集合为" + ids, (String) getSessionAttr("loginUser"));
		}
		
		renderJson("{\"success\":" + flag + "}");
	}
	
	public void deleteGroupItem(){
		String ids = getPara("ids");
		boolean flag = false;
		try{
			flag = item.deleteGroupItems(ids);
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("删除组条目", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		} finally {
			SystemLogger.warn("删除组条目", "删除组条目 " + ( flag ? "成功" : "失败") + "，ID集合为" + ids, (String) getSessionAttr("loginUser"));
		}
		renderJson("{\"success\":" + flag + "}");
	}
}

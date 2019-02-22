package com.zens.unify.controller;

import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zens.unify.model.Group;
import com.zens.unify.service.SystemLogger;

public class GroupController extends Controller {
	
	private Group group = new Group();
	
	public void getGroups(){
		int currPage = getParaToInt("currPage");
		int pageSize = getParaToInt("pageSize");
		int deleted = getParaToInt("deleted");
		String orderCase = getPara("orderCase");
		
		Page<Record> records = group.getGroupsByPagin(currPage, pageSize, deleted, orderCase);
		renderJson(records);
	}
	
	public void insertGroup(){
		Map<String, String[]> map = getParaMap();
		
		boolean flag = false;
		try{
			flag = group.insertGroup(map);
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("添加组异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		} finally {
			SystemLogger.warn("添加组", "添加组 " + map.get("Name") + " " + ( flag ? "成功" : "失败"), (String) getSessionAttr("loginUser"));
		}
		
		renderJson("{\"success\":" + flag + "}");
	}
	
	public void updateGroup(){
		Map<String, String[]> map = getParaMap();
		
		boolean flag = false;
		try{
			flag = group.updateGroup(map);
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("更新组异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		} finally {
			SystemLogger.warn("更新组", "更新组 " + map.get("Name") + " " + ( flag ? "成功" : "失败"), (String) getSessionAttr("loginUser"));
		}
		
		renderJson("{\"success\":" + flag + "}");
	}
	
	public void removeGroup(){
		String ids = getPara("ids");
		boolean flag = false;
		try{
			flag = group.removeGroups(ids);
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("移除组", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		} finally {
			SystemLogger.warn("移除组", "移除组 " + ( flag ? "成功" : "失败") + "，ID集合为" + ids, (String) getSessionAttr("loginUser"));
		}
		
		renderJson("{\"success\":" + flag + "}");
	}
	
	public void deleteGroup(){
		String ids = getPara("ids");
		boolean flag = false;
		try{
			flag = group.deleteGroups(ids);
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("删除组", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		} finally {
			SystemLogger.warn("删除组", "删除组 " + ( flag ? "成功" : "失败") + "，ID集合为" + ids, (String) getSessionAttr("loginUser"));
		}
		renderJson("{\"success\":" + flag + "}");
	}
}

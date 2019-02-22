package com.zens.unify.controller;

import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zens.unify.model.User;
import com.zens.unify.service.SystemLogger;

public class UserController extends Controller {

	private User user = new User();
	
	public void getUsers(){
		int currPage = getParaToInt("currPage");
		int pageSize = getParaToInt("pageSize");
		int deleted = getParaToInt("deleted");
		String orderCase = getPara("orderCase");
		
		Page<Record> records = user.getUsersByPagin(currPage, pageSize, deleted, orderCase);
		renderJson(records);
	}
	
	public void insertUser(){
		Map<String, String[]> map = getParaMap();
		
		boolean flag = false;
		try{
			flag = user.insertUser(map);
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("添加用户异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		} finally {
			SystemLogger.warn("添加用户", "添加用户 " + map.get("Username") + " " + ( flag ? "成功" : "失败"), (String) getSessionAttr("loginUser"));
		}
		
		renderJson("{\"success\":" + flag + "}");
	}
	
	public void updateUser(){
		Map<String, String[]> map = getParaMap();
		
		boolean flag = false;
		try{
			flag = user.updateUser(map);
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("更新用户异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		} finally {
			SystemLogger.warn("更新用户", "更新用户 " + map.get("Username") + " " + ( flag ? "成功" : "失败"), (String) getSessionAttr("loginUser"));
		}
		
		renderJson("{\"success\":" + flag + "}");
	}
	
	public void removeUser(){
		String ids = getPara("ids");
		boolean flag = false;
		try{
			flag = user.removeUsers(ids);
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("移除用户", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		} finally {
			SystemLogger.warn("移除用户", "移除用户 " + ( flag ? "成功" : "失败") + "，ID集合为" + ids, (String) getSessionAttr("loginUser"));
		}
		
		renderJson("{\"success\":" + flag + "}");
	}
	
	public void deleteUser(){
		String ids = getPara("ids");
		boolean flag = false;
		try{
			flag = user.deleteUsers(ids);
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("删除用户", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		} finally {
			SystemLogger.warn("删除用户", "删除用户 " + ( flag ? "成功" : "失败") + "，ID集合为" + ids, (String) getSessionAttr("loginUser"));
		}
		renderJson("{\"success\":" + flag + "}");
	}
}

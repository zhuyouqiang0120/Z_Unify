package com.zens.unify.controller;

import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zens.unify.model.CPTask;
import com.zens.unify.service.JobUnify;
import com.zens.unify.service.SystemLogger;

public class CPTaskController extends Controller {
	private CPTask task = new CPTask();
	
	public void getTasks(){
		int currPage = getParaToInt("currPage");
		int pageSize = getParaToInt("pageSize");
		int taskType = getParaToInt("taskType");
		int interTag = getParaToInt("interTag");
		int deleted = getParaToInt("deleted");
		
		String orderCase = getPara("orderCase");
		
		Page<Record> records = task.getTasksByPagin(currPage, pageSize, taskType, interTag, deleted, orderCase);
		renderJson(records);
	}
	
	public void runTask(){
		JobUnify job = new JobUnify();
		String ids = getPara("ids");
		String operator = getSessionAttr("loginUser");
		//System.out.println(job.batchProbe(ids, operator).toString());
		renderJson(job.batchProbe(ids, operator));
	}
	
	public void insertTask(){
		Map<String, String[]> map = getParaMap();
		
		boolean flag = false;
		try{
			flag = task.insertTask(map);
			SystemLogger.warn("添加接口任务", "添加接口任务 " + map.get("Name") + " " + ( flag ? "成功" : "失败"), (String) getSessionAttr("loginUser"));
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("添加接口任务异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		}
		/**
		 * 添加任务自动加入或者停止系统定时任务
		 */
		renderJson("{\"success\":" + flag + "}");
	}
	
	public void updateTask(){
		Map<String, String[]> map = getParaMap();
		boolean flag = false;
		try{
			flag = task.updateTask(map);
			SystemLogger.warn("更新接口任务", "更新接口任务 " + map.get("Name") + " " + ( flag ? "成功" : "失败"), (String) getSessionAttr("loginUser"));
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("更新接口任务异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		}
		/**
		 * 更改任务自动加入或者停止系统定时任务
		 */
		renderJson("{\"success\":" + flag + "}");
	}
	
	public void removeTask(){
		String ids = getPara("ids");
		boolean flag = false;
		try{
			flag = task.removeTasks(ids);
			SystemLogger.warn("移除接口任务", "移除接口任务 " + ( flag ? "成功" : "失败") + "，ID集合为" + ids, (String) getSessionAttr("loginUser"));
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("移除接口任务异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		}
		
		renderJson("{\"success\":" + flag + "}");
	}
	
	public void deleteTask(){
		String ids = getPara("ids");
		boolean flag = false;
		try{
			flag = task.deleteTasks(ids);
			SystemLogger.warn("删除接口任务", "删除接口任务 " + ( flag ? "成功" : "失败") + "，ID集合为" + ids, (String) getSessionAttr("loginUser"));
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("删除接口任务异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		}
		renderJson("{\"success\":" + flag + "}");
	}
}

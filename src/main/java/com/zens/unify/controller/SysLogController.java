package com.zens.unify.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zens.unify.model.SysLog;

public class SysLogController extends Controller {
	private SysLog sysLog = new SysLog();
	
	public void getSysLogs(){
		int currPage = getParaToInt("currPage");
		int pageSize = getParaToInt("pageSize");
		int deleted = getParaToInt("deleted");
		String orderCase = getPara("orderCase");
		
		Page<Record> records = sysLog.getSysLogsByPagin(currPage, pageSize, deleted, orderCase);
		renderJson(records);
	}
	
	public void insertSysLog(){
		renderJson("{\"success\":" + sysLog.insertSysLog(getParaMap()) + "}");
	}
	
	public void updateSysLog(){
		renderJson("{\"success\":" + sysLog.updateSysLog(getParaMap()) + "}");
	}
	
	public void removeSysLog(){
		renderJson("{\"success\":" + sysLog.removeSysLogs(getPara("ids")) + "}");
	}
	
	public void deleteSysLog(){
		renderJson("{\"success\":" + sysLog.deleteSysLogs(getPara("ids")) + "}");
	}
}

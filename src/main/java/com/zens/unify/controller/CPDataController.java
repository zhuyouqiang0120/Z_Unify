package com.zens.unify.controller;

import java.util.Map;

import net.sf.json.JSONObject;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.zens.unify.model.CPData;

public class CPDataController extends Controller {

	private CPData data = new CPData();
	
	public void getData(){
		long id = getParaToLong("ID");
		Record record = data.getDataByTaskID(id); 
		renderText(new JSONObject().element("CP", record.getStr("Code"))
									.element("OriginTime", record.getStr("OriginTime"))
									.element("UpdateTime", record.getStr("UpdateTime"))
									.element("newData", record.getStr("Data"))
									.element("oldData", record.getStr("BackupData"))
									.toString());
	} 
	
	public void getDatas(){
		int currPage = getParaToInt("currPage");
		int pageSize = getParaToInt("pageSize");
		int deleted = getParaToInt("deleted");
		String orderCase = getPara("orderCase");
		
		Page<Record> records = data.getDatasByPagin(currPage, pageSize, deleted, orderCase);
		renderJson(records);
	}
	
	public void insertData(){
		Map<String, String[]> map = getParaMap();
		renderJson("{\"success\":" + data.insertData(map) + "}");
	}
	
	public void updateData(){
		Map<String, String[]> map = getParaMap();
		renderJson("{\"success\":" + data.updateData(map) + "}");
	}
	
	public void removeData(){
		renderJson("{\"success\":" + data.removeDatas(getPara("ids")) + "}");
	}
	
	public void deleteData(){
		renderJson("{\"success\":" + data.deleteDatas(getPara("ids")) + "}");
	}
}

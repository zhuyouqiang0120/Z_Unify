package com.zens.unify.controller;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jfinal.core.Controller;
import com.zens.unify.service.MysqlRecorder;
import com.zens.unify.service.SystemLogger;
import com.zens.unify.utils.PropertiesKey;
import com.zens.unify.utils.StringUtil;

public class BackupController extends Controller {

	public void getBackupFiles(){
		int currPage = getParaToInt("currPage");
		int pageSize = getParaToInt("pageSize");
		final String order = getPara("order");
		final String orderCase = getPara("orderCase");
		
		File backupFile = new File(PropertiesKey.backupPath);
		if(!backupFile.exists()){
			backupFile.mkdirs();
		}
		File[] files = backupFile.listFiles();
		List<JSONObject> fileArray = new ArrayList<JSONObject>();
		
		DecimalFormat dFormat = new DecimalFormat("#.00");
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		for(File file : files){
			if(file.isFile()){
				JSONObject obj = new JSONObject();
				obj.element("time", dateFormat.format(file.lastModified())).element("name", file.getName()).element("size", dFormat.format( file.length() / 1024) + " KB").element("path", PropertiesKey.backupPath + file.getName());
				fileArray.add(obj);
			}
		}
		Collections.sort(fileArray, new Comparator<JSONObject>(){
			public int compare(JSONObject o1, JSONObject o2) {
				final String _order = StringUtil.isAvailable(orderCase) ? orderCase.split(" ")[0] : order; 
				return o1.getString( _order ).compareTo(o2.getString( _order ));
			}
		});
		
		List<JSONObject> newFiles = new ArrayList<JSONObject>();
		int begin = (currPage - 1) * pageSize, len = begin + pageSize; 
		for(; begin < len && begin < fileArray.size(); ++ begin){
			newFiles.add( fileArray.get(begin) );
		}
		
		renderText(new JSONObject().element("totalRow", fileArray.size()).element("list", JSONArray.fromObject(newFiles)).toString());
	}
	
	public void backUpDB(){
		String fileName = PropertiesKey.database + "_" + new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss").format(new Date()) + ".sql";
		
		boolean flag = false;
		try{
			flag = MysqlRecorder.backUpTable(PropertiesKey.database, fileName);
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("数据库备份异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		} finally {
			SystemLogger.warn("数据库备份", "备份文件为" + fileName + "，备份" + ( flag ? "成功" : "失败"), (String) getSessionAttr("loginUser"));
		}
		
		renderJson("{\"success\":" + flag + "}");
	}
	
	public void restoreDB(){
		String fileName = getPara("fileName");
		boolean flag = false;
		try{
			flag = MysqlRecorder.restoreTable(PropertiesKey.database, fileName);
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("数据库还原异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		} finally {
			SystemLogger.warn("数据库备份", "备份文件为" + fileName + "，备份" + ( flag ? "成功" : "失败"), (String) getSessionAttr("loginUser"));
		}
		renderJson("{\"success\":" + flag + "}");
	}
	
	public void deleteDBFile(){
		String fileName = getPara("name");
		File file = new File(PropertiesKey.backupPath + fileName);

		boolean flag = false;
		try{
			if(file.exists()){
				flag = file.delete();
			}
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("数据库备份文件删除异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		} finally {
			SystemLogger.warn("数据库备份文件删除", "数据库备份文件为" + fileName + "，文件删除" + ( flag ? "成功" : "失败"), (String) getSessionAttr("loginUser"));
		}
		
		renderJson("{\"success\":" + flag + "}");
	}
}

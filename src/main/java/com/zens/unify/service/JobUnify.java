package com.zens.unify.service;

import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Record;
import com.zens.unify.model.CPData;
import com.zens.unify.model.CPTask;
import com.zens.unify.utils.StringUtil;
import com.zens.unify.utils.TimeUtil;

public class JobUnify implements Job{

	private CPTask task = new CPTask();
	private CPData data = new CPData();
	private ProbeHelper probe = new ProbeHelper();
	
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		JobDataMap jobMap = jobContext.getJobDetail().getJobDataMap();
		
		String code = jobMap.getString("Code"),
			   url = jobMap.getString("Url");
		
		long id = jobMap.getLong("ID");

		runProbe(id, code, url);
	}

	/**
	 * 获取爬取数据并存储
	 * 
	 * @param id 任务ID
	 * @param code 任务索引名称
	 * @param url 任务URL
	 * @return
	 */
	public boolean runProbe(long id, String code, String url){
		boolean flag = false;
		if(url.isEmpty() || code.isEmpty() || id <= 0){
			return flag;
		}
		if(code.indexOf("weather") != -1){
			code = "weather";
		}
		String result = "";
		try{
			switch(code){
				case "weather":
					result = probe.weather(url);
					break; 
				case "air": 
					result = probe.air(url);
					break;
				case "oil_city": 
					result = probe.oil(url);
					break;
				case "lottery": 
					result = probe.lottery(url);
					break;
				case "shgold": 
					result = probe.gold(url);
					break;
				case "rmbquot": 
					result = probe.rmbquot(url);
					break;
				case "smMail":
					break;
			}
	
			if( !result.isEmpty() || !result.equals("[]") || !result.equals("{}") ){
				Record record = data.getDataByTaskID(id);
				if( record == null ){
					record = new Record();
					record.set("TaskID", id);
				}
				String bakData = record.getStr("Data");
				
				record.set("Code", code);
				record.set("Data", result);
				if( !bakData.isEmpty() || !bakData.equals("[]") || !bakData.equals("{}")){
					record.set("BackupData", bakData);
				}
				record.set("UpdateTime", TimeUtil.getCurrTime());
				
				flag = data.updateData(record);
				
				SystemLogger.warn("数据抓取@" + code, "数据抓取" + (flag ? "成功" : "失败"), "SYSTEM");
			}
		} catch (Exception e){
			flag = false;
			SystemLogger.error("数据抓取异常@" + code, e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		}
		return flag;
	}
	
	public List<Record> batchProbe(String ids, String operator){
		boolean flag = false;
		List<Record> results = new ArrayList<Record>();
		List<Record> records = task.getTasksByID(ids);
		
		if( records.size() > 0){
			for(Record record : records){
				String url = record.getStr("Url"),
					   code = record.getStr("Code"),
					   resultHtml = "";
				if(code.indexOf("weather") != -1){
					code = "weather";
					}
				System.out.println(code);
				Record result = new Record();
				try{
					switch(code){
						case "weather":
							resultHtml = probe.weather(url);
							break; 
						case "air": 
							resultHtml = probe.air(url);
							break;
						case "oil_city": 
							resultHtml = probe.oil(url);
							break;
						case "lottery": 
							resultHtml = probe.lottery(url);
							break;
						case "shgold": 
							resultHtml = probe.gold(url);
							break;
						case "rmbquot": 
							resultHtml = probe.rmbquot(url);
							break;
						case "smMail":
							break;
					}  

					if( !resultHtml.isEmpty() || !resultHtml.equals("[]") || !resultHtml.equals("{}") ){
						Record taskData = data.getDataByTaskID(record.getLong("ID"));
						if( taskData == null ){
							taskData = new Record();
							taskData.set("TaskID", record.getLong("ID"));
						}
						
						String bakData = taskData.getStr("Data");
						
						taskData.set("Code", code);
						taskData.set("Data", resultHtml);
						
						if( !bakData.isEmpty() || !bakData.equals("[]") || !bakData.equals("{}")){
							taskData.set("BackupData", bakData);
							taskData.set("OriginTime", taskData.getStr("UpdateTime"));
						}					
						taskData.set("UpdateTime", TimeUtil.getCurrTime());
						flag = data.updateData(taskData);
						//SystemLogger.warn("定时任务@" + code, "数据抓抓取" + (flag ? "成功" : "失败"), (StringUtil.isAvailable(operator) ? operator : "SYSTEM"));
					}
				}catch( Exception e){
					flag = false;
					SystemLogger.error("数据抓取异常@" + code, e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
				}
				
				result.set("taskID", record.get("ID"));
				result.set("name", record.getStr("Name"));System.out.println(record.getStr("Name"));
				result.set("success", flag);System.out.println(flag);
				results.add(result);
			}
		}
		
		return results;
	}
}

package com.zens.unify.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.dreampie.quartz.QuartzKey;
import cn.dreampie.quartz.job.QuartzCronJob;

import com.jfinal.plugin.activerecord.Record;
import com.zens.unify.model.CPTask;

/**
 * 任务定时器
 * @author huyi@zensvision.com
 * 2015年4月30日
 */
public class TaskQuartzSchedule {

	private CPTask task = new CPTask();
	private Map<Integer, QuartzCronJob> quartzTasks = new HashMap<Integer, QuartzCronJob>();
	private List<Record> tasks = new ArrayList<Record>();
	
	public TaskQuartzSchedule() {
	}
	
	/**
	 * 初始化定时任务
	 */
	public boolean start(){
		Record record = null;
		QuartzCronJob job = null;
		
		try{
			tasks = task.getTasksByType(1); //获取定时任务集合
			
			for(int i = 0; i < tasks.size(); ++i){
				record = tasks.get(i);
				job = new QuartzCronJob(new QuartzKey(record.getLong("ID"), record.getStr("Name"), " TimerSchedule"), record.getStr("Cycle"), JobUnify.class);
				
				job.addParam("ID", record.getLong("ID"));
				job.addParam("Name", record.getStr("Name"));
				job.addParam("Code", record.getStr("Code"));
				job.addParam("Url", record.getStr("Url"));
				
				quartzTasks.put(i, job);
				job.start();
				
				record.set("Status", (job.getState().toString().equals("STARTED") ? 1 : 0));
				
				boolean flag = task.updateTask(record); //更新任务状态
				
				SystemLogger.warn(record.getStr("Name"), "开启定时任务@" + record.getStr("Code") + ( flag ? "成功" : "失败"), "SYSTEM");
			}
		} catch( Exception e ){
			SystemLogger.error("系统定时任务开启异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
			return false;
		}
		return true;
	}
	
	/**
	 * 停止所有定时任务 
	 * @return 
	 */
	public boolean stop(){
		try{
			if(!quartzTasks.isEmpty()){
				QuartzCronJob job = null;
				Map<String, Object> params = null;
				
				for (Map.Entry<Integer, QuartzCronJob> entry : quartzTasks.entrySet()) {
				   job = entry.getValue();
				   params = job.getParams();
				   
				   boolean flag = false;
				   if( !job.getState().toString().equals("STARTED") ){
					   flag = task.updateTask(tasks.get(entry.getKey())); //更新任务状态
				   }
				   
				   SystemLogger.warn(params.get("Name").toString(), "开启定时任务@" + params.get("Code").toString() + ( flag ? "成功" : "失败"), "SYSTEM");
				}
			}
		} catch( Exception e ){
			SystemLogger.error("系统定时任务关闭异常", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
			return false;
		}
		return true;
	}
}

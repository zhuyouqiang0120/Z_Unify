package com.zens.unify.controller;

import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.zens.unify.model.ItemOption;
import com.zens.unify.service.SystemLogger;

public class ItemOptionController extends Controller{

	private ItemOption option = new ItemOption();
	
	/**
	 * 页面调取接口
	 * 投票接口
	 */
	@Before(Tx.class)
	public void vote(){
		Map<String, String[]> map = getParaMap();
		boolean flag = false;
		int vote = -1;
		try{
			List<Record> records = option.isExist(map);
			flag = records.size() > 0;
			if( flag ){
				vote = 0; //已投票，不能再投
			}else{
				flag = option.insertItemOption(map);
				vote = 1; //投票成功
			}
		} catch(Exception e) {
			flag = false;
		}
		renderJson("{\"success\":" + flag + ", \"vote\":" + vote + "}");
	}
	
	/**
	 * 获取投票结果接口
	 * 页面也会调取
	 */
	public void getVoteResult(){
		String GID = getPara("GID");
		long ItemID = getParaToLong("ItemID");
		
		List<Record> records = option.countVoteResult(GID, ItemID);
		renderJson( records );
	}
	
	public void getItemOptions(){
		int currPage = getParaToInt("currPage");
		int pageSize = getParaToInt("pageSize");
		int deleted = getParaToInt("deleted");
		String orderCase = getPara("orderCase");
		
		Page<Record> records = option.getItemOptionsByPagin(currPage, pageSize, deleted, orderCase);
		renderJson(records);
	}
	
	public void removeItemOption(){
		String ids = getPara("ids");
		boolean flag = false;
		try{
			flag = option.removeItemOptions(ids);
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("移除投票详情", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		} finally {
			SystemLogger.warn("移除投票详情", "移除投票详情 " + ( flag ? "成功" : "失败") + "，ID集合为" + ids, (String) getSessionAttr("loginUser"));
		}
		
		renderJson("{\"success\":" + flag + "}");
	}
	
	public void deleteItemOption(){
		String ids = getPara("ids");
		boolean flag = false;
		try{
			flag = option.deleteItemOptions(ids);
		} catch(Exception e) {
			flag = false;
			SystemLogger.error("删除投票详情", e.getCause().getClass() + ", Cause By: " + e.getCause().getMessage(), "EXCEPTION");
		} finally {
			SystemLogger.warn("删除投票详情", "删除投票详情 " + ( flag ? "成功" : "失败") + "，ID集合为" + ids, (String) getSessionAttr("loginUser"));
		}
		renderJson("{\"success\":" + flag + "}");
	}
}

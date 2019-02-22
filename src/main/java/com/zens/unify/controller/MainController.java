package com.zens.unify.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.zens.unify.interceptor.SessionInterceptor;
import com.zens.unify.model.Menu;

public class MainController extends Controller {

	/**
	 * 主页session检测
	 */
	@Before(SessionInterceptor.class)
	public void home(){
		renderJsp("home.jsp");
	}
	
	public void head(){
		renderJsp("head.jsp");
	}
	
	public void center(){
		Menu menu = new Menu();
		setAttr( "menus", JsonKit.toJson(menu.getMenuList()) );
		renderJsp("center.jsp");
	}
	
	public void left(){
		Menu menu = new Menu();
		setAttr( "menus", JsonKit.toJson(menu.getMenuList()) );
		renderJsp("left.jsp");
	}
	
	public void right(){
		renderJsp("right.jsp");
	}
	
	public void bottom(){
		renderJsp("bottom.jsp");
	}
	
	public void task(){
		renderJsp("cp/task.jsp");
	}
	
	public void nativeTask(){
		renderJsp("cp/native.jsp");
	}
	
	public void vote(){
		renderJsp("strategy/vote.jsp");
	}
	
	public void vote_detail(){
		renderJsp("strategy/vote_detail.jsp");
	}
	
	public void sysLog(){
		renderJsp("log/sys.jsp");
	}
	
	public void userIndex(){
		renderJsp("sys/user.jsp");
	}
	
	public void backup(){
		renderJsp("sys/data.jsp");
	}
	
	public void sys_log(){
		renderJsp("log/sys.jsp");
	}
}

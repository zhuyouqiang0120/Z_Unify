package com.zens.unify.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.zens.unify.interceptor.LoginInterceptor;
import com.zens.unify.service.SystemLogger;

public class LoginController extends Controller {
	
	public void index(){
		renderJsp("login.jsp");
	}
	
	/**
	 * 登陆拦截
	 */
	@Before(LoginInterceptor.class)
	public void login(){
		SystemLogger.warn("用户登录系统", getPara("username") + "登入系统。", "LOGIN");
		redirect("/main/home");
	}
	
	public void logout(){
		SystemLogger.warn("用户退出系统", getSessionAttr("loginUser") + "登出系统。", "LOGIN");
		getSession().invalidate();
		renderJsp("login.jsp");
	}
}

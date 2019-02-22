package com.zens.unify.config;

import cn.dreampie.quartz.QuartzPlugin;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.zens.unify.controller.BackupController;
import com.zens.unify.controller.CPDataController;
import com.zens.unify.controller.CPTaskController;
import com.zens.unify.controller.FileUploadController;
import com.zens.unify.controller.GroupController;
import com.zens.unify.controller.GroupItemController;
import com.zens.unify.controller.ItemOptionController;
import com.zens.unify.controller.LoginController;
import com.zens.unify.controller.MainController;
import com.zens.unify.controller.SysLogController;
import com.zens.unify.controller.UserController;
import com.zens.unify.service.TaskQuartzSchedule;
import com.zens.unify.shanghai.PublicController;
import com.zens.unify.shanghai.TrafficController;

public class UnifyConfig extends JFinalConfig {

	private TaskQuartzSchedule schdule = new TaskQuartzSchedule();
	/**
	 * JFinal 常量值配置
	 */
	public void configConstant(Constants constants) {
		constants.setDevMode(true); //开发模式
		
		constants.setBaseViewPath("WEB-INF/views"); //路径
		constants.setViewType(ViewType.JSP); //视图类型
		constants.setErrorView(404, "/WEB-INF/views/error/404.jsp");
		constants.setErrorView(500, "/WEB-INF/views/error/500.jsp");
	}

	/**
	 * JFinal处理器，可以接管所有的web请求，对应用可以完全控制
	 */
	public void configHandler(Handlers handlers) {
		ContextPathHandler path = new ContextPathHandler("ContextPath");
		handlers.add(path); //添加全局路径，提供给jsp定位使用
	}

	/**
	 * 拦截器,此处为 Global 全局拦截
	 */
	public void configInterceptor(Interceptors interceptors) {
		
	}

	/**
	 * 配置JFinal插件
	 */
	public void configPlugin(Plugins plugins) {
		loadPropertyFile("jdbc.properties"); //load配置文件
		C3p0Plugin c3p0 = new C3p0Plugin(getProperty("jdbc.url"), getProperty("jdbc.username"), getProperty("jdbc.password"));
		plugins.add(c3p0); //添加插件
		
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0);
		plugins.add(arp);
		arp.setDialect(new MysqlDialect()); //配置MySQL方言
		
		//配置定時插件
		//plugins.add(new QuartzPlugin());
	}

	/**
	 * 路由配置
	 */
	public void configRoute(Routes routes) {
		routes.add("/", LoginController.class);
		routes.add("/main", MainController.class);
		routes.add("/user", UserController.class);
		routes.add("/backup", BackupController.class); 
		routes.add("/task", CPTaskController.class);
		routes.add("/data", CPDataController.class);
		routes.add("/sysLog", SysLogController.class);
		routes.add("/traffic", TrafficController.class);
		routes.add("/public", PublicController.class);
		routes.add("/group", GroupController.class);
		routes.add("/item", GroupItemController.class);
		routes.add("/option", ItemOptionController.class);
		routes.add("/upload", FileUploadController.class);
	}
	/*
	public void afterJFinalStart() {
		super.afterJFinalStart();
		schdule.start();
	}
	
	public void beforeJFinalStop() {
		super.beforeJFinalStop();
		schdule.stop();
	}
	*/
}

package com.zens.unify.utils;

import com.jfinal.kit.Prop;

public class PropertiesKey {

	private static final Prop prop = new Prop("jdbc.properties");
	
	public static final String imgPath = prop.get("imgPath");
	public static final String backupPath = prop.get("backupPath");
	public static final String database = prop.get("database");
	
	public static final String mysqluname = prop.get("jdbc.username");	//MySQL用户名
	public static final String mysqlpwd = prop.get("jdbc.password");	//MySQL密码

}

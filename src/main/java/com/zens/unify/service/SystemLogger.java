package com.zens.unify.service;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.zens.unify.utils.StringUtil;

public class SystemLogger {

	private static Logger logger = Logger.getLogger("SYSTEM");;
	
	/**
	 * 崩溃级别
	 * 
	 * @param username
	 * @param message
	 */
	public static void fatal(String action, String summary, String operator) {
		MDC.put("action", action);
		MDC.put("operator", StringUtil.isAvailable(operator) ? operator : "");
		logger.fatal(summary);
	}

	/**
	 * 错误级别
	 * 
	 * @param username
	 * @param message
	 */
	public static void error(String action, String summary, String operator) {
		MDC.put("action", action);
		MDC.put("operator", StringUtil.isAvailable(operator) ? operator : "");
		logger.error(summary);
	}

	/**
	 * 消息级别
	 * 
	 * @param username
	 * @param message
	 */
	public static void info(String action, String summary, String operator) {
		MDC.put("action", action);
		MDC.put("operator", StringUtil.isAvailable(operator) ? operator : "");
		logger.info(summary);
	}

	/**
	 * 警告级别
	 * 
	 * @param username
	 * @param message
	 */
	public static void warn(String action, String summary, String operator) {
		MDC.put("action", action);
		MDC.put("operator", StringUtil.isAvailable(operator) ? operator : "");
		logger.warn(summary);
	}

	/**
	 * 调试级别
	 * 
	 * @param username
	 * @param message
	 */
	public static void debug(String action, String summary, String operator) {
		MDC.put("action", action);
		MDC.put("operator", StringUtil.isAvailable(operator) ? operator : "");
		logger.debug(summary);
	}

}

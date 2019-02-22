package com.zens.unify.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;

import com.zens.unify.utils.PropertiesKey;

/**
 * 数据库快速备份
 * @author HUYI
 */
public class MysqlRecorder implements Serializable{
	
	private static final long serialVersionUID = -6940737202672774676L;
	
	/**
	 * 备份数据库
	 * @param database 数据库名
	 * @param bakName 备份文件名
	 * @return
	 */
	public static boolean backUpTable(String database, String bakName){
		boolean res = false; 
		Runtime run = Runtime.getRuntime();
		System.out.println("mysqldump --add-drop-table -u" + PropertiesKey.mysqluname + " -p" + PropertiesKey.mysqluname + " " + PropertiesKey.database);
		try {
			Process process = run.exec("mysqldump --add-drop-table -u" + PropertiesKey.mysqluname + " -p" + PropertiesKey.mysqlpwd + " " + PropertiesKey.database);
			InputStream in = process.getInputStream();
			InputStreamReader inr = new InputStreamReader(in,"utf-8");
			StringBuffer sb = new StringBuffer(1000);
			String string = "";
			BufferedReader br = new BufferedReader(inr);
			File file = new File(PropertiesKey.backupPath);
			if(!file.exists())
				file.mkdirs();
			FileOutputStream out = new FileOutputStream(file + "/" + bakName);
			OutputStreamWriter write = new OutputStreamWriter(out,"utf-8");
			int i = 0;
			
			while( (string = br.readLine()) != null){
				sb.append(string + "\r\n");
				i ++;
				if(i == 100){
					write.write(sb.toString());
					write.flush();
					sb = new StringBuffer();
					i = 0;
				}
			}
			
			write.write(sb.toString());
			write.flush();
			write.close();
			out.close();
			br.close();
			inr.close();
			in.close();
			process.destroy();
			res = true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return res;
	}
	
	/**
	 * 还原数据库
	 * @param database 数据库名
	 * @param fileName 备份的全文件名
	 * @return
	 */
	public static boolean restoreTable(String database, String fileName){
		boolean res = false;
		Runtime run = Runtime.getRuntime();
		try {
			Process pro = run.exec("mysql -u"+ PropertiesKey.mysqluname +" -p" + PropertiesKey.mysqlpwd + " " + PropertiesKey.database);
			OutputStream out = pro.getOutputStream();
			String instr, outstr;
			StringBuffer sb = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));
			OutputStreamWriter writer = new OutputStreamWriter(out,"utf-8");
			int i = 0;
			while((instr = reader.readLine()) != null){
				sb.append(instr + "\r\n");
				i ++;
				if(i == 100){
					writer.write(sb.toString());
					writer.flush();
					sb = new StringBuffer();
					i = 0;
				}
			}
			outstr = sb.toString();
			
			writer.write(outstr);
			writer.flush();
			out.close();
			reader.close();
			writer.close();
			pro.destroy();
			res = true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return res;
	}
}

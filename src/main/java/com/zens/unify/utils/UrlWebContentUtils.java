package com.zens.unify.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 抓取网页内容
 * @author fangzhu
 * @time 2013-4-3 上午10:32:56
 */
public class UrlWebContentUtils {
	Logger log = Logger.getLogger(getClass());
	
	private String charset = "utf-8";

	public UrlWebContentUtils() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UrlWebContentUtils(String charset) {
		// TODO Auto-generated constructor stub
		this.charset = charset;
	}

	/**
	 * 读取一个网页全部内容
	 */
	public String getOneHtml(String uri){
		String temp;
		StringBuffer sb = new StringBuffer(1024);
		try {
			URL url = new URL(new String(uri.getBytes(),charset));
			URLConnection urlconn = url.openConnection();
			// 抽象类 URLConnection是所有类的超类，它代表应用程序和 URL之间的通信链接，通过在 URL 上调用openConnection 方法创建连接对象
			urlconn.connect(); // 使用 connect 方法建立到远程对象的实际连接
			HttpURLConnection httpconn = (HttpURLConnection) urlconn;
			// 每个HttpURLConnection实例都可用于生成单个请求，但是其他实例可以透明地共享连接到HTTP 服务器的基础网络
			int HttpResult = httpconn.getResponseCode();
			// getResponseCode可以从 HTTP 响应消息获取状态码
			if (HttpResult != HttpURLConnection.HTTP_OK) {
				System.out.println(HttpResult);
			} else {
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(),charset));// 读取网页全部内容
				while ((temp = in.readLine()) != null) {
					sb.append(temp);
				}
				in.close();
			}
		} catch (MalformedURLException me) {
			log.info("你输入的URL格式有问题！请仔细输入" + uri);
		} catch (IOException e) {
			log.info("链接超时");
		}
		return sb.toString().replace("<link.*?>", "").replace("<script.*?</script>", "").replace("<style.*?</style>", "");
	}

	/**
	 * 
	 * @param s
	 * @return 获得网页标题
	 */
	public String getTitle(String s) {
		String regex;
		String title = "";
		List<String> list = new ArrayList<String>();
		regex = "<title>.*?</title>";
		Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
		Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		for (int i = 0; i < list.size(); i++) {
			title = title + list.get(i);
		}
		return outTag(title);
	}

	/**
	 * 
	 * @param s
	 * @return 获得a链接
	 */
	public List<String> getA(String s) {
		String regex;
		List<String> list = new ArrayList<String>();
		regex = "<a[^>]*href=(\"([^\"]*)\"|\'([^\']*)\'|([^\\s>]*))[^>]*>(.*?)</a>";
		Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		return list;
	}
	
	public List<String> getLink(String s){
		String regex;
		List<String> list = new ArrayList<String>();
		regex = "<link.*?>";
		Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		return list;
	}
	/**
	 * 
	 * @param s
	 * @return 获得脚本代码
	 */
	public List<String> getScript(String s) {
		String regex;
		List<String> list = new ArrayList<String>();
		regex = "<script.*?</script>";
		Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		return list;
	}

	/**
	 * 
	 * @param s
	 * @return 获得CSS
	 */
	public List<String> getCSS(String s) {
		String regex;
		List<String> list = new ArrayList<String>();
		regex = "<style.*?</style>";
		Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		return list;
	}

	/**
	 * 
	 * @param s
	 * @return 去掉标记
	 */
	public String outTag(String s) {
		return s.replaceAll("<.*?>", "");
	}
	
	
	public static void main(String[] args) {
		UrlWebContentUtils web = new UrlWebContentUtils();
		System.err.println(web.getOneHtml("http://www.zensvision.com/WebContentGrab/index.php?city=松江&type=weather"));
	}
	
}
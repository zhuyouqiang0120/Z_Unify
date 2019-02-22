package com.zens.unify.entity;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 *{'resultcode':'200','reason':'successed','result':[{'wid':'00','weather':'晴'},{'wid':'01','weather':'多云'},{'wid':'02','weather':'阴'},{'wid':'03','weather':'阵雨'},{'wid':'04','weather':'雷阵雨'},{'wid':'05','weather':'雷阵雨伴有冰雹'},{'wid':'06','weather':'雨夹雪'},{'wid':'07','weather':'小雨'},{'wid':'08','weather':'中雨'},{'wid':'09','weather':'大雨'},{'wid':'10','weather':'暴雨'},{'wid':'11','weather':'大暴雨'},{'wid':'12','weather':'特大暴雨'},{'wid':'13','weather':'阵雪'},{'wid':'14','weather':'小雪'},{'wid':'15','weather':'中雪'},{'wid':'16','weather':'大雪'},{'wid':'17','weather':'暴雪'},{'wid':'18','weather':'雾'},{'wid':'19','weather':'冻雨'},{'wid':'20','weather':'沙尘暴'},{'wid':'21','weather':'小雨-中雨'},{'wid':'22','weather':'中雨-大雨'},{'wid':'23','weather':'大雨-暴雨'},{'wid':'24','weather':'暴雨-大暴雨'},{'wid':'25','weather':'大暴雨-特大暴雨'},{'wid':'26','weather':'小雪-中雪'},{'wid':'27','weather':'中雪-大雪'},{'wid':'28','weather':'大雪-暴雪'},{'wid':'29','weather':'浮尘'},{'wid':'30','weather':'扬沙'},{'wid':'31','weather':'强沙尘暴'},{'wid':'53','weather':'霾'}],'error_code':0}
 * 
 * todo： 天气
 * @author vector
 * @date 2014年7月15日 上午9:38:38
 */
public class WeatherUni {
	private String wid;
	private String weather;
	public String getWid() {
		return wid;
	}
	public void setWid(String wid) {
		this.wid = wid;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	
	@Override
	public String toString() {
		return "WeatherUni [wid=" + wid + ", weather=" + weather + "]";
	}
	/**
	 * 返回对象map
	 * @return
	 */
	public static Map<String, String> toMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("00", "晴");
		map.put("01", "多云");
		map.put("02", "阴");
		map.put("03", "阵雨");
		map.put("04", "雷阵雨");
		map.put("05", "雷阵雨伴有冰雹");
		map.put("06", "雨夹雪");
		map.put("07", "小雨");
		map.put("08", "中雨");
		map.put("09", "大雨");
		map.put("10", "暴雨");
		map.put("11", "大暴雨");
		map.put("12", "特大暴雨");
		map.put("13", "阵雪");
		map.put("14", "小雪");
		map.put("15", "中雪");
		map.put("16", "大雪");
		map.put("17", "暴雪");
		map.put("18", "雾");
		map.put("19", "冻雨");
		map.put("20", "沙尘暴");
		map.put("21", "小雨-中雨");
		map.put("22", "中雨-大雨");
		map.put("23", "大雨-暴雨");
		map.put("24", "暴雨-大暴雨");
		map.put("25", "大暴雨-特大暴雨");
		map.put("26", "小雪-中雪");
		map.put("27", "中雪-大雪");
		map.put("28", "大雪-暴雪");
		map.put("29", "浮尘");
		map.put("30", "扬沙");
		map.put("31", "强沙尘暴'");
		map.put("53", "霾");
		return map;
	}
	
}

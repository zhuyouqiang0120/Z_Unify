package com.zens.unify.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.JsonKit;

/*"result":[
{
    "lotteryId":1,
    "lottery":"双色球"
    },
    {
    "lotteryId":2,
    "lottery":"福彩3D"
    },
    {
    "lotteryId":3,
    "lottery":"新3D"
    },
    {
    "lotteryId":4,
    "lottery":"七乐彩"
    },
    {
    "lotteryId":5,
    "lottery":"超级大乐透"
    },
    {
    "lotteryId":6,
    "lottery":"七星彩"
    },
    {
    "lotteryId":7,
    "lottery":"排列3"
    },
    {
    "lotteryId":8,
    "lottery":"排列5"
    },
    {
    "lotteryId":9,
    "lottery":"14场胜负彩"
    },
    {
    "lotteryId":10,
    "lottery":"任选9场"
    },
    {
    "lotteryId":11,
    "lottery":"四场进球"
    },
    {
    "lotteryId":12,
    "lottery":"六场半全场"
    }
],*/
public class Lottery {
	private Integer lotteryId;
	private String name;
	private String times;//期数
	private String time;//开奖时间
	private String num;//开奖号码
	private String json;
	
	public Lottery() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Integer getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Integer lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	public void setJson(List<Lottery> lotterys) {
		this.json = JsonKit.toJson(lotterys);
	}
	public void setJson(Lottery lottery) {
		this.json = JsonKit.toJson(lottery);
	}

	/**
	 * 
	 * @return
	 */
	public static Map<Integer, String> getMap(){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "双色球");
		map.put(2, "福彩3D");
		map.put(3, "新3D");
		map.put(4, "七乐彩");
		map.put(5, "超级大乐透");
		map.put(6, "七星彩");
		map.put(7, "排列3");
		map.put(8, "排列5");
		map.put(9, "14场胜负彩");
		map.put(10, "任选9场");
		map.put(11, "四场进球");
		map.put(12, "六场半全场");
		
		return map;
	}
	/**
	 * 
	 * @return
	 */
	public static Map<String, Integer> getMapKeyByName(){
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("双色球",1);
		map.put("福彩3D",2);
		map.put("新3D",3);
		map.put("七乐彩",4);
		map.put("超级大乐透",5);
		map.put("七星彩",6);
		map.put("排列3",7);
		map.put("排列5",8);
		map.put("14场胜负彩",9);
		map.put("任选9场",10);
		map.put("四场进球",11);
		map.put("六场半全场",12);
		
		return map;
	}
}

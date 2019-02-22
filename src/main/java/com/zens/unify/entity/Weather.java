package com.zens.unify.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author vector
 * @date 2014年10月23日 下午2:22:02
 * @project Z_UnifyService
 * @package com.zens.unify.entity Weather.java
 *
 */
public class Weather {
	
	private List<Future> future = new ArrayList<Future>();
	
	private Today today;
	
	/**
	 * 今天
	 * @author vector
	 * @date 2014年10月23日 下午2:14:54
	 * @project Z_UnifyService
	 * @package com.zens.unify.entity Weather.java
	 *
	 */
	public class Today {
		private String temper1;
		private String temper2;
		private String weather;
		private String wind;
		private String fl;
		private String week;
		private String city;
		private String date_y;
		private String gl;//太阳镜指数
		private String ct;//穿衣
		private String tr;//旅游
		private String yd;//运动
		private String xc;//洗车
		private String pp;//化妆
		private String gm;//感冒
		private String uv;//紫外线
		private String co;//舒适
		private Weather_id weather_id;
		
		
		public class Weather_id{
			private String fa;
			private String fb;
			
			public String getFa() {
				return fa;
			}
			public void setFa(String fa) {
				this.fa = fa;
			}
			public String getFb() {
				return fb;
			}
			public void setFb(String fb) {
				this.fb = fb;
			}
		}

		public String getTemper1() {
			return temper1;
		}

		public void setTemper1(String temper1) {
			this.temper1 = temper1;
		}

		public String getTemper2() {
			return temper2;
		}

		public void setTemper2(String temper2) {
			this.temper2 = temper2;
		}

		public String getWeather() {
			return weather;
		}

		public void setWeather(String weather) {
			this.weather = weather;
		}

		public Weather_id getWeather_id() {
			return weather_id;
		}

		public void setWeather_id(Weather_id weather_id) {
			this.weather_id = weather_id;
		}

		public String getWind() {
			return wind;
		}

		public void setWind(String wind) {
			this.wind = wind;
		}

		public String getWeek() {
			return week;
		}

		public void setWeek(String week) {
			this.week = week;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getDate_y() {
			return date_y;
		}

		public void setDate_y(String date_y) {
			this.date_y = date_y;
		}

		public String getGl() {
			return gl;
		}

		public void setGl(String gl) {
			this.gl = gl;
		}

		public String getCt() {
			return ct;
		}

		public void setCt(String ct) {
			this.ct = ct;
		}

		public String getTr() {
			return tr;
		}

		public void setTr(String tr) {
			this.tr = tr;
		}

		public String getYd() {
			return yd;
		}

		public void setYd(String yd) {
			this.yd = yd;
		}

		public String getXc() {
			return xc;
		}

		public void setXc(String xc) {
			this.xc = xc;
		}

		public String getPp() {
			return pp;
		}

		public void setPp(String pp) {
			this.pp = pp;
		}

		public String getGm() {
			return gm;
		}

		public void setGm(String gm) {
			this.gm = gm;
		}

		public String getUv() {
			return uv;
		}

		public void setUv(String uv) {
			this.uv = uv;
		}

		public String getCo() {
			return co;
		}

		public void setCo(String co) {
			this.co = co;
		}

		public String getFl() {
			return fl;
		}

		public void setFl(String fl) {
			this.fl = fl;
		}

	}

	public class Future {
		private String date;
		private String week;
		private String temper1;
		private String temper2;
		private String weather;
		private String wind;
		
		private Weather_id weather_id;
		
		public class Weather_id {
			
			private String fa;
			private String fb;

			public Weather_id() {
				super();
				// TODO Auto-generated constructor stub
			}

			public Weather_id(String fa, String fb) {
				super();
				this.fa = fa;
				this.fb = fb;
			}

			public String getFa() {
				return fa;
			}

			public void setFa(String fa) {
				this.fa = fa;
			}

			public String getFb() {
				return fb;
			}

			public void setFb(String fb) {
				this.fb = fb;
			}
		}

		public Weather_id getWeather_id() {
			return weather_id;
		}

		public void setWeather_id(Weather_id weather_id) {
			this.weather_id = weather_id;
		}

		public String getTemper1() {
			return temper1;
		}

		public void setTemper1(String temper1) {
			this.temper1 = temper1;
		}

		public String getTemper2() {
			return temper2;
		}

		public void setTemper2(String temper2) {
			this.temper2 = temper2;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getWeek() {
			return week;
		}

		public void setWeek(String week) {
			this.week = week;
		}

		public String getWeather() {
			return weather;
		}

		public void setWeather(String weather) {
			this.weather = weather;
		}

		public String getWind() {
			return wind;
		}

		public void setWind(String wind) {
			this.wind = wind;
		}

	}

	public List<Future> getFuture() {
		return future;
	}

	public void setFuture(List<Future> future) {
		this.future = future;
	}

	public Today getToday() {
		return today;
	}

	public void setToday(Today today) {
		this.today = today;
	}

}
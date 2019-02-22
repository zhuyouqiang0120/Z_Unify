package com.zens.unify.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfinal.kit.JsonKit;
import com.zens.unify.entity.Air;
import com.zens.unify.entity.Gold;
import com.zens.unify.entity.Lottery;
import com.zens.unify.entity.Oil;
import com.zens.unify.entity.Rmbquot;
import com.zens.unify.entity.Rmbquot.RefePrice;
import com.zens.unify.entity.Weather;
import com.zens.unify.entity.Weather.Future;
import com.zens.unify.entity.Weather.Today;
import com.zens.unify.entity.Weather.Today.Weather_id;
import com.zens.unify.utils.HttpRequest;
import com.zens.unify.utils.UrlWebContentUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 天气抓去处理
 * 
 * @author vector
 * @date 2014年10月23日 下午12:21:04
 * @project Z_UnifyService
 * @package com.zens.unify.task.halper WeatherHelper.java
 *
 */
public class ProbeHelper {

	private Logger log = Logger.getLogger(getClass());

	/**
	 * @param url
	 * @return
	 */
	public String weather(String url) {
		System.out.println(url);
		Weather weather = new Weather();
		Today today = weather.new Today();
		Weather_id twid = today.new Weather_id();

		Document doc = Jsoup.parse(new HttpRequest().sendGet(url));
		//System.out.println(doc.toString());
		String cityName = doc.getElementsByClass("crumbs").get(0).text();// 城市名称
		today.setCity(cityName);
		System.out.println("地区：" + cityName);
		try {
			Element s_d = doc.getElementById("7d");
			Element days7 = s_d.child(3);
			Element day1 = days7.child(0);

			Element d1 = day1;// s_d.getElementsByAttributeValue("data-dn", "7d1").get(0);

			today.setDate_y(d1.child(0).text());
			today.setWeek(d1.child(0).text());
			if (d1.child(1).attr("class").split(" ").length > 1) {
				twid.setFa(d1.child(1).attr("class").split(" ")[1].replace("d", ""));
			} else {
				twid.setFa(d1.child(2).attr("class").split(" ")[1].replace("n", ""));
			}
			twid.setFb(d1.child(2).attr("class").split(" ")[1].replace("n", ""));
			today.setWeather_id(twid);
			today.setWeather(d1.child(3).text());
			Element tem = d1.child(4);
			today.setTemper1(tem.child(0).text() + "℃");
			if (tem.childNodeSize() > 1) {
				today.setTemper2(tem.child(1).text());
			} else {
				today.setTemper2(tem.child(0).text() + "℃");
			}

			Element win = d1.child(5);
			Element em = win.child(0);
			String w1 = em.child(0).attr("title");
			String w2 = "";
			if (em.childNodeSize() > 1) {
				w2 = em.child(1).attr("title");
			}
			if (!w1.equals(w2)) {
				w1 += "转" + w2;
			}

			today.setFl(win.child(1).text());
			today.setWind(w1);

			List<Future> futures = new ArrayList<Weather.Future>();
			for (int i = 1; i < 7; i++) {
				Future future = weather.new Future();
				Future.Weather_id futureWeatherId = future.new Weather_id();
				d1 = days7.child(i);
				future.setDate(d1.child(0).text());
				future.setWeek(d1.child(0).text());
				futureWeatherId.setFa(d1.child(1).attr("class").split(" ")[1].replace("d", ""));
				futureWeatherId.setFb(d1.child(2).attr("class").split(" ")[1].replace("n", ""));
				future.setWeather_id(futureWeatherId);
				future.setWeather(d1.child(3).text());
				tem = d1.child(4);
				future.setTemper1(tem.child(0).text());
				future.setTemper2(tem.child(1).text());

				win = d1.child(5);
				em = win.child(0);
				w1 = em.child(0).attr("title");
				w2 = em.child(1).attr("title");
				if (!w1.equals(w2)) {
					w1 += "转" + w2;
				}
				w1 += win.child(1).text();
				future.setWind(w1);
				futures.add(future);
			}
			weather.setFuture(futures);
			/*
			 * 大理不需要指数
			// 指数
			Element zs = doc.getElementById("livezs");
			Element ul = zs.getElementsByClass("clearfix").get(0);

			today.setGl(ul.child(0).child(1).text());
			today.setGm(ul.child(1).child(0).child(4).text());
			// today.setCt(ul.child(2).child(0).child(2).text());
			today.setCt(ul.child(2).child(1).text());
			// today.setXc(ul.child(3).child(1).text());
			today.setXc(ul.child(3).child(0).child(2).text());
			today.setPp(ul.child(4).child(1).text());
			today.setUv(ul.child(5).child(1).text());
			*/
			weather.setToday(today);
			return JsonKit.toJson(weather);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return "";
	}

	/**
	 * 空气质量
	 * 
	 * @param url
	 * @return
	 */
	public String air(String url) {
		Air air = new Air();
		try {
			Document doc = Jsoup.parse(new URL(url), 3000);
			Element conleft = doc.getElementsByClass("conleft").get(0);
			air.setCity(conleft.child(0).child(0).text());
			Element today = conleft.child(0).child(1);
			air.setAqi(today.child(0).text());
			air.setQuality(today.child(1).child(0).text());
			air.setUpdateTime(today.child(1).child(1).text());

			air.setContent(conleft.child(2).child(1).text());

			Element kqnongdu = doc.getElementsByClass("kqnongdu").get(0);
			air.setPm25(kqnongdu.child(0).child(1).text());
			air.setPm10(kqnongdu.child(1).child(1).text());
			air.setCo(kqnongdu.child(2).child(1).text());
			air.setNo2(kqnongdu.child(3).child(1).text());
			air.setO3(kqnongdu.child(4).child(1).text());
			air.setSo2(kqnongdu.child(5).child(1).text());

		} catch (IOException e) {
			// e.printStackTrace();
			log.error(e.getMessage());
		}
		return JsonKit.toJson(air);
	}

	/**
	 * 油价
	 * 
	 * @param url
	 * @return
	 */
	public String oil(String url) {

		List<Oil> oils = new ArrayList<Oil>();
		try {
			Document doc = Jsoup.parse(new URL(url), 3000);

			Element oilTable = doc.getElementsByClass("oilTable").get(0);
			Element tbody = oilTable.child(1);
			// 所有tr
			Elements trs = tbody.children();
			for (Element tr : trs) {
				Oil oil = new Oil();
				oil.setCity(tr.child(0).text());
				oil.setGas90(tr.child(1).text());
				oil.setGas93(tr.child(2).text());
				oil.setGas97(tr.child(3).text());
				oil.setDie(tr.child(4).text());
				oils.add(oil);
				oil = new Oil();
				String city = tr.child(5).text().replace(" ", "");
				if ("".equals(city)) {
					break;
				}
				oil.setCity(city);
				oil.setGas90(tr.child(6).text());
				oil.setGas93(tr.child(7).text());
				oil.setGas97(tr.child(8).text());
				oil.setDie(tr.child(9).text());
				oils.add(oil);
			}

		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return JsonKit.toJson(oils);
	}

	/**
	 * 黄金数据
	 * 
	 * @param url
	 * @return
	 */
	public String gold(String url) throws IOException {

		List<Gold> glods = new ArrayList<Gold>();
		Document doc = Jsoup.parse(new HttpRequest().sendGet(url, ""));
		Element table = doc.getElementsContainingText("上海黄金交易所行情").parents().parents().parents().last();
		Elements trs = table.getElementsByClass("r");
		for (Element tr : trs) {
			if ("tr".equals(tr.tagName())) {
				Gold gold = new Gold();
				gold.setVariety(tr.child(0).text());
				gold.setLastestpri(tr.child(1).text());
				gold.setOpenpri(tr.child(2).text());
				gold.setMaxpri(tr.child(3).text());
				gold.setMinpri(tr.child(4).text());
				gold.setLimit(tr.child(5).text());
				gold.setYespri(tr.child(6).text());
				gold.setTotalvol(tr.child(7).text());
				gold.setTime(tr.child(8).text());
				glods.add(gold);
			}
		}
		return JsonKit.toJson(glods);
	}

	/**
	 * 货币汇率
	 * 
	 * @param url
	 * @return
	 */
	public String rmbquot(String url) {
		// UrlWebContentUtils webContent = null;
		String[] urls = url.split(";");

		List<Rmbquot> rmbquots = new ArrayList<Rmbquot>();
		List<Rmbquot> showDatalist = null;
		List<Rmbquot.RefePrice> refePrice = null;
		Gson gson = new Gson();
		for (String u : urls) {
			String[] fun = u.split("=");
			// webContent = new UrlWebContentUtils("GBK");
			String content = new HttpRequest().sendGet(u, "").replace(fun[1] + "([", "[").replace("}])", "}]");// webContent.getOneHtml(u).replace(fun[1]+"(",
																												// "").replace(")",
																												// "");
			if ("ShowDatalist".equals(fun[1])) {
				showDatalist = gson.fromJson(content, new TypeToken<List<Rmbquot>>() {
				}.getType());
			} else {
				refePrice = gson.fromJson(content, new TypeToken<List<Rmbquot.RefePrice>>() {
				}.getType());
			}
		}
		for (Rmbquot r : showDatalist) {
			if ("中国银行".equals(r.getBank())) {
				for (RefePrice ref : refePrice) {
					if (ref.getCode().equals(r.getCode())) {
						r.setRefePrice(ref.getRefePrice());
						break;
					}
				}
				rmbquots.add(r);
			}
		}
		return new Gson().toJson(rmbquots);
	}

	/**
	 * 彩票
	 * 
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public String lottery(String url) {

		Map<String, List<Lottery>> lotteries = new HashMap<String, List<Lottery>>();
		try {
			Document doc = Jsoup.parse(new URL(url), 20000);
			Elements awardList = doc.getElementsByClass("awardList");
			// 数字彩票
			lotteries.put("digitalLottery", lottery(awardList.first(), 7));
			// 竞技体育
			lotteries.put("sportsLottery", lottery(awardList.get(1), 3));

			// 高频彩frequency of lottery
			lotteries.put("frequencyLottery", lottery(awardList.get(2), 12));

		} catch (MalformedURLException e) {
			log.debug(e.getMessage());
			// e.printStackTrace();
		} catch (IOException e) {
			log.debug(e.getMessage());
			// e.printStackTrace();
		}

		return new Gson().toJson(lotteries);
	}

	/**
	 * 彩票种类
	 * 
	 * @param table
	 * @return
	 */
	private List<Lottery> lottery(Element table, int line) {

		List<Lottery> lotteries = new ArrayList<Lottery>();

		Map<String, Integer> lotteryKey = Lottery.getMapKeyByName();
		// tbody
		Element tbody = table.getElementsByTag("tbody").first();
		// tr
		Elements trs = tbody.children();
		for (int i = 0, len = trs.size(); i < (len < line ? len : line); i++) {
			Element tr = trs.get(i);
			Lottery lottery = new Lottery();
			Elements tds = tr.children();
			if (tds.size() > 3) {
				String lotteryName = tds.get(0).text();
				lottery.setName(lotteryName);
				lottery.setLotteryId(lotteryKey.get(lotteryName));
				lottery.setTimes(tds.get(1).text());
				lottery.setTime(tds.get(2).text());
				String s = tds.get(3).html();
				s = s.replaceAll("计算奖金", "").replaceAll("\"", "'").replaceAll("em", "span")
						.replaceAll("<a[^>]+>[^<]*</a>", "");
				lottery.setNum(s);
				lotteries.add(lottery);
			}
		}
		return lotteries;
	}
}

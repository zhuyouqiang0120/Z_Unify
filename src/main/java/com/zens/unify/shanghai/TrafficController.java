package com.zens.unify.shanghai;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ResourceBundle;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jfinal.core.Controller;
import com.zens.unify.utils.HttpRequest;

public class TrafficController extends Controller {

	HttpRequest request = new HttpRequest();

	public void DZJK() throws IOException {
		JSONObject jsonObject = new JSONObject();
		String cardno = new String(getPara("cardno").getBytes("iso-8859-1"),
				"utf-8");
		String fdjh = getPara("fdjh");
		String atype = getPara("atype");
		String url = ResourceBundle.getBundle("url").getString("DZJK");
		String param = "cardno=" + cardno + "&fdjh=" + fdjh + "&atype=" + atype
				+ "&ctype=wfqk";// "cardno=沪CQZ101&fdjh=BB674098&atype=02&ctype=wfqk";
		String result = request.sendPost(url, param, true);
//		System.out.println(result);
		if (result.equals("error") || result.equals("-3")) {
			jsonObject.element("status", "0");
			jsonObject.element("msg", "网络或参数异常！");
		} else {
			jsonObject.element("status", "-1");
			jsonObject.element("msg", result);
		}

		renderText(jsonObject.toString());
	}

	public void WFJF() {
		JSONObject jsonObject = new JSONObject();
		String cardno = getPara("cardno");
		String url = ResourceBundle.getBundle("url").getString("WFJF");
		String param = "cardno=" + cardno + "&ctype=dabh";// "cardno=310021049154&ctype=dabh";
		String result = request.sendPost(url, param, true);
//		System.out.println(result);
		if (result.equals("error") || result.equals("-3")) {
			jsonObject.element("status", "0");
			jsonObject.element("msg", "网络或参数异常！");
		} else {
			jsonObject.element("status", "-1");
			jsonObject.element("msg", result);
		}

		renderText(jsonObject.toString());
	}

	public void JTKYE() {
		JSONObject jsonObject = new JSONObject();

		String id = getPara("id");// "53655966234";//
		String url = ResourceBundle.getBundle("url").getString("JTKYE");
		String param = "id=" + id;
		String result = request.sendPost(url, param, true);
		JSONObject resultob = JSONObject.fromObject(result);
		Object status = resultob.get("status");
		int i = Integer.parseInt(status.toString());
		if (i == -2) {
			jsonObject.element("status", "0");
			jsonObject.element("msg", "网络或参数异常！");
		} else {
			JSONObject msgob = new JSONObject();
			jsonObject.element("status", "-1");
			msgob.element("num",
					JSONArray.fromObject(resultob.get("mes")).get(0));
			msgob.element("date", JSONArray.fromObject(resultob.get("mes"))
					.get(1));
			msgob.element("balance", JSONArray.fromObject(resultob.get("mes"))
					.get(2));
			jsonObject.element("msg", msgob);
		}

		renderText(jsonObject.toString().replace(" ", ""));
	}

	public void BUS() throws IOException {
		JSONObject jsonObject = new JSONObject();
		String idnum = URLDecoder.decode(getPara("idnum"), "utf-8");
		String url = ResourceBundle.getBundle("url").getString("BUS");
		String href = ResourceBundle.getBundle("url").getString("BUSdetail");
		String param = "idnum=" + idnum;
//		System.out.println(url + "?" + param);
		String result = request.sendPost(url, param, true);
		JSONObject sidob = JSONObject.fromObject(result);
		String sid = (String) sidob.get("sid");

		if (sid == null) {
			jsonObject.element("status", "0");
			jsonObject.element("msg", "公交路线错误！");
		} else {
			String datas = request.sendGet(href + sid, "");
			Document doc = Jsoup.parse(datas);
			Elements busDirection = doc.getElementsByAttributeValue("class",
					"busDirection");
			Elements times = busDirection.first().getElementsByTag("em");
			Elements station = busDirection.first().getElementsByTag("span");

			JSONObject line = new JSONObject();
			JSONObject upob = new JSONObject();
			JSONObject dowob = new JSONObject();

			upob.element("beg", times.get(0).text());
			upob.element("end", times.get(1).text());
			upob.element("begsta", station.get(0).text());
			upob.element("endsta", station.get(1).text());

			dowob.element("beg", times.get(2).text());
			dowob.element("end", times.get(3).text());
			dowob.element("begsta", station.get(2).text());
			dowob.element("endsta", station.get(3).text());

			line.element("busline", idnum);
			line.element("upgoing", upob);
			line.element("downgoing", dowob);

			Elements stations = doc.getElementsByAttributeValue("class",
					"stationBox");
			Elements stats = stations.first().getElementsByAttributeValue(
					"class", "station");
			JSONObject lines = new JSONObject();
			for (int i = 0; i < stats.size(); i++) {
				Element e = stats.get(i);
				Elements names = e.getElementsByTag("span");
				lines.element(i + "", names.get(1).text());
			}

			line.element("stations", lines);

			jsonObject.element("status", "-1");
			jsonObject.element("msg", line);
		}

		renderText(jsonObject.toString());
	}

	public static void main(String[] args) {
		new TrafficController().JTKYE();
	}
}

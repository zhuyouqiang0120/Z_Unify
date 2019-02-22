package com.zens.unify.shanghai;

import java.io.IOException;
import java.util.ResourceBundle;

import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.jfinal.core.Controller;
import com.zens.unify.utils.HttpRequest;

public class PublicController extends Controller {
	
	HttpRequest request = new HttpRequest();
	
	public void GJJ() {//公积金
		
		JSONObject jsonObject = new JSONObject();
		
		String username = getPara("username");// hy247767221
		String pwd = getPara("pwd");// Hy0705laopo520
		String act = "do_login";
		String url = ResourceBundle.getBundle("url").getString("GJJ");
		String detailurl = ResourceBundle.getBundle("url").getString("GJJdetail");
		String param = "act=" + act + "&username=" + username + "&pwd=" + pwd;
		String cookie = request.getcookie(url, param);
		String result = request.sendGet(detailurl, cookie);
		
		Document doc = Jsoup.parse(result);
		
		Elements users = doc.getElementsByAttributeValue("class", "ui-bar ui-body-a user");
		if(users.size() != 0){
			JSONObject object = new JSONObject();
			Elements ps = users.first().getElementsByTag("p");
			object.element("username", ps.first().text());
			Elements date = users.first().getElementsByTag("span");
			object.element("enddate", date.first().text());
			
			Elements uls = doc.getElementsByTag("ul");
			Elements lips = uls.first().getElementsByTag("p");
			object.element("state", lips.get(1).text());
			object.element("balance", lips.get(2).text());
			object.element("monthbal", lips.get(3).text());
			object.element("lastbal", lips.get(4).text());
			object.element("account", lips.get(5).text());
			object.element("opentime", lips.get(6).text());
			object.element("company", lips.get(7).text());
			
			jsonObject.element("status", "-1");
			jsonObject.element("msg", object);	
		}else{
			jsonObject.element("status", "0");
			jsonObject.element("msg", "用户名或密码错误！");			
		}
		renderText(jsonObject.toString());
	}
	
	public void YLJ() throws IOException{//养老金
		
		JSONObject jsonObject = new JSONObject();
		
		String idnum = getPara("idnum");
		String idpwd = getPara("idpwd");
		String url = ResourceBundle.getBundle("url").getString("YLJ");
		String param = "idnum=" + idnum + "&idpwd=" + idpwd;
		String data = request.sendPost(url, param, true);//"{"errcode":"200","msg":"5ba7b37039eced8b102095388c01956a"}"
		JSONObject object = JSONObject.fromObject(data);
		String msg = (String) object.get("msg");//"5ba7b37039eced8b102095388c01956a";
		String code = (String) object.get("errcode");
		
		if(code.equals("200")){
			jsonObject.element("status", "-1");
			String token = ResourceBundle.getBundle("url").getString("YLJtoken");
			String result = request.sendGet(token+msg,"");
				
			Document doc = Jsoup.parse(result);
			Elements cbs = doc.getElementsByAttributeValue("class", "box person_info");
			JSONObject userob = new JSONObject();
			userob.element("name", cbs.first().child(0).text().split(":")[1]);
			userob.element("account", cbs.first().child(1).text().split(":")[1]);
			userob.element("company", cbs.first().child(2).text().split(":")[1]);
			
			Elements pays = doc.getElementsByAttributeValue("class", "recentPay_result");
			Elements sumos = pays.get(2).getElementsByAttributeValue("class", "sumo");
			JSONObject payob = new JSONObject();
			payob.element("type", "养老金");
			payob.element("enddate", sumos.get(0).text());
			payob.element("months", sumos.get(1).text());
			payob.element("balance", sumos.get(2).text());
			
			JSONObject msgob = new JSONObject();
			msgob.element("user", userob);
			msgob.element("pay", payob);
			jsonObject.element("msg", msgob);
			
		}else{
			jsonObject.element("status", "0");
			jsonObject.element("msg", "用户名或密码错误！");
		}
		renderText( jsonObject.toString());
	}
	
	public void YB(){//医保
		JSONObject jsonObject = new JSONObject();
		String idnum = getPara("idnum");//6006586032
		String idpwd = getPara("idpwd");//19850320
		String url = ResourceBundle.getBundle("url").getString("YB");
		String param = "idnum=" + idnum + "&idpwd=" + idpwd;
		System.out.println(url+"?"+param);
		String data = request.sendPost(url, param, true);//"{"errcode":"200","msg":"81bddd057652094e1a072970786272c1","msg": "验证成功",}"
		System.out.println(data);
		JSONObject object = JSONObject.fromObject(data);
		String msg = (String) object.get("data");//"81bddd057652094e1a072970786272c1";
		String code = (String) object.get("errcode");
		
		if(code.equals("200")){
			jsonObject.element("status", "-1");
			String token = ResourceBundle.getBundle("url").getString("YBtoken");
			String result = request.sendGet(token+msg,"");
				
			Document doc = Jsoup.parse(result);
			Elements arts = doc.getElementsByTag("article");
			Elements ps = arts.first().getElementsByTag("p");
			JSONObject msgob = new JSONObject();
			
			msgob.element("account", idnum);
			msgob.element("workstate", ps.get(0).getElementsByTag("span").text());
			msgob.element("acstate", ps.get(1).getElementsByTag("span").text());
			msgob.element("type", ps.get(2).getElementsByTag("span").text());
			msgob.element("city", ps.get(3).getElementsByTag("span").text());
			msgob.element("batype", ps.get(4).getElementsByTag("span").text());
			msgob.element("medtype", ps.get(5).getElementsByTag("span").text());
			msgob.element("mednum", ps.get(7).getElementsByTag("span").text());
			msgob.element("balance", ps.get(8).getElementsByTag("span").text());
			
			jsonObject.element("msg", msgob);
			
		}else{
			jsonObject.element("status", "0");
			jsonObject.element("msg", "用户名或密码错误！");
		}
		
		
		renderText( jsonObject.toString());
	}
	
	public void JHYY(){//结婚预约查询
		JSONObject object = new JSONObject();
		String bookid = getPara("bookid");//6006586032
		String cardid = getPara("cardid");
		String url = ResourceBundle.getBundle("url").getString("JHYY");
		String param = "bookid=" + bookid + "&cardid=" + cardid;
		String data = request.sendPost(url, param, true);
		
		Document doc = Jsoup.parse(data);
		Elements arts = doc.getElementsByTag("article");
		object.element("status", "-1");
		object.element("msg", arts.first().text());
		renderText(object.toString());
	}
	
	public void CRJYY(){//出入境预约查询
		String cknumber = getPara("cknumber");//6006586032
		String ckname = getPara("ckname");
		String url = ResourceBundle.getBundle("url").getString("CRJ");
		String param = "ac=getYyxx&cknumber=" + cknumber + "&ckname=" + ckname;
		url = url+"?"+param;
		String data = request.sendGet(url, "");
		renderText(data);
	}
	
	public void CRJJD(){//出入境进度查询
		String cknumber = getPara("cknumber");//6006586032
		String ckzwxm = getPara("ckzwxm");
		String url = ResourceBundle.getBundle("url").getString("CRJ");
		String param = "ac=getFlowInfo&cknumber=" + cknumber + "&ckzwxm=" + ckzwxm;
		url = url+"?"+param;
		String data = request.sendGet(url, "");
		System.out.println(data);
		renderText(data);
	}
}

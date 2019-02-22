package com.zens.unify.entity;
/**
 * 上海市民信箱
 * @author vector
 * @time 2014年8月17日 下午2:18:55
 *
 */
public class SMMail {
	private String title;//标题
	private String content;//内容
	private String from;//来源
	private String updateTime;//更新时间
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public SMMail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
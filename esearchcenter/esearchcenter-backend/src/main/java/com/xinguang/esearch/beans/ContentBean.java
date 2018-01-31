package com.xinguang.esearch.beans;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ContentBean extends BaseBean {
	// id
	private Long id;

	// clientcode
	private String clientCode;

	// 标题
	private String title;

	// 封面图片
	private JSONObject cover;

	// 如果是视频类的，需要加上视频对象
	private JSONObject videoObj;

	// 类型
	private int type;

	// 分类
	private String classify;

	// 正文
	private String contents;

	// 用户id
	private Long creator;

	// 作者头像url
	private JSONObject headImg;

	// 作者用户名
	private String nickName;
	
	// 图片列表（新增）
	private JSONArray imgList;

	// 阅读量
	private int read;

	// 点赞数
	private int like;

	// 评论数
	private int comment;

	// 发布时间
	private long publishTime;
	
	//摘要
	private String brief;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JSONObject getCover() {
		return cover;
	}

	public void setCover(JSONObject cover) {
		this.cover = cover;
	}

	public JSONObject getVideoObj() {
		return videoObj;
	}

	public void setVideoObj(JSONObject videoObj) {
		this.videoObj = videoObj;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public JSONObject getHeadImg() {
		return headImg;
	}

	public void setHeadImg(JSONObject headImg) {
		this.headImg = headImg;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public JSONArray getImgList() {
		return imgList;
	}

	public void setImgList(JSONArray imgList) {
		this.imgList = imgList;
	}

	public int getRead() {
		return read;
	}

	public void setRead(int read) {
		this.read = read;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	public long getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(long publishTime) {
		this.publishTime = publishTime;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	
}

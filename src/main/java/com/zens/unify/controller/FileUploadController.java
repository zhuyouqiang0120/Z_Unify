package com.zens.unify.controller;

import com.jfinal.core.Controller;
import com.zens.unify.utils.PropertiesKey;

public class FileUploadController extends Controller {

	public void uploadFile(){
		String imgPath = PropertiesKey.imgPath;
		getFile(getPara("img"), imgPath);
		renderJson("{\"success\" : true, \"path\":\"" + imgPath + "\"}");
	}
}

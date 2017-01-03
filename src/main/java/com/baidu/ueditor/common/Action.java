package com.baidu.ueditor.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 定义请求action
 */
public enum Action {
	
	CONFIG("config"),
	UPLOAD_IMAGE("uploadimage"),
	UPLOAD_SCRAWL("uploadscrawl"),
	UPLOAD_VIDEO("uploadvideo"),
	UPLOAD_FILE("uploadfile"),
	CATCH_IMAGE("catchimage"),
	LIST_FILE("listfile"),
	LIST_IMAGE("listimage");

	private final String type;
	
	public String type() {
		return type;
	}
	
	private Action(String type) {
		this.type = type;
	}
	
	public static Map<String, Action> getActionMap() {
		Map<String,Action> map = new LinkedHashMap<String,Action>();
		for (Action action : values()) {
			map.put(action.type(), action);
		}
		return map;
	}
	
	public static Action of(String type) {
		for (Action action : values()) {
			if (action.type().equals(type)) {
				return action;
			}
		}
		return null;
	}
	
}

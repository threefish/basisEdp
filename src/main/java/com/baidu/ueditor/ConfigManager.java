package com.baidu.ueditor;

import com.baidu.ueditor.common.Action;
import com.baidu.ueditor.common.Config;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置管理器
 */
public final class ConfigManager {

	private final String rootPath;
	private final String contextPath;
	private final Map<Action,Config> configMap = new HashMap<Action,Config>();
	private JSONObject jsonConfig = null;
	
	/*
	 * 通过一个给定的路径构建一个配置管理器， 该管理器要求地址路径所在目录下必须存在config.properties文件
	 */
	public ConfigManager(String rootPath, String contextPath, InputStream configStream) {
		if (rootPath==null || contextPath==null || configStream==null) {
			throw new IllegalArgumentException();
		}
		rootPath = com.baidu.ueditor.PathFormat.formatFileSeparator(rootPath);
		this.rootPath = rootPath.endsWith("/")?rootPath:rootPath+"/";
		this.contextPath = contextPath.endsWith("/")?contextPath:contextPath+"/";
		try{
			JSONObject jsonConfig = new JSONObject(readConfig(configStream));
			this.jsonConfig = jsonConfig;
			for (Action action : Action.values()) {
				configMap.put(action, initConfig(action));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	// 验证配置文件加载是否正确
	public boolean valid(){
		return this.jsonConfig != null;
	}
	
	public String getAllConfigString(){
		return this.jsonConfig.toString();
	}
	
	public Config getConfig(Action action) {
		return configMap.get(action);
	}

	private Config initConfig(Action action) throws JSONException {
		Map<String,Object> conf = new HashMap<String,Object>();
		String savePath = null;	
		switch (action) {
			case UPLOAD_FILE:
				conf.put("isBase64","false");
				conf.put("maxSize", this.jsonConfig.getLong("fileMaxSize"));
				conf.put("allowFiles", this.getArray("fileAllowFiles"));
				conf.put("fieldName", this.jsonConfig.getString("fileFieldName"));
				savePath = this.jsonConfig.getString("filePathFormat");
				break;	
			case UPLOAD_IMAGE:
				conf.put("isBase64", "false");
				conf.put("maxSize", this.jsonConfig.getLong("imageMaxSize"));
				conf.put("allowFiles", this.getArray("imageAllowFiles"));
				conf.put("fieldName", this.jsonConfig.getString("imageFieldName"));
				savePath = this.jsonConfig.getString("imagePathFormat");
				break;
			case UPLOAD_VIDEO:
				conf.put("maxSize", this.jsonConfig.getLong("videoMaxSize"));
				conf.put("allowFiles", this.getArray("videoAllowFiles"));
				conf.put("fieldName", this.jsonConfig.getString("videoFieldName"));
				savePath = this.jsonConfig.getString("videoPathFormat");
				break;
			case UPLOAD_SCRAWL:
				conf.put("filename", "scrawl");
				conf.put("maxSize", this.jsonConfig.getLong("scrawlMaxSize"));
				conf.put("fieldName", this.jsonConfig.getString("scrawlFieldName"));
				conf.put("allowFiles", new String[] {".jpg"});
				conf.put("isBase64", "true");
				savePath = this.jsonConfig.getString("scrawlPathFormat");
				break;
			case CATCH_IMAGE:
				conf.put("filename", "remote");
				conf.put("filter", this.getArray("catcherLocalDomain"));
				conf.put("maxSize", this.jsonConfig.getLong("catcherMaxSize"));
				conf.put("allowFiles", this.getArray("catcherAllowFiles"));
				conf.put("fieldName", this.jsonConfig.getString("catcherFieldName") + "[]");
				savePath = this.jsonConfig.getString("catcherPathFormat");
				break;
			case LIST_IMAGE:
				conf.put("allowFiles", this.getArray("imageManagerAllowFiles"));
				conf.put("dir", this.jsonConfig.getString("imageManagerListPath"));
				conf.put("count", this.jsonConfig.getInt("imageManagerListSize"));
				break;
			case LIST_FILE:
				conf.put("allowFiles", this.getArray("fileManagerAllowFiles"));
				conf.put("dir", this.jsonConfig.getString("fileManagerListPath"));
				conf.put("count", this.jsonConfig.getInt("fileManagerListSize"));
				break;
			default:;
		}
		conf.put("action", action);
		conf.put("savePath", savePath);
		conf.put("rootPath", this.rootPath);
		conf.put("contextPath", this.contextPath);
		return new Config(conf);
	}
	
	private String[] getArray(String key) throws JSONException {
		JSONArray jsonArray = this.jsonConfig.getJSONArray(key);
		String[] result = new String[jsonArray.length()];
		for (int i = 0, len = jsonArray.length(); i < len; i++) {
			result[i] = jsonArray.getString(i);
		}
		return result;
	}
	
	private String readConfig(InputStream configStream) throws IOException {
		String content = IOUtils.toString(configStream);
		return content.replaceAll( "/\\*[\\s\\S]*?\\*/", "" );
	}
	
}

package com.baidu.ueditor.result;

import com.baidu.ueditor.common.ActionState;
import com.sgaop.basis.cache.PropertiesManager;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class BaseResult implements Result {

	private ActionState state = null;
	private Map<String, Object> infos;
	
	public BaseResult (ActionState state) {
		this.state = state;
	}
	
	public boolean isSuccess() {
		return this.state==ActionState.SUCCESS;
	}
	
	public void setState(ActionState state) {
		this.state = state;
	}
	
	public ActionState getState() {
		return this.state;
	}
	
	protected Map<String,Object> getJsonMap() {
		Map<String,Object> json = new LinkedHashMap<String,Object>();
		String stateVal = isSuccess()?ActionState.SUCCESS.name(): PropertiesManager.getCacheStr(state.name());
		json.put("state", stateVal);
		if (infos!=null) {
			for (Map.Entry<String, Object> entry : infos.entrySet()) {
				json.put(entry.getKey(), entry.getValue());
			}
		}
		return json;
	}
	
	@Override
	public JSONObject toJSONObject() {
		return new JSONObject(getJsonMap());
	}
	
	public String toString() {
		return toJSONObject().toString();
	}
	
	private Map<String,Object> createInfoMapIfNecessary() {
		if (infos==null) {
			infos = new LinkedHashMap<String,Object>();
		}
		return infos;
	}

	@Override
	public void putInfo(String name, int val) {
		createInfoMapIfNecessary().put(name, val);
	}
	
	@Override
	public void putInfo(String name, long val) {
		createInfoMapIfNecessary().put(name, val);
	}

	@Override
	public void putInfo(String name, String val) {
		createInfoMapIfNecessary().put(name, val);
	}

	@Override
	public String getInfo(String name) {
		return String.valueOf(createInfoMapIfNecessary().get(name));
	}

}

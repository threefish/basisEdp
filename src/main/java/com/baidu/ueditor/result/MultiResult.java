package com.baidu.ueditor.result;

import com.baidu.ueditor.common.ActionState;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 多状态集合状态
 * 其包含了多个状态的集合, 其本身自己也是一个状态
 */
public class MultiResult extends BaseResult {

	private List<Result> subResults;
	
	public MultiResult (ActionState state) {
		super(state);
	}
	
	public void addResult(Result result) {
		if (subResults==null) {
			subResults = new ArrayList<Result>();
		}
		subResults.add(result);
	}
	
	protected Map<String,Object> getJsonMap() {
		Map<String,Object> json = super.getJsonMap();
		if (subResults!=null) {
			List<JSONObject> list = new ArrayList<JSONObject>(subResults.size());
			for (Result result : subResults) {
				list.add(result.toJSONObject());
			}
			json.put("list", list);
		} else {
			json.put("list", new ArrayList<Result>(0));
		}
		return json;
	}
	
}

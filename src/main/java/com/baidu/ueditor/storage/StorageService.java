package com.baidu.ueditor.storage;


import com.baidu.ueditor.common.Config;
import com.baidu.ueditor.result.Result;

public interface StorageService {

	public Result save(Config conf,MultipartFile file);
	
	public Result list(Config conf,int start);
	
}

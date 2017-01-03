package com.baidu.ueditor.storage.impl;

import com.baidu.ueditor.common.ActionState;
import com.baidu.ueditor.common.Config;
import com.baidu.ueditor.result.BaseResult;
import com.baidu.ueditor.result.Result;
import com.baidu.ueditor.storage.MultipartFile;
import com.baidu.ueditor.storage.StorageService;

public abstract class AbstractStorageService implements StorageService {

	public Result save(Config conf,MultipartFile file) {
		if (file==null) {
			return new BaseResult(ActionState.NOT_MULTIPART_CONTENT);
		}
		if (file.isEmpty()) {
			return new BaseResult(ActionState.NOTFOUND_UPLOAD_DATA);
		}
		long maxSize = conf.getMaxSize();
		if (file.getSize()>maxSize) {
			return new BaseResult(ActionState.MAX_SIZE);
		}
		if (!conf.containAllowType(file.getExtension())) {
			return new BaseResult(ActionState.NOT_ALLOW_FILE_TYPE);
		}
		return doSaveInternel(conf, file);
	}
	
	protected abstract Result doSaveInternel(Config conf,MultipartFile file);

	@Override
	public Result list(Config conf,int start) {
		return doListInternel(conf, start);
	}
	
	protected abstract Result doListInternel(Config conf,int start);
	
}

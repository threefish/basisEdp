package com.baidu.ueditor.storage.impl;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.common.ActionState;
import com.baidu.ueditor.common.Config;
import com.baidu.ueditor.result.BaseResult;
import com.baidu.ueditor.result.MultiResult;
import com.baidu.ueditor.result.Result;
import com.baidu.ueditor.storage.MultipartFile;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class LocalStorageService extends AbstractStorageService {

	@Override
	protected Result doSaveInternel(Config conf,MultipartFile file) {
		String savePath = conf.getSavePath();
		String originalFileName = file.getOriginalFilename();
		String extension = file.getExtension();
		originalFileName = originalFileName.substring(0, originalFileName.length() - extension.length());
		savePath = savePath + extension;
		savePath = PathFormat.parse(savePath, originalFileName,file.getAttachmodule());
		File targetFile = new File(conf.getRootPath(),savePath);
		try {
			file.transferTo(targetFile);
			Result result = new BaseResult(ActionState.SUCCESS);
			result.putInfo("size", targetFile.length());
			result.putInfo("title", targetFile.getName());
			result.putInfo("type", extension);
			result.putInfo("original", originalFileName);
			result.putInfo("url", conf.pathToUrl(targetFile));
			return result;
		} catch (IOException e) {
			FileUtils.deleteQuietly(targetFile);
			return new BaseResult(ActionState.IO_ERROR);
		} 
	}
	
	@Override
	protected Result doListInternel(Config conf,int start) {
		File dir = new File(conf.getRootPath(), conf.getDir());
		if (!dir.exists()) {
			return new BaseResult(ActionState.NOT_EXIST);
		}
		if (!dir.isDirectory()) {
			return new BaseResult(ActionState.NOT_DIRECTORY);
		}
		String[] allowTypes = conf.getAllowTypes();
		for (int i=0;i<allowTypes.length;i++) {
			allowTypes[i] = allowTypes[i].replace(".", "");
		}
		Collection<File> list = FileUtils.listFiles(dir, allowTypes , true);
		Result result = null;
		if (start < 0 || start > list.size()) {
			result = new MultiResult(ActionState.SUCCESS);
		} else {
			Object[] fileList = Arrays.copyOfRange(list.toArray(), start, start + conf.getCount());
			result = this.listFile(conf, fileList);
		}
		result.putInfo("start", start);
		result.putInfo("total", list.size());
		return result;
	}
	
	private Result listFile(Config conf, Object[] files) {
		MultiResult result = new MultiResult(ActionState.SUCCESS);
		File file = null;
		for (Object obj : files) {
			if (!(obj instanceof File)) {
				continue;
			}
			file = (File)obj;
			BaseResult fileResult = new BaseResult(ActionState.SUCCESS);
			fileResult.putInfo("url", conf.pathToUrl(file));
			result.addResult(fileResult);
		}
		return result;
	}
	
}

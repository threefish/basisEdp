package com.baidu.ueditor.storage;

import com.baidu.ueditor.common.ActionState;
import com.baidu.ueditor.common.Config;
import com.baidu.ueditor.result.BaseResult;
import com.baidu.ueditor.result.MultiResult;
import com.baidu.ueditor.result.Result;
import com.baidu.ueditor.storage.impl.DefaultMultipartFile;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class StorageManager {
	
	protected static final Map<String, String> IMAGE_TYPES = Collections.unmodifiableMap(new HashMap<String, String>(){{
		put("image/gif", ".gif");
		put("image/jpeg", ".jpg");
		put("image/jpg", ".jpg");
		put("image/png", ".png");
		put("image/bmp", ".bmp");
	}});
	
	protected final StorageService storageService;
	
	public StorageManager(StorageService storageService) {
		this.storageService = storageService;
	}

	public Result upload(Config conf,HttpServletRequest request) {
		Result argumentsResult = checkArguments(conf, request);
		if (argumentsResult!=null) { return argumentsResult; }
		try {
			MultipartFile multipartFile = getMultipartFile(conf, request);
			return storageService.save(conf, multipartFile);
		} catch (IOException e) {
			e.printStackTrace();
			return new BaseResult(ActionState.IO_ERROR);
		} catch (FileUploadException e) {
			e.printStackTrace();
			return new BaseResult(ActionState.PARSE_REQUEST_ERROR);
		}
	}
	
	protected MultipartFile getMultipartFile(Config conf,HttpServletRequest request) throws IOException, FileUploadException {
		MultipartFile multipartFile = null;
		String filedName = conf.getFieldName();
		String attachmodule = request.getParameter("attachmodule");
		if (conf.isBase64()) {
			String content = request.getParameter(filedName);
			if (content!=null && content.length()>=0) {
				byte[] bytes = Base64.decodeBase64(content);
				multipartFile = new DefaultMultipartFile(filedName, new ByteArrayInputStream(bytes), (conf.getFilename()==null?"scrawl":conf.getFilename())+".jpg", "image/jpg", attachmodule);
			}
		} else {
			if (ServletFileUpload.isMultipartContent(request)) {
				//这里还是用FileItemStream，如果使用FileItem，截图上传有问题
				ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
		        if (request.getHeader("X_Requested_With")!=null) {
		            upload.setHeaderEncoding("UTF-8");
		        }
		        FileItemStream fileItem = null;
				FileItemIterator fileItemIterator = upload.getItemIterator(request);
				while (fileItemIterator.hasNext()) {
					fileItem = fileItemIterator.next();
					if (!fileItem.isFormField())
						break;
					fileItem = null;
				}
				if (fileItem != null) {
					multipartFile = new DefaultMultipartFile(fileItem.getFieldName(),fileItem.openStream(),fileItem.getName(),fileItem.getContentType(), attachmodule);
				}
			}
		}
		return multipartFile;
	}
	
	public Result capture(Config conf, HttpServletRequest request) {
		Result argumentsResult = checkArguments(conf, request);
		if (argumentsResult!=null) { return argumentsResult; }
		String[] list = request.getParameterValues(conf.getFieldName());
		if (list==null) {
			return new BaseResult(ActionState.ILLEGAL);
		}
		MultiResult result = new MultiResult(ActionState.SUCCESS);
		for (String source : list) {
			result.addResult(captureRemoteData(conf, source));
		}
		return result;
	}
	
	private Result captureRemoteData(Config conf,String urlStr) {
		HttpURLConnection connection = null;
		URL url = null;
		try {
			url = new URL(urlStr);
			if (conf.containFitler(url.getHost())) {
				return new BaseResult(ActionState.PREVENT_HOST);
			}
			connection = (HttpURLConnection)url.openConnection();
			connection.setReadTimeout(conf.getTimeout());
			connection.setInstanceFollowRedirects(true);
			connection.setUseCaches(true);
			if (connection.getResponseCode()!=HttpServletResponse.SC_OK) {
				return new BaseResult(ActionState.CONNECTION_ERROR);
			}
			String extention = IMAGE_TYPES.get(connection.getContentType());
			if (!conf.containAllowType(extention)) {
				return new BaseResult(ActionState.NOT_ALLOW_FILE_TYPE );
			}
			if (connection.getContentLength()>conf.getMaxSize()) {
				return new BaseResult(ActionState.MAX_SIZE);
			}
			MultipartFile file = new DefaultMultipartFile(conf.getFieldName(), connection.getInputStream(), (conf.getFilename()==null?"remote":conf.getFilename())+extention, connection.getContentType(),null);
			Result result = storageService.save(conf, file);
			if (result!=null) {
				result.putInfo("source", urlStr);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseResult(ActionState.REMOTE_FAIL);
		} finally {
			if (connection!=null) {
				try {
					connection.disconnect();
				} catch(Exception e) {
					//NOP
				}
			}
		}
	}
	
	public Result list(Config conf,HttpServletRequest request) {
		Result argumentsResult = checkArguments(conf, request);
		if (argumentsResult!=null) { return argumentsResult; }
		int start = 0;
		try {
			String startParam = request.getParameter("start");
			if (startParam!=null) {
				start = Integer.parseInt(startParam);
			}
		} catch(Exception e) {
			//NOP
		} 
		if (start<0) {
			return new BaseResult(ActionState.ILLEGAL);
		}
		return storageService.list(conf, start);
	}
	
	private Result checkArguments(Config conf,HttpServletRequest request) {
		if (conf==null) {
			return new BaseResult(ActionState.CONFIG_ERROR);
		}
		if (request==null) {
			return new BaseResult(ActionState.ILLEGAL);
		}
		return null;
	}
	
}

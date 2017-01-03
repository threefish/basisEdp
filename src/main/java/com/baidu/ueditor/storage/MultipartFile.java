package com.baidu.ueditor.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface MultipartFile {

	String getAttachmodule();

	String getName();

	String getOriginalFilename();
	
	String getExtension();

	String getContentType();

	boolean isEmpty();

	long getSize();

	byte[] getBytes() throws IOException;

	InputStream getInputStream() throws IOException;
	
	void transferTo(File dest) throws IOException, IllegalStateException;

	String getStorageDescription();
	
}

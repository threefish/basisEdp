
 package com.baidu.ueditor.storage.impl;

 import com.baidu.ueditor.PathFormat;
 import com.baidu.ueditor.storage.MultipartFile;
 import org.apache.commons.io.FileUtils;

 import java.io.*;

@SuppressWarnings({"serial","resource"})
public class DefaultMultipartFile implements MultipartFile, Serializable {

	private String attachmodule;
	private String name;
	private String originalName;
	private String contentType;
	private File dfosFile;

	public DefaultMultipartFile(String name,InputStream input,String originalName,String contentType,String attachmodule) throws IOException {
		this.name = name;
		File dfosFile = new File(FileUtils.getTempDirectory(), PathFormat.random());
		FileUtils.copyInputStreamToFile(input, dfosFile);
		this.dfosFile = dfosFile;
		this.contentType = contentType;
		this.originalName = originalName;
		this.attachmodule = attachmodule;
	}

	@Override
	public String getAttachmodule() {
		return attachmodule;
	}

	public String getName() {
		return name;
	}
	
	public String getExtension() {
		String originalFilename = getOriginalFilename();
		int pos = originalFilename.lastIndexOf(".");
		if (pos == -1) {
			return "";
		}
		return originalFilename.substring(pos);
	}

	public String getOriginalFilename() {
		String filename = this.originalName;
		if (filename == null) {
			// Should never happen.
			return "";
		}
		// check for Unix-style path
		int pos = filename.lastIndexOf("/");
		if (pos == -1) {
			// check for Windows-style path
			pos = filename.lastIndexOf("\\");
		}
		if (pos != -1)  {
			// any sort of path separator found
			return filename.substring(pos + 1);
		}
		else {
			// plain name
			return filename;
		}
	}

	public String getContentType() {
		return this.contentType;
	}

	public boolean isEmpty() {
		return (getSize() == 0);
	}

	public long getSize() {
		return fileExists()?dfosFile.length():0;
	}
	
	public File getStoreLocation() {
		return this.dfosFile;
	}
	
	private boolean fileExists() {
		return this.dfosFile!=null && dfosFile.exists();
	}

	public byte[] getBytes() throws IOException {
		return fileExists()?FileUtils.readFileToByteArray(this.dfosFile):new byte[0];
	}

	public InputStream getInputStream() throws IOException {
		return fileExists()?new FileInputStream(this.dfosFile):new ByteArrayInputStream(new byte[0]);
	}

	public String getStorageDescription() {
		return "at [" + dfosFile.getAbsolutePath() + "]";
	}

	public void transferTo(File dest) throws IOException, IllegalStateException {
		if (dest.exists() && !dest.delete()) {
			throw new IOException("Destination file [" + dest.getAbsolutePath() + "] already exists and could not be deleted");
		} 
		try {
			FileUtils.copyFile(dfosFile, dest);
		} catch (IOException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new IOException("Could not transfer to file: " + ex.getMessage());
		}
	}

	@Override
	public String toString() {
		return "DefaultMultipartFile [name=" + name + ", originalName="
				+ originalName + ", contentType=" + contentType + ", dfosFile="
				+ dfosFile + ", attachmodule="+ attachmodule+"]";
	}

}

package com.baidu.ueditor.common;

import com.baidu.ueditor.PathFormat;

import java.io.File;
import java.util.*;

/**
 * Unmodifiable
 */
public class Config extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;
	
	public Config(Map<? extends String, ? extends Object> m) {
		super(m);
	}

	public String getString(String key) {
		Object obj = super.get(key);
		return obj==null?null:obj.toString();
	}
	
	public Number getNumber(String key) {
		return getNumber(key, null);
	}
	
	private Number getNumber(String key,Number def) {
		Object obj = super.get(key);
		if (obj instanceof Number) {
			return (Number)obj;
		}
		return def;
	}
	
	public String[] getStringArray(String key) {
		Object obj = super.get(key);
		if (obj instanceof String[]) {
			return (String[])obj;
		} else if (obj instanceof String) {
			return new String[] { (String)obj };
		}
		return new String[0];
	}
	
	public Boolean getBoolean(String key) {
		Object obj = super.get(key);
		if (obj instanceof Boolean) {
			return (Boolean)obj;
		}
		return Boolean.valueOf(String.valueOf(obj));
	}
	
	public String getRootPath() {
		return getString("rootPath");
	}
	
	public String getContextPath() {
		return getString("contextPath");
	}
	
	public String getDir() {
		return getString("dir");
	}
	
	public String getFilename() {
		return getString("filename");
	}
	
	public String getActionName() {
		return getString("actionName");
	}
	
	public String getFieldName() {
		return getString("fieldName");
	}

	public String getSavePath() {
		return getString("savePath");
	}
	
	public String getUrlPrefix() {
		return getString("urlPrefix");
	}
	
	public String getInsertAlign() {
		return getString("insertAlign");
	}
	
	public boolean getCompressEnable() {
		return getBoolean("compressEnable");
	}
	
	public boolean isBase64() {
		return getBoolean("isBase64");
	}
	
	public int getCompressBorder() {
		return getNumber("compressBorder", 1600).intValue();
	}
	
	public long getMaxSize() {
		return getNumber("maxSize", 100*1024*1024L).longValue();
	}
	
	public int getCount() {
		return getNumber("count", 20).intValue();
	}
	
	public int getTimeout() {
		return getNumber("timeout", 30000).intValue();
	}
	
	public String[] getFilter() {
		return getStringArray("filter");
	}

	public String[] getAllowTypes() {
		return getStringArray("allowFiles");
	}
	
	public Action getAction() {
		return (Action)super.get("action");
	}
	
	public String pathToUrl(File file) {
		String path = PathFormat.formatFileSeparator(file.getAbsolutePath());
//		return path.replace(getRootPath(), getContextPath());
		return path.replace(getRootPath(),"");//这里原来会加入相对路径，现在去掉
	}
	
	private Set<String> allowTypes;
	
	public boolean containAllowType(String type) {
		synchronized(this) {
			if (allowTypes==null) {
				allowTypes = new HashSet<String>(Arrays.asList(getAllowTypes()));
			}
		}
		return allowTypes.contains(type);
	}
	
	private Set<String> filters;
	
	public boolean containFitler(String fitler) {
		synchronized(this) {
			if (filters==null) {
				filters = new HashSet<String>(Arrays.asList(getFilter()));
			}
		}
		return filters.contains(fitler);
	}

	 public Object put(String key, Object value) {
         throw new UnsupportedOperationException();
     }
     public Object remove(Object key) {
         throw new UnsupportedOperationException();
     }
	 public void putAll(Map<? extends String, ? extends Object> m) {
		 throw new UnsupportedOperationException();
	 }
	 public void clear() {
         throw new UnsupportedOperationException();
     }

     private transient Set<String> keySet = null;
     private transient Set<Map.Entry<String,Object>> entrySet = null;
     private transient Collection<Object> values = null;

     public Set<String> keySet() {
         if (keySet==null)
             keySet = Collections.unmodifiableSet(super.keySet());
         return keySet;
     }

     public Set<Map.Entry<String,Object>> entrySet() {
         if (entrySet==null)
             entrySet = Collections.unmodifiableSet(super.entrySet());
         return entrySet;
     }

     public Collection<Object> values() {
         if (values==null)
             values = Collections.unmodifiableCollection(super.values());
         return values;
     }
	
}

package com.baidu.ueditor;

import com.sgaop.basis.mvc.Mvcs;
import com.sgaop.common.cons.Cons;
import com.sgaop.entity.sys.UserAccount;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("all")
public class PathFormat {
	
	private static final String TIME = "time";
	private static final String FULL_YEAR = "yyyy";
	private static final String YEAR = "yy";
	private static final String MONTH = "mm";
	private static final String DAY = "dd";
	private static final String HOUR = "hh";
	private static final String MINUTE = "ii";
	private static final String SECOND = "ss";
	private static final String RAND = "rand";
	private static final String USERID = "userid";
	private static final String ATTACH_MODULE = "attach_module";

	public static String random() {
		return String.valueOf(Math.random() * 10000).replace(".", "");
	}
	
	public static String parse(String input) {
		Pattern pattern = Pattern.compile("\\{([^\\}]+)\\}", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, PathFormat.getString(matcher.group(1),null));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 格式化路径, 把windows路径替换成标准路径
	 * @param input 待格式化的路径
	 * @return 格式化后的路径
	 */
	public static String formatFileSeparator(String path) {
		return path.replace(File.separator, "/");
	}

	public static String parse(String input, String filename ) {
		Pattern pattern = Pattern.compile("\\{([^\\}]+)\\}", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);
		String matchStr = null;
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matchStr = matcher.group( 1 );
			if (matchStr.indexOf("filename") != -1 ) {
				filename = filename.replace("$", "\\$").replaceAll("[\\/:*?\"<>|]", "");
				matcher.appendReplacement(sb, filename);
			} else {
				matcher.appendReplacement(sb, PathFormat.getString(matchStr,null));
			}
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static String parse(String input, String filename,String attachmodule) {
		Pattern pattern = Pattern.compile("\\{([^\\}]+)\\}", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);
		String matchStr = null;
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matchStr = matcher.group(1);
			if (matchStr.indexOf("filename") != -1) {
				filename = filename.replace("$", "\\$").replaceAll("[\\/:*?\"<>|]", "");
				matcher.appendReplacement(sb, filename);
			} else {
				matcher.appendReplacement(sb, PathFormat.getString(matchStr, attachmodule));
			}
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
		
	private static String getString (String pattern,String attachmodule) {
		pattern = pattern.toLowerCase();
		Date currentDate = new Date();
		// time 处理
		if (pattern.indexOf(PathFormat.TIME) != -1) {
			return String.valueOf(currentDate.getTime());
		} else if (pattern.indexOf(PathFormat.FULL_YEAR) != -1) {
			return PathFormat.getFullYear(currentDate);
		} else if (pattern.indexOf(PathFormat.YEAR) != -1) {
			return PathFormat.getYear(currentDate);
		} else if (pattern.indexOf(PathFormat.MONTH) != -1) {
			return PathFormat.getMonth(currentDate);
		} else if (pattern.indexOf(PathFormat.DAY) != -1) {
			return PathFormat.getDay(currentDate);
		} else if (pattern.indexOf(PathFormat.HOUR) != -1) {
			return PathFormat.getHour(currentDate);
		} else if (pattern.indexOf(PathFormat.MINUTE) != -1) {
			return PathFormat.getMinute(currentDate);
		} else if (pattern.indexOf(PathFormat.SECOND) != -1) {
			return PathFormat.getSecond(currentDate);
		} else if (pattern.indexOf(PathFormat.RAND) != -1) {
			return PathFormat.getRandom(pattern);
		}if (pattern.indexOf(PathFormat.USERID) != -1) {
			return PathFormat.getUserid();
		}if (pattern.indexOf(PathFormat.ATTACH_MODULE) != -1) {
			return attachmodule+"";
		}
		return pattern;
		
	}

	private static String getFullYear (Date date) {
		return new SimpleDateFormat("yyyy").format(date);
	}
	private static String getUserid () {
		UserAccount user = (UserAccount) Mvcs.getSession().getAttribute(Cons.SESSION_USER);
		return String.valueOf(user.getId());
	}

	private static String getYear (Date date) {
		return new SimpleDateFormat("yy").format(date);
	}
	
	private static String getMonth (Date date) {
		return new SimpleDateFormat("MM").format(date);
	}
	
	private static String getDay (Date date) {
		return new SimpleDateFormat("dd").format(date);
	}
	
	private static String getHour (Date date) {
		return new SimpleDateFormat("HH").format(date);
	}
	
	private static String getMinute (Date date) {
		return new SimpleDateFormat("mm").format(date);
	}
	
	private static String getSecond (Date date) {
		return new SimpleDateFormat("ss").format(date);
	}
	
	private static String getRandom(String pattern) {
		pattern = pattern.split(":")[1].trim();
		int length = Integer.parseInt(pattern);
		return (Math.random() + "").replace(".", "").substring(0, length);
	}

}

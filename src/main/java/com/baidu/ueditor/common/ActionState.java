package com.baidu.ueditor.common;

public enum ActionState {
	
	SUCCESS(0),
	MAX_SIZE(1),
	PERMISSION_DENIED(2),
	FAILED_CREATE_FILE(3),
	IO_ERROR(4),
	NOT_MULTIPART_CONTENT(5),
	PARSE_REQUEST_ERROR(6),
	NOTFOUND_UPLOAD_DATA(7),
	NOT_ALLOW_FILE_TYPE(8),
	
	INVALID_ACTION(101),
	CONFIG_ERROR(102),
	
	PREVENT_HOST(201),
	CONNECTION_ERROR(202),
	REMOTE_FAIL(203),
	
	NOT_DIRECTORY(301),
	NOT_EXIST(302),
	
	ILLEGAL(401),
	ERROR(500)
	;
	
	private final int code;
	
	public int code() {
		return code;
	}
	
	private ActionState(int code) {
		this.code = code;
	}
	
	public static ActionState of(int code) {
		for (ActionState info : values()) {
			if (info.code()==code) {
				return info;
			}
		}
		return null;
	}
	
}

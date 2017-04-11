package com.allets.backend.data.server;

public interface ApiConstants {

	// [DataSource Constants]
	public static final String DATASOURCE_TYPE_COMMON = "COMMON";
	public static final String DATASOURCE_TYPE_READ = "READ";

	// [Common Constants]
	public static final String DEFAULT_CHARSET = "utf-8";
	public static final long COMMON_NUMBER_NULL = -999999;
	public static final String COMMON_NUMBER_NULL_STR = "-999999";

	// [Common Delimiter String]
	public static final String DELIMITER_UNDERSCORE = "_";
	public static final String DELIMITER_HYPHEN = "-";
	public static final String DELIMITER_SLASH = "/";
	public static final String DELIMITER_COLON = ":";
	public static final String DELIMITER_SEMICOLON = ";";
	public static final String DELIMITER_COMMA = ",";
	public static final String DELIMITER_DOT = ".";
	public static final String DELIMITER_AT = "@";
	public static final String DELIMITER_SPACE = " ";
	public static final String DELIMITER_EQUAL = "=";

	// [Common Sort Prefix String]
	public static final String SORT_ASCENDING = "+";
	public static final String SORT_DESCENDING = "-";

	// [USER table contants]
	public static final int AUTH_NONE = 0;
	public static final int AUTH_DONE_DOMESTIC = 1;
	public static final int AUTH_DONE_ABROAD = 2;
	public static final int AUTH_DONE_ALL = 3;

	public static final String CERTIFICATE_TYPE_CONTENTS_DOMESTIC = "00";
	public static final String CERTIFICATE_TYPE_CONTENTS_OVERSEAS = "01";
	public static final String CERTIFICATE_TYPE_JOIN_DOMESTIC = "10";
	public static final String CERTIFICATE_TYPE_JOIN_OVERSEAS = "11";

	// [Comment Sort constants]
	public static final String SORT_LIKE = "LIKE";
	public static final String SORT_TIME = "TIME";
}

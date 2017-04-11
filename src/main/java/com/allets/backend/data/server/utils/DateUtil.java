package com.allets.backend.data.server.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 날짜 관련 유틸리티 서비스
 * 
 * <p>
 * .
 *
 * @author tktaeki.kim@partner.samsung.com
 * @since 2015. 3. 3
 */
public class DateUtil {

	/*
	 * ---------------------------------------------------------------------
	 * Instance fields.
	 * ---------------------------------------------------------------------
	 */
	
	/* 기본 날짜 포맷 */
	final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/*
	 * ---------------------------------------------------------------------
	 * Constructors.
	 * ---------------------------------------------------------------------
	 */
	/*
	 * ---------------------------------------------------------------------
	 * public & interface method.
	 * ---------------------------------------------------------------------
	 */

	/**
	 * 연속된 날짜 텍스트를 받아서 Date 포맷에 맞게 변환 한다.
	 * 
	 * <p/>
	 * 
	 * getDateService().parseDateString("20151201", "yyyy-MM-dd") = "2015-12-01"
	 * getDateService().parseDateString("20151201121324", "yyyy-MM-dd HH:mm:ss")
	 * = "2015-12-01 12:13:12"
	 *
	 * @param date 연속된 날짜 텍스트
	 * @param pattern Date 포맷
	 * @return String 변환된 날짜 텍스트
	 * @throws Exception the exception
	 * @since 2015. 3. 3
	 */
	public static String parseString(String date, String pattern) throws Exception {

		String inputPattern = "yyyyMMdd";
		String sourceDate = StringUtils.trimToEmpty(date);

		// 날짜가 8자리 이상일 경우
		if (StringUtils.length(sourceDate) > 8) {

			inputPattern = "yyyyMMddHHmmss";
		}

		DateTimeFormatter dtf = DateTimeFormat.forPattern(inputPattern);
		DateTime dateTime = dtf.parseDateTime(sourceDate);

		return parseFormat(dateTime, pattern);
	}

	/**
	 * ncp 표준 데이터 포맷을 리턴 한다.
	 * 
	 * <p/>
	 * 
	 * getDateService().parseDefaultFormat(new Date()) = "2015-03-03 13:41:40"
	 *
	 * @param date Date 오브젝트
	 * @return String 기본 포맷으로 변환한 텍스트
	 * @throws Exception the exception
	 * @since 2015. 3. 3
	 */
	public static String parseDefaultFormat(Date date) throws Exception {
		DateTime jodatime = new DateTime(date);
		return parseFormat(jodatime, DEFAULT_DATE_FORMAT);
	}

	/**
	 * 데이트 오브젝트를 날짜 포맷에 맞게 스트링 형태로 리턴 한다.
	 * 
	 * <p/>
	 * 
	 * getDateService().parseDateToString(new Date(), "yyyyMMddHHmmss") =
	 * "20150303134140"
	 *
	 * @param date Date 오브젝트
	 * @param pattern 날짜 포맷
	 * @return String 변환된 텍스트
	 * @throws Exception the exception
	 * @since 2015. 3. 3
	 */
	public static String parseString(Date date, String pattern) throws Exception {
		DateTime jodatime = new DateTime(date);
		return parseFormat(jodatime, pattern);
	}

	/**
	 * 시작일자, 검색일자를 입력 하면 포함된 리스트 월을 리턴 한다
	 * 
	 * <p/>
	 * 
	 * getDateService().getBetweenMonths("20141101", "20150405", "yyyy-MM")
	 * ="[2014-11, 2014-12, 2015-01, 2015-02, 2015-03, 2015-04]"
	 *
	 * @param beginDate 시작날짜 (예:20141101)
	 * @param endDate 마지막날짜(예:20150405)
	 * @param pattern 데이트 포맷 (예:yyyy-MM)
	 * @return List 검색조건에 해당하는 모든 월
	 * @throws Exception the exception
	 * @since 2015. 3. 3
	 */
	public static List<String> getBetweenMonths(final String beginDate,
	        final String endDate, final String pattern) throws Exception {

		final String sourcePattern = "yyyyMMdd";

		DateTimeFormatter dtf = DateTimeFormat.forPattern(sourcePattern);
		DateTime s = dtf.parseDateTime(beginDate);
		DateTime d = dtf.parseDateTime(endDate);

		int months = Months.monthsBetween(s, d).getMonths();

		DateTime tempDateTime;
		String result;
		List<String> results = new ArrayList<String>();

		// 시작일자를 기준으로 종료일자까지 List에 추가 한다.
		// 단 시작일자, 종료일자를 포함 한다.
		for (int i = 0; i <= months; i++) {

			tempDateTime = s.plusMonths(i);
			result = tempDateTime.toString(pattern);
			results.add(result);
		}

		return results;
	}

	/**
	 * [메서드 설명].
	 * 
	 * <p/>
	 * 
	 * Date d1 = d.createDate("20140101142323", "yyyyMMddHHmmss");<br/>
	 * Date d2 = d.createDate("20150101142323", "yyyyMMddHHmmss");<br/>
	 *
	 * System.out.println(getDateService().getBetweenMonths(d1, d2, "yyyy-MM"))
	 * =
	 * "[2014-01, 2014-02, 2014-03, 2014-04, 2014-05, 2014-06, 2014-07, 2014-08, 2014-09, 2014-10, 2014-11, 2014-12, 2015-01]"
	 * <br/>
	 *
	 * @param beginDate 시작일자 텍스트
	 * @param endDate 마지막일자 텍스트
	 * @param pattern 월단위 패턴
	 * @return List 검색조건에 해당하는 모든 월
	 * @throws Exception the exception
	 * @since 2015. 3. 3
	 */
	public static List<String> getBetweenMonths(final Date beginDate,
	        final Date endDate, final String pattern) throws Exception {

		final String sourcePattern = "yyyyMMdd";
		DateTimeFormatter dtf = DateTimeFormat.forPattern(sourcePattern);

		DateTime b = new DateTime(beginDate);
		DateTime e = new DateTime(endDate);
		return getBetweenMonths(dtf.print(b), dtf.print(e), pattern);

	}

	/**
	 * 스트링 텍스트를 Date 형태로 반환 한다.
	 * 
	 * <p/>
	 * 
	 * Date date = getDateService().createDate("20150101142323",
	 * "yyyyMMddHHmmss");<br/>
	 * System.out.println(d1) = "Thu Jan 01 14:23:23 KST 2015"
	 *
	 * @param date 날짜 스트링 데이터
	 * @param pattern 날짜 포맷
	 * @return Date 날짜 오브젝트
	 * @throws Exception the exception
	 * @since 2015. 3. 3
	 */
	public static Date createDate(final String date, String pattern) throws Exception {

		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);

		DateTime dt = DateTime.parse(date, fmt);

		return dt.toDate();

	}

	/**
	 * 스트링 텍스트를 Date 형태로 반환 한다.
	 *
	 * <p/>
	 *
	 * Date date = getDateService().createDefaultDate("20150101142323");<br/>
	 * System.out.println(d1) = "Thu Jan 01 14:23:23 KST 2015"
	 *
	 * @param date 날짜 스트링 데이터
	 * @param pattern 날짜 포맷
	 * @return Date 날짜 오브젝트
	 * @throws Exception the exception
	 * @since 2015. 3. 3
	 */
	public static Date createDefaultDate(final String date) throws Exception {

		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMddHHmmss");

		DateTime dt = DateTime.parse(date, fmt);

		return dt.toDate();

	}

	/**
	 * 지정된 일자 다은 달을 리턴 한다.
	 * 
	 * <p/>
	 * 
	 * getDateService().getNextMonth(new Date(), "yyyy-MM") = 2014-02
	 *
	 * @param beginDate 지정 일자
	 * @param pattern 출력 패턴
	 * @return String 다음날 날짜 스트링
	 * @throws Exception the exception
	 * @since 2015. 3. 3
	 */
	public static String getNextMonth(final Date beginDate, final String pattern)
	        throws Exception {

		DateTime beginDateTime = new DateTime(beginDate).plusMonths(1);

		String results = beginDateTime.toString(pattern);

		return results;
	}

	/**
	 * 지정된 일자의 지정된 달을 리턴 한다.
	 * 
	 * <p/>
	 * 
	 * getDateService().getNextMonth(new Date(), "yyyy-MM",2) = 2014-03
	 *
	 * @param beginDate 지정 일자
	 * @param pattern 출력 패턴
	 * @param plus 카운트 예) 2달은 2, 3달은 3
	 * @return String 다음날 날짜 스트링
	 * @throws Exception the exception
	 * @since 2015. 3. 3
	 */
	public static String getNextMonth(final Date beginDate, final String pattern,
	        int plus) throws Exception {

		DateTime beginDateTime = new DateTime(beginDate).plusMonths(plus);

		String results = beginDateTime.toString(pattern);

		return results;
	}

	/**
	 * 시작일자 와 마지막일자를 계산해서 몇일 차이가 나는지 출력.
	 * 
	 * <p/>
	 * 
	 * Date d1 = getDateService().createDate("20140102142323",
	 * "yyyyMMddHHmmss");<br/>
	 * Date d2 = getDateService().createDate("20150102142323",
	 * "yyyyMMddHHmmss");<br/>
	 *
	 * System.out.println(getDateService().getDurationDate(d1, d2)) = 365<br/>
	 *
	 * @param beginDate 시작일자
	 * @param endDate 마지막 일자
	 * @return Long 차이난 일의 수
	 * @throws Exception the exception
	 * @since 2015. 3. 3
	 */
	public static long getDurationDate(Date beginDate, Date endDate) throws Exception {

		DateTime beginDateTime = new DateTime(beginDate);
		DateTime endDateTime = new DateTime(endDate);

		Duration duration = new Duration(beginDateTime, endDateTime);
		return duration.getStandardDays();
	}

	/**
	 * 시작일자 와 마지막일자를 계산해서 몇 시간 차이가 나는지 출력.<br/>
	 * 만약 11시 32분 과 12시 31분일 경우 0을 리턴 한다.<br/>
	 * 하지만 11시 32분 과 12시 32분일 경우 1을 리턴 한다.
	 * 
	 * <p/>
	 * 
	 * Date d1 = getDateService().createDate("20140102142323",
	 * "yyyyMMddHHmmss");<br/>
	 * Date d2 = getDateService().createDate("20150102142323",
	 * "yyyyMMddHHmmss");<br/>
	 *
	 * System.out.println(getDateService().getDurationDate(d1, d2)) = 24<br/>
	 *
	 * @param beginDate 시작일자
	 * @param endDate 마지막 일자
	 * @return Long 차이난 시간
	 * @throws Exception the exception
	 * @since 2015. 3. 3
	 */
	public static long getDurationHour(Date beginDate, Date endDate) throws Exception {

		DateTime beginDateTime = new DateTime(beginDate);
		DateTime endDateTime = new DateTime(endDate);

		Duration duration = new Duration(beginDateTime, endDateTime);
		return duration.getStandardHours();
	}
	
	/**
	 * 지정된 일자 만큼 더한 날짜를 리턴 한다..
	 * 
	 * <p/>
	 * 
	 * Date d1 = getDateService().createDate("20140101142423",
	 * "yyyyMMddHHmmss"); getDateService().plusDate(d1, 2) =
	 * "Fri Jan 03 14:24:23 KST 2014"
	 *
	 * @param date 지정된 일자
	 * @param plus 추가할 일자
	 * @return Date 추가 일자가 반영된 날짜
	 * @throws Exception the exception
	 * @since 2015. 3. 3
	 */
	public static Date plusDate(Date date, int plus) throws Exception {

		DateTime dateTime = new DateTime(date);
		dateTime = dateTime.plusDays(plus);

		return dateTime.toDate();
	}

	/**
	 * 지정된 날짜의 마지막 일자를 리턴 한다.
	 * 
	 * <p/>
	 *  
	 *  getDateService().daysOfMonth(2015, 2) = 28 
	 *
	 * @param year 년
	 * @param month 월
	 * @return Int 마지막 일자
	 * @since 2015. 3. 3
	 */
	public static int daysOfMonth(int year, int month) {
		DateTime dateTime = new DateTime(year, month, 14, 12, 0, 0, 000);
		return dateTime.dayOfMonth().getMaximumValue();
	}
	
	/**
	 * 날짜 오브젝트를 패턴에 맞는 형식으로 스트링 변환 한다.
	 * 
	 * <p/>
	 * 
	 * 
	 * @param dateTime 데이트 오브젝트
	 * @param pattern 날짜 패턴
	 * @return String 변환된 텍스트
	 * @since 2015. 3. 3
	 */
	private static String parseFormat(DateTime dateTime, String pattern) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
		return dtf.print(dateTime);
	}

	/*
	 * ---------------------------------------------------------------------
	 * private method.
	 * ---------------------------------------------------------------------
	 */


	public static String getUTCDate() throws Exception {
 		java.util.Calendar cal = java.util.Calendar.getInstance();
		int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
		int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
		cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		return  parseDefaultFormat(new Date(cal.getTimeInMillis()));
	}
}

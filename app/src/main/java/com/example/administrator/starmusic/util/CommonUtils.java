package com.example.administrator.starmusic.util;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
/**
 * ͨ�ù�����
 */
public class CommonUtils {
	/**
	 *  ��ʽ��ʱ��Ĺ���
	 */
	private static SimpleDateFormat sdf = new SimpleDateFormat("mm:ss", Locale.CHINA);
	/**
	 *  ����ʽ����ʱ�����
	 */
	private static Date date = new Date();

	/**
	 * ��ȡ��ʽ�����ʱ��
	 * 
	 * @param timeMillis
	 *            ʱ��ֵ��ȡֵΪ��1970-1-1 0:00:00����ĺ�����
	 * @return ��ʽ��Ϊmm:ss��ʽ���ַ���
	 */
	public static String getFormattedTime(long timeMillis) {
		// ����ʱ��
		date.setTime(timeMillis);
		// ���ظ�ʽ����Ľ��
		return sdf.format(date);
	}
}

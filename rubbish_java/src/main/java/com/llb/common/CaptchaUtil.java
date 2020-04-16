package com.llb.common;

import java.util.Random;

/**
 * 验证码管理
 * @author zuoliangzhu
 *
 */
public class CaptchaUtil {

	/**
	 * 生成新的验证码
	 */
	public static String createNewCaptcha(){
		final char[] codeSequence = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
				'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		final int codeCount = 4;// 验证码个数
		Random random = new Random();
		final StringBuffer code = new StringBuffer();
		for (int i = 0; i < codeCount; i++) {
			String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
			code.append(strRand);
		}
		return code.toString();
	}
}

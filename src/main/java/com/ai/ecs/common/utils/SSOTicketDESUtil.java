package com.ai.ecs.common.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SSOTicketDESUtil{
	
	private static final String IV = "Isca5!88";

	public static byte[] generateKey() {
		try {
			SecureRandom sr = new SecureRandom();

			KeyGenerator kg = KeyGenerator.getInstance("DES");
			kg.init(sr);

			SecretKey secretKey = kg.generateKey();

			byte[] key = secretKey.getEncoded();

			return key;
		} catch (NoSuchAlgorithmException e) {
			System.err.println("DES�㷨��������Կ����!");
			e.printStackTrace();
		}

		return null;
	}

	public static byte[] encrypt(byte[] data, byte[] key) {
		try {
			SecureRandom sr = new SecureRandom();

			DESKeySpec dks = new DESKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);

			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

			cipher.init(1, secretKey, sr);

			byte[] encryptedData = cipher.doFinal(data);

			return encryptedData;
		} catch (Exception e) {
			System.err.println("DES�㷨���������ݳ���!");
			e.printStackTrace();
		}

		return null;
	}

	public static byte[] decrypt(byte[] data, byte[] key) {
		try {
			SecureRandom sr = new SecureRandom();

			DESKeySpec dks = new DESKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);

			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

			cipher.init(2, secretKey, sr);

			byte[] decryptedData = cipher.doFinal(data);

			return decryptedData;
		} catch (Exception e) {
			System.err.println("DES�㷨�����ܳ���");
			e.printStackTrace();
		}

		return null;
	}

	public static byte[] CBCEncrypt(byte[] data, byte[] key, byte[] iv) {
		try {
			DESKeySpec dks = new DESKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);

			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

			IvParameterSpec param = new IvParameterSpec(iv);
			cipher.init(1, secretKey, param);

			byte[] encryptedData = cipher.doFinal(data);

			return encryptedData;
		} catch (Exception e) {
			System.err.println("DES�㷨���������ݳ���!");
			e.printStackTrace();
		}

		return null;
	}

	public static byte[] CBCDecrypt(byte[] data, byte[] key, byte[] iv) {
		try {
			DESKeySpec dks = new DESKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);

			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

			IvParameterSpec param = new IvParameterSpec(iv);
			cipher.init(2, secretKey, param);

			byte[] decryptedData = cipher.doFinal(data);

			return decryptedData;
		} catch (Exception e) {
			System.err.println("DES�㷨�����ܳ���");
			e.printStackTrace();
		}

		return null;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; ++n) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs;
		}
		return hs.toLowerCase();
	}

	public static byte[] hexStr2Bytes(String src) {
		int m = 0;
		int n = 0;
		int l = src.length() / 2;

		byte[] ret = new byte[l];
		for (int i = 0; i < l; ++i) {
			m = i * 2 + 1;
			n = m + 1;
			ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
		}
		return ret;
	}

	public static byte uniteBytes(String src0, String src1) {
		byte b0 = Byte.decode("0x" + src0).byteValue();
		b0 = (byte) (b0 << 4);
		byte b1 = Byte.decode("0x" + src1).byteValue();
		byte ret = (byte) (b0 | b1);
		return ret;
	}

	public static String encrypt(String source, String key) {
		byte[] b = CBCEncrypt(source.getBytes(), key.getBytes(), IV.getBytes());
		return byte2hex(b);
	}

	public static String decrypt(String ciphertext, String key) {
		byte[] b = CBCDecrypt(hexStr2Bytes(ciphertext), key.getBytes(), IV.getBytes());
		if(null != b){
			return new String(b);
		}
		return null;
	}

	public static void main(String[] args) {
		String key = "eccm&crM#$Rm&eccm&crM#$Rm&eccm";
		//String key = "cs&cr)(&$m&loia&crM#$RVCXR";
		String content = "测试";// 要加密内容  uid:经分编号
//		String content = "&staffid=TESTTJ10&ticket=123456&uid=uTest001";// 要加密内容  uid:经分编号
//		String content = "&staffid=TESTTJ10&ticket=123456&uid=uTest001";// 要加密内容  uid:经分编号
		
		String data = SSOTicketDESUtil.encrypt(content, key);
//		String  data = "635c5965241c2ac3ed0a9d3a696b486d6cbde91683938b88c074fc208bd5d7a7";

//		String data2 = SSOTicketDESUtil.decrypt(data, key);
		System.out.println("content:" + data);
		System.out.println("解密："+ SSOTicketDESUtil.decrypt(data, key));
//		System.out.println("content1:" + data2);
	}	
	
}
package com.msgcloud.utils;
/**
 * DES加密解密工具类 注意，此类使用通用密钥。
 *
 */
import java.security.Key;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.codec.binary.Base64;

import com.elementspeed.framework.base.exception.BOException;
public class DESUtil{
    private static final Log _logger = LogFactory.getLog(DESUtil.class);
    private final static String algorithm = "AES";
    private static SecretKeySpec key;
    private static Cipher c;
	//默认密码
	private static final String DEF_PASSWORD = "abc123";
    static {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance(algorithm);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            //密钥
            String PASSWORD_KEY = "1qaz!QAZ";
            secureRandom.setSeed(PASSWORD_KEY.getBytes());
            keygen.init(128, secureRandom);
            SecretKey deskey = keygen.generateKey();
            byte[] enCodeFormat = deskey.getEncoded();
            key = new SecretKeySpec(enCodeFormat, algorithm);
            c = Cipher.getInstance(algorithm);
        } catch (Exception ex) {
            _logger.error(ex);
        }
    }

    /**
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String encode(String str) throws Exception {
        if (StringUtil.isEmpty(str)) {
            return str;
        }
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = c.doFinal(str.getBytes("utf-8"));
        return new String(Base64.encodeBase64(bytes));
    }
    /**
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String decode(String str) {
        String r = str;
        if (!StringUtil.isEmpty(str)) {
            try {
                byte[] bytes = Base64.decodeBase64(str.getBytes("utf-8"));
                c.init(Cipher.DECRYPT_MODE, key);
                byte[] abytes = c.doFinal(bytes);
                r = new String(abytes, "utf-8");
            } catch (Exception ex) {
                _logger.warn(ex.getMessage(), ex);
            }
        }
        return r;
    }
    /***
     * 创建默认密码
     * @return
     * @throws BOException
     */
	public static  String createPassword() throws BOException{
		try {
			return DESUtil.encode(DEF_PASSWORD);
		} catch (Exception e) {
			throw new BOException( e.getMessage());
		}
	}
    
    public static void main(String[] args) throws Exception {
    	String password = "123456";
    	String passwordStr = DESUtil.encode(password);
    	System.out.println("加密：" + passwordStr);
    	System.out.println("加密：" + passwordStr);
    	System.out.println("加密：" + passwordStr);
    	System.out.println("加密：" + passwordStr);
    	System.out.println("解密:" + DESUtil.decode(passwordStr));
    	   getKey();// 生成密匙

    	  String strEnc = getEncString("/vendormgt/assess/compute/plan/getStep/get&admin@qq.com&DC8vGbK/+nCwzJItxd2Y2A==&2&/3/0b98cb64-c777-407e-9726-5ade275b50da");// 加密字符串,返回String的密文
    	  System.out.println(strEnc);

    	  String strDes = getDesString("FB06F6BA971AA0ACC3DBA29B9153D6EEF5CFF95B5B9FA1910D68A1B7D6C7A03AB643B58990E5128AD8242901AAF819FB97D46497DCCA86452E6981CF71125DE1B455F1928C84D77936929F21A92D08EE698CD42F358156EDA8EA81C33089A4E1D29939924BBA6D014B43A51CE8459CC0AA28C2900169517DA2E42DE25758A0E7F6F1BF67EE27C99E");// 把String 类型的密文解密
    	  System.out.println(strDes);
	}
    
    
    public final static String keyCode = "elementspeed";
    static Key key2;

    /**
     * 根据参数生成KEY
     * 
     * @param strKey
     */
    public static void getKey() {
     try {
    	 
    	 SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 
         DESKeySpec keySpec = new DESKeySpec(keyCode.getBytes("utf-8")); 
         key2 = keyFactory.generateSecret(keySpec); 
//         _generator = null;
//      KeyGenerator _generator = KeyGenerator.getInstance("DES");
//      _generator.init(new SecureRandom(keyCode.getBytes()));
//      key2 = _generator.generateKey();
//      _generator = null;
     } catch (Exception e) {
      e.printStackTrace();
     }
    }

    /**
     * 加密String明文输入,String密文输出
     * 
     * @param strMing
     * @return
     */
    public static String getEncString(String strMing) {
     String strMi = "";
     try {
      return byte2hex(getEncCode(strMing.getBytes()));
     } catch (Exception e) {
      e.printStackTrace();
     } 
     return strMi;
    }

    /**
     * 解密 以String密文输入,String明文输出
     * 
     * @param strMi
     * @return
     */
    public static String getDesString(String strMi) {
     String strMing = "";
     try {
      return new String(getDesCode(hex2byte(strMi.getBytes())));
     } catch (Exception e) {
      e.printStackTrace();
     } finally {
     }
     return strMing;
    }

    /**
     * 加密以byte[]明文输入,byte[]密文输出
     * 
     * @param byteS
     * @return
     */
    private static byte[] getEncCode(byte[] byteS) {
     byte[] byteFina = null;
     Cipher cipher;
     try {
      cipher = Cipher.getInstance("DES");
      if(key2==null){
    	  getKey();
      }
      cipher.init(Cipher.ENCRYPT_MODE, key2);
      byteFina = cipher.doFinal(byteS);
     } catch (Exception e) {
      e.printStackTrace();
     } finally {
      cipher = null;
     }
     return byteFina;
    }

    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     * 
     * @param byteD
     * @return
     */
    private static byte[] getDesCode(byte[] byteD) {
     Cipher cipher;
     byte[] byteFina = null;
     try {
      cipher = Cipher.getInstance("DES");
      if(key2==null){
    	  getKey();
      }
      cipher.init(Cipher.DECRYPT_MODE, key2);
      byteFina = cipher.doFinal(byteD);
     } catch (Exception e) {
      e.printStackTrace();
     } finally {
      cipher = null;
     }
     return byteFina;
    }

    /**
     * 二行制转字符串
     * 
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) { // 一个字节的数，
     // 转成16进制字符串
     String hs = "";
     String stmp = "";
     for (int n = 0; n < b.length; n++) {
      // 整数转成十六进制表示
      stmp = Integer.toHexString(b[n] & 0XFF);
      if (stmp.length() == 1) {
		hs = hs + "0" + stmp;
	} else {
		hs = hs + stmp;
	}
     }
     return hs.toUpperCase(); // 转成大写
    }

    public static byte[] hex2byte(byte[] b) {
     if (b.length % 2 != 0) {
		throw new IllegalArgumentException("长度不是偶数");
	}
     byte[] b2 = new byte[b.length / 2];
     for (int n = 0; n < b.length; n += 2) {
      String item = new String(b, n, 2);
      // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
      b2[n / 2] = (byte) Integer.parseInt(item, 16);
     }

     return b2;
    }
}

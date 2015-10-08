package com.gcs.utils;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class StringUtils {
	

    public static void main(String[] args) { 
        String[] strArr = new String[] { "www.micmiu.com", "!@#$%^&*()_+{}[]|\"'?/:;<>,.", "！￥……（）——：；“”‘'《》，。？、", "sss不s要s啊aa", "やめて", "韩佳人", "???" }; 
        for (String str : strArr) { 
            System.out.println("===========> 测试字符串：" + str); 
            System.out.println("正则判断结果：" + isChineseByREG(str) + " -- " + isChineseByName(str)); 
            System.out.println("Unicode判断结果 ：" + isChinese(str)); 
            System.out.println("详细判断列表："); 
            char[] ch = str.toCharArray(); 
            for (int i = 0; i < ch.length; i++) { 
                char c = ch[i]; 
                System.out.println(c + " --> " + (isChinese(c) ? "是" : "否")); 
            } 
        } 
    }
    

	/**
     * 
     * @Title: processFileName
     * 
     * @Description: ie,chrom,firfox下处理文件名显示乱码
     */
    public static String processFileName(HttpServletRequest request, String fileNames) {
        String codedfilename = null;
        try {
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && -1 != agent.indexOf("MSIE") || null != agent && -1 != agent.indexOf("Trident")) {// ie
                String name = java.net.URLEncoder.encode(fileNames, "UTF8");
                codedfilename = name;
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等
                codedfilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codedfilename;
    }

	/**
	 * 生成机器码
	 */
	public static String getGuid(){
		UUID uuid = UUID.randomUUID();   
		return uuid.toString();
	}
	
	/**
	 * 判断实体类全部的属性是否有值
	 * @param obj
	 */
	public static boolean isObject(Object obj) {
		Class<?> cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields){
			try {
				if(!field.getName().equals("serialVersionUID")){
					PropertyDescriptor pd = new PropertyDescriptor(field.getName(), cls);
				    Method rM = pd.getReadMethod();//获得读方法
				    Object object = rM.invoke(obj);
				    if(null != object && object != "")
				    	return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
    	return false;
    }
	
	/**
	 * String类型转换为数组
	 * @param str 带转换的字符串
	 * @param spt 分隔符
	 * @return
	 */
	public static String[] getStrArr(String str,String spt){
		String[] strs = str.split(spt);
		String[] ls = new String[strs.length];
		for (int i = 0,length = strs.length; i < length; i++) {
			ls[i] = strs[i];
		}
		return ls;
	}
	
	
    /*
     * 将空的long类型转换未0L
     * 
     */
    public static Long changeNullToZero(Long o){
		if(o!=null){
			return o;
		}else{
			return 0L;
		}
	}
	/*
	 * 将空的double类型转换未0.0
	 */
	public static Double changeNullToZero(Double o){
		if(o!=null){
			return o;
		}else{
			return 0.0;
		}
	}
  //随机生成字符串
    public static String getRandomString(int length) { //length表示生成字符串的长度
    	String base = "abcdefghijklmnopqrstuvwxyz0123456789"; //生成字符串从此序列中取
    	Random random = new Random(); 
    	StringBuffer sb = new StringBuffer(); 
    	for (int i = 0; i < length; i++) { 
    	int number = random.nextInt(base.length()); 
    	sb.append(base.charAt(number)); 
    	} 
    	Date d = new Date();
    	long longtime = d.getTime();
    	sb.append(longtime);
    	return sb.toString(); 
    }
    // 根据Unicode编码完美的判断中文汉字和符号 
    private static boolean isChinese(char c) { 
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c); 
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS 
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B 
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS 
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) { 
            return true; 
        } 
        return false; 
    } 

    // 完整的判断中文汉字和符号 
    public static boolean isChinese(String strName) { 
        char[] ch = strName.toCharArray(); 
        for (int i = 0; i < ch.length; i++) { 
            char c = ch[i]; 
            if (isChinese(c)) { 
                return true; 
            } 
        } 
        return false; 
    } 

    // 只能判断部分CJK字符（CJK统一汉字） 
    public static boolean isChineseByREG(String str) { 
        if (str == null) { 
            return false; 
        } 
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+"); 
        return pattern.matcher(str.trim()).find(); 
    } 

    // 只能判断部分CJK字符（CJK统一汉字） 
    public static boolean isChineseByName(String str) { 
        if (str == null) { 
            return false; 
        } 
        // 大小写不同：\\p 表示包含，\\P 表示不包含 
        // \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码 
        String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}"; 
        Pattern pattern = Pattern.compile(reg); 
        return pattern.matcher(str.trim()).find(); 
    } 

	
	
	public static String changeNullToZero(Object o){
		if(o!=null){
			return o.toString();
		}else{
			return "0";
		}
	}
	public static String changeNull(Object o){
		if(o!=null){
			return o.toString();
		}else{
			return "";
		}
	}
	/**
	 * 根据字符串获得查询的sql
	 */
	public static String getQuerySql(String str){
		String reStr = "";
		if(org.apache.commons.lang3.StringUtils.isNotBlank(str)){
			String[] strs = str.split("-");
			for(String s : strs){
				if(org.apache.commons.lang3.StringUtils.isNotBlank(s)){
					reStr = reStr+"'"+s+"',";
				}
			}
		}
		if(reStr.length()>0){
			reStr = reStr.substring(0,reStr.length()-1);
		}
		return reStr;
	}
	
	/**
	 * 根据字符串
	 * @param args
	 */
	public static String getIDsByList(List<String> list){
		String ids = "";
		for(String str:list){
			ids = ids + "'"+str+"',";
		}
		if(ids.length()>0){
			ids = ids.substring(0,ids.length()-1);
		}
		return ids;
	}
	
	/**
	 * 转换字符串为指定编码格式
	 * 
	 * @param str 字符串
	 * @param encoding 原字符串编码格式
	 * @param encoding 转后编码格式
	 * @return
	 */
	public static String changeCharSet(String str, String encod, String encoding) {
		try {
			return new String(str.getBytes(encod), encoding);
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	/**
	 * 判断一个字符串是否包含子字符串
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean stringFindString(String s1,String s2){
		boolean result = false;
		 int count = 0; 
		 //长字符串
		 String str ="";
		 //短字符串
		 String s = "";
		 if(s2.length()>s1.length()){
			 str = s2;
			 s = s1;
		 }else{
			 str = s1;
			 s = s2;
		 }
		 for(int i=0; i<str.length() ; ){  
	            int c = -1;  
	            c = str.indexOf(s);  
	            //如果有S这样的子串。则C的值不是-1.  
	            if(c != -1){  
	                //这里的c+1 而不是 c+ s.length();这是因为。如果str的字符串是“aaaa”， s = “aa”，则结果是2个。但是实际上是3个子字符串  
	                //将剩下的字符冲洗取出放到str中  
	                str = str.substring(c + 1);  
	                count ++;  
	                result = true;
	            }  
	            else {  
	                //i++;  
	                break;  
	            }  
	              
	        }  
		return result;
		
	}
	/**
	 * 分割字符串，将字符串中的‘，’换成‘|’
	 * @param str
	 * @return
	 */
	public static String changeString(List<String> str){
		StringBuilder userAndOrg = new StringBuilder();
		if(str!=null){
			for(int i=0;i<str.size();i++){
					userAndOrg.append(str.get(i)+"|");
				}
			}
		return userAndOrg.toString();
	}
	
}

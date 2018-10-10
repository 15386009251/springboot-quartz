package com.quartz.demo.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javax.servlet.http.HttpServletRequest;

/**
 * 系统级工具类
 */
public class GloubFunc
{
    protected static Logger logger = LoggerFactory.getLogger(GloubFunc.class);

    /**
     * @param
     * @return 对象
     * @throws Exception
     * @desc 获得对象
     */
    public static Object refObject(String objName)
        throws Exception
    {
        try
        {
            Object objBean = Class.forName(objName);
            return objBean;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    /**
     * 检测字符串是否不为空(null,"","null")
     *
     * @param s
     * @return 不为空则返回true，否则返回false
     */
    public static boolean notEmpty(String s)
    {
        return s != null && !"".equals(s) && !"null".equals(s);
    }

    /**
     * 检测字符串是否为空(null,"","null")
     *
     * @param s
     * @return 为空则返回true，不否则返回false
     */
    public static boolean isEmpty(String s)
    {
        return s == null || "".equals(s) || "null".equals(s);
    }

    /**
     * @return 方法返回对象
     * @throws Exception
     * @desc 反射对象方法
     */
    public static Object refMethod(Object obj, String methodName, Object parm, Class<?> arg)
        throws Exception
    {
        try
        {
            Method m = obj.getClass().getMethod(methodName, arg);
            Object reVal = (Object)m.invoke(obj, (String)parm);
            return reVal;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    /**
     * 字符串比较
     *
     * @param value
     * @param equalValue
     * @return
     */
    public static boolean equalsValue(String value, String equalValue)
    {
        if (value != null && value.equals(equalValue))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 判断字符串是否为空
     *
     * @param value
     * @return
     */
    public static boolean equalsNull(Object value)
    {
        if (value != null && !value.equals("") && !value.equals("null"))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 验证整形字符串
     *
     * @param numStr
     * @return
     */
    public static boolean equalsNum(String numStr)
    {
        boolean flag = false;
        try
        {
            Long.parseLong(numStr);
            flag = true;
        }
        catch (Exception e)
        {
            flag = false;
        }
        return flag;
    }

    /**
     * 字符串参数检查
     *
     * @param str
     * @return boolean，检查不通过返回true
     */
    public static boolean checkString(String str)
    {
        boolean flag = true;
        if (null == str || "".equals(str) || "null".equals(str))
        {
            return flag;
        }
        else
        {
            flag = false;
        }
        return flag;
    }

    /**
     * 字符串null转换
     *
     * @param str
     * @return 如果为null返回空，
     */
    public static String isNull(String str)
    {
        if (null == str)
        {
            return "";
        }
        else
        {
            return str.trim();
        }
    }

    /**
     * ************************************************************************ 是否为正数判断（非负数）
     *
     * @param str
     * @return 正确返回true，错误返回false
     */
    public static boolean isNumber(String str)
    {
        if (str == null || str.equals(""))
        {
            return false;
        }

        Pattern pattern = Pattern.compile("^(-|\\+)?\\d+(\\.\\d+)?$");
        Matcher isNum = pattern.matcher(str);
        boolean flag = isNum.matches();
        return flag;
    }

    /**
     * ************************************************************************ 随机产生四位数字
     *
     * @return string
     */
    public static String random(int n)
    {
        String sRand = "";

        Random random = new Random();
        for (int i = 0; i < n; i++)
        {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
        }
        return sRand;

    }

    /**
     * ************************************************************************ 是否为数字
     *
     * @return string
     */
    public static boolean isNumeric(String str)
    {
        for (int i = str.length(); --i >= 0; )
        {
            int chr = str.charAt(i);
            if (chr < 48 || chr > 57)
                return false;
        }
        return true;

    }

    /**
     * 初始化数字
     *
     * @param s    格式化对象
     * @param nDef 对象为null时的返回str
     * @return 结果字符串
     */
    public static long initLong(String s, long nDef)
    {
        if (s != null && !"".equals(s))
        {
            return Long.parseLong(s);
        }
        else
        {
            return nDef;
        }
    }

    /**
     * 初始化数字
     *
     * @param s    格式化对象
     * @param nDef 对象为null时的返回 nDef
     * @return 结果字符串
     */
    public static int initInt(String s, int nDef)
    {
        if (s != null && !"".equals(s))
        {
            return Integer.parseInt(s);
        }
        else
        {
            return nDef;
        }
    }

    /**
     * @param s
     * @param nDef
     * @return
     * @Title: initDouble
     * @Description:
     */
    public static double initDouble(String s, int nDef)
    {
        if (s != null && !"".equals(s))
        {
            return Double.parseDouble(s);
        }
        else
        {
            return nDef;
        }
    }

    /**
     * 初始化字符串
     *
     * @param o    格式化对象
     * @param sDef 对象为null时的返回 sDef
     * @return 结果字符串
     */
    public static String initStr(Object o, String sDef)
    {
        if (o != null)
        {
            return o.toString();
        }
        else
        {
            return sDef;
        }
    }

    /**
     * 格式化字符串
     *
     * @param o 格式化对象
     * @return 结果字符串 对象为null时的返回“”；
     */
    public static String initStr(Object o)
    {
        return initStr(o, "");
    }

    /**
     * @param time
     * @return
     * @Title: strToDate
     * @Description:字符串转化为时间
     */
    public static Date strToDate(String time)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try
        {
            if (!"".equals(time) && time != null)
            {
                date = df.parse(time);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * @param time
     * @return
     * @Title: strToDateByAlipay
     * @Description: 时间转日期
     */
    public static Date strToDateByAlipay(String time)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try
        {
            if (!"".equals(time) && time != null)
            {
                date = df.parse(time);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return date;
    }

    public static String date2StringByWx(Date time)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        String date = "";
        try
        {
            if (time != null)
            {
                date = df.format(time);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return date;
    }

    public static Date strToDateByWx(String time)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        Date date = new Date();
        try
        {
            if (!"".equals(time) && time != null)
            {
                date = df.parse(time);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return date;
    }

    public static Date strToDateByWxDone(String time)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        try
        {
            if (!"".equals(time) && time != null)
            {
                date = df.parse(time);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * @param time
     * @return
     * @Title: date2StringBydayTime
     * @Description: 时间转化字符串
     */
    public static String date2StringBydayTime(Date time)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = "";
        try
        {
            if (time != null)
            {
                date = df.format(time);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param time
     * @return
     * @Title: dateToStr
     * @Description: 时间转化字符串
     */
    public static String dateToStr(Date time)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = "";
        try
        {
            if (time != null)
            {
                date = df.format(time);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 格式化时间,Date->String
     *
     * @param date
     * @param mode 时间模式,如yyyy.MM.dd HH:mm:ss
     * @return
     */
    public static String formatDate(Date date, String mode)
    {
        return new SimpleDateFormat(mode).format(date);
    }

    /**
     * 解析时间,String->Date
     *
     * @param date
     * @param mode
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public static Date parseDate(String date, String mode)
        throws ParseException
    {
        return new SimpleDateFormat(mode).parse(date);
    }

    /**
     * 当前时间
     *
     * @return
     * @Title: now
     * @Description:
     */
    public static Date now()
    {
        return Calendar.getInstance().getTime();
    }

    /**
     * 当前时间字符串
     *
     * @param format 格式,如"2008-11-15"
     * @return
     */
    public static String now(String format)
    {
        return formatDate(now(), format);
    }

    /**
     * @param longObj
     * @return
     */
    public static Long ObjectTOLong(Object longObj)
    {
        if (longObj != null && !"null".equals(longObj))
        {
            return Long.parseLong(String.valueOf(longObj));
        }
        else
        {
            return null;
        }
    }

    public static long ObjectTolong(Object longObj)
    {
        if (longObj != null && !"null".equals(longObj))
        {
            return Long.parseLong(String.valueOf(longObj));
        }
        else
        {
            return 0l;
        }
    }

    /**
     * 获取公共异常
     *
     * @param e
     * @return
     */
    public static BusinessException getException(Exception e)
    {
        logger.error("origin Exception：", e);
        BusinessException ex;
        if (BusinessException.class.equals(e.getClass()))
        {
            ex = (BusinessException)e;
        }
        else
        {
            // 其他未捕获的异常返回"1-系统异常"
            ex = new BusinessException(e);
        }

        return ex;
    }

    /**
     * 带毫秒的时间转换
     *
     * @param time yyyyMMdd HHmmss FFF
     * @return
     */
    public static String date2StrByInt(Date time, String par)
    {
        SimpleDateFormat df = new SimpleDateFormat(par);
        String date = "";
        try
        {
            if (time != null)
            {
                date = df.format(time);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return date;
    }

    public static String[] listToArr(List<String> list)
    {

        String[] arr = null;

        if (list != null && list.size() > 0)
        {

            arr = new String[list.size()];

            for (int i = 0; i < list.size(); i++)
            {
                arr[i] = list.get(i);
            }

        }

        return arr;
    }

    /**
     * 封装请求头
     * @param request
     * @return
     */
    public static HttpEntity<?> wrapHttpEntity(Object request)
    {
        //请求头封装
        HttpEntity<?> httpEntity = null;
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        if (null != request)
        {
            if(MultiValueMap.class.isAssignableFrom(request.getClass())) {
                headers.add("Content-Type", "application/x-www-form-urlencoded");
            } else if(request instanceof String && ((String) request).indexOf("xml version") != -1) {
                headers.add("Content-Type", "application/x-www-form-urlencoded");
            }
        }

        httpEntity = new HttpEntity<Object>(request, headers);
        return httpEntity;
    }

    /**
     * 封装请求头for fullservice xml
     * @param request
     * @return
     */
    public static HttpEntity<?> wrapHttpEntityForFullServiceXml(Object request)
    {
        //请求头封装
        HttpEntity<?> httpEntity = null;
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        httpEntity = new HttpEntity<Object>(request, headers);
        return httpEntity;
    }

//    public static List<Integer> random(int n, int L){
//        List<Integer> list = new ArrayList<>();
//        Random rand = new Random();
//        int temp = L;
//        for(int i = 1, j; i < n; i++){
//            j = rand.nextInt(temp-1) + 1;
//            if(j > temp-(n-i)){
//                j = temp-(n-i);
//            }
//            else if(j<= 0){
//                j = 1;
//            }
//            temp -= j;
//            list.add(j);
//        }
//        list.add(temp);
//        return  list;
//    }



    private static double[] randomParts(int parts) {
        double[] values = new double[parts];
        for (int i = 0; i < parts; i++) {
            values[i] = Math.random();
        }
        return values;
    }



    /**
     * 將一個正整數隨機分成N份
     * @param parts 份數
     * @param total 正整數
     */
    public static List<Integer> random(int parts,int total) {
        int[] v = new int[parts];
        double[] r = randomParts(parts);
        double totalRate = Arrays.stream(r).sum();
        for (int i = 0; i < parts; i++) {
            v[i] = Double.valueOf(Math.floor((r[i] / totalRate) * total)).intValue();
            if(v[i] == 0) v[i] = 1;
        }
        v[parts - 1] = total - Arrays.stream(v).sum() + v[parts - 1];

        List<Integer> li = new ArrayList<>();

        for(int inn : v){
            li.add(inn);
        }

        return li;
    }




    public static  String getString(JSONObject originalJson, String nodePath) {
        if(nodePath.indexOf(".") < 0 && !StringUtils.isEmpty(nodePath)) {
            return originalJson.getString(nodePath);
        } else {
            String targetJsonNodePath = nodePath.substring(0, nodePath.lastIndexOf("."));
            JSONObject targetJson = getJSONObject(originalJson, targetJsonNodePath);
            String targetNodePath = nodePath.substring(nodePath.lastIndexOf(".") + 1, nodePath.length());
            return targetJson.getString(targetNodePath);
        }
    }

    public static JSONObject getJSONObject(JSONObject originalJson, String nodePath) {
        if(nodePath.indexOf(".") < 0 && !StringUtils.isEmpty(nodePath)) {
            return originalJson.getJSONObject(nodePath);
        } else {
            String dealNode = nodePath.substring(0, nodePath.indexOf("."));
            JSONObject targetJson = originalJson.getJSONObject(dealNode);
            String leftNodePath = nodePath.substring(nodePath.indexOf(".") + 1, nodePath.length());
            return getJSONObject(targetJson, leftNodePath);
        }
    }



    public static List<String> setElementList(Map<String,Object> map){
        List<String> list = new ArrayList<>();

        JSONObject json = JSON.parseObject(JSON.toJSONString(map));

        if(json!=null && json.containsKey("elementInfo")){

            JSONArray array = json.getJSONArray("elementInfo");

            if(array!=null && array.size()>0){

                for(int i = 0; i < array.size(); i ++) {
                    JSONObject item = array.getJSONObject(i);

                    String productId = item.getString("id");

                    list.add(productId);
                }
            }

        }
        return list;
    }



    public static String getElement(List<String> liEle,String[] eleArr){

        String s ="";

        if(eleArr != null && eleArr.length>0)
        {
            for(String ele : eleArr)
            {

                if(!liEle.contains(ele)){

                    s = ele;
                    break;
                }
            }
        }

        return s;
    }



    public static void main(String[] args)
    {

//        for(int i=0;i<20;i++){
//
//            System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
//
//        }

//        boolean flag = true;
//
//        System.out.println(flag?1:2);

        long timestampL=0;
        try {
            timestampL=Long.valueOf("1518314680252");
        } catch (Exception e) {
        }

        long current=System.currentTimeMillis();
        long offset=Math.abs(current-timestampL);

        System.out.println(current);
        System.out.println(timestampL);
        System.out.println(offset);
        if (offset>60*10*1000) {
        }


    }

}

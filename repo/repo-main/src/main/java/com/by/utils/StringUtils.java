package com.by.utils;

public class StringUtils {
   public  static  boolean isEmpty(String str)
   {
       if(null==str||"".equals(str))
       {
           return true;
       }
       return false;
   }
}

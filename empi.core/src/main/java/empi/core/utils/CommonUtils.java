package empi.core.utils;

import java.util.UUID;

public class CommonUtils {
    
    /**
     * 返回小写 的uuid 146982ce1d904767ad1d02ab0547951f
     * @return
     */
    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
    }

    

    public static void main(String[] args) {
       String s= uuid();
       System.out.println(s);
    }
}

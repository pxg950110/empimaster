package empi.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class CommonUtils {
    private static Logger log = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 返回小写 的uuid 146982ce1d904767ad1d02ab0547951f
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
    }


    public static void main(String[] args) {
        String s = uuid();
        System.out.println(s);
    }


    /**
     * java实体类生成json的String串
     * @param object java实体类
     * @return 返回JSON的string字符串
     */
    public static String ObjectToJSONString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = null;
        try {
            jsonStr = objectMapper.writeValueAsString(object);
            return jsonStr;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return null;
        }
    }

}

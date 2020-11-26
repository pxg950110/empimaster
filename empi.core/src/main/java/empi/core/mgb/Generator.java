package empi.core.mgb;


import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 2020/7/27  22:25
 * </p>
 * <p>
 * @author pxg
 * @emil pxg950110@163.com
 * @Date 2020/7/27
 * @Version 1.0.0
 * @description </p>
 */
public class Generator  {
    public static void main(String[] args)  throws Exception{
        // 自动生成过程中的警告信息
        List<String> warnings=new ArrayList<>();
        //设置是否覆盖代码
        boolean overwrite=true;
        //读取配置文件
        InputStream is= Generator.class.getResourceAsStream("/generatorConfig.xml");
        ConfigurationParser configurationParser=new ConfigurationParser(warnings);
        Configuration configuration=configurationParser.parseConfiguration(is);
        is.close();
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        //创建 MBG
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, callback, warnings);
        //执行生成代码
        myBatisGenerator.generate(null);
        //输出警告信息
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}

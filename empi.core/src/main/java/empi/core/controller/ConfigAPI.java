package empi.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author chpxg
 * @see 配置相关内容
 * @version v1.0.0
 */
@RestController
@Api(description = "配置条件的接口")
@RequestMapping("/empi/config")
public class ConfigAPI {

    private static final Logger log=LoggerFactory.getLogger(ConfigAPI.class);
    //  纳排指标 查询
    @ApiOperation(value = "测试信息")
    @GetMapping("/test")
    public void test(){
        log.info("测试信息");
    }
    //  纳排指标 更新接口
    //  

    //  
}
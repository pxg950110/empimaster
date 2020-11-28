package empi.core.controller;

import empi.core.model.MatchFactor;
import io.swagger.annotations.ApiModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import empi.core.model.MatchProperty;
import empi.core.server.ConfigService;
import empi.core.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.PostConstruct;

/**
 * @author chpxg
 * @description 配置相关内容
 * @version v1.0.0
 */
@RestController
@Api(description = "配置条件的接口")
@RequestMapping("/empi/config")
public class ConfigAPI {

    @Autowired
    private ConfigService configService;
    private static final Logger log = LoggerFactory.getLogger(ConfigAPI.class);

    // 纳排指标 查询
    @ApiOperation(value = "测试信息")
    @GetMapping("/test")
    public void test() {
        log.info("测试信息");
    }


    @ApiOperation(value = "获取所有属性")
    @GetMapping("/property/all")
    public CommonResult<Object> getAllMatchProperty(){
        return configService.getAllMatchProperty();
    }
    @ApiOperation(value = "更新单个属性")
    @PostMapping("/property/update")
    public CommonResult<Object> opeationMatchProperty(@RequestBody() MatchProperty  matchProperty) {
        return configService.opeationMatchProperty(matchProperty);
    }



    /**
     *
     * @param matchFactor
     * @return
     */
    @ApiOperation(value = "匹配规则增加")
    @PostMapping("/factor/opertion")
    public CommonResult<Object> operationMatchFactor(@RequestBody MatchFactor matchFactor){
        return configService.operationMatchFactor(matchFactor);
    }
    // 现有所有的匹配规则
    @ApiOperation(value = "获取所有匹配规则")
    @GetMapping("/factor/all")
    public CommonResult<Object> getAllMatchFator(){
        return  configService.getAllMatchFator();
    }

}

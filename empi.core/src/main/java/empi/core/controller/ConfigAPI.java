package empi.core.controller;

import empi.core.model.MatchFactor;
import io.swagger.annotations.ApiModel;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import empi.core.model.MatchProperty;
import empi.core.server.ConfigService;
import empi.core.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.PostConstruct;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author chpxg
 * @version v1.0.0
 * @description 配置相关内容
 */
@RestController
@Api(description = "配置条件的接口")
@RequestMapping("/empi/config")
public class ConfigAPI {

    @Autowired
    private ConfigService configService;
    private static final Logger log = LoggerFactory.getLogger(ConfigAPI.class);

    @ApiOperation(value = "获取所有属性")
    @GetMapping("/property/all")
    public CommonResult<Object> getAllMatchProperty() {
        return configService.getAllMatchProperty();
    }

    @ApiOperation(value = "更新单个属性")
    @PostMapping("/property/update")
    public CommonResult<Object> opeationMatchProperty(@RequestBody() MatchProperty matchProperty) {
        return configService.operationMatchProperty(matchProperty);
    }

    /**
     * @param matchFactor
     * @return
     */
    @ApiOperation(value = "匹配规则增加")
    @PostMapping("/factor/opertion")
    public CommonResult<Object> operationMatchFactor(@RequestBody MatchFactor matchFactor) {
        return configService.operationMatchFactor(matchFactor);
    }

    // 现有所有的匹配规则
    @ApiOperation(value = "获取所有匹配规则")
    @GetMapping("/factor/all")
    public CommonResult<Object> getAllMatchFator() {
        return configService.getAllMatchFator();
    }

    /**
     * 获取纳排的属性
     * @return
     */
    @ApiOperation(value = "获取纳排属性")
    @GetMapping("/match/select/all")
    public CommonResult<Object> getAllSelectionProperty() {
        return configService.getAllSelectionProperty();
    }

    /**
     * 设置纳排属性
     * @param matchProperties
     * @return
     */
    @ApiOperation(value = "设置纳排属性")
    @PostMapping("/match/select/setting")
    public CommonResult<Object> operationSelectionProperty(@RequestBody List<MatchProperty> matchProperties) {
        return configService.operationSelectionProperty(matchProperties);
    }


    //设置其他属性

    @PostMapping("/system/{code}/update")
    @ApiOperation(value = "设置其他属性")
    public CommonResult<Object> setEmpiconfig(@PathVariable("code") String code, @RequestBody List<MatchProperty> matchProperties) {
        return configService.setEmpiconfig(code, matchProperties);
    }



    //获取其他属性

    @ApiOperation(value = "获取属性")
    @GetMapping("/system/info")
    public CommonResult<Object> getEmpiConfig(String code) {
        return configService.getEmpiConfig(code);
    }


    @ApiOperation(value = "生成JSON样例")
    @GetMapping("/system/create/json/sample")
    public CommonResult<Object> createAPIJsonSample(){
        return configService.createAPIJsonSample();
    }

}

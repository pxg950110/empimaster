package empi.core.server.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import empi.core.model.MatchFactor;
import empi.core.model.MatchProperty;
import empi.core.server.ConfigService;

/**
 * @author chpxg
 * @see 配置服务的实现类
 * @version v1.0.0
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Override
    public List<MatchProperty> getAllMatchProperty() {

        return null;
    }

    @Override
    public boolean opeationMatchProperty(MatchProperty matchProperty) {
        return false;
    }

    @Override
    public boolean operationMatchFactor(MatchFactor matchFactor) {

        return false;
    }

    @Override
    public boolean operationMatchFactor(List<MatchFactor> matchFactors) {

        return false;
    }

    @Override
    public List<MatchFactor> getAllMatchFator() {
        return null;
    }

    @Override
    public boolean operationSelectionProperty(List<MatchProperty> matchProperties) {

        return false;
    }

    @Override
    public List<MatchProperty> getAllSelectionProperty() {

        return null;
    }

    @Override
    public boolean setEmpiconfig(String code, String value) {

        return false;
    }

    @Override
    public Map<String, Object> getEmpiConfig() {
        return null;
    }

}

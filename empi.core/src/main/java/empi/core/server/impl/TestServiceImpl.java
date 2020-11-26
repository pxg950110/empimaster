package empi.core.server.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import empi.core.init.EmpiProtertyCache;
import empi.core.server.TestService;
import empi.core.utils.CommonUtils;
/**
 * server实现类
 */
@Service
public class TestServiceImpl implements TestService{

    private final static Logger log=LoggerFactory.getLogger(TestService.class);
    @Override
    public void test() {
        log.info(EmpiProtertyCache.getCacheMap().keySet().toString());
        log.info(CommonUtils.uuid());
    }
    
}

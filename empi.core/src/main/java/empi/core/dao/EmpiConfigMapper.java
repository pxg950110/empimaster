package empi.core.dao;

import empi.core.model.EmpiConfig;
import empi.core.model.EmpiConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmpiConfigMapper {
    long countByExample(EmpiConfigExample example);

    int deleteByExample(EmpiConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EmpiConfig record);

    int insertSelective(EmpiConfig record);

    List<EmpiConfig> selectByExample(EmpiConfigExample example);

    EmpiConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EmpiConfig record, @Param("example") EmpiConfigExample example);

    int updateByExample(@Param("record") EmpiConfig record, @Param("example") EmpiConfigExample example);

    int updateByPrimaryKeySelective(EmpiConfig record);

    int updateByPrimaryKey(EmpiConfig record);
}
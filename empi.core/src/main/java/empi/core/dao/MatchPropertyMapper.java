package empi.core.dao;

import empi.core.model.MatchProperty;
import empi.core.model.MatchPropertyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MatchPropertyMapper {
    long countByExample(MatchPropertyExample example);

    int deleteByExample(MatchPropertyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MatchProperty record);

    int insertSelective(MatchProperty record);

    List<MatchProperty> selectByExample(MatchPropertyExample example);

    MatchProperty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MatchProperty record, @Param("example") MatchPropertyExample example);

    int updateByExample(@Param("record") MatchProperty record, @Param("example") MatchPropertyExample example);

    int updateByPrimaryKeySelective(MatchProperty record);

    int updateByPrimaryKey(MatchProperty record);
}
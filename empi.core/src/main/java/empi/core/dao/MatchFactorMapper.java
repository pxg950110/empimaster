package empi.core.dao;

import empi.core.model.MatchFactor;
import empi.core.model.MatchFactorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MatchFactorMapper {
    long countByExample(MatchFactorExample example);

    int deleteByExample(MatchFactorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MatchFactor record);

    int insertSelective(MatchFactor record);

    List<MatchFactor> selectByExampleWithBLOBs(MatchFactorExample example);

    List<MatchFactor> selectByExample(MatchFactorExample example);

    MatchFactor selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MatchFactor record, @Param("example") MatchFactorExample example);

    int updateByExampleWithBLOBs(@Param("record") MatchFactor record, @Param("example") MatchFactorExample example);

    int updateByExample(@Param("record") MatchFactor record, @Param("example") MatchFactorExample example);

    int updateByPrimaryKeySelective(MatchFactor record);

    int updateByPrimaryKeyWithBLOBs(MatchFactor record);

    int updateByPrimaryKey(MatchFactor record);
}
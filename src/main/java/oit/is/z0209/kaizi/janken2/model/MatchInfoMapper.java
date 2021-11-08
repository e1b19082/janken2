package oit.is.z0209.kaizi.janken2.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {
  @Select("SELECT * FROM MATCHINFO WHERE ID = #{id}")
  MatchInfo selectById(int id);

  @Select("SELECT * FROM MATCHINFO WHERE ISACTIVE = #{b}")
  ArrayList<MatchInfo> selectAllByActive(boolean b);

  @Select("SELECT * FROM MATCHINFO WHERE ISACTIVE=true AND USER1 = #{user2} AND USER2 = #{user1}")
  MatchInfo selectByMatchInfo(MatchInfo m);

  @Insert("INSERT INTO MATCHINFO (user1,user2,user1Hand,ISACTIVE) VALUES (#{user1},#{user2},#{user1Hand},#{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatchinfo(MatchInfo matchinfo);

  @Update("UPDATE MATCHINFO SET ISACTIVE = false WHERE ID = #{id}")
  void updateById(MatchInfo m);
}

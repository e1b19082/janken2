package oit.is.z0209.kaizi.janken2.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchMapper {
  @Select("SELECT * FROM MATCHES")
  ArrayList<Match> selectAllMatches();

  @Select("SELECT * FROM MATCHES WHERE ISACTIVE = #{b}")
  Match selectByBoolean(boolean b);

  @Insert("INSERT INTO MATCHES (USER1,USER2,USER1Hand,USER2Hand,ISACTIVE) VALUES (#{user1},#{user2},#{user1Hand},#{user2Hand},#{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatch(Match match);

  @Update("UPDATE MATCHES SET ISACTIVE = false WHERE ID = #{id}")
  void updateById(Match m);
}

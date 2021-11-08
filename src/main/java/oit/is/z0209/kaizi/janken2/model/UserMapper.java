package oit.is.z0209.kaizi.janken2.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Select("SELECT ID,NAME FROM USERS")
  ArrayList<User> selectAllUsers();

  @Select("SELECT * FROM USERS WHERE ID = #{id}")
  User selectById(int id);

  @Select("SELECT * FROM USERS WHERE NAME = #{name}")
  User selectByName(String user);

  @Insert("INSERT INTO USERS (NAME) VALUES (#{name});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertUser(User users);

}

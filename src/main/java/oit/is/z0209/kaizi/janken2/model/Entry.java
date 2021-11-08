package oit.is.z0209.kaizi.janken2.model;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class Entry {
  ArrayList<String> users = new ArrayList<>();
  String userName = "";

  public void addUser(String name) {
    // 同名のユーザが居たら何もせずにreturn
    for (String s : this.users) {
      if (s.equals(name)) {
        return;
      }
    }
    // 同名のユーザが居なかった場合はusersにnameを追加する
    this.users.add(name);
    this.userName = name;
  }

  // 以降はフィールドのgetter/setter
  // これらがないとThymeleafで値を取得できない
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public ArrayList<String> getUsers() {
    return users;
  }

  public void setUsers(ArrayList<String> users) {
    this.users = users;
  }

}

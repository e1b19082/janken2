package oit.is.z0209.kaizi.janken2.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z0209.kaizi.janken2.model.Janken;
import oit.is.z0209.kaizi.janken2.model.Entry;
import oit.is.z0209.kaizi.janken2.model.Match;
import oit.is.z0209.kaizi.janken2.model.MatchInfo;
import oit.is.z0209.kaizi.janken2.model.MatchInfoMapper;
import oit.is.z0209.kaizi.janken2.model.MatchMapper;
import oit.is.z0209.kaizi.janken2.model.User;
import oit.is.z0209.kaizi.janken2.model.UserMapper;
import oit.is.z0209.kaizi.janken2.service.AsyncKekka;

@Controller
public class Lec02Controller {

  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchInfoMapper;

  @Autowired
  AsyncKekka asyncKekka;

  @PostMapping("/lec02")
  public String lec02(@RequestParam String name, ModelMap model) {
    model.addAttribute("name", name);

    return "lec02.html";
  }

  @GetMapping("/lec02")
  @Transactional
  public String lec02(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    if (userMapper.selectByName(loginUser) == null) {
      User user = new User();
      user.setName(loginUser);
      userMapper.insertUser(user);
    }

    ArrayList<User> users = userMapper.selectAllUsers();
    model.addAttribute("users", users);

    ArrayList<Match> matches = matchMapper.selectAllMatches();
    model.addAttribute("matches", matches);

    // insert後にすべてのアクティブな試合を取得する
    ArrayList<MatchInfo> activeGame = matchInfoMapper.selectAllByActive(true);
    model.addAttribute("activeGame", activeGame);

    return "lec02.html";

  }

  @GetMapping("/match")
  public String match(@RequestParam Integer id, Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    User user1 = userMapper.selectByName(loginUser);
    User user2 = userMapper.selectById(id);
    model.addAttribute("user1", user1);
    model.addAttribute("user2", user2);
    model.addAttribute("id", id);

    return "match.html";
  }

  /*
   * Transactionalはメソッドでトランザクション処理を実施したい場合に付与する*このメソッドが開始するとトランザクションが開始され，
   * メソッドが正常に終了するとDBへのアクセスが確定する（Runtime*errorなどで止まった場合はロールバックが行われる）
   */
  @GetMapping("/matchjanken")
  @Transactional
  public String matchjanken(@RequestParam String hand, @RequestParam Integer id, ModelMap model, Principal prin) {
    String loginUser = prin.getName(); // ログインユーザ情報
    User user1 = userMapper.selectByName(loginUser);
    MatchInfo activeGame = new MatchInfo();
    activeGame.setUser1(user1.getId());
    activeGame.setUser2(id);
    activeGame.setUser1Hand(hand);// 自身が選んだ手
    activeGame.setIsActive(true);
    if (matchInfoMapper.selectByMatchInfo(activeGame) == null) {
      matchInfoMapper.insertMatchinfo(activeGame);
    } else {
      MatchInfo activeGame2 = matchInfoMapper.selectByMatchInfo(activeGame);
      Match match = new Match();
      match.setUser1(activeGame2.getUser1());
      match.setUser2(activeGame2.getUser2());
      match.setUser1Hand(activeGame2.getUser1Hand());
      match.setUser2Hand(hand);
      match.setIsActive(true);
      Match match1 = asyncKekka.syncMatch(match);
      matchInfoMapper.updateById(activeGame2);
      model.addAttribute("match", match1);
    }

    return "wait.html";
  }

  @GetMapping("/step1")
  public SseEmitter sseEmitter() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.asyncKekka.asyncShowMatch(sseEmitter);

    return sseEmitter;
  }
}

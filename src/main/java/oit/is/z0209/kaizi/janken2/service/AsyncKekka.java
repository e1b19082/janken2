package oit.is.z0209.kaizi.janken2.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z0209.kaizi.janken2.model.Match;
import oit.is.z0209.kaizi.janken2.model.MatchMapper;

@Service
public class AsyncKekka {
  boolean dbUpdated = false;

  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  MatchMapper matchMapper;

  public Match syncShowMatch() {
    return matchMapper.selectByBoolean(true);
  }

  @Transactional
  public Match syncMatch(Match m) {
    matchMapper.insertMatch(m);
    this.dbUpdated = true;
    return m;
  }

  @Async
  public void asyncShowMatch(SseEmitter emitter) {
    dbUpdated = true;
    try {
      while (true) {
        if (false == dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }
        Match match = this.syncShowMatch();
        emitter.send(match);
        TimeUnit.SECONDS.sleep(50);
        dbUpdated = false;
        matchMapper.updateById(match);
      }
    } catch (Exception e) {
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
  }
}

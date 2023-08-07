package com.rks.orderservice.logging;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Slf4j
@WebListener
public class SessionCounter implements HttpSessionListener {

    private static int count;
    public SessionCounter() {
        count = 0;
    }
    public static int getCount() {
        return count;
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
      log.info("Session created by id: " + event.getSession().getId());
      synchronized (this) {
          count++;
      }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        log.info("Session destroyed by Id : " + event.getSession().getId());
        synchronized (this) {
            count--;
        }
    }
}

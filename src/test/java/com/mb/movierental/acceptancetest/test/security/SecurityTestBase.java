package com.mb.movierental.acceptancetest.test.security;


public class SecurityTestBase {

  public static String getURL(int port) {
    return  "http://localhost:" + port + "/api/customer";
  }

}

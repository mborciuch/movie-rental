package com.mb.movierental.acceptancetest.test.security;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("security-in-memory")
public class InMemorySecurityTest extends SecurityTestBase {

  protected  static CloseableHttpClient httpClient;

  protected  static ObjectMapper objectMapper;

  @LocalServerPort
  int port;

  @BeforeAll
  public static void setUpAll() {

   httpClient = HttpClientBuilder.create().build();
   objectMapper = new ObjectMapper();
   objectMapper.registerModules(new JavaTimeModule(), new Jdk8Module());
   objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @AfterEach
  public  void tearDown() {

  }

  @Test
  void inMemoryLogin_CorrectCredentials_200 () throws IOException {
    HttpGet httpGet = this.prepareRequest("user", "password");


    CloseableHttpResponse response = httpClient.execute(httpGet);

    assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
  }

  @Test
  void inMemoryLogin_InCorrectCredentials_401 () throws IOException {
    HttpGet httpGet = this.prepareRequest("admin", "wrongpassword");

    CloseableHttpResponse response = httpClient.execute(httpGet);

    assertThat(response.getStatusLine().getStatusCode()).isEqualTo(401);
  }


  private HttpGet prepareRequest(String login, String password) {
    StringBuilder stringBuilder = new StringBuilder();
    String credentials = stringBuilder.append(login).append(":").append(password).toString();
    HttpGet request = new HttpGet(SecurityTestBase.getURL(port));
    byte[] plainCredentialsBytes = credentials.getBytes();
    byte[] base64CredentialBytes = Base64.encodeBase64(plainCredentialsBytes);
    stringBuilder = new StringBuilder();
    stringBuilder.append("Basic").append(" ").append(new String(base64CredentialBytes));
    String base64Creds = stringBuilder.toString();
    request.setHeader(new BasicHeader("Authorization", base64Creds));
    return request;
  }
}

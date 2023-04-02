package com.mb.movierental.acceptancetest.test.security;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

public class StepDefsSecurityTest extends SecurityTestBase {

  protected static CloseableHttpClient httpClient;

  protected static ObjectMapper objectMapper;

  @BeforeAll
  public static void setUpAll() {
    httpClient = HttpClientBuilder.create().build();
    objectMapper = new ObjectMapper();
    objectMapper.registerModules(new JavaTimeModule(), new Jdk8Module());
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @After
  public static void tearDown() {
    StepDefsSecurityTest.credentials = null;
    request = null;
    response = null;
  }

  static String credentials;
  static HttpPost request;
  static HttpResponse response;

  @Given("correct {string} and {string}")
  public void correctLoginAndPassword(String login, String password) throws IOException {
    this.prepareRequest(login, password);
  }

  @When("user sends request with login and password")
  public void loginAndPasswordAreAddedToRequest() throws IOException {
    response = httpClient.execute(request);
  }

  @Then("user receives token")
  public void clientReceivesToken() throws IOException {
    assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
  }

  @Given("incorrect {string} and {string}")
  public void incorrectLoginAndPassword(String login, String password) throws IOException {
    this.prepareRequest(login, password);
  }

  @Then("request has 401 status")
  public void requestHas401Status() throws IOException {
    assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
  }

  private void prepareRequest(String login, String password) {
    StringBuilder stringBuilder = new StringBuilder();
    credentials = stringBuilder.append(login).append(":").append(password).toString();
    request = new HttpPost(AUTHENTICATION_URL);
    byte[] plainCredentialsBytes = this.credentials.getBytes();
    byte[] base64CredentialBytes = Base64.encodeBase64(plainCredentialsBytes);
    String base64Creds = new String(base64CredentialBytes);
    request.setHeader(new BasicHeader("Authenticate", base64Creds));
  }
}

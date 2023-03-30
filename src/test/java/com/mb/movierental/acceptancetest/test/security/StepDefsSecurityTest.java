package com.mb.movierental.acceptancetest.test.security;

import com.fasterxml.jackson.databind.DeserializationFeature;import com.fasterxml.jackson.databind.ObjectMapper;import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;import org.apache.http.impl.client.HttpClientBuilder;import org.apache.http.message.BasicHeader;
import org.junit.jupiter.api.BeforeEach;
import java.io.IOException;import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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


  String credentials;
  HttpPost request;
  HttpResponse response;

  @Given("correct {string} and {string}")
  public void correctLoginAndPassword(String login, String password) throws IOException {
      StringBuilder stringBuilder = new StringBuilder();
    this.credentials =  stringBuilder.append(login).append(":").append(password).toString();
    request = new HttpPost(AUTHENTICATION_URL);
    byte[] plainCredentialsBytes = this.credentials.getBytes();
    byte[] base64CredentialBytes = Base64.encodeBase64(plainCredentialsBytes);
    String base64Creds = new String(base64CredentialBytes);
    request.setHeader(new BasicHeader("Authenticate", base64Creds));
  }

  @When("user sends request with login and password")
  public void loginAndPasswordAreAddedToRequest() throws IOException {
    this.response = httpClient.execute(this.request);
  }

  @Then("user receives token")
  public void clientReceivesToken() throws IOException {
    assertThat(this.response.getStatusLine().getStatusCode()).isEqualTo(200);
  }
}

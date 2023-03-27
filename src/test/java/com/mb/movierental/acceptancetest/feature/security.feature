Feature: authentication with login and password
  #  Authentication using login and password

  Scenario: # Successfully authenticated
    Given correct login and password
    When  user sends request with login and password
    Then  user receives token

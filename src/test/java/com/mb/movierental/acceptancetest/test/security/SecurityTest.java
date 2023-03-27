package com.mb.movierental.acceptancetest.test.security;

import io.cucumber.junit.Cucumber;import io.cucumber.junit.CucumberOptions;import org.junit.runner.RunWith;@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/com/mb/movierental/acceptancetest/feature",
        glue = {"com/mb/movierental/acceptancetest"},
        plugin = {"pretty"})
public class SecurityTest {

}

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    monochrome = true,
    features = "src/test/resources/features/",

    glue = {"com.cloud.accelarator.stepdefs"},
    plugin = {
      "pretty",
      "html:target/cucumber-reports",
      "json:target/cucumber-reports/cucumber.json",
    },
    tags = {"@speechToText"})
public class CucumberTestRunner {
}



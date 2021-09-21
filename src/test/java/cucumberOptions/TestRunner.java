package cucumberOptions;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;




@RunWith(cucumber.api.junit.Cucumber.class)
@CucumberOptions( 
		features="src/test/java/features/HealthCheck.feature",
		glue="stepDefinations")

public class TestRunner {

	
	
}

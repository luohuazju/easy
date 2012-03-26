package com.easybddweb.core.story;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.web.selenium.WebDriverHtmlOutput.WEB_DRIVER_HTML;

import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.web.selenium.ContextView;
import org.jbehave.web.selenium.FirefoxWebDriverProvider;
import org.jbehave.web.selenium.LocalFrameContextView;
import org.jbehave.web.selenium.SeleniumConfiguration;
import org.jbehave.web.selenium.SeleniumContext;
import org.jbehave.web.selenium.SeleniumContextOutput;
import org.jbehave.web.selenium.SeleniumStepMonitor;
import org.jbehave.web.selenium.WebDriverProvider;

public abstract class WebsiteStory extends JUnitStories {

	CrossReference crossReference = new CrossReference().withJsonOnly()
			.withOutputAfterEachStory(true)
			.excludingStoriesWithNoExecutedScenarios(true);
	ContextView contextView = new LocalFrameContextView().sized(500, 100);
	SeleniumContext seleniumContext = new SeleniumContext();
	SeleniumStepMonitor stepMonitor = new SeleniumStepMonitor(contextView,
			seleniumContext, crossReference.getStepMonitor());
	Format[] formats = new Format[] {
			new SeleniumContextOutput(seleniumContext), CONSOLE,
			WEB_DRIVER_HTML };
	StoryReporterBuilder reporterBuilder = new StoryReporterBuilder()
			.withCodeLocation(codeLocationFromClass(this.getClass()))
			.withFailureTrace(true).withFailureTraceCompression(true)
			.withDefaultFormats().withFormats(formats)
			.withCrossReference(crossReference);

	public Configuration configuration() {
		return new SeleniumConfiguration()
				.useSeleniumContext(seleniumContext)
				.useFailureStrategy(new FailingUponPendingStep())
				.useStoryControls(
						new StoryControls().doResetStateBeforeScenario(false))
				.useStepMonitor(stepMonitor)
				.useStoryLoader(new LoadFromClasspath(this.getClass()))
				.useStoryReporterBuilder(reporterBuilder);
	}

	public List<CandidateSteps> candidateSteps() {
		return new InstanceStepsFactory(configuration(), getStepsInstances())
				.createCandidateSteps();
	}

	protected abstract List<Object> getStepsInstances();

	protected WebDriverProvider getFirefoxWebDriverProvider() {
		return new FirefoxWebDriverProvider();
	}

}

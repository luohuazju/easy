package com.easybddweb.vendors.petco;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStoryMaps;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.StoryReporterBuilder;

public class PetcoStoryMaps extends JUnitStoryMaps {

	public PetcoStoryMaps() {
		configuredEmbedder().useMetaFilters(metaFilters());
	}

	public Configuration configuration() {
		return new MostUsefulConfiguration()
				.useStoryParser(
						new RegexStoryParser(new ExamplesTableFactory(
								new LoadFromClasspath(this.getClass()))))
				.useStoryReporterBuilder(
						new StoryReporterBuilder().withCodeLocation(CodeLocations
								.codeLocationFromClass(this.getClass())));
	}

	protected List<String> metaFilters() {
		return asList(); // will be specified by values in the pom.xml file when
							// run from Maven
	}

	protected List<String> storyPaths() {
		return new StoryFinder().findPaths(
				codeLocationFromClass(this.getClass()),
				"**/petco/*.story", "");
	}

}

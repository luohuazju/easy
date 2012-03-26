package com.easybddweb.vendors.baidu;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.io.StoryFinder;

import com.easybddweb.core.story.WebsiteStory;

public class BaiduStories extends WebsiteStory {

	protected List<String> storyPaths() {
		List<String> list = new StoryFinder().findPaths(
				codeLocationFromClass(this.getClass()).getFile(),
				asList("**/baidu/*.story"), null);
		return list;
	}

	@Override
	protected List<Object> getStepsInstances() {
		BaiduSteps steps = new BaiduSteps(super.getFirefoxWebDriverProvider());
		List<Object> list = new ArrayList<Object>();
		list.add(steps);
		return list;
	}

}

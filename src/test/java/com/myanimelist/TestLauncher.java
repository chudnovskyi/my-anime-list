package com.myanimelist;

import java.io.PrintWriter;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

public class TestLauncher {

	public static void main(String[] args) {
		Launcher launcher = LauncherFactory.create();

		SummaryGeneratingListener summaryGeneratingListener = new SummaryGeneratingListener();
		launcher.registerTestExecutionListeners(summaryGeneratingListener);

		LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
				.request()
				.selectors(DiscoverySelectors.selectPackage("com.myanimelist"))
				.build();

		launcher.execute(request);

		try (PrintWriter printWriter = new PrintWriter(System.out)) {
			summaryGeneratingListener.getSummary().printTo(printWriter);
		}
	}
}

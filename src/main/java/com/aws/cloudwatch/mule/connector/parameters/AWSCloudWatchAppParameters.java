package com.aws.cloudwatch.mule.connector.parameters;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

public class AWSCloudWatchAppParameters {
	@Parameter
	@DisplayName(value = "Application name")
	@Optional(defaultValue = "#[app.name]")
	@Placement(order = 1)
	private String AppName;

	@Parameter
	@DisplayName(value = "Application Version")
	@Optional(defaultValue = "v1")
	@Example(value = "1.0.0")
	@Placement(order = 2)
	private String AppVersion;

	@Parameter
	@DisplayName(value = "Environment")
	@Example(value = "dev")
	@Optional(defaultValue = "dev")
	@Placement(order = 3)
	private String AppEnv;

	public String getAppName() {
		return AppName;
	}

	public String getAppVersion() {
		return AppVersion;
	}

	public String getAppEnv() {
		return AppEnv;
	}

}

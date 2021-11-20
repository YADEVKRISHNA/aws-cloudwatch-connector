package com.aws.cloudwatch.mule.connector.parameters;

import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;

import com.aws.cloudwatch.mule.connector.valueProviders.AWSCloudWatchAppLogGroupValueProvider;

public class AWSCloudwatchCreateLogStreamParamters {

	@Parameter
	@DisplayName("Log Group Name")
	@OfValues(AWSCloudWatchAppLogGroupValueProvider.class)
	@Placement(order = 1)
	private String LogGroupName;

	@Parameter
	@DisplayName("Log Stream Name")
	@Placement(order = 2)
	private String LogStreamName;

	public String getLogGroupName() {
		return LogGroupName;
	}

	public String getLogStreamName() {
		return LogStreamName;
	}

}

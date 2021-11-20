package com.aws.cloudwatch.mule.connector.parameters;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;

import com.aws.cloudwatch.mule.connector.valueProviders.AWSCloudWatchAppLogGroupValueProvider;
import com.aws.cloudwatch.mule.connector.valueProviders.AWSCloudWatchLogStreamvalueProvider;

public class AwsCloudWatchFilterLogParameters {
	@Parameter
	@DisplayName("Log Group Name")
	@OfValues(AWSCloudWatchAppLogGroupValueProvider.class)
	@Placement(order = 1)
	private String LogGroupName;

	@Parameter
	@DisplayName("Log Stream Name")
	@OfValues(AWSCloudWatchLogStreamvalueProvider.class)
	@Placement(order = 2)
	private String LogStreamName;

	@Parameter
	@DisplayName("Start Time")
	@Placement(order = 3)
	@Expression(ExpressionSupport.SUPPORTED)
	@Example(value = "This should be in epocs (1632893364946)")
	private long startTime;

	@Parameter
	@DisplayName("End Time")
	@Placement(order = 4)
	@Expression(ExpressionSupport.SUPPORTED)
	@Example(value = "This should be in epocs (1632893364946)")
	private long endTime;

	@Parameter
	@DisplayName("Limit")
	@Placement(order = 5)
	@Optional
	@Expression(ExpressionSupport.SUPPORTED)
	@Example(value = "This should be in epocs (1632893364946)")
	private int Limit = 0;

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public int getLimit() {
		return Limit;
	}

	public String getLogGroupName() {
		return LogGroupName;
	}

	public String getLogStreamName() {
		return LogStreamName;
	}

}

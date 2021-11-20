package com.aws.cloudwatch.mule.connector.parameters;

import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

public class AWSCloudWatchCreateLogGroupParameter {
	@Parameter
	@DisplayName(value = "Log Group Name")
	@Placement(order = 1)
	private String groupName;

	public String getGroupName() {
		return groupName;
	}
}

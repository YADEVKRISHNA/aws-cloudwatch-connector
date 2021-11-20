package com.aws.cloudwatch.mule.connector.Operations;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.exception.ModuleException;

import com.amazonaws.services.cloudwatch.model.LimitExceededException;
import com.amazonaws.services.cloudwatchevents.model.ResourceAlreadyExistsException;
import com.amazonaws.services.logs.AWSLogsClient;
import com.amazonaws.services.logs.model.CreateLogGroupRequest;
import com.amazonaws.services.logs.model.CreateLogGroupResult;
import com.amazonaws.services.logs.model.InvalidParameterException;
import com.amazonaws.services.logs.model.OperationAbortedException;
import com.amazonaws.services.logs.model.ServiceUnavailableException;
import com.aws.cloudwatch.mule.connector.AWSCloudWatchConnection;
import com.aws.cloudwatch.mule.connector.errors.AWSCloudWatchErrorTypeProvider;
import com.aws.cloudwatch.mule.connector.errors.AWSCloudWatchErrorTypes;
import com.aws.cloudwatch.mule.connector.parameters.AWSCloudWatchCreateLogGroupParameter;

public class AWSCloudWatchCreateLogGroupOperation {

	@MediaType(value = ANY, strict = false)
	@DisplayName("Create Log group")
	@Throws(AWSCloudWatchErrorTypeProvider.class)
	public CreateLogGroupResult CreateLogGroup(@Connection AWSCloudWatchConnection connection,
			@ParameterGroup(name = "Log Group Name") AWSCloudWatchCreateLogGroupParameter logGroupName) {
		CreateLogGroupResult result = null;
		try {
			AWSLogsClient logsClient = (AWSLogsClient) connection.getlogsClient();
			CreateLogGroupRequest createLogGroupRequest = new CreateLogGroupRequest(logGroupName.getGroupName());
			result = logsClient.createLogGroup(createLogGroupRequest);
		} catch (InvalidParameterException e) {
			throw new ModuleException(AWSCloudWatchErrorTypes.INVALID_PARAMETER, e.getCause());
		} catch (ResourceAlreadyExistsException e) {
			throw new ModuleException(AWSCloudWatchErrorTypes.RESOURCE_ALREADY_EXISTS, e.getCause());
		} catch (LimitExceededException e) {
			throw new ModuleException(AWSCloudWatchErrorTypes.LIMIT_EXCEEDED, e.getCause());
		} catch (OperationAbortedException e) {
			throw new ModuleException(AWSCloudWatchErrorTypes.OPERATION_OBORDED, e.getCause());
		} catch (ServiceUnavailableException e) {
			throw new ModuleException(AWSCloudWatchErrorTypes.SERVICE_UNAVAILABLE, e.getCause());
		}
		return result;
	}

}

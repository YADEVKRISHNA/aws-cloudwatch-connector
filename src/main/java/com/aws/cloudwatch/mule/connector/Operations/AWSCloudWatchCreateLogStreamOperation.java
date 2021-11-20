package com.aws.cloudwatch.mule.connector.Operations;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.cloudwatch.model.ResourceNotFoundException;
import com.amazonaws.services.cloudwatchevents.model.ResourceAlreadyExistsException;
import com.amazonaws.services.logs.AWSLogsClient;
import com.amazonaws.services.logs.model.CreateLogStreamRequest;
import com.amazonaws.services.logs.model.CreateLogStreamResult;
import com.amazonaws.services.logs.model.InvalidParameterException;
import com.amazonaws.services.logs.model.ServiceUnavailableException;
import com.aws.cloudwatch.mule.connector.AWSCloudWatchConnection;
import com.aws.cloudwatch.mule.connector.errors.AWSCloudWatchErrorTypeProvider;
import com.aws.cloudwatch.mule.connector.errors.AWSCloudWatchErrorTypes;
import com.aws.cloudwatch.mule.connector.parameters.AWSCloudwatchCreateLogStreamParamters;

public class AWSCloudWatchCreateLogStreamOperation {
	private static final Logger LOGGER = LoggerFactory.getLogger(AWSCloudWatchCreateLogStreamOperation.class);

	@MediaType(value = ANY, strict = false)
	@DisplayName("Create Log Stream")
	@Throws(AWSCloudWatchErrorTypeProvider.class)
	public CreateLogStreamResult CreateLogStream(@Connection AWSCloudWatchConnection connection,
			@ParameterGroup(name = "Log") AWSCloudwatchCreateLogStreamParamters p) {
		AWSLogsClient logsClient = (AWSLogsClient) connection.getlogsClient();
		try {
			CreateLogStreamRequest createLogGroupRequest = new CreateLogStreamRequest(p.getLogGroupName().toString(),
					p.getLogStreamName().toString());
			CreateLogStreamResult s = logsClient.createLogStream(createLogGroupRequest);
			return s;
		} catch (InvalidParameterException e) {
			LOGGER.error(AWSCloudWatchErrorTypes.INVALID_PARAMETER.toString());
			throw new ModuleException(AWSCloudWatchErrorTypes.INVALID_PARAMETER, e);
		} catch (ResourceAlreadyExistsException e) {
			LOGGER.error(AWSCloudWatchErrorTypes.RESOURCE_ALREADY_EXISTS.toString());
			throw new ModuleException(AWSCloudWatchErrorTypes.RESOURCE_ALREADY_EXISTS, e);
		} catch (ResourceNotFoundException e) {
			LOGGER.error(AWSCloudWatchErrorTypes.RESOURCE_NOT_FOUND.toString());
			throw new ModuleException(AWSCloudWatchErrorTypes.RESOURCE_NOT_FOUND, e);
		} catch (ServiceUnavailableException e) {
			LOGGER.error(AWSCloudWatchErrorTypes.SERVICE_UNAVAILABLE.toString());
			throw new ModuleException(AWSCloudWatchErrorTypes.SERVICE_UNAVAILABLE, e);
		}

	}
}

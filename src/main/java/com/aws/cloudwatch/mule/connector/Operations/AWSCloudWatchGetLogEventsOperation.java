package com.aws.cloudwatch.mule.connector.Operations;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.metadata.fixed.OutputJsonType;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.values.OfValues;
import org.mule.runtime.extension.api.exception.ModuleException;

import com.amazonaws.services.cloudwatch.model.ResourceNotFoundException;
import com.amazonaws.services.logs.AWSLogsClient;
import com.amazonaws.services.logs.model.GetLogEventsRequest;
import com.amazonaws.services.logs.model.InvalidParameterException;
import com.amazonaws.services.logs.model.ServiceUnavailableException;
import com.aws.cloudwatch.mule.connector.AWSCloudWatchConnection;
import com.aws.cloudwatch.mule.connector.errors.AWSCloudWatchErrorTypeProvider;
import com.aws.cloudwatch.mule.connector.errors.AWSCloudWatchErrorTypes;
import com.aws.cloudwatch.mule.connector.valueProviders.AWSCloudWatchAppLogGroupValueProvider;
import com.aws.cloudwatch.mule.connector.valueProviders.AWSCloudWatchLogStreamvalueProvider;

public class AWSCloudWatchGetLogEventsOperation {

	@MediaType(value = ANY, strict = false)
	@DisplayName("Get Log Events")
	@OutputJsonType(schema = "meta-data.json")
	@Throws(AWSCloudWatchErrorTypeProvider.class)
	public List<HashMap<String, Object>> getLogEvents(@Connection AWSCloudWatchConnection connection,
			@OfValues(AWSCloudWatchAppLogGroupValueProvider.class) String LogGroupName,
			@OfValues(AWSCloudWatchLogStreamvalueProvider.class) String LogStreamName) {

		AWSLogsClient logsClient = (AWSLogsClient) connection.getlogsClient();

		try {
			GetLogEventsRequest getLogEventsRequest = new GetLogEventsRequest().withLogGroupName(LogGroupName)
					.withLogStreamName(LogStreamName).withStartFromHead(true);

			String s = logsClient.getLogEvents(getLogEventsRequest).getEvents().toString();
			JSONArray jsonArray = new JSONArray(s);
			List<HashMap<String, Object>> output = jsonArray.toList().stream().map(m -> ((HashMap<String, Object>) m))
					.collect(Collectors.toList());
			return output;
		} catch (InvalidParameterException e) {
			throw new ModuleException(AWSCloudWatchErrorTypes.INVALID_PARAMETER, e.getCause());
		} catch (ResourceNotFoundException e) {
			throw new ModuleException(AWSCloudWatchErrorTypes.RESOURCE_NOT_FOUND, e.getCause());
		} catch (ServiceUnavailableException e) {
			throw new ModuleException(AWSCloudWatchErrorTypes.SERVICE_UNAVAILABLE, e.getCause());
		}

	}

}

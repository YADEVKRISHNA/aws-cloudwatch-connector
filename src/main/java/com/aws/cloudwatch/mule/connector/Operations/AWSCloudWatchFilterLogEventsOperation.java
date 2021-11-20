package com.aws.cloudwatch.mule.connector.Operations;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.metadata.fixed.OutputJsonType;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.exception.ModuleException;

import com.amazonaws.services.cloudwatch.model.ResourceNotFoundException;
import com.amazonaws.services.logs.AWSLogsClient;
import com.amazonaws.services.logs.model.FilterLogEventsRequest;
import com.amazonaws.services.logs.model.InvalidParameterException;
import com.amazonaws.services.logs.model.ServiceUnavailableException;
import com.aws.cloudwatch.mule.connector.AWSCloudWatchConnection;
import com.aws.cloudwatch.mule.connector.AWSCloudWatchGeneralConfig;
import com.aws.cloudwatch.mule.connector.errors.AWSCloudWatchErrorTypeProvider;
import com.aws.cloudwatch.mule.connector.errors.AWSCloudWatchErrorTypes;
import com.aws.cloudwatch.mule.connector.parameters.AwsCloudWatchFilterLogParameters;

public class AWSCloudWatchFilterLogEventsOperation {

	@MediaType(value = ANY, strict = false)
	@DisplayName("Filter Log Events")
	@OutputJsonType(schema = "meta-data.json")
	@Throws(AWSCloudWatchErrorTypeProvider.class)
	public List<HashMap<String, Object>> FilterLogEvents(@Config AWSCloudWatchGeneralConfig configuration,
			@Connection AWSCloudWatchConnection connection,
			@ParameterGroup(name = "Filter Parameters") AwsCloudWatchFilterLogParameters p) {
		String logGroupName = p.getLogGroupName(), logStreamName = p.getLogStreamName();
		AWSLogsClient logsClient = (AWSLogsClient) connection.getlogsClient();
		FilterLogEventsRequest filterLogEventsRequest = null;
		List<HashMap<String, Object>> output = null;
		try {
			if (p.getLimit() != 0) {
				filterLogEventsRequest = new FilterLogEventsRequest().withStartTime(p.getStartTime())
						.withEndTime(p.getEndTime()).withLogGroupName(logGroupName)
						.withLogStreamNamePrefix(logStreamName).withLimit(p.getLimit());
			} else {
				filterLogEventsRequest = new FilterLogEventsRequest().withStartTime(p.getStartTime())
						.withEndTime(p.getEndTime()).withLogGroupName(logGroupName)
						.withLogStreamNamePrefix(logStreamName);
			}
			String s = logsClient.filterLogEvents(filterLogEventsRequest).getEvents().toString();
			JSONArray jsonArray = new JSONArray(s);
			output = jsonArray.toList().stream().map(m -> ((HashMap<String, Object>) m)).collect(Collectors.toList());

		} catch (InvalidParameterException e) {
			throw new ModuleException(AWSCloudWatchErrorTypes.INVALID_PARAMETER, e.getCause());
		} catch (ResourceNotFoundException e) {
			throw new ModuleException(AWSCloudWatchErrorTypes.RESOURCE_NOT_FOUND, e.getCause());
		} catch (ServiceUnavailableException e) {
			throw new ModuleException(AWSCloudWatchErrorTypes.SERVICE_UNAVAILABLE, e.getCause());
		}
		return output;
	}

}

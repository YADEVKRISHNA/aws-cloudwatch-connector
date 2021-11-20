package com.aws.cloudwatch.mule.connector.Operations;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.parameter.CorrelationInfo;
import org.mule.runtime.extension.api.runtime.process.CompletionCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.logs.AWSLogsClient;
import com.amazonaws.services.logs.model.DescribeLogStreamsRequest;
import com.amazonaws.services.logs.model.InputLogEvent;
import com.amazonaws.services.logs.model.LogStream;
import com.amazonaws.services.logs.model.PutLogEventsRequest;
import com.amazonaws.services.logs.model.PutLogEventsResult;
import com.aws.cloudwatch.mule.connector.AWSCloudWatchConfiguration;
import com.aws.cloudwatch.mule.connector.AWSCloudWatchConnection;
import com.aws.cloudwatch.mule.connector.parameters.AWSCloudWatchParameter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AWSCloudWatchPutLogEventsOperation {
	private static final Logger LOGGER = LoggerFactory.getLogger(AWSCloudWatchPutLogEventsOperation.class);

	@MediaType(value = ANY, strict = false)
	@DisplayName(value = "Put Log Events")
	public void PutLogEvent(@Config AWSCloudWatchConfiguration configuration,
			@Connection AWSCloudWatchConnection connection, @ParameterGroup(name = "Log") AWSCloudWatchParameter p,
			CorrelationInfo correlationInfo, CompletionCallback<Void, Void> callback) {
		String logGroupName = p.getLogGroupName(), logStreamName = p.getLogStreamName();
		Map<String, Object> LogMessageMap = new HashMap<String, Object>();
		LogMessageMap.put("App_Name", configuration.getAppName());
		LogMessageMap.put("App_Version", configuration.getAppVersion());
		LogMessageMap.put("Environment", configuration.getAppEnv());
		LogMessageMap.put("LEVEL", p.getLevel().toString());
		LogMessageMap.put("TRACE_POINT", p.getTracePoint().toString());
		LogMessageMap.put("Message", p.getMessage());
		LogMessageMap.put("CorrelationId", correlationInfo.getCorrelationId());
		LogMessageMap.put("Payload", p.getLogMessage());
		Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
		String JSONMessageObject = prettyGson.toJson(LogMessageMap);
		Calendar calendar = Calendar.getInstance();
		try {
			AWSLogsClient logsClient = (AWSLogsClient) connection.getlogsClient();
			PutLogEventsRequest putLogEventsRequest = new PutLogEventsRequest();
			putLogEventsRequest.setLogGroupName(logGroupName);
			putLogEventsRequest.setLogStreamName(logStreamName);
			String token = null;
			DescribeLogStreamsRequest logStreamsRequest = new DescribeLogStreamsRequest()
					.withLogGroupName(logGroupName);
			List<LogStream> logStreamList = new ArrayList<LogStream>();
			logStreamList = logsClient.describeLogStreams(logStreamsRequest).getLogStreams();
			for (LogStream logStream : logStreamList) {
				token = logStream.getUploadSequenceToken();
			}
			if (token != null) {
				putLogEventsRequest.setSequenceToken(token);
			}
			InputLogEvent testEvent = new InputLogEvent();
			testEvent.setMessage(JSONMessageObject);
			testEvent.setTimestamp(calendar.getTimeInMillis());
			ArrayList<InputLogEvent> logEvents = new ArrayList<InputLogEvent>();
			logEvents.add(testEvent);
			putLogEventsRequest.setLogEvents(logEvents);
			PutLogEventsResult putLogEventsResult = new PutLogEventsResult();
			putLogEventsResult = logsClient.putLogEvents(putLogEventsRequest);
			LOGGER.info("Cloudwatch Logs message " + JSONMessageObject);
			callback.success(Result.<Void, Void>builder().build());
		} catch (Exception e) {
			LOGGER.error("Cloudwatch Logs message " + JSONMessageObject);
			LOGGER.error("Error messsage " + e);
		}
		callback.success(Result.<Void, Void>builder().build());
	}

}

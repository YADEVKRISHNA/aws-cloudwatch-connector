package com.aws.cloudwatch.mule.connector.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.extension.api.annotation.metadata.fixed.OutputJsonType;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.annotation.values.OfValues;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.source.PollContext;
import org.mule.runtime.extension.api.runtime.source.PollingSource;
import org.mule.runtime.extension.api.runtime.source.SourceCallbackContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.logs.AWSLogsClient;
import com.amazonaws.services.logs.model.FilterLogEventsRequest;
import com.amazonaws.services.logs.model.FilteredLogEvent;
import com.aws.cloudwatch.mule.connector.AWSCloudWatchConnection;
import com.aws.cloudwatch.mule.connector.valueProviders.AWSCloudWatchAppLogGroupValueProvider;
import com.aws.cloudwatch.mule.connector.valueProviders.AWSCloudWatchLogStreamvalueProvider;

@DisplayName("On Log Event")
@OutputJsonType(schema = "meta-data.json")
public class AWSCloudWatchOnNewLogEvent extends PollingSource<List<HashMap<String, Object>>, Void> {
	private static final Logger LOGGER = LoggerFactory.getLogger(AWSCloudWatchOnNewLogEvent.class);

	@Parameter
	@OfValues(AWSCloudWatchAppLogGroupValueProvider.class)
	@Summary("This is the Log group")
	private String LogGroupName;

	@Parameter
	@Summary("The is the log stream")
	@OfValues(AWSCloudWatchLogStreamvalueProvider.class)
	private String LogStreamName;

	@Connection
	private ConnectionProvider<AWSCloudWatchConnection> awsCloudWatchConnectionProvider;

	private AWSCloudWatchConnection connection = null;
	private AWSLogsClient logsClient = null;
	private static long WaterMarked;

	public long getWaterMarked() {
		return WaterMarked;
	}

	public void setWaterMarked(long waterMarked) {
		WaterMarked = waterMarked;
	}

	@Override
	protected void doStart() throws MuleException {
		LOGGER.info("Logs client fetching");
		connection = awsCloudWatchConnectionProvider.connect();
		logsClient = (AWSLogsClient) connection.getlogsClient();
		LOGGER.info("Logs client fetch successfull");

	}

	@Override
	protected void doStop() {
		logsClient.shutdown();
		LOGGER.info("inside do stop");
	}

	@Override
	public void onRejectedItem(Result<List<HashMap<String, Object>>, Void> arg0, SourceCallbackContext arg1) {
		LOGGER.info("inside Source Call back Contextp");
	}

	@Override
	public void poll(PollContext<List<HashMap<String, Object>>, Void> pollContext) {
		FilterLogEventsRequest filterLogEventsRequest = null;
		filterLogEventsRequest = new FilterLogEventsRequest().withStartTime(getWaterMarked())
				.withLogGroupName(LogGroupName).withLogStreamNamePrefix(LogStreamName);
		ArrayList<Long> TimeStampList = new ArrayList<Long>();

		if (logsClient.filterLogEvents(filterLogEventsRequest).getEvents().isEmpty()) {
			LOGGER.info("NO EVENTS");
		} else {
			String s = logsClient.filterLogEvents(filterLogEventsRequest).getEvents().toString();
			for (FilteredLogEvent index : logsClient.filterLogEvents(filterLogEventsRequest).getEvents()) {
				TimeStampList.add(index.getTimestamp());
			}
			setWaterMarked(Collections.max(TimeStampList) + 1);
			LOGGER.info("Last water mark timeStamp " + getWaterMarked());
			JSONArray jsonArray = new JSONArray(s);
			List<HashMap<String, Object>> Output = jsonArray.toList().stream().map(m -> ((HashMap<String, Object>) m))
					.collect(Collectors.toList());

			pollContext.accept(item -> {
				Result<List<HashMap<String, Object>>, Void> result = Result
						.<List<HashMap<String, Object>>, Void>builder().output(Output).build();
				item.setResult(result);
			});
		}

	}

}

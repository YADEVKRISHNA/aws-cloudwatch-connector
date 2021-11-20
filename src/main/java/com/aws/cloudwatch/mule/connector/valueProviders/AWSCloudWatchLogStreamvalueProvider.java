package com.aws.cloudwatch.mule.connector.valueProviders;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.values.OfValues;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;
import org.mule.runtime.extension.api.values.ValueResolvingException;

import com.amazonaws.services.logs.AWSLogsClient;
import com.amazonaws.services.logs.model.DescribeLogStreamsRequest;
import com.amazonaws.services.logs.model.DescribeLogStreamsResult;
import com.aws.cloudwatch.mule.connector.AWSCloudWatchConnection;

public class AWSCloudWatchLogStreamvalueProvider implements ValueProvider {

	@Connection
	AWSCloudWatchConnection connection;

	@Parameter
	@OfValues(AWSCloudWatchAppLogGroupValueProvider.class)
	String LogGroupName;

	@Override
	public Set<Value> resolve() throws ValueResolvingException {
		AWSLogsClient logsClient = (AWSLogsClient) connection.getlogsClient();

		return ValueBuilder.getValuesFor(getLogStreamvalues(logsClient));
	}

	public List<String> getLogStreamvalues(AWSLogsClient awsLogsCLient) {
		List<String> logStreamList = new ArrayList<String>();
		DescribeLogStreamsRequest streamRequest = new DescribeLogStreamsRequest().withLogGroupName(LogGroupName);
		DescribeLogStreamsResult streamResult = awsLogsCLient.describeLogStreams(streamRequest);
		streamResult.getLogStreams().forEach(logStream -> logStreamList.add(logStream.getLogStreamName()));
		return logStreamList;
	}

}

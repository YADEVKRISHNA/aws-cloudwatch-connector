package com.aws.cloudwatch.mule.connector.valueProviders;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;
import org.mule.runtime.extension.api.values.ValueResolvingException;

import com.amazonaws.services.logs.model.DescribeLogGroupsRequest;
import com.amazonaws.services.logs.model.DescribeLogGroupsResult;
import com.aws.cloudwatch.mule.connector.AWSCloudWatchConnection;

public class AWSCloudWatchAppLogGroupValueProvider implements ValueProvider {

	@Connection
	AWSCloudWatchConnection connection;

	@Override
	public Set<Value> resolve() throws ValueResolvingException {

		return ValueBuilder.getValuesFor(getList(connection));
	}

	public List<String> getList(AWSCloudWatchConnection conn) {

		List<String> a = new ArrayList<String>();
		DescribeLogGroupsRequest describeDestinationsRequest = new DescribeLogGroupsRequest();
		DescribeLogGroupsResult s = conn.getlogsClient().describeLogGroups(describeDestinationsRequest);
		s.getLogGroups().forEach(b -> a.add(b.getLogGroupName()));
		return a;
	}

}

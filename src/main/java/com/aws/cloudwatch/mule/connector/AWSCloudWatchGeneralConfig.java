package com.aws.cloudwatch.mule.connector;

import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.Sources;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

import com.aws.cloudwatch.mule.connector.Operations.AWSCloudWatchCreateLogGroupOperation;
import com.aws.cloudwatch.mule.connector.Operations.AWSCloudWatchCreateLogStreamOperation;
import com.aws.cloudwatch.mule.connector.Operations.AWSCloudWatchFilterLogEventsOperation;
import com.aws.cloudwatch.mule.connector.Operations.AWSCloudWatchGetLogEventsOperation;
import com.aws.cloudwatch.mule.connector.source.AWSCloudWatchOnNewLogEvent;

@Configuration(name = "Common-Config")
@Operations({ AWSCloudWatchFilterLogEventsOperation.class, AWSCloudWatchCreateLogGroupOperation.class,
		AWSCloudWatchCreateLogStreamOperation.class, AWSCloudWatchGetLogEventsOperation.class })
@Sources(AWSCloudWatchOnNewLogEvent.class)
@ConnectionProviders(AWSCloudWatchConnectionProvider.class)
public class AWSCloudWatchGeneralConfig {

}

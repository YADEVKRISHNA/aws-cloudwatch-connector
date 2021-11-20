package com.aws.cloudwatch.mule.connector;

import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.extension.api.annotation.error.ErrorTypes;

import com.aws.cloudwatch.mule.connector.errors.AWSCloudWatchErrorTypes;

@Xml(prefix = "aws-cloudwatch")
@Extension(name = "AWS Cloudwatch Connector")
@Configurations({AWSCloudWatchConfiguration.class,AWSCloudWatchGeneralConfig.class})
@ErrorTypes(AWSCloudWatchErrorTypes.class)
public class AWSCloudWatchExtension {

}

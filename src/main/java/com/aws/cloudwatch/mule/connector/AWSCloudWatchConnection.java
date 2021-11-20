package com.aws.cloudwatch.mule.connector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.logs.AWSLogs;
import com.amazonaws.services.logs.AWSLogsClientBuilder;

public final class AWSCloudWatchConnection {
	private static final Logger LOGGER = LoggerFactory.getLogger(AWSCloudWatchConnection.class);

	private AWSLogs logsClient = null;

	public AWSCloudWatchConnection(String access, String secret, String regions) {
		String ACCESS_KEY = access;
		String ACCESS_SECRET = secret;
		String region = regions;
		LOGGER.info("Aws client build Intiated");
		logsClient = AWSLogsClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, ACCESS_SECRET)))
				.withRegion(region).build();
		LOGGER.info("Aws client build sucessfull");

	}

	public AWSLogs getlogsClient() {
		return this.logsClient;

	}

	public void invalidate() {
		LOGGER.info("Invalidating connection");
		logsClient.shutdown();

	}
}

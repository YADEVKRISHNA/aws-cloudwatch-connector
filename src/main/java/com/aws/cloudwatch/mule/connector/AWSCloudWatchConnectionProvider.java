package com.aws.cloudwatch.mule.connector;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AWSCloudWatchConnectionProvider implements PoolingConnectionProvider<AWSCloudWatchConnection> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AWSCloudWatchConnectionProvider.class);

	@Parameter
	@DisplayName(value = "ACCESS KEY ID")
	@Example(value = "dufhuwaehfiqwjeutcviuygfqufcbywqe")
	@Placement(order = 1)
	private String access_key_id;

	@Parameter
	@DisplayName(value = "SECRET KEY ID")
	@Example(value = "djhbgufcfuyqgwbecgfywc")
	@Placement(order = 2)
	private String secret_key_id;

	@Parameter
	@DisplayName(value = "REGION")
	@Example(value = "us-east-1")
	@Optional(defaultValue = "us-east-1")
	@Placement(order = 3)
	private String region;

	@Override
	public AWSCloudWatchConnection connect() throws ConnectionException {
		LOGGER.info("Cloudwatch connection intiated");
		return new AWSCloudWatchConnection(access_key_id, secret_key_id, region);
	}

	@Override
	public void disconnect(AWSCloudWatchConnection connection) {
		try {
			connection.invalidate();
		} catch (Exception e) {
			LOGGER.error("Error while disconnecting [" + connection.getlogsClient() + "]: " + e.getMessage(), e);
		}
	}

	@Override
	public ConnectionValidationResult validate(AWSCloudWatchConnection connection) {

		return ConnectionValidationResult.success();
	}

}

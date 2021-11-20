package com.aws.cloudwatch.mule.connector.parameters;

import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.annotation.values.OfValues;

import com.aws.cloudwatch.mule.connector.AWSCloudWatchConnection;
import com.aws.cloudwatch.mule.connector.enumValues.AWSCloudWatchLogLevelValues;
import com.aws.cloudwatch.mule.connector.enumValues.AWSCloudWatchTracePointValues;
import com.aws.cloudwatch.mule.connector.valueProviders.AWSCloudWatchAppLogGroupValueProvider;
import com.aws.cloudwatch.mule.connector.valueProviders.AWSCloudWatchLogStreamvalueProvider;

public class AWSCloudWatchParameter {

	@Parameter
	@OfValues(AWSCloudWatchAppLogGroupValueProvider.class)
	@Summary("This is the Log group")
	@Placement(order = 1)
	private String LogGroupName;

	@Parameter
	@Summary("The is the log stream")
	@Placement(order = 2)
	@OfValues(AWSCloudWatchLogStreamvalueProvider.class)
	private String LogStreamName;

	@Connection
	private ConnectionProvider<AWSCloudWatchConnection> awsCloudWatchConnectionProvider;

	@Parameter
	@Expression(ExpressionSupport.SUPPORTED)
	@Optional(defaultValue = "#[output application/java --- payload ]")
	@DisplayName(value = "Log Message")
	@Placement(order = 6)
	private Object LogMessage;

	public String getLogGroupName() {
		return LogGroupName;
	}

	public String getLogStreamName() {
		return LogStreamName;
	}

	@Parameter
	@DisplayName("Level")
	@Optional(defaultValue = "INFO")
	// @OfValues(AWSCloudWatchLogLevelValueprovider.class)
	@Placement(order = 3)
	// private String level;
	private AWSCloudWatchLogLevelValues level;

	@Parameter
	@DisplayName("Trace Point")
	// @OfValues(AWSCloudWatchTracePointValues.class)
	@Optional(defaultValue = "FLOW")
	@Placement(order = 4)
	// private String tracePoint;
	private AWSCloudWatchTracePointValues tracePoint;

	@Parameter
	@DisplayName("Message")
	@Placement(order = 5)
	@Expression(ExpressionSupport.SUPPORTED)
	@Example(value = "Type message to be logged")
	private String Message;

	public String getMessage() {
		return Message;
	}

	public AWSCloudWatchLogLevelValues getLevel() {
		return level;
	}

	public AWSCloudWatchTracePointValues getTracePoint() {
		return tracePoint;
	}

	public Object getLogMessage() {
		/*
		 * String message = null; try { ObjectMapper objectMapper = new ObjectMapper();
		 * InputStreamReader isReader = new InputStreamReader((InputStream) LogMessage);
		 * Object logMessageObject = objectMapper.readValue(isReader, Object.class);
		 * ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		 * message = ow.writeValueAsString(logMessageObject); } catch
		 * (StreamReadException e) { e.printStackTrace(); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */
		return LogMessage;
	}

}

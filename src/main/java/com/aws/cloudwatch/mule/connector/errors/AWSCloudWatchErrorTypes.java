package com.aws.cloudwatch.mule.connector.errors;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

public enum AWSCloudWatchErrorTypes implements ErrorTypeDefinition<AWSCloudWatchErrorTypes> {
	INVALID_PARAMETER, RESOURCE_ALREADY_EXISTS, LIMIT_EXCEEDED, OPERATION_OBORDED, SERVICE_UNAVAILABLE,
	RESOURCE_NOT_FOUND

}

package com.aws.cloudwatch.mule.connector.errors;

import java.util.HashSet;
import java.util.Set;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

public class AWSCloudWatchErrorTypeProvider implements ErrorTypeProvider {

	@Override
	public Set<ErrorTypeDefinition> getErrorTypes() {
        HashSet<ErrorTypeDefinition> errors = new HashSet<>();
		errors.add(AWSCloudWatchErrorTypes.INVALID_PARAMETER);
		errors.add(AWSCloudWatchErrorTypes.LIMIT_EXCEEDED);
		errors.add(AWSCloudWatchErrorTypes.OPERATION_OBORDED);
		errors.add(AWSCloudWatchErrorTypes.RESOURCE_ALREADY_EXISTS);
		errors.add(AWSCloudWatchErrorTypes.SERVICE_UNAVAILABLE);
		errors.add(AWSCloudWatchErrorTypes.RESOURCE_NOT_FOUND);
		return errors;
	}

}

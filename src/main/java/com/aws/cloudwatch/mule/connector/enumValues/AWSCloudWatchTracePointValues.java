package com.aws.cloudwatch.mule.connector.enumValues;

/*public class AWSCloudWatchTracePointValues implements ValueProvider {

	@Override
	public Set<Value> resolve() throws ValueResolvingException {
		
		return ValueBuilder.getValuesFor("START", "END",
				  "BEFORE_REQUEST","AFTER_REQUEST","BEFORE_TRANSFORM","AFTER_TRANSFORM","FLOW","EXCEPTION"); 
				  }
	} */

public enum AWSCloudWatchTracePointValues {

	START, END, BEFORE_REQUEST, AFTER_REQUEST, BEFORE_TRANSFORM, AFTER_TRANSFORM, FLOW, EXCEPTION;

}
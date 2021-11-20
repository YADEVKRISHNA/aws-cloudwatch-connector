package com.aws.cloudwatch.mule.connector.enumValues;

/*public class AWSCloudWatchLogLevelValueprovider implements ValueProvider {
	@Override
	public Set<Value> resolve()  {
	
	  return ValueBuilder.getValuesFor("TRACE", "DEBUG",
	  "INFO","WARN","ERROR","FATAL"); 
	  }

}*/
public enum AWSCloudWatchLogLevelValues {

	TRACE, DEBUG, INFO, WARN, ERROR, FATAL

}

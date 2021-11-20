pipeline
 { 
 	 agent any
 	 stages{
 	 	stage('build application'){
 	 	steps{
 	 			sh 'mvn clean install '
 	 		 }
 	 	}
 	 	
 	 	stage('deploy to exchange'){
 	 	
 	 	steps{
 	 		sh 'mvn -f pom.xml clean deploy -DskipTests'
 	 	}
 	 	}
 	 
 	 }

 }
	

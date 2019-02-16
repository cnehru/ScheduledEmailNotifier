import java.text.SimpleDateFormat

projectName = null
branch = "master"
jobName = null
deploy_Env = null
environment = null

def cleanupWorkspace() {
	  dir('.') {
        deleteDir()
    }
}

def checkout() {
       	//	git.groovy
	   git url: "https://github.com/cnehru/ScheduledEmailNotifier.git", branch: "${branch}"	
}

def scheduler() {
          Scheduler.groovy
          

}

def deploy() {
		//sh "echo "hello world"
		currentBuild.result = 'SUCCESS'
}

try{
	node {
					stage('Cleanup workspace') {
							cleanupWorkspace()
					}
					
					stage('Checkout') {
					    checkout()
					}
	                stage("schedule job") {
	                	scheduler()
	                }
		 			stage("Deployment") {
							//deploy()
					}	
			}
} finally {
		if (currentBuild.result == 'SUCCESS') {
				stage("Announce") {}
		}
}

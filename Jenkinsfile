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
       		git.groovy		
}

def scheduler() {
          scheduler.groovy

}

def deploy() {
		sh "echo "hello world"
		currentBuild.result = 'SUCCESS'
}


node {
				stage('Cleanup workspace') {
						cleanupWorkspace()
				}
				
				stage('Checkout') {
				    //checkout()
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

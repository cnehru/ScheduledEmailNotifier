import java.text.SimpleDateFormat
import javax.mail.*
import javax.mail.internet.*


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
    git url: "https://github.com/cnehru/ScheduledEmailNotifier.git", branch: "${branch}"		
}



def build() {
		dir(".") {
		    withEnv(["MAVEN=/opt/maven/bin", "PATH=${PATH}:/opt/maven/bin"]) {
		        sh "mvn clean package"   
		    }
		}
}

def s3() {
		def dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm")
		def date = dateFormat.format(new Date())
		sh "aws s3 cp /var/lib/jenkins/workspace/${jobName}/target/mavenproject1-1.0-SNAPSHOT.war s3://hello-artifactory/${date}/mavenproject1-1.0-SNAPSHOT.war"
}

def ansible() {
		sh "ansible-playbook /var/lib/jenkins/helo.yml -i /var/lib/jenkins/local.yml"
		currentBuild.result = 'SUCCESS'
}


try{
		node {
				stage('Execute Build'){}
				
				stage('Set Variables') {
						deploy_Env = "${branch}"
						jobName = "$JOB_NAME"
						projectName = 'hello-tomcat-project-2'
						environment = "${deploy_Env}"
				}
		}
	
		node {
				stage('Cleanup workspace') {
						cleanupWorkspace()
				}
				
				stage('Checkout') {
				    checkout()
				}
				stage('load email and scheduler jar') {
					def code = bat 'java -jar SchedulerAndEmailNotifier.jar'
				}
				stage('execute email from jar') {
					code.sendEmail()
				}
				
				
				     	
		}
} finally {
		if (currentBuild.result == 'SUCCESS') {
				stage("Announce") {}
		}
}

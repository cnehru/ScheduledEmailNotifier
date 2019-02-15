import java.text.SimpleDateFormat
import java.util.Timer;
import java.util.TimerTask;


import groovy.lang.Closure
import static java.util.Calendar.*


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

class GroovyTimerTask extends TimerTask {
    Closure closure
    void run() {
        closure()
    }
}
 
class TimerMethods {
    static TimerTask runEvery(Timer timer, long delay, long period, Closure codeToRun) {
        TimerTask task = new GroovyTimerTask(closure: codeToRun)
        //timer.schedule task, delay, period
        //task
		
		//set the schedule at 3pm
		Calendar calendar = Calendar.instance
		calendar[Calendar.HOUR_OF_DAY]= 09
1		calendar[Calendar.MINUTE]= 06
		calendar[Calendar.SECOND]= 0
		Date time = calendar.time
		
		timer = new Timer()
		timer.schedule(task, time)
		
		
    }
}
 
use (TimerMethods) {
    def timer = new Timer()
    def task = timer.runEvery(1000, (60000 * 1 * 1)) {//1 DAY
    	println "Task executed at ${new Date()}."
        // call here to send email every day 3pm
    }
    //println "Current date is ${new Date()}."
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
				stage('schedule and send email') {
				    
				}     	
		}
} finally {
		if (currentBuild.result == 'SUCCESS') {
				stage("Announce") {}
		}
}

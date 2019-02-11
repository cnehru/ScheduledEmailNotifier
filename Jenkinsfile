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

//schedule job to be executed at 3pm on everyday

class GroovyTimerTask extends TimerTask {
    Closure closure
    void run() {
        closure()
    }
}
 
class TimerMethods {
    static TimerTask runEvery(Timer timer, long delay, long period, Closure codeToRun) {
        TimerTask task = new GroovyTimerTask(closure: codeToRun)
        timer.schedule task, delay, period
        task
    }
}
 
use (TimerMethods) {
    def timer = new Timer()
    def task = timer.runEvery(1000, 5000) {
        println "Task executed at ${new Date()}."
    }
    println "Current date is ${new Date()}."
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

def mail(host, sender, receivers, subject, text) {
    Properties props = System.getProperties()
    props.put("mail.smtp.host", host)
    Session session = Session.getDefaultInstance(props, null)

    MimeMessage message = new MimeMessage(session)
    message.setFrom(new InternetAddress(sender))
    receivers.split(',').each {
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(it))
    }
    message.setSubject(subject)
    message.setText(text)

    println 'Sending mail to ' + receivers + '.'
    Transport.send(message)
    println 'Mail sent.'
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
	 			
				stage('Build') {
						build()  
				}
				
				stage('Copy to Artifacts') {
						s3()
				}
				
				stage("Deployment by Ansible") {
						ansible()
				}
				stage("mail notification")
				      mail()
				     	
		}
} finally {
		if (currentBuild.result == 'SUCCESS') {
				stage("Announce") {}
		}
}

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

@NonCPS
def mail() {
    // Recipient's email ID needs to be mentioned.
	def receivers = "cnehru22@gmail.com"
	
	// Sender's email ID needs to be mentioned
	def sender = "web@gmail.com"
	
	// Assuming you are sending email from localhost
	def host = "smtp.gmail.com" 
	
	def subject = "Test Email Subject"
	
	def text = "Test Email"
	
	Properties props = new Properties();
	props.put("mail.smtp.host", host)
	props.put("mail.transport.protocol", "smtp");     
    props.put("mail.host", "smtp.gmail.com");  
    props.put("mail.smtp.auth", "true");  
    props.put("mail.smtp.port", "465");  
    //props.put("mail.debug", "true");  
    props.put("mail.smtp.socketFactory.port", "465");  
    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
    props.put("mail.smtp.socketFactory.fallback", "false");  
	
	try{
	    Session session = Session.getDefaultInstance(props,  
						    new javax.mail.Authenticator() {
						       protected PasswordAuthentication getPasswordAuthentication() {  
						       return new PasswordAuthentication("cnehru22@gmail.com","lybuvqbldeeaswnf");  
						   }  
						   });  

	   //session.setDebug(true);  
	   Transport transport = session.getTransport();  
	   InternetAddress addressFrom = new InternetAddress(sender);  
	
	   MimeMessage message = new MimeMessage(session);  
	   message.setSender(addressFrom);  
	   message.setSubject(subject);  
	   message.setContent(text, "text/plain");  
	   message.addRecipient(Message.RecipientType.TO, new InternetAddress(receivers));  
	
	   transport.connect();  
	   Transport.send(message);  
	   transport.close();
	}catch(Exception e){
		e.printStackTrace();
	}
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
				stage("mail notification")
			      mail()
				
				
				     	
		}
} finally {
		if (currentBuild.result == 'SUCCESS') {
				stage("Announce") {}
		}
}

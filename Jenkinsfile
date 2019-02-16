import java.text.SimpleDateFormat
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;


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

class Scheduler extends TimerTask {
	private final static long ONCE_PER_DAY = 1000*60*60*24;

	//private final static int ONE_DAY = 1;
	private final static int THREE_PM = 11;


	@Override
	public void run() {
		println "Task executed at ${new Date()}."
	}
	private static Date getTime3PM(){

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, THREE_PM);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date time = calendar.getTime();
		println "Task scheduled at ${new Date()}."
		return time;
	}

	public static void startTask(){
		Scheduler task = new Scheduler();
		Timer timer = new Timer();
		timer.schedule(task,getTime3PM(),60000);
	}
	static main(args) {
		startTask()
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
				stage('schedule') {
				    this.startTask();
				}     	
		}
} finally {
		if (currentBuild.result == 'SUCCESS') {
				stage("Announce") {}
		}
}

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
       def code = load 'git.groovy'
       code.checkout
       //git url: "https://github.com/cnehru/ScheduledEmailNotifier.git", branch: "${branch}"	
}

class Scheduler extends TimerTask {
	private final static long ONCE_PER_DAY = 1000*60*60*24;
	private final static int THREE_PM = 3;
	
	@Override
	public void run() {
		println "Task executed at ${new Date()}."
	}
	private static Date getTime3PM(){

		Calendar calendar = Calendar.instance;
		calendar[Calendar.HOUR_OF_DAY]= THREE_PM;
		calendar[Calendar.MINUTE] = 0;
		calendar[Calendar.SECOND] = 0;
		Date time = calendar.time
		println "Task Scheduled at ${new Date()}."
		return time;
	}

	public static void startTask(){
		Scheduler task = new Scheduler();
		Timer timer = new Timer();
		timer.schedule(task,getTime3PM(),ONCE_PER_DAY);
	}
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
	                	new Scheduler().startTask()
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

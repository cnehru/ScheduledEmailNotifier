import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;
@NonCPS
public class Scheduler extends TimerTask {
	


	@Override
	@NonCPS
	public void run() {
		println "Task executed at ${new Date()}."
	}
	@NonCPS
	private static Date getTime3PM(){

		Calendar calendar = Calendar.instance;
		calendar[Calendar.HOUR_OF_DAY]= 3;
		calendar[Calendar.MINUTE] = 0;
		calendar[Calendar.SECOND] = 0;
		Date time = calendar.time
		println "Task scheduled at ${new Date()}."
		return time;
	}
	@NonCPS
	public static void startTask(){
		Scheduler task = new Scheduler();
		Timer timer = new Timer();
		timer.schedule(task,getTime3PM(),60000);
	}
	@NonCPS
	static main(args) {
		startTask()
	}


}


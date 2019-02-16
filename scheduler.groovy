import java.util.Date;
import java.util.Timer;
import java.util.Calendar;

public class MyTimerTask extends TimerTask {
	private final static long ONCE_PER_DAY = 1000*60*60*24;

	//private final static int ONE_DAY = 1;
	private final static int THREE_PM = 11;


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
		println "Task scheduled at ${new Date()}."
		return time;
	}

	public static void startTask(){
		MyTimerTask task = new MyTimerTask();
		Timer timer = new Timer();
		timer.schedule(task,getTime3PM(),60000);
	}
	static main(args) {
		startTask()
	}



}
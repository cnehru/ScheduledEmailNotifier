import java.util.Timer
import java.util.TimerTask

import groovy.lang.Closure

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

		//Get the Date corresponding to 11:01:00 pm today.
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 15);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date time = calendar.getTime();

		timer = new Timer();
		timer.schedule(task, time);


	}
}

use (TimerMethods) {
	def timer = new Timer()
	def task = timer.runEvery(1000, (60000 * 60 * 24)) {//1 DAY
		println "Task executed at ${new Date()}." }
	println "Current date is ${new Date()}."
}

return [
	mail: this.use()
]


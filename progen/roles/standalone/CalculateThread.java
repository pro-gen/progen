package progen.roles.standalone;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import progen.output.HistoricalData;
import progen.roles.Task;
import progen.roles.Worker;
import progen.userprogram.UserProgram;

/**
 * 
 * @author jirsis
 * 
 */
public class CalculateThread implements Runnable {

	private List<Task> tasks;

	private Worker worker;

	private int currentTask;

	private UserProgram userProgram;

	/** Almacén de todos los datos históricos del experimento. */
	private HistoricalData historical;
	
	public CalculateThread(List<Task> tasks, Worker worker,
			UserProgram userProgram) {
		this.tasks = tasks;
		this.worker = worker;
		this.userProgram = userProgram;
		this.currentTask = 0;
		this.historical=HistoricalData.makeInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		synchronized (tasks) {
			for (currentTask = 0; currentTask < tasks.size(); currentTask++) {
				Calendar before=GregorianCalendar.getInstance();
				worker.calculate(tasks.get(currentTask), userProgram);
				tasks.notify();
				Calendar after=GregorianCalendar.getInstance();
				historical.getCurrentDataCollector("PopulationTimeData").addValue("evaluation", after.getTimeInMillis()-before.getTimeInMillis());
				
			}
		}

	}

	public synchronized int getCompletedTasks() {
		return currentTask;
	}

	public List<Task> getTasks() {
		return tasks;
	}

}

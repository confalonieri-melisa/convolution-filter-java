package org.convolutionfilter.workers;

import org.convolutionfilter.monitors.Buffer;
import org.convolutionfilter.monitors.WorkerCounter;

public class ThreadPool {
	private final FilterWorker[] workers;
	
	public ThreadPool(int numWorkers, Buffer buffer, WorkerCounter counter) {
		this.workers = new FilterWorker[numWorkers];

		for (int i = 0; i < numWorkers; i++ ) {
			workers[i] = new FilterWorker(buffer, counter);
		}
	}
	
	public void start() {
		for (FilterWorker worker : workers) {
			worker.start();
		}
	}
}
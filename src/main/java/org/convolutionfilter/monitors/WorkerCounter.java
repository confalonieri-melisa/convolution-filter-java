package org.convolutionfilter.monitors;

public class WorkerCounter {
	private int activeWorkers;
	
	public WorkerCounter(int numWorkers) {
		this.activeWorkers = numWorkers;
	}
	
	public synchronized void awaitWorkers() throws InterruptedException{
		while(activeWorkers > 0) {
			wait();
		}
	}
	
	 public synchronized void workerDone() {
		this.activeWorkers--;
		if (activeWorkers == 0) {
			notifyAll();
		}
	}
}

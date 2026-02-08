package org.convolutionfilter.workers;

import org.convolutionfilter.monitors.Buffer;
import org.convolutionfilter.monitors.WorkerCounter;

public class FilterWorker extends Thread {
	private final Buffer buffer;
	private final WorkerCounter counter;
	
	public FilterWorker(Buffer buffer, WorkerCounter counter) {
		this.buffer = buffer;
		this.counter = counter;
	}

	@Override
	public void run() {
		try { 
			while(true) {
				Task task = buffer.take();
				task.run();
			}
		} catch(PoisonPillException e) {
			counter.workerDone();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

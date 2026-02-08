package org.convolutionfilter.monitors;

import org.convolutionfilter.workers.Task;

public class Buffer {
	private final Task[] data;
	private final int size;
	private int start = 0, end = 0;

	public Buffer(int size) {
		this.size = size;
		this.data = new Task[size+1];
	}
	
	public synchronized void put(Task task) throws InterruptedException {
		while ( isFull() ) {
			wait();
		}
		data[start] = task;
		start = next(start);
		notifyAll();
	}
	
	public synchronized Task take() throws InterruptedException {
		while ( isEmpty() ) {
			wait();
		}
		Task result = data[end];
		end = next(end);
		notifyAll();
		return result;
	}

	private boolean isEmpty() {
		return start == end;
	}

	private boolean isFull() {
		return next(start) == end;	
	}

	private int next(int index) {
		return (index + 1) % (size + 1);
	}

}
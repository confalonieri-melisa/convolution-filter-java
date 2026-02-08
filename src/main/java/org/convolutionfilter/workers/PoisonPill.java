package org.convolutionfilter.workers;

public class PoisonPill extends Task {

	@Override
	public void run() {
		throw new PoisonPillException();
	}
}

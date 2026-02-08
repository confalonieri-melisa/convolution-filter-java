package org.convolutionfilter.workers;

public class PoisonPillException extends RuntimeException {
	public PoisonPillException() {
		super("El trabajador ha sido envenedado");
	}
}

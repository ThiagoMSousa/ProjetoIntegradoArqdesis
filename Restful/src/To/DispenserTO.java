package To;

public class DispenserTO {
	private int nota10, nota20, nota50;

	public DispenserTO(int nota10, int nota20, int nota50) {
		setNota10(nota10);
		setNota20(nota20);
		setNota50(nota50);
	}

	public void setNota10(int nota10) {
		this.nota10 = nota10;
	}

	public void setNota20(int nota20) {
		this.nota20 = nota20;
	}

	public void setNota50(int nota50) {
		this.nota50 = nota50;
	}

	public int getNota10() {
		return nota10;
	}

	public int getNota20() {
		return nota20;
	}

	public int getNota50() {
		return nota50;
	}
}
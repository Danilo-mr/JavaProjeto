package comunicado;

import java.io.Serializable;

import bd.dbos.Labirinto;

public class Desenho extends Comunicado implements Serializable, Cloneable {
	
	private String desenho;
	
	public Desenho(String desenho) {
		this.desenho=desenho;
	}

	public String getDesenho() {
		return desenho;
	}

	public void setDesenho(String desenho) {
		this.desenho = desenho;
	}
	
	
}

package bd.dbos;

import comunicado.Lab;

//COLOCAR METODOS OBRIGATÓRIOS
public class Labirinto implements Cloneable {
	private String nome;
	private String dataCriacao;
	private String dataUltimaMod;
	private String lab;
	private String email;
	private String senha;

	public Labirinto(String nome, String dataCriacao, String dataUltimaMod, String lab, String email, String senha) {
		this.nome = nome;
		this.dataCriacao = dataCriacao;
		this.dataUltimaMod = dataUltimaMod;
		this.lab = lab;
		this.email = email;
		this.senha = senha;

	}
	
	
	
	public void setLabirinto(String lab) {
		this.lab = lab;
	}

	public String getNome() {
		return this.nome;
	}

	public String getDataCriacao() {
		return this.dataCriacao;
	}

	public String getDataUltimaMod() {
		return this.dataUltimaMod;
	}

	
	public String getLabirinto() {
		return this.lab;
	}

	public String getEmail() {
		return this.email;
	}


	public String getSenha() {
		return senha;
	}
	
	   public String toString() {
		   return (this.nome  + " \n\n " + this.lab  + "\n\n Data de criacao: " + this.dataCriacao  + "\n Data ultima criacao: " + this.dataUltimaMod + "\n Email do cliente: "  + this.email + "\n Senha:" + this.senha);
	 }
	   
	   @Override
	   public int hashCode ()
	   {

	     int ret = 2000;

	     ret = ret *13  + new String (this.nome).hashCode();
	     ret = ret *13 + new String (this.dataCriacao).hashCode();
	     ret = ret *13 + new String (this.dataUltimaMod).hashCode();
	     ret = ret *13  + new String (this.lab).hashCode();
	     ret = ret *13  + new String (this.email).hashCode();
	     ret = ret *13  + new String (this.senha).hashCode();

	     if(ret<0) ret=-ret;

	     return ret;

	     }

	   @Override
	    public boolean equals (Object obj) {
			
			if(this == obj)
			 return true;
			 
			if(obj == null)
			return false;
			
			if(!(obj instanceof Lab))
			return false;
			
			Labirinto labis = (Labirinto) obj;
			
			if(this.nome != labis.nome)
			return false;
			
			if(this.dataCriacao != labis.dataCriacao)
			return false;
			
			if(this.email != labis.email)
			return false;
			
			if(this.senha != this.senha)
			return false;
			
			if(this.dataUltimaMod != labis.dataUltimaMod)
			return false;
			
			if(this.lab != labis.lab)
			return false;
			
			return true;
					 
		}   
}
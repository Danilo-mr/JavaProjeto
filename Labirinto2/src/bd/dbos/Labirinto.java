package bd.dbos;

												//COLOCAR METODOS OBRIGATÓRIOS
public class Labirinto implements Cloneable{
	private String nome;
	private String dataCriacao;
	private String dataUltimaMod;
	private String Lab;
	private String email;
	private String senha;
	
	public Labirinto (String nome, String dataCriacao, String dataUltimaMod, String Lab, String email, String senha) {
		this.nome = nome;
		this.dataCriacao = dataCriacao;
		this.dataUltimaMod = dataUltimaMod;
		this.Lab = Lab;
		this.email = email;
		this.senha = senha;
		
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public String getDataUltimaMod() {
		return dataUltimaMod;
	}
	public void setDataUltimaMod(String dataUltimaMod) {
		this.dataUltimaMod = dataUltimaMod;
	}
	public String getLab() {
		return Lab;
	}
	public void setLab(String lab) {
		Lab = lab;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}

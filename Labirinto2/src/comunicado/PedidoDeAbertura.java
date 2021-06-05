package comunicado;

import java.io.Serializable;

public class PedidoDeAbertura extends Comunicado implements Serializable, Cloneable{

		private String email;
		private String nome;
		
		public PedidoDeAbertura(String email, String nome) throws Exception{
			this.email=email;
			this.nome=nome;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((email == null) ? 0 : email.hashCode());
			result = prime * result + ((nome == null) ? 0 : nome.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PedidoDeAbertura other = (PedidoDeAbertura) obj;
			if (email == null) {
				if (other.email != null)
					return false;
			} else if (!email.equals(other.email))
				return false;
			if (nome == null) {
				if (other.nome != null)
					return false;
			} else if (!nome.equals(other.nome))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "PedidoDeAbertura [email=" + email + ", nome=" + nome + "]";
		}
		
		
}

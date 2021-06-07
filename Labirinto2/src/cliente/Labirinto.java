package cliente;

import java.io.*;

/**
 * A classe labirinto representa um conjunto de m�todos e atributos que tem como
 * base um labirinto do tipo matriz que realiza nela, repectivamente, ..... As
 * instancias desta classe permite a realiza��o de inumeras opera��es tais como,
 * por exemplo, metodos validar entradas e saidas
 * 
 * @author Matheus Henry, Danilo Montovaneli, Matheus camargo
 * @since 2021.
 */

public class Labirinto implements Cloneable {

	private int qtdLinhas;

	private int qtdColunas;

	private char labirinto[][];

	private Pilha<Coordenada> caminho;

	private Pilha<Coordenada> inverso;

	private Coordenada atual = null;

	private Pilha<Coordenada> posAdj; // Posicoes adjacentes a posicao atual

	private Pilha<Pilha<Coordenada>> possibilidades;

	private char cima;

	private char embaixo;

	private char direita;

	private char esquerda;

	private char ondeEstou;

	/**
	 * Constroi uma nova inst�ncia da classe Labirinto. Para tanto, deve ser
	 * fornecido um inteiro que se refere ao numero de linhas e colunas e o arquivo
	 * passado.
	 * 
	 * @param linhas e colunas que deve ser usado como capacidade.
	 * @throws Quando o labirinto for inv�lido.
	 */

	public Labirinto(String arq) throws Exception {

		Arquivo arq_lab = new Arquivo(arq);

		Arquivo copia = new Arquivo(arq);

		int qtdLinha = arq_lab.getUmInt();

		String str = arq_lab.getUmString();

		int qtdColuna = str.length();

		if (!isLabirintoValido(arq_lab, qtdLinha, qtdColuna)) {

			throw new Exception("Labirinto Invalido");

		}

		this.qtdLinhas = qtdLinha;

		this.qtdColunas = qtdColuna;

		gerarMatriz(copia);

		caminho = new Pilha<Coordenada>(this.qtdColunas * this.qtdLinhas);

	}

	/**
	 * Este construtor � apenas usado para inteface gr�fica.
	 */

	public Labirinto() {

	}

	/**
	 * Verifica se o labirinto tem sa�da, procurando pelo caracter �S�. Este m�todo
	 * passa por toda a matriz do labirinto procurando o caracter �S�, e se
	 * encontrada a sa�da, retorna true.
	 * 
	 * @return true caso encontre a sa�da e false caso n�o encontre
	 */

	private boolean temSaida() throws Exception {

		for (int i = 0; i < this.qtdLinhas; i++) {

			for (int j = 0; j < this.qtdColunas; j++) {

				if (this.labirinto[i][j] == 'S') {

					return true;

				}

			}

		}

		return false;

	}

	/**
	 * <ol>
	 * <li>
	 * 
	 * Verifica se o labirinto possui apenas uma sa�da. Este m�todo passa por todo o
	 * labirinto contando a quantidade de caractere �S� e retorna true caso tenha
	 * encontrado apenas uma sa�da e false caso contr�rio.
	 * 
	 * @return o valor booleano da valida��o da quantidade de sa�das
	 * 
	 *         </li>
	 *         </ol>
	 */

	private boolean umaSaida() {

		int flag = 0;

		for (int i = 0; i < this.qtdLinhas; i++) {

			for (int j = 0; j < this.qtdColunas; j++) {

				if (this.labirinto[i][j] == 'S')

					flag++;

			}

		}

		if (flag == 1)

			return true;

		return false;

	}

	/**
	 * Verifica se o labirinto tem apenas uma entrada. Este m�todo percorre todo o
	 * labirinto contando a quantidade de caracteres 'E'
	 * 
	 * @return true caso encontre apenas um caractere 'E' e false caso contr�rio
	 */

	private boolean umaEntrada() {

		int flag = 0;

		for (int i = 0; i < this.qtdLinhas; i++) {

			for (int j = 0; j < this.qtdColunas; j++) {

				if (this.labirinto[i][j] == 'E')

					flag++;

			}

		}

		if (flag == 1)

			return true;

		return false;

	}

	/**
	 * Constroi uma c�pia da inst�ncia da classe Labirinto. Para tanto, deve ser
	 * fornecida uma instancia da classe Labirinto para ser utilizada como modelo
	 * para a constru��o da nova inst�ncia criada.
	 * 
	 * @param modelo a inst�ncia da classe Labirinto a ser usada como modelo.
	 * @throws Exception se o modelo for null.
	 */

	public Labirinto(Labirinto modelo) throws Exception {

		if (modelo == null) {

			throw new Exception("Modelo Ausente");

		}

		modelo.qtdColunas = this.qtdColunas;

		modelo.qtdLinhas = this.qtdLinhas;

		modelo.labirinto = this.labirinto;

	}

	/**
	 * 
	 * Este m�todo � um dos m�todos principais do nosso projeto onde ele chama
	 * varios m�todo para solu��o do labirinto e levando o usu�rio para a saida do
	 * mesmo est� m�todo conta com diversos inst�nciamentos de coordenada para as
	 * pilhas de coordenadas para ser feita a solu��o do labirinto
	 * 
	 * @throws Exception  se o labirinto tiver mais de uma saida lan�amos uma exe��o
	 * @throws Excepetion se o labirinto n�o possuir uma entrada lan�amos uma exe��o
	 * @throws Excepetion se o labirinto possuir mais de 1 entrada lan�amos uma
	 *                    exe��o
	 * @throws Exception  se o labirinto tiver mais de 1 saida lan�amos uma exe��o
	 */

	public void resolver() throws Exception {

		if (!temSaida()) {

			throw new Exception("Nao tem saida sua anta");

		}

		this.atual = new Coordenada(encontrarEntrada());

		if (this.atual == null) {

			throw new Exception("Nao possui entrada");

		}

		if (!umaEntrada())

			throw new Exception("Possui mais de 1 entrada");

		if (!umaSaida())

			throw new Exception("Possui mais de 1 saida");

		this.caminho = new Pilha<Coordenada>(getQtdColunas() * getQtdLinhas());// 5 e 8 colunas 5* 8 = 40

		this.possibilidades = new Pilha<Pilha<Coordenada>>(getQtdColunas() * getQtdLinhas());

		while (getOndeEstou() != 'S') {

			preencherAdj();

			while (this.posAdj.isVazia()) {

				this.atual = caminho.recupereUmItem();

				this.caminho.removaUmItem();

				this.labirinto[this.atual.getLinha()][this.atual.getColuna()] = ' ';

				this.posAdj = this.possibilidades.recupereUmItem();

				this.possibilidades.removaUmItem();

			}

			this.atual = this.posAdj.recupereUmItem();

			if (getOndeEstou() != 'S') {

				this.posAdj.removaUmItem();

				this.labirinto[this.atual.getLinha()][this.atual.getColuna()] = '*';

				this.caminho.guardeUmItem(this.atual);

				this.possibilidades.guardeUmItem(this.posAdj); //

			}

		}

		System.out.println();

		percorrerInverso();

	}

	/**
	 * Este m�todo percorre a pilha de caminho e vai esvaziando ela e jogando todo
	 * conte�do na pilha de inverso para depois o usu�rio conseguir ver o caminho
	 * percorrido em ordem cron�logica perfeitamente
	 */

	private void percorrerInverso() throws Exception {

		inverso = new Pilha<Coordenada>(caminho.getQuantidade());

		while (!caminho.isVazia()) {

			inverso.guardeUmItem(caminho.recupereUmItem());

			caminho.removaUmItem();

		}

	}

	/**
	 * Este m�todo printar a resolu��o do labirinto em coordenadas ele resulta o
	 * caminho percorrido pelo usu�rio pelo usu�rio atravez de uma pilha de inverso
	 * 
	 * @return retorna para o usu�rio todo caminho percorrido do labirinto em
	 *         coordenadas
	 */

	public String printarResolucao() throws Exception {

		System.out.println("Estas s�o as Coordenada que levam � sa�da:");

		int i = 0;

		String str = "";

		while (!inverso.isVazia()) {
			i++;
			if (i < 23) {
				str = str + inverso.recupereUmItem() + "  ";
				System.out.print(" " + inverso.recupereUmItem());
				inverso.removaUmItem();
			} else {
				str = str + inverso.recupereUmItem() + "   \n";
				System.out.print(" " + inverso.recupereUmItem());
				inverso.removaUmItem();
				i = 0;
			}
		}

		System.out.println("\n");

		return str;

	}

	/**
	 * Este m�todo � aplicavel quando o usu�rio come�a ver as possibilidades que ele
	 * pode andar e come�ar guardar cada coordenada da posi��o atual dele na pilha
	 * de coordenadas para depois poder se locomover est� metodo instancia a
	 * coordenada adjascente atual da posi��o dele para pilha de adj para depois
	 * come�ar escolher as possibilidades para percorrer todo labirinto e resolvelo.
	 * 
	 */

	private void preencherAdj() throws Exception {

		this.posAdj = new Pilha<Coordenada>(3);

		Coordenada coorAdj;

		coorAdj = coordenadaDeCima(); // (1.2)

		if (coorAdj != null) {

			if (getCima() == ' ' || getCima() == 'S')// da pra ele anda

				this.posAdj.guardeUmItem(coorAdj);

		}

		coorAdj = coordenadaDebaixo();

		if (coorAdj != null && coorAdj.getLinha() < getQtdLinhas()) { // nao da

			if (getEmbaixo() == ' ' || getEmbaixo() == 'S')

				this.posAdj.guardeUmItem(coorAdj);

		}

		coorAdj = coordenadaDaDireita();

		if (coorAdj != null && coorAdj.getColuna() < getQtdColunas()) {// nap da

			if (getDireita() == ' ' || getDireita() == 'S')

				this.posAdj.guardeUmItem(coorAdj);

		}

		coorAdj = coordenadaDaEsquerda();

		if (coorAdj != null) {

			if (getEsquerda() == ' ' || getEsquerda() == 'S') // da pra andar

				this.posAdj.guardeUmItem(coorAdj);

		}

	}

	/**
	 * Este m�todo inst�ncia a posi��o da coordenada de cima ao qual ele esta no
	 * momento
	 * 
	 * @return o retorno deste m�todo e a coordenada da posi��o de cima do usu�rio
	 * @throws Exception caso de exe��o que a posi��o de cima seja inv�lida
	 *                   retornamos ela como o ret
	 */

	private Coordenada coordenadaDeCima() throws Exception {

		Coordenada ret = null;

		try {

			ret = new Coordenada(this.atual.getLinha() - 1, this.atual.getColuna());

		} catch (Exception erro) {

			return null;

		}

		return ret;

	}

	/**
	 * Este m�todo inst�ncia a posi��o da coordenada de baixo ao qual ele esta no
	 * momento
	 * 
	 * @return o retorno deste m�todo e a coordenada da posi��o da baixo do usu�rio
	 * @throws Exception caso de exe��o que a posi��o da direita seja inv�lida
	 *                   retornamos ela como o ret
	 */

	private Coordenada coordenadaDebaixo() throws Exception {

		Coordenada ret = null;

		try {

			ret = new Coordenada(this.atual.getLinha() + 1, this.atual.getColuna());

		} catch (Exception erro) {

			return ret;

		}

		return ret;

	}

	/**
	 * Este m�todo inst�ncia a posi��o da coordenada da direita ao qual ele esta no
	 * momento
	 * 
	 * @return o retorno deste m�todo e a coordenada da posi��o da direita do
	 *         usu�rio
	 * @throws Exception caso de exe��o que a posi��o da direita seja inv�lida
	 *                   retornamos ela como o ret
	 */

	private Coordenada coordenadaDaDireita() throws Exception {

		Coordenada ret = null;

		try {

			ret = new Coordenada(this.atual.getLinha(), this.atual.getColuna() + 1);

		} catch (Exception erro) {

			return ret;

		}

		return ret;

	}

	/**
	 * Este m�todo inst�ncia a posi��o da coordenada da esquerda ao qual ele esta no
	 * momento
	 * 
	 * @return o retorno deste m�todo e a coordenada da posi��o da esquerda do
	 *         usu�rio
	 * @throws Exception caso de exe��o que a posi��o da esquerda seja inv�lida
	 *                   retornamos ela como o ret
	 */

	private Coordenada coordenadaDaEsquerda() throws Exception {

		Coordenada ret = null;

		try {

			ret = new Coordenada(this.atual.getLinha(), this.atual.getColuna() - 1);

		} catch (Exception erro) {

			return ret;

		}

		return ret;

	}

	/**
	 * 
	 * Este m�todo verificar se o labirinto tem uma entrada e valida o n�mero de
	 * parede ao mesmo tempo primeiramente o m�todo encontrar entrada vai percorrer
	 * todo labirinto procurando um caractere q seja igual a E se encontrar iremos
	 * inst�nciar essa posi��o da matriz para classe coordenada segundamente
	 * validamos se a parede contem caracteres preenchendo ela caso contrario seria
	 * inv�lida
	 * 
	 * @return ele retorna a coordenada ao qual foi encontrado a entrada do
	 *         labirinto
	 * @throws Exception caso o labirinto percebea que contem uma parede sem
	 *                   caractereses a preenchendo lan�amos uma exe��o
	 */

	private Coordenada encontrarEntrada() throws Exception {

		Coordenada ret = null;

		int cont = 0;

		for (int i = 0; i < this.qtdLinhas; i++) {

			for (int j = 0; j < this.qtdColunas; j++) {

				if ((i == 0 || i == this.qtdLinhas - 1)) { // PAREDE DE CIMA E DE BAIXO

					if (this.labirinto[i][j] == 'E') {

						ret = new Coordenada(i, j);

						cont++;

					}

					if (this.labirinto[i][j] == ' ')

						throw new Exception("Sem parede");

				}

				if ((j == 0 || j == this.qtdColunas - 1)) { // PAREDE DA ESQUERDA E DA DIREITA

					if (this.labirinto[i][j] == 'E') {

						ret = new Coordenada(i, j); // (1,0)

						cont++;

					}

					if (this.labirinto[i][j] == ' ')

						throw new Exception("Sem parede");

				}

			}

		}

		if (cont > 1)

			throw new Exception("Mais de duas entradas");

		return ret;

	}

	/**
	 * Este m�todo � o m�todo clone ao qual se refere no nosso caso ao labirinto
	 * 
	 * @return retorna uma c�pia do labirinto chamante do m�todo
	 */

	public Object Clone() {

		Labirinto ret = null;

		try {

			ret = new Labirinto(this);

		} catch (Exception erro) {

		}

		return ret;

	}

	/**
	 * Este m�todo faz a valida��o do labirinto ele percorre cada posi��o do
	 * labirinto para verificar se o numero de colunas e linhas � valido � um metodo
	 * de valida��o para posteriormente a cria��o do labirinto no atributo this.
	 * 
	 * @param lab       no 1 parametro temos o arquivo do labirinto que usamos para
	 *                  fazer a valida��o
	 * @param qtdLinha  no 2 parametro temos o n�mero de linhas passado pelo
	 *                  construtor para come�ar a valida��o
	 * @param qtdColuna no 3 parametro temos o n�mero de colunas passado pelo
	 *                  construtor para come�ar a valida��o
	 * @return ele tem um retorno de booleano caso for valido retornara true caso
	 *         contrario retornara falso
	 */

	private boolean isLabirintoValido(Arquivo lab, int qtdLinha, int qtdColuna) throws Exception {

		String str;

		int coluna;

		for (int i = 2; i <= qtdLinha; i++) {

			str = lab.getUmString();

			coluna = str.length();

			if (qtdColuna != coluna) {

				return false;

			}

		}

		str = lab.getUmString();

		if (str != null) {

			return false;

		}

		return true;

	}

	/**
	 * Este m�todo gera uma matriz para ser atribuido ao this ao qual se refere o
	 * atributo do nosso labirinto
	 * 
	 * @param copia ele pega a copia do arquivo instanciado para o arquivo depois da
	 *              valida��o, ele constroi cada caractere com essa copia do txt
	 */

   private void  gerarMatriz(Arquivo copia) {

		try {

			String str = null;

			copia.getUmInt();

			this.labirinto = new char[this.qtdLinhas][this.qtdColunas];

			for (int i = 0; i < this.qtdLinhas; i++) {

				str = copia.getUmString();

				for (int j = 0; j < this.qtdColunas; j++) {
					str+=this.labirinto[i][j] = str.charAt(j);
				}

			}

		} catch (Exception erro) {

		}
  
	}

	@Override

	/**
	 * Gera uma representa��o visual do Labirinto produz e resulta uma String com o
	 * Labirinto
	 * 
	 * @return um Labirinto contendo o caminho percorrido
	 */

	public String toString() {

		String ret = "";

		if (this.labirinto == null)

			ret = "Labirinto inexistente";

		else {

			for (int i = 0; i < this.qtdLinhas; i++) {

				for (int j = 0; j < this.qtdColunas; j++) {

					ret += Character.toString(this.labirinto[i][j]);

				}

				ret += "\n";

			}

		}

		return ret;

	}

	/**
	 * Calcula o c�digo de espalhamento (ou c�digo de hash) de um Labirinto. Calcula
	 * e resulta o c�digo de espalhamento (ou c�digo de hash, ou ainda o hashcode)
	 * da agenda representada pela inst�ncia � qual o m�todo for aplicado.
	 * 
	 * @return o c�digo de espalhamento da agenda chamante do m�todo.
	 */

	@Override

	public int hashCode() {

		int ret = 7;

		ret = ret * 7 + new Integer(this.qtdLinhas).hashCode();

		ret = ret * 7 + new Integer(this.qtdColunas).hashCode();

		ret = ret * 7 + new Character(this.cima).hashCode();

		ret = ret * 7 + new Character(this.embaixo).hashCode();

		ret = ret * 7 + new Character(this.direita).hashCode();

		ret = ret * 7 + new Character(this.esquerda).hashCode();

		for (int i = 0; i < this.qtdLinhas; i++) {

			for (int j = 0; j < this.qtdColunas; j++)

				ret = ret * 7 + new Character(this.labirinto[i][j]).hashCode();

		}

		if (ret < 0)

			ret = ret - ret;

		return ret;

	}

	/**
	 * Verifica a igualdade entre dois Labirintos Verifica se o Object fornecido
	 * como par�metro representa um labirinto igual �quela representada pela
	 * inst�ncia � qual este m�todo for aplicado, resultando true em caso
	 * afirmativo, ou false, caso contr�rio
	 * 
	 * @param obj o objeto a ser comparado com a inst�ncia � qual esse m�todo for
	 *            aplicado.
	 * @return true, caso o Object fornecido ao m�todo e a inst�ncia chamante do
	 *         m�todo representarem labirintos iguais, ou false, caso contr�rio.
	 * 
	 */

	@Override

	public boolean equals(Object obj) {

		if (this == obj) // endereco

			return true;

		if (obj == null)

			return false;

		if (!(obj instanceof Labirinto))

			return false;

		Labirinto labi = (Labirinto) obj;

		if (this.qtdLinhas != labi.qtdLinhas) // 5 6

			return false;

		if (this.qtdColunas != labi.qtdColunas) // 7 6

			return false;

		for (int i = 0; i < this.qtdLinhas; i++) {

			for (int j = 0; j < this.qtdColunas; j++) {

				if (this.labirinto[i][j] != labi.labirinto[i][j])

					return false;

			}

		}

		return true;

	}

	/**
	 * Obtem o numero de colunas do Labirinto Resulta o n�mero de colunas do
	 * Labirinto
	 * 
	 * @return o n�mero de colunas do Labirinto
	 * @throws Exception quando o n�mero de colunas estiver vazio n�o foi atribuido
	 *                   ao this
	 * 
	 */

	public int getQtdColunas() throws Exception {

		try {

			return this.qtdColunas;

		} catch (Exception erro) {

			throw new Exception("Colunas inv�lidas");

		}

	}

	/**
	 * Obtem o numero de linhas do labirinto. Resulta o n�mero de linhas do
	 * labirinto
	 * 
	 * @return o num�ro de linhas do Labirinto @ throws Exception quando o n�mero de
	 * linhas estiver vazio n�o foi atribuido ao this.
	 */

	public int getQtdLinhas() throws Exception {

		try {

			return this.qtdLinhas;

		} catch (Exception erro) {

			throw new Exception("Linhas nulas");

		}

	}

	/**
	 * Obtem a linha de cima do labirinto Resulta a linha de cima do Labirinto
	 * 
	 * @return a linha de cima do labirinto @ throws Exception quando n�o tem linha
	 * em cima uma linha nula exemplo
	 */

	private char getCima() throws Exception {

		try {

			return this.labirinto[this.atual.getLinha() - 1][this.atual.getColuna()];

		} catch (Exception erro) {

			throw new Exception("Linha de cima inv�lida");

		}

	}

	/**
	 * Obt�m a linha de baixo do labirinto Resulta a linha de baixo do labirinto
	 * 
	 * @return a linha de baixo do labirinto @ throws Exception quando a linha de
	 * baixo � nula um exemplo
	 */

	private char getEmbaixo() throws Exception {

		try {

			return this.labirinto[this.atual.getLinha() + 1][this.atual.getColuna()];

		} catch (Exception erro) {

			throw new Exception("Linha de baixo inv�lida");

		}

	}

	/**
	 * Obt�m a coordenada da linha direita Resulta a posi��o da linha a direita do
	 * labirinto
	 * 
	 * @return a linha direita do labirinto
	 * @throws Exception quando n�o tem linhas da direita ou nula
	 */

	private char getDireita() throws Exception {

		try {

			return this.labirinto[this.atual.getLinha()][this.atual.getColuna() + 1];

		} catch (Exception erro) {

			throw new Exception("Linha direita inv�lida");

		}

	}

	/**
	 * Obtem a coordenada da linha esquerda do labirinto Resulta a posi��o da linha
	 * esquerda do labirinto
	 * 
	 * @return a coordenada da linha esquerda da posi��o atual
	 * @throws Exception quando a linha esquerda da posicao atual � nula
	 */

	private char getEsquerda() throws Exception {

		try {

			return this.labirinto[this.atual.getLinha()][this.atual.getColuna() - 1];

		} catch (Exception erro) {

			throw new Exception("linha esquerda nula");

		}

	}

	/**
	 * Obt�m a coordenada atual do labirinto Resulta a posi��o atual da pessoa no
	 * labirinto
	 * 
	 * @return a coordenada que a pessoa est� no momento atual
	 */

	private char getOndeEstou() {

		return this.labirinto[this.atual.getLinha()][this.atual.getColuna()];

	}

}

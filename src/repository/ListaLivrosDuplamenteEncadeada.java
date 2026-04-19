package repository;

// Importação das classes necessárias localizadas no pacote model.
// O 'Livro' representa os dados e o 'No' representa o elo da corrente.
import model.Livro;
import model.No;

/**
* Classe responsável pela implementação e gerenciamento da Lista Duplamente Encadeada.
* Ela atua como o repositório de dados do sistema, controlando como os livros são armazenados, organizados e acessados na memória.
*/

public class ListaLivrosDuplamenteEncadeada {

    // Atributos:

    // Referência para o primeiro nó da lista. É o ponto de partida para qualquer percurso.
    private No primeiro;
    // Referência para o último nó da lista. Permite inserções rápidas no final e percursos de trás para frente.
    private No ultimo;
    // Referência auxiliar que funciona como um "marcador de posição" atual. Essencial para a navegação na interface (Próximo/Anterior).
    private No atual;
    // Atributo inteiro que armazena a quantidade total de elementos (livros) na lista. Facilita o controle de limites e a exibição de informações (Ex: "Livro 1 de 10").
    private int totalLivros;

    /**
    * Construtor da classe.
    * Inicializa uma lista vazia, definindo os ponteiros como null e o tamanho como 0.
    */

    public ListaLivrosDuplamenteEncadeada() {
        this.primeiro = null;
        this.ultimo = null;
        this.atual = null;
        this.totalLivros = 0;
    }

    // Getters para o Swing:
    
    public Livro getAtual() {
        return (atual != null) ? atual.getLivro() : null;
    }

    public int getTotalLivros() {
        return totalLivros;
    }

    /**
    * Retorna o índice (posição base zero) do nó 'atual' na lista.
    * Retorna o índice do livro atual ou -1 se a lista estiver vazia ou nada selecionado.
    */

    public int getIndiceAtual() {
        // Se a lista está vazia ou o marcador atual não aponta para ninguém, o índice é inexistente.
        if (primeiro == null || atual == null) {
            return -1;
        }
        // O índice técnico começa em 0 (o 'primeiro' é o índice 0).
        int indice = 0;
        No aux = primeiro;
        // Percorre a lista a partir do início até encontrar o nó 'atual'.
        while (aux != null && aux != atual) {
            indice++;
            aux = aux.getProximo();
        }
        // Verifica se o ponteiro auxiliar encontrou o nó atual antes de chegar ao fim da lista.
        // Se o loop terminou porque aux == atual, retorna o contador.
        if (aux != null) {
            return indice;
        }
        // Caso contrário (segurança), se aux ficou nulo sem achar o atual, retorna -1.
        else {
            return -1;
        }
    }

    /**
    * Verifica se a lista está vazia para habilitar/desabilitar botões no Swing.
    */

    public boolean estaVazia() {
        return primeiro == null;
    }

    // Métodos de navegação (Essenciais para a interface Swing):

    // Avançar:

    /**
    * Move o marcador 'atual' para o próximo nó da lista e retorna o livro correspondente.
    * Caso o cursor já esteja no último nó ou a lista esteja vazia, retorna o livro atual.
    */

    public Livro avancar() {
        // Verifica se a lista está vazia.
        if (primeiro == null) {
            return null;
        }
        // Se o marcador atual for nulo, começamos pelo primeiro livro.
        if (atual == null) {
            atual = primeiro;
        }
        else {
            // Se houver um próximo nó (livro), acançamos o marcador atual.
            if (atual.getProximo() != null) {
                atual = atual.getProximo();
            }
        }
        return atual.getLivro();
    }

    // Voltar:

    /**
    * Move o marcador 'atual' para o nó anterior da lista e retorna o livro.
    * Caso o cursor já esteja no primeiro nó (inicio), ele permanece no primeiro livro.
    */

    public Livro voltar() {
        // Verifica se a lista está vazia.
        if (primeiro == null) {
            return null;
        }
        // Se o marcador atual for nulo, assume-se o início da lista.
        if (atual == null) {
            atual = primeiro;
        }
        else {
            // Se houver um nó (livro) anterior, movemos o marcador atual para trás (volta).
            if (atual.getAnterior() != null) {
                atual = atual.getAnterior();
            }
        }
        return atual.getLivro();
    }

    // Comparar por ano de publicação:

    /**
    * Compara dois nós com base no ano de publicação dos livros.
    * Útil para algoritmos de ordenação como o Bubble Sort.
    * Retorna true se o ano do livro 'a' for maior que o do livro 'b'.
    */

    private boolean compararPorAno(No a, No b) {
        // Usamos os getters para acessar os dados privados.
        return a.getLivro().getAnoPublicacao() > b.getLivro().getAnoPublicacao();
    }

    // Trocar com o próximo:

    /**
    * Realiza a troca de posição entre um nó e o seu próximo na lista.
    * Ajusta os ponteiros vizinhos para manter a integridade da lista dupla.
    * O nó que "subirá" uma posição na direção do fim da lista.
    * Retorna o nó que agora ocupa a posição original do 'node'.
    */

    private No trocarComProximo(No node) {
        // Armazena a referência do próximo nó para não perdê-la durante a troca.
        No proximoNo = node.getProximo();
        // Caso base: se não houver um próximo, a troca é impossível.
        if (proximoNo == null) {
            return node;
        }
        // 1. Ajuste dos vizinhos externos (quem está em volta do par).
        // Ajusta o vizinho da esquerda:
        // Se houver alguém antes do 'node', esse alguém agora apontará para o 'proximoNo'.
        if (node.getAnterior() != null) {
            node.getAnterior().setProximo(proximoNo);
        }
        // Se não houver ninguém antes, o 'node' era o início. Agora o 'proximoNo' vira o novo primeiro.
        else {
            primeiro = proximoNo;
        }
        // Ajusta o vizinho da direita:
        // Se houver alguém após o 'proximoNo', esse alguém agora terá o 'node' como seu anterior.
        if (proximoNo.getProximo() != null) {
            proximoNo.getProximo().setAnterior(node);
        }
        else {
            // Se não houver ninguém depois, o 'proximoNo' era o fim. Agora o 'node' vira o novo último.
            ultimo = node;
        }
        // 2. Troca dos ponteiros internos (a "dança" entre os dois nós).
        // O 'node' pula para a frente: o próximo dele passa a ser o que vinha depois do seu parceiro
        node.setProximo(proximoNo.getProximo());
        // O 'proximoNo' recua: o anterior dele passa a ser o que vinha antes do 'node'
        proximoNo.setAnterior(node.getAnterior());
        // Finaliza a conexão entre os dois: eles passam a apontar um para o outro na nova ordem
        proximoNo.setProximo(node);
        node.setAnterior(proximoNo);
        // Retorna o nó que agora ocupa a posição mais próxima do início da lista
        return proximoNo;
    }

    /**
    * Ordena a lista de livros por ano de publicação utilizando o algoritmo Bubble Sort.
    * O método percorre a lista repetidamente, comparando nós adjacentes e realizando a troca de posições quando o livro atual é mais recente que o próximo.
    * O processo se repete até que uma passagem completa ocorra sem nenhuma troca, garantindo que todos os elementos estejam em ordem crescente.
    */

    // Ordenar:

    public void ordenar() {
        if (primeiro == null) {
            return;
        }
        boolean trocado;
        do {
            No atualOrdenacao = primeiro;
            trocado = false;

            while (atualOrdenacao != null && atualOrdenacao.getProximo() != null) {
                if (compararPorAno(atualOrdenacao, atualOrdenacao.getProximo())) {
                    atualOrdenacao = trocarComProximo(atualOrdenacao);
                    trocado = true;
                }
                else {
                    atualOrdenacao = atualOrdenacao.getProximo();
                }
            }
        }
        while(trocado);
    }
}

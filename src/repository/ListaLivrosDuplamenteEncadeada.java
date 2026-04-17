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
    private No cabeca;
    // Referência para o último nó da lista. Permite inserções rápidas no final e percursos de trás para frente.
    private No cauda;
    // Referência auxiliar que funciona como um "marcador de posição" atual. Essencial para a navegação na interface (funções 'Próximo' e 'Anterior').
    private No cursor;
    // Atributo inteiro que armazena a quantidade total de elementos na lista. Facilita o controle de limites e a exibição de informações (Ex: "Livro 1 de 10").
    private int tamanho;

    /**
     * Construtor da classe.
     * Inicializa uma lista vazia, definindo os ponteiros como null e o tamanho como 0.
    */

    public ListaLivrosDuplamenteEncadeada() {
        this.cabeca = null;
        this.cauda = null;
        this.cursor = null;
        this.tamanho = 0;
    }

    // Métodos de navegação utilizando o cursor (Extra do projeto):

    /**
    * Move o cursor para o próximo nó da lista e retorna o livro correspondente.
    * Caso o cursor já esteja no último nó ou a lista esteja vazia, retorna o livro atual.
    */

    public Livro avancar() {
        // Verifica se a lista está vazia.
        if (cabeca == null) {
            return null;
        }
        // Se o cursor ainda não foi iniciado (primeiro clique), ele começa na cabeça.
        if (cursor == null) {
            cursor = cabeca;
        }
        else {
            // Se houver um próximo nó, movemos o cursor para frente.
            if (cursor.getProximo() != null) {
                cursor = cursor.getProximo();
            }
        }
        return cursor.getLivro();
    }

    /**
    * Move o cursor para o nó anterior da lista e retorna o livro correspondente.
    * Caso o cursor já esteja no primeiro nó, ele permanece nele.
    */

    public Livro voltar() {
        // Verifica se a lista está vazia.
        if (cabeca == null) {
            return null;
        }
        // Se o cursor for nulo, assume-se o início da lista por segurança.
        if (cursor == null) {
            cursor = cabeca;
        }
        else {
            // Se houver um nó anterior, movemos o cursor para trás.
            if (cursor.getAnterior() != null){
                cursor = cursor.getAnterior();
            }
        }
        return cursor.getLivro();
    }

    /**
    * Compara dois nós com base no ano de publicação dos livros.
    * Auxilia no algoritmo de ordenação.
    */

    private boolean comparar(No a, No b) {
        // Usamos os getters para acessar os dados privados.
        return a.getLivro().getAnoPublicacao() > b.getLivro().getAnoPublicacao();
    }

    /**
    * Realiza a troca de posição entre um nó e o seu próximo na lista.
    * Ajusta todos os 4 ponteiros envolvidos para manter a integridade da lista dupla.
    */

    private No trocar(No node) {
        No next = node.getProximo();
        // 1. Ajusta o link do nó que vem antes do par (o "vizinho da esquerda").
        if (node.getAnterior() != null) {
            node.getAnterior().setProximo(next);
        } else {
            // Se o 'node' era o primeiro, agora o 'next' vira a cabeça.
            cabeca = next;
        }
        // 2. Ajusta o link do nó que vem depois do par (o "vizinho da direita").
        if (next.getProximo() != null) {
            next.getProximo().setAnterior(node);
        }else{
            cauda = node;
        }
        // faz a troca
        node.proximo = next.proximo;
        next.anterior = node.anterior;

        next.proximo = node;
        node.anterior = next;

        return next;
    }
    public void ordenar(){
        if(cabeca == null) return;
        boolean trocado;

        do{
            No atual = cabeca;
            trocado = false;

            while(atual != null && atual.proximo != null){
                if(comparar(atual, atual.proximo)){
                    atual = trocar(atual);
                    trocado = true;
                }else{
                    atual = atual.proximo;
                }
            }
        }while(trocado);
    }
}

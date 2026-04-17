package model;

/**
 * Classe que representa um nó da lista duplamente encadeada.
 * Cada nó armazena um objeto Livro e referências para o nó anterior e para o próximo nó da lista.
 * Essa estrutura permite navegação bidirecional entre os elementos da lista.
*/

public class No {

    // Atributos:

    // Referência para o objeto Livro armazenado no nó.
    private Livro livro;
    // Referência para o próximo nó da lista.
    private No proximo;
    // Referência para o nó anterior da lista.
    private No anterior;

    /**
     * Construtor da classe No.
     * Inicializa o nó com um objeto Livro e define as referências anterior e próximo como null (ou seja, o nó começa isolado).
    */

    public No(Livro livro) {
        this.livro = livro;

        // Inicialmente o nó não está conectado a nenhum outro nó.
        this.proximo = null;
        this.anterior = null;
    }

    // Métodos getters e setters para acesso e modificação dos atributos do objeto No:

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public No getProximo() {
        return proximo;
    }

    public void setProximo(No proximo) {
        this.proximo = proximo;
    }

    public No getAnterior() {
        return anterior;
    }

    public void setAnterior(No anterior) {
        this.anterior = anterior;
    }
}

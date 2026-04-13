package model;

public class No {

    // Nó responsável por guardar as referências do livro atual,
    // do anterior e do próximo — base da lista duplamente encadeada

    public Livro livro;
    public No proximo;
    public No anterior;


    //construtor
    public No(Livro livro) {
        this.livro = livro;
        //O proximo e o anterior o java ja inicia como null(Ja que eles nao foram criados ainda), porém criei eles para facilitar a leitura
        this.proximo = null;
        this.anterior = null;
    }
    
}

package util;

import repository.ListaLivrosDuplamenteEncadeada;
import model.Livro;

public class GeradorLivrosTeste {
    public static ListaLivrosDuplamenteEncadeada gerarLista() {

        ListaLivrosDuplamenteEncadeada lista = new ListaLivrosDuplamenteEncadeada();

        lista.adicionarNoFim(new Livro("Dom Quixote", "Miguel de Cervantes", 1605, "Editora X", "ISBN1"));
        lista.adicionarNoFim(new Livro("1984", "George Orwell", 1949, "Companhia das Letras", "ISBN2"));
        lista.adicionarNoFim(new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", 1954, "HarperCollins", "ISBN3"));
        lista.adicionarNoFim(new Livro("Orgulho e Preconceito", "Jane Austen", 1813, "Penguin", "ISBN4"));
        lista.adicionarNoFim(new Livro("A Revolução dos Bichos", "George Orwell", 1945, "Companhia das Letras", "ISBN5"));

        return lista;
    }

}

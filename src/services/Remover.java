package services;

//imports necessarios
import datastructure.ListaBiblioteca;
import model.No;
import ui.TelaPrincipal;

public class Remover {
    //declaracao de objetos necessarios
    public String excluirPos(ListaBiblioteca lista, int posicao) {
    String msg = "";
    //chamando metodo para percorrer a lista
    No noTemp = getNo(lista, posicao);
    
    //verificando se valor inserido eh valido, caso sim, o no eh removido
    if (noTemp == null) {
        msg = "Posicao não existe";
    } else if (posicao == 0) {
        if (noTemp.proximo == null) {
            lista.cabeca = null;
            lista.cauda = null;
            msg = "posicao excluida: " + posicao + ", valor: " + noTemp.livro.getTitulo();
        } else {
            lista.cabeca = noTemp.proximo;
            lista.cabeca.anterior = null;
            msg = "posicao excluida: " + posicao + ", valor: " + noTemp.livro.getTitulo();
        }
    } else if (posicao == lista.tamanho - 1) {
        lista.cauda = noTemp.anterior;
        lista.cauda.proximo = null;
        msg = "posicao excluida: " + posicao + ", valor: " + noTemp.livro.getTitulo();
    } else {
        noTemp.anterior.proximo = noTemp.proximo;
        noTemp.proximo.anterior = noTemp.anterior;
        msg = "posicao excluida: " + posicao + ", valor: " + noTemp.livro.getTitulo();
    }
    //reduzindo tamanho da lista
    lista.tamanho--;
    return msg;
}

// percorrendo a lista
private No getNo(ListaBiblioteca lista, int posicao) {
    if (posicao < 0 || posicao >= lista.tamanho) {
        return null;
    }
    
    No atual = lista.cabeca;
    for (int i = 0; i < posicao; i++) {
        atual = atual.proximo;
    }
    return atual;
}
}

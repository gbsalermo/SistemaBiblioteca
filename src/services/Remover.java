package services;

//imports necessarios
import repository.ListaLivrosDuplamenteEncadeada;
import model.No;
import view.TelaPrincipal;

public class Remover {
    //declaracao de objetos necessarios
    public String excluirPos(ListaLivrosDuplamenteEncadeada lista, int posicao) {
    String msg = "";
    //chamando metodo para percorrer a lista
    No noTemp = getNo(lista, posicao);
    
    //verificando se valor inserido eh valido, caso sim, o no eh removido
    if (noTemp == null) {
        msg = "Posicao não existe";
    } else if (posicao == 0) {
        if ((noTemp.getProximo()) == null) {
            lista.setPrimeiro(null);
            lista.setUltimo(null);
            msg = "posicao excluida: " + posicao + ", valor: " + noTemp.getLivro().getTitulo();
        } else {
            lista.setPrimeiro(noTemp.getProximo());
            lista.getPrimeiro().setAnterior(null);
            msg = "posicao excluida: " + posicao + ", valor: " + noTemp.getLivro().getTitulo();
        }
    } else if (posicao == lista.getTotalLivros() - 1) {
        lista.setUltimo(noTemp.getAnterior());
        lista.getUltimo().setProximo(null);
        msg = "posicao excluida: " + posicao + ", valor: " + noTemp.getLivro().getTitulo();
    } else {
        noTemp.getAnterior().setProximo(noTemp.getProximo());
        (noTemp.getProximo()).setAnterior(noTemp.getAnterior());
        msg = "posicao excluida: " + posicao + ", valor: " + noTemp.getLivro().getTitulo();
    }
    //reduzindo totalLivros da lista
    lista.setTotalLivros(lista.getTotalLivros() - 1);
    return msg;
}

// percorrendo a lista
private No getNo(ListaLivrosDuplamenteEncadeada lista, int posicao) {
    if (posicao < 0 || posicao >= lista.getTotalLivros()) {
        return null;
    }
    
    No atual = lista.getPrimeiro();
    for (int i = 0; i < posicao; i++) {
        atual = atual.getProximo();
    }
    return atual;
}
}

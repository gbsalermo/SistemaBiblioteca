package services;

import repository.ListaLivrosDuplamenteEncadeada;
import model.No;
import view.TelaPrincipal;
import util.CsvExporter;

public class Remover {
    
    public String excluirPos(ListaLivrosDuplamenteEncadeada lista, int posicao) {
        String msg = "";
        No noTemp = getNo(lista, posicao);
        
        if (noTemp == null) {
            msg = "Posicao invalida ou inexistente";
            return msg;
        }
        
        //remover o primeiro no
        if (posicao == 0) {
            if (noTemp.getProximo() == null) {
                // Lista ficará vazia
                lista.setPrimeiro(null);
                lista.setUltimo(null);
                lista.setAtual(null);  //ATUALIZA O ATUAL
                msg = "livro removido: " + (posicao+1) + ", titulo: " + noTemp.getLivro().getTitulo();
            } else {
                // Remove o primeiro e o segundo vira o novo primeiro
                lista.setPrimeiro(noTemp.getProximo());
                lista.getPrimeiro().setAnterior(null);
                lista.setAtual(lista.getPrimeiro());  //ATUAL APONTA PARA O NOVO PRIMEIRO
                msg = "livro removido: " + (posicao+1) + ", titulo: " + noTemp.getLivro().getTitulo();
            }
        } 
        //remover o último no
        else if (posicao == lista.getTotalLivros() - 1) {
            lista.setUltimo(noTemp.getAnterior());
            lista.getUltimo().setProximo(null);
            lista.setAtual(lista.getUltimo());  //ATUAL APONTA PARA O NOVO ÚLTIMO
            msg = "livro removido: " + (posicao+1) + ", titulo: " + noTemp.getLivro().getTitulo();
        } 
        //remover do meio
        else {
            noTemp.getAnterior().setProximo(noTemp.getProximo());
            noTemp.getProximo().setAnterior(noTemp.getAnterior());
            lista.setAtual(noTemp.getProximo());  //ATUAL APONTA PARA O PRÓXIMO
            msg = "livro removido: " + (posicao+1) + ", titulo: " + noTemp.getLivro().getTitulo();
        }
        
        //Reduzindo totalLivros da lista
        lista.setTotalLivros(lista.getTotalLivros() - 1);
        CsvExporter.exportar(lista);
        return msg;
    }

    //percorrendo a lista
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
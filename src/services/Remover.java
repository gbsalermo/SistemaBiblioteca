package services;

//imports necessarios
import datastructure.ListaBiblioteca;
import model.No;

public class Remover {
    //metodo para exclusão de elemento
    public String excluir(ListaBiblioteca lista, int valor){
        //verificando se a lista está vazia
        if(lista.getCabeça()== null){
            return "A lista esta vazia!!";
        }
        //getter para percorrer a lista
        No atual = lista.getCabeça();
        //percorrer lista
        while(atual!= null){

        }

    }
}

package datastructure;

//Importo as outras classes
import model.Livro;
import model.No;


//Classe responsavel pelos metodos da lista encadeada
public class ListaBiblioteca {

    //atributos cabeca, calda e cursor

    private No cabeca;
    private No cauda;
    private No cursor;

    //Metodos restantes

    //metodos de proximo e anterior
    public Livro avancar(){

        // lista vazia
        if (cabeca == null) return null;

        //cursor não usado ainda
        if(cursor == null){
            cursor = cabeca;
            return cursor.livro;
        }
        
        //Quando o cursor tiver no ultimo

        if(cursor.proximo == null) return cursor.livro;

        //Avança normal
        cursor = cursor.proximo;
        return cursor.livro;
    }

     public Livro voltar(){

        //1 - lista vazia
        if (cabeca == null) return null;

        //cursor não usado ainda
        if(cursor == null){
            cursor = cabeca;
            return cursor.livro;
        }
        
        //Cursor ja ta no primeiro
        if(cursor.anterior == null) return cursor.livro;

        //Volta normal
        cursor = cursor.anterior;
        return cursor.livro;
    }
    private boolean comparar(No a, No b){
       return a.livro.getAnoPublicacao() > b.livro.getAnoPublicacao();
    }
    private No trocar(No node){
        No next = node.proximo;

        // verifica se e o primeiro elemento
        if(node.anterior != null){
            node.anterior.proximo = next;
        }else{
            cabeca = next;
        }
        // verifica se e o ultimo elemento
        if(next.proximo != null){
            next.proximo.anterior = node;
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

package datastructure;

//Importo as outras classes
import model.Livro;
import model.No;

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

}

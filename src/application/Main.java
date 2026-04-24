package application;

import javax.swing.SwingUtilities;

import repository.ListaLivrosDuplamenteEncadeada;
import util.GeradorLivrosTeste;
import view.TelaPrincipal;

public class Main {
    public static void main(String[] args) {
        // SwingUtilities.invokeLater garante que a interface gráfica 
        // seja iniciada na thread correta do Java (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            try {
                // Cria a instância da tela que orquestra os painéis e a lista
                //Teste do swing
                ListaLivrosDuplamenteEncadeada lista = GeradorLivrosTeste.gerarLista();
                new TelaPrincipal(lista);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

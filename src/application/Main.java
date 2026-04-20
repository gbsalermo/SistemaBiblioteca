package application;

import javax.swing.SwingUtilities;
import view.TelaPrincipal;

public class Main {
    public static void main(String[] args) {
        // SwingUtilities.invokeLater garante que a interface gráfica 
        // seja iniciada na thread correta do Java (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            try {
                // Cria a instância da tela que orquestra os painéis e a lista
                new TelaPrincipal();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

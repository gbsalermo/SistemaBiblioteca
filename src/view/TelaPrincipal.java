package view;

//Testando como vai ser a tela principal ainda, com comandos basicos de swing
//Até aqui é apenas uma janela vazia
import javax.swing.JFrame;

public class TelaPrincipal {

    public TelaPrincipal() {

        JFrame janela = new JFrame("Sistema de Biblioteca");

        janela.setSize(500, 400);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        janela.setVisible(true);
    }
}
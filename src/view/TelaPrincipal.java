package view;

//Testando como vai ser a tela principal ainda, com comandos basicos de swing
//Até aqui é apenas uma janela vazia
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class TelaPrincipal {

    //atributos
    private JFrame janela;
    private PainelSuperior painelSuperior;
    private PainelEsquerdo painelEsquerdo;
    private PainelDireito painelDireito;



    public TelaPrincipal() {

        // criação da janela e o titulo
        janela = new JFrame("Sistema de Biblioteca");
        //Tamanho da tela, largxalt em pixels
        janela.setSize(1000, 700);
        //comportamento para fechar a janela e centralizar janela
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);

        //Criando painel com BorderLayout
        JPanel painelPrincipal = new JPanel(new BorderLayout());

        //instanciando os paineis especificos
        painelSuperior = new PainelSuperior();
        painelDireito = new PainelDireito();
        painelEsquerdo = new PainelEsquerdo();
        
        //Adiciona cada painel em uma posição
        painelPrincipal.add(painelSuperior, BorderLayout.NORTH);
        painelPrincipal.add(painelDireito, BorderLayout.WEST);
        painelPrincipal.add(painelEsquerdo, BorderLayout.EAST);


        //Adicionando o painel principal na janela
        janela.add(painelPrincipal);

        //Mostra a janela na tela
        janela.setVisible(true);
    }
}
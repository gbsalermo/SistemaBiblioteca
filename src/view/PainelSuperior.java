package view;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/* 
* Classe responsavel pelo painel dos botoes e controles da parte superior
*/

public class PainelSuperior extends JPanel{

    //Atributos dos botoes - Inserir, remover, Buscar, Anterior, Proximo
    private JButton btInserir;
    private JButton btRemover;
    private JButton btBuscar;
    private JButton btAnterior;
    private JButton btProximo;
    private JComboBox<String> btOrdenar; //Botao para o usuario escolher como quer ordenar

    public PainelSuperior() {
        
        //O flowLayout coloca os componentes em linha, da esquerda para a direita
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        //Botões de ação
        
        btInserir = new JButton("INSERIR");
        btRemover = new JButton("REMOVER");
        btBuscar = new JButton("BUSCAR");
        btAnterior = new JButton("ANTERIOR");
        btProximo = new JButton("PROXIMO");

        String[] opcoes = {"ORDENAR POR TITULO", "ORDENAR POR AUTOR", "ORDERNAR POR ANO"}; //Crio uma lista de strings com as opções de ordenar
        btOrdenar = new JComboBox<>(opcoes); //atribuo ao botao de ordenar

        //Adicionando componentes ao painel

        this.add(btInserir);
        this.add(btRemover);
        this.add(btBuscar);
        this.add(new JLabel(" | ")); //Separador
        this.add(btAnterior);
        this.add(btProximo);
        this.add(new JLabel(" | ")); //Separador
        this.add(btOrdenar);
    
    }


    //Getters para acessar os botoes
    public JButton getBtInserir() {
        return btInserir;
    }

    public JButton getBtRemover() {
        return btRemover;
    }

    public JButton getBtBuscar() {
        return btBuscar;
    }

    public JButton getBtAnterior() {
        return btAnterior;
    }

    public JButton getBtProximo() {
        return btProximo;
    }

    public JComboBox<String> getBtOrdenar() {
        return btOrdenar;
    }



    
    


}

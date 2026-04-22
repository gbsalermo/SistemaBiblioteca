package view;

import java.awt.FlowLayout;
import javax.swing.*;
import repository.ListaLivrosDuplamenteEncadeada;

/**
* Classe responsavel pelo painel dos botoes e controles da parte superior.
*/

public class PainelSuperior extends JPanel{

    //Atributos dos botoes - Inserir, remover, Buscar, Anterior, Proximo:

    private JButton btInserir, btRemover, btBuscar, btAnterior, btProximo;
    // Para mostrar a posição atual e botão para o usuário escolher como quer ordenar.
    private JComboBox<String> cbOrdenar;
    // Para mostrar a posição atual.
    private JLabel lblContador;

    private TelaPrincipal tela;
    private ListaLivrosDuplamenteEncadeada lista;

    // Construtor atualizado para receber as referências
    public PainelSuperior(TelaPrincipal tela, ListaLivrosDuplamenteEncadeada lista) {
        this.tela = tela;
        this.lista = lista;
        
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        btInserir = new JButton("INSERIR");
        btRemover = new JButton("REMOVER");
        btBuscar = new JButton("BUSCAR");
        btAnterior = new JButton("ANTERIOR");
        btProximo = new JButton("PROXIMO");
        lblContador = new JLabel(" | Livros: 0/0");

        String[] opcoes = {"ORDENAR POR TITULO", "ORDENAR POR AUTOR", "ORDENAR POR ANO"};
        cbOrdenar = new JComboBox<>(opcoes);

        this.add(btInserir);
        this.add(btRemover);
        this.add(btBuscar);
        this.add(new JLabel(" | "));
        this.add(btAnterior);
        this.add(btProximo);
        this.add(lblContador);
        this.add(new JLabel(" | "));
        this.add(cbOrdenar);
        
        configurarEventos();
    }

    private void configurarEventos() {
        // Agora os botões do topo também controlam a lista!
        btProximo.addActionListener(e -> {
            lista.avancar();
            tela.atualizarInterface();
        });
        
        btAnterior.addActionListener(e -> {
            lista.voltar();
            tela.atualizarInterface();
        });

        btRemover.addActionListener(e -> {
            if (!lista.estaVazia()) {
                lista.removerAtual();
                tela.atualizarInterface();
            }
        });
    }

    public void atualizarContador(int atual, int total) {
        if (total == 0) {
            lblContador.setText(" | Lista Vazia");
        } else {
            lblContador.setText(" | Livro: " + (atual + 1) + " / " + total);
        }
    }
}

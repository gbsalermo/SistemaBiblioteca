package view;

import java.awt.FlowLayout;
import javax.swing.*;
import repository.ListaLivrosDuplamenteEncadeada;
import model.Livro;

/**
* Classe responsavel pelo painel dos botoes e controles da parte superior.
*/

public class PainelSuperior extends JPanel{

    //Atributos dos botoes - Inserir, remover, Buscar, Anterior, Proximo, Listar:

    private JButton btRemover, btBuscar, btAnterior, btProximo, btOrdenar, btListar;
    // Para mostrar a posição atual.
    private JLabel lblContador;

    private TelaPrincipal tela;
    private ListaLivrosDuplamenteEncadeada lista;

    // Construtor atualizado para receber as referências
    public PainelSuperior(TelaPrincipal tela, ListaLivrosDuplamenteEncadeada lista) {
        this.tela = tela;
        this.lista = lista;
        
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        btRemover = new JButton("REMOVER");
        btBuscar = new JButton("BUSCAR");
        btAnterior = new JButton("ANTERIOR");
        btProximo = new JButton("PROXIMO");
        lblContador = new JLabel(" | Livros: 0/0");
        btOrdenar = new JButton("ORDENAR");
        btListar = new JButton("LISTAR");

        this.add(btBuscar);
        this.add(btRemover);
        this.add(new JLabel(" | "));
        this.add(btAnterior);
        this.add(btProximo);
        this.add(lblContador);
        this.add(new JLabel(" | "));
        this.add(btOrdenar);
        this.add(btListar);
        
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

        btOrdenar.addActionListener(e -> {
            if (!lista.estaVazia()) {
                lista.ordenar();
                tela.atualizarInterface();
            }
        });
        
        btBuscar.addActionListener(e -> {
            if(!lista.estaVazia()){
                String titulo = JOptionPane.showInputDialog(null, "Digite o título a buscar:");
                if (titulo != null && !titulo.trim().isEmpty()){
                    Livro encontrado = lista.buscarPorTitulo(titulo.trim());
                    
                    if (encontrado != null){
                        tela.atualizarInterfaceBusca(encontrado);
                    }else {
                        JOptionPane.showMessageDialog(null, "Livro não encontrado.");
                    }
            }
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

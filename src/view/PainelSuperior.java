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
    private PainelEsquerdo painelEsquerdo;

    // Construtor atualizado para receber as referências
    public PainelSuperior(TelaPrincipal tela, ListaLivrosDuplamenteEncadeada lista, PainelEsquerdo painelEsquerdo) {
        this.painelEsquerdo = painelEsquerdo;
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
                String[] opcoes = {"Título", "Autor"};
                int tipo = JOptionPane.showOptionDialog(
                    null, "Buscar por:", "Tipo de Busca",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, opcoes, opcoes[0]
                );

                if (tipo == -1) return; //usuário fechou o dialog

                String termo = JOptionPane.showInputDialog(null, "Digite o " + opcoes[tipo]);
                if (termo != null && !termo.trim().isEmpty()){
                    tela.getPainelEsquerdo().filtrarTabela(termo.trim(), tipo);
                    
                    if (tela.getPainelEsquerdo().getTabelaLivros().getRowCount() == 0){
                        JOptionPane.showMessageDialog(null, "Livro não encontrado.");
                    }
                }
            }
        });

        btListar.addActionListener(e -> {
            if (!lista.estaVazia()) {
                lista.listarTodos();
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

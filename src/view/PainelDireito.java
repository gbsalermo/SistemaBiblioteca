package view;

import javax.swing.*;
import java.awt.*;
import repository.ListaLivrosDuplamenteEncadeada;
import model.Livro;

/**
* Painel de Detalhes e Ações.
* Este painel contém os campos para cadastro e os botões de controle da lista.
*/

public class PainelDireito extends JPanel {
    
    // Campos de Texto
    private JTextField txtTitulo, txtAutor, txtAno, txtGenero, txtEditora, txtPosicao;
    
    // Botões
    private JButton btnSalvarInicio, btnSalvarFim, btnSalvarPosicao;

    private TelaPrincipal tela;
    private ListaLivrosDuplamenteEncadeada lista;

    public PainelDireito(TelaPrincipal tela, ListaLivrosDuplamenteEncadeada lista) {
        this.tela = tela;
        this.lista = lista;
        
        // Layout de Grade: 12 linhas, 1 coluna (para labels, campos e botões)
        this.setLayout(new GridLayout(15, 1, 5, 5));
        this.setBorder(BorderFactory.createTitledBorder("Gerenciar Livro"));

        // Inicializando Componentes
        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtAno = new JTextField();
        txtGenero = new JTextField();
        txtEditora = new JTextField();
        txtPosicao = new JTextField("(opcional)");
        txtPosicao.setForeground(Color.GRAY);

        // Adicionando ao Painel
        add(new JLabel("Título:"));
        add(txtTitulo);
        add(new JLabel("Autor:"));
        add(txtAutor);
        add(new JLabel("Ano:"));
        add(txtAno);
        add(new JLabel("Gênero:"));
        add(txtGenero);
        add(new JLabel("Editora:"));
        add(txtEditora);
        add(new JLabel("Posição:"));
        add(txtPosicao);

        // Botão de Ação
        btnSalvarInicio = new JButton("Inserir no Início");
        btnSalvarFim = new JButton("Inserir no Fim");
        btnSalvarPosicao = new JButton("Inserir na Posição");
        add(btnSalvarInicio);
        add(btnSalvarFim);
        add(btnSalvarPosicao);

        configurarEventos();
    }

    private void configurarEventos() {
        // Ação: Salvar no Início
        btnSalvarInicio.addActionListener(e -> {
            try {
                String titulo = txtTitulo.getText();
                String autor = txtAutor.getText();
                int ano = Integer.parseInt(txtAno.getText());
                String genero = txtGenero.getText();
                String editora = txtEditora.getText();

                Livro novoLivro = new Livro(titulo, autor, ano, genero, editora);
                lista.adicionarNoInicio(novoLivro);
                
                limparCampos();
                tela.atualizarInterface();
                JOptionPane.showMessageDialog(this, "Livro salvo com sucesso!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: O campo Ano deve ser um número.");
            }
        });

        // Ação: Salvar no Fim
        btnSalvarFim.addActionListener(e -> {
            try {
                String titulo = txtTitulo.getText();
                String autor = txtAutor.getText();
                int ano = Integer.parseInt(txtAno.getText());
                String genero = txtGenero.getText();
                String editora = txtEditora.getText();

                Livro novoLivro = new Livro(titulo, autor, ano, genero, editora);
                lista.adicionarNoFim(novoLivro);
                
                limparCampos();
                tela.atualizarInterface();
                JOptionPane.showMessageDialog(this, "Livro salvo com sucesso!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: O campo Ano deve ser um número.");
            }
        });

        // Ação: Salvar na Posição
        btnSalvarPosicao.addActionListener(e -> {
            try {
                String titulo = txtTitulo.getText();
                String autor = txtAutor.getText();
                String genero = txtGenero.getText();
                String editora = txtEditora.getText();
                
                int ano;
                try {
                    ano = Integer.parseInt(txtAno.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Erro: O campo Ano deve ser um número.");
                    return;
                }
                String textoPosicao = txtPosicao.getText().trim();
                if (textoPosicao.isEmpty() || textoPosicao.equals("(opcional)")){
                    JOptionPane.showMessageDialog(this, "Erro: Informe a posição.");
                    return;
                }
                int posicao;
                try {
                    posicao = Integer.parseInt(textoPosicao);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Erro: O campo Posição deve ser um número.");
                    return;
                }

                Livro novoLivro = new Livro(titulo, autor, ano, genero, editora);
                lista.adicionarNaPosicao(novoLivro, posicao);
                
                limparCampos();
                tela.atualizarInterface();
                JOptionPane.showMessageDialog(this, "Livro salvo com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage());
            }
        });

        // Placeholder do campo posição
        txtPosicao.addFocusListener(new java.awt.event.FocusAdapter(){
            public void focusGained(java.awt.event.FocusEvent e){
                if (txtPosicao.getText().equals("(opcional)")){
                    txtPosicao.setText("");
                    txtPosicao.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent e){
                if (txtPosicao.getText().isEmpty()){
                    txtPosicao.setText("(opcional)");
                    txtPosicao.setForeground(Color.GRAY);
                }
            }
        });
    }

    // Método para a TelaPrincipal preencher os campos ao navegar
    public void exibirLivro(Livro livro) {
        if (livro != null) {
            txtTitulo.setText(livro.getTitulo());
            txtAutor.setText(livro.getAutor());
            txtAno.setText(String.valueOf(livro.getAnoPublicacao()));
            txtGenero.setText(livro.getGenero());
            txtEditora.setText(livro.getEditora());
        } else {
            limparCampos();
        }
        
    }

    private void limparCampos() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtAno.setText("");
        txtGenero.setText("");
        txtEditora.setText("");
    }
}
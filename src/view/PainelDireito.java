package view;

import javax.swing.*;
import java.awt.*;
import repository.ListaLivrosDuplamenteEncadeada;
import model.Livro;

/**
 * Painel responsável pelo cadastro e exibição dos dados do livro.
 * 
 * Este painel contém:
 * - Campos de texto para preenchimento dos dados;
 * - Botões para inserção em diferentes posições da lista;
 * - Eventos responsáveis pela validação e salvamento.
 */

public class PainelDireito extends JPanel {

    // ======================================================
    // CAMPOS DE TEXTO
    // ======================================================

    // Campos utilizados para entrada de dados do livro
    private JTextField txtTitulo, txtAutor, txtAno, txtGenero, txtEditora, txtPosicao;

    // ======================================================
    // BOTÕES
    // ======================================================

    // Botões responsáveis pelas diferentes formas de inserção
    private JButton btnSalvarInicio, btnSalvarFim, btnSalvarPosicao;

    // ======================================================
    // REFERÊNCIAS PRINCIPAIS
    // ======================================================

    // Referência da tela principal para atualização da interface
    private TelaPrincipal tela;

    // Referência da lista encadeada principal do sistema
    private ListaLivrosDuplamenteEncadeada lista;

    // Runnable utilizado para executar ações externas após salvar
    // Exemplo: fechar um modal automaticamente
    private Runnable aoSalvar;

    /**
     * Construtor principal do painel.
     * 
     * Recebe a tela principal e a lista principal do sistema.
     */
    public PainelDireito(TelaPrincipal tela, ListaLivrosDuplamenteEncadeada lista) {

        this.tela = tela;
        this.lista = lista;

        // ======================================================
        // CONFIGURAÇÃO VISUAL DO PAINEL
        // ======================================================

        /*
         * GridLayout:
         * 15 linhas
         * 1 coluna
         * Espaçamento horizontal: 5
         * Espaçamento vertical: 10
         */
        this.setLayout(new GridLayout(15, 1, 5, 10));

        // Adiciona uma borda com título ao painel
        this.setBorder(BorderFactory.createTitledBorder("Gerenciar Livro"));

        // ======================================================
        // INICIALIZAÇÃO DOS CAMPOS
        // ======================================================

        txtTitulo = new JTextField();
        txtTitulo.setPreferredSize(new Dimension(200, 30));

        txtAutor = new JTextField();
        txtAutor.setPreferredSize(new Dimension(200, 30));

        txtAno = new JTextField();
        txtAno.setPreferredSize(new Dimension(200, 30));

        txtGenero = new JTextField();
        txtGenero.setPreferredSize(new Dimension(200, 30));

        txtEditora = new JTextField();
        txtEditora.setPreferredSize(new Dimension(200, 30));

        /*
         * Campo de posição:
         * utilizado quando o usuário deseja inserir
         * o livro em uma posição específica da lista.
         */
        txtPosicao = new JTextField("(opcional)");
        txtPosicao.setPreferredSize(new Dimension(200, 30));

        // Cor cinza para simular placeholder
        txtPosicao.setForeground(Color.GRAY);

        // ======================================================
        // ADIÇÃO DOS COMPONENTES NA TELA
        // ======================================================

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

        // ======================================================
        // BOTÕES DE INSERÇÃO
        // ======================================================

        btnSalvarInicio = new JButton("Inserir no Início");

        btnSalvarFim = new JButton("Inserir no Fim");

        btnSalvarPosicao = new JButton("Inserir na Posição");

        add(btnSalvarInicio);
        add(btnSalvarFim);
        add(btnSalvarPosicao);

        // Registra todos os eventos do painel
        configurarEventos();
    }

    /**
     * Método responsável por configurar todos os eventos
     * relacionados aos botões e campos do painel.
     */
    private void configurarEventos() {

        // ======================================================
        // EVENTO - INSERIR NO INÍCIO
        // ======================================================

        btnSalvarInicio.addActionListener(e -> {

            try {

                // Captura os dados digitados nos campos
                String titulo = txtTitulo.getText();
                String autor = txtAutor.getText();

                // Conversão do campo ano para inteiro
                int ano = Integer.parseInt(txtAno.getText());

                String genero = txtGenero.getText();
                String editora = txtEditora.getText();

                // Cria um novo objeto Livro
                Livro novoLivro = new Livro(
                        titulo,
                        autor,
                        ano,
                        genero,
                        editora);

                // Adiciona no início da lista
                lista.adicionarNoInicio(novoLivro);

                // Limpa os campos após salvar
                limparCampos();

                // Atualiza a interface principal
                tela.atualizarInterface();

                // Executa ação externa caso exista
                if (aoSalvar != null)
                    aoSalvar.run();

                // Exibe mensagem de sucesso
                JOptionPane.showMessageDialog(
                        this,
                        "Livro salvo com sucesso!");

            } catch (NumberFormatException ex) {

                // Erro caso o campo ano não seja numérico
                JOptionPane.showMessageDialog(
                        this,
                        "Erro: O campo Ano deve ser um número.");
            }
        });

        // ======================================================
        // EVENTO - INSERIR NO FIM
        // ======================================================

        btnSalvarFim.addActionListener(e -> {

            try {

                String titulo = txtTitulo.getText();
                String autor = txtAutor.getText();

                int ano = Integer.parseInt(txtAno.getText());

                String genero = txtGenero.getText();
                String editora = txtEditora.getText();

                Livro novoLivro = new Livro(
                        titulo,
                        autor,
                        ano,
                        genero,
                        editora);

                // Adiciona no final da lista
                lista.adicionarNoFim(novoLivro);

                limparCampos();

                tela.atualizarInterface();

                if (aoSalvar != null)
                    aoSalvar.run();

                JOptionPane.showMessageDialog(
                        this,
                        "Livro salvo com sucesso!");

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Erro: O campo Ano deve ser um número.");
            }
        });

        // ======================================================
        // EVENTO - INSERIR EM POSIÇÃO ESPECÍFICA
        // ======================================================

        btnSalvarPosicao.addActionListener(e -> {

            try {

                String titulo = txtTitulo.getText();
                String autor = txtAutor.getText();
                String genero = txtGenero.getText();
                String editora = txtEditora.getText();

                int ano;

                // ======================================================
                // VALIDAÇÃO DO CAMPO ANO
                // ======================================================

                try {

                    ano = Integer.parseInt(txtAno.getText());

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Erro: O campo Ano deve ser um número.");

                    return;
                }

                // ======================================================
                // VALIDAÇÃO DO CAMPO POSIÇÃO
                // ======================================================

                String textoPosicao = txtPosicao.getText().trim();

                // Verifica se o campo está vazio
                // ou ainda contém o placeholder
                if (textoPosicao.isEmpty() ||
                        textoPosicao.equals("(opcional)")) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Erro: Informe a posição.");

                    return;
                }

                int posicao;

                try {

                    posicao = Integer.parseInt(textoPosicao);

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Erro: O campo Posição deve ser um número.");

                    return;
                }

                // ======================================================
                // CRIAÇÃO E INSERÇÃO DO LIVRO
                // ======================================================

                Livro novoLivro = new Livro(
                        titulo,
                        autor,
                        ano,
                        genero,
                        editora);

                lista.adicionarNaPosicao(novoLivro, posicao);

                limparCampos();

                tela.atualizarInterface();

                if (aoSalvar != null)
                    aoSalvar.run();

                JOptionPane.showMessageDialog(
                        this,
                        "Livro salvo com sucesso!");

            } catch (Exception ex) {

                // Captura erros inesperados
                JOptionPane.showMessageDialog(
                        this,
                        "Erro inesperado: " + ex.getMessage());
            }
        });

        // ======================================================
        // PLACEHOLDER DO CAMPO POSIÇÃO
        // ======================================================

        /*
         * Simula um placeholder:
         * quando o usuário clica no campo,
         * o texto desaparece.
         * 
         * Quando o campo perde foco e está vazio,
         * o placeholder reaparece.
         */

        txtPosicao.addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(java.awt.event.FocusEvent e) {

                if (txtPosicao.getText().equals("(opcional)")) {

                    txtPosicao.setText("");

                    txtPosicao.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {

                if (txtPosicao.getText().isEmpty()) {

                    txtPosicao.setText("(opcional)");

                    txtPosicao.setForeground(Color.GRAY);
                }
            }
        });
    }

    /**
     * Exibe os dados de um livro nos campos do formulário.
     * 
     * Utilizado principalmente durante navegação
     * e edição de livros existentes.
     */
    public void exibirLivro(Livro livro) {

        if (livro != null) {

            txtTitulo.setText(livro.getTitulo());

            txtAutor.setText(livro.getAutor());

            txtAno.setText(
                    String.valueOf(livro.getAnoPublicacao()));

            txtGenero.setText(livro.getGenero());

            txtEditora.setText(livro.getEditora());

        } else {

            // Caso não exista livro selecionado,
            // limpa todos os campos
            limparCampos();
        }
    }

    /**
     * Limpa todos os campos do formulário.
     */
    private void limparCampos() {

        txtTitulo.setText("");

        txtAutor.setText("");

        txtAno.setText("");

        txtGenero.setText("");

        txtEditora.setText("");
    }

    /**
     * Define uma ação personalizada
     * a ser executada após salvar.
     */
    public void setAoSalvar(Runnable aoSalvar) {

        this.aoSalvar = aoSalvar;
    }
}
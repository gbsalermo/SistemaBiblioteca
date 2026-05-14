package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

import model.Livro;
import repository.ListaLivrosDuplamenteEncadeada;
import util.DataLivro;

/**
 * Tela modal para visualizar e editar informações do livro.
 * Esta janela aparece como pop-up (modal).
 * Possui suporte para:
 * - Navegação entre livros
 * - Empréstimo
 * - Devolução
 * - Edição dos dados
 */
public class TelaModalLivro extends JDialog {

    // Livro atualmente exibido
    private Livro livro;

    // Indica se houve alteração/salvamento
    private boolean salvou = false;

    // Lista usada para navegação
    private ListaLivrosDuplamenteEncadeada lista;

    // Índice atual do livro na navegação
    private int indiceAtual;

    // Campos de texto
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtAno;
    private JTextField txtGenero;
    private JTextField txtEditora;

    // Label do ID
    private JLabel lblId;

    // Botões de navegação
    private JButton btnProximo;
    private JButton btnAnterior;

    // Label que mostra posição atual
    private JLabel lblNavegacao;

    // Painel inferior de botões
    private JPanel painelBotoes;

    /**
     * Construtor original.
     * Mantido para compatibilidade com versões antigas.
     */
    public TelaModalLivro(JFrame parent, Livro livro) {
        this(parent, livro, null, 0);
    }

    /**
     * Construtor principal com suporte a navegação.
     */
    public TelaModalLivro(JFrame parent,
            Livro livro,
            ListaLivrosDuplamenteEncadeada lista,
            int indiceAtual) {

        // Cria dialog modal
        super(parent, "Detalhes do Livro", true);

        this.livro = livro;
        this.lista = lista;
        this.indiceAtual = indiceAtual;

        // Configurações básicas da janela
        setSize(500, 420);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Cria os painéis da interface
        JPanel painelCampos = criarPainelCampos();
        JPanel painelNavegacao = criarPainelNavegacao();

        // Cria painel de botões
        painelBotoes = criarPainelBotoes();

        // Adiciona os painéis na janela
        add(painelNavegacao, BorderLayout.NORTH);
        add(painelCampos, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // Preenche os dados do livro
        preencherCampos();

        // Atualiza label da navegação
        atualizarLabelNavegacao();
    }

    /**
     * Cria o painel superior de navegação.
     */
    private JPanel criarPainelNavegacao() {

        JPanel panel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Margens internas
        panel.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Botões
        btnAnterior = new JButton("⬅️ ANTERIOR");
        btnProximo = new JButton("PRÓXIMO ➡️");

        // Label da navegação
        lblNavegacao = new JLabel("Livro 1/1");

        // Verifica se existe mais de um livro
        boolean podeNavegar =
                lista != null && lista.getTotalLivros() > 1;

        // Habilita/desabilita botões
        btnAnterior.setEnabled(podeNavegar);
        btnProximo.setEnabled(podeNavegar);

        // Eventos dos botões
        btnProximo.addActionListener(e -> navegarProximo());
        btnAnterior.addActionListener(e -> navegarAnterior());

        // Adiciona componentes
        panel.add(btnAnterior);
        panel.add(lblNavegacao);
        panel.add(btnProximo);

        return panel;
    }

    /**
     * Cria o painel central com os campos do livro.
     */
    private JPanel criarPainelCampos() {

        JPanel panel = new JPanel(
                new GridLayout(6, 2, 10, 10));

        // Espaçamento interno
        panel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ID
        panel.add(new JLabel("ID:"));
        lblId = new JLabel();
        panel.add(lblId);

        // Título
        panel.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panel.add(txtTitulo);

        // Autor
        panel.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        panel.add(txtAutor);

        // Ano
        panel.add(new JLabel("Ano de Publicação:"));
        txtAno = new JTextField();
        panel.add(txtAno);

        // Gênero
        panel.add(new JLabel("Gênero:"));
        txtGenero = new JTextField();
        panel.add(txtGenero);

        // Editora
        panel.add(new JLabel("Editora:"));
        txtEditora = new JTextField();
        panel.add(txtEditora);

        // Preenche ID caso exista livro
        if (livro != null) {
            lblId.setText(String.valueOf(livro.getId()));
        }

        return panel;
    }

    /**
     * Cria o painel inferior de botões.
     * O botão muda dependendo do estado do livro.
     */
    private JPanel criarPainelBotoes() {

        JPanel panel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Botões fixos
        JButton btnSalvar = new JButton("💾 Salvar");
        JButton btnCancelar = new JButton("❌ Cancelar");

        // Eventos
        btnSalvar.addActionListener(e -> salvarAlteracoes());
        btnCancelar.addActionListener(e -> dispose());

        // Adiciona botão salvar
        panel.add(btnSalvar);

        /**
         * Se o livro estiver emprestado:
         * mostra botão DEVOLVER
         */
        if (livro != null && livro.isEmprestado()) {

            JButton btnDevolver = new JButton("📥 Devolver");

            btnDevolver.addActionListener(e -> devolverLivro());

            panel.add(btnDevolver);

        } else {

            /**
             * Caso contrário:
             * mostra botão EMPRESTAR
             */
            JButton btnEmprestar = new JButton("📤 Emprestar");

            btnEmprestar.addActionListener(e -> emprestarLivro());

            panel.add(btnEmprestar);
        }

        // Botão cancelar
        panel.add(btnCancelar);

        return panel;
    }

    /**
     * Atualiza o painel de botões.
     * Necessário ao alternar entre emprestado/devolvido.
     */
    private void atualizarPainelBotoes() {

        // Remove painel antigo
        remove(painelBotoes);

        // Cria novo painel atualizado
        painelBotoes = criarPainelBotoes();

        // Adiciona novamente
        add(painelBotoes, BorderLayout.SOUTH);

        // Atualiza interface
        revalidate();
        repaint();
    }

    /**
     * Preenche os campos da interface
     * com os dados do livro atual.
     */
    private void preencherCampos() {

        if (livro != null) {

            lblId.setText(
                    String.valueOf(livro.getId()));

            txtTitulo.setText(
                    livro.getTitulo());

            txtAutor.setText(
                    livro.getAutor());

            txtAno.setText(
                    String.valueOf(livro.getAnoPublicacao()));

            txtGenero.setText(
                    livro.getGenero());

            txtEditora.setText(
                    livro.getEditora());
        }
    }

    /**
     * Navega para o próximo livro.
     */
    private void navegarProximo() {

        // Verifica se existe lista válida
        if (lista == null || lista.getTotalLivros() == 0) {
            return;
        }

        // Tenta salvar alterações antes de trocar
        tentarSalvar();

        // Verifica se não está no último
        if (indiceAtual < lista.getTotalLivros() - 1) {

            // Avança lista
            lista.avancar();

            // Atualiza índice
            indiceAtual++;

            // Atualiza livro atual
            livro = lista.getAtual();

            // Atualiza interface
            preencherCampos();
            atualizarPainelBotoes();
            atualizarLabelNavegacao();
        }
    }

    /**
     * Navega para o livro anterior.
     */
    private void navegarAnterior() {

        // Verifica lista válida
        if (lista == null || lista.getTotalLivros() == 0) {
            return;
        }

        // Tenta salvar alterações
        tentarSalvar();

        // Verifica se não está no primeiro
        if (indiceAtual > 0) {

            // Volta lista
            lista.voltar();

            // Atualiza índice
            indiceAtual--;

            // Atualiza livro atual
            livro = lista.getAtual();

            // Atualiza interface
            preencherCampos();
            atualizarPainelBotoes();
            atualizarLabelNavegacao();
        }
    }

    /**
     * Atualiza o label da navegação.
     */
    private void atualizarLabelNavegacao() {

        if (lista != null && lista.getTotalLivros() > 0) {

            lblNavegacao.setText(
                    "Livro "
                            + (indiceAtual + 1)
                            + " / "
                            + lista.getTotalLivros());
        }
    }

    /**
     * Salva alterações sem fechar a janela.
     * Usado durante a navegação.
     */
    private void tentarSalvar() {

        try {

            if (livro != null) {

                livro.setTitulo(txtTitulo.getText());
                livro.setAutor(txtAutor.getText());

                livro.setAnoPublicacao(
                        Integer.parseInt(txtAno.getText()));

                livro.setGenero(txtGenero.getText());
                livro.setEditora(txtEditora.getText());

                salvou = true;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "❌ Erro: Ano deve ser um número válido!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Salva alterações e fecha a janela.
     */
    private void salvarAlteracoes() {

        try {

            livro.setTitulo(txtTitulo.getText());
            livro.setAutor(txtAutor.getText());

            livro.setAnoPublicacao(
                    Integer.parseInt(txtAno.getText()));

            livro.setGenero(txtGenero.getText());
            livro.setEditora(txtEditora.getText());

            salvou = true;

            JOptionPane.showMessageDialog(
                    this,
                    "✅ Livro atualizado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "❌ Erro: Ano deve ser um número válido!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Marca o livro como emprestado.
     */
    private void emprestarLivro() {

        if (livro != null) {

            // Marca como emprestado
            livro.setEmprestado(true);

            // Obtém data atual
            LocalDate dataAtual =
                    DataLivro.obterDataAtual();

            // Salva data do empréstimo
            livro.setDataEmprestimo(dataAtual);

            // Formata data
            String dataFormatada =
                    DataLivro.formatarData(dataAtual);

            // Exibe mensagem
            JOptionPane.showMessageDialog(
                    this,
                    "✅ Livro emprestado com sucesso!\n\n"
                            + "Título: " + livro.getTitulo() + "\n"
                            + "Data do empréstimo: " + dataFormatada,
                    "Empréstimo Registrado",
                    JOptionPane.INFORMATION_MESSAGE);

            salvou = true;

            // Fecha modal
            dispose();
        }
    }

    /**
     * Marca o livro como devolvido.
     */
    private void devolverLivro() {

        // Verifica se existe livro e se está emprestado
        if (livro != null && livro.isEmprestado()) {

            // Calcula quantidade de dias emprestado
            long diasEmprestado =
                    DataLivro.diasDesdeEmprestimo(
                            livro.getDataEmprestimo());

            // Remove status de empréstimo
            livro.setEmprestado(false);

            // Remove data
            livro.setDataEmprestimo(null);

            // Exibe mensagem
            JOptionPane.showMessageDialog(
                    this,
                    "✅ Livro devolvido com sucesso!\n\n"
                            + "Título: " + livro.getTitulo() + "\n"
                            + "Tempo emprestado: "
                            + diasEmprestado + " dias",
                    "Devolução Registrada",
                    JOptionPane.INFORMATION_MESSAGE);

            salvou = true;

            // Fecha modal
            dispose();
        }
    }

    /**
     * Retorna se houve alteração/salvamento.
     */
    public boolean salvou() {
        return salvou;
    }
}
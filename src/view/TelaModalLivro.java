package view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import model.Livro;
import repository.ListaLivrosDuplamenteEncadeada;
import util.DataLivro;

/**
 * Tela modal para visualizar e editar informações do livro
 * Esta janela aparece como pop-up (modal)
 * Agora com suporte a navegação entre livros e empréstimos
 */
public class TelaModalLivro extends JDialog {

    private Livro livro;
    private boolean salvou = false;
    private ListaLivrosDuplamenteEncadeada lista;
    private int indiceAtual;

    // Campos de texto
    private JTextField txtTitulo, txtAutor, txtAno, txtGenero, txtEditora;
    private JLabel lblId;

    // Botões de navegação
    private JButton btnProximo, btnAnterior;
    private JLabel lblNavegacao;
    private JPanel painelBotoes;

    /**
     * Construtor original (compatibilidade com código antigo)
     */
    public TelaModalLivro(JFrame parent, Livro livro) {
        this(parent, livro, null, 0);
    }

    /**
     * Construtor com navegação
     */
    public TelaModalLivro(JFrame parent, Livro livro, ListaLivrosDuplamenteEncadeada lista, int indiceAtual) {
        super(parent, "Detalhes do Livro", true);
        this.livro = livro;
        this.lista = lista;
        this.indiceAtual = indiceAtual;

        setSize(500, 420);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // ✅ ORDEM CORRETA: Criar painelCampos PRIMEIRO
        JPanel painelCampos = criarPainelCampos();
        JPanel painelNavegacao = criarPainelNavegacao();
        painelBotoes = criarPainelBotoes();

        add(painelNavegacao, BorderLayout.NORTH);
        add(painelCampos, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        preencherCampos();
        atualizarLabelNavegacao();
    }

    /**
     * Cria o painel de navegação
     */
    private JPanel criarPainelNavegacao() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnAnterior = new JButton("⬅️ ANTERIOR");
        lblNavegacao = new JLabel("Livro 1/1");
        btnProximo = new JButton("PRÓXIMO ➡️");

        boolean podeNavegar = lista != null && lista.getTotalLivros() > 1;
        btnAnterior.setEnabled(podeNavegar);
        btnProximo.setEnabled(podeNavegar);

        btnProximo.addActionListener(e -> navegarProximo());
        btnAnterior.addActionListener(e -> navegarAnterior());

        panel.add(btnAnterior);
        panel.add(lblNavegacao);
        panel.add(btnProximo);

        return panel;
    }

    private JPanel criarPainelCampos() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("ID:"));
        lblId = new JLabel();
        panel.add(lblId);

        panel.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panel.add(txtTitulo);

        panel.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        panel.add(txtAutor);

        panel.add(new JLabel("Ano de Publicação:"));
        txtAno = new JTextField();
        panel.add(txtAno);

        panel.add(new JLabel("Gênero:"));
        txtGenero = new JTextField();
        panel.add(txtGenero);

        panel.add(new JLabel("Editora:"));
        txtEditora = new JTextField();
        panel.add(txtEditora);

        if (livro != null) {
            lblId.setText(String.valueOf(livro.getId()));
        }

        return panel;
    }

    /**
     * Cria o painel de botões - MUDA CONFORME ESTADO DO LIVRO
     */
    private JPanel criarPainelBotoes() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnSalvar = new JButton("💾 Salvar");
        JButton btnCancelar = new JButton("❌ Cancelar");

        btnSalvar.addActionListener(e -> salvarAlteracoes());
        btnCancelar.addActionListener(e -> dispose());

        panel.add(btnSalvar);

        // ✅ MUDA CONFORME O LIVRO ESTÁ EMPRESTADO OU NÃO
        if (livro != null && livro.isEmprestado()) {
            // Livro está emprestado → mostra DEVOLVER
            JButton btnDevolver = new JButton("📥 Devolver");
            btnDevolver.addActionListener(e -> devolverLivro());
            panel.add(btnDevolver);
        } else {
            // Livro está disponível → mostra EMPRESTAR
            JButton btnEmprestar = new JButton("📤 Emprestar");
            btnEmprestar.addActionListener(e -> emprestarLivro());
            panel.add(btnEmprestar);
        }

        panel.add(btnCancelar);

        return panel;
    }

    private void atualizarPainelBotoes() {

        remove(painelBotoes);

        painelBotoes = criarPainelBotoes();

        add(painelBotoes, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void preencherCampos() {
        if (livro != null) {
            lblId.setText(String.valueOf(livro.getId()));
            txtTitulo.setText(livro.getTitulo());
            txtAutor.setText(livro.getAutor());
            txtAno.setText(String.valueOf(livro.getAnoPublicacao()));
            txtGenero.setText(livro.getGenero());
            txtEditora.setText(livro.getEditora());
        }
    }

    /**
     * Navega para o próximo livro
     */
    private void navegarProximo() {
        if (lista == null || lista.getTotalLivros() == 0) {
            return;
        }

        tentarSalvar();

        if (indiceAtual < lista.getTotalLivros() - 1) {
            lista.avancar();
            indiceAtual++;
            livro = lista.getAtual();
            preencherCampos();
            atualizarPainelBotoes();
            atualizarLabelNavegacao();
        }
    }

    /**
     * Navega para o livro anterior
     */
    private void navegarAnterior() {
        if (lista == null || lista.getTotalLivros() == 0) {
            return;
        }

        tentarSalvar();

        if (indiceAtual > 0) {
            lista.voltar();
            indiceAtual--;
            livro = lista.getAtual();
            preencherCampos();
            atualizarPainelBotoes();
            atualizarLabelNavegacao();
        }
    }

    /**
     * Atualiza o label de navegação
     */
    private void atualizarLabelNavegacao() {
        if (lista != null && lista.getTotalLivros() > 0) {
            lblNavegacao.setText("Livro " + (indiceAtual + 1) + " / " + lista.getTotalLivros());
        }
    }

    /**
     * Tenta salvar alterações sem mostrar mensagem
     */
    private void tentarSalvar() {
        try {
            if (livro != null) {
                livro.setTitulo(txtTitulo.getText());
                livro.setAutor(txtAutor.getText());
                livro.setAnoPublicacao(Integer.parseInt(txtAno.getText()));
                livro.setGenero(txtGenero.getText());
                livro.setEditora(txtEditora.getText());
                salvou = true;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "❌ Erro: Ano deve ser um número válido!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Salva as alterações e fecha a janela
     */
    private void salvarAlteracoes() {
        try {
            livro.setTitulo(txtTitulo.getText());
            livro.setAutor(txtAutor.getText());
            livro.setAnoPublicacao(Integer.parseInt(txtAno.getText()));
            livro.setGenero(txtGenero.getText());
            livro.setEditora(txtEditora.getText());

            salvou = true;
            JOptionPane.showMessageDialog(this,
                    "✅ Livro atualizado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "❌ Erro: Ano deve ser um número válido!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Marca o livro como emprestado
     */
    private void emprestarLivro() {
        if (livro != null) {
            livro.setEmprestado(true);

            LocalDate dataAtual = DataLivro.obterDataAtual();
            livro.setDataEmprestimo(dataAtual);

            String dataFormatada = DataLivro.formatarData(dataAtual);

            JOptionPane.showMessageDialog(
                    this,
                    "✅ Livro emprestado com sucesso!\n\n" +
                            "Título: " + livro.getTitulo() + "\n" +
                            "Data do empréstimo: " + dataFormatada,
                    "Empréstimo Registrado",
                    JOptionPane.INFORMATION_MESSAGE);

            salvou = true;
            dispose();
        }
    }

    /**
     * Marca o livro como devolvido
     */
    private void devolverLivro() {
        if (livro != null && livro.isEmprestado()) {
            // Calcula quantos dias estava emprestado
            long diasEmprestado = DataLivro.diasDesdeEmprestimo(livro.getDataEmprestimo());

            livro.setEmprestado(false);
            livro.setDataEmprestimo(null);

            JOptionPane.showMessageDialog(
                    this,
                    "✅ Livro devolvido com sucesso!\n\n" +
                            "Título: " + livro.getTitulo() + "\n" +
                            "Tempo emprestado: " + diasEmprestado + " dias",
                    "Devolução Registrada",
                    JOptionPane.INFORMATION_MESSAGE);

            salvou = true;
            dispose();
        }
    }

    public boolean salvou() {
        return salvou;
    }
}
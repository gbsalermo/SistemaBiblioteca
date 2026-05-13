package view;

import javax.swing.*;
import java.awt.*;
import model.Livro;
import repository.ListaLivrosDuplamenteEncadeada;

/**
 * Tela modal para visualizar e editar informações do livro
 * Esta janela aparece como pop-up (modal)
 * Agora com suporte a navegação entre livros
 */
public class TelaModalLivro extends JDialog {

    private Livro livro;
    private boolean salvou = false;
    private ListaLivrosDuplamenteEncadeada lista;
    private int indiceAtual;

    // Campos de texto
    private JTextField txtTitulo, txtAutor, txtAno, txtGenero, txtEditora;
    private JLabel lblId; // ← ADICIONE ISTO
    
    // Botões de navegação
    private JButton btnProximo, btnAnterior;
    private JLabel lblNavegacao;

    /**
     * Construtor original (compatibilidade com código antigo)
     * @param parent Janela pai (TelaPrincipal)
     * @param livro Livro a ser visualizado/editado
     */
    public TelaModalLivro(JFrame parent, Livro livro) {
        this(parent, livro, null, 0);
    }

    /**
     * Construtor com navegação
     * @param parent Janela pai (TelaPrincipal)
     * @param livro Livro a ser visualizado/editado
     * @param lista Referência à lista de livros para navegação
     * @param indiceAtual Índice do livro atual na lista
     */
    public TelaModalLivro(JFrame parent, Livro livro, ListaLivrosDuplamenteEncadeada lista, int indiceAtual) {
        super(parent, "Detalhes do Livro", true); // 'true' torna a janela modal
        this.livro = livro;
        this.lista = lista;
        this.indiceAtual = indiceAtual;

        setSize(500, 420);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // ✅ ORDEM CORRETA: Criar painelCampos PRIMEIRO (para inicializar lblId)
        JPanel painelCampos = criarPainelCampos();
        // Cria o painel de navegação (novo)
        JPanel painelNavegacao = criarPainelNavegacao();
        // Cria o painel de botões
        JPanel painelBotoes = criarPainelBotoes();

        add(painelNavegacao, BorderLayout.NORTH);
        add(painelCampos, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // ✅ AGORA: preenche os campos (lblId já foi criado acima)
        preencherCampos();
        atualizarLabelNavegacao();
    }

    /**
     * Cria o painel de navegação (ANTERIOR / Livro X/Y / PRÓXIMO)
     */
    private JPanel criarPainelNavegacao() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnAnterior = new JButton("⬅️ ANTERIOR");
        lblNavegacao = new JLabel("Livro 1/1");
        btnProximo = new JButton("PRÓXIMO ➡️");

        // Desabilita os botões se não houver lista ou se houver apenas 1 livro
        boolean podeNavegar = lista != null && lista.getTotalLivros() > 1;
        btnAnterior.setEnabled(podeNavegar);
        btnProximo.setEnabled(podeNavegar);

        // Eventos dos botões de navegação
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

        // Criando os campos
        panel.add(new JLabel("ID:"));
        lblId = new JLabel(); // ← Salva como atributo da classe
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

        // Configura o ID (não editável)
        if (livro != null) {
            lblId.setText(String.valueOf(livro.getId()));
        }

        return panel;
    }

    private JPanel criarPainelBotoes() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnSalvar = new JButton("💾 Salvar");
        JButton btnCancelar = new JButton("❌ Cancelar");

        btnSalvar.addActionListener(e -> salvarAlteracoes());
        btnCancelar.addActionListener(e -> dispose());

        panel.add(btnSalvar);
        panel.add(btnCancelar);

        return panel;
    }

    private void preencherCampos() {
        if (livro != null) {
            lblId.setText(String.valueOf(livro.getId())); // ← ADICIONE ISTO
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

        // Salva as alterações antes de navegar (sem mostrar mensagem)
        tentarSalvar();

        // Verifica se pode avançar
        if (indiceAtual < lista.getTotalLivros() - 1) {
            lista.avancar();
            indiceAtual++;
            livro = lista.getAtual();
            preencherCampos();
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

        // Salva as alterações antes de navegar (sem mostrar mensagem)
        tentarSalvar();

        // Verifica se pode voltar
        if (indiceAtual > 0) {
            lista.voltar();
            indiceAtual--;
            livro = lista.getAtual();
            preencherCampos();
            atualizarLabelNavegacao();
        }
    }

    /**
     * Atualiza o label mostrando a posição atual (Livro X / Y)
     */
    private void atualizarLabelNavegacao() {
        if (lista != null && lista.getTotalLivros() > 0) {
            lblNavegacao.setText("Livro " + (indiceAtual + 1) + " / " + lista.getTotalLivros());
        }
    }

    /**
     * Tenta salvar alterações sem mostrar mensagem de sucesso
     * (útil quando navegando entre livros)
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
            dispose(); // Fecha a janela
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "❌ Erro: Ano deve ser um número válido!",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean salvou() {
        return salvou;
    }
}
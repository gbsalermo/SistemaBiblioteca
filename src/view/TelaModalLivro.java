package view;

import javax.swing.*;
import java.awt.*;
import model.Livro;

/**
 * Tela modal para visualizar e editar informações do livro
 * Esta janela aparece como pop-up (modal)
 */
public class TelaModalLivro extends JDialog {

    private Livro livro;
    private boolean salvou = false;

    // Campos de texto
    private JTextField txtTitulo, txtAutor, txtAno, txtGenero, txtEditora;

    /**
     * Construtor da tela modal
     * @param parent Janela pai (TelaPrincipal)
     * @param livro Livro a ser visualizado/editado
     */
    public TelaModalLivro(JFrame parent, Livro livro) {
        super(parent, "Detalhes do Livro", true); // 'true' torna a janela modal
        this.livro = livro;

        setSize(450, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Cria o painel de campos
        JPanel painelCampos = criarPainelCampos();
        // Cria o painel de botões
        JPanel painelBotoes = criarPainelBotoes();

        add(painelCampos, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // Preenche os campos com os dados do livro
        preencherCampos();
    }

    private JPanel criarPainelCampos() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Criando os campos
        panel.add(new JLabel("ID:"));
        JLabel lblId = new JLabel();
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
        JButton btnFechar = new JButton("🔒 Fechar");

        btnSalvar.addActionListener(e -> salvarAlteracoes());
        btnCancelar.addActionListener(e -> dispose());
        btnFechar.addActionListener(e -> dispose());

        panel.add(btnSalvar);
        panel.add(btnCancelar);
        panel.add(btnFechar);

        return panel;
    }

    private void preencherCampos() {
        if (livro != null) {
            txtTitulo.setText(livro.getTitulo());
            txtAutor.setText(livro.getAutor());
            txtAno.setText(String.valueOf(livro.getAnoPublicacao()));
            txtGenero.setText(livro.getGenero());
            txtEditora.setText(livro.getEditora());
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
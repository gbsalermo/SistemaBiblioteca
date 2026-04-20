package view;

/* 
* Classe responsavel pelo painel da direita onde ficaram os detalhes do livro
*/
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class PainelDireito extends JPanel {

    private JTextField txtId;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtAno;
    private JTextField txtGenero;
    private JTextField txtEditora;

    public PainelDireito() {
        this.setLayout(new GridLayout(6, 2, 10, 10));

        this.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        this.add(txtId);

        this.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        this.add(txtTitulo);

        this.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        this.add(txtAutor);

        this.add(new JLabel("Ano:"));
        txtAno = new JTextField();
        this.add(txtAno);

        this.add(new JLabel("Gênero:"));
        txtGenero = new JTextField();
        this.add(txtGenero);

        this.add(new JLabel("Editora:"));
        txtEditora = new JTextField();
        this.add(txtEditora);
    }

    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtTitulo() { return txtTitulo; }
    public JTextField getTxtAutor() { return txtAutor; }
    public JTextField getTxtAno() { return txtAno; }
    public JTextField getTxtGenero() { return txtGenero; }
    public JTextField getTxtEditora() { return txtEditora; }
}
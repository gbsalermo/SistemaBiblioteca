package view;

/* 
* Classe responsavel pelo painel da direita onde ficam os detalhes do livro selecionado
* Exibe e permite edição dos campos: ID, Título, Autor, Ano, Gênero e Editora
*/
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class PainelDireito extends JPanel {

    // Campos de texto para cada atributo do livro
    private JTextField txtId;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtAno;
    private JTextField txtGenero;
    private JTextField txtEditora;

    public PainelDireito() {
        // GridLayout(6, 2, 10, 10) organiza em 6 linhas e 2 colunas com espaçamento de 10px
        // Primeira coluna: labels (ID:, Título:, etc)
        // Segunda coluna: campos de texto (JTextField)
        this.setLayout(new GridLayout(6, 2, 10, 10));

        // Linha 1: Label "ID:" + campo ID (não editável, gerado automaticamente)
        this.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false); // ID nunca pode ser alterado pelo usuário
        this.add(txtId);

        // Linha 2: Label "Título:" + campo para digitar o título do livro
        this.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        this.add(txtTitulo);

        // Linha 3: Label "Autor:" + campo para digitar o nome do autor
        this.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        this.add(txtAutor);

        // Linha 4: Label "Ano:" + campo para digitar o ano de publicação
        this.add(new JLabel("Ano:"));
        txtAno = new JTextField();
        this.add(txtAno);

        // Linha 5: Label "Gênero:" + campo para digitar o gênero do livro
        this.add(new JLabel("Gênero:"));
        txtGenero = new JTextField();
        this.add(txtGenero);

        // Linha 6: Label "Editora:" + campo para digitar o nome da editora
        this.add(new JLabel("Editora:"));
        txtEditora = new JTextField();
        this.add(txtEditora);
    }

    // Getters — permitem que outras classes acessem os campos de texto
    // Usado pela TelaPrincipal para ler/escrever dados do livro
    
    public JTextField getTxtId() { 
        return txtId; 
    }
    
    public JTextField getTxtTitulo() { 
        return txtTitulo; 
    }
    
    public JTextField getTxtAutor() { 
        return txtAutor; 
    }
    
    public JTextField getTxtAno() { 
        return txtAno; 
    }
    
    public JTextField getTxtGenero() { 
        return txtGenero; 
    }
    
    public JTextField getTxtEditora() { 
        return txtEditora; 
    }
}
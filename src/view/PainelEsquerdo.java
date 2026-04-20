
package view;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;


/* 
* Classe responsavel pelo painel da esquerda onde vai estar a tabela de livros
*/
public class PainelEsquerdo extends JPanel {

    private JTable tabelaLivros;

    public PainelEsquerdo() {
        this.setLayout(new BorderLayout());

        String[] colunas = {"ID", "Título", "Autor", "Ano"};
        Object[][] dados = {};

        tabelaLivros = new JTable(dados, colunas);

        JScrollPane scrollPane = new JScrollPane(tabelaLivros);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public JTable getTabelaLivros() {
        return tabelaLivros;
    }
}

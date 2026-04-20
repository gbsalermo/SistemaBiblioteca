package view;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

/* 
* Classe responsável pelo painel da esquerda onde fica a tabela de livros
* Exibe todos os livros da biblioteca em formato de tabela com colunas: ID, Título, Autor, Ano
* Permite ao usuário selecionar um livro para visualizar/editar seus detalhes
*/
public class PainelEsquerdo extends JPanel {

    private JTable tabelaLivros; // Tabela que exibe os livros

    public PainelEsquerdo() {
        // BorderLayout coloca componentes em regiões (NORTH, SOUTH, EAST, WEST, CENTER)
        this.setLayout(new BorderLayout());

        // Define as colunas que aparecerão na tabela
        String[] colunas = {"ID", "Título", "Autor", "Ano"};
        
        // Dados vazios no início — será preenchida dinamicamente quando livros forem inseridos
        // Object[][] permite dados de qualquer tipo (int, String, etc)
        Object[][] dados = {};

        // Cria a tabela com os dados iniciais (vazios) e as colunas
        tabelaLivros = new JTable(dados, colunas);

        // JScrollPane adiciona barras de scroll (horizontal e vertical) à tabela
        // Necessário quando há muitos livros e não cabem todos na tela
        JScrollPane scrollPane = new JScrollPane(tabelaLivros);
        
        // Adiciona o scroll (com a tabela dentro) no centro do painel
        // CENTER significa que ocupa todo o espaço disponível
        this.add(scrollPane, BorderLayout.CENTER);
    }

    // Getter — permite que TelaPrincipal acesse a tabela
    // Usado para atualizar os dados quando livros são inseridos/removidos/ordenados
    public JTable getTabelaLivros() {
        return tabelaLivros;
    }
}

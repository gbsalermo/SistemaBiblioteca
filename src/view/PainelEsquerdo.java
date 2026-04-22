package view;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import model.Livro;
import repository.ListaLivrosDuplamenteEncadeada;

/* 
* Classe responsável pelo painel da esquerda onde fica a tabela de livros.
* Exibe todos os livros da biblioteca em formato de tabela com colunas: ID, Título, Autor, Ano.
* Permite ao usuário selecionar um livro para visualizar/editar seus detalhes.
*/

public class PainelEsquerdo extends JPanel {
    // Tabela que exibe os livros.
    private JTable tabelaLivros;
    // O "motor" da tabela.
    private DefaultTableModel modeloTabela;
    private ListaLivrosDuplamenteEncadeada lista;

    public PainelEsquerdo(TelaPrincipal tela, ListaLivrosDuplamenteEncadeada lista) {
        this.lista = lista;
        // BorderLayout coloca componentes em regiões (NORTH, SOUTH, EAST, WEST, CENTER).
        this.setLayout(new BorderLayout());

        // 1. Criamos o modelo da tabela definindo as colunas
        // Sobrescrevemos isCellEditable para que o usuário não edite direto na tabela.
        String[] colunas = {"ID", "Título", "Autor", "Ano"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        // Cria a tabela com os dados iniciais (vazios) e as colunas.
        tabelaLivros = new JTable(modeloTabela);
        // JScrollPane adiciona barras de scroll (horizontal e vertical) à tabela.
        // Necessário quando há muitos livros e não cabem todos na tela.
        JScrollPane scrollPane = new JScrollPane(tabelaLivros);
        // Adiciona o scroll (com a tabela dentro) no centro do painel.
        // CENTER significa que ocupa todo o espaço disponível.
        this.add(scrollPane, BorderLayout.CENTER);

        // 2. Opcional: Adicionar um evento de clique na tabela.
        // Quando o usuário clica em uma linha, a TelaPrincipal poderia buscar esse livro.
    }

    /**
    * Limpa a tabela e insere todos os livros vindos da lista encadeada.
    */

    public void atualizarTabela() {
        // Limpa todas as linhas atuais
        modeloTabela.setRowCount(0);
        // Pega a lista convertida que fizemos no repositório
        List<Livro> todosOsLivros = lista.listarTodos();
        // Adiciona cada livro como uma nova linha no modelo
        for (Livro l : todosOsLivros) {
            Object[] linha = {
                l.getId(),
                l.getTitulo(),
                l.getAutor(),
                l.getAnoPublicacao()
            };
            modeloTabela.addRow(linha);
        }
    }
    public JTable getTabelaLivros() {
        return tabelaLivros;
    }
}

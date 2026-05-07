package view;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.List;
import model.Livro;
import model.No;
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
    private TelaPrincipal tela;
    
    public PainelEsquerdo(TelaPrincipal tela, ListaLivrosDuplamenteEncadeada lista) {
        this.tela = tela;
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

        tabelaLivros.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int coluna = tabelaLivros.columnAtPoint(e.getPoint());
                lista.ordenarPorColuna(coluna);

                // Verifica em qual modo está
                if (tela.getPainelSuperior().isModoBusca()) {
                    filtrarTabela(
                        tela.getPainelSuperior().getTermoAtual(),
                        tela.getPainelSuperior().getTipoAtual()
                    );
                } else {
                    atualizarTabela();
                }
            }
        });
        tabelaLivros.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            int linhaSelecionada = tabelaLivros.getSelectedRow();

            if (linhaSelecionada != -1) {
                String titulo = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
                Livro livroSelecionado = lista.buscarPorTitulo(titulo);

                if (livroSelecionado != null) {
                    JFrame janelaPai = (JFrame) SwingUtilities.getWindowAncestor(PainelEsquerdo.this);
                    TelaModalLivro modal = new TelaModalLivro(janelaPai, livroSelecionado);
                    modal.setVisible(true);

                    // Se salvou, atualiza a tabela
                    if (modal.salvou()) {
                        atualizarTabela();
                    }
                }
            }
        }
    });
} // <--- ESTA CHAVE FECHA O CONSTRUTOR! (É ISSO QUE FALTAVA)

    /**
    * Limpa a tabela e insere todos os livros vindos da lista encadeada.
    */
    public void atualizarTabela() {
        //Zera a tabela removendo linhas existentes
        modeloTabela.setRowCount(0);

        //Pega a lista encadeada do repositorio
        ListaLivrosDuplamenteEncadeada listaEncadeada = lista.listarTodos();
        
        //Crio um array para armazenar esta lista
        List<Livro> listaConvertida = new ArrayList<>();

        //Criação de ponteiro auxiliar que inicia no primeiro no da lista
        No aux = listaEncadeada.getPrimeiro();

        //Segue a lista até o final(null), pega o livro do nó atual e adiciona no array, depois avança para o prox. nó e verifica a condição
        while (aux != null) {
            listaConvertida.add(aux.getLivro());
            aux = aux.getProximo();
        }

        //Aqui percorre o arrayList convertido e cria uma linha da tabela
        for (Livro l : listaConvertida) {
            Object[] linha = {
                l.getId(),
                l.getTitulo(),
                l.getAutor(),
                l.getAnoPublicacao()
            };

            //Adiciona essa linha na tabela no swing
            modeloTabela.addRow(linha);
        }
    }

    public void filtrarTabela(String termo, int tipo) {
        modeloTabela.setRowCount(0);

        No aux = lista.getPrimeiro();

        while (aux != null) {
            Livro l = aux.getLivro();

            boolean corresponde = (tipo == 0)
                ? l.getTitulo().toLowerCase().contains(termo.toLowerCase())
                : l.getAutor().toLowerCase().contains(termo.toLowerCase());

            if (corresponde) {
                modeloTabela.addRow(new Object[]{
                    l.getId(),
                    l.getTitulo(),
                    l.getAutor(),
                    l.getAnoPublicacao()
                });
            }

            aux = aux.getProximo();
        }
    }
    
    public JTable getTabelaLivros() {
        return tabelaLivros;
    }
    
    public int getQuantidadeLinhasTabela() {
        return modeloTabela.getRowCount();
    }
}
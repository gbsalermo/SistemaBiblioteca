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
 * Classe responsável pelo painel onde fica a tabela de livros.
 * Exibe todos os livros da biblioteca em formato de tabela com colunas:
 * ID, Título, Autor e Ano.
 * 
 * Também permite:
 * - Ordenar a tabela ao clicar no cabeçalho
 * - Abrir o modal de detalhes ao clicar em um livro
 * - Filtrar os livros durante buscas
 */

public class PainelLista extends JPanel {

    // Tabela visual exibida na interface
    private JTable tabelaLivros;

    // Modelo responsável pelos dados da tabela
    private DefaultTableModel modeloTabela;

    // Referência da lista principal da aplicação
    private ListaLivrosDuplamenteEncadeada lista;

    // Referência da tela principal
    private TelaPrincipal tela;

    /**
     * Construtor do painel esquerdo.
     * 
     * Recebe:
     * - A tela principal
     * - A lista encadeada de livros
     */
    public PainelLista(TelaPrincipal tela, ListaLivrosDuplamenteEncadeada lista) {

        this.tela = tela;
        this.lista = lista;

        // Define o layout principal do painel
        // BorderLayout divide o painel em regiões:
        // NORTH, SOUTH, EAST, WEST e CENTER
        this.setLayout(new BorderLayout());

        // Define as colunas da tabela
        String[] colunas = { "ID", "Título", "Autor", "Ano" };

        /*
         * Criação do modelo da tabela.
         * 
         * O valor 0 significa que a tabela inicia sem linhas.
         * 
         * O método isCellEditable foi sobrescrito para impedir
         * edição direta nas células da tabela.
         */
        modeloTabela = new DefaultTableModel(colunas, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Criação da JTable usando o modelo criado acima
        tabelaLivros = new JTable(modeloTabela);

        /*
         * JScrollPane adiciona barras de rolagem automaticamente.
         * Muito útil quando a tabela possui muitos livros.
         */
        JScrollPane scrollPane = new JScrollPane(tabelaLivros);

        // Adiciona a tabela no centro do painel
        this.add(scrollPane, BorderLayout.CENTER);

        /*
         * Evento de clique no cabeçalho da tabela.
         * 
         * Quando o usuário clica em uma coluna:
         * - A lista é ordenada
         * - A tabela é atualizada
         */
        tabelaLivros.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                // Descobre qual coluna foi clicada
                int coluna = tabelaLivros.columnAtPoint(e.getPoint());

                // Ordena a lista com base na coluna
                lista.ordenarPorColuna(coluna);

                // Verifica se a tela está em modo busca
                if (tela.getPainelSuperior().isModoBusca()) {

                    // Atualiza mantendo o filtro atual
                    filtrarTabela(
                            tela.getPainelSuperior().getTermoAtual(),
                            tela.getPainelSuperior().getTipoAtual());

                } else {

                    // Atualiza normalmente
                    atualizarTabela();
                }
            }
        });

        /*
         * Evento de clique em uma linha da tabela.
         * 
         * Abre o modal de detalhes do livro selecionado.
         */
        tabelaLivros.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                // Pega a linha selecionada
                int linhaSelecionada = tabelaLivros.getSelectedRow();

                // Verifica se alguma linha foi selecionada
                if (linhaSelecionada != -1) {

                    // Obtém o título do livro da linha clicada
                    String titulo = (String) modeloTabela.getValueAt(linhaSelecionada, 1);

                    // Busca o livro correspondente na lista
                    Livro livroSelecionado = lista.buscarPorTitulo(titulo);

                    // Se encontrou o livro
                    if (livroSelecionado != null) {

                        // Obtém a janela pai para o modal
                        JFrame janelaPai =
                                (JFrame) SwingUtilities.getWindowAncestor(PainelLista.this);

                        // Verifica se está em modo busca
                        boolean isModoBusca =
                                tela.getPainelSuperior().isModoBusca();

                        // Lista que será usada pelo modal
                        ListaLivrosDuplamenteEncadeada listaParaModal;

                        if (isModoBusca) {

                            /*
                             * Em modo busca:
                             * cria uma lista filtrada apenas com os livros exibidos
                             */
                            listaParaModal = criarListaFiltrada();

                        } else {

                            /*
                             * Em modo normal:
                             * usa a lista completa
                             */
                            listaParaModal = PainelLista.this.lista;
                        }

                        // Índice da linha clicada
                        int indiceAtual = linhaSelecionada;

                        // Move o ponteiro da lista para o índice correto
                        listaParaModal.irParaIndice(indiceAtual);

                        /*
                         * Cria o modal de detalhes do livro
                         * passando:
                         * - janela pai
                         * - livro selecionado
                         * - lista utilizada
                         * - índice atual
                         */
                        TelaModalLivro modal =
                                new TelaModalLivro(
                                        janelaPai,
                                        livroSelecionado,
                                        listaParaModal,
                                        indiceAtual);

                        // Exibe o modal
                        modal.setVisible(true);

                        // Se houve alteração no modal, atualiza tabela
                        if (modal.salvou()) {
                            atualizarTabela();
                        }
                    }
                }
            }
        });
    }

    /**
     * Atualiza completamente a tabela.
     * 
     * Remove todas as linhas atuais e adiciona novamente
     * todos os livros da lista encadeada.
     */
    public void atualizarTabela() {

        // Remove todas as linhas da tabela
        modeloTabela.setRowCount(0);

        // Obtém a lista completa
        ListaLivrosDuplamenteEncadeada listaEncadeada =
                lista.listarTodos();

        /*
         * Cria um ArrayList auxiliar para armazenar os livros.
         * 
         * Isso facilita o percurso e organização dos dados.
         */
        List<Livro> listaConvertida = new ArrayList<>();

        // Ponteiro auxiliar para percorrer a lista encadeada
        No aux = listaEncadeada.getPrimeiro();

        /*
         * Percorre toda a lista:
         * - pega o livro do nó atual
         * - adiciona no ArrayList
         * - avança para o próximo nó
         */
        while (aux != null) {

            listaConvertida.add(aux.getLivro());

            aux = aux.getProximo();
        }

        /*
         * Percorre o ArrayList convertido
         * adicionando cada livro como uma linha da tabela.
         */
        for (Livro l : listaConvertida) {

            Object[] linha = {
                    l.getId(),
                    l.getTitulo(),
                    l.getAutor(),
                    l.getAnoPublicacao()
            };

            // Adiciona a linha no modelo da tabela
            modeloTabela.addRow(linha);
        }
    }

    /**
     * Filtra a tabela com base:
     * 
     * tipo = 0 → busca por título
     * tipo = 1 → busca por autor
     */
    public void filtrarTabela(String termo, int tipo) {

        // Limpa a tabela antes de inserir os resultados filtrados
        modeloTabela.setRowCount(0);

        // Ponteiro auxiliar
        No aux = lista.getPrimeiro();

        // Percorre toda a lista
        while (aux != null) {

            Livro l = aux.getLivro();

            /*
             * Verifica se o livro corresponde ao filtro.
             * 
             * contains() permite busca parcial.
             * toLowerCase() evita problemas com maiúsculas/minúsculas.
             */
            boolean corresponde = (tipo == 0)
                    ? l.getTitulo().toLowerCase().contains(termo.toLowerCase())
                    : l.getAutor().toLowerCase().contains(termo.toLowerCase());

            // Se corresponde ao filtro, adiciona na tabela
            if (corresponde) {

                modeloTabela.addRow(new Object[] {
                        l.getId(),
                        l.getTitulo(),
                        l.getAutor(),
                        l.getAnoPublicacao()
                });
            }

            // Avança para o próximo nó
            aux = aux.getProximo();
        }
    }

    /**
     * Retorna a JTable.
     */
    public JTable getTabelaLivros() {
        return tabelaLivros;
    }

    /**
     * Retorna a quantidade de linhas exibidas na tabela.
     */
    public int getQuantidadeLinhasTabela() {
        return modeloTabela.getRowCount();
    }

    /**
     * Preenche a tabela com todos os livros da lista.
     * 
     * Similar ao atualizarTabela(), porém utilizando diretamente
     * o modelo obtido da JTable.
     */
    public void preencherTabelaComTodos() {

        // Obtém o modelo da tabela
        DefaultTableModel modelo =
                (DefaultTableModel) tabelaLivros.getModel();

        // Remove todas as linhas existentes
        modelo.setRowCount(0);

        // Ponteiro auxiliar
        No aux = lista.getPrimeiro();

        // Percorre toda a lista
        while (aux != null) {

            Livro livro = aux.getLivro();

            // Adiciona o livro na tabela
            modelo.addRow(new Object[] {
                    livro.getId(),
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getAnoPublicacao()
            });

            // Avança para o próximo nó
            aux = aux.getProximo();
        }
    }

    /**
     * Seleciona uma linha específica da tabela.
     * 
     * Também move automaticamente a barra de rolagem
     * para deixar a linha visível.
     */
    public void selecionarLinhaTabela(int indice) {

        // Verifica se o índice é válido
        if (indice >= 0 && tabelaLivros.getRowCount() > indice) {

            // Seleciona a linha
            tabelaLivros.setRowSelectionInterval(indice, indice);

            // Faz scroll automático até a linha
            tabelaLivros.scrollRectToVisible(
                    tabelaLivros.getCellRect(indice, 0, true));
        }
    }

    /**
     * Cria uma nova lista contendo apenas os livros
     * atualmente exibidos na tabela filtrada.
     * 
     * Utilizado no modo busca para permitir navegação
     * apenas entre os livros filtrados.
     */
    private ListaLivrosDuplamenteEncadeada criarListaFiltrada() {

        // Cria nova lista vazia
        ListaLivrosDuplamenteEncadeada listaFiltrada =
                new ListaLivrosDuplamenteEncadeada();

        // Percorre todas as linhas da tabela
        for (int i = 0; i < tabelaLivros.getRowCount(); i++) {

            // Obtém o título da linha atual
            String titulo =
                    (String) modeloTabela.getValueAt(i, 1);

            // Busca o livro original
            Livro livro = lista.buscarPorTitulo(titulo);

            // Se encontrou, adiciona na nova lista
            if (livro != null) {

                listaFiltrada.adicionarNoFim(livro);
            }
        }

        // Retorna a lista filtrada criada
        return listaFiltrada;
    }

    /**
     * Retorna o modelo da tabela.
     */
    public DefaultTableModel getModeloTabela() {
        return modeloTabela;
    }
}
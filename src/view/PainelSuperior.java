package view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.*;

import model.Livro;
import model.No;
import repository.ListaLivrosDuplamenteEncadeada;
import model.Livro;
import services.Remover;
import util.DataLivro;

/**
 * Classe responsavel pelo painel dos botoes e controles da parte superior.
 */

public class PainelSuperior extends JPanel {

    // Atributos dos botoes - Inserir, remover, Buscar, Anterior, Proximo, Listar:
    private JButton btRemover, btBuscar, btAnterior, btProximo, btAdicionar, btListar;

    // Label responsável por mostrar a posição atual na navegação
    private JLabel lblContador;

    // Referência da tela principal
    private TelaPrincipal tela;

    // Lista principal de livros
    private ListaLivrosDuplamenteEncadeada lista;

    // Referência do painel da tabela
    private PainelLista painelEsquerdo;

    // Controla em qual tela a interface está
    // 0 = modo normal
    // 1 = modo busca
    private int flag_tela = 0;

    // Armazena o último termo pesquisado
    private String termoAtual = "";

    // Tipo da busca:
    // 0 = título
    // 1 = autor
    private int tipoAtual = 0;

    // Referência da tabela do painel esquerdo
    private JTable tabelaLivros;

    /**
     * Construtor do painel superior.
     * Recebe as referências necessárias para controlar a interface.
     */
    public PainelSuperior(TelaPrincipal tela,
            ListaLivrosDuplamenteEncadeada lista,
            PainelLista painelEsquerdo) {

        this.tabelaLivros = painelEsquerdo.getTabelaLivros();
        this.painelEsquerdo = painelEsquerdo;
        this.tela = tela;
        this.lista = lista;

        // Define o layout horizontal dos botões
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Inicialização dos botões
        btRemover = new JButton("REMOVER");
        btBuscar = new JButton("BUSCAR");
        btAnterior = new JButton("ANTERIOR");
        btProximo = new JButton("PROXIMO");
        btAdicionar = new JButton("ADICIONAR");
        btListar = new JButton("LISTAR");

        // Label inicial do contador
        lblContador = new JLabel(" | Livros: 0/0");

        // Adiciona os componentes ao painel
        this.add(btBuscar);
        this.add(btRemover);

        this.add(new JLabel(" | "));

        this.add(btAnterior);
        this.add(btProximo);
        this.add(lblContador);

        this.add(new JLabel(" | "));

        this.add(btAdicionar);
        this.add(btListar);

        // Configura os eventos dos botões
        configurarEventos();
    }

    /**
     * Método responsável por configurar os eventos dos botões.
     */
    private void configurarEventos() {

        /**
         * Evento do botão PRÓXIMO
         */
        btProximo.addActionListener(e -> {

            // Verifica se está no modo busca
            if (flag_tela == 1) {

                // Obtém a linha selecionada atualmente
                int linhaAtual = tabelaLivros.getSelectedRow();

                // Verifica se ainda existe próxima linha
                if (linhaAtual < tabelaLivros.getRowCount() - 1) {

                    // Seleciona a próxima linha
                    tabelaLivros.setRowSelectionInterval(
                            linhaAtual + 1,
                            linhaAtual + 1);

                    // Pega o título da próxima linha
                    String titulo = (String) tabelaLivros
                            .getModel()
                            .getValueAt(linhaAtual + 1, 1);

                    // Busca o livro na lista principal
                    Livro livro = lista.buscarPorTitulo(titulo);

                    // Exibe o livro no painel direito
                    tela.getPainelDireito().exibirLivro(livro);

                    // Atualiza contador da navegação
                    atualizarContador(
                            linhaAtual + 1,
                            tabelaLivros.getRowCount());
                }

            } else {

                // Avança o ponteiro da lista
                lista.avancar();

                // Pega o índice atual
                int indiceAtual = lista.getIndiceAtual();

                // Atualiza interface
                tela.atualizarInterface();

                // Seleciona a linha correspondente na tabela
                painelEsquerdo.selecionarLinhaTabela(indiceAtual);

                // Atualiza contador
                atualizarContador(
                        indiceAtual,
                        lista.getTotalLivros());
            }
        });

        /**
         * Evento do botão ANTERIOR
         */
        btAnterior.addActionListener(e -> {

            // Verifica se está em modo busca
            if (flag_tela == 1) {

                // Linha atualmente selecionada
                int linhaAtual = tabelaLivros.getSelectedRow();

                // Verifica se não está na primeira linha
                if (linhaAtual > 0) {

                    // Seleciona linha anterior
                    tabelaLivros.setRowSelectionInterval(
                            linhaAtual - 1,
                            linhaAtual - 1);

                    // Obtém o título do livro
                    String titulo = (String) tabelaLivros
                            .getModel()
                            .getValueAt(linhaAtual - 1, 1);

                    // Busca o livro
                    Livro livro = lista.buscarPorTitulo(titulo);

                    // Exibe no painel direito
                    tela.getPainelDireito().exibirLivro(livro);

                    // Atualiza contador
                    atualizarContador(
                            linhaAtual - 1,
                            tabelaLivros.getRowCount());
                }

            } else {

                // Volta o ponteiro da lista
                lista.voltar();

                // Índice atual após voltar
                int indiceAtual = lista.getIndiceAtual();

                // Atualiza interface
                tela.atualizarInterface();

                // Seleciona linha correspondente
                painelEsquerdo.selecionarLinhaTabela(indiceAtual);

                // Atualiza contador
                atualizarContador(
                        indiceAtual,
                        lista.getTotalLivros());
            }
        });

        /**
         * Evento do botão REMOVER
         */
        btRemover.addActionListener(e -> {

            // Verifica se a lista não está vazia
            if (!lista.estaVazia()) {

                // Serviço responsável pela remoção
                Remover remover = new Remover();

                // Obtém índice atual
                int indiceAtual = lista.getIndiceAtual();

                // Verifica se o índice é válido
                if (indiceAtual != -1) {

                    // Remove o livro
                    String resultado = remover.excluirPos(lista, indiceAtual);

                    // Exibe mensagem
                    JOptionPane.showMessageDialog(this, resultado);

                    // Se estiver em modo busca
                    if (flag_tela == 1) {

                        // Atualiza a tabela filtrada
                        painelEsquerdo.filtrarTabela(
                                termoAtual,
                                tipoAtual);

                        int filtrados = painelEsquerdo.getQuantidadeLinhasTabela();

                        // Linha atualmente selecionada
                        int linhaSelecionada = tabelaLivros.getSelectedRow();

                        // Atualiza contador
                        atualizarContador(
                                linhaSelecionada == -1 ? 0 : linhaSelecionada,
                                filtrados);

                    } else {

                        // Atualiza interface completa
                        tela.atualizarInterface();
                    }
                }
            }
        });

        /**
         * Evento do botão ADICIONAR
         */
        btAdicionar.addActionListener(e -> abrirDialogAdicionar());

        /**
         * Evento do botão BUSCAR
         */
        btBuscar.addActionListener(e -> {
            JanelaBusca();
        });

        /**
         * Evento do botão LISTAR
         */
        btListar.addActionListener(e -> {

            // Verifica se a lista possui livros
            if (!lista.estaVazia()) {

                // Lista todos os livros novamente
                lista.listarTodos();

                // Sai do modo busca
                flag_tela = 0;

                // Limpa o termo da busca
                termoAtual = "";

                // Preenche novamente a tabela
                painelEsquerdo.preencherTabelaComTodos();

                // Seleciona a primeira linha
                painelEsquerdo.selecionarLinhaTabela(0);

                // Atualiza interface
                tela.atualizarInterface();
            }
        });
    }

    /**
     * Atualiza o contador exibido no topo da tela.
     */
    public void atualizarContador(int atual, int total) {

        // Caso a lista esteja vazia
        if (total == 0) {

            lblContador.setText(" | Lista Vazia");

        } else {

            lblContador.setText(
                    " | Livro: " + (atual + 1) + " / " + total);
        }
    }

    /**
     * Método responsável pela janela de busca.
     */
    private void JanelaBusca() {

        // Painel principal da busca
        JPanel painelBusca = new JPanel();

        // Layout vertical
        painelBusca.setLayout(
                new BoxLayout(painelBusca, BoxLayout.Y_AXIS));

        // Opções disponíveis
        String[] opcoes = {
                "Buscar por Título",
                "Buscar por Autor"
        };

        // ComboBox com opções
        JComboBox<String> cbBusca = new JComboBox<>(opcoes);

        // Campo de texto da busca
        JTextField txtBusca = new JTextField(20);

        // Adiciona componentes
        painelBusca.add(new JLabel("Como gostaria de buscar?"));
        painelBusca.add(cbBusca);

        painelBusca.add(new JLabel("Digite a busca:"));
        painelBusca.add(txtBusca);

        // Cria janela de confirmação
        int resultado = JOptionPane.showConfirmDialog(
                this,
                painelBusca,
                "Buscar Livro",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        // Verifica se clicou em OK
        if (resultado == JOptionPane.OK_OPTION) {

            // Obtém texto digitado
            String termoBusca = txtBusca.getText().trim();

            // Validação de campo vazio
            if (termoBusca.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Por favor, digite um termo de busca!",
                        "Campo Vazio",
                        JOptionPane.WARNING_MESSAGE);

                return;
            }

            /**
             * BUSCA POR TÍTULO
             */
            if (cbBusca.getSelectedIndex() == 0) {

                // Busca livro pelo título
                Livro livroEncontrado =
                        lista.buscarPorTitulo(termoBusca);

                // Se encontrou
                if (livroEncontrado != null) {

                    JOptionPane.showMessageDialog(
                            this,
                            "✅ Livro encontrado!\n\n" +
                                    "Título: " + livroEncontrado.getTitulo() + "\n" +
                                    "Autor: " + livroEncontrado.getAutor() + "\n" +
                                    "Ano: " + livroEncontrado.getAnoPublicacao(),
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Atualiza estado da busca
                    termoAtual = termoBusca;
                    tipoAtual = 0;
                    flag_tela = 1;

                    // Filtra tabela
                    tela.getPainelEsquerdo()
                            .filtrarTabela(termoBusca, 0);

                    // Exibe livro encontrado
                    tela.getPainelDireito()
                            .exibirLivro(livroEncontrado);

                    // Atualiza contador
                    int filtrados =
                            tela.getPainelEsquerdo()
                                    .getQuantidadeLinhasTabela();

                    atualizarContador(
                            lista.getIndiceAtual(),
                            filtrados);

                } else {

                    // Livro não encontrado
                    JOptionPane.showMessageDialog(
                            this,
                            "Nenhum livro encontrado!",
                            "Livro não encontrado",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            /**
             * BUSCA POR AUTOR
             */
            else {

                // Busca todos os livros do autor
                ListaLivrosDuplamenteEncadeada livrosEncontrados =
                        lista.buscarTodosPorAutor(termoBusca);

                // Verifica se encontrou resultados
                if (livrosEncontrados == null
                        || livrosEncontrados.getTotalLivros() == 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Autor não encontrado!",
                            "Autor não encontrado",
                            JOptionPane.WARNING_MESSAGE);

                    return;
                }

                // Exibe mensagem de sucesso
                JOptionPane.showMessageDialog(
                        this,
                        "✅ " + livrosEncontrados.getTotalLivros()
                                + " livro(s) encontrado(s) de: "
                                + termoBusca,
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);

                // Move ponteiro para o primeiro livro encontrado
                lista.buscarPorAutor(termoBusca);

                // Atualiza estado da busca
                termoAtual = termoBusca;
                tipoAtual = 1;
                flag_tela = 1;

                // Filtra tabela
                tela.getPainelEsquerdo()
                        .filtrarTabela(termoBusca, 1);

                // Obtém primeiro livro
                Livro primeiroLivro =
                        livrosEncontrados.getPrimeiro() != null
                                ? livrosEncontrados.getPrimeiro().getLivro()
                                : null;

                // Exibe livro
                tela.getPainelDireito()
                        .exibirLivro(primeiroLivro);

                // Atualiza contador
                int filtrados =
                        tela.getPainelEsquerdo()
                                .getQuantidadeLinhasTabela();

                atualizarContador(0, filtrados);
            }
        }
    }

    /**
     * Abre o formulário de adicionar livro em um JDialog.
     */
    private void abrirDialogAdicionar() {

        // Obtém janela pai
        JFrame janelaPai =
                (JFrame) SwingUtilities.getWindowAncestor(this);

        // Cria dialog modal
        JDialog dialog =
                new JDialog(janelaPai, "Adicionar Livro", true);

        dialog.setSize(600, 600);

        // Centraliza na tela
        dialog.setLocationRelativeTo(janelaPai);

        // Cria formulário
        PainelDireito painelForm =
                new PainelDireito(tela, lista);

        // Fecha dialog ao salvar
        painelForm.setAoSalvar(() -> dialog.dispose());

        // Adiciona formulário
        dialog.add(painelForm);

        // Exibe janela
        dialog.setVisible(true);

        // Atualiza interface
        tela.atualizarInterface();
    }

    /**
     * Retorna se está em modo busca.
     */
    public boolean isModoBusca() {
        return flag_tela == 1;
    }

    /**
     * Retorna o termo atual da busca.
     */
    public String getTermoAtual() {
        return termoAtual;
    }

    /**
     * Retorna o tipo atual da busca.
     */
    public int getTipoAtual() {
        return tipoAtual;
    }
}
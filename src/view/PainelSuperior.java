package view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.*;

import model.Livro;
import repository.ListaLivrosDuplamenteEncadeada;
import model.Livro;

/**
 * Classe responsavel pelo painel dos botoes e controles da parte superior.
 */

public class PainelSuperior extends JPanel {

    // Atributos dos botoes - Inserir, remover, Buscar, Anterior, Proximo, Listar:

    private JButton btRemover, btBuscar, btAnterior, btProximo, btOrdenar, btListar;
    // Para mostrar a posição atual.
    private JLabel lblContador;

    private TelaPrincipal tela;
    private ListaLivrosDuplamenteEncadeada lista;
    private PainelEsquerdo painelEsquerdo;

    // Controla em qual tela a interface está (normal ou busca)
    private int flag_tela = 0; // 0 = normal; 1 = busca
    private String termoAtual = "";
    private int tipoAtual = 0;
    private JTable tabelaLivros;

    // Construtor atualizado para receber as referências
    public PainelSuperior(TelaPrincipal tela, ListaLivrosDuplamenteEncadeada lista, PainelEsquerdo painelEsquerdo) {
        this.tabelaLivros = painelEsquerdo.getTabelaLivros();
        this.painelEsquerdo = painelEsquerdo;
        this.tela = tela;
        this.lista = lista;

        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        btRemover = new JButton("REMOVER");
        btBuscar = new JButton("BUSCAR");
        btAnterior = new JButton("ANTERIOR");
        btProximo = new JButton("PROXIMO");
        lblContador = new JLabel(" | Livros: 0/0");
        btOrdenar = new JButton("ORDENAR");
        btListar = new JButton("LISTAR");

        this.add(btBuscar);
        this.add(btRemover);
        this.add(new JLabel(" | "));
        this.add(btAnterior);
        this.add(btProximo);
        this.add(lblContador);
        this.add(new JLabel(" | "));
        this.add(btOrdenar);
        this.add(btListar);

        configurarEventos();
    }

    private void configurarEventos() {
        // Agora os botões do topo também controlam a lista!
        // Evento do botão PRÓXIMO
    btProximo.addActionListener(e -> {
    // Verifica se está em modo busca
    if (flag_tela == 1) {
        // Em modo busca, navega na tabela filtrada
        int linhaAtual = tabelaLivros.getSelectedRow(); // Pega a linha selecionada
        
        // Verifica se não está na última linha
        if (linhaAtual < tabelaLivros.getRowCount() - 1) {
            // Seleciona a próxima linha
            tabelaLivros.setRowSelectionInterval(linhaAtual + 1, linhaAtual + 1);
            
            // Pega o título do livro da próxima linha
            String titulo = (String) tabelaLivros.getModel().getValueAt(linhaAtual + 1, 1);
            
            // Busca o livro pelo título
            Livro livro = lista.buscarPorTitulo(titulo);
            
            // Exibe o livro no painel direito
            tela.getPainelDireito().exibirLivro(livro);
            
            // Atualiza o contador
            atualizarContador(linhaAtual + 1, tabelaLivros.getRowCount());
        }
    } else {
        // Em modo normal, avança na lista original
        lista.avancar();
        tela.atualizarInterface();
    }
});

// Evento do botão ANTERIOR
btAnterior.addActionListener(e -> {
    // Verifica se está em modo busca
    if (flag_tela == 1) {
        // Em modo busca, navega na tabela filtrada
        int linhaAtual = tabelaLivros.getSelectedRow(); // Pega a linha selecionada
        
        // Verifica se não está na primeira linha
        if (linhaAtual > 0) {
            // Seleciona a linha anterior
            tabelaLivros.setRowSelectionInterval(linhaAtual - 1, linhaAtual - 1);
            
            // Pega o título do livro da linha anterior
            String titulo = (String) tabelaLivros.getModel().getValueAt(linhaAtual - 1, 1);
            
            // Busca o livro pelo título
            Livro livro = lista.buscarPorTitulo(titulo);
            
            // Exibe o livro no painel direito
            tela.getPainelDireito().exibirLivro(livro);
            
            // Atualiza o contador
            atualizarContador(linhaAtual - 1, tabelaLivros.getRowCount());
        }
    } else {
        // Em modo normal, volta na lista original
        lista.voltar();
        tela.atualizarInterface();
    }
});

        btRemover.addActionListener(e -> {
            if (!lista.estaVazia()) {
                lista.removerAtual();
                if (flag_tela == 1) {
                    painelEsquerdo.filtrarTabela(termoAtual, tipoAtual);
                    int filtrados = painelEsquerdo.getQuantidadeLinhasTabela();
                    atualizarContador(lista.getIndiceAtual(), filtrados);
                } else {
                    tela.atualizarInterface();
                }
            }
        });

        // btOrdenar.addActionListener(e -> {
        //     if (!lista.estaVazia()) {
        //         lista.ordenar();
        //         if (flag_tela == 1) {
        //             painelEsquerdo.filtrarTabela(termoAtual, tipoAtual);
        //             int filtrados = painelEsquerdo.getQuantidadeLinhasTabela();
        //             atualizarContador(lista.getIndiceAtual(), filtrados);
        //         } else {
        //             tela.atualizarInterface();
        //         }
        //     }
        // });

        btBuscar.addActionListener(e -> {
            JanelaBusca();
        });

        btListar.addActionListener(e -> {
            if (!lista.estaVazia()) {
                lista.listarTodos();
                flag_tela = 0;
                termoAtual = "";
                tela.atualizarInterface();
            }

        });
    }

    public void atualizarContador(int atual, int total) {
        if (total == 0) {
            lblContador.setText(" | Lista Vazia");
        } else {
            lblContador.setText(" | Livro: " + (atual + 1) + " / " + total);
        }
    }

    // Método responsável pela janela de busca
    private void JanelaBusca() {
        // Crio um painel e defino o layout como vertical
        JPanel painelBusca = new JPanel();
        painelBusca.setLayout(new BoxLayout(painelBusca, BoxLayout.Y_AXIS));

        // Aqui eu crio o array e a regra para as duas opções de busca e a caixa para
        // digitar a busca
        String[] opcoes = { "Buscar por Título", "Buscar por Autor" };
        JComboBox<String> cbBusca = new JComboBox<>(opcoes);
        JTextField txtBusca = new JTextField(20);

        // Adiciono os componentes ao painel
        painelBusca.add(new JLabel("Como gostaria de buscar?"));
        painelBusca.add(cbBusca);
        painelBusca.add(new JLabel("Digite a busca:"));
        painelBusca.add(txtBusca);

        // Aqui eu crio a janela que vai aparecer e seu retorno, com os botões ok e
        // cancelar e um ícone de interrogação para confirmar a busca
        int resultado = JOptionPane.showConfirmDialog(
                this,
                painelBusca,
                "Buscar Livro",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        // Verifico se o usuário clicou em ok
        if (resultado == JOptionPane.OK_OPTION) {
            String termoBusca = txtBusca.getText().trim(); // Salvo o valor digitado

            // Verifico se é vazio e dou o alerta
            if (termoBusca.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Por favor, digite um termo de busca!",
                        "Campo Vazio",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Se escolheu buscar por TÍTULO
            if (cbBusca.getSelectedIndex() == 0) {
                Livro livroEncontrado = lista.buscarPorTitulo(termoBusca); // Busco o livro pelo título

                // Se encontrado, mostro os dados
                if (livroEncontrado != null) {
                    JOptionPane.showMessageDialog(
                            this,
                            "✅ Livro encontrado!\n\n" +
                                    "Título: " + livroEncontrado.getTitulo() + "\n" +
                                    "Autor: " + livroEncontrado.getAutor() + "\n" +
                                    "Ano: " + livroEncontrado.getAnoPublicacao(),
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                    termoAtual = termoBusca;
                    tipoAtual = 0;
                    flag_tela = 1;
                    tela.getPainelEsquerdo().filtrarTabela(termoBusca, 0);

                    tela.getPainelDireito().exibirLivro(livroEncontrado);

                    int filtrados = tela.getPainelEsquerdo().getQuantidadeLinhasTabela();
                    atualizarContador(lista.getIndiceAtual(), filtrados);
                } else { // Se não, informo que o livro não foi encontrado
                    JOptionPane.showMessageDialog(
                            this,
                            "Nenhum livro encontrado!",
                            "Livro não encontrado",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
            // Se escolheu buscar por AUTOR
            else {
                List<Livro> livrosEncontrados = lista.buscarTodosPorAutor(termoBusca); // Busco todos os livros do autor

                // Se não encontrou nada
                if (livrosEncontrados == null || livrosEncontrados.size() == 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Autor não encontrado!",
                            "Autor não encontrado",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Se encontrou livros (um ou mais), exibe todos na tabela
                JOptionPane.showMessageDialog(
                        this,
                        "✅ " + livrosEncontrados.size() + " livro(s) encontrado(s) de: " + termoBusca,
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);

                // Move para o primeiro livro encontrado
                lista.buscarPorAutor(termoBusca);

                // Configura as variáveis de estado
                termoAtual = termoBusca;
                tipoAtual = 1;
                flag_tela = 1;

                // Atualiza a tabela com os livros encontrados
                tela.getPainelEsquerdo().filtrarTabela(termoBusca, 1);

                // Atualiza o painel direito com o primeiro livro
                tela.getPainelDireito().exibirLivro(livrosEncontrados.get(0));

                // Atualiza o contador
                int filtrados = tela.getPainelEsquerdo().getQuantidadeLinhasTabela();
                atualizarContador(0, filtrados);
            }
        }
    }
}

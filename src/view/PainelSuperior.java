package view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.*;

import model.Livro;
import repository.ListaLivrosDuplamenteEncadeada;

/**
* Classe responsavel pelo painel dos botoes e controles da parte superior.
*/

public class PainelSuperior extends JPanel{

    //Atributos dos botoes - Inserir, remover, Buscar, Anterior, Proximo:

    private JButton btInserir, btRemover, btBuscar, btAnterior, btProximo, btOrdenar;
    // Para mostrar a posição atual e botão para o usuário escolher como quer ordenar.
    private JComboBox<String> cbOrdenar;
    // Para mostrar a posição atual.
    private JLabel lblContador;

    private TelaPrincipal tela;
    private ListaLivrosDuplamenteEncadeada lista;

    // Construtor atualizado para receber as referências
    public PainelSuperior(TelaPrincipal tela, ListaLivrosDuplamenteEncadeada lista) {
        this.tela = tela;
        this.lista = lista;
        
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        btInserir = new JButton("INSERIR");
        btRemover = new JButton("REMOVER");
        btBuscar = new JButton("BUSCAR");
        btAnterior = new JButton("ANTERIOR");
        btProximo = new JButton("PROXIMO");
        lblContador = new JLabel(" | Livros: 0/0");
        btOrdenar = new JButton("Ordenar");

        this.add(btInserir);
        this.add(btRemover);
        this.add(btBuscar);
        this.add(new JLabel(" | "));
        this.add(btAnterior);
        this.add(btProximo);
        this.add(lblContador);
        this.add(new JLabel(" | "));
        this.add(btOrdenar);
        
        configurarEventos();
    }

    private void configurarEventos() {
        // Agora os botões do topo também controlam a lista!
        btProximo.addActionListener(e -> {
            lista.avancar();
            tela.atualizarInterface();
        });
        
        btAnterior.addActionListener(e -> {
            lista.voltar();
            tela.atualizarInterface();
        });

        btRemover.addActionListener(e -> {
            if (!lista.estaVazia()) {
                lista.removerAtual();
                tela.atualizarInterface();
            }
        });

        btOrdenar.addActionListener(e -> {
            if (!lista.estaVazia()) {
                lista.ordenar();
                tela.atualizarInterface();
            }
        });

        btBuscar.addActionListener(e -> {
            JanelaBusca();
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

    // Aqui eu crio o array e a regra para as duas opções de busca e a caixa para digitar a busca
    String[] opcoes = {"Buscar por Título", "Buscar por Autor"};
    JComboBox<String> cbBusca = new JComboBox<>(opcoes);
    JTextField txtBusca = new JTextField(20);

    // Adiciono os componentes ao painel
    painelBusca.add(new JLabel("Como gostaria de buscar?"));
    painelBusca.add(cbBusca);
    painelBusca.add(new JLabel("Digite a busca:"));
    painelBusca.add(txtBusca);

    // Aqui eu crio a janela que vai aparecer e seu retorno, com os botões ok e cancelar e um ícone de interrogação para confirmar a busca
    int resultado = JOptionPane.showConfirmDialog(
        this,
        painelBusca,
        "Buscar Livro",
        JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE
    );

    // Verifico se o usuário clicou em ok
    if (resultado == JOptionPane.OK_OPTION) {
        String termoBusca = txtBusca.getText().trim(); // Salvo o valor digitado

        // Verifico se é vazio e dou o alerta
        if (termoBusca.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Por favor, digite um termo de busca!",
                "Campo Vazio",
                JOptionPane.WARNING_MESSAGE
            );
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
                    JOptionPane.INFORMATION_MESSAGE
                );
                tela.atualizarInterface();
            } else { // Se não, informo que o livro não foi encontrado
                JOptionPane.showMessageDialog(
                    this,
                    "Nenhum livro encontrado!",
                    "Livro não encontrado",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        }
        // Se escolheu buscar por AUTOR
        else {
            List<Livro> livrosEncontrados = lista.buscarTodosPorAutor(termoBusca); // Busco todos os livros do autor

            // Se não encontrou nada
            if (livrosEncontrados == null || livrosEncontrados.size() == 0) {
                JOptionPane.showMessageDialog(
                    this,
                    "Nenhum livro encontrado!",
                    "Livro não encontrado",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // Se encontrou apenas UM livro
            if (livrosEncontrados.size() == 1) {
                Livro livro = livrosEncontrados.get(0);
                JOptionPane.showMessageDialog(
                    this,
                    "✅ Livro encontrado!\n\n" +
                    "Título: " + livro.getTitulo() + "\n" +
                    "Autor: " + livro.getAutor() + "\n" +
                    "Ano: " + livro.getAnoPublicacao(),
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
                );
                lista.buscarPorAutor(termoBusca);
                tela.atualizarInterface();
            }
            // Se encontrou VÁRIOS livros
            else {
                // Monto a mensagem com todos os livros encontrados
                StringBuilder mensagem = new StringBuilder();
                mensagem.append("⚠️ Foram encontrados ").append(livrosEncontrados.size()).append(" livros de ").append(termoBusca).append(":\n\n");
                for (int i = 0; i < livrosEncontrados.size(); i++) {
                    Livro livro = livrosEncontrados.get(i);
                    mensagem.append((i + 1)).append(". ").append(livro.getTitulo()).append(" (").append(livro.getAnoPublicacao()).append(")\n");
                }
                mensagem.append("\nDigite o número do livro que deseja:");

                // Mostro a lista e peço para escolher um livro
                String escolhaString = JOptionPane.showInputDialog(this, mensagem.toString());

                // Se clicou em Cancelar
                if (escolhaString == null) {
                    return;
                }

                try {
                    // Converto a escolha para número
                    int escolha = Integer.parseInt(escolhaString.trim());

                    // Verifico se o número é válido (entre 1 e o tamanho da lista)
                    if (escolha < 1 || escolha > livrosEncontrados.size()) {
                        JOptionPane.showMessageDialog(
                            this,
                            "❌ Escolha inválida! Digite um número entre 1 e " + livrosEncontrados.size(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }

                    // Pego o livro selecionado (escolha - 1 porque array começa em 0)
                    Livro livroSelecionado = livrosEncontrados.get(escolha - 1);

                    // Mostro o livro selecionado com os dados
                    JOptionPane.showMessageDialog(
                        this,
                        "✅ Livro selecionado!\n\n" +
                        "Título: " + livroSelecionado.getTitulo() + "\n" +
                        "Autor: " + livroSelecionado.getAutor() + "\n" +
                        "Ano: " + livroSelecionado.getAnoPublicacao(),
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                    );

                    // Busco o livro na lista para atualizar o "atual"
                    lista.buscarPorTitulo(livroSelecionado.getTitulo());
                    tela.atualizarInterface();

                } catch (NumberFormatException e) {
                    // Se digitar algo que não é número
                    JOptionPane.showMessageDialog(
                        this,
                        "❌ Digite um número válido!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }
}
}

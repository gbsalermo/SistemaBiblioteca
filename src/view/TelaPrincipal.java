package view;

// Testando como vai ser a tela principal ainda, com comandos básicos de Swing.
// Até aqui é apenas uma janela vazia.

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import repository.ListaLivrosDuplamenteEncadeada;
import model.Livro;

public class TelaPrincipal {

    // =========================
    // ATRIBUTOS PRINCIPAIS
    // =========================

    // Lista principal da aplicação
    private ListaLivrosDuplamenteEncadeada lista;

    // Janela principal do sistema
    private JFrame janela;

    // Painéis da interface
    private PainelSuperior painelSuperior;
    private PainelLista painelEsquerdo;
    private PainelDireito painelDireito;

    /*
     * A ideia aqui é manter apenas UMA instância da lista
     * e compartilhar essa mesma lista entre todos os painéis.
     * 
     * Isso evita inconsistências de dados.
     */

    // =====================================================
    // CONSTRUTOR PRINCIPAL
    // =====================================================

    /*
     * Recebe a lista pronta por parâmetro.
     * 
     * Isso é chamado de Injeção de Dependência:
     * a lista vem de fora ao invés de ser criada aqui dentro.
     */
    public TelaPrincipal(ListaLivrosDuplamenteEncadeada lista) {

        // Salva a referência da lista
        this.lista = lista;

        // Monta toda a interface gráfica
        inicializar();
    }

    // =====================================================
    // CONSTRUTOR PADRÃO
    // =====================================================

    /*
     * Caso a tela seja criada sem passar nenhuma lista,
     * cria automaticamente uma lista vazia.
     */
    public TelaPrincipal() {

        /*
         * this(...) chama outro construtor da mesma classe.
         * 
         * Nesse caso:
         * - Cria uma nova lista vazia
         * - Reaproveita o construtor principal
         */
        this(new ListaLivrosDuplamenteEncadeada());
    }

    // =====================================================
    // MÉTODO RESPONSÁVEL POR MONTAR A INTERFACE
    // =====================================================

    public void inicializar() {

        // =========================
        // CRIAÇÃO DA JANELA
        // =========================

        // Cria a janela principal
        janela = new JFrame("Sistema de Biblioteca");

        // Define tamanho da janela (largura x altura)
        janela.setSize(1000, 700);

        // Fecha completamente a aplicação ao clicar no X
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Centraliza a janela na tela
        janela.setLocationRelativeTo(null);

        // =========================
        // PAINEL PRINCIPAL
        // =========================

        /*
         * BorderLayout divide a tela em:
         * NORTH, SOUTH, EAST, WEST e CENTER
         */
        JPanel painelPrincipal = new JPanel(new BorderLayout());

        // =========================
        // CRIAÇÃO DOS PAINÉIS
        // =========================

        /*
         * Passamos:
         * - a própria TelaPrincipal
         * - a lista principal
         * 
         * Assim os painéis conseguem:
         * - acessar os dados
         * - atualizar a interface
         * - chamar métodos da tela principal
         */

        painelEsquerdo = new PainelLista(this, lista);

        painelSuperior = new PainelSuperior(this, lista, painelEsquerdo);

        painelDireito = new PainelDireito(this, lista);

        // =========================
        // ADIÇÃO DOS PAINÉIS
        // =========================

        // Painel dos botões superiores
        painelPrincipal.add(painelSuperior, BorderLayout.NORTH);

        /*
         * O painel direito foi removido temporariamente.
         * 
         * Caso queira usar novamente:
         * painelPrincipal.add(painelDireito, BorderLayout.WEST);
         */

        // Tabela principal fica no centro
        painelPrincipal.add(painelEsquerdo, BorderLayout.CENTER);

        // =========================
        // FINALIZAÇÃO DA JANELA
        // =========================

        // Adiciona o painel principal dentro da janela
        janela.add(painelPrincipal);

        // Torna a janela visível
        janela.setVisible(true);

        // Atualiza todos os componentes da interface
        atualizarInterface();
    }

    // =====================================================
    // MÉTODO CENTRAL DE ATUALIZAÇÃO DA INTERFACE
    // =====================================================

    /**
     * Sempre que algo mudar:
     * - adicionar livro
     * - remover
     * - navegar
     * - buscar
     * 
     * este método deve ser chamado.
     */
    public void atualizarInterface() {

        // =========================
        // ATUALIZA TABELA
        // =========================

        // Recarrega os dados da tabela
        painelEsquerdo.atualizarTabela();

        // =========================
        // ATUALIZA CAMPOS DA DIREITA
        // =========================

        /*
         * Exibe o livro atual no formulário.
         * 
         * Está comentado no momento porque o painel direito
         * não está sendo exibido na tela.
         */

        // painelDireito.exibirLivro(lista.getAtual());

        // =========================
        // ATUALIZA CONTADOR
        // =========================

        /*
         * Atualiza o texto:
         * "Livro X / Total"
         */
        painelSuperior.atualizarContador(
                lista.getIndiceAtual(),
                lista.getTotalLivros()
        );
    }

    // =====================================================
    // GETTERS
    // =====================================================

    /*
     * Esses métodos permitem que outros painéis
     * acessem componentes da TelaPrincipal.
     */

    public PainelLista getPainelEsquerdo() {
        return painelEsquerdo;
    }

    public PainelDireito getPainelDireito() {
        return painelDireito;
    }

    public PainelSuperior getPainelSuperior() {
        return painelSuperior;
    }
}
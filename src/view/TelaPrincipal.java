package view;

//Testando como vai ser a tela principal ainda, com comandos basicos de swing.
//Até aqui é apenas uma janela vazia.
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import repository.ListaLivrosDuplamenteEncadeada;
import model.Livro;

public class TelaPrincipal {

    // Atributos:

    private ListaLivrosDuplamenteEncadeada lista; //teste
    private JFrame janela;
    private PainelSuperior painelSuperior;
    private PainelEsquerdo painelEsquerdo;
    private PainelDireito painelDireito;
            
    // 1. A instância única da sua lista.
    //private ListaLivrosDuplamenteEncadeada lista = new ListaLivrosDuplamenteEncadeada();
    //teste
   

     // Construtor principal da classe, recebendo a lista pronta e realizando a injeção de dependência
    public TelaPrincipal(ListaLivrosDuplamenteEncadeada lista) {
    this.lista = lista;
    // Chama o método responsável por montar a interface gráfica.    
    inicializar();
    }

    public TelaPrincipal() {
        // Cria uma nova lista vazia e chama o construtor principal.
        // Isso garante que a inicialização sempre siga o mesmo fluxo.
        this(new ListaLivrosDuplamenteEncadeada());
    }

    //Mu

    public void inicializar() {
        
        // Criação da janela e o titulo.
        janela = new JFrame("Sistema de Biblioteca");
        // Tamanho da tela, largxalt em pixels.
        janela.setSize(1000, 700);
        // Comportamento para fechar a janela e centralizar janela.
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        //Criando painel com BorderLayout
        JPanel painelPrincipal = new JPanel(new BorderLayout());

        // 2. Passar a 'lista' e a própria 'TelaPrincipal' para os painéis.
        // Isso permite que os botões dentro dos painéis chamem métodos daqui.
        painelEsquerdo = new PainelEsquerdo(this, lista);
        painelSuperior = new PainelSuperior(this, lista, painelEsquerdo); 
        painelDireito = new PainelDireito(this, lista);
        // Adiciona cada painel em uma posição.
        painelPrincipal.add(painelSuperior, BorderLayout.NORTH);
        painelPrincipal.add(painelDireito, BorderLayout.WEST);
        // Center é melhor para tabelas.
        painelPrincipal.add(painelEsquerdo, BorderLayout.CENTER);
        // Adicionando o painel principal na janela.
        janela.add(painelPrincipal);
        // Mostra a janela na tela
        janela.setVisible(true);
        // Inicializa a tela
        atualizarInterface();
    }

    /**
    * Método que orquestra a atualização de todos os componentes da tela.
    * Sempre que algo mudar na lista (add, remover, buscar, navegar), chamamos este método.
    */
    public void atualizarInterface() {
        // 1. Atualiza a tabela (O método correto é atualizarTabela)
        painelEsquerdo.atualizarTabela();
    
        // 2. Atualiza os campos de texto no painel direito
        painelDireito.exibirLivro(lista.getAtual());
    
        // 3. Atualiza o contador lá em cima
        painelSuperior.atualizarContador(lista.getIndiceAtual(), lista.getTotalLivros());
    }
    
    public PainelEsquerdo getPainelEsquerdo(){
        return painelEsquerdo;
    }

    public PainelDireito getPainelDireito() {
    return painelDireito;
}
}
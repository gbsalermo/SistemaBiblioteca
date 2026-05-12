package repository;

import java.util.ArrayList;
import java.util.List;

// Importação das classes necessárias localizadas no pacote model.
// O 'Livro' representa os dados e o 'No' representa o elo da corrente.
import model.Livro;
import model.No;

/**
* Classe responsável pela implementação e gerenciamento da Lista Duplamente Encadeada.
* Ela atua como o repositório de dados do sistema, controlando como os livros são armazenados, organizados e acessados na memória.
*/

public class ListaLivrosDuplamenteEncadeada {

    // Atributos:

    // Referência para o primeiro nó da lista. É o ponto de partida para qualquer percurso.
    private No primeiro;
    // Referência para o último nó da lista. Permite inserções rápidas no final e percursos de trás para frente.
    private No ultimo;
    // Referência auxiliar que funciona como um "marcador de posição" atual. Essencial para a navegação na interface (Próximo/Anterior).
    private No atual;
    // Atributo inteiro que armazena a quantidade total de elementos (livros) na lista. Facilita o controle de limites e a exibição de informações (Ex: "Livro 1 de 10").
    private int totalLivros;

    /**
    * Construtor da classe.
    * Inicializa uma lista vazia, definindo os ponteiros como null e o tamanho como 0.
    */

    public ListaLivrosDuplamenteEncadeada() {
        this.primeiro = null;
        this.ultimo = null;
        this.atual = null;
        this.totalLivros = 0;
    }

    // Getters para o Swing:
    
    public Livro getAtual() {
        return (atual != null) ? atual.getLivro() : null;
    }

    public int getTotalLivros() {
        return totalLivros;
    }

    public No getPrimeiro() {
    return primeiro;
}

    /**
    * Retorna o índice (posição base zero) do nó 'atual' na lista.
    * Retorna o índice do livro atual ou -1 se a lista estiver vazia ou nada selecionado.
    */

    public int getIndiceAtual() {
        // Se a lista está vazia ou o marcador atual não aponta para ninguém, o índice é inexistente.
        if (primeiro == null || atual == null) {
            return -1;
        }
        // O índice técnico começa em 0 (o 'primeiro' é o índice 0).
        int indice = 0;
        No aux = primeiro;
        // Percorre a lista a partir do início até encontrar o nó 'atual'.
        while (aux != null && aux != atual) {
            indice++;
            aux = aux.getProximo();
        }
        // Verifica se o ponteiro auxiliar encontrou o nó atual antes de chegar ao fim da lista.
        // Se o loop terminou porque aux == atual, retorna o contador.
        if (aux != null) {
            return indice;
        }
        // Caso contrário (segurança), se aux ficou nulo sem achar o atual, retorna -1.
        else {
            return -1;
        }
    }

    /**
    * Verifica se a lista está vazia para habilitar/desabilitar botões no Swing.
    */

    public boolean estaVazia() {
        return primeiro == null;
    }

    // Métodos de navegação (Essenciais para a interface Swing):

    // Avançar:

    /**
    * Move o marcador 'atual' para o próximo nó da lista e retorna o livro correspondente.
    * Caso o cursor já esteja no último nó ou a lista esteja vazia, retorna o livro atual.
    */

    public Livro avancar() {
        // Verifica se a lista está vazia.
        if (primeiro == null) {
            return null;
        }
        // Se o marcador atual for nulo, começamos pelo primeiro livro.
        if (atual == null) {
            atual = primeiro;
        }
        else {
            // Se houver um próximo nó (livro), acançamos o marcador atual.
            if (atual.getProximo() != null) {
                atual = atual.getProximo();
            }
        }
        return atual.getLivro();
    }

    // Voltar:

    /**
    * Move o marcador 'atual' para o nó anterior da lista e retorna o livro.
    * Caso o cursor já esteja no primeiro nó (inicio), ele permanece no primeiro livro.
    */

    public Livro voltar() {
        // Verifica se a lista está vazia.
        if (primeiro == null) {
            return null;
        }
        // Se o marcador atual for nulo, assume-se o início da lista.
        if (atual == null) {
            atual = primeiro;
        }
        else {
            // Se houver um nó (livro) anterior, movemos o marcador atual para trás (volta).
            if (atual.getAnterior() != null) {
                atual = atual.getAnterior();
            }
        }
        return atual.getLivro();
    }
    // Comparações auxiliares:

    /*
    * positivo = maior
    * negativo = menor
    * zero = iguais 
    */
    private int compararTitulo(No a, No b){
        // Compara primeiro o titulo, se sao iguais, faz o desempate no proximo
       if(!a.getLivro().getTitulo().equalsIgnoreCase(b.getLivro().getTitulo())){
        return a.getLivro().getTitulo().compareToIgnoreCase(b.getLivro().getTitulo());
       }
       // Compara o autor, se sao iguais, faz o desempate no proximo
       if(!a.getLivro().getAutor().equalsIgnoreCase(b.getLivro().getAutor())){
        return a.getLivro().getAutor().compareToIgnoreCase(b.getLivro().getAutor());
       }
       // finaliza o desempate comparando o ano
       if(a.getLivro().getAnoPublicacao() != b.getLivro().getAnoPublicacao()){
        return a.getLivro().getAnoPublicacao() - b.getLivro().getAnoPublicacao();
       }
       // os livros sao iguais
       return 0;
    }
    private int compararAutor(No a, No b){
        // Compara o autor, se sao iguais, faz o desempate no proximo
        if(!a.getLivro().getAutor().equalsIgnoreCase(b.getLivro().getAutor())){
         return a.getLivro().getAutor().compareToIgnoreCase(b.getLivro().getAutor());
        }
        // Compara primeiro o titulo, se sao iguais, faz o desempate no proximo
       if(!a.getLivro().getTitulo().equalsIgnoreCase(b.getLivro().getTitulo())){
        return a.getLivro().getTitulo().compareToIgnoreCase(b.getLivro().getTitulo());
       }
       // finaliza o desempate comparando o ano
       if(a.getLivro().getAnoPublicacao() != b.getLivro().getAnoPublicacao()){
        return a.getLivro().getAnoPublicacao() - b.getLivro().getAnoPublicacao();
       }
       // os livros sao iguais
       return 0;
    }
    private int compararAno(No a, No b){
        // finaliza o desempate comparando o ano
        if(a.getLivro().getAnoPublicacao() != b.getLivro().getAnoPublicacao()){
         return a.getLivro().getAnoPublicacao() - b.getLivro().getAnoPublicacao();
        }
        // Compara primeiro o titulo, se sao iguais, faz o desempate no proximo
       if(!a.getLivro().getTitulo().equalsIgnoreCase(b.getLivro().getTitulo())){
        return a.getLivro().getTitulo().compareToIgnoreCase(b.getLivro().getTitulo());
       }
       // Compara o autor, se sao iguais, faz o desempate no proximo
       if(!a.getLivro().getAutor().equalsIgnoreCase(b.getLivro().getAutor())){
        return a.getLivro().getAutor().compareToIgnoreCase(b.getLivro().getAutor());
       }
       // os livros sao iguais
       return 0;
    }

    // Trocar com o próximo:

    /**
    * Realiza a troca de posição entre um nó e o seu próximo na lista.
    * Ajusta os ponteiros vizinhos para manter a integridade da lista dupla.
    * O nó que "subirá" uma posição na direção do fim da lista.
    * Retorna o nó que agora ocupa a posição original do 'node'.
    */

    private No trocarComProximo(No node) {
        // Armazena a referência do próximo nó para não perdê-la durante a troca.
        No proximoNo = node.getProximo();

        // 1. Ajuste dos vizinhos externos (quem está em volta do par).
        // Ajusta o vizinho da esquerda:
        // Se houver alguém antes do 'node', esse alguém agora apontará para o 'proximoNo'.
        if (node.getAnterior() != null) {
            node.getAnterior().setProximo(proximoNo);
        }
        // Se não houver ninguém antes, o 'node' era o início. Agora o 'proximoNo' vira o novo primeiro.
        else {
            primeiro = proximoNo;
        }
        // Ajusta o vizinho da direita:
        // Se houver alguém após o 'proximoNo', esse alguém agora terá o 'node' como seu anterior.
        if (proximoNo.getProximo() != null) {
            proximoNo.getProximo().setAnterior(node);
        }
        else {
            // Se não houver ninguém depois, o 'proximoNo' era o fim. Agora o 'node' vira o novo último.
            ultimo = node;
        }
        // 2. Troca dos ponteiros internos (a "dança" entre os dois nós).
        // O 'node' pula para a frente: o próximo dele passa a ser o que vinha depois do seu parceiro
        node.setProximo(proximoNo.getProximo());
        // O 'proximoNo' recua: o anterior dele passa a ser o que vinha antes do 'node'
        proximoNo.setAnterior(node.getAnterior());
        // Finaliza a conexão entre os dois: eles passam a apontar um para o outro na nova ordem
        proximoNo.setProximo(node);
        node.setAnterior(proximoNo);
        // Retorna o nó que agora ocupa a posição mais próxima do início da lista
        return proximoNo;
    }

    // Buscar por título:

    /**
    * Busca um livro pelo título na lista.
    * Se encontrado, o marcador 'atual' é movido para este nó.
    * @param titulo O título do livro a ser pesquisado.
    * @return O objeto Livro encontrado ou null caso não exista.
    */

    public Livro buscarPorTitulo(String titulo) {
        // Começamos a busca sempre pelo início da lista.
        No aux = primeiro;
        // Percorre a lista enquanto houver nós.
        while (aux != null) {
            // Compara o título do livro do nó atual com o título buscado.
            if (aux.getLivro().getTitulo().toLowerCase().contains(titulo.toLowerCase())) { //Adicionei o toLowerCase e o contains para melhorar a eficiencia da pesquisa
                this.atual = aux;
                return aux.getLivro();
            }
            // Avança para o próximo nó para continuar a busca.
            aux = aux.getProximo();
        }
        // Se o loop terminar sem retornar, o livro não foi encontrado.
        return null;
    }

    // Buscar por autor:

    /**
    * Busca um livro pelo autor na lista.
    * Se encontrado, o marcador 'atual' é movido para este nó.
    * @param autor O nome do autor a ser pesquisado.
    * @return O objeto Livro encontrado ou null caso não exista.
    */

    public Livro buscarPorAutor(String autor) {
        // Começamos a busca sempre pelo início da lista.
        No aux = primeiro;
        // Percorre a lista enquanto houver nós.
        while (aux != null) {
            // Compara o autor do livro do nó atual com o autor buscado.
            if (aux.getLivro().getAutor().toLowerCase().contains(autor.toLowerCase())) {
                this.atual = aux;
                return aux.getLivro();
            }
            // Avança para o próximo nó para continuar a busca.
            aux = aux.getProximo();
        }
        // Se o loop terminar sem retornar, o livro não foi encontrado.
        return null;
    }

    // Ordenar por ano de publicação:

    /**
    * Ordena a lista de livros por ano de publicação utilizando o algoritmo Bubble Sort.
    * O método percorre a lista repetidamente, comparando nós adjacentes e realizando a troca de posições quando o livro atual é mais recente que o próximo.
    * O processo se repete até que uma passagem completa ocorra sem nenhuma troca, garantindo que todos os elementos estejam em ordem crescente.
    */

    public void ordenarPorColuna(int coluna) {
    if (primeiro == null) return;
    boolean trocado;

    do {
        No aux = primeiro;
        trocado = false;

        while (aux != null && aux.getProximo() != null) {
            int cmp = switch (coluna) {
                case 1 -> compararTitulo(aux, aux.getProximo());
                case 2 -> compararAutor(aux, aux.getProximo());
                case 3 -> compararAno(aux, aux.getProximo());
                default -> compararTitulo(aux, aux.getProximo());
            };

            if (cmp > 0) {
                aux = trocarComProximo(aux);
                trocado = true;
            } else {
                aux = aux.getProximo();
            }
        }
    } while (trocado);
    }

    // Inserir:

    // Inserir no fim:

    /**
    * Adiciona um livro ao final da lista (usando o ponteiro 'ultimo').
    */
    public void adicionarNoFim(Livro livro) {
        No novoNo = new No(livro);
        if (primeiro == null) {
            primeiro = ultimo = atual = novoNo;
        }
        else {
            // Aqui o atributo 'ultimo' é essencial!
            ultimo.setProximo(novoNo);
            novoNo.setAnterior(ultimo);
            ultimo = novoNo;
        }
        totalLivros++;
    }

    // Inserir no início:

    /**
    * Adiciona um livro ao início da lista.
    */

    public void adicionarNoInicio(Livro livro) {
        No novoNo = new No(livro);
        if (primeiro == null) {
            primeiro = ultimo = atual = novoNo;
        }
        else {
            novoNo.setProximo(primeiro);
            primeiro.setAnterior(novoNo);
            primeiro = novoNo;
        }
        totalLivros++;
    }

    // Inserir na posição:

    /**
    * Insere um livro em uma posição específica (base zero).
    * Se a posição for 0, insere no início.
    * Se a posição for igual ao total de livros, insere no fim.
    */

    public void adicionarNaPosicao(Livro livro, int posicao) {
        // Validação de segurança: posição negativa ou além do limite
        if (posicao < 0 || posicao > totalLivros) {
            throw new IndexOutOfBoundsException("Posição inválida!");
        }

        if (posicao == 0) {
            adicionarNoInicio(livro);
        } else if (posicao == totalLivros) {
            adicionarNoFim(livro);
        } else {
            // Inserção no meio da lista
            No novoNo = new No(livro);
            No aux = primeiro;

            // Caminha até o nó que atualmente ocupa a posição desejada
            for (int i = 1; i < posicao; i++) {
                aux = aux.getProximo();
            }

            // O nó anterior ao que encontramos
            No anteriorAoAux = aux.getAnterior();

            // Fazendo as conexões:
            // 1. O novo nó aponta para quem estava lá e para quem vinha antes
            novoNo.setProximo(aux);
            novoNo.setAnterior(anteriorAoAux);

            // 2. O nó de trás agora aponta para o novo
            anteriorAoAux.setProximo(novoNo);

            // 3. O nó da frente agora aponta para o novo (voltando)
            aux.setAnterior(novoNo);

            totalLivros++;
            // Opcional: move o marcador atual para a nova inserção
            this.atual = novoNo;
        }
    }

    // Remover atual:

    /**
    * Remove o livro que está atualmente selecionado pelo marcador 'atual'.
    */

    public void removerAtual() {
        if (atual == null) return;

        // Se for o único nó da lista
        if (atual == primeiro && atual == ultimo) {
            primeiro = ultimo = atual = null;
        } 
        // Se for o primeiro
        else if (atual == primeiro) {
            primeiro = primeiro.getProximo();
            primeiro.setAnterior(null);
            atual = primeiro;
        } 
        // Se for o último
        else if (atual == ultimo) {
            ultimo = ultimo.getAnterior();
            ultimo.setProximo(null);
            atual = ultimo;
        } 
        // Se estiver no meio
        else {
            No ant = atual.getAnterior();
            No prox = atual.getProximo();
            ant.setProximo(prox);
            prox.setAnterior(ant);
            // Move o marcador para o próximo após remover
            atual = prox;
        }
        totalLivros--;
    }

    // Listar:

    /**
    * Retorna uma lista contendo todos os livros da estrutura.
    * Útil para exibir em tabelas ou relatórios na interface Swing.
    */
    /*
    public java.util.List<Livro> listarTodos() {
        java.util.List<Livro> listaTemporaria = new java.util.ArrayList<>();
        No aux = primeiro;
        while (aux != null) {
            listaTemporaria.add(aux.getLivro());
            aux = aux.getProximo();
        }
        return listaTemporaria;
    }
    */
   
    public ListaLivrosDuplamenteEncadeada listarTodos() {
        // Cria uma nova lista duplamente encadeada vazia
        // Essa lista vai armazenar a cópia dos livros
        ListaLivrosDuplamenteEncadeada listaTemporaria = new ListaLivrosDuplamenteEncadeada();

        // Cria um nó auxiliar que começa apontando para o primeiro nó da lista original
        No aux = primeiro;

        // Percorre a lista enquanto o nó atual não for nulo
        while (aux != null) {

            // Cria um novo nó copiando o livro do nó atual
            // (não copia o nó inteiro, só o objeto Livro)
            No novo = new No(aux.getLivro());

            // Verifica se a lista temporária ainda está vazia
            if (listaTemporaria.primeiro == null) {

                // Se estiver vazia, o novo nó será o primeiro e o último ao mesmo tempo
                listaTemporaria.primeiro = novo;
                listaTemporaria.ultimo = novo;

            } else {

                // Liga o último nó atual da lista temporária ao novo nó
                listaTemporaria.ultimo.setProximo(novo);

                // Faz o novo nó apontar para o nó anterior (duplamente encadeada)
                novo.setAnterior(listaTemporaria.ultimo);

                // Atualiza o ponteiro "último" para o novo nó inserido
                listaTemporaria.ultimo = novo;
            }

            // Avança para o próximo nó da lista original
            aux = aux.getProximo();
        }

        // Retorna a nova lista contendo todos os livros copiados
        return listaTemporaria;
    }

   // Método para buscar por autor retornando uma nova Lista encadeada com os resultados
    public ListaLivrosDuplamenteEncadeada buscarTodosPorAutor(String autor) {
        // Criamos uma nova lista para armazenar os resultados encontrados
        ListaLivrosDuplamenteEncadeada resultados = new ListaLivrosDuplamenteEncadeada();
        
        No aux = primeiro;
        boolean primeiroEncontrado = true;

        while (aux != null) {
            // Verifica se o nome do autor contém o trecho pesquisado (ignora maiúsculas/minúsculas)
            if (aux.getLivro().getAutor().toLowerCase().contains(autor.toLowerCase())) {
                
                // Em vez de ArrayList.add, usamos o método de inserir da sua própria lista
                // Supondo que seu método de inserir se chame 'adicionarNoFinal' ou 'inserir'
                resultados.adicionarNoFim(aux.getLivro());

                // Se for o primeiro resultado da busca, posiciona o ponteiro 'atual' da lista principal nele
                if (primeiroEncontrado) {
                    this.atual = aux;
                    primeiroEncontrado = false;
                }
            }
            aux = aux.getProximo();
        }
        
        return resultados; // Retorna a lista própria com os livros filtrados
    }
}
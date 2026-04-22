package model;

/**
 * Classe Model que representa a entidade Livro.
 * Responsável por armazenar os atributos e os dados associados ao objeto livro.
*/

public class Livro {

    // Contador estático compartilhado entre todos os objetos Livro.
    // É utilizado para gerar identificadores (IDs) únicos automáticos para cada objeto Livro criado.
    private static int contadorId = 0;

    // Atributos:

    private final int id;
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private String genero;
    private String editora;
    // Indica se o livro está emprestado (true) ou disponível (false).
    private boolean emprestado;

    /**
    * Construtor da classe Livro.
    * Inicializa o objeto Livro com título, autor, ano de publicação, gênero e editora fornecidos como parâmetros.
    * O identificador do livro é gerado automaticamente por meio de um contador estático e o status de empréstimo é definido inicialmente como falso (indicando que o livro está disponível).
    */

    public Livro(String titulo, String autor, int anoPublicacao, String genero, String editora) {
        this.id = ++contadorId;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.genero = genero;
        this.editora = editora;
        this.emprestado = false;
    }

    // Métodos getters e setters para acesso e modificação dos atributos do objeto Livro:

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public boolean isEmprestado() {
        return emprestado;
    }

    public void setEmprestado(boolean emprestado) {
        this.emprestado = emprestado;
    }

    // Método equals sobrescrito para comparar objetos Livro com base no identificador único (id):

    @Override
    public boolean equals(Object obj) {
        // Os dois objetos são o mesmo na memória?
        if (this == obj) {
            return true;
        }
        // O objeto comparado é null?
        if (obj == null) {
            return false;
        }
        // Os objetos são de classes diferentes?
        if (getClass() != obj.getClass()) {
            return false;
        }
        // O objeto é um livro!
        // Faremos uma conversão de tipo chamada de casting.
        // Isso é necessário porque na assinatura do método o parâmetro recebido é Object obj e Object não tem o atributo id.
        // A próxima linha converte obj de Object para Livro e agora podemos acessar other.id.
        Livro other = (Livro) obj;
        // Retorna true se os objetos representarem o mesmo Livro (mesmo id), caso contrário retorna false. 
        return this.id == other.id;
    }

    // Método hashCode sobrescrito para manter o contrato com o método equals.
    // Garante a identificação correta do objeto em coleções que utilizam tabelas hash (como HashSet e HashMap).
    
    @Override
    public int hashCode() {
        // Como o nosso id já é um número inteiro único, podemos usá-lo diretamente como o código hash.
        return Integer.hashCode(id);
    }

    // Método toString sobrescrito para exibir os dados do objeto Livro em formato legível:

    @Override
    public String toString() {
        String status;
        if (emprestado) {
            status = "Emprestado";
        }
        else {
            status = "Disponível";
        }
        return "Livro id = " + id +
        "\nTítulo = " + titulo + 
        "\nAutor = " + autor + 
        "\nAno de Publicação = " + anoPublicacao +
        "\nStatus = " + status;
    }
}

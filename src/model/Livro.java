package model;


//Classe responsavel pelos atributos e dados do objeto livro
public class Livro {

    // gerador de id automatico baseado na anotação @GeneratedValue de JPA(SPRINGBOOT)
    private static int contadorId = 0;

    // ATRIBUTOS

    private int id;
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private String genero;
    private String editora;

    // Construtor

    public Livro(String titulo, String autor, int anoPublicacao, String genero, String editora) {
        this.id = ++contadorId;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.genero = genero;
        this.editora = editora;

    }

     // Getters e setters
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

     //hashCode e equals do id
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Livro other = (Livro) obj;
        if (id != other.id)
            return false;
        return true;
    }

    //ToString basico
    @Override
    public String toString() {
        return "Livro id=" + id +
        "\ntitulo=" + titulo + 
        "\nautor=" + autor + 
        "\n anoPublicacao=" + anoPublicacao;
    }

}

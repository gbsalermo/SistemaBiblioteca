package util;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.No;
import repository.ListaLivrosDuplamenteEncadeada;

public class CsvExporter {

    private static final String BASE = "data";
    private static final String CABECALHO = "id,titulo,autor,anoPublicacao,genero,editora";
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH-mm-ss");

    public static void exportar(ListaLivrosDuplamenteEncadeada lista) {
        LocalDateTime agora = LocalDateTime.now();

        Path pasta = Paths.get(BASE,
            "year="  + agora.getYear(),
            "month=" + String.format("%02d", agora.getMonthValue()),
            "day="   + String.format("%02d", agora.getDayOfMonth())
        );

        try {
            Files.createDirectories(pasta);

            // Nome único por timestamp: livros_14-32-01.csv
            Path arquivo = pasta.resolve("livros_" + agora.format(FMT) + ".csv");

            try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo.toFile()))) {
                pw.println(CABECALHO);
                No aux = lista.getPrimeiro();
                while (aux != null) {
                    var l = aux.getLivro();
                    pw.printf("%d,\"%s\",\"%s\",%d,\"%s\",\"%s\"%n",
                        l.getId(), l.getTitulo(), l.getAutor(),
                        l.getAnoPublicacao(), l.getGenero(), l.getEditora());
                    aux = aux.getProximo();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao exportar CSV: " + e.getMessage());
        }
    }
}
package util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DataLivro {
    
    /**
     * Retorna a data atual como LocalDate
     */
    public static LocalDate obterDataAtual() {
        return LocalDate.now();  // ✅ Retorna LocalDate, não String!
    }
    
    /**
     * Formata uma LocalDate para String no formato DD/MM/YYYY
     */
    public static String formatarData(LocalDate data) {
        if (data == null) {
            return "Sem empréstimo";
        }
        return data.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * Calcula quantos dias passaram desde o empréstimo
     */
    public static long diasDesdeEmprestimo(LocalDate dataEmprestimo) {

        if (dataEmprestimo == null) {
            return 0;
        }

        return ChronoUnit.DAYS.between(
            dataEmprestimo,
            LocalDate.now()
        );
    }
}
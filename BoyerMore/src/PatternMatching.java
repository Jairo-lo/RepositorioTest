import java.util.ArrayList;
import java.util.List;

public class PatternMatching {
    // Algoritmo de Boyer-Moore
    public static class BoyerMoore {
        private final int R;     // tamaño del alfabeto
        private int[] right;     // tabla de "mal prefijo"
        private String pattern;  // el patrón a buscar
        private long preprocessingCost;  // contador de operaciones de preprocesamiento
        private long searchCost;         // contador de operaciones de búsqueda

        // BOYER MOORE
        public BoyerMoore(String pattern) {
            this.R = 256;
            this.pattern = pattern;
            this.preprocessingCost = 0;

            // Inicializar la tabla de "mal prefijo"
            right = new int[R];
            for (int c = 0; c < R; c++) {
                right[c] = -1;
                preprocessingCost++;  // O(R)
            }
            for (int j = 0; j < pattern.length(); j++) {
                right[pattern.charAt(j)] = j;
                preprocessingCost++;  // O(m)
            }
        }

        // Método para buscar el patrón en el texto
        public List<Integer> search(String text) {
            int M = pattern.length();
            int N = text.length();
            int skip;
            searchCost = 0;
            List<Integer> positions = new ArrayList<>();

            for (int i = 0; i <= N - M; i += skip) {  // Bucle exterior
                skip = 0;
                for (int j = M - 1; j >= 0; j--) {  // Bucle interior
                    searchCost++;  // Cada comparación cuenta como una operación
                    if (pattern.charAt(j) != text.charAt(i + j)) {
                        skip = Math.max(1, j - right[text.charAt(i + j)]);
                        searchCost++;  // Cada cálculo de salto cuenta como una operación
                        break;
                    }
                }
                if (skip == 0) {
                    positions.add(i);
                    i++;
                }
            }
            return positions;  // Lista de posiciones donde se encontró el patrón
        }

        public long getPreprocessingCost() {
            return preprocessingCost;
        }

        public long getSearchCost() {
            return searchCost;
        }
    }

    // Algoritmo KMP
    public static class KMP {
        private int[] lps; // Longest Prefix Suffix array
        private String pattern;

        public KMP(String pattern) {
            this.pattern = pattern;
            lps = new int[pattern.length()];
            preprocessPattern();
        }

        private void preprocessPattern() {
            int length = 0;
            int i = 1;
            lps[0] = 0;

            while (i < pattern.length()) {
                if (pattern.charAt(i) == pattern.charAt(length)) {
                    length++;
                    lps[i] = length;
                    i++;
                } else {
                    if (length != 0) {
                        length = lps[length - 1];
                    } else {
                        lps[i] = 0;
                        i++;
                    }
                }
            }
        }

        public List<Integer> search(String text) {
            List<Integer> positions = new ArrayList<>();
            int i = 0, j = 0;

            while (i < text.length()) {
                if (pattern.charAt(j) == text.charAt(i)) {
                    i++;
                    j++;
                }

                if (j == pattern.length()) {
                    positions.add(i - j);
                    j = lps[j - 1];
                } else if (i < text.length() && pattern.charAt(j) != text.charAt(i)) {
                    if (j != 0) {
                        j = lps[j - 1];
                    } else {
                        i++;
                    }
                }
            }

            return positions;
        }
    }

    public static void main(String[] args) {
        String pattern = "TCCTATTCTT";
        String text = "TTATAGATCTCGTATTCTTTTATAGATCTCCTATTCTT";

        // Boyer-Moore
        BoyerMoore bm = new BoyerMoore(pattern);
        long bmStart = System.nanoTime();
        List<Integer> bmPositions = bm.search(text);
        long bmEnd = System.nanoTime();
        long bmTime = bmEnd - bmStart;

        System.out.println("\nBoyer-Moore:");
        System.out.println("Pattern: " + pattern);
        System.out.println("Text: " + text);
        System.out.println("Positions: " + bmPositions);
        System.out.println("Occurrences: " + bmPositions.size());
        System.out.println("Preprocessing cost: " + bm.getPreprocessingCost() + " operations.");
        System.out.println("Search cost: " + bm.getSearchCost() + " operations.");
        System.out.println("Time taken: " + bmTime + " nanoseconds.\n");
        // KMP
        KMP kmp = new KMP(pattern);
        long kmpStart = System.nanoTime();
        List<Integer> kmpPositions = kmp.search(text);
        long kmpEnd = System.nanoTime();
        long kmpTime = kmpEnd - kmpStart;

        System.out.println("KMP:");
        System.out.println("Pattern: " + pattern);
        System.out.println("Text: " + text);
        System.out.println("Positions: " + kmpPositions);
        System.out.println("Occurrences: " + kmpPositions.size());
        System.out.println("Time taken: " + kmpTime + " nanoseconds.\n");
    }
}

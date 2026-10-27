import java.util.Random;

public class ReHash {
    static final int TAMANHO = 100;
    static final int INSERCOES = 100;
    static final int LIMITE_OCUPACAO = 90;
    static final int LIMITE = 100000;
    static final long SEMENTE = 1090L;

    public static void main(String[] args) {
        int[] tabela = new int[TAMANHO];
        boolean[] ocupado = new boolean[TAMANHO];

        int[] inseridos = new int[INSERCOES];
        int[] colisoesDoElemento = new int[INSERCOES];
        int quantidadeInserida = 0;
        int totalColisoes = 0;
        int bloqueados = 0;

        Random random = new Random(SEMENTE);
        for (int n = 0; n < INSERCOES; n++) {
            int valor = random.nextInt(LIMITE + 1);
            if (ocupacao(ocupado) >= LIMITE_OCUPACAO) {
                bloqueados++;
            } else {
                int colisoes = inserir(tabela, ocupado, valor);
                inseridos[quantidadeInserida] = valor;
                colisoesDoElemento[quantidadeInserida] = colisoes;
                quantidadeInserida++;
                totalColisoes = totalColisoes + colisoes;
            }
        }

        System.out.println("Rehash");
        System.out.println("Insercoes pedidas: " + INSERCOES);
        System.out.println("Inseridos antes de 90%: " + quantidadeInserida);
        System.out.println("Insercoes bloqueadas: " + bloqueados);
        System.out.println("Total de colisoes na insercao: " + totalColisoes);

        System.out.println("Tres elementos que mais colidiram:");
        boolean[] escolhido = new boolean[quantidadeInserida];
        int[] maisColidiram = new int[3];
        int encontrados = 0;
        while (encontrados < 3 && encontrados < quantidadeInserida) {
            int melhor = -1;
            for (int i = 0; i < quantidadeInserida; i++) {
                if (!escolhido[i]) {
                    if (melhor == -1 || colisoesDoElemento[i] > colisoesDoElemento[melhor]) {
                        melhor = i;
                    }
                }
            }
            escolhido[melhor] = true;
            maisColidiram[encontrados] = melhor;
            encontrados++;
            System.out.println(inseridos[melhor] + " (" + colisoesDoElemento[melhor] + " colisoes)");
        }

        System.out.println("Posicoes livres:");
        int livres = 0;
        for (int i = 0; i < TAMANHO; i++) {
            if (!ocupado[i]) {
                if (livres > 0) {
                    System.out.print(", ");
                }
                System.out.print(i);
                livres++;
            }
        }
        System.out.println();
        System.out.println("Quantidade de posicoes livres: " + livres);

        System.out.println("Busca dos tres elementos que mais colidiram:");
        for (int k = 0; k < encontrados; k++) {
            int valor = inseridos[maisColidiram[k]];
            int indice = buscar(tabela, ocupado, valor);
            if (indice == -1) {
                System.out.println(valor + ": nao encontrado");
            } else {
                System.out.println(valor + ": encontrado na posicao " + indice);
            }
        }
    }

    static int inserir(int[] tabela, boolean[] ocupado, int valor) {
        int indice = valor % TAMANHO;
        int colisoes = 0;
        int tentativas = 0;

        while (ocupado[indice] && tentativas < TAMANHO) {
            colisoes++;
            indice = (indice + 1) % TAMANHO;
            tentativas++;
        }

        if (!ocupado[indice]) {
            ocupado[indice] = true;
            tabela[indice] = valor;
        }
        return colisoes;
    }

    static int buscar(int[] tabela, boolean[] ocupado, int valor) {
        int indice = valor % TAMANHO;
        int tentativas = 0;

        while (tentativas < TAMANHO) {
            if (!ocupado[indice]) {
                return -1;
            }
            if (tabela[indice] == valor) {
                return indice;
            }
            indice = (indice + 1) % TAMANHO;
            tentativas++;
        }
        return -1;
    }

    static int ocupacao(boolean[] ocupado) {
        int ocupadas = 0;
        for (int i = 0; i < ocupado.length; i++) {
            if (ocupado[i]) {
                ocupadas++;
            }
        }
        return ocupadas;
    }
}

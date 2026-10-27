import java.util.Random;

public class HashEncadeado {
    static final int TAMANHO = 100;
    static final int INSERCOES = 1000;
    static final int LIMITE = 100000;
    static final long SEMENTE = 1090L;

    static class No {
        int valor;
        No proximo;

        No(int valor) {
            this.valor = valor;
        }
    }

    static class Posicao {
        boolean ocupada;
        int chave;
        No proximo;
    }

    public static void main(String[] args) {
        Posicao[] tabela = new Posicao[TAMANHO];
        for (int i = 0; i < TAMANHO; i++) {
            tabela[i] = new Posicao();
        }

        int[] ultimos = new int[3];
        int quantidadeUltimos = 0;

        Random random = new Random(SEMENTE);
        for (int i = 0; i < INSERCOES; i++) {
            int valor = random.nextInt(LIMITE + 1);
            int indice = valor % TAMANHO;

            if (!tabela[indice].ocupada) {
                tabela[indice].ocupada = true;
                tabela[indice].chave = valor;
            } else {
                tabela[indice].proximo = inserirOrdenado(tabela[indice].proximo, valor);
                quantidadeUltimos = guardarUltimo(ultimos, quantidadeUltimos, valor);
            }
        }

        int listas = 0;
        int vazias = 0;
        int indiceMaior = -1;
        int tamanhoMaior = -1;

        for (int i = 0; i < TAMANHO; i++) {
            if (!tabela[i].ocupada) {
                vazias++;
            } else if (tabela[i].proximo != null) {
                listas++;
                int tamanho = tamanhoLista(tabela[i].proximo);
                if (tamanho > tamanhoMaior) {
                    tamanhoMaior = tamanho;
                    indiceMaior = i;
                }
            }
        }

        System.out.println("Hash encadeado");
        System.out.println("Insercoes: " + INSERCOES);
        System.out.println("Tamanho da tabela: " + TAMANHO);
        System.out.println("Listas encadeadas geradas: " + listas);
        System.out.println("Posicoes vazias: " + vazias);
        System.out.println("Tres ultimos valores inseridos em listas encadeadas:");
        for (int i = 0; i < quantidadeUltimos; i++) {
            System.out.println(ultimos[i]);
        }

        System.out.println("Maior lista encadeada:");
        if (indiceMaior == -1) {
            System.out.println("nenhuma");
        } else {
            System.out.println("posicao " + indiceMaior);
            System.out.println("chave na posicao: " + tabela[indiceMaior].chave);
            System.out.println("tamanho da lista: " + tamanhoMaior);
            imprimirLista(tabela[indiceMaior].proximo);
        }
    }

    static No inserirOrdenado(No inicio, int valor) {
        No novo = new No(valor);
        if (inicio == null || valor < inicio.valor) {
            novo.proximo = inicio;
            return novo;
        }

        No atual = inicio;
        while (atual.proximo != null && atual.proximo.valor <= valor) {
            atual = atual.proximo;
        }
        novo.proximo = atual.proximo;
        atual.proximo = novo;
        return inicio;
    }

    static int guardarUltimo(int[] ultimos, int quantidade, int valor) {
        if (quantidade < ultimos.length) {
            ultimos[quantidade] = valor;
            return quantidade + 1;
        }
        ultimos[0] = ultimos[1];
        ultimos[1] = ultimos[2];
        ultimos[2] = valor;
        return quantidade;
    }

    static int tamanhoLista(No inicio) {
        int tamanho = 0;
        No atual = inicio;
        while (atual != null) {
            tamanho++;
            atual = atual.proximo;
        }
        return tamanho;
    }

    static void imprimirLista(No inicio) {
        No atual = inicio;
        while (atual != null) {
            System.out.print(atual.valor);
            if (atual.proximo != null) {
                System.out.print(" -> ");
            }
            atual = atual.proximo;
        }
        System.out.println();
    }
}

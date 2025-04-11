package gerenciamentoalunos;

import java.util.Scanner;

class Aluno {
    int rgm;
    String nome;
    String[] disciplinas;
    int qtdDisciplinas;

    public Aluno(int rgm, String nome) {
        this.rgm = rgm;
        this.nome = nome;
        this.disciplinas = new String[20]; // Supondo máximo 20 disciplinas por aluno
        this.qtdDisciplinas = 0;
    }

    public void adicionarDisciplina(String disciplina) {
        if (qtdDisciplinas < disciplinas.length) {
            disciplinas[qtdDisciplinas++] = disciplina;
        } else {
            System.out.println("Limite de disciplinas atingido para este aluno.");
        }
    }

    @Override
    public String toString() {
        String disc = "";
        for (int i = 0; i < qtdDisciplinas; i++) {
            disc += disciplinas[i] + (i < qtdDisciplinas - 1 ? ", " : "");
        }
        return "RGM: " + rgm + ", Nome: " + nome + ", Disciplinas: [" + disc + "]";
    }
}

public class Gerenciamentoalunos {
    private static Aluno[] alunos = new Aluno[60];
    private static int qtdAlunos = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Cadastrar alunos");
            System.out.println("2. Mostrar todos os alunos");
            System.out.println("3. Procurar aluno por RGM");
            System.out.println("4. Remover aluno por RGM");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    cadastrarAlunos();
                    break;
                case 2:
                    mostrarTodosAlunos();
                    break;
                case 3:
                    procurarAlunoPorRGM();
                    break;
                case 4:
                    removerAlunoPorRGM();
                    break;
                case 5:
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 5);
    }

    private static void cadastrarAlunos() {
        System.out.print("Quantos alunos deseja cadastrar? (máximo " + (60 - qtdAlunos) + "): ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        if (n > 60 - qtdAlunos) {
            System.out.println("Número máximo de alunos é " + (60 - qtdAlunos) + ". Cadastrando " + (60 - qtdAlunos) + " alunos.");
            n = 60 - qtdAlunos;
        }

        for (int i = 0; i < n; i++) {
            System.out.print("\nDigite o nome do aluno " + (i + 1) + ": ");
            String nome = scanner.nextLine();

            System.out.print("Digite o RGM do aluno " + (i + 1) + " (8 dígitos): ");
            int rgm = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            Aluno aluno = new Aluno(rgm, nome);

            String resposta;
            do {
                System.out.print("Digite o nome da disciplina: ");
                String disciplina = scanner.nextLine();
                aluno.adicionarDisciplina(disciplina);

                System.out.print("Adicionar mais disciplinas? (S/N): ");
                resposta = scanner.nextLine();
            } while (resposta.equalsIgnoreCase("S"));

            alunos[qtdAlunos++] = aluno;
        }

        // Ordenar a lista por RGM (usando Bubble Sort simples)
        ordenarAlunosPorRGM();
        System.out.println("\nAlunos cadastrados e ordenados por RGM com sucesso!");
    }

    private static void ordenarAlunosPorRGM() {
        for (int i = 0; i < qtdAlunos - 1; i++) {
            for (int j = 0; j < qtdAlunos - i - 1; j++) {
                if (alunos[j].rgm > alunos[j + 1].rgm) {
                    Aluno temp = alunos[j];
                    alunos[j] = alunos[j + 1];
                    alunos[j + 1] = temp;
                }
            }
        }
    }

    private static void mostrarTodosAlunos() {
        if (qtdAlunos == 0) {
            System.out.println("Nenhum aluno cadastrado ainda.");
            return;
        }

        System.out.println("\n--- LISTA DE ALUNOS ---");
        for (int i = 0; i < qtdAlunos; i++) {
            System.out.println(alunos[i]);
        }
    }

    private static void procurarAlunoPorRGM() {
        System.out.print("\nDigite o RGM do aluno a ser procurado: ");
        int rgm = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        boolean encontrado = false;
        for (int i = 0; i < qtdAlunos; i++) {
            if (alunos[i].rgm == rgm) {
                System.out.println("Aluno encontrado: " + alunos[i]);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Aluno com RGM " + rgm + " não encontrado.");
        }
    }

    private static void removerAlunoPorRGM() {
        System.out.print("\nDigite o RGM do aluno a ser removido: ");
        int rgm = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        int posicao = -1;
        for (int i = 0; i < qtdAlunos; i++) {
            if (alunos[i].rgm == rgm) {
                posicao = i;
                break;
            }
        }

        if (posicao == -1) {
            System.out.println("Aluno com RGM " + rgm + " não encontrado.");
            return;
        }

        // Remover o aluno deslocando os elementos subsequentes
        for (int i = posicao; i < qtdAlunos - 1; i++) {
            alunos[i] = alunos[i + 1];
        }
        qtdAlunos--;
        alunos[qtdAlunos] = null; // Limpar a última referência

        System.out.println("Aluno removido com sucesso.");
        mostrarTodosAlunos();
    }
}

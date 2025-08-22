import java.sql.Connection;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DbFunctions db = new DbFunctions();
        Scanner sc = new Scanner(System.in);

        // Conexão com o banco
        Connection conn = db.connect_to_db("associacao_patinhas", "------", "-----");

        int opcoes;

        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Listar todos os animais");
            System.out.println("2 - Deletar animal");
            System.out.println("3 - Atualizar cadastro de animal");
            System.out.println("4 - Procurar por animal");
            System.out.println("5 - Inserir novo animal");
            System.out.println("0 - Encerrar programa");
            System.out.print("Escolha uma opção: ");
            opcoes = sc.nextInt();
            sc.nextLine();

            switch (opcoes) {
                case 1:
                    db.read_data(conn, "animais");
                    break;

                case 2:
                    System.out.println("Função de deletar ainda não implementada.");
                    break;

                case 3:
                    System.out.println("Digite o nome do animal que deseja atualizar: ");
                    String nomeAnimal = sc.next();

                    int animalId = db.getAnimalIdByName(conn, "animais", nomeAnimal);
                    if (animalId == -1) {
                        System.out.println("Animal não encontrado!");
                        break;
                    }

                    System.out.println("O QUE VOCE DESEJA ALTERAR? (nome, tipo, idade, raca, sexo, status, data_resgate, canil_id ou todos)");
                    String alteracao = sc.next();

                    switch (alteracao.toLowerCase()) {
                        case "nome":
                            System.out.print("Digite o novo nome: ");
                            db.updateFieldById(conn, "animais", "nome", animalId, sc.next());
                            break;
                        case "tipo":
                            System.out.print("Digite o novo tipo: ");
                            db.updateFieldById(conn, "animais", "tipo", animalId, sc.next());
                            break;
                        case "idade":
                            System.out.print("Digite a nova idade: ");
                            db.updateFieldById(conn, "animais", "idade", animalId, sc.nextInt());
                            break;
                        case "raca":
                            System.out.print("Digite a nova raça: ");
                            db.updateFieldById(conn, "animais", "raca", animalId, sc.next());
                            break;
                        case "sexo":
                            System.out.print("Digite o novo sexo: ");
                            db.updateFieldById(conn, "animais", "sexo", animalId, sc.next());
                            break;
                        case "status":
                            System.out.print("Digite o novo status: ");
                            db.updateFieldById(conn, "animais", "status", animalId, sc.next());
                            break;
                        case "data_resgate":
                            System.out.print("Digite a nova data (AAAA-MM-DD): ");
                            db.updateFieldById(conn, "animais", "data_resgate", animalId, java.sql.Date.valueOf(sc.next()));
                            break;
                        case "canil_id":
                            System.out.print("Digite o novo canil_id: ");
                            db.updateFieldById(conn, "animais", "canil_id", animalId, sc.nextLong());
                            break;
                        case "todos":
                            System.out.print("Digite o novo nome: ");
                            db.updateFieldById(conn, "animais", "nome", animalId, sc.next());
                            System.out.print("Digite o novo tipo: ");
                            db.updateFieldById(conn, "animais", "tipo", animalId, sc.next());
                            System.out.print("Digite a nova idade: ");
                            db.updateFieldById(conn, "animais", "idade", animalId, sc.nextInt());
                            System.out.print("Digite a nova raça: ");
                            db.updateFieldById(conn, "animais", "raca", animalId, sc.next());
                            System.out.print("Digite o novo sexo: ");
                            db.updateFieldById(conn, "animais", "sexo", animalId, sc.next());
                            System.out.print("Digite o novo status: ");
                            db.updateFieldById(conn, "animais", "status", animalId, sc.next());
                            System.out.print("Digite a nova data (AAAA-MM-DD): ");
                            db.updateFieldById(conn, "animais", "data_resgate", animalId, java.sql.Date.valueOf(sc.next()));
                            System.out.print("Digite o novo canil_id: ");
                            db.updateFieldById(conn, "animais", "canil_id", animalId, sc.nextLong());
                            break;
                        default:
                            System.out.println("Opção inválida!");
                    }
                    break;


                case 4:
                    System.out.println("Digite o nome do animal desejado: ");
                    String animalDesejado = sc.next();

                    animalId = db.getAnimalIdByName(conn, "animais", animalDesejado);

                    if (animalId != -1) {
                        db.searchById(conn, "animais", animalId);
                    } else {
                        System.out.println("Animal não encontrado!");
                    }
                    break;

                case 5:
                    System.out.println("=== Cadastro de Animal ===");

                    System.out.print("Digite o nome do animal: ");
                    String nome = sc.nextLine();

                    System.out.print("Digite o tipo (Ex: Gato, Cachorro): ");
                    String tipo = sc.nextLine();

                    System.out.print("Digite a idade: ");
                    int idade = sc.nextInt();
                    sc.nextLine(); // consome quebra de linha

                    System.out.print("Digite a raça: ");
                    String raca = sc.nextLine();

                    System.out.print("Digite o sexo (Masc/Fem): ");
                    String sexo = sc.nextLine();

                    System.out.print("Digite o status (Ex: Saudavel, Doente, Adotado): ");
                    String status = sc.nextLine();

                    System.out.print("Digite a data de resgate (YYYY-MM-DD) ou deixe vazio: ");
                    String dataStr = sc.nextLine();
                    LocalDate dataResgate = null;
                    if (!dataStr.isBlank()) {
                        dataResgate = LocalDate.parse(dataStr);
                    }

                    System.out.print("Digite o ID do canil ou deixe vazio: ");
                    String canilStr = sc.nextLine();
                    Long canilId = null;
                    if (!canilStr.isBlank()) {
                        canilId = Long.parseLong(canilStr);
                    }

                    db.insertAnimal(conn, nome, tipo, idade, raca, sexo, status, dataResgate, canilId);
                    break;


                case 0:
                    System.out.println("Encerrando programa...");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

        } while (opcoes != 0); // Repete até digitar 0

        sc.close();
        System.out.println("Programa finalizado.");
    }
}

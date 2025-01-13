/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

import dao.SoloDao;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;
import model.Solo;

/**
 *
 * @author rodri
 */
public class MenuGerenciarSolos {
     /*==================================================================================================*/
    
//       GERENCIANDO INFORMAÇÕES DA CLASSE SOLO
    
    public static void gerenciarSolos(Connection connection, Scanner scanner) {
        SoloDao soloDao = new SoloDao(connection);
        boolean sair = false;

        while (!sair) {
            System.out.println("\n------------------------------");
            System.out.println("        Gerenciar Solos        ");
            System.out.println("------------------------------");
            System.out.println("1 -> Inserir Solo");
            System.out.println("2 -> Atualizar Solo");
            System.out.println("3 -> Excluir Solo");
            System.out.println("4 -> Listar Solos");
            System.out.println("5 -> Detalhar Solo");
            System.out.println("0 -> IR PARA O MENU PRINCIPAL");
            System.out.print("Digite o numero para escolher: ");

            int escolha = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (escolha) {
                case 0:
                    sair = true;
                    break;

                // Caso para inserir um solo
                case 1:
                    System.out.println("Inserir Solo:");
                    System.out.print("Digite o Tipo do Solo: ");
                    String tipoSolo = scanner.nextLine();

                    System.out.print("Digite a Fertilidade do Solo: ");
                    String fertilidade = scanner.nextLine();

                    System.out.print("Digite a Umidade do Solo: ");
                    float umidade = scanner.nextFloat();
                    scanner.nextLine();

                    Solo novoSolo = new Solo();
                    novoSolo.setTipo_solo(tipoSolo);
                    novoSolo.setFertilidade(fertilidade);
                    novoSolo.setUmidade(umidade);

                    try {
                        soloDao.insert(novoSolo);
                        System.out.println("Solo inserido com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao inserir solo: " + e.getMessage());
                    }
                    break;

                // Caso para atualizar um solo
                case 2:
                    System.out.println("Atualizar Solo:");
                    System.out.print("ID do Solo: ");
                    int idUpdate = scanner.nextInt();
                    scanner.nextLine();

                    // Buscar o solo existente com base no ID
                    Solo soloExistente = soloDao.busca(idUpdate);
                    if (soloExistente == null) {
                        System.out.println("Nenhum solo encontrado com o ID informado.");
                        break;
                    }

                    // Exibir os dados atuais para o usuário
                    System.out.println("Dados atuais:");
                    System.out.println("Tipo do Solo: " + soloExistente.getTipo_solo());
                    System.out.println("Fertilidade: " + soloExistente.getFertilidade());
                    System.out.println("Umidade: " + soloExistente.getUmidade());

                    // Permitir ao usuário atualizar os campos desejados
                    System.out.print("Novo Tipo de Solo (ou pressione Enter para manter o atual): ");
                    String novoTipoSolo = scanner.nextLine();
                    if (!novoTipoSolo.isEmpty()) {
                        soloExistente.setTipo_solo(novoTipoSolo);
                    }

                    System.out.print("Nova Fertilidade (ou pressione Enter para manter a atual): ");
                    String novaFertilidade = scanner.nextLine();
                    if (!novaFertilidade.isEmpty()) {
                        soloExistente.setFertilidade(novaFertilidade);
                    }

                    System.out.print("Nova Umidade (ou pressione Enter para manter a atual): ");
                    String novaUmidadeStr = scanner.nextLine();
                    if (!novaUmidadeStr.isEmpty()) {
                        soloExistente.setUmidade(Float.parseFloat(novaUmidadeStr));
                    }

                    // Atualizar no banco de dados
                    soloDao.update(soloExistente);
                    break;

                // Caso para excluir um solo
                case 3:
                    System.out.println("Excluir Solo:");
                    System.out.print("ID do Solo: ");
                    int idDelete = scanner.nextInt();
                    scanner.nextLine();

                    // Verificar se o solo existe antes de excluir
                    Solo soloParaExcluir = soloDao.busca(idDelete);
                    if (soloParaExcluir == null) {
                        System.out.println("Nenhum solo encontrado com o ID informado.");
                        break;
                    }

                    // Confirmar exclusão
                    System.out.print("Tem certeza que deseja excluir o solo " + soloParaExcluir.getTipo_solo() + "? (S/N): ");
                    String confirmacao = scanner.nextLine();
                    if (confirmacao.equalsIgnoreCase("S")) {
                        soloDao.delete(idDelete);
                        System.out.println("Solo excluído com sucesso.");
                    } else {
                        System.out.println("Exclusão cancelada.");
                    }
                    break;

                // Caso para listar todos os solos
                case 4:
                    System.out.println("Listar Solos:");

                    // Chamar o método listAll para obter a lista de solos
                    List<Solo> solos = soloDao.listAll();

                    if (solos.isEmpty()) {
                        System.out.println("Nenhum solo encontrado.");
                    } else {
                        System.out.println("\n==== Solos Registrados ====");
                        for (Solo solo : solos) {
                            System.out.println("ID: " + solo.getId_solo());
                            System.out.println("Tipo: " + solo.getTipo_solo());
                            System.out.println("Fertilidade: " + solo.getFertilidade());
                            System.out.println("Umidade: " + solo.getUmidade());
                            System.out.println("---------------------------");
                        }
                    }
                    break;

                // Caso para detalhar um solo
                case 5:
                    System.out.println("Detalhar Solo:");
                    System.out.print("ID do Solo: ");
                    int idDetail = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha

                    // Buscar solo pelo ID fornecido
                    Solo soloDetalhe = soloDao.busca(idDetail);

                    if (soloDetalhe == null) {
                        System.out.println("Nenhum solo encontrado com o ID informado.");
                    } else {
                        // Exibir detalhes do solo
                        System.out.println("\n==== Detalhes do Solo ====");
                        System.out.println("ID: " + soloDetalhe.getId_solo());
                        System.out.println("Tipo: " + soloDetalhe.getTipo_solo());
                        System.out.println("Fertilidade: " + soloDetalhe.getFertilidade());
                        System.out.println("Umidade: " + soloDetalhe.getUmidade());
                    }
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}

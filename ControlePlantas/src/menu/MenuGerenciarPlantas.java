/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

import dao.SoloDao;
import dao.PlantaDao;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import model.Planta;
import model.Solo;


/**
 *
 * @author rodri
 */
public class MenuGerenciarPlantas {
    
    /*=======================================================================================*/
    
    //       GERENCIANDO INFORMAÇÕES DA CLASSE PLANTA
    
    
    public static void gerenciarPlantas(Connection connection, Scanner scanner) {
        PlantaDao plantaDao = new PlantaDao(connection);
        SoloDao soloDao = new SoloDao(connection);
        boolean sair = false;

        while (!sair) {
            System.out.println("\n------------------------------");
            System.out.println("        Gerenciar Plantas       ");
            System.out.println("------------------------------");
            System.out.println("1 -> Inserir Planta");
            System.out.println("2 -> Atualizar Planta");
            System.out.println("3 -> Excluir Planta");
            System.out.println("4 -> Listar Plantas");
            System.out.println("5 -> Detalhar Planta");
            System.out.println("0 -> IR PARA O MENU PRINCIPAL");
            System.out.print("Digite o numero para escolher: ");

            int escolha = scanner.nextInt();
            scanner.nextLine(); 

            switch (escolha) {
                case 0:
                    sair = true;
                    break;
    /*-----------------------------------------------------------------------------*/        
            //Caso para inserir uma planta
                case 1:
                    System.out.println("Inserir Planta:");
                    System.out.println("Digite o Nome da Planta: ");
                    String nome = scanner.nextLine();
                    
                    System.out.println("Qual o Tipo da Planta: ");
                    String tipoPlanta = scanner.nextLine();

                    
                    System.out.println("\nTIPOS DE SOL");
                    List<Solo> solos = soloDao.listAll();

                    if (solos.isEmpty()) {
                        System.out.println("    Nenhum solo encontrado.  ");
                    } else {
                        System.out.println("\n==== Solos Registrados ====");
                        for (Solo solo : solos) {
                            System.out.println("ID: " + solo.getId_solo());
                            System.out.println("Tipo: " + solo.getTipo_solo());
                            System.out.println("---------------------------");
                        }
                    }
                    System.out.print("Digite o ID do Solo: ");
                    int idSolo = scanner.nextInt();
                    scanner.nextLine();
                    
                    Date dataPlantio = null;
                    boolean dataValida = false;
                    
                    while (dataValida == false) {
                        System.out.print("Digite a Data de Plantio ex:(2024-12-25): ");
                        String dataPlantioStr = scanner.nextLine();

                        try {
                            dataPlantio = new SimpleDateFormat("yyyy-MM-dd").parse(dataPlantioStr);
                            dataValida = true;  
                        } catch (ParseException e) {
                            System.out.println("Formato de data inválido. Tente novamente.");
                        }
                    }

                    Planta novaPlanta = new Planta();
                    novaPlanta.setNome(nome);
                    novaPlanta.setTipo_planta(tipoPlanta);
                    novaPlanta.setId_solo(idSolo);
                    novaPlanta.setData_plantio(dataPlantio);

                    try {
                        PlantaDao plantaeDao = new PlantaDao(connection);
                        plantaeDao.insert(novaPlanta);
                        System.out.println("Planta inserida com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao inserir planta: " + e.getMessage());
                    }
                    break;
                    
    /*-----------------------------------------------------------------------------*/
            //Caso para atualizar alguma informação de uma planta
                case 2:
                    System.out.println("Atualizar Planta:");
                    System.out.print("ID da Planta: ");
                    int idUpdate = scanner.nextInt();
                    scanner.nextLine(); 

                    // Buscar a planta existente com base no ID
                    Planta plantaExistente = plantaDao.busca(idUpdate); 
                    if (plantaExistente == null) {
                        System.out.println("Nenhuma planta encontrada com o ID informado.");
                        break;
                    }

                    // Exibir os dados atuais para o usuário
                    System.out.println("Dados atuais:");
                    System.out.println("ID Solo: " + plantaExistente.getId_solo());
                    System.out.println("Nome: " + plantaExistente.getNome());
                    System.out.println("Tipo de Planta: " + plantaExistente.getTipo_planta());
                    System.out.println("Data de Plantio: " + plantaExistente.getData_plantio());

                    // Permitir ao usuário atualizar os campos desejados
                    System.out.print("Novo ID Solo (ou pressione Enter para manter o atual): ");
                    String novoIdSoloStr = scanner.nextLine();
                    if (!novoIdSoloStr.isEmpty()) {
                        plantaExistente.setId_solo(Integer.parseInt(novoIdSoloStr));
                    }

                    System.out.print("Novo Nome (ou pressione Enter para manter o atual): ");
                    String novoNome = scanner.nextLine();
                    if (!novoNome.isEmpty()) {
                        plantaExistente.setNome(novoNome);
                    }

                    System.out.print("Novo Tipo de Planta (ou pressione Enter para manter o atual): ");
                    String novoTipoPlanta = scanner.nextLine();
                    if (!novoTipoPlanta.isEmpty()) {
                        plantaExistente.setTipo_planta(novoTipoPlanta);
                    }

                    System.out.print("Nova Data de Plantio (yyyy-MM-dd, ou pressione Enter para manter a atual): ");
                    String novaDataPlantioStr = scanner.nextLine();
                    if (!novaDataPlantioStr.isEmpty()) {
                        try {
                            Date novaDataPlantio = new SimpleDateFormat("yyyy-MM-dd").parse(novaDataPlantioStr);
                            plantaExistente.setData_plantio(novaDataPlantio);
                        } catch (ParseException e) {
                            System.out.println("Formato de data inválido. A data atual será mantida.");
                        }
                    }

                    // Atualizar no banco de dados
                    plantaDao.update(plantaExistente);
                    break;


    /*-----------------------------------------------------------------------------*/
            //Caso para excluir uma planta
                    case 3:
                        System.out.println("Excluir Planta:");
                        System.out.print("ID da Planta: ");
                        int idDelete = scanner.nextInt();
                        scanner.nextLine();

                        // Verificar se a planta existe antes de excluir
                        Planta plantaE = plantaDao.busca(idDelete);
                        if (plantaE == null) {
                            System.out.println("Nenhuma planta encontrada com o ID informado.");
                            break;
                        }

                        // Verifica se realmente sera feita a exclusão
                        System.out.print("Tem certeza que deseja excluir a planta " + plantaE.getNome() + "? (S/N): ");
                        String confirmacao = scanner.nextLine();
                        if (confirmacao.equalsIgnoreCase("S")) {
                            plantaDao.delete(idDelete); 
                            System.out.println("Planta excluída com sucesso.");
                        } else {
                            System.out.println("Exclusão cancelada.");
                        }
                        break;

                    
    /*-----------------------------------------------------------------------------*/
            //Caso para listar todas as plantas planta
                    case 4:
                        System.out.println("Listar Plantas:");

                        // Chamar o método listAll para obter a lista de plantas
                        List<Planta> plantas = plantaDao.listAll();

                        if (plantas.isEmpty()) {
                            System.out.println("Nenhuma planta encontrada.");
                        } else {
                            System.out.println("\n==== Plantas Registradas ====");
                            for (Planta planta : plantas) {
                                System.out.println("ID: " + planta.getId_planta());
                                System.out.println("Solo: " + planta.getId_solo());
                                System.out.println("Nome: " + planta.getNome());
                                System.out.println("Tipo: " + planta.getTipo_planta());
                                System.out.println("Data de Plantio: " + planta.getData_plantio());
                                System.out.println("---------------------------");
                            }
                        }
                        break;

    /*-----------------------------------------------------------------------------*/
            //Caso para buscar uma planta pelo ID uma planta
                    case 5:
                    
                        System.out.println("Detalhar Planta:");
                        System.out.print("ID da Planta: ");
                        int idDetail = scanner.nextInt();
                        scanner.nextLine(); // Consumir a quebra de linha

                        // Buscar planta pelo ID fornecido
                        Planta plantaDetalhe = plantaDao.busca(idDetail);

                        if (plantaDetalhe == null) {
                            System.out.println("Nenhuma planta encontrada com o ID informado.");
                        } else {
                            // Exibir detalhes da planta
                            System.out.println("\n==== Detalhes da Planta ====");
                            System.out.println("ID: " + plantaDetalhe.getId_planta());
                            System.out.println("Solo: " + plantaDetalhe.getId_solo());
                            System.out.println("Nome: " + plantaDetalhe.getNome());
                            System.out.println("Tipo: " + plantaDetalhe.getTipo_planta());
                            System.out.println("Data de Plantio: " + plantaDetalhe.getData_plantio());
                        }
                        break;

    /*--------------------------------------------------------------------------------*/
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }

    
}

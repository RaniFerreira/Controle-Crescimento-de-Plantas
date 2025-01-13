/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

import dao.IrrigacaoDao;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import model.Irrigacao;

/**
 *
 * @author rodri
 */
public class MenuGerenciarIrrigacao {
    /*==================================================================================================*/
    
//       GERENCIANDO INFORMAÇÕES DA CLASSE IRRIGAÇÃO
  
    
    public static void gerenciarIrrigacao(Connection connection, Scanner scanner) {
        // Criação de um objeto 'irrigacaoDao' que irá interagir com o banco de dados para realizar operações CRUD
        IrrigacaoDao irrigacaoDao = new IrrigacaoDao(connection);

        // Variável que controla o loop do menu, permitindo sair ou continuar com as operações
        boolean sair = false;

        // Início do loop de exibição do menu e processamento das escolhas
        while (!sair) {
            // Exibe o menu de opções para o usuário
            System.out.println("\n------------------------------");
            System.out.println("   Gerenciar Irrigações   ");
            System.out.println("------------------------------");
            System.out.println("1 -> Inserir Irrigação");
            System.out.println("2 -> Atualizar Irrigação");
            System.out.println("3 -> Excluir Irrigação");
            System.out.println("4 -> Listar Irrigações");
            System.out.println("5 -> Detalhar Irrigação");
            System.out.println("0 -> IR PARA O MENU PRINCIPAL");
            System.out.print("Digite o número para escolher: ");

            // Captura a escolha do usuário
            int escolha = scanner.nextInt();
            scanner.nextLine(); // Consome a quebra de linha pendente após a entrada do número

            // Switch para determinar qual ação tomar com base na escolha do usuário
            switch (escolha) {
                case 0:
                    // Caso de saída para o menu principal
                    sair = true;
                    break;

                // Caso para inserir uma nova irrigação
                case 1:
                    System.out.println("Inserir Irrigação:");

                    // Solicita o ID da planta
                    System.out.print("Digite o ID da Planta: ");
                    int idPlanta = scanner.nextInt();
                    scanner.nextLine(); // Consome a quebra de linha

                    // Valida a data de irrigação
                    Date dataIrrigacao = null;
                    boolean dataValida = false;
                    while (!dataValida) {
                        System.out.print("Digite a Data da Irrigação (yyyy-MM-dd): ");
                        String dataIrrigacaoStr = scanner.nextLine();
                        try {
                            // Tenta converter a data digitada para o formato esperado
                            dataIrrigacao = new SimpleDateFormat("yyyy-MM-dd").parse(dataIrrigacaoStr);
                            dataValida = true; // Data válida, pode sair do loop
                        } catch (ParseException e) {
                            // Caso o formato da data esteja errado, solicita nova entrada
                            System.out.println("Formato de data inválido. Tente novamente.");
                        }
                    }

                    // Solicita a quantidade de água
                    System.out.print("Digite a Quantidade de Água (em litros): ");
                    float quantidadeAgua = scanner.nextFloat();
                    scanner.nextLine(); // Consome a quebra de linha

                    // Cria um novo objeto Irrigacao com os dados fornecidos
                    Irrigacao novaIrrigacao = new Irrigacao();
                    novaIrrigacao.setId_planta(idPlanta);
                    novaIrrigacao.setData_irrigacao(dataIrrigacao);
                    novaIrrigacao.setQuantidade_agua(quantidadeAgua);

                    try {
                        // Tenta inserir a nova irrigação no banco de dados
                        irrigacaoDao.insert(novaIrrigacao);
                        System.out.println("Irrigação inserida com sucesso!");
                    } catch (Exception e) {
                        // Caso ocorra algum erro durante a inserção, exibe mensagem de erro
                        System.out.println("Erro ao inserir irrigação: " + e.getMessage());
                    }

                    break;

                // Caso para atualizar uma irrigação existente
                case 2:
                    System.out.println("Atualizar Irrigação:");
                    System.out.print("ID da Irrigação: ");
                    int idIrrigacaoUpdate = scanner.nextInt();
                    scanner.nextLine(); // Consome a quebra de linha

                    // Busca a irrigação no banco de dados pelo ID fornecido
                    Irrigacao irrigacaoExistente = irrigacaoDao.busca(idIrrigacaoUpdate);
                    if (irrigacaoExistente == null) {
                        // Se a irrigação não for encontrada, exibe mensagem e retorna ao menu
                        System.out.println("Nenhuma irrigação encontrada com o ID informado.");
                        break;
                    }

                    // Exibe os dados atuais da irrigação para que o usuário possa decidir o que atualizar
                    System.out.println("Dados atuais:");
                    System.out.println("ID Planta: " + irrigacaoExistente.getId_planta());
                    System.out.println("Data Irrigação: " + irrigacaoExistente.getData_irrigacao());
                    System.out.println("Quantidade de Água: " + irrigacaoExistente.getQuantidade_agua());

                    // Solicita novos dados para atualização, permitindo que o usuário mantenha os antigos
                    System.out.print("Novo ID da Planta (ou pressione Enter para manter o atual): ");
                    String novoIdPlantaStr = scanner.nextLine();
                    if (!novoIdPlantaStr.isEmpty()) {
                        irrigacaoExistente.setId_planta(Integer.parseInt(novoIdPlantaStr));
                    }

                    System.out.print("Nova Data da Irrigação (yyyy-MM-dd, ou pressione Enter para manter a atual): ");
                    String novaDataIrrigacaoStr = scanner.nextLine();
                    if (!novaDataIrrigacaoStr.isEmpty()) {
                        try {
                            // Converte a string para o tipo Date utilizando SimpleDateFormat
                            Date novaDataIrrigacao = new SimpleDateFormat("yyyy-MM-dd").parse(novaDataIrrigacaoStr);
                            irrigacaoExistente.setData_irrigacao(novaDataIrrigacao);
                        } catch (ParseException e) {
                            // Caso haja erro na conversão da data, informa o erro
                            System.out.println("Formato de data inválido. A data atual será mantida.");
                        }
                    }


                    System.out.print("Nova Quantidade de Água (ou pressione Enter para manter a atual): ");
                    String novaQuantidadeAguaStr = scanner.nextLine();
                    if (!novaQuantidadeAguaStr.isEmpty()) {
                        irrigacaoExistente.setQuantidade_agua(Float.parseFloat(novaQuantidadeAguaStr));
                    }

                    try {
                        // Tenta atualizar a irrigação no banco de dados
                        irrigacaoDao.update(irrigacaoExistente);
                        System.out.println("Irrigação atualizada com sucesso!");
                    } catch (Exception e) {
                        // Caso ocorra algum erro durante a atualização, exibe mensagem de erro
                        System.out.println("Erro ao atualizar irrigação: " + e.getMessage());
                    }
                    break;

                // Caso para excluir uma irrigação
                case 3:
                    System.out.println("Excluir Irrigação:");
                    System.out.print("ID da Irrigação: ");
                    int idIrrigacaoDelete = scanner.nextInt();
                    scanner.nextLine(); // Consome a quebra de linha

                    // Verifica se a irrigação existe no banco antes de tentar excluir
                    Irrigacao irrigacaoParaExcluir = irrigacaoDao.busca(idIrrigacaoDelete);
                    if (irrigacaoParaExcluir == null) {
                        // Se a irrigação não for encontrada, exibe mensagem e retorna ao menu
                        System.out.println("Nenhuma irrigação encontrada com o ID informado.");
                        break;
                    }

                    // Solicita confirmação do usuário para realizar a exclusão
                    System.out.print("Tem certeza que deseja excluir a irrigação? (S/N): ");
                    String confirmacaoDelete = scanner.nextLine();
                    if (confirmacaoDelete.equalsIgnoreCase("S")) {
                        // Caso confirmado, realiza a exclusão
                        irrigacaoDao.delete(idIrrigacaoDelete);
                        System.out.println("Irrigação excluída com sucesso.");
                    } else {
                        // Caso o usuário cancele, exibe mensagem
                        System.out.println("Exclusão cancelada.");
                    }
                    break;

                // Caso para listar todas as irrigacoes
                case 4:
                    System.out.println("Listar Irrigações:");
                    List<Irrigacao> irrigacoes = irrigacaoDao.listAll();
                    if (irrigacoes.isEmpty()) {
                        // Caso não haja irrigação, exibe mensagem informando
                        System.out.println("Nenhuma irrigação encontrada.");
                    } else {
                        // Caso haja irrigação, exibe cada irrigação com seus detalhes
                        System.out.println("\n==== Irrigações ====");
                        for (Irrigacao irrigacao : irrigacoes) {
                            System.out.println("ID Irrigação: " + irrigacao.getId_irrigacao());
                            System.out.println("ID Planta: " + irrigacao.getId_planta());
                            System.out.println("Data Irrigação: " + irrigacao.getData_irrigacao());
                            System.out.println("Quantidade de Água: " + irrigacao.getQuantidade_agua());
                            System.out.println("---------------------------");
                        }
                    }
                    break;

                // Caso para detalhar uma irrigação específica
                case 5:
                    System.out.println("Detalhar Irrigação:");
                    System.out.print("ID da Irrigação: ");
                    int idIrrigacaoDetalhe = scanner.nextInt();
                    scanner.nextLine(); // Consome a quebra de linha

                    // Busca a irrigação no banco de dados
                    Irrigacao irrigacaoDetalhe = irrigacaoDao.busca(idIrrigacaoDetalhe);
                    if (irrigacaoDetalhe == null) {
                        // Caso a irrigação não seja encontrada, exibe mensagem informando
                        System.out.println("Nenhuma irrigação encontrada com o ID informado.");
                    } else {
                        // Caso encontrada, exibe os detalhes da irrigação
                        System.out.println("\n==== Detalhes da Irrigação ====");
                        System.out.println("ID Irrigação: " + irrigacaoDetalhe.getId_irrigacao());
                        System.out.println("ID Planta: " + irrigacaoDetalhe.getId_planta());
                        System.out.println("Data Irrigação: " + irrigacaoDetalhe.getData_irrigacao());
                        System.out.println("Quantidade de Água: " + irrigacaoDetalhe.getQuantidade_agua());
                    }
                    break;

                default:
                    // Caso o usuário forneça uma opção inválida
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}

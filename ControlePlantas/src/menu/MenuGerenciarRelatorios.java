/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

import dao.RelatorioCrescimentoDao;
import dao.RelatorioCrescimentoDao;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import model.RelatorioCrescimento;
import model.RelatorioCrescimento;

/**
 *
 * @author rodri
 */
public class MenuGerenciarRelatorios {
   /*==================================================================================================*/
    
//       GERENCIANDO INFORMAÇÕES DA CLASSE Relatorio de Crescimento
  
    public static void gerenciarRelatoriosCrescimento(Connection connection, Scanner scanner) {
        // Criação de um objeto 'relatorioDao' que irá interagir com o banco de dados para realizar operações CRUD
        RelatorioCrescimentoDao relatorioDao = new RelatorioCrescimentoDao(connection);

        // Variável que controla o loop do menu, permitindo sair ou continuar com as operações
        boolean sair = false;

        // Início do loop de exibição do menu e processamento das escolhas
        while (!sair) {
            // Exibe o menu de opções para o usuário
            System.out.println("\n------------------------------");
            System.out.println("   Gerenciar Relatórios de Crescimento   ");
            System.out.println("------------------------------");
            System.out.println("1 -> Inserir Relatório de Crescimento");
            System.out.println("2 -> Atualizar Relatório de Crescimento");
            System.out.println("3 -> Excluir Relatório de Crescimento");
            System.out.println("4 -> Listar Relatórios de Crescimento");
            System.out.println("5 -> Detalhar Relatório de Crescimento");
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

                // Caso para inserir um novo relatório de crescimento
                case 1:
                    System.out.println("Inserir Relatório de Crescimento:");

                    // Solicita o ID da planta
                    System.out.print("Digite o ID da Planta: ");
                    int idPlanta = scanner.nextInt();
                    scanner.nextLine(); // Consome a quebra de linha

                    // Valida a data de medição
                    Date dataMedicao = null;
                    boolean dataValida = false;
                    while (!dataValida) {
                        System.out.print("Digite a Data da Medição (yyyy-MM-dd): ");
                        String dataMedicaoStr = scanner.nextLine();
                        try {
                            // Tenta converter a data digitada para o formato esperado
                            dataMedicao = new SimpleDateFormat("yyyy-MM-dd").parse(dataMedicaoStr);
                            dataValida = true; // Data válida, pode sair do loop
                        } catch (ParseException e) {
                            // Caso o formato da data esteja errado, solicita nova entrada
                            System.out.println("Formato de data inválido. Tente novamente.");
                        }
                    }

                    // Solicita a altura da planta e a descrição da saúde
                    System.out.print("Digite a Altura da Planta (em metros): ");
                    float altura = scanner.nextFloat();
                    scanner.nextLine(); // Consome a quebra de linha

                    System.out.print("Digite a Descrição da Saúde da Planta: ");
                    String descricaoSaude = scanner.nextLine();

                    // Cria um novo objeto RelatorioCrescimento com os dados fornecidos
                    RelatorioCrescimento novoRelatorio = new RelatorioCrescimento();
                    novoRelatorio.setId_planta(idPlanta);
                    novoRelatorio.setData_medicao(dataMedicao);
                    novoRelatorio.setAltura(altura);
                    novoRelatorio.setDescricao_saude(descricaoSaude);

                    try {
                        // Tenta inserir o novo relatório no banco de dados
                        relatorioDao.insert(novoRelatorio);
                        
                    } catch (Exception e) {
                        // Caso ocorra algum erro durante a inserção, exibe mensagem de erro
                        System.out.println("Erro ao inserir relatório de crescimento: " + e.getMessage());
                    }
                    break;

                // Caso para atualizar um relatório de crescimento existente
                case 2:
                    System.out.println("Atualizar Relatório de Crescimento:");
                    System.out.print("ID do Relatório de Crescimento: ");
                    int idRelatorioUpdate = scanner.nextInt();
                    scanner.nextLine(); // Consome a quebra de linha

                    // Busca o relatório no banco de dados pelo ID fornecido
                    RelatorioCrescimento relatorioExistente = relatorioDao.busca(idRelatorioUpdate);
                    if (relatorioExistente == null) {
                        // Se o relatório não for encontrado, exibe mensagem e retorna ao menu
                        System.out.println("Nenhum relatório encontrado com o ID informado.");
                        break;
                    }

                    // Exibe os dados atuais do relatório para que o usuário possa decidir o que atualizar
                    System.out.println("Dados atuais:");
                    System.out.println("ID Planta: " + relatorioExistente.getId_planta());
                    System.out.println("Data da Medição: " + relatorioExistente.getData_medicao());
                    System.out.println("Altura: " + relatorioExistente.getAltura());
                    System.out.println("Descrição da Saúde: " + relatorioExistente.getDescricao_saude());

                    // Solicita novos dados para atualização, permitindo que o usuário mantenha os antigos
                    System.out.print("Novo ID da Planta (ou pressione Enter para manter o atual): ");
                    String novoIdPlantaStr = scanner.nextLine();
                    if (!novoIdPlantaStr.isEmpty()) {
                        relatorioExistente.setId_planta(Integer.parseInt(novoIdPlantaStr));
                    }

                    System.out.print("Nova Data da Medição (yyyy-MM-dd, ou pressione Enter para manter a atual): ");
                    String novaDataMedicaoStr = scanner.nextLine();
                    if (!novaDataMedicaoStr.isEmpty()) {
                        try {
                            // Atualiza a data, se fornecida
                            Date novaDataMedicao = new SimpleDateFormat("yyyy-MM-dd").parse(novaDataMedicaoStr);
                            relatorioExistente.setData_medicao(novaDataMedicao);
                        } catch (ParseException e) {
                            // Caso a data seja inválida, mantém a atual
                            System.out.println("Formato de data inválido. A data atual será mantida.");
                        }
                    }

                    System.out.print("Nova Altura (ou pressione Enter para manter a atual): ");
                    String novaAlturaStr = scanner.nextLine();
                    if (!novaAlturaStr.isEmpty()) {
                        relatorioExistente.setAltura(Float.parseFloat(novaAlturaStr));
                    }

                    System.out.print("Nova Descrição da Saúde (ou pressione Enter para manter a atual): ");
                    String novaDescricaoSaude = scanner.nextLine();
                    if (!novaDescricaoSaude.isEmpty()) {
                        relatorioExistente.setDescricao_saude(novaDescricaoSaude);
                    }

                    // Tenta atualizar o relatório no banco de dados
                    relatorioDao.update(relatorioExistente);
                    
                    break;

                // Caso para excluir um relatório de crescimento
                case 3:
                    System.out.println("Excluir Relatório de Crescimento:");
                    System.out.print("ID do Relatório de Crescimento: ");
                    int idRelatorioDelete = scanner.nextInt();
                    scanner.nextLine(); // Consome a quebra de linha

                    // Verifica se o relatório existe no banco antes de tentar excluir
                    RelatorioCrescimento relatorioParaExcluir = relatorioDao.busca(idRelatorioDelete);
                    if (relatorioParaExcluir == null) {
                        // Se o relatório não for encontrado, exibe mensagem e retorna ao menu
                        System.out.println("Nenhum relatório encontrado com o ID informado.");
                        break;
                    }

                    // Solicita confirmação do usuário para realizar a exclusão
                    System.out.print("Tem certeza que deseja excluir o relatório de crescimento? (S/N): ");
                    String confirmacaoDelete = scanner.nextLine();
                    if (confirmacaoDelete.equalsIgnoreCase("S")) {
                        // Caso confirmado, realiza a exclusão
                        relatorioDao.delete(idRelatorioDelete);
                        
                    } else {
                        // Caso o usuário cancele, exibe mensagem
                        System.out.println("Exclusão cancelada.");
                    }
                    break;

                // Caso para listar todos os relatórios de crescimento
                case 4:
                    System.out.println("Listar Relatórios de Crescimento:");

                    // Obtém todos os relatórios de crescimento do banco de dados
                    List<RelatorioCrescimento> relatorios = relatorioDao.listAll();
                    if (relatorios.isEmpty()) {
                        // Caso não haja relatórios, exibe mensagem informando
                        System.out.println("Nenhum relatório encontrado.");
                    } else {
                        // Caso haja, exibe cada relatório com seus detalhes
                        System.out.println("\n==== Relatórios de Crescimento ====");
                        for (RelatorioCrescimento relatorio : relatorios) {
                            System.out.println("ID: " + relatorio.getId_relatorio());
                            System.out.println("ID Planta: " + relatorio.getId_planta());
                            System.out.println("Data da Medição: " + relatorio.getData_medicao());
                            System.out.println("Altura: " + relatorio.getAltura());
                            System.out.println("Saúde: " + relatorio.getDescricao_saude());
                            System.out.println("---------------------------");
                        }
                    }
                    break;

                // Caso para detalhar um relatório específico
                case 5:
                    System.out.println("Detalhar Relatório de Crescimento:");
                    System.out.print("ID do Relatório de Crescimento: ");
                    int idRelatorioDetalhe = scanner.nextInt();
                    scanner.nextLine(); // Consome a quebra de linha

                    // Busca o relatório no banco de dados
                    RelatorioCrescimento relatorioDetalhe = relatorioDao.busca(idRelatorioDetalhe);
                    if (relatorioDetalhe == null) {
                        // Caso o relatório não seja encontrado, exibe mensagem informando
                        System.out.println("Nenhum relatório encontrado com o ID informado.");
                    } else {
                        // Caso encontrado, exibe os detalhes do relatório
                        System.out.println("\n==== Detalhes do Relatório ====");
                        System.out.println("ID: " + relatorioDetalhe.getId_relatorio());
                        System.out.println("ID Planta: " + relatorioDetalhe.getId_planta());
                        System.out.println("Data da Medição: " + relatorioDetalhe.getData_medicao());
                        System.out.println("Altura: " + relatorioDetalhe.getAltura());
                        System.out.println("Saúde: " + relatorioDetalhe.getDescricao_saude());
                    }
                    break;

                default:
                    // Caso o usuário forneça uma opção inválida
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}

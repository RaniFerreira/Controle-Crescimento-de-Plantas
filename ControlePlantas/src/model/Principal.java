package model;

import dao.SoloDao;
import java.sql.Connection;
import connection.ConexaoBD;
import dao.AlertaDao;
import model.RelatorioCrescimento;
import dao.IrrigacaoDao;
import dao.PlantaDao;
import dao.RelatorioCrescimentoDao;
import dao.ViewDao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Ranielly Ferreira e Paulo Junior
 */
//Classe principal para rodar o menu de opções
public class Principal {
    public static void main(String[] args) {
        
        Connection connection = ConexaoBD.connect();// instanciando a conexão com o banco(é necessário apenas uma vez)
        // servira para o menu completo(Não apagar)
        Scanner scanner = new Scanner(System.in);
        boolean sair = false;
        
        while(!sair){
            System.out.println("================================================");
            System.out.println("                       Menu                     ");
            System.out.println("================================================");
            System.out.println("0 -> SAIR");
            System.out.println("1 -> Gerenciar Plantas");
            System.out.println("2 -> Gerenciar Solos");
            System.out.println("3 -> Gerenciar Relatorios de Crescimento");
            System.out.println("4 -> Gerenciar Irigação");
            System.out.println("5 -> Visualizar Alerta");
            System.out.println("6 -> Visualização de relatorios (VIEW)");
            System.out.println("================================================");
            System.out.println("Digite um numero para escolher o comando: ");

            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 0:
                    sair = true;
                    System.out.println("Fechando o Sistema, Ate a proxima!");
                    break;

                case 1:
                    gerenciarPlantas(connection, scanner);
                    break;

                case 2:
                    gerenciarSolos(connection, scanner);
                    break;

                case 3: 
                    gerenciarRelatoriosCrescimento(connection, scanner);
                    break;

                case 4:
                    gerenciarIrrigacao(connection, scanner);
                    break;

                case 5:
                    gerenciamentoAlerta(connection, scanner);
                    break;
                    
                case 6:
                    gerenciarViews(connection, scanner);
                    break;
            }
        }
        
    }
    
    
/*=======================================================================================*/
    
//       GERENCIANDO INFORMAÇÕES DA CLASSE PLANTA
    
    public static void gerenciarPlantas(Connection connection, Scanner scanner) {
        PlantaDao plantaDao = new PlantaDao(connection);
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
/*==================================================================================================*/
    
//       GERENCIANDO INFORMAÇÕES DA CLASSE SOLO
  
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
                        System.out.println("Relatório de Crescimento inserido com sucesso!");
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
                    System.out.println("Relatório de Crescimento atualizado com sucesso!");
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
                        System.out.println("Relatório de Crescimento excluído com sucesso.");
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
/*============================================================================================================*/
    
//                  GERENCIANDO INFORMAÇÕES DA CLASSE ALERTA
    
        public static void gerenciamentoAlerta(Connection connection, Scanner scanner) {
        // Criação de um objeto 'alertaDao' para interagir com a tabela de alertas
        AlertaDao alertaDao = new AlertaDao(connection);

        // Variável que controla o loop do menu
        boolean sair = false;

        // Loop para exibição do menu e processamento das opções do usuário
        while (!sair) {
            // Exibição do menu de opções
            System.out.println("\n------------------------------");
            System.out.println("   Gerenciar Alertas   ");
            System.out.println("------------------------------");
            System.out.println("1 -> Excluir Alerta");
            System.out.println("2 -> Listar Alerta");
            System.out.println("0 -> IR PARA O MENU PRINCIPAL");
            System.out.print("Digite o número para escolher: ");

            // Captura da escolha do usuário
            int escolha = scanner.nextInt();
            scanner.nextLine(); // Consome a quebra de linha pendente

            // Switch para determinar qual ação tomar
            switch (escolha) {
                case 0:
                    // Saída para o menu principal
                    sair = true;
                    break;

                case 1:
                    // Opção para excluir alerta
                    System.out.println("Excluir Alerta:");
                    System.out.print("ID do Alerta: ");
                    int idAlertaExcluir = scanner.nextInt();
                    scanner.nextLine(); // Consome a quebra de linha

                    // Verificação se o alerta existe no banco antes de tentar excluir
                    Alerta alertaParaExcluir = alertaDao.busca(idAlertaExcluir);
                    if (alertaParaExcluir == null) {
                        System.out.println("Nenhum alerta encontrado com o ID informado.");
                    } else {
                        // Confirmação de exclusão
                        System.out.print("Tem certeza que deseja excluir o alerta? (S/N): ");
                        String confirmacaoExcluir = scanner.nextLine();
                        if (confirmacaoExcluir.equalsIgnoreCase("S")) {
                            alertaDao.delete(idAlertaExcluir);
                            System.out.println("Alerta excluído com sucesso.");
                        } else {
                            System.out.println("Exclusão cancelada.");
                        }
                    }
                    break;

                case 2:
                // Opção para listar alertas
                System.out.println("Listar Alertas:");
                List<Alerta> alertas = alertaDao.listAll();
                if (alertas.isEmpty()) {
                    System.out.println("Nenhum alerta encontrado.");
                } else {
                    // Exibição de cada alerta com seus detalhes
                    System.out.println("\n==== Alertas ====");
                    for (Alerta alerta : alertas) {
                        System.out.println("ID Alerta: " + alerta.getId_alerta());
                        System.out.println("ID Solo: " + alerta.getId_solo());
                        System.out.println("ID Irrigação: " + alerta.getId_irrigacao());
                        System.out.println("Nível Alerta: " + alerta.getNivel_alerta());
                        System.out.println("Descrição: " + alerta.getDescricao());
                        System.out.println("---------------------------");
                    }
                }
                break;


                default:
                    // Caso o usuário forneça uma opção inválida
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
        
/*============================================================================================================*/
    
//                  VISUALIZAÇÃO DE RELATORIOS (VIEWS)


    public static void gerenciarViews(Connection connection, Scanner scanner) {
        ViewDao viewDao = new ViewDao(connection);
        boolean sair = false;

        while (!sair) {
            System.out.println("\n------------------------------");
            System.out.println("  Visualizar Relatorios Views   ");
            System.out.println("------------------------------");
            System.out.println("1 -> Visualizar Relatório de Crescimento");
            System.out.println("2 -> Visualizar Plantas e Solo");
            System.out.println("0 -> Voltar ao Menu Principal");
            System.out.print("Digite o número para escolher: ");

            int escolha = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (escolha) {
                case 0:
                    sair = true;
                    break;

                case 1:
                    System.out.println("\nRelatório de Crescimento:");
                    viewDao.consultarViewRelatorio();
                    break;

                case 2:
                    System.out.println("\nPlantas e Solo:");
                    viewDao.consultarPlantasSolo();
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }


    
}

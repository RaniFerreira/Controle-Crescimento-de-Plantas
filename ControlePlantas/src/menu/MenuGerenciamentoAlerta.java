/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

import dao.AlertaDao;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;
import model.Alerta;

/**
 *
 * @author rodri
 */
public class MenuGerenciamentoAlerta {
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
        
}

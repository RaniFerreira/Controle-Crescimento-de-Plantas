/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

import dao.ViewDao;
import java.sql.Connection;
import java.util.Scanner;

/**
 *
 * @author rodri
 */
public class MenuGerenciarViews {
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

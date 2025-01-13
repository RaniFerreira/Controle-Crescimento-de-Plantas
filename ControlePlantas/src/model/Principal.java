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
import static menu.MenuGerenciamentoAlerta.gerenciamentoAlerta;
import static menu.MenuGerenciarIrrigacao.gerenciarIrrigacao;
import static menu.MenuGerenciarPlantas.gerenciarPlantas;
import static menu.MenuGerenciarRelatorios.gerenciarRelatoriosCrescimento;
import static menu.MenuGerenciarSolos.gerenciarSolos;
import static menu.MenuGerenciarViews.gerenciarViews;

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
  
}

package model;

import dao.SoloDao;
import java.sql.Connection;
import connection.ConexaoBD;
import model.RelatorioCrescimento;
import dao.IrrigacaoDao;
import dao.PlantaDao;
import dao.RelatorioCrescimentoDao;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ranielly Ferreira e Paulo Junior
 */
//Classe principal para rodar o menu de opções
public class Principal {
    public static void main(String[] args) {
        
        Connection connection = ConexaoBD.connect();// instanciando a conexão com o banco(é necessário apenas uma vez)
        // servira para o menu completo(Não apagar)
        
        System.out.println("=================");
        System.out.println("       Menu      ");
        System.out.println("=================");
        
        
        RelatorioCrescimentoDao relatorioDao = new RelatorioCrescimentoDao(connection);
        
        //Testando insert RelatorioDao
                
        /*RelatorioCrescimento relatorio = new RelatorioCrescimento();
        
        relatorio.setId_planta(1);
        relatorio.setData_medicao(new Date());
        relatorio.setAltura((float) 0.50);
        relatorio.setDescricao_saude("Planta saudavel, sem pragas");
        relatorioDao.insert(relatorio);*/
        
        
        
        
        
        /*// Testando o método update
            System.out.println("\nTestando o método update...");
            RelatorioCrescimento relatorioUpdate = new RelatorioCrescimento();
            
            
            relatorioUpdate.setId_relatorio(4);
            relatorioUpdate.setAltura((float) 0.55);
            relatorioUpdate.setDescricao_saude("Planta com um bom crescimento");
            
            relatorioDao.update(relatorioUpdate); */
            
            /*//Testando o método delete
            System.out.println("\nTestando o método delete...");
            relatorioDao.delete(4);  // Deleta o solo com o ID 1*/
            
            /*// Testando o método listAll
            System.out.println("\nTestando o método listAll...");
            List<RelatorioCrescimento> relatorios = relatorioDao.listAll(); // Lista todos os registros

            if (relatorios.isEmpty()) {
                System.out.println("Nenhum relatório encontrado."); // Mensagem mais clara
            } else {
                for (RelatorioCrescimento relatorio : relatorios) {
                    System.out.println(
                        relatorio.getId_relatorio() + " | " +
                        relatorio.getId_planta() + " | " +
                        relatorio.getData_medicao() + " | " +
                        relatorio.getAltura() + " | " +
                        relatorio.getDescricao_saude()
                    );
                }
            }
            */
            
            
    
    }   
    
}

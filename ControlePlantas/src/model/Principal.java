package model;

import dao.SoloDao;
import java.sql.Connection;
import connection.ConexaoBD;
import dao.PlantaDao;
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
        
        
        
        
        /*// Testando o método update
            System.out.println("\nTestando o método update...");
            Solo soloUpdate = new Solo();
            soloUpdate.setId_solo(4);  // Supondo que o id do solo inserido seja 1
            soloUpdate.setTipo_solo("Arenoso");
            soloUpdate.setFertilidade("Média");
            soloUpdate.setUmidade(15.0f);
            soloDao.update(soloUpdate); */ // Atualiza o solo com o ID 1
            
            /*//Testando o método delete
            System.out.println("\nTestando o método delete...");
            soloDao.delete(4);  // Deleta o solo com o ID 1*/
            
            /*// Testando o método listAll
            System.out.println("\nTestando o método listAll...");
            List<Solo> solos = soloDao.listAll();  // Lista todos os registros de solos
            
            if (solos.isEmpty()) {
                System.out.println("Nenhum solo encontrado.");// caso o não tenha nenhuma registro na tabela
            } else {
                for (Solo solo : solos) {
                    System.out.println(solo.getId_solo() + " | " + solo.getTipo_solo() + " | " + solo.getFertilidade() + " | " + solo.getUmidade());
                }
            }
            */
            
    
    }   
    
}

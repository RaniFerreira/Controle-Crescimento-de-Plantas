package model;



import connection.ConexaoBD;

/**
 *
 * @author Ranielly Ferreira e Paulo Junior
 */
//Classe principal para rodar o menu de opções
public class Principal {
    public static void main(String[] args) {
        
        System.out.println("=================");
        System.out.println("       Menu      ");
        System.out.println("=================");
       
   
        ConexaoBD.connect();
       /*como o metodo é estatico posso chama-lo diretamente sem precisar de uma instancia*/
        
        /*o método connect() da classe ConexaoBD é chamado. 
        Esse método tenta estabelecer uma conexão
        com o banco de dados e retorna um objeto do tipo Connection. 
        Esse objeto será atribuído à variável testeConexao.*/

    }

    
    
    
}

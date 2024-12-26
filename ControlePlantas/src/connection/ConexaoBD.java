package connection;

//importação de classes

import java.sql.Connection;         /*a classe Connection é usada para representar uma conexão com o banco*/
import java.sql.DriverManager;      /*DriverManager é a classe responsavel por gerenciar os drivers do banco*/
import java.sql.SQLException;       /*SQLExxeption é usada para tratar erros que poder ocorrer ao interagir com o banco*/



/**
 *
 * @author Ranielly Ferreira e Paulo Junior
 */
//classe para gerenciar a conexão
public class ConexaoBD {
    
    //Essas três variáveis contêm informações essenciais para se conectar ao banco de dados MySQL.
    
    private static final String URL = "jdbc:mysql://localhost:3306/controle_planta"; /* Essa constante define o URL de conexão 
                                                                                        com o banco de dados. No caso, está configurado para conectar ao MySQL no localhost (mesma máquina)
                                                                                        na porta 3306 e ao banco de dados chamado controle_planta. 
                                                                                        O formato padrão é jdbc:mysql://[host]:[porta]/[banco].*/
    private static final String USER = "controle_usuario";              // Nome do usuário definido no banco
    private static final String PASSWORD = "senha_controle_plantas";    // Senha definida no banco
    
    
    /*Definição dométodo connect(), que é responsável por abrir a conexão com o banco de dados. 
        Esse método é estático, 
        o que significa que você pode chamá-lo diretamente pela classe 
        ConexaoBD sem precisar instanciar um objeto dessa classe.*/
    public static Connection connect() {
        Connection connection = null;    //A variável connection é declarada como null = nula. 
                                        //Ela será usada para armazenar a conexão que será estabelecida com o banco de dados.   
        try{
            /*DriverManager.getConnection(URL, USER, PASSWORD): Esse método tenta estabelecer 
            uma conexão com o banco de dados usando as credenciais fornecidas (URL, usuário e senha). 
            Se a conexão for bem-sucedida, ele retorna um objeto Connection e o armazena na variável connection.*/
            connection = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão Feita com sucesso!");
        }
        /*catch(SQLException e): Se ocorrer um erro ao tentar estabelecer a conexão,
        a exceção SQLException será lançada e capturada aqui.*/
        catch(SQLException e){
            
            System.err.println("Connection error: " + e.getMessage());
            
        }
        return connection; 
        /*Após a tentativa de estabelecer a conexão (ou em caso de erro), 
        o método retorna o objeto connection. 
        Se a conexão foi bem-sucedida, ele retorna um objeto Connection válido; 
        caso contrário, retorna null.*/
    }
    

}
    


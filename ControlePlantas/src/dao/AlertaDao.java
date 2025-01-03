
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Alerta;


/**
 *
 * @author Ranielly Ferreira e Paulo Junior
 */
public class AlertaDao {
    
    private Connection connection; // Variável para gerenciar a conexão com o banco de dados

    // Construtor que recebe a conexão
    public AlertaDao(Connection connection) {
        this.connection = connection;
    }
    // Método para deletar um registro pelo ID
    public void delete(int id_alerta) {
        String sql = "DELETE FROM alerta WHERE id_aleta = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define o valor para o parâmetro ID
            stmt.setInt(1, id_alerta);

            // Executa o comando de exclusão no banco de dados
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Alerta deletado com sucesso!");
            } else {
                System.out.println("Nenhum registro encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            // Trata possíveis erros de SQL
            System.err.println("Erro ao deletar alerta: " + e.getMessage());
        }
    }
    
    // Metodo para listar 
    public List<Alerta> listAll() {
    String sql = "SELECT * FROM alerta";
    List<Alerta> alertas = new ArrayList<>();

    try (PreparedStatement stmt = connection.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        // Verificar se o ResultSet tem dados
        if (!rs.isBeforeFirst()) {
            System.out.println("Nenhum dado encontrado na tabela 'alerta'.");
        }

        // Itera sobre os resultados da consulta
        while (rs.next()) {
            Alerta alerta = new Alerta();
            alerta.setId_alerta(rs.getInt("id_alerta")); 
            alerta.setId_solo(rs.getInt("id_solo"));
            alerta.setId_irrigacao(rs.getInt("id_irrigacao"));
            alerta.setNivel_alerta(rs.getInt("nivel_alerta"));
            alerta.setDescricao(rs.getString("descricao"));
            

            // Adiciona o objeto alerta à lista
            alertas.add(alerta);
        }

    } catch (SQLException e) {
        // Trata possíveis erros de SQL
        System.err.println("Erro ao listar alerta: " + e.getMessage());
    }

    return alertas;
}
    
    
}

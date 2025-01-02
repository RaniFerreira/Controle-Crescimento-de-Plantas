
package dao;

import java.sql.Connection;
import model.RelatorioCrescimento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 * @author Ranielly Ferreira e Paulo Junior
 */
public class RelatorioCrescimentoDao {
    
    private Connection connection; // Variável para gerenciar a conexão com o banco de dados

    // Construtor que recebe a conexão
    public RelatorioCrescimentoDao(Connection connection) {
        this.connection = connection;
    }
    
    // Método para inserir um novo registro no banco de dados
    public void insert(RelatorioCrescimento relatorio) {
        String sql = "INSERT INTO relatorioCrescimento (id_planta, data_medicao, altura, descricao_saude) VALUES (?, ?, ?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define os valores para cada parâmetro da query
            stmt.setInt(1, relatorio.getId_planta());
            stmt.setDate(2, new java.sql.Date(relatorio.getData_medicao().getTime()));
            stmt.setFloat(3, relatorio.getAltura());
            stmt.setString(4, relatorio.getDescricao_saude());

            // Executa o comando de inserção no banco de dados
            stmt.executeUpdate();
            System.out.println("Registro inserido com sucesso!");

        } catch (SQLException e) {
            // Trata possíveis erros de SQL
            System.err.println("Erro ao inserir Relatorio: " + e.getMessage());
        }
    }
    
    // Método para deletar um registro pelo ID
    public void delete(int id_relatorio) {
        String sql = "DELETE FROM relatorioCrescimento WHERE id_relatorio= ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define o valor para o parâmetro ID
            stmt.setInt(1, id_relatorio);

            // Executa o comando de exclusão no banco de dados
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Registro deletado com sucesso!");
            } else {
                System.out.println("Nenhum registro encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            // Trata possíveis erros de SQL
            System.err.println("Erro ao deletar registro: " + e.getMessage());
        }
    }
    
    
    // Método para atualizar um registro na tabela relatorioCrescimento
    public void update(RelatorioCrescimento relatorio) {
        String sql = "UPDATE relatorioCrescimento SET data_medicao altura = ?, descricao_saude = ? WHERE id_relatorio = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define os valores para cada parâmetro da query
            stmt.setDate(1, new java.sql.Date(relatorio.getData_medicao().getTime()));
            stmt.setFloat(2, relatorio.getAltura());
            stmt.setString(3, relatorio.getDescricao_saude());

            // Executa o comando de atualização no banco de dados
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Registro atualizado com sucesso!");
            } else {
                System.out.println("Nenhum registro encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            // Trata possíveis erros de SQL
            System.err.println("Erro ao atualizar Relatorio: " + e.getMessage());
        }
    }
    
    // Metodo para listar 
    public List<RelatorioCrescimento> listAll() {
    String sql = "SELECT * FROM relatorioCrescimento";
    List<RelatorioCrescimento> relatorio = new ArrayList<>();

    try (PreparedStatement stmt = connection.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        // Verificar se o ResultSet tem dados
        if (!rs.isBeforeFirst()) {
            System.out.println("Nenhum dado encontrado na tabela 'relatorioCrescimento'.");
        }

        // Itera sobre os resultados da consulta
        while (rs.next()) {
            RelatorioCrescimento relatorioC = new RelatorioCrescimento();
            relatorioC.setId_relatorio(rs.getInt("id_relatorio")); 
            relatorioC.setId_planta(rs.getInt("id_planta")); 
            relatorioC.setData_medicao(new Date(rs.getDate("data_medicao").getTime())); 
            relatorioC.setAltura(rs.getFloat("altura"));
            relatorioC.setDescricao_saude(rs.getString("descricao_altura")); 

            // Adiciona o objeto relatoorio à lista
            relatorio.add(relatorioC);
        }

    } catch (SQLException e) {
        // Trata possíveis erros de SQL
        System.err.println("Erro ao listar srelatorio: " + e.getMessage());
    }

        return relatorio;
    }
    
    
}

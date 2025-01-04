
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
        String sql = "INSERT INTO relatorio_crescimento (id_planta, data_medicao, altura, descricao_saude) VALUES (?, ?, ?,?)";

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
        String sql = "DELETE FROM relatorio_crescimento WHERE id_relatorio= ?";

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
        String sql = "UPDATE relatorio_crescimento SET  altura = ?, descricao_saude = ? WHERE id_relatorio = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define os valores para cada parâmetro da query
            
            stmt.setFloat(1, relatorio.getAltura());
            stmt.setString(2, relatorio.getDescricao_saude());
            stmt.setInt(3, relatorio.getId_relatorio());

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
    
    public RelatorioCrescimento busca(int id) {
        // SQL para buscar o relatório de crescimento com base no ID
        String sql = "SELECT * FROM relatorio_crescimento WHERE id_relatorio = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id); // Define o ID para a consulta

            // Executa a consulta no banco de dados
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Se encontrar um resultado, cria um objeto RelatorioCrescimento
                RelatorioCrescimento relatorio = new RelatorioCrescimento();

                // Preenche o objeto com os dados do banco
                relatorio.setId_relatorio(rs.getInt("id_relatorio"));
                relatorio.setId_planta(rs.getInt("id_planta"));
                relatorio.setData_medicao(rs.getDate("data_medicao"));
                relatorio.setAltura(rs.getFloat("altura"));
                relatorio.setDescricao_saude(rs.getString("descricao_saude"));

                // Retorna o relatório encontrado
                return relatorio;
            }
        } catch (SQLException e) {
            // Em caso de erro, exibe a mensagem de erro
            System.err.println("Erro ao buscar relatório de crescimento: " + e.getMessage());
        }

        // Retorna null se nenhum relatório for encontrado
        return null;
    }

    
    // Metodo para listar 
    public List<RelatorioCrescimento> listAll() {
    String sql = "SELECT * FROM relatorio_crescimento";
    List<RelatorioCrescimento> relatorio = new ArrayList<>();

    try (PreparedStatement stmt = connection.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        // Verificar se o ResultSet tem dados
        if (!rs.isBeforeFirst()) {
            System.out.println("Nenhum dado encontrado na tabela 'relatorio_crescimento'.");
        }

        // Itera sobre os resultados da consulta
        while (rs.next()) {
            RelatorioCrescimento relatorioC = new RelatorioCrescimento();
            relatorioC.setId_relatorio(rs.getInt("id_relatorio")); 
            relatorioC.setId_planta(rs.getInt("id_planta")); 
            relatorioC.setData_medicao(new Date(rs.getDate("data_medicao").getTime())); 
            relatorioC.setAltura(rs.getFloat("altura"));
            relatorioC.setDescricao_saude(rs.getString("descricao_saude")); 

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

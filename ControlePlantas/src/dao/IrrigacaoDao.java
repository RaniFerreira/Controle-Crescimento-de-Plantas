package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Irrigacao;

/**
 *
 * @author Ranielly Ferreira e Paulo Junior
 */
public class IrrigacaoDao {
    
    private Connection connection;

    public IrrigacaoDao(Connection connection) {
        this.connection = connection;
    }

    // Método para inserir um registro na tabela irrigacao
    public void insert(Irrigacao irrigacao) {
        
        String sql = "INSERT INTO irrigacao (id_planta, data_irrigacao, quantidade_agua) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, irrigacao.getId_planta());
            stmt.setDate(2, new java.sql.Date(irrigacao.getData_irrigacao().getTime()));
            stmt.setFloat(3, irrigacao.getQuantidade_agua());
            // Executa o comando de inserção no banco de dados
            stmt.executeUpdate();
            System.out.println("Registro de Irrigacao inserido com sucesso!");
           
        } catch (SQLException e) {
            // Trata possíveis erros de SQL
            System.err.println("Erro ao inserir irrigacao: " + e.getMessage());
        }
        
    }
    
    // Método para deletar um registro pelo ID
    public void delete(int id_irrigacao) {
        String sql = "DELETE FROM irrigacao WHERE id_irrigacao = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define o valor para o parâmetro ID
            stmt.setInt(1, id_irrigacao);

            // Executa o comando de exclusão no banco de dados
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Registro de Irrigacao deletado com sucesso!");
            } else {
                System.out.println("Nenhum registro de Irrigacao encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            // Trata possíveis erros de SQL
            System.err.println("Erro ao deletar registro de Irrigacao: " + e.getMessage());
        }
    }


    // Método para atualizar um registro na tabela irrigacao
    public void update(Irrigacao irrigacao) {
        String sql = "UPDATE irrigacao SET id_planta = ?, data_irrigacao = ?, quantidade_agua = ? WHERE id_irrigacao = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define os valores para cada parâmetro da query
            stmt.setInt(1, irrigacao.getId_planta());
            stmt.setDate(2, new java.sql.Date(irrigacao.getData_irrigacao().getTime()));
            stmt.setFloat(3, irrigacao.getQuantidade_agua());
            stmt.setInt(4, irrigacao.getId_irrigacao());
            stmt.executeUpdate();

            // Executa o comando de atualização no banco de dados
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Registro de irrigacao atualizado com sucesso!");
            } else {
                System.out.println("Nenhum registro de irrigacao encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            // Trata possíveis erros de SQL
            System.err.println("Erro ao atualizar irrigacao: " + e.getMessage());
        }
    }
    
    public Irrigacao busca(int id) {
        // SQL para buscar a irrigação com base no ID
        String sql = "SELECT * FROM irrigacao WHERE id_irrigacao = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id); // Define o ID para a consulta

            // Executa a consulta no banco de dados
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Se encontrar um resultado, cria um objeto Irrigacao
                Irrigacao irrigacao = new Irrigacao();

                // Preenche o objeto com os dados do banco
                irrigacao.setId_irrigacao(rs.getInt("id_irrigacao"));
                irrigacao.setId_planta(rs.getInt("id_planta"));
                irrigacao.setData_irrigacao(rs.getDate("data_irrigacao"));
                irrigacao.setQuantidade_agua(rs.getFloat("quantidade_agua"));

                // Retorna a irrigação encontrada
                return irrigacao;
            }
        } catch (SQLException e) {
            // Em caso de erro, exibe a mensagem de erro
            System.err.println("Erro ao buscar irrigação: " + e.getMessage());
        }

        // Retorna null se nenhuma irrigação for encontrada
        return null;
    }


    // Método para listar todos os registros na tabela irrigacao
    public List<Irrigacao> listAll() {
        String sql = "SELECT * FROM irrigacao";
        List<Irrigacao> irrigacoes = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Verifica se o ResultSet tem dados
            if (!rs.isBeforeFirst()) {
                System.out.println("Nenhum dado encontrado na tabela 'irrigacao'.");
            }

            // Itera sobre os resultados da consulta
            while (rs.next()) {
                Irrigacao irrigacao = new Irrigacao();
                irrigacao.setId_irrigacao(rs.getInt("id_irrigacao"));
                irrigacao.setId_planta(rs.getInt("id_planta"));
                irrigacao.setData_irrigacao(new Date(rs.getDate("data_irrigacao").getTime()));
                irrigacao.setQuantidade_agua(rs.getFloat("quantidade_agua"));

                // Adiciona o objeto irrigacao à lista
                irrigacoes.add(irrigacao);
            }

        } catch (SQLException e) {
            // Trata possíveis erros de SQL
            System.err.println("Erro ao listar irrigacao: " + e.getMessage());
        }

        return irrigacoes;
    }
    
    
}

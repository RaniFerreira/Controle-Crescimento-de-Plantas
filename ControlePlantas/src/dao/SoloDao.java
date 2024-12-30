package dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Solo;

public class SoloDao {

    private Connection connection; // Variável para gerenciar a conexão com o banco de dados

    // Construtor que recebe a conexão
    public SoloDao(Connection connection) {
        this.connection = connection;
    }

    // Método para inserir um novo registro no banco de dados
    public void insert(Solo solo) {
        String sql = "INSERT INTO solo (tipo_solo, fertilidade, umidade) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define os valores para cada parâmetro da query
            stmt.setString(1, solo.getTipo_solo());
            stmt.setString(2, solo.getFertilidade());
            stmt.setFloat(3, solo.getUmidade());

            // Executa o comando de inserção no banco de dados
            stmt.executeUpdate();
            System.out.println("Registro inserido com sucesso!");

        } catch (SQLException e) {
            // Trata possíveis erros de SQL
            System.err.println("Erro ao inserir solo: " + e.getMessage());
        }
    }
    
    // Método para deletar um registro pelo ID
    public void delete(int id_solo) {
        String sql = "DELETE FROM solo WHERE id_solo = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define o valor para o parâmetro ID
            stmt.setInt(1, id_solo);

            // Executa o comando de exclusão no banco de dados
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Registro deletado com sucesso!");
            } else {
                System.out.println("Nenhum registro encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            // Trata possíveis erros de SQL
            System.err.println("Erro ao deletar solo: " + e.getMessage());
        }
    }
    
    // Método para atualizar um registro na tabela Solo
    public void update(Solo solo) {
        String sql = "UPDATE solo SET tipo_solo = ?, fertilidade = ?, umidade = ? WHERE id_solo = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define os valores para cada parâmetro da query
            stmt.setString(1, solo.getTipo_solo());
            stmt.setString(2, solo.getFertilidade());
            stmt.setFloat(3, solo.getUmidade());
            stmt.setInt(4, solo.getId_solo());

            // Executa o comando de atualização no banco de dados
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Registro atualizado com sucesso!");
            } else {
                System.out.println("Nenhum registro encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            // Trata possíveis erros de SQL
            System.err.println("Erro ao atualizar solo: " + e.getMessage());
        }
    }
    
    public List<Solo> listAll() {
    String sql = "SELECT * FROM solo";
    List<Solo> solos = new ArrayList<>();

    try (PreparedStatement stmt = connection.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        // Verificar se o ResultSet tem dados
        if (!rs.isBeforeFirst()) {
            System.out.println("Nenhum dado encontrado na tabela 'solo'.");
        }

        // Itera sobre os resultados da consulta
        while (rs.next()) {
            Solo solo = new Solo();
            solo.setId_solo(rs.getInt("id_solo")); // Define o ID do solo
            solo.setTipo_solo(rs.getString("tipo_solo")); // Define o tipo do solo
            solo.setFertilidade(rs.getString("fertilidade")); // Define a fertilidade
            solo.setUmidade(rs.getFloat("umidade")); // Define a umidade

            // Adiciona o objeto Solo à lista
            solos.add(solo);
        }

    } catch (SQLException e) {
        // Trata possíveis erros de SQL
        System.err.println("Erro ao listar solos: " + e.getMessage());
    }

    return solos;
}
}

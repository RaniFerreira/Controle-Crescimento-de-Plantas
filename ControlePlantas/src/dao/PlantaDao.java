package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Planta;

public class PlantaDao {

    private Connection connection; // Variável para gerenciar a conexão com o banco de dados

    // Construtor que recebe a conexão
    public PlantaDao(Connection connection) {
        this.connection = connection;
    }

    // Método para inserir um novo registro no banco de dados
    public void insert(Planta planta) {
        String sql = "INSERT INTO planta (id_solo, nome, tipo_planta, data_plantio) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define os valores para cada parâmetro da query
            stmt.setInt(1, planta.getId_solo());
            stmt.setString(2, planta.getNome());
            stmt.setString(3, planta.getTipo_planta());
            stmt.setDate(4, new java.sql.Date(planta.getData_plantio().getTime()));

            // Executa o comando de inserção no banco de dados
            stmt.executeUpdate();
            System.out.println("Registro de planta inserido com sucesso!");

        } catch (SQLException e) {
            // Trata possíveis erros de SQL
            System.err.println("Erro ao inserir planta: " + e.getMessage());
        }
    }

    // Método para deletar um registro pelo ID
    public void delete(int id_planta) {
        String sql = "DELETE FROM planta WHERE id_planta = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define o valor para o parâmetro ID
            stmt.setInt(1, id_planta);

            // Executa o comando de exclusão no banco de dados
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Registro de planta deletado com sucesso!");
            } else {
                System.out.println("Nenhum registro de planta encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            // Trata possíveis erros de SQL
            System.err.println("Erro ao deletar planta: " + e.getMessage());
        }
    }

    // Método para atualizar um registro na tabela Planta
    public void update(Planta planta) {
        String sql = "UPDATE planta SET id_solo = ?, nome = ?, tipo_planta = ?, data_plantio = ? WHERE id_planta = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define os valores para cada parâmetro da query
            stmt.setInt(1, planta.getId_solo());
            stmt.setString(2, planta.getNome());
            stmt.setString(3, planta.getTipo_planta());
            stmt.setDate(4, new java.sql.Date(planta.getData_plantio().getTime()));
            stmt.setInt(5, planta.getId_planta());

            // Executa o comando de atualização no banco de dados
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Registro de planta atualizado com sucesso!");
            } else {
                System.out.println("Nenhum registro de planta encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            // Trata possíveis erros de SQL
            System.err.println("Erro ao atualizar planta: " + e.getMessage());
        }
    }

    // Método para listar todos os registros na tabela Planta
    public List<Planta> listAll() {
        String sql = "SELECT * FROM planta";
        List<Planta> plantas = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Verifica se o ResultSet tem dados
            if (!rs.isBeforeFirst()) {
                System.out.println("Nenhum dado encontrado na tabela 'planta'.");
            }

            // Itera sobre os resultados da consulta
            while (rs.next()) {
                Planta planta = new Planta();
                planta.setId_planta(rs.getInt("id_planta"));
                planta.setId_solo(rs.getInt("id_solo"));
                planta.setNome(rs.getString("nome"));
                planta.setTipo_planta(rs.getString("tipo_planta"));
                planta.setData_plantio(new Date(rs.getDate("data_plantio").getTime()));

                // Adiciona o objeto Planta à lista
                plantas.add(planta);
            }

        } catch (SQLException e) {
            // Trata possíveis erros de SQL
            System.err.println("Erro ao listar plantas: " + e.getMessage());
        }

        return plantas;
    }
}

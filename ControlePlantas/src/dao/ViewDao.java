/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ranielly Ferreira e Paulo Junior
 */

public class ViewDao {
    
    private Connection connection; // Variável para gerenciar a conexão com o banco de dados

    // Construtor que recebe a conexão
    public ViewDao(Connection connection) {
        this.connection = connection;
    }
    


    public void consultarViewRelatorio() {
        // SQL para consultar a view existente
        String sql = "SELECT * FROM view_relatorios_crescimento ORDER BY id_planta, data_medicao";

        // Mapa para armazenar os relatórios agrupados por planta
        Map<Integer, StringBuilder> relatoriosPorPlanta = new HashMap<>();

        // Tentativa de executar a consulta
        try (PreparedStatement statement = connection.prepareStatement(sql); // Prepara a consulta
             ResultSet resultSet = statement.executeQuery()) { // Executa a consulta e armazena o resultado

            // Itera pelos resultados obtidos da view
            while (resultSet.next()) {
                // Obtém os valores de cada coluna do resultado
                int idPlanta = resultSet.getInt("id_planta"); // ID da planta
                String nomePlanta = resultSet.getString("nome_planta"); // Nome da planta
                String tipoPlanta = resultSet.getString("tipo_planta"); // Tipo da planta (ex.: Fruta, Raiz)
                String dataMedicao = resultSet.getString("data_medicao"); // Data da medição
                float altura = resultSet.getFloat("altura"); // Altura registrada da planta
                String descricaoSaude = resultSet.getString("descricao_saude"); // Descrição da saúde da planta

                // Verifica se já existe uma entrada para essa planta no mapa
                StringBuilder relatorio = relatoriosPorPlanta.getOrDefault(idPlanta, new StringBuilder());

                // Adiciona a descrição da planta junto com o relatório de crescimento
                if (relatorio.length() == 0) {
                    relatorio.append("ID Planta: ").append(idPlanta)
                            .append(", Nome: ").append(nomePlanta)
                            .append(", Tipo: ").append(tipoPlanta)
                            .append("\n");
                }

                // Adiciona o relatório de crescimento da planta
                relatorio.append("Data Medição: ").append(dataMedicao)
                        .append(", Altura: ").append(altura)
                        .append(", Descrição: ").append(descricaoSaude)
                        .append("\n");

                // Atualiza o mapa com o relatório completo
                relatoriosPorPlanta.put(idPlanta, relatorio);
            }

            // Exibe os relatórios agrupados por planta
            for (Map.Entry<Integer, StringBuilder> entry : relatoriosPorPlanta.entrySet()) {
                System.out.println(entry.getValue().toString());
                System.out.println("---------------------------------------------"); // Separador entre as plantas
            }

        } catch (SQLException e) {
            // Captura e exibe erros relacionados ao banco de dados
            System.err.println("Erro ao consultar a view: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
     // Método para consultar a view e exibir as informações das plantas e solo
    public void consultarPlantasSolo() {
        // SQL para consultar a view de plantas e solo
        String sql = "SELECT * FROM view_plantas_solo";

        // Tentativa de executar a consulta
        try (PreparedStatement statement = connection.prepareStatement(sql); // Prepara a consulta
             ResultSet resultSet = statement.executeQuery()) { // Executa a consulta e armazena o resultado

            // Itera pelos resultados obtidos da view
            while (resultSet.next()) {
                // Obtém os valores de cada coluna do resultado
                int idPlanta = resultSet.getInt("id_planta"); // ID da planta
                String nomePlanta = resultSet.getString("nome_planta"); // Nome da planta
                String tipoPlanta = resultSet.getString("tipo_planta"); // Tipo da planta
                String dataPlantio = resultSet.getString("data_plantio"); // Data do plantio
                String tipoSolo = resultSet.getString("tipo_solo"); // Tipo de solo
                String fertilidade = resultSet.getString("fertilidade"); // Fertilidade do solo
                float umidade = resultSet.getFloat("umidade"); // Umidade do solo

                // Exibe as informações recuperadas da view
                System.out.println("ID Planta: " + idPlanta);
                System.out.println("Nome Planta: " + nomePlanta);
                System.out.println("Tipo Planta: " + tipoPlanta);
                System.out.println("Data de Plantio: " + dataPlantio);
                System.out.println("Tipo de Solo: " + tipoSolo);
                System.out.println("Fertilidade: " + fertilidade);
                System.out.println("Umidade do Solo: " + umidade);
                System.out.println("---------------------------------------------"); // Separador entre as plantas
            }

        } catch (SQLException e) {
            // Captura e exibe erros relacionados ao banco de dados
            System.err.println("Erro ao consultar a view: " + e.getMessage());
            e.printStackTrace();
        }
    }




    
    
}

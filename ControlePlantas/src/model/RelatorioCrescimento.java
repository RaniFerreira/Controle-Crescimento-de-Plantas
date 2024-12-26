package model;

import java.util.Date;

/**
 *
 * @author Ranielly Ferreira e Paulo Junior
 */
//classe de dados da entidade relatorio_crescimento
public class RelatorioCrescimento {
    
    //declaração das variavéis encapsuladas
    
    private int id_relatorio;           //chave primaria do tipo inteiro, será auto incrementada
    private int id_planta;              // variavel para a chave estrangeira
    private Date data_medicao;          // data da medicao
    private float altura;               // altura que da planta 
    private String descricao_saude;     // descrever o estado de saude da planta

    public RelatorioCrescimento() {
        // Este construtor pode ser usado para inicializar com valores padrão
    }

    //construtor com parametros
    public RelatorioCrescimento(int id_relatorio, int id_planta, Date data_medicao, float altura, String descricao_saude) {
        this.id_relatorio = id_relatorio;
        this.id_planta = id_planta;
        this.data_medicao = data_medicao;
        this.altura = altura;
        this.descricao_saude = descricao_saude;
    }
    
    //getters e setters

    public String getDescricao_saude() {
        return descricao_saude;
    }

    public void setDescricao_saude(String descricao_saude) {
        this.descricao_saude = descricao_saude;
    }

    public int getId_relatorio() {
        return id_relatorio;
    }

    public void setId_relatorio(int id_relatorio) {
        this.id_relatorio = id_relatorio;
    }

    public int getId_planta() {
        return id_planta;
    }

    public void setId_planta(int id_planta) {
        this.id_planta = id_planta;
    }

    public Date getData_medicao() {
        return data_medicao;
    }

    public void setData_medicao(Date data_medicao) {
        this.data_medicao = data_medicao;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }
    
    
    
}

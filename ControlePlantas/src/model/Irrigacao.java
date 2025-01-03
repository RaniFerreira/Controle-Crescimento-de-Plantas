package model;

import java.util.Date;

/**
 *
 * @author Ranielly Ferreira e Paulo Junior
 */
//classe de dados da entidade Irrigacao
public class Irrigacao {
    
    //declaração das variavéis encapsuladas
    
    private int id_irrigacao;           //chave primaria do tipo inteiro, será auto incrementada
    private int id_planta;              // variavel para a chave estrangeira
    private Date data_irrigacao;        // variavel para a data que a planta foi irrigada
    private float quantidade_agua;      // quantidade de agua usada na irrigacao 

    public Irrigacao() {
        // Este construtor pode ser usado para inicializar com valores padrão
    }

    //construtor com parametros
    public Irrigacao( int id_planta, Date data_irrigacao, float quantidade_agua) {
        this.id_planta = id_planta;
        this.data_irrigacao = data_irrigacao;
        this.quantidade_agua = quantidade_agua;
    }
    
    // getters e setters
    
    public int getId_irrigacao() {
        return id_irrigacao;
    }

    public void setId_irrigacao(int id_irrigacao) {
        this.id_irrigacao = id_irrigacao;
    }

    public int getId_planta() {
        return id_planta;
    }

    public void setId_planta(int id_planta) {
        this.id_planta = id_planta;
    }

    public Date getData_irrigacao() {
        return data_irrigacao;
    }

    public void setData_irrigacao(Date data_irrigacao) {
        this.data_irrigacao = data_irrigacao;
    }

    public float getQuantidade_agua() {
        return quantidade_agua;
    }

    public void setQuantidade_agua(float quantidade_agua) {
        this.quantidade_agua = quantidade_agua;
    }

    
}

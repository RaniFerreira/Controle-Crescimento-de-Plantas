package model;



/**
 *
 * @author Ranielly Ferreira e Paulo Junior
 */
//classe de dados da entidade Irrigacao
public class Solo {
    
    //declaração das variavéis encapsuladas
    
    private int id_solo;                //chave primaria do tipo inteiro, será auto incrementada
    private String tipo_solo;           // tipo do solo que é uma String
    private String fertilidade;         // descricao de como o solo foi fetilizado
    private float umidade;              // umididade do solo

    public Solo() {
        // Este construtor pode ser usado para inicializar com valores padrão
    }

     //construtor com parametros
    public Solo( String tipo_solo, String fertilidade, float umidade) {
        this.tipo_solo = tipo_solo;
        this.fertilidade = fertilidade;
        this.umidade = umidade;
    }

    // getters e setters
    public int getId_solo() {
        return id_solo;
    }

    public void setId_solo(int id_solo) {
        this.id_solo = id_solo;
    }

    public String getTipo_solo() {
        return tipo_solo;
    }

    public void setTipo_solo(String tipo_solo) {
        this.tipo_solo = tipo_solo;
    }

    public String getFertilidade() {
        return fertilidade;
    }

    public void setFertilidade(String fertilidade) {
        this.fertilidade = fertilidade;
    }

    public float getUmidade() {
        return umidade;
    }

    public void setUmidade(float umidade) {
        this.umidade = umidade;
    }

    @Override
    public String toString() {
        return "Solo{" + "id_solo=" + id_solo + ", tipo_solo=" + tipo_solo + ", fertilidade=" + fertilidade + ", umidade=" + umidade + '}';
    }

    
}

package model;

import java.util.Date;

/**
 *
 * @author Ranielly Ferreira e Paulo Junior
 */

//classe de dados da entidade planta
public class Planta {
    //dados que serão inseridos no cadastro de cada planta
    //declaração das variavéis encapsuladas
    
    private int id_planta;              //chave primaria do tipo inteiro, será auto incrementada
    private int id_solo;
    private String nome;                // o nome da planta do tipo String
    private String tipo_planta;         // tipo da lanto do String
    private Date data_plantio;          //a data que que a planta foi plantada

    /*Esse construtor não recebe parâmetros
    e é usado para criar um objeto da classe Planta
    sem definir valores iniciais para seus atributos.*/
    public Planta() {
    }

    /*Esse construtor é útil quando você quer criar um objeto da classe Planta
    já com todos os valores definidos de uma vez, ao invés de definir 
    os valores individualmente com setters depois.*/

    public Planta(int id_solo, String nome, String tipo_planta, Date data_plantio) {
        this.id_solo = id_solo;
        this.nome = nome;
        this.tipo_planta = tipo_planta;
        this.data_plantio = data_plantio;
    }
    
    
    //abaixo esta os getters e setters para manipulação de variaveis encapsuladas
    /*Esses métodos são essenciais para garantir o encapsulamento, permitindo o acesso 
    e a modificação dos atributos privados de maneira controlada, sem expor diretamente os campos.*/
    public Date getData_plantio() {
        return data_plantio;
    }

    public void setData_plantio(Date data_plantio) {
        this.data_plantio = data_plantio;
    }

    public int getId_planta() {
        return id_planta;
    }

    public void setId_planta(int id_planta) {
        this.id_planta = id_planta;
    }

    public int getId_solo() {
        return id_solo;
    }

    public void setId_solo(int id_solo) {
        this.id_solo = id_solo;
    }
    
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo_planta() {
        return tipo_planta;
    }

    public void setTipo_planta(String tipo_planta) {
        this.tipo_planta = tipo_planta;
    }

    @Override
    public String toString() {
        return "Planta{" + "id_planta=" + id_planta + ", id_solo=" + id_solo + ", nome=" + nome + ", tipo_planta=" + tipo_planta + ", data_plantio=" + data_plantio + '}';
    }
    
    
    
}

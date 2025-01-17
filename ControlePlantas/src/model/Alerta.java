package model;

/**
 *
 * @author Ranielly Ferreira e Paulo Junior
 */
//classe de dados da entidade alerta
public class Alerta {
    
    //declaração das variavéis encapsuladas
    
    private int id_alerta;      // chave primaria do tipo inteiro, será auto incrementada 
    private int nivel_alerta;   // nivel de alerta do tipo inteiro   
    private String descricao;   // descricao do alerta do tipo String  
    

    //construtor vazio
    public Alerta() {
        // Este construtor pode ser utilizado para inicializar com valores padrão
    }

    // Construtor com parâmetros para inicializar as variáveis diretamente
    public Alerta(int nivel_alerta, String descricao) {
       
        this.nivel_alerta = nivel_alerta;
        this.descricao = descricao;
    }

    // Getters e Setters
    public int getId_alerta() {
        return id_alerta;
    }

    public void setId_alerta(int id_alerta) {
        this.id_alerta = id_alerta;
    }

    public int getNivel_alerta() {
        return nivel_alerta;
    }

    public void setNivel_alerta(int nivel_alerta) {
        this.nivel_alerta = nivel_alerta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Alerta{" + "id_alerta=" + id_alerta + ", nivel_alerta=" + nivel_alerta + ", descricao=" + descricao + '}';
    }
    
}

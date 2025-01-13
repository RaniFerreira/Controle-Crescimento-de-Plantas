# 🌍 Sistema de Controle de Crescimento e Irrigação de Plantas
Bem-vindo ao projeto de Controle de Crescimento e Irrigação de Plantas! Este sistema foi desenvolvido para monitorar, automatizar e otimizar o cuidado com plantas, garantindo que elas recebam as condições ideais para um crescimento saudável. 🌿💧
***

## 📂 Organização do Repositório
  ```plaintext
  .
  ├── ControlePlantas        ->  Projeto
  ├── script banco de dados  ->  Script DDL
  └── README                 ->  Descriçaõ do projeto
  ```

## 📋 Descrição
Este projeto integra um banco de dados MySQL com uma aplicação interativa para gerenciar informações sobre plantas, solos e irrigação. Ele foi criado como parte de um trabalho prático da disciplina de Banco de Dados II no IFTM - Campus Patrocínio.

As principais funcionalidades incluem o gerenciamento de dados (CRUD), automação com triggers e visualizações para análise de dados.

## ✨ Principais Funcionalidades
  *  CRUD Completo: Operações de criação, leitura, atualização e exclusão de dados.
  *  Automação com Triggers: Executa ações automáticas no banco de dados.
  *  Visões Inteligentes: Consultas otimizadas para análises eficientes.
  *  Interface Amigável: Sistema interativo para operação e visualização dos dados.

## 🛠️ Tecnologias Utilizadas
  * 🛢️ Banco de Dados: MySQL
    *  5 tabelas com relacionamentos via chaves estrangeiras.
    *  2 visão criada para consultas otimizadas.
    *  4 Trigger para automação de processos.
    
  ### ☕ Linguagem de Programação: SQL e Java
  ### ⚙️ Ferramentas Adicionais   
  *  MySQL Workbench 8.0 CE
  *  NetBeans IDE

## 🗂️ Estrutura do Banco de Dados
### 📋 Tabelas Principais
  * Planta 🌱: Registro de espécies e características.
  * Alerta 📡: Monitoramento de condições ambientais.
  * Irrigação 💧: Controle de ciclos de rega.
  * Relatorio de Crescimento 🌳: Dados de progresso das plantas.
  * Solo 🪴: Registro de solos e características

### 🔗 Relacionamentos
As tabelas estão conectadas por chaves estrangeiras para garantir integridade e consistência.

## ⚡ Trigger
    
  #### `alerta_umidade_solo`
Dispara após a inserção de novos registros na tabela `solo`, verificando a umidade do solo e gerando alertas se estiver fora dos limites esperados.

```
DELIMITER $$

CREATE TRIGGER alerta_umidade_solo
AFTER INSERT ON solo
FOR EACH ROW
BEGIN
    IF NEW.umidade < 10 THEN
        INSERT INTO alerta (nivel_alerta, descricao)
        VALUES (1, CONCAT('Alerta: A umidade do solo ', NEW.id_solo, ' está abaixo do esperado.'));
    ELSEIF NEW.umidade > 15 THEN
        INSERT INTO alerta (nivel_alerta, descricao)
        VALUES (3, CONCAT('Alerta: A umidade do solo ', NEW.id_solo, ' está acima do esperado.'));
    END IF;
END $$

DELIMITER ;
```
 ####  `alerta_solo_update`
 Dispara após a atualização de registros na tabela solo, verificando se a nova umidade do solo está dentro dos limites esperados.
 ```
DELIMITER $$

CREATE TRIGGER alerta_solo_update
AFTER UPDATE ON solo
FOR EACH ROW
BEGIN
    IF NEW.umidade < 10 THEN
        INSERT INTO alerta (nivel_alerta, descricao)
        VALUES (1, CONCAT('Alerta: A umidade do solo ', NEW.id_solo, ' está abaixo do esperado.'));
    ELSEIF NEW.umidade > 15 THEN
        INSERT INTO alerta (nivel_alerta, descricao)
        VALUES (3, CONCAT('Alerta: A umidade do solo ', NEW.id_solo, ' está acima do esperado.'));
    END IF;
END $$

DELIMITER ;
 ```
 ####  `alerta_irriga_agua`
 Dispara após a inserção de novos registros na tabela irrigacao, verificando a quantidade de água aplicada e gerando alertas se estiver fora dos limites esperados.
 ```
DELIMITER $$

CREATE TRIGGER alerta_irriga_agua
AFTER INSERT ON irrigacao
FOR EACH ROW
BEGIN
    IF NEW.quantidade_agua < 1.0 THEN
        INSERT INTO alerta (nivel_alerta, descricao)
        VALUES (1, CONCAT('Alerta: A quantidade de água para a planta ', NEW.id_planta, ' está abaixo do esperado.'));
    ELSEIF NEW.quantidade_agua > 2.0 THEN
        INSERT INTO alerta (nivel_alerta, descricao)
        VALUES (3, CONCAT('Alerta: A quantidade de água para a planta ', NEW.id_planta, ' está acima do esperado.'));
    END IF;
END $$

DELIMITER ;

 ```
 ####  `alerta_irriga_update`
 Dispara após a atualização de registros na tabela irrigacao, verificando a nova quantidade de água aplicada e gerando alertas se estiver fora dos limites esperados.
 ```
DELIMITER $$

CREATE TRIGGER alerta_irriga_update
AFTER UPDATE ON irrigacao
FOR EACH ROW
BEGIN
    IF NEW.quantidade_agua < 1.0 THEN
        INSERT INTO alerta (nivel_alerta, descricao)
        VALUES (1, CONCAT('Alerta: A quantidade de água para a planta ', NEW.id_planta, ' está abaixo do esperado.'));
    ELSEIF NEW.quantidade_agua > 2.0 THEN
        INSERT INTO alerta (nivel_alerta, descricao)
        VALUES (3, CONCAT('Alerta: A quantidade de água para a planta ', NEW.id_planta, ' está acima do esperado.'));
    END IF;
END $$

DELIMITER ;

 ```
    
  ## 📊 Visão
 #### `view_plantas_solo`
 Junta as tabelas planta e solo para fornecer informações sobre cada planta, incluindo seu nome, tipo, data de plantio, e as características do solo (tipo, fertilidade e umidade).

```
CREATE VIEW view_plantas_solo AS
    SELECT 
        planta.id_planta,
        planta.nome AS nome_planta,
        planta.tipo_planta,
        planta.data_plantio,
        solo.tipo_solo,
        solo.fertilidade,
        solo.umidade
    FROM planta
    JOIN solo ON planta.id_solo = solo.id_solo;

```
#### `view_relatorios_crescimento`
 Combina as tabelas planta e relatorio_crescimento para fornecer detalhes sobre o crescimento das plantas, incluindo a data de medição, altura da planta e descrição da saúde da planta.

```
CREATE VIEW view_relatorios_crescimento AS
SELECT 
    planta.id_planta,
    planta.nome AS nome_planta,
    planta.tipo_planta,
    relatorio_crescimento.data_medicao,
    relatorio_crescimento.altura,
    relatorio_crescimento.descricao_saude
FROM 
    relatorio_crescimento
JOIN 
    planta ON relatorio_crescimento.id_planta = planta.id_planta;


```
    
***
## 🤝 Equipe
  * PAULO JUNIOR RODRIGUES
  * RANIELLY FERREIRA DOS SANTOS

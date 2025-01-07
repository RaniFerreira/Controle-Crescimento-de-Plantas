
	CREATE DATABASE controle_planta; /*criação do banco de dados*/
	USE controle_planta;  /*usando o banco de dados*/
    
    /*criacao da tabela solo*/
	CREATE TABLE solo( 
		id_solo INT PRIMARY KEY NOT NULL AUTO_INCREMENT,      /*chave primaria que será auto 
																incrementada e o campo não poderá ficar nulo*/
		tipo_solo VARCHAR(50) NOT NULL,								  
		fertilidade VARCHAR(20) NOT NULL,
        umidade float NOT NULL

	);

	/*criacao da tabela planta*/
	CREATE TABLE planta(
		id_planta INT PRIMARY KEY NOT NULL AUTO_INCREMENT, 	/*chave primaria que será auto 
																incrementada e o campo não poderá ficar nulo*/
		id_solo INT,										/*campo para referenciar a chave estrangeira */
		nome VARCHAR(100),  								/*camo para guardar o nome da planta */
		tipo_planta VARCHAR(50), 							/*tipo da planta*/
		data_plantio DATE  ,									/*camopo para guardar a data que planta foi plantada*/
        FOREIGN KEY (id_solo) REFERENCES solo(id_solo)
        ON UPDATE CASCADE
        ON DELETE CASCADE

	);

	/*criacao da tabela Irrigacao*/
	CREATE TABLE irrigacao(
		id_irrigacao INT PRIMARY KEY NOT NULL AUTO_INCREMENT,	/*chave primaria que será auto 
																incrementada e o campo não poderá ficar nulo*/
		id_planta INT,											/*campo criado se tornar a chave estrangeira*/
		data_irrigacao DATE NOT NULL,							/*data que a planta esta sendo irrigada*/
		quantidade_agua FLOAT NOT NULL,								
		FOREIGN KEY (id_planta) REFERENCES planta(id_planta)    /*criacao da chave estrangeira*/
		ON UPDATE CASCADE 										/*comando para atualizar a chave estrangeira conforme a tabela principal*/
		ON DELETE CASCADE										/*comando para deletar a chave estrangeira conforme a tabela principal*/

	);

	



	/*criacao da tabela alerta*/
	CREATE TABLE alerta(
		id_alerta INT PRIMARY KEY NOT NULL AUTO_INCREMENT,             /*chave primaria que será auto */
        nivel_alerta INT,
		descricao VARCHAR(100)
	);

	/*criacao da tabela relatorio_crescomento*/
	CREATE TABLE relatorio_crescimento(
		id_relatorio INT PRIMARY KEY NOT NULL AUTO_INCREMENT,         /*chave primaria que será auto 
																		incrementada e o campo não poderá ficar nulo*/
		id_planta INT,
		data_medicao DATE NOT NULL,
		altura FLOAT NOT NULL,
		descricao_saude VARCHAR(100),
		FOREIGN KEY (id_planta) REFERENCES planta(id_planta)
		ON UPDATE CASCADE
		ON DELETE CASCADE
		
	);
    
    /*criação de usuario e senha para adiministracao do banco*/
    CREATE USER 'controle_usuario'@'localhost' IDENTIFIED BY 'senha_controle_plantas';
    
    
    /*aplicando a permissões ao usuario*/
    /*o usuario nao podera criar novas tabelas, somente ler, inserir, atualizar e deletar registros*/
    GRANT SELECT, INSERT, UPDATE, DELETE ON controle_planta.* TO 'controle_usuario'@'localhost';
    
    /*aplicando e salvando configurações*/
    FLUSH PRIVILEGES;
    
    /*Inserindo valores na tabela SOLO*/
    INSERT INTO solo 
    (tipo_solo, fertilidade, umidade) VALUES 
	('Arenoso', 'Baixa', 11.5),
	('Argiloso', 'Média', 12.3),
	('Organico', 'Alta', 16.0);
    
    /*Inserindo valores na tabela PLANTA*/
    INSERT INTO planta 
    (id_solo, nome, tipo_planta, data_plantio) VALUES 
	(1, 'Morango', 'Fruta', '2024-02-03'),
	(2, 'Batata Doce', 'Raiz', '2024-03-05'),
	(3, 'Alface', 'Hortaliça', '2024-01-10');

	/*Inserindo valores na tabela IRRIGAÇÃO*/
	INSERT INTO irrigacao
    (id_planta, data_irrigacao, quantidade_agua) VALUES 
	(1, '2024-02-04', 1.5),
	(2, '2024-03-06', 1.0),
	(3, '2024-01-11', 1.5);
   
	/*Inserindo valores na tabela ALERTA*/
    INSERT INTO alerta 
    ( nivel_alerta, descricao) VALUES 
	(1,  'Umidade baixa no solo arenoso'),
	(2,  'Solo argiloso precisa de menor quantidade de água'),
	(3,   'Necessita de irrigação frequente');

	/*Inserindo valores na tabela RELATORIO DE CRESCIMENTO*/
	INSERT INTO relatorio_crescimento 
    (id_planta, data_medicao, altura, descricao_saude) VALUES 
	(1, '2024-02-05', 5.0, 'Saudável e em crescimento'),
	(2, '2024-03-07', 10.0, 'Crecimento otimo, mais rapido que o esperando'),
	(3, '2024-01-12', 8.0, 'Resistente e saudável');


	/* VIEW Para Descrição completa das Plantas e solo*/
    
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
    
	SELECT * FROM view_plantas_solo;
    
/*exibe todos os relatórios de crescimento organizados por planta.*/

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

-- Exibir os dados da view
SELECT * FROM view_relatorios_crescimento;

/********************************************************************************************************/

/*dispara dados na tabela alerta_solo*/

DROP TRIGGER IF EXISTS alerta_umidade_solo;

DELIMITER $$

CREATE TRIGGER alerta_umidade_solo
AFTER INSERT ON solo
FOR EACH ROW
BEGIN
    -- Verifica se a umidade do solo é menor que o esperado
    IF NEW.umidade < 10 THEN
        INSERT INTO alerta (nivel_alerta, descricao)
        VALUES (1, CONCAT('Alerta: A umidade do solo ', NEW.id_solo, ' está abaixo do esperado.'));
    
    -- Verifica se a umidade do solo é maior que o esperado
    ELSEIF NEW.umidade > 15 THEN
        INSERT INTO alerta (nivel_alerta, descricao)
        VALUES (3, CONCAT('Alerta: A umidade do solo ', NEW.id_solo, ' está acima do esperado.'));
    END IF;
END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER alerta_solo_update
AFTER UPDATE ON solo
FOR EACH ROW
BEGIN
    -- Verifica se a umidade do solo é menor que o esperado
    IF NEW.umidade < 10 THEN
        INSERT INTO alerta (nivel_alerta, descricao)
        VALUES (1, CONCAT('Alerta: A umidade do solo ', NEW.id_solo, ' está abaixo do esperado.'));
    
    -- Verifica se a umidade do solo é maior que o esperado
    ELSEIF NEW.umidade > 15 THEN
        INSERT INTO alerta (nivel_alerta, descricao)
        VALUES (3, CONCAT('Alerta: A umidade do solo ', NEW.id_solo, ' está acima do esperado.'));
    END IF;
END $$

DELIMITER ;


/*************************************************************************************************/
/*trigger para disparar alerta de irrigação*/


DELIMITER $$

CREATE TRIGGER alerta_irriga_agua
AFTER INSERT ON irrigacao
FOR EACH ROW
BEGIN
    -- Verifica se a quantidade de água é menor que o esperado
    IF NEW.quantidade_agua < 1.0 THEN
        INSERT INTO alerta (nivel_alerta, descricao)
        VALUES (1, CONCAT('Alerta: A quantidade de água para a planta ', NEW.id_planta, ' está abaixo do esperado.'));
    
    -- Verifica se a quantidade de água é maior que o esperado
    ELSEIF NEW.quantidade_agua > 2.0 THEN
        INSERT INTO alerta (nivel_alerta, descricao)
        VALUES (3, CONCAT('Alerta: A quantidade de água para a planta ', NEW.id_planta, ' está acima do esperado.'));
    END IF;
END $$

/*apos update*/
DELIMITER $$

CREATE TRIGGER alerta_irriga_update
AFTER UPDATE ON irrigacao
FOR EACH ROW
BEGIN
    -- Verifica se a quantidade de água é menor que o esperado
    IF NEW.quantidade_agua < 1.0 THEN
        INSERT INTO alerta (nivel_alerta, descricao)
        VALUES (1, CONCAT('Alerta: A quantidade de água para a planta ', NEW.id_planta, ' está abaixo do esperado.'));
    
    -- Verifica se a quantidade de água é maior que o esperado
    ELSEIF NEW.quantidade_agua > 2.0 THEN
        INSERT INTO alerta (nivel_alerta, descricao)
        VALUES (3, CONCAT('Alerta: A quantidade de água para a planta ', NEW.id_planta, ' está acima do esperado.'));
    END IF;
END $$

DELIMITER ;

/* testes
INSERT INTO irrigacao (id_planta, data_irrigacao, quantidade_agua)
VALUES (2, '2024-04-01', 0.5);  -- Quantidade de água abaixo de 1.0

INSERT INTO solo (tipo_solo, fertilidade, umidade)
VALUES ('Arenoso', 'Baixa', 8.5); -- Umidade abaixo de 10

UPDATE solo set umidade = 23 where id_solo = 1;

select * from alerta;
*/









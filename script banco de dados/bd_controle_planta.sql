
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
		id_alerta INT PRIMARY KEY NOT NULL AUTO_INCREMENT,             /*chave primaria que será auto 
																		incrementada e o campo não poderá ficar nulo*/
		id_solo INT NOT NULL,                                          /*campo criado para referenciar a tabela solo*/
        id_irrigacao INT NOT NULL,                                     /*campo criado para referenciar a tabela irrigacao*/
        nivel_alerta INT,
		descricao VARCHAR(100),
		FOREIGN KEY (id_solo) REFERENCES solo(id_solo)		           /*criacao da chave estrangeira*/
		ON UPDATE CASCADE                                              /*comando para atualizar a chave estrangeira conforme a tabela principal*/
		ON DELETE CASCADE,                                             /*comando para deletar a chave estrangeira conforme a tabela principal*/
		FOREIGN KEY (id_irrigacao) REFERENCES irrigacao(id_irrigacao)  /*criacao da chave estrangeira*/
		ON UPDATE CASCADE 
		ON DELETE CASCADE

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
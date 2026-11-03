CREATE TABLE account(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);
    CREATE TABLE movimentacao(

        id        BIGSERIAL PRIMARY KEY,
        data      VARCHAR(50)    NOT NULL,
        descricao VARCHAR(255)   NOT NULL,
        valor     DECIMAL(19, 2) NOT NULL,
        tipo      VARCHAR(20)    NOT NULL
    );

CREATE TABLE movimentacao_fixa(
                                  id BIGSERIAL PRIMARY KEY,
                                  descricao VARCHAR(255) NOT NULL,
                                  valor DECIMAL(19,2) NOT NULL,
                                  tipo VARCHAR(20) NOT NULL,
                                  dia_do_mes INTEGER NOT NULL,
                                  ativo BOOLEAN DEFAULT TRUE,
                                  account_id BIGINT,
                                  CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES account(id)
);
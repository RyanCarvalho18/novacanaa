CREATE TABLE account(
                        id BIGSERIAL PRIMARY KEY,
                        username VARCHAR(255) NOT NULL UNIQUE,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL,
                        role VARCHAR(50) NOT NULL
);

CREATE TABLE movimentacao(
                             id BIGSERIAL PRIMARY KEY,
                             data DATE NOT NULL,
                             descricao VARCHAR(255) NOT NULL,
                             valor DECIMAL(19,2) NOT NULL,
                             tipo VARCHAR(50) NOT NULL,
                             account_id BIGINT NOT NULL,
                             CONSTRAINT fk_movimentacao_account FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE movimentacao_fixa(
                                  id BIGSERIAL PRIMARY KEY,
                                  descricao VARCHAR(255) NOT NULL,
                                  valor DECIMAL(19,2) NOT NULL,
                                  tipo VARCHAR(50) NOT NULL,
                                  dia_do_mes INTEGER NOT NULL,
                                  ativo BOOLEAN DEFAULT TRUE,
                                  account_id BIGINT NOT NULL,
                                  CONSTRAINT fk_movimentacao_fixa_account FOREIGN KEY (account_id) REFERENCES account(id)
);
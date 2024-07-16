CREATE TABLE topicos (
    id BIGINT AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    mensaje VARCHAR(200) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    estado BOOLEAN NOT NULL,
    autor VARCHAR(100) NOT NULL,
    curso VARCHAR(100) NOT NULL,

    PRIMARY KEY(id)
);
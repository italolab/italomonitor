
create table usuario (
    id int primary key,
    nome varchar( 256 ) not null,
    email varchar( 100 ) not null,
    username varchar( 100 ) not null unique,
    senha varchar( 50 ) not null
);

create table usuario_grupo (
    id int primary key,
    nome varchar( 256 ) not null unique
);

create table role (
    id int primary key,
    nome varchar( 256 ) not null unique
);

create table usuario_grupo_map (
    id int primary key,
    usuario_id int,
    usuario_grupo_id int,
    constraint usuario_fk foreign key( usuario_id ) references usuario( id ),
    constraint usuario_grupo_fk foreign key( usuario_grupo_id ) references usuario_grupo( id )
);

create table role_grupo_map (
    id int primary key,
    role_id int,
    role_grupo_id int,
    constraint role_fk foreign key( role_id ) references role( id ),
    constraint usuario_grupo_fk foreign key( usuario_grupo_id ) references usuario_grupo( id )
);
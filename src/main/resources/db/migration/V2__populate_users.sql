insert into usuario( id, nome, email, username, senha ) values (
    1,
    'Italo Herbert',
    'italoherbert@outlook.com',
    'italo',
    '59f62a0320ea304cbec2498764c5c6742bfcabf1b591e26a3bea6bfcca3e358e'
);

insert into usuario_grupo ( id, nome ) values ( 1, 'Suporte' );

insert into role ( id, nome ) values ( 1, 'usuario-write' );
insert into role ( id, nome ) values ( 2, 'usuario-get' );
insert into role ( id, nome ) values ( 3, 'usuario-filter' );
insert into role ( id, nome ) values ( 4, 'usuario-delete' );

insert into usuario_grupo_map( id, usuario_id, usuario_grupo_id ) values ( 1, 1, 1 );

insert into role_grupo_map( id, role_id, usuario_grupo_id ) values ( 1, 1, 1 );
insert into role_grupo_map( id, role_id, usuario_grupo_id ) values ( 2, 2, 1 );
insert into role_grupo_map( id, role_id, usuario_grupo_id ) values ( 3, 3, 1 );
insert into role_grupo_map( id, role_id, usuario_grupo_id ) values ( 4, 4, 1 );

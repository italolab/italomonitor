insert into usuario( nome, email, username, senha ) values (
    'Italo Herbert',
    'italoherbert@outlook.com',
    'italo',
    '59f62a0320ea304cbec2498764c5c6742bfcabf1b591e26a3bea6bfcca3e358e'
);

insert into usuario_grupo ( nome ) values ( 'Suporte' );

insert into role ( nome ) values ( 'usuario-write' );
insert into role ( nome ) values ( 'usuario-read' );
insert into role ( nome ) values ( 'usuario-delete' );

insert into usuario_grupo_map( usuario_id, usuario_grupo_id ) values ( 1, 1 );

insert into role_grupo_map( role_id, role_grupo_id ) values ( 1, 1 );
insert into role_grupo_map( role_id, role_grupo_id ) values ( 2, 1 );
insert into role_grupo_map( role_id, role_grupo_id ) values ( 3, 1 );

delete from usuario_grupo_map;
delete from role_grupo_map;
delete from usuario;
delete from usuario_grupo;
delete from role;

insert into usuario( nome, email, username, senha ) values (
    'Italo Herbert',
    'italoherbert@outlook.com',
    'italo',
    '59f62a0320ea304cbec2498764c5c6742bfcabf1b591e26a3bea6bfcca3e358e'
);

insert into usuario_grupo ( nome ) values ( 'admin' );
insert into usuario_grupo ( nome ) values ( 'suporte' );

insert into role ( nome ) values ( 'usuario-write' );
insert into role ( nome ) values ( 'usuario-read' );
insert into role ( nome ) values ( 'usuario-get' );
insert into role ( nome ) values ( 'usuario-delete' );
insert into role ( nome ) values ( 'usuario-grupo-write' );
insert into role ( nome ) values ( 'usuario-grupo-read' );
insert into role ( nome ) values ( 'usuario-grupo-delete' );
insert into role ( nome ) values ( 'role-write' );
insert into role ( nome ) values ( 'role-read' );
insert into role ( nome ) values ( 'role-delete' );

insert into usuario_grupo_map( usuario_id, usuario_grupo_id ) values ( (select id from usuario where username='italo'), (select id from usuario_grupo where nome='admin') );
insert into usuario_grupo_map( usuario_id, usuario_grupo_id ) values ( (select id from usuario where username='italo'), (select id from usuario_grupo where nome='suporte') );

insert into role_grupo_map( role_id, usuario_grupo_id ) values ( (select id from role where nome='usuario-write'), (select id from usuario_grupo where nome='admin') );
insert into role_grupo_map( role_id, usuario_grupo_id ) values ( (select id from role where nome='usuario-read'), (select id from usuario_grupo where nome='admin') );
insert into role_grupo_map( role_id, usuario_grupo_id ) values ( (select id from role where nome='usuario-delete'), (select id from usuario_grupo where nome='admin') );
insert into role_grupo_map( role_id, usuario_grupo_id ) values ( (select id from role where nome='usuario-grupo-write'), (select id from usuario_grupo where nome='admin') );
insert into role_grupo_map( role_id, usuario_grupo_id ) values ( (select id from role where nome='usuario-grupo-read'), (select id from usuario_grupo where nome='admin') );
insert into role_grupo_map( role_id, usuario_grupo_id ) values ( (select id from role where nome='usuario-grupo-delete'), (select id from usuario_grupo where nome='admin') );
insert into role_grupo_map( role_id, usuario_grupo_id ) values ( (select id from role where nome='role-write'), (select id from usuario_grupo where nome='admin') );
insert into role_grupo_map( role_id, usuario_grupo_id ) values ( (select id from role where nome='role-read'), (select id from usuario_grupo where nome='admin') );
insert into role_grupo_map( role_id, usuario_grupo_id ) values ( (select id from role where nome='role-delete'), (select id from usuario_grupo where nome='admin') );

insert into role_grupo_map( role_id, usuario_grupo_id ) values ( (select id from role where nome='usuario-get'), (select id from usuario_grupo where nome='suporte') );

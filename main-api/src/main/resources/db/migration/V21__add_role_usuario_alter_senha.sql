
insert into role ( nome ) values ( 'usuario-alter-senha' );

insert into role_grupo_map ( role_id, usuario_grupo_id ) values 
	( (select id from role where nome='usuario-alter-senha' ), (select id from usuario_grupo where nome='admin') ),
	( (select id from role where nome='usuario-alter-senha' ), (select id from usuario_grupo where nome='suporte') );
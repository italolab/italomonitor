
insert into role (nome) values ( 'no-admin-get-config' );

insert into role_grupo_map ( role_id, usuario_grupo_id ) values 
	( (select id from role where nome='no-admin-get-config'), (select id from usuario_grupo where nome='suporte') );
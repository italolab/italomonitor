
insert into role (nome) values ( 'no-admin-update-empresa' );

insert into role_grupo_map ( role_id, usuario_grupo_id ) values 
	( (select id from role where nome='no-admin-update-empresa'), (select id from usuario_grupo where nome='suporte') );

insert into role ( nome ) values ( 'start-or-restart-monitoramentos' );

insert into role_grupo_map ( role_id, usuario_grupo_id ) values 
	( (select id from role where nome='start-or-restart-monitoramentos'), (select id from usuario_grupo where nome='admin') );
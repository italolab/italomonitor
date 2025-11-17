
insert into role (nome) values ( 'pagamento-all' );

insert into role_grupo_map ( role_id, usuario_grupo_id ) values 
	( (select id from role where nome='pagamento-all'), (select id from usuario_grupo where nome='admin') ),
	( (select id from role where nome='pagamento-all'), (select id from usuario_grupo where nome='suporte') );

update role set nome='pagamentos-all' where nome='pagamento-all';
insert into role ( nome ) values ( 'pagamentos-get' );

insert into role_grupo_map ( role_id, usuario_grupo_id ) values 
	( (select id from role where nome='pagamentos-get'), (select id from usuario_grupo where nome='suporte') );
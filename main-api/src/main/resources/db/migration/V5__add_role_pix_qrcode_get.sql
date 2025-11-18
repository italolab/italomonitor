
insert into role ( nome ) values ( 'pix-qrcode-get' );

delete from role_grupo_map where role_id=(select id from role where nome='pagamento-all') and usuario_grupo_id=(select id from usuario_grupo where nome='suporte');

insert into role_grupo_map ( role_id, usuario_grupo_id ) values 
	( (select id from role where nome='pix-qrcode-get'), (select id from usuario_grupo where nome='suporte') );
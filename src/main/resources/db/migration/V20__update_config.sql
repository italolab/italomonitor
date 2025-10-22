alter table config alter column monitoramento_delay set default 20;

insert into role ( nome ) values ( 'dispositivo-monitoramento' );

insert into role_grupo_map ( role_id, usuario_grupo_id ) values
    ( (select id from role where nome='dispositivo-monitoramento'), (select id from usuario_grupo where nome='admin') ),
    ( (select id from role where nome='dispositivo-monitoramento'), (select id from usuario_grupo where nome='suporte') );
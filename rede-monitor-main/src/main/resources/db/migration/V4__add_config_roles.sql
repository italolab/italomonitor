
insert into role ( nome ) values
    ( 'config-write' ),
    ( 'config-read' );

insert into role_grupo_map ( role_id, usuario_grupo_id ) values
    ( (select id from role where nome='config-write'), (select id from usuario_grupo where nome='admin') ),
    ( (select id from role where nome='config-read'), (select id from usuario_grupo where nome='admin') );
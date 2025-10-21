
insert into role ( nome ) values
    ( 'dispositivo-read' ),
    ( 'dispositivo-write' ),
    ( 'dispositivo-delete' );

insert into role_grupo_map ( role_id, usuario_grupo_id ) values
    ( (select id from role where nome='dispositivo-write'), (select id from usuario_grupo where nome='admin') ),
    ( (select id from role where nome='dispositivo-read'), (select id from usuario_grupo where nome='admin') ),
    ( (select id from role where nome='dispositivo-delete'), (select id from usuario_grupo where nome='admin') ),

    ( (select id from role where nome='dispositivo-write'), (select id from usuario_grupo where nome='suporte') ),
    ( (select id from role where nome='dispositivo-read'), (select id from usuario_grupo where nome='suporte') ),
    ( (select id from role where nome='dispositivo-delete'), (select id from usuario_grupo where nome='suporte') );
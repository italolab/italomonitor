
delete from role_grupo_map where role_id in (
    (select id from role where nome='dispositivo-write'),
    (select id from role where nome='dispositivo-read'),
    (select id from role where nome='dispositivo-delete')
);

insert into role_grupo_map ( role_id, usuario_grupo_id ) values
    ( (select id from role where nome='dispositivo-write'), (select id from usuario_grupo where nome='admin') ),
    ( (select id from role where nome='dispositivo-read'), (select id from usuario_grupo where nome='admin') ),
    ( (select id from role where nome='dispositivo-delete'), (select id from usuario_grupo where nome='admin') ),

    ( (select id from role where nome='dispositivo-write'), (select id from usuario_grupo where nome='suporte') ),
    ( (select id from role where nome='dispositivo-read'), (select id from usuario_grupo where nome='suporte') ),
    ( (select id from role where nome='dispositivo-delete'), (select id from usuario_grupo where nome='suporte') );
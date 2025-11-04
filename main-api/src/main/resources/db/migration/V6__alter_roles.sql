
delete from role_grupo_map;
delete from role;

insert into role ( nome ) values
    ( 'usuario-all' ),
    ( 'usuario-grupo-all' ),
    ( 'role-all' ),
    ( 'empresa-all' ),
    ( 'dispositivo-all' ),
    ( 'dispositivo-monitoramento-all'),
    ( 'config-all' ),

	( 'usuario-get' ),
    ( 'empresa-get' ),
    ( 'dispositivo-get' );

insert into role_grupo_map( role_id, usuario_grupo_id ) values
    ( (select id from role where nome='usuario-all'), (select id from usuario_grupo where nome='admin') ),
    ( (select id from role where nome='usuario-grupo-all'), (select id from usuario_grupo where nome='admin') ),
    ( (select id from role where nome='role-all'), (select id from usuario_grupo where nome='admin') ),
    ( (select id from role where nome='empresa-all'), (select id from usuario_grupo where nome='admin') ),
    ( (select id from role where nome='dispositivo-all'), (select id from usuario_grupo where nome='admin') ),
    ( (select id from role where nome='dispositivo-monitoramento-all'), (select id from usuario_grupo where nome='admin') ),
    ( (select id from role where nome='config-all'), (select id from usuario_grupo where nome='admin') ),

    ( (select id from role where nome='usuario-get'), (select id from usuario_grupo where nome='suporte') ),
    ( (select id from role where nome='empresa-get'), (select id from usuario_grupo where nome='suporte') ),
    ( (select id from role where nome='dispositivo-all'), (select id from usuario_grupo where nome='suporte') ),
    ( (select id from role where nome='dispositivo-monitoramento-all'), (select id from usuario_grupo where nome='suporte') );

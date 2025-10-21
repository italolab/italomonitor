
delete from role_grupo_map where role_id is null;

alter table role_grupo_map alter column role_id set not null;
alter table role_grupo_map alter column usuario_grupo_id set not null;
alter table usuario_grupo_map alter column usuario_id set not null;
alter table usuario_grupo_map alter column usuario_grupo_id set not null;


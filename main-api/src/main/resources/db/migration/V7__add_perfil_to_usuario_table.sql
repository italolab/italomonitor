
alter table usuario add column perfil varchar( 20 ) default '';

alter table usuario alter column perfil set not null;
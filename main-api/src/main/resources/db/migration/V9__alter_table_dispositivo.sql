
alter table dispositivo alter column empresa_id drop not null;

alter table dispositivo add column monitorado_por_agente boolean default false;
alter table dispositivo add column agente_id bigint;

alter table dispositivo add constraint agente_fk foreign key( agente_id ) references agente( id );
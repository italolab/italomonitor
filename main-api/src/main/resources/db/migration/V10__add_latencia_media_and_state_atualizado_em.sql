
alter table dispositivo add column latencia_media int default 0;
alter table dispositivo add column state_atualizado_em timestamp default current_timestamp;
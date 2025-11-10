
alter table dispositivo drop column ultima_notif_em;

alter table empresa add column ultima_notif_em timestamp default current_timestamp;

alter table empresa rename column min_tempo_para_proximo_evento to min_tempo_para_prox_notif;

alter table dispositivo add column ultima_notif_em timestamp default current_timestamp;
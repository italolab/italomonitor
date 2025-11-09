
alter table config add column telegram_bot_token varchar( 100 ) default '';

alter table empresa add column telegram_chat_id varchar( 50 ) default '';
alter table empresa add column diaPagto int default 1;
alter table empresa add column temporario boolean default false;
alter table empresa add column uso_temporario_por int default 7;
alter table empresa add min_tempo_para_proximo_evento int default 3600;
alter table empresa add column criado_em timestamp default current_timestamp;
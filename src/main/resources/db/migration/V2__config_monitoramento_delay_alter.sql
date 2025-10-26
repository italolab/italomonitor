alter table config add column registro_monitoramento_periodo int default 3600000;

alter table config alter column monitoramento_delay set default 1;
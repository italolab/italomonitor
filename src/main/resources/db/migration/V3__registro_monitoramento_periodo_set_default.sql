alter table config rename column registro_monitoramento_periodo to registro_evento_periodo;

alter table config alter column registro_evento_periodo set default 3600;

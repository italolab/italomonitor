
alter table evento add column duracao int;

update evento set duracao=tempo_inatividade;

alter table evento alter column duracao set not null;
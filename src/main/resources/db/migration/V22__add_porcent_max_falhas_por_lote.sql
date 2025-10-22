alter table config drop column max_falhas_por_vez;

alter table empresa add column porcentagem_max_falhas_por_lote double precision default 0.3333;
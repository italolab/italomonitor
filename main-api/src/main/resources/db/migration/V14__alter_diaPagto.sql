
alter table empresa drop column diaPagto;

alter table empresa add column dia_pagto int default 1;

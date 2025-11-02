
alter table dispositivo drop constraint dispositivo_status_check;

update dispositivo set status='INATIVO';

alter table dispositivo alter column status set not null;
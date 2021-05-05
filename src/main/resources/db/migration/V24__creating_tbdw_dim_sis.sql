create table EXTRATO_APP.TBDW_DIM_SIS
(
  cd_seq_sis_orig NUMBER(7) not null,
  cd_sis_orig     VARCHAR2(4),
  nm_sistema      VARCHAR2(35)
);

alter table EXTRATO_APP.TBDW_DIM_SIS
  add constraint TBDW_DIM_SIS_ORIG_PK primary key (CD_SEQ_SIS_ORIG);
 
alter table EXTRATO_APP.TBDW_DIM_SIS
  add constraint TBDW_DIM_SIS_ORIG_UK unique (CD_SIS_ORIG);
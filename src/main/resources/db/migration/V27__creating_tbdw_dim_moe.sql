create table FDWB.TBDW_DIM_MOE
(
  cd_moeda          NUMBER(5) not null,
  cd_moeda_iso      VARCHAR2(3),
  nm_moeda          VARCHAR2(15),
  tp_paridade_dolar VARCHAR2(1),
  dt_exclusao_ptax  DATE,
  constraint "TBDW_DIM_MOE_PK" primary key ("CD_MOEDA"));
create table EXTRATO_APP.TBDW_DIM_MODALIDADE
(
  cd_seq_modalidade NUMBER(7) not null,
  cd_modalidade     NUMBER(6),
  ds_modalidade     VARCHAR2(50),
  cd_produto        NUMBER(4),
  ds_produto        VARCHAR2(35),
  cd_grprod         NUMBER(3),
  ds_grprod         VARCHAR2(25),
  cd_famprod        NUMBER(2),
  ds_famprod        VARCHAR2(25)
);

create table EXTRATO_APP.TBDW_DIM_EMP
(
  cd_empresa         NUMBER(6) not null,
  nm_empresa         VARCHAR2(64) not null,
  cd_grupo_empresa   VARCHAR2(3),
  nm_grupo_empresa   VARCHAR2(35),
  cd_holding_empresa VARCHAR2(3),
  nm_holding_empresa VARCHAR2(35),
  cd_moeda_empr      NUMBER(5),
  cd_tpemp           VARCHAR2(2) not null,
  cd_fluxo_caixa     NUMBER(2),
  nm_fluxo_caixa     VARCHAR2(30),
  db_cons            NUMBER(6),
  dmp_code           NUMBER(6),
  constraint "TBDW_DIM_EMP_PK" primary key ("CD_EMPRESA"));

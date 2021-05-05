create table EXTRATO_APP.TBDW_DIM_INX
(
  cd_indexador         NUMBER(3) not null,
  nm_indexador         VARCHAR2(35) not null,
  fl_tipo_inx          VARCHAR2(3) not null,
  cd_moeda_de          NUMBER(5),
  cd_moeda_para        NUMBER(5),
  fl_tipo_paridade     VARCHAR2(1),
  cd_tppe              VARCHAR2(4),
  fl_tipo_paridade_mtm VARCHAR2(1),
  cd_inx_ceri          NUMBER(2),
  constraint "TBDW_DIM_INX_PK" primary key ("CD_INDEXADOR"));
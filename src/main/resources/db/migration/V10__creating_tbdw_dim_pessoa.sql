create table EXTRATO_APP.TBDW_DIM_PESSOA
(
  cd_pessoa                     NUMBER(8) not null,
  nm_pessoa                     VARCHAR2(60) not null,
  nm_pessoa_abrev               VARCHAR2(25) not null,
  cd_gec_br                     NUMBER(5),
  nm_gec_br                     VARCHAR2(40),
  cd_ate_br                     NUMBER(6),
  nm_ate_br                     VARCHAR2(60),
  cd_pessoa_ccdb                NUMBER(10),
  cd_ate_al                     NUMBER(7),
  nm_ate_al                     VARCHAR2(60),
  cd_pais                       NUMBER(5),
  fl_emp_fundo_db               VARCHAR2(1) not null,
  cd_pessoa_buba                NUMBER(10),
  fl_tipo_pessoa                VARCHAR2(1),
  cd_cgc_cpf                    NUMBER(15),
  cd_cosif                      NUMBER(7),
  cd_nace                       NUMBER(8),
  dt_inclusao                   DATE,
  vl_ptrm_lqd                   NUMBER(20,4),
  vl_prev_mvt                   NUMBER(16,4),
  fl_sensibilidade              VARCHAR2(35),
  cd_tp_empresa                 NUMBER(2),
  cd_catg_pessoa                NUMBER(2),
  cd_pais_origem                NUMBER(5),
  cod_sit_pessoa                VARCHAR2(1),
  ind_pep                       VARCHAR2(1),
  vl_tot_ativos                 NUMBER(20,4),
  vl_faturamento                NUMBER(20,4),
  vl_rec_operacional            NUMBER(20,4),
  vl_outr_cap_financ            NUMBER(20,4),
  fl_cap_financ                 VARCHAR2(35),
  cd_paragon                    VARCHAR2(35),
  fl_benef_final                VARCHAR2(3),
  cd_contrato_global            NUMBER(3),
  cd_paragon_grupo              VARCHAR2(35),
  fl_rating_paragon             VARCHAR2(5),
  fl_rating_grupo_paragon       VARCHAR2(5),
  fl_rating_bacen               VARCHAR2(5),
  fl_rating_grupo_bacen         VARCHAR2(5),
  dt_rating_grupo_paragon       DATE,
  dt_rating_paragon             DATE,
  fl_rating_arrasto_bacen       VARCHAR2(5),
  fl_rating_arrasto_paragon     VARCHAR2(5),
  ds_logradouro                 VARCHAR2(60),
  ds_cidade                     VARCHAR2(40),
  cd_uf                         VARCHAR2(2),
  cd_cep                        NUMBER,
  dt_update                     DATE,
  fl_cgd                        CHAR(1),
  nr_logradouro                 VARCHAR2(5),
  ds_compl_logr                 VARCHAR2(30),
  ds_bairro                     VARCHAR2(30),
  telefone_pessoa               VARCHAR2(20),
  data_atualizacao_renda        DATE,
  data_fim_relacionamento_conta DATE,
  cd_assessor                   NUMBER(10),
  nm_assessor                   VARCHAR2(100),
  email_assessor                VARCHAR2(100),
  CONSTRAINT "TBDW_DIM_PESSOA_PK" PRIMARY KEY ("CD_PESSOA"));
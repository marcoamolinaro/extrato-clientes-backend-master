CREATE TABLE EXTRATO_APP.EXTRACT_LOG_REPORT 
(	LOG_ID NUMBER(38,0), 
	COD_CLI NUMBER(8,0) NOT NULL, 
	NM_CLI VARCHAR2(390 BYTE), 
	AG_TIPO VARCHAR2(50 BYTE), 
	EXECUCAO VARCHAR2(100 BYTE), 
	STATUS VARCHAR2(60 BYTE), 
	DESCRICAO VARCHAR2(400 BYTE), 
	FILENAME VARCHAR2(100 BYTE), 
	DT_LOG DATE NOT NULL, 
CONSTRAINT "LOG_ID" PRIMARY KEY ("LOG_ID"));

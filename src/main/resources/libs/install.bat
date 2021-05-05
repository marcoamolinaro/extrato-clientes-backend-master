@echo off
set APP_DIR=%~dp0

call mvn install:install-file -DgroupId=net.sf.jasperreports -DartifactId=jasperreports -Dversion=6.16.0 -Dpackaging=jar -Dfile="%APP_DIR%jasperreports-6.16.0.jar" --global-settings H:\config\.m2\settings.xml 
call mvn install:install-file -DgroupId=net.sf.jasperreports -DartifactId=jasperreports-functions -Dversion=6.16.0 -Dpackaging=jar -Dfile="%APP_DIR%jasperreports-functions-6.16.0.jar" --global-settings H:\config\.m2\settings.xml 
call mvn install:install-file -DgroupId=net.sf.jasperreports -DartifactId=jasperreports-fonts -Dversion=6.16.0 -Dpackaging=jar -Dfile="%APP_DIR%jasperreports-fonts-6.16.0.jar" --global-settings H:\config\.m2\settings.xml 
call mvn install:install-file -DgroupId=com.mpobjects.jasperreports.font -DartifactId=jasperreports-fonts-liberation -Dversion=2.1.2 -Dpackaging=jar -Dfile="%APP_DIR%jasperreports-fonts-liberation-2.1.2.jar" --global-settings H:\config\.m2\settings.xml 
call mvn install:install-file -DgroupId=org.apache.commons -DartifactId=commons-collections4 -Dversion=4.4 -Dpackaging=jar -Dfile="%APP_DIR%commons-collections4-4.4.jar" --global-settings H:\config\.m2\settings.xml 
call mvn install:install-file -DgroupId=commons-digester -DartifactId=commons-digester -Dversion=2.1 -Dpackaging=jar -Dfile="%APP_DIR%commons-digester-2.1.jar" --global-settings H:\config\.m2\settings.xml 
call mvn install:install-file -DgroupId=commons-beanutils -DartifactId=commons-beanutils -Dversion=1.8.2 -Dpackaging=jar -Dfile="%APP_DIR%commons-beanutils-1.8.2.jar" --global-settings H:\config\.m2\settings.xml 
call mvn install:install-file -DgroupId=com.db.secserver -DartifactId=secserver-public-interfaces-client -Dversion=2.1.0-RELEASE -Dpackaging=jar -Dfile="%APP_DIR%secserver-public-interfaces-client-2.1.0.jar" --global-settings H:\config\.m2\settings.xml 

pause
set currPath=%~dp0
set parentPath=
:begin
FOR /F "tokens=1,* delims=\" %%i IN ("%currPath%")  DO (set front=%%i)
FOR /F "tokens=1,* delims=\" %%i IN ("%currPath%")  DO (set currPath=%%j)

if not "%parentPath%" == "" goto gotParentPath
:gotParentPath
if "%parentPath%%front%\"=="%~dp0" goto end
set parentPath=%parentPath%%front%\
goto begin
:end
set CUR_INSTALL_DIR=%parentPath%

set JAVA_OPT=-Xmx512M -Xms512M -DINSTALL_DIR=%CUR_INSTALL_DIR%
set JPDA=
if not ""%1"" == ""jpda"" goto noJpda
set JPDA=jpda
if not "%JPDA_ADDRESS%" == "" goto gotJpdaAddress
set JPDA_ADDRESS=65535
:gotJpdaAddress
if not "%JPDA_SUSPEND%" == "" goto gotJpdaSuspend
set JPDA_SUSPEND=n
:gotJpdaSuspend
if not "%JPDA_OPTS%" == "" goto gotJpdaOpts
set JPDA_OPTS=-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=%JPDA_SUSPEND%,address=%JPDA_ADDRESS%
:gotJpdaOpts
shift
:noJpda
%JAVA_HOME%\bin\java %JPDA_OPTS% %JAVA_OPT% -jar %CUR_INSTALL_DIR%\alpaca-bootstrap-1.0.0-SNAPSHOT.jar START
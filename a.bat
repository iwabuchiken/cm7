@ECHO OFF


REM ************************************
REM *
REM *	Set vars
REM *
REM *	WORKS_HOME	COMMANDS_HOME	SAKURA_HOME
REM *	JAVA_HOME	GIT_CMD			ADB_HOME
REM *	MINGW_BIN_HOME	QMAKE_HOME	
REM *	
REM *	
REM ************************************
:set_path
ECHO Setting a var: WORKS_HOME=C:\WORKS
SET WORKS_HOME=C:\WORKS
PATH=%PATH%;%WORKS_HOME%;


ECHO Setting a var: COMMANDS_HOME=C:\WORKS\commands
SET COMMANDS_HOME=C:\WORKS\commands
PATH=%PATH%;%COMMANDS_HOME%;


ECHO Setting a var: SAKURA_HOME=C:\WORKS\Programs\sakura_2.1.1
SET SAKURA_HOME=C:\WORKS\Programs\sakura_2.1.1
PATH=%PATH%;%SAKURA_HOME%;

ECHO Setting a var: JAVA_HOME=C:\WORKS\PROGRAMS\Java_7u7_i586
SET JAVA_HOME=C:\WORKS\PROGRAMS\Java_7u7_i586
PATH=%PATH%;%JAVA_HOME%;

ECHO Setting a var: GIT_CMD=C:\WORKS\Programs\Git\cmd
SET GIT_CMD=C:\WORKS\Programs\Git\cmd
PATH=%PATH%;%GIT_CMD%;

ECHO Setting a var: ADB_HOME=C:\WORKS\Programs\adt-bundle-windows-x86-20140321\adt-bundle-windows-x86-20140321\sdk\platform-tools
SET ADB_HOME=C:\WORKS\Programs\adt-bundle-windows-x86-20140321\adt-bundle-windows-x86-20140321\sdk\platform-tools
PATH=%PATH%;%ADB_HOME%;

ECHO Setting a var: MINGW_BIN_HOME=C:\MinGW\bin
SET MINGW_BIN_HOME=C:\MinGW\bin
PATH=%PATH%;%MINGW_BIN_HOME%;

ECHO Setting a var: QMAKE_HOME=C:\WORKS\Programs\Qt_5.3.0\5.2.1\mingw48_32\bin
SET QMAKE_HOME=C:\WORKS\Programs\Qt_5.3.0\5.2.1\mingw48_32\bin
PATH=%PATH%;%QMAKE_HOME%;


ECHO Setting aliases for git
ECHO 	=^> checkout -^> co, push -^> p, add -^> a, log -^> l,^
			status -^> s, commit -^> c
git config --global alias.co checkout
git config --global alias.p push
git config --global alias.a add
git config --global alias.l log
git config --global alias.s status
git config --global alias.c commit
git config --global alias.b branch

git config --global core.editor sakura.exe

goto :end

REM *********************
REM *
REM *	End
REM *
REM *********************
:end
rem exit 0

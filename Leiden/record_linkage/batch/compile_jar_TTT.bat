@echo off
echo.
echo #################################################
echo #######  TTT COMPILE SCRIPT FOR BASH  #######
echo #################################################
::   ####### ONLY CHANGE PROJECT VARIABLE BELOW ######
::   #################################################
:: NOT SURE IF IT WORKS FOR WINDOWS-TRY NOT TO COMPILE IN WINDOWS

set PROJECT="C:\Users\laptop\Documents\TracesThroughTime\TNA_final\record_linkage"

::   #################################################
::   #######    DO NOT CHANGE ANYTHING BELOW    ######
::   #################################################
set SRC=%PROJECT%\src
set BIN=..\bin
set LIBS=%PROJECT%\lib

::	to compile chartex source code Javac 7 or higher is required
set JAVAC="C:\Program Files\Java\jdk1.8.0_25\bin\javac"
set OPTIONS=-Xlint -g
set CLASSPATH=.;..\bin;..\lib\*
:: set CLASSPATH=%CLASSPATH%;%BIN%;%LIBS%*
set COMPILE=%JAVAC% %OPTIONS% -cp %CLASSPATH% 
set OUTPUT=ttt

::	script need not be run from within %PROJECT%, return to %WD% afterwards
:: set WD=echo %cd%
:: cd %SRC%

REM echo Start compiling using compile command (thanks to claudio):
REM echo %COMPILE%
REM echo.

echo Using compile command:
echo %COMPILE%
echo.
echo Start compiling...
echo.

cd %SRC%
%COMPILE% -d %BIN% nl\liacs\link\*.java 
%COMPILE% -d %BIN% nl\liacs\link\distance\*.java 
%COMPILE% -d %BIN% nl\liacs\link\exception\*.java 
%COMPILE% -d %BIN% nl\liacs\link\field\*.java 
%COMPILE% -d %BIN% nl\liacs\link\util\*.java 
%COMPILE% -d %BIN% nl\liacs\link\recordlinker\*.java
echo.
:: cd ..\batch cortana

::	compile errors occured if javac exit code is anything other than 0
REM if [[ %EXIT% -ne 0 ]]
REM then
	REM echo javac returned ERROR CODE %EXIT%, no %OUTPUT%.jar created, exiting...
	REM echo
	REM echo %WD%
	REM exit %EXIT%
REM fi

echo Compiling done...
echo.

echo Creating %OUTPUT%.jar...
echo.

cd %BIN%
jar cvfm %OUTPUT%.jar MANIFEST.MF nl resources 
move %OUTPUT%.jar ..\%OUTPUT%.jar 
echo.
echo Creating %OUTPUT%.jar done...

cd ..
echo Finished!

echo %WD%


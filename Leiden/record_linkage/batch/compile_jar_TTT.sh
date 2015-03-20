!/bin/bash

echo "#################################################"
echo "#######  TTT COMPILE SCRIPT FOR BASH  #######"
echo "#################################################"
#     ####### ONLY CHANGE PROJECT VARIABLE BELOW ######
#     #################################################

PROJECT="/Users/Clean8ie/Desktop/sci.knobb101.ChartEx/TracesThroughTime/TNA_final/record_linkage"

#     #################################################
#     #######    DO NOT CHANGE ANYTHING BELOW    ######
#     #################################################
SRC="$PROJECT/src/"
BIN="$PROJECT/bin/"
LIBS="$PROJECT/lib/"

# to compile chartex source code Javac 7 or higher is required
VERSION="7"
JAVAC="/usr/bin/javac"
OPTIONS="-Xlint -g"
CLASSPATH=$CLASSPATH:$BIN:$LIBS*
COMPILE="$JAVAC $OPTIONS -cp $CLASSPATH -d $BIN"
OUTPUT="ttt"

# script need not be run from within $PROJECT, return to $WD afterwards
WD=`pwd`
cd $SRC

echo Start compiling using compile command:
echo $COMPILE
echo

cd $SRC
$COMPILE nl/liacs/link/util/*.java nl/liacs/link/*.java nl/liacs/link/distance/*.java nl/liacs/link/exception/*.java nl/liacs/link/field/*.java  nl/liacs/link/recordlinker/*.java
EXIT=$?
echo

# compile errors occured if javac exit code is anything other than 0
if [[ $EXIT -ne 0 ]]
then
	echo javac returned ERROR CODE $EXIT, no $OUTPUT.jar created, exiting...
	echo
	cd $WD
	exit $EXIT
fi

echo Compiling done...
echo

echo Creating $OUTPUT.jar...
echo

cd $BIN
jar cvfm $OUTPUT.jar MANIFEST.MF nl 
mv $OUTPUT.jar ../$OUTPUT.jar 
echo
echo Creating $OUTPUT.jar done...

cd ..
echo Finished!

cd $WD


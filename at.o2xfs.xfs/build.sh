#!bin/sh

cygwin=false

if [ "$(uname -o)" = "Cygwin" ]
then
    cygwin=true
fi

function fixpath () {
	echo $1 | sed -e "s/\([A-z]\):/cygdrive\/\1/"
}

JAVA_HOME=$1
XFS_SDK=$2
O2XFS_WIN32=$(dirname $(dirname $0))/at.o2xfs.win32/native/O2XfsWIN32
BUILD_DIR=$3

echo "Java home: $JAVA_HOME"
echo "XFS_SDK=$XFS_SDK"
echo "O2XFS_WIN32=$O2XFS_WIN32"
echo $(dirname $0)

make -f "$(dirname $0)/make.mk" JAVA_HOME="$JAVA_HOME" XFS_SDK="$XFS_SDK" O2XFS_WIN32="$O2XFS_WIN32" SRC_DIR="$(dirname $0)/native" BUILD_DIR="$BUILD_DIR" BIN_DIR="$4"
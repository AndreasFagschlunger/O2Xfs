#!bin/sh

cygwin=false

if [ "$(uname -o)" = "Cygwin" ]
then
    cygwin=true
fi

make -f "$(dirname $0)/make.mk" JAVA_HOME="$1" O2XFS_WIN32="$(dirname $(dirname $0))/at.o2xfs.win32/native/O2XfsWIN32" SRC_DIR="$(dirname $0)/native/at.o2xfs.ctapi" BIN_DIR="$2"
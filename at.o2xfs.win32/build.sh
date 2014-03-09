#!bin/sh

cygwin=false

if [ "$(uname -o)" = "Cygwin" ]
then
    cygwin=true
fi

function fixpath () {
	echo $1 | sed -e "s/\([A-z]\):/cygdrive\/\1/"
}

make -f "$(dirname $0)/make.mk" JAVA_HOME="$1" SRC_DIR="$(dirname $0)/native/O2XfsWIN32" BIN_DIR="$3"
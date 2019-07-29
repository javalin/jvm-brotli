#!/bin/sh

CURPATH=$(pwd)
TARGET_CLASSES_PATH="target/classes/lib/linux-x86-amd64"
TARGET_PATH="target"

exitWithError() {
  cd ${CURPATH}
  echo "*** An error occured. Please check log messages. ***"
  exit $1
}

mkdir -p "$TARGET_CLASSES_PATH"

cd "$TARGET_PATH"
cmake ../../../ || exitWithError $?
make || exitWithError $?
rm -f "$CURPATH/${TARGET_CLASSES_PATH}/libbrotli.so"
cp "./libbrotli.so" "$CURPATH/${TARGET_CLASSES_PATH}" || exitWithError $?

cd ${CURPATH}
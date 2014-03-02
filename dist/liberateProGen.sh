#!/bin/bash

if [ $# != 1 ]; then
    echo "Error: $0 svn-user"
    exit
else
    USER=$1
fi

EXCLUDE_DIRS="test app/regressionMultiple app/Gphash app/ProGen_*"

RELEASE=`date +%d-%b-%Y`
PROGEN_NAME="ProGen"

echo -n "Conectando con svn… [1/4]"
svn --username $USER export -q svn+ssh://evannai.uc3m.es/var/svn/ProGen/branches/ProGen\ v2.0/src ${PROGEN_NAME}
echo " [OK]"

echo -n "Eliminando ficheros innecesarios… [2/4]"
for i in ${EXCLUDE_DIRS}; do
    rm -rf  ${PROGEN_NAME}/$i
done
echo " [OK]"

echo -n "Contruyendo paquete para distribución [3/4]"
find ${PROGEN_NAME} -name "*" -exec zip -q9 ${PROGEN_NAME}_${RELEASE}.zip \{} \;
echo " [OK]"

echo -n "Eliminando ficheros temporales… [4/4]"
rm -rf ${PROGEN_NAME}
echo " [OK]"
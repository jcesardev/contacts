#!/usr/bin/env bash
echo ""
echo ""
echo "------------------------------------------------"
echo "-            ALBO DIRECTORY                    -"
echo "------------------------------------------------"
echo ""
echo ""
echo " - CLEAN PROJECT"
mvn clean
echo ""
echo ""
echo " - RESOLVE DEPENDENCIES"
mvn dependency resolve
echo ""
echo ""
echo " - RUN UNIT TESTS AND PACKAGE"
mvn package
echo ""
echo ""
echo "-----------------------------------------------"
echo "-            SUCCESS                          -"
echo "-----------------------------------------------"




#!/bin/bash

if [ "$1" != "cv" ] && [ "$1" != "kl" ]
then 
  echo "Error! Wrong params! Allowed params are: cv | kl"
  exit
fi

DST=$1
FE_PATH=./front/src
FE_FILE=config.ts
BE_PATH=./src/main/resources
BE_FILE=application.properties

cp -f $FE_PATH/$DST.$FE_FILE $FE_PATH/$FE_FILE
cp -f $BE_PATH/$DST.$BE_FILE $BE_PATH/$BE_FILE

echo "Project switched to $DST"
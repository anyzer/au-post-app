#!/usr/bin/env bash

path=`pwd`

if [[ ${path} =~ "workspace_bi" ]]; then
    echo "DEV workspace_bi"
    source ./script/PROJECT_CONFIG
    if [ ! -d "${TO}" ]; then
      mkdir ${TO}
    fi
    ./target/universal/stage/bin/bran_job ${ENV} ${FROM} ${TO}
else
    echo "PRODUCTION"
    source ./PROJECT_CONFIG
    if [ ! -d "${TO}" ]; then
      mkdir ${TO}
    fi
    ./stage/bin/bran_job ${ENV} ${FROM} ${TO}
fi


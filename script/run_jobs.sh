#!/usr/bin/env bash

path=`pwd`

if [[ ${path} =~ "workspace_bi" ]]; then
    echo "DEV workspace_bi"
    source ./script/PROJECT_CONFIG
    echo "post environment: <${ENV}>"
    ./target/universal/stage/bin/bran_job ${ENV} ${FROM} ${TO}
else
    echo "PRODUCTION"
    source ./PROJECT_CONFIG
    echo "post environment: <${ENV}>"
    ./stage/bin/bran_job ${ENV} ${FROM} ${TO}
fi


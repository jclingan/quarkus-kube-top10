#!/bin/bash

ARG=$1

if [ "$ARG" == "" ];
then
    echo "USAGE: $0 create | delete"
    exit
else
    COMMAND=${ARG}
fi

VERSION=v1.19.0

if [ "$ARG" == "create" ];
then
    kubectl create namespace observability
fi

kubectl ${COMMAND} -n observability -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/${VERSION}/deploy/crds/jaegertracing.io_jaegers_crd.yaml

kubectl ${COMMAND} -n observability -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/${VERSION}/deploy/service_account.yaml

kubectl ${COMMAND} -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/${VERSION}/deploy/role.yaml

kubectl ${COMMAND} -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/${VERSION}/deploy/cluster_role_binding.yaml

# Sets WATCH_NAMESPACE to all namespaces by providing an empty value
kubectl ${COMMAND} -n observability -f ./operator.yaml

#kubectl ${COMMAND} -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/${VERSION}/deploy/cluster_role.yaml

#kubectl ${COMMAND} -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/${VERSION}/deploy/cluster_role_binding.yaml

kubectl ${COMMAND} -n observability -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/${VERSION}/deploy/examples/simplest.yaml

if [ "$ARG" == "delete" ];
then
    kubectl delete namespace observability
fi

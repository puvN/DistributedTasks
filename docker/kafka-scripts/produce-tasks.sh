#!/bin/bash

BROKER="localhost:9092"
TOPIC="tasks-topic"

cat /data/tasks.json | sed 's/},{/}\n{/g' | while read -r record; do
    echo "Producing: $record"
    echo "$record" | kafka-console-producer --broker-list $BROKER --topic $TOPIC
done
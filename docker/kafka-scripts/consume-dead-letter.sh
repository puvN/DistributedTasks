#!/bin/bash

BROKER="localhost:9092"
DLQ_TOPIC="dead_letter_topic"

# Consume messages from the DLQ
kafka-console-consumer --bootstrap-server "$BROKER" \
    --topic "$DLQ_TOPIC" \
    --from-beginning
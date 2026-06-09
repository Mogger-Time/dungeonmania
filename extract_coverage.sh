#!/bin/bash

set -euo pipefail

report_file="build/reports/jacoco/test/jacocoTestReport.xml"

if [ ! -f "$report_file" ]; then
  echo "0%"
  exit 0
fi

line_node=$(awk -F'"' '/counter type="LINE"/ { print; exit }' "$report_file")
missed=$(echo "$line_node" | awk -F'"' '{print $4}')
covered=$(echo "$line_node" | awk -F'"' '{print $6}')
total=$((missed + covered))

if [ "$total" -eq 0 ]; then
  echo "0%"
  exit 0
fi

percent=$((covered * 100 / total))
echo "${percent}%"

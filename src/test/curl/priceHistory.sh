#!/bin/sh

HOST=127.0.0.1
PORT=443
URL="https://${HOST}:${PORT}/api/v1/tda/priceHistory?symbol=spy"

/usr/bin/curl -Lv -s --insecure \
 --cert-type P12 \
 --cert ./../../../x509/tda_client_1.p12:password \
 -H "Accept: application/json" \
  -XGET "${URL}" | jq '.'








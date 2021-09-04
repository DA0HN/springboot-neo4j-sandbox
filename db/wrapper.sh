#!/bin/bash

# https://github.com/marcellodesales/neo4j-with-cypher-seed-docker

log_info() {
  printf '%s %s\n' "$(date -u +"%Y-%m-%d %H:%M:%S:%3N%z") INFO  Wrapper: $1"
  return
}

set -m

/docker-entrypoint.sh neo4j &

log_info "Waiting until neo4j stats at :7474 ..."
wget --quiet --tries=10 --waitretry=10 -O /dev/null http://localhost:7474

if [ -d "/cyphers" ]; then
  log_info "Deleting all relations"
  cypher-shell --format plain "MATCH (n) DETACH DELETE n"

  log_info "Wrapper: Loading cyphers from '/cyphers'"
  for cipherFile in /cyphers/*.cql; do
    log_info "Running cypher ${cipherFile}"
    contents=$(cat "${cipherFile}")
    cypher-shell -a bolt://localhost:7687 "${contents}"
  done
  log_info "Finished loading all cyphers from '/cyphers'"
fi

TOTAL_CHANGES=$(cypher-shell --format plain "MATCH (n) RETURN count(n) AS count")
log_info "Wrapper: Changes $(echo ${TOTAL_CHANGES} | sed -e 's/[\r\n]//g')"

fg %1

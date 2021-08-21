#!/bin/bash

cd db || exit

docker-compose up --build

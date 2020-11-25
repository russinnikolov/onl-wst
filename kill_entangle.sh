#!/bin/bash
PID=$(ps -ef | grep entangle | awk '/entangle/{print $2}')
kill $PID;
rm -rf /tmp/.X99-lock

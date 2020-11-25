#!/bin/bash
rm -rf "/home/mercari/Pictures/Capture/*"
rm -rf /tmp/.X99-lock
#screen -X -S "Entangle" stuff "^C"
Xvfb :99 &
entangle --display=:99 

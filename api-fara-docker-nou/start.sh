#!/bin/bash

# Script pentru incantatie
#incantatie_script="incantatie.sh"
#if [ -f "$incantatie_script" ]; then
#    gnome-terminal --tab --title="Incantatie" -- bash -c "./$incantatie_script; read -p 'Press Enter to close this terminal.'"
#else
#    echo "Scriptul pentru Incantatie nu exista."
#fi

# Script pentru query
query_script="query.sh"
if [ -f "$query_script" ]; then
    gnome-terminal --tab --title="Query" -- bash -c "./$query_script; read -p 'Press Enter to close this terminal.'"
else
    echo "Scriptul pentru query nu exista."
fi

# Script pentru gateway
gateway_script="gateway.sh"
if [ -f "$gateway_script" ]; then
    gnome-terminal --tab --title="Gateway" -- bash -c "./$gateway_script; read -p 'Press Enter to close this terminal.'"
else
    echo "Scriptul pentru Gateway nu exista."
fi




#!/bin/bash

# Script pentru authorization-microservice
authorization_script="authorization-microservice.sh"
if [ -f "$authorization_script" ]; then
    gnome-terminal --tab --title="authorization" -- bash -c "./$authorization_script; read -p 'Press Enter to close this terminal.'"
else
    echo "Scriptul pentru authorization nu exista."
fi

# Script pentru comments-microservice
comments="comments-microservice.sh"
if [ -f "$comments" ]; then
    gnome-terminal --tab --title="comments" -- bash -c "./$comments; sleep 15; read -p 'Press Enter to close this terminal.'"
else
    echo "Scriptul pentru comments nu exista."
fi

# Script pentru gateway
gateway_script="gateway.sh"
if [ -f "$gateway_script" ]; then
    gnome-terminal --tab --title="Gateway" -- bash -c "./$gateway_script; read -p 'Press Enter to close this terminal.'"
else
    echo "Scriptul pentru Gateway nu exista."
fi

# Script pentru notify-microservice
notify="notify-microservice.sh"
if [ -f "$notify" ]; then
    gnome-terminal --tab --title="notify" -- bash -c "./$notify; read -p 'Press Enter to close this terminal.'"
else
    echo "Scriptul pentru notify nu exista."
fi

# Script pentru posts-microservice
posts="posts-microservice.sh"
if [ -f "$posts" ]; then
    gnome-terminal --tab --title="posts" -- bash -c "./$posts;  sleep 10; read -p 'Press Enter to close this terminal.'"
else
    echo "Scriptul pentru posts nu exista."
fi

# Script pentru query
query_script="query.sh"
if [ -f "$query_script" ]; then
    gnome-terminal --tab --title="Query" -- bash -c "./$query_script; read -p 'Press Enter to close this terminal.'"
else
    echo "Scriptul pentru query nu exista."
fi

# Script pentru storage-microservice
storage="storage-microservice.sh"
if [ -f "$storage" ]; then
    gnome-terminal --tab --title="storage" -- bash -c "./$storage; read -p 'Press Enter to close this terminal.'"
else
    echo "Scriptul pentru storage nu exista."
fi

# Script pentru topics-microservice
topics="topics-microservice.sh"
if [ -f "$topics" ]; then
    gnome-terminal --tab --title="topics" -- bash -c "./$topics; sleep 5; read -p 'Press Enter to close this terminal.'"
else
    echo "Scriptul pentru topics nu exista."
fi

# Script pentru users-microservice
users="users-microservice.sh"
if [ -f "$users" ]; then
    gnome-terminal --tab --title="users" -- bash -c "./$users; read -p 'Press Enter to close this terminal.'"
else
    echo "Scriptul pentru users nu exista."
fi



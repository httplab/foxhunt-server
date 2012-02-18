#!/bin/sh

DESC="Foxhunt Server"
FOXHUNT_HOME=/u/srv/foxhunt-server
FOXHUNT_JAR=foxhunt-server.jar
FOXHUNT_BUILD_JAR=com.foxhunt.server-1.0-SNAPSHOT.one-jar.jar

CONNECTION=dev@dev.httplab.ru
FOXHUNT_INIT='sudo service foxhunt'

REMOTE_CMD="rm -f $FOXHUNT_HOME/echo $FOXHUNT_JAR && \
           mv $FOXHUNT_HOME/$FOXHUNT_BUILD_JAR $FOXHUNT_HOME/$FOXHUNT_JAR && \
           $FOXHUNT_INIT start"

case "$1" in
  start)
        echo "Starting remote $DESC: "
        echo `ssh $CONNECTION $FOXHUNT_INIT $1`
        echo "Done."
        ;;
  stop)
        echo "Stopping remote $DESC: "
        echo `ssh $CONNECTION $FOXHUNT_INIT $1`
        ;;
  deploy)
        echo "Upload current build and restart remote $DESC: "
        scp ./target/$FOXHUNT_BUILD_JAR $CONNECTION:$FOXHUNT_HOME
        echo $
        echo `ssh $CONNECTION $REMOTE_CMD`
        ;;
     *)
        N=fh-deploy
        echo "Usage: $N {start|stop|deploy}" >&2
        exit 1
        ;;
  esac

  exit 0
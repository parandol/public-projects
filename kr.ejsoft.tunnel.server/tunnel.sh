#!/bin/sh
PID=/var/run/tunnel.pid
LOG=/var/log/tunnel.log

CLAZZ="kr.ejsoft.tunnel.server.Application"
BASEPATH="/root/tunnel.server"

CLASSPATH="${BASEPATH}/kr.ejsoft.tunnel.server-0.0.1-SNAPSHOT.jar"
CLASSPATH="${CLASSPATH}:${BASEPATH}/log4j-api-2.10.0.jar"
CLASSPATH="${CLASSPATH}:${BASEPATH}/log4j-core-2.10.0.jar"
CLASSPATH="${CLASSPATH}:${BASEPATH}/log4j-slf4j-impl-2.10.0.jar"
CLASSPATH="${CLASSPATH}:${BASEPATH}/slf4j-api-1.8.0-alpha2.jar"

OPTIONS="-Djavax.net.ssl.keyStore=${BASEPATH}/server.keystore"
OPTIONS="${OPTIONS} -Djavax.net.ssl.keyStorePassword=changeit"
OPTIONS="${OPTIONS} -Djavax.net.ssl.trustStore=${BASEPATH}/cacerts.keystore"
OPTIONS="${OPTIONS} -Djavax.net.ssl.trustStorePassword=changeit"
#OPTIONS="${OPTIONS} -Djavax.net.debug=all"

COMMAND="java -classpath ${CLASSPATH} ${OPTIONS} ${CLAZZ} remote tunnel-port host port"


status() {
    # echo
    # echo "==== Status"

    if [ -f $PID ]
    then
        # echo
        echo "Pid file: $( cat $PID ) [$PID]"
        # echo
        ps -ef | grep -v grep | grep $( cat $PID )
    else
        # echo
        echo "No Pid file"
    fi
}

start() {
    if [ -f $PID ]
    then
        # echo
        echo "Already started. PID: [$( cat $PID )]"
    else
        # echo "==== Start"
        touch $PID
        if nohup $COMMAND >>$LOG 2>&1 &
        then echo $! >$PID
             echo "Started..."
             # echo "$(date '+%Y-%m-%d %X'): START" >> $LOG
        else echo "Error... "
             /bin/rm $PID
        fi
    fi
}

kill_cmd() {
    SIGNAL=""; MSG="Killing "
    while true
    do

        # LIST=`ps -ef | grep -v grep | grep $CMD | grep -w $USR | awk '{print $2}'`
        LIST=`ps -ef | grep -v grep | grep $CLAZZ | awk '{print $2}'`
        if [ "$LIST" ]
        then
            echo; echo "$MSG $LIST"
            # echo
            echo $LIST | xargs kill $SIGNAL
            sleep 2
            SIGNAL="-9" ; MSG="Killing $SIGNAL"
            if [ -f $PID ]
            then
                /bin/rm $PID
            fi
        else
           # echo; echo "All killed..." ; echo
           break
        fi
    done
}

stop() {
    # echo "==== Stop"

    if [ -f $PID ]
    then
        if kill $( cat $PID )
        then
             echo "Stoped...."
             # echo "$(date '+%Y-%m-%d %X'): STOP" >>$LOG
        fi
        /bin/rm $PID
        kill_cmd
    else
        echo "No pid file. Already stopped?"
    fi
}

case "$1" in
    'start')
            start
            ;;
    'stop')
            stop
            ;;
    'restart')
            stop ; echo "Sleeping..."; sleep 1 ;
            start
            ;;
    'status')
            status
            ;;
    *)
            echo
            echo "Usage: $0 { start | stop | restart | status }"
            echo
            exit 1
            ;;
esac

exit 0



#!/bin/sh

if ! [ -z $NEW_RELIC_LOG_LEVEL ]; then
   sed -i -e "/log_level:/s+info+$NEW_RELIC_LOG_LEVEL+" \
       /usr/local/tomcat/newrelic/newrelic.yml
fi

if ! [ -z $AGENT_NAME ]; then
   sed -i -e "/app_name:/s+My Application+$AGENT_NAME+" \
       /usr/local/tomcat/newrelic/newrelic.yml
fi

if ! [ -z $NEW_RELIC_LICENSE_KEY ]; then
   sed -i -e "/license_key:/s+<%= license_key %>+$NEW_RELIC_LICENSE_KEY+" \
       /usr/local/tomcat/newrelic/newrelic.yml

   echo "Enabling java agent $PLUGIN_VERSION"
   export JAVA_OPTS="-javaagent:/usr/local/tomcat/newrelic/newrelic-agent-$PLUGIN_VERSION.jar"
fi

exec /usr/local/tomcat/bin/catalina.sh run

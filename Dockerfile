FROM tomcat:jre8

ENV PLUGIN_VERSION=5.0.0

ADD stateful-server.war /usr/local/tomcat/webapps/
ADD http://apt.newrelic.com/newrelic/java-agent/newrelic-agent/${PLUGIN_VERSION}/newrelic-agent-${PLUGIN_VERSION}.jar /usr/local/tomcat/newrelic/
ADD http://apt.newrelic.com/newrelic/java-agent/newrelic-agent/${PLUGIN_VERSION}/newrelic.yml /usr/local/tomcat/newrelic/
ADD start-agent.sh /usr/local/tomcat/bin/
RUN sed -i -e '/^tomcat.util.scan.StandardJarScanFilter.jarsToSkip/a\stateful-server.war,\\' -e '/^tomcat.util.scan.StandardJarScanFilter.jarsToSkip/a\newrelic-agent-4.11.0.jar,\\' /usr/local/tomcat/conf/catalina.properties

ENV NEW_RELIC_LICENSE_KEY="" NEW_RELIC_LOG_LEVEL="info" AGENT_NAME=""

ENTRYPOINT ["/usr/local/tomcat/bin/start-agent.sh"]

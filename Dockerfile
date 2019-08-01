FROM tomcat:jre8

ENV AGENT_NAME=""
ENV NEW_RELIC_LICENSE_KEY=""
ENV NEW_RELIC_LOG_LEVEL="info"
ENV PLUGIN_VERSION=5.3.0

ADD target/demo.war /usr/local/tomcat/webapps/
ADD http://apt.newrelic.com/newrelic/java-agent/newrelic-agent/${PLUGIN_VERSION}/newrelic-agent-${PLUGIN_VERSION}.jar /usr/local/tomcat/newrelic/
ADD http://apt.newrelic.com/newrelic/java-agent/newrelic-agent/${PLUGIN_VERSION}/newrelic.yml /usr/local/tomcat/newrelic/
ADD newrelic.yml /usr/local/tomcat/newrelic/newrelic.yml
ADD start-agent.sh /usr/local/tomcat/bin/
RUN sed -i -e "/^tomcat.util.scan.StandardJarScanFilter.jarsToSkip/a\demo.war,\\" -e "/^tomcat.util.scan.StandardJarScanFilter.jarsToSkip/a\newrelic-agent-${PLUGIN_VERSION}.jar,\\" /usr/local/tomcat/conf/catalina.properties

ENTRYPOINT ["/usr/local/tomcat/bin/start-agent.sh"]

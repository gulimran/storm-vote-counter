storm -c nimbus.host=localhost kill vote-count-topology -w 10
sleep 11
echo "Using Environment $environment"
storm -c nimbus.host=localhost -c environment=${environment} jar /opt/@artifactId@/lib/@artifactId@-@version@-all.jar org.apache.storm.flux.Flux --remote /opt/@artifactId@/conf/vote-count-topology.yaml

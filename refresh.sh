rm -rf target/ 
sbt assembly 
cp /Users/apple/git_space/gatling-solr/gatling-solr/target/scala-2.12/gatling-solr* ~/Work/GATLING/lib/
cp /Users/apple/git_space/gatling-solr/gatling-solr/src/test/scala/* /Users/apple/Work/GATLING/user-files/simulations/

This is the demo application used at my Dev2Next presentation about GraphQL.

* lolomo: The main application used in the demo. This is a Gradle project that you can load in Intellij
* reviews: The second DGS used in the demo to federate `reviews`.
* apollo-federation-demo: Contains the configuration file to start Rover.


To start the federated system.
1. Start `lolomo` from Intellij or `./gradlew bootRun`
2. Start `reviews` from Intellij or `./gradlew bootRun`
3. Start the gateway from the `apollo-federation-demo` directory with `rover dev --supergraph-config ./demo-subgraph.yml`

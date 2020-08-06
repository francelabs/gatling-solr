package lucidworks.gatling.solr.action

import java.util
import java.util.{Base64, Collections}

import com.lucidworks.cloud.{ManagedSearchClusterStateProvider, OAuth2HttpRequestInterceptor, OAuth2HttpRequestInterceptorBuilder}
import io.gatling.core.action.Action
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.structure.ScenarioContext
import lucidworks.gatling.solr.protocol.{SolrComponents, SolrProtocol}
import lucidworks.gatling.solr.request.builder.SolrQueryAttributes
import org.apache.solr.client.solrj.impl.CloudSolrClient


class ManagedSolrQueryRequestActionBuilder[K](solrAttributes: SolrQueryAttributes[K]) extends ActionBuilder {

  override def build(ctx: ScenarioContext, next: Action): Action = {
    import ctx.{coreComponents, protocolComponentsRegistry, throttled}

    val solrComponents: SolrComponents = protocolComponentsRegistry.components(SolrProtocol.SolrProtocolKey)

    val solrClients = new util.ArrayList[CloudSolrClient]()

    var solrClient = null: CloudSolrClient;
    // create http request interceptor and start it

    for (i <- 0 until solrComponents.solrProtocol.numClients) {
      solrClient = new CloudSolrClient.Builder(new ManagedSearchClusterStateProvider(Collections.singletonList(solrComponents.solrProtocol.solrurl))).build
      solrClient.setDefaultCollection(solrComponents.solrProtocol.collection)
      solrClients.add(solrClient)
    }

    coreComponents.actorSystem.registerOnTermination(
      for (i <- 0 until solrComponents.solrProtocol.numClients) {
        solrClients.get(i).close()
      }
    )

    new SolrQueryRequestAction(
      solrClients,
      solrAttributes,
      coreComponents,
      solrComponents.solrProtocol,
      throttled,
      next
    )

  }

}
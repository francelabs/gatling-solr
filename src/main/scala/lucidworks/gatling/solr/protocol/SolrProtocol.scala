package lucidworks.gatling.solr.protocol

import java.util.Properties

import io.gatling.core.CoreComponents
import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.protocol.{Protocol, ProtocolKey}

object SolrProtocol {

  def apply(configuration: GatlingConfiguration): SolrProtocol = SolrProtocol(
    authClientId = "",
    authClientSecret = "",
    customerId = "",
    zkhost = "",
    solrurl = "",
    apikey = "",
    collection = "",
    properties = new Properties(),
    numClients = 0
  )

  val SolrProtocolKey = new ProtocolKey[SolrProtocol, SolrComponents] {

    type Protocol = SolrProtocol
    type Components = SolrComponents

    def protocolClass: Class[io.gatling.core.protocol.Protocol] = classOf[SolrProtocol].asInstanceOf[Class[io.gatling.core.protocol.Protocol]]

    def defaultProtocolValue(configuration: GatlingConfiguration): SolrProtocol = SolrProtocol(configuration)

    def newComponents(coreComponents: CoreComponents): SolrProtocol => SolrComponents = {

      solrProtocol => {
        val solrComponents = SolrComponents(
          solrProtocol
        )

        solrComponents
      }
    }
  }
}

case class SolrProtocol(
                         authClientId: String,
                         authClientSecret: String,
                         customerId: String,
                         zkhost: String,
                         solrurl: String,
                         apikey: String,
                         collection: String,
                         properties: Properties,
                         numClients: Int,
                         numIndexClients: Int) extends Protocol {

  def authClientId(authClientId: String): SolrProtocol = copy(authClientId = authClientId)

  def authClientSecret(authClientSecret: String): SolrProtocol = copy(authClientSecret = authClientSecret)

  def customerId(customerId: String): SolrProtocol = copy(customerId = customerId)

  def zkhost(zkhost: String): SolrProtocol = copy(zkhost = zkhost)

  def solrurl(solrurl: String): SolrProtocol = copy(solrurl = solrurl)

  def apikey(apikey: String): SolrProtocol = copy(apikey = apikey)

  def collection(collection: String): SolrProtocol = copy(collection = collection)

  def properties(properties: Properties): SolrProtocol = copy(properties = properties)

  def numClients(numClients: Int): SolrProtocol = copy(numClients = numClients)

  def numIndexClients(numIndexClients: Int): SolrProtocol = copy(numIndexClients = numIndexClients)
}

package adverity.adetlv.csvParser

import adverity.adetlv.rest.config.ConfigDto
import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object ConfigControllerSpec : Spek({
    describe("ConfigControlle Suite") {
        var embeddedServer: EmbeddedServer = ApplicationContext.run(EmbeddedServer::class.java) // <1>
        var client: HttpClient = HttpClient.create(embeddedServer.url) // <2>

        it("test /config responds distinct datasource and campaign list") {
            val req = HttpRequest.GET<Any>("/config")
            val retrieve = client.toBlocking().retrieve(req, Argument.of(ConfigDto::class.java))
            assertEquals(mutableListOf(
                    "Facebook Ads",
                    "Google Adwords",
                    "Google Analytics"),    retrieve.datasource )
            assertEquals(mutableListOf(
                    "Like Ads",
                    "Offer Campaigns - Conversions",
                    "B2B - Leads",
                    "GDN Prospecting - App - Prio 1 Offer",
                    "GDN Prospecting - App - Prio 2 Offer",
                    "GDN RMKT - Mobile - Prio 1 Offer",
                    "GDN RMKT - Mobile - Prio 2 Offer",
                    "New General Campaign - Africa - Desktop",
                    "New General Campaign - Africa - Mobile",
                    "New General Campaign - Arab - Desktop",
                    "New General Campaign - Arab - Mobile",
                    "GDN RMKT - Interstitials - Prio 1 Offer",
                    "GDN RMKT - Interstitials - Prio 2 Offer",
                    "New General Campaign - Asia - Desktop",
                    "New General Campaign - Asia - Mobile"), retrieve.campaign)
        }

        afterGroup {
            client.close()
            embeddedServer.close()
        }
    }
})
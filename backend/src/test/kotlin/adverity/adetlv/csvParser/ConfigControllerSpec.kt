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
            assertEquals(retrieve.datasource, mutableListOf("Facebook Ads", "Google Adwords", "Google Analytis"))
            assertEquals(retrieve.campaign, mutableListOf("Like Ads", "Offer Campaigns - Conversions", "B2B - Leads", "GDN Prospecting - App - Prio 1 Offer", "GDN Prospecting - App - Prio 2 Offer"))
        }

        afterGroup {
            client.close()
            embeddedServer.close()
        }
    }
})
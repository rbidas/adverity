package adverity.adetlv.csvParser

import adverity.adetlv.rest.config.ConfigDto
import adverity.adetlv.rest.data.DataDto
import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object DataControllerSpec : Spek({
    describe("DataControlle Suite") {
        var embeddedServer: EmbeddedServer = ApplicationContext.run(EmbeddedServer::class.java) // <1>
        var client: HttpClient = HttpClient.create(embeddedServer.url) // <2>

        it("test /data responds sum of clicks and impressions") {
            val req = HttpRequest.GET<Any>("/data?campaign=Like%20Ads&campaign=Offer%20Campaigns%20-%20Conversions&datasource=Facebook%20Ads")
            val retrieve = client.toBlocking().retrieve(req, Argument.of(List::class.java, DataDto::class.java))
            assertEquals(retrieve.size, 2)
            //assertEquals(retrieve.campaign, mutableListOf("Like Ads", "Offer Campaigns - Conversions", "B2B - Leads", "GDN Prospecting - App - Prio 1 Offer", "GDN Prospecting - App - Prio 2 Offer"))
        }

        afterGroup {
            client.close()
            embeddedServer.close()
        }
    }
    describe("DataControlle Suite") {
        var embeddedServer: EmbeddedServer = ApplicationContext.run(EmbeddedServer::class.java) // <1>
        var client: HttpClient = HttpClient.create(embeddedServer.url) // <2>

        it("test /data responds sum of clicks and impressions") {
            val req = HttpRequest.GET<Any>("/data/")
            val retrieve = client.toBlocking().retrieve(req, Argument.of(List::class.java, DataDto::class.java))
            assertEquals(retrieve.size, 4)
            //assertEquals(retrieve.campaign, mutableListOf("Like Ads", "Offer Campaigns - Conversions", "B2B - Leads", "GDN Prospecting - App - Prio 1 Offer", "GDN Prospecting - App - Prio 2 Offer"))
        }

        afterGroup {
            client.close()
            embeddedServer.close()
        }
    }
})


package adverity.adetlv.rest.config

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import javax.inject.Inject

@Controller("/config")
class ConfigController(@Inject var configService: ConfigService) {
    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    fun index(): ConfigDto {

        val dto = ConfigDto(configService.getDataSource(), configService.getCampaign())
        return dto
    }
}
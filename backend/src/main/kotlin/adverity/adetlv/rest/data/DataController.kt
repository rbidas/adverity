package adverity.adetlv.rest.data

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import java.util.*
import javax.inject.Inject

@Controller("/data")
class DataController(@Inject var dataService: DataService) {
    @Get("/{?campaign}{?datasource}")
    @Produces(MediaType.APPLICATION_JSON)
    fun index(campaign: Optional<List<String>>, datasource: Optional<List<String>>): Iterable<DataDto> {

        return dataService.getData(campaign, datasource)

    }
}
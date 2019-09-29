package adverity.adetlv.rest.data

import java.util.*

interface DataService {
    fun getData(campaign: Optional<List<String>>, datasource: Optional<List<String>>): Iterable<DataDto>

}

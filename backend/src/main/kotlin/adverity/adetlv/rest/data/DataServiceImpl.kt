package adverity.adetlv.rest.data

import adverity.adetlv.csvParser.CsvDataSource
import adverity.adetlv.csvParser.DataSourceDto
import adverity.adetlv.rest.config.ConfigService
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataServiceImpl(@Inject var csvDataSource: CsvDataSource) : DataService {

    override fun getData(campaign: Optional<List<String>>, datasource: Optional<List<String>>): Iterable<DataDto> {
        val groupBy = csvDataSource.data()
                .filter { if(datasource.isPresent) datasource.get().contains(it.datasource) else true}
                .filter { if (campaign.isPresent) campaign.get().contains(it.campaign)  else true }
                .groupBy { it.date }

        val mutableListOf = mutableListOf<DataDto>()
        groupBy.forEach {
            mutableListOf.add(
                    DataDto(
                            it.key, it.value.stream().mapToInt(DataSourceDto::clicks).sum(),
                            it.value.stream().mapToInt(DataSourceDto::impressions).sum()))
        }
        return mutableListOf
    }
}
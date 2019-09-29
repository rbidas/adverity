package adverity.adetlv.rest.data

import adverity.adetlv.csvParser.CsvDataSource
import adverity.adetlv.csvParser.DataSourceDto
import adverity.adetlv.rest.config.ConfigService
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataServiceImpl(@Inject var csvDataSource: CsvDataSource,
                      @Inject var configService: ConfigService) : DataService {

    override fun getData(campaign: Optional<List<String>>, datasource: Optional<List<String>>): Iterable<DataDto> {
        val campaignList = if (campaign.isPresent) campaign.get() else configService.getCampaign()
        val datasourceList = if (campaign.isPresent) datasource.get() else configService.getDataSource()

        val groupBy = csvDataSource.data()
                .filter { datasourceList.contains(it.datasource) }
                .filter { campaignList.contains(it.campaign) }
                .groupBy { it.date }

        val mutableListOf = mutableListOf<DataDto>()
        groupBy.forEach {
            mutableListOf.add(DataDto(it.key, it.value.stream().mapToInt(DataSourceDto::clicks).sum(), it.value.stream().mapToInt(DataSourceDto::impressions).sum()))
        }
        return mutableListOf
    }
}
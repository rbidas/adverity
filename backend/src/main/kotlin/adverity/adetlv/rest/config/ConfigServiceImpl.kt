package adverity.adetlv.rest.config

import adverity.adetlv.csvParser.CsvDataSource
import java.util.stream.Collectors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigServiceImpl(@Inject var datasource: CsvDataSource) : ConfigService {
    override fun getCampaign(): Collection<String> {
        return datasource.data().stream().map { it -> it.campaign }.distinct().collect(Collectors.toList())
    }

    override fun getDataSource(): Collection<String> {
        return datasource.data().stream().map { it -> it.datasource }.distinct().collect(Collectors.toList())
    }
}
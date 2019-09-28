package adverity.adetlv.csvParser

import io.micronaut.context.annotation.Value
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.nio.file.Files
import java.nio.file.Paths
import javax.inject.Singleton

const val DATE: String = "Date"
const val DATASOURCE: String = "Datasource"
const val CAMPAIGN: String = "Campaign"
const val CLICKS: String = "Clicks"
const val IMPRESSIONS: String = "Impressions"

@Singleton
class CsvDataSource(@Value("\${datasource.path}") val path: String) : DataSource {

    override fun data(): Collection<DataSourceDto> {
        val reader = Files.newBufferedReader(Paths.get(path))
        val csvParser = CSVParser(reader, CSVFormat.EXCEL.withSkipHeaderRecord().withHeader(DATE, DATASOURCE, CAMPAIGN, CLICKS, IMPRESSIONS))

        val toReturn = mutableListOf<DataSourceDto>()
        csvParser.mapTo(toReturn, { DataSourceDto(it.get(DATE), it.get(DATASOURCE), it.get(CAMPAIGN), it.get(CLICKS), it.get(IMPRESSIONS)) })

        return toReturn
    }
}
package adverity.adetlv.csvParser

import io.micronaut.context.annotation.Value
import java.nio.file.Files
import java.nio.file.Paths
import javax.inject.Singleton

@Singleton
class CsvDataSource(@Value("\${datasource.path}") val path: String) : DataSource {

    override fun data(): Collection<DataSourceDto> {
        val reader = Files.newBufferedReader(Paths.get(path))
        val toReturn = mutableListOf<DataSourceDto>()
        //read line by line to avoid read very big files into memory
        val header = reader.readLine()
        reader.forEachLine { line ->
            val split = line.split(",")
            toReturn.add(DataSourceDto(split[0], split[1], split[2], convert(split[3]), convert(split[4])))
         }

        return toReturn
    }

    private fun convert(get: String?): Int {
        if (get.isNullOrEmpty())
            return 0;
        return get.toInt()
    }
}
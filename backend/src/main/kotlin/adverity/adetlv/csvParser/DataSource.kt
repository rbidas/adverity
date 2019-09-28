package adverity.adetlv.csvParser

interface DataSource {
    fun data(): Collection<DataSourceDto>
}
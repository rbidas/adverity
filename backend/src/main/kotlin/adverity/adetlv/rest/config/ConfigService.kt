package adverity.adetlv.rest.config

interface ConfigService {
    fun getDataSource(): Collection<String>
    fun getCampaign(): Collection<String>
    fun getConfig(): ConfigDto
}
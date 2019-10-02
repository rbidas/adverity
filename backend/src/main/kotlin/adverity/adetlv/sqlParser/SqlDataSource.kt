package adverity.adetlv.sqlParser

import adverity.adetlv.csvParser.CsvDataSource
import adverity.adetlv.csvParser.DataSourceDto
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession
import javax.inject.Inject
import javax.inject.Singleton
import javax.persistence.EntityManager


@Singleton
class SqlDataSource(@CurrentSession var entityManager: EntityManager, @Inject var csvDataSource: CsvDataSource) {
    fun importDb(): String {
        csvDataSource.data().forEach { save(it) }
        return "OK"
    }
    fun save(sourceDto: DataSourceDto): DataSource {
        val entity = DataSource(sourceDto.date, sourceDto.datasource, sourceDto.campaign, sourceDto.clicks, sourceDto.impressions)
        entityManager.persist(entity)

        return entity
    }
    fun getConfig(title: String): List<DataSource> {
        return entityManager.createQuery("FROM DATASOURCE AS entity", DataSource::class.java)
                .setParameter("datasource", title)
                .resultList
    }
    fun data(): List<DataSource> {
        //return entityManager.createNativeQuery("select * from DATASOURCE", DataSource::class.java).resultList as List<DataSource>
        return entityManager.createQuery("from DATASOURCE", DataSource::class.java).resultList as List<DataSource>
    }

}
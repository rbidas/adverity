package adverity.adetlv.sqlParser

import javax.persistence.*

@Entity
@Table(name = "DATASOURCE")
class DataSource(val date: String,
                 val datasource: String,
                 val campaign: String,
                 val clicks: Int,
                 val impressions: Int){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null

}
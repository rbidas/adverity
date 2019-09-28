package adverity.adetlv

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("adverity.adetlv")
                .mainClass(Application.javaClass)
                .start()
    }
}
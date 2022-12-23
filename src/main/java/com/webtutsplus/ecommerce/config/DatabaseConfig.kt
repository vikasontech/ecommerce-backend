package com.webtutsplus.ecommerce.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DatabaseConfig {
    @Value("\${spring.datasource.url}") //@Value("postgres://bleobskvknjebh:0f375194cb9cc4f76743f959ba0e72a17a4b90592b984e4ec35d5d8908323342@ec2-107-22-245-82.compute-1.amazonaws.com:5432/d1pv0jjbf5qlbd")

    private val dbUrl: String? = null
    @Bean
    fun dataSource(): DataSource {
        val config = HikariConfig()
        config.jdbcUrl = dbUrl
        return HikariDataSource(config)
    }
}
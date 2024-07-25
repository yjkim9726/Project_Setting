package com.project.common.config.database

import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.annotation.MapperScan
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

/**
 * @fileName Databaseconfiguration
 * @author yunjeong
 * @since  2023/08/03
 * @version 1.0
 *
 * @Modification Information
 * @
 * @  DATE          AUTHOR         NOTE
 * @ -----------   ------------   -------------------------------
 * @ 2023/08/03        yunjeong        최초 작성
 */
@Configuration
@MapperScan(
    basePackages = ["com.project.api.**.repository"],
    sqlSessionFactoryRef = "sqlSessionFactory"
)
@EnableTransactionManagement
class DatabaseConfiguration {

    @Primary
    @Bean(name= ["sqlDataSource"])
    @ConfigurationProperties(prefix = "spring.database.datasource.hikari")
    fun dataSource(): DataSource {
        val dataSource = DataSourceBuilder.create().type(HikariDataSource::class.java).build()
        dataSource.connectionInitSql = "SET NAMES utf8mb4"
        return dataSource
    }

    @Primary
    @Bean(name = ["sqlSessionFactory"])
    @Throws(Exception::class)
    fun sqlSessionFactory(@Qualifier("sqlDataSource") dataSource: DataSource?): SqlSessionFactory? {
        val sessionFactory = SqlSessionFactoryBean()
        sessionFactory.setDataSource(dataSource)
        sessionFactory.setTypeAliasesPackage("com.project.api.*.model")
        sessionFactory.setConfigLocation(PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"))
        sessionFactory.setMapperLocations(*PathMatchingResourcePatternResolver().getResources("classpath:/mybatis/**/*.xml"))
        sessionFactory.vfs = SpringBootVFS::class.java
        return sessionFactory.`object`!!
    }

    @Primary
    @Bean(name = ["sqlSessionFactory"])
    @Throws(Exception::class)
    fun sqlSessionTemplate(sqlSessionFactory: SqlSessionFactory?): SqlSessionTemplate? {
        return SqlSessionTemplate(sqlSessionFactory)
    }

    @Bean
    @Throws(Exception::class)
    fun dataSourceTransactionManager(): DataSourceTransactionManager {
        return DataSourceTransactionManager(dataSource())
    }

}
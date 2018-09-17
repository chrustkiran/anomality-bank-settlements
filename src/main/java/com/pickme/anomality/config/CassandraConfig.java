package com.pickme.anomality.config;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import jnr.ffi.annotations.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.cassandra.config.*;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@PropertySources({
        @PropertySource(value = {"classpath:application.properties"}),
        @PropertySource(value = "file:/opt/bank-settlements/config/application.properties", ignoreResourceNotFound = true)
})

public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.contact-points}")
    String host;

    @Value("${spring.data.cassandra.port}")
    String port;

    @Value("${spring.data.cassandra.keyspace-name}")
    String keyspace;

    @Override
    protected String getKeyspaceName() {
        return keyspace;
    }



    @Bean
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster =
                new CassandraClusterFactoryBean();
        cluster.setContactPoints(host);
        cluster.setPort(Integer.parseInt(port));
        return cluster;
    }

    @Bean
    public CassandraMappingContext cassandraMapping()
            throws ClassNotFoundException {
        return new BasicCassandraMappingContext();
    }
}
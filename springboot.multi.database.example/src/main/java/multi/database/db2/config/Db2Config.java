package multi.database.db2.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;


@Configuration
@PropertySource("classpath:/application.properties")
@MapperScan(value="multi.database.db2.dao", sqlSessionFactoryRef="db2SqlSessionFactory")
public class Db2Config {
	private static final Logger logger = LoggerFactory.getLogger(Db2Config.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Value("${spring.db2.datasource.mapper-locations}")
	private String mapperLocations;
	
	@Value("${spring.db2.datasource.mybatis-config}")
	private String configPath;
	
//	@Bean(name = "db2HikariConfig")
//	@ConfigurationProperties(prefix = "spring.db2.datasource")
//	public HikariConfig db2HikariConfig() {
//		return new HikariConfig();
//	}
//
//	@Bean(name = "db2DataSource")
//	public DataSource db2DataSource() {
//		DataSource dataSource = new HikariDataSource(db2HikariConfig());
////		DataSource dataSource = DataSourceBuilder.create().build();
//		logger.info("Resource Datasource : {}", dataSource);
//		return dataSource;
//	}

	@Bean(name = "db2DataSource")
	@ConfigurationProperties(prefix = "spring.db2.datasource")
	public DataSource db2DataSource() {
		DataSource dataSource = DataSourceBuilder.create().build();
		logger.info("Datasource : {}", dataSource);
		return dataSource;
	}

	@Bean(name = "db2SqlSessionFactory")
	public SqlSessionFactory db2SqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(mapperLocations));
		
//		Properties properties = new Properties();
//		properties.put("mapUnderscoreToCamelCase", true);
//		sqlSessionFactoryBean.setConfigurationProperties(properties);
		
		//Mybatis config파일 위치
		Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource(configPath);
		sqlSessionFactoryBean.setConfigLocation(myBatisConfig);
		
		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name = "db2SqlSessionTemplate")
	public SqlSessionTemplate db2SqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}

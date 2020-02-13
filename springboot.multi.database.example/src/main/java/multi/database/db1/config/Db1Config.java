package multi.database.db1.config;

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
//import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@PropertySource("classpath:/application.properties")
@MapperScan(value="multi.database.db1.dao", sqlSessionFactoryRef="db1SqlSessionFactory")
public class Db1Config {
	private static final Logger logger = LoggerFactory.getLogger(Db1Config.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Value("${spring.db1.datasource.mapper-locations}")
	private String mapperLocations;
	
	@Value("${spring.db1.datasource.mybatis-config}")
	private String configPath;
	
//	@Bean(name = "db1HikariConfig")
//	@Primary
//	@ConfigurationProperties(prefix = "spring.db1.datasource")
//	public HikariConfig db1HikariConfig() {
//		return new HikariConfig();
//	}
//	
//	@Bean(name = "db1DataSource")
//	@Primary
//	public DataSource db1DataSource() {
//		DataSource dataSource = new HikariDataSource(db1HikariConfig());
//		logger.info("OAuth Datasource : {}", dataSource);
//		return dataSource;
//	}
	
	@Bean(name = "db1DataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.db1.datasource")
	public DataSource db1DataSource() {
		DataSource dataSource = DataSourceBuilder.create().build();
		logger.info("Datasource : {}", dataSource);
		return dataSource;
	}

	@Bean(name = "db1SqlSessionFactory")
	@Primary
	public SqlSessionFactory db1SqlSessionFactory(DataSource dataSource) throws Exception {
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

	@Bean(name = "db1SqlSessionTemplate")
	@Primary
	public SqlSessionTemplate db1SqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}

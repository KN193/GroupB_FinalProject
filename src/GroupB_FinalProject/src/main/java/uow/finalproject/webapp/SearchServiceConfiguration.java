package uow.finalproject.webapp;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import uow.finalproject.webapp.utility.SearchService;

@EnableAutoConfiguration 
@Configuration
public class SearchServiceConfiguration {
	
	@Autowired
	private EntityManager bentityManager;
	
	
	@Bean
	SearchService hibernateSearchService() {
		SearchService hibernateSearchService = new SearchService(bentityManager);
		hibernateSearchService.initializeHibernateSearch();
		return hibernateSearchService;
	}

}

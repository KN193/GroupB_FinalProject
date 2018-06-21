package uow.finalproject.webapp.utility;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uow.finalproject.webapp.entity.Persons;

@Service
public class SearchService {

    @Autowired
    private final EntityManager centityManager;


    @Autowired
    public SearchService(EntityManager entityManager) {
        super();
        this.centityManager = entityManager;
    }


    public void initializeHibernateSearch() {

        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(centityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public List<uow.finalproject.webapp.entity.Service> fuzzySearch(String searchTerm) {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(centityManager);
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(uow.finalproject.webapp.entity.Service.class).get();
        
        //Query luceneQuery = qb.keyword().fuzzy().withEditDistanceUpTo(2).withPrefixLength(1).onFields("name").matching(searchTerm).createQuery();

        Query luceneQuery = qb.bool()
        		.should(qb.keyword().fuzzy().withEditDistanceUpTo(2).withPrefixLength(1).onFields("name").matching(searchTerm).createQuery())
        		.should(qb.phrase().withSlop(2).onField("description").sentence(searchTerm).createQuery())
				.createQuery();
        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, uow.finalproject.webapp.entity.Service.class);

        // execute search

        List<uow.finalproject.webapp.entity.Service> ServiceList = null;
        try {
        	
            ServiceList = jpaQuery.getResultList();
            if(ServiceList.isEmpty()) {
            	System.out.println("WWWWWW" + searchTerm);
            }
        } catch (NoResultException nre) {
            System.out.println(nre.getMessage());

        }

        return ServiceList;


    }

}

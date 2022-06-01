package pe.com.bootcamp.microservice.account.transactions.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import pe.com.bootcamp.microservice.account.transactions.entity.Commission;
import reactor.core.publisher.Flux;

@Repository
public interface ITransactionRepository extends ReactiveMongoRepository<Commission, String>{
}

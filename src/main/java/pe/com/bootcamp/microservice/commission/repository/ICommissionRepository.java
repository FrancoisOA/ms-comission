package pe.com.bootcamp.microservice.commission.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import pe.com.bootcamp.microservice.commission.entity.Commission;
import reactor.core.publisher.Flux;

@Repository
public interface ICommissionRepository extends ReactiveMongoRepository<Commission, String>{
	Flux<Commission> findByIdAccount(String idAccount);
}
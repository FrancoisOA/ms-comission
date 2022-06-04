package pe.com.bootcamp.microservice.commission.service;

import reactor.core.publisher.Mono;

public interface CrudService <T, ID>{ 
    Mono<T> deleteById(ID id);
    Mono<T> findById(ID id);
}

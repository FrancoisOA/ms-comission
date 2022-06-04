package pe.com.bootcamp.microservice.commission.service;

import java.util.Date;

import pe.com.bootcamp.microservice.commission.entity.Commission;
import pe.com.bootcamp.microservice.commission.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommissionService extends CrudService<Commission, String> {
	Mono<Account> getAccount(String idAccount);
	Flux<Commission> findByNumAccount(Date startDate, Date endDate, String numCuenta);
	Mono<Commission> saveMaintenance(Commission tran);
	Mono<Commission> saveUsing(Commission tran);
}

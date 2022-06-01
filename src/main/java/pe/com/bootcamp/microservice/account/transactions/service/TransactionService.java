package pe.com.bootcamp.microservice.account.transactions.service;

import pe.com.bootcamp.microservice.account.transactions.entity.Commission;
import pe.com.bootcamp.microservice.account.transactions.model.Account;
import reactor.core.publisher.Mono;

public interface TransactionService extends CrudService<Commission, String> {
	Mono<Account> getAccount(String idAccount);
}

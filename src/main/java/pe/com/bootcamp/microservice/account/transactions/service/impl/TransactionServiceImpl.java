package pe.com.bootcamp.microservice.account.transactions.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.com.bootcamp.microservice.account.transactions.config.WebclientConfig;
import pe.com.bootcamp.microservice.account.transactions.entity.Commission;
import pe.com.bootcamp.microservice.account.transactions.model.Account;
import pe.com.bootcamp.microservice.account.transactions.repository.ITransactionRepository;
import pe.com.bootcamp.microservice.account.transactions.service.CalculationService;
import pe.com.bootcamp.microservice.account.transactions.service.TransactionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    ITransactionRepository trxRepo;

    private WebclientConfig webclient= new WebclientConfig();
    
    @Override
    public Flux<Commission> findAll() {
    	log.info("Dentro de findAll");
    	return  trxRepo.findAll();
    }


	@Override
	public Mono<Commission> saveTrx(Commission tran) {
		tran.setStatus(true);
		tran.setDateTransaction(new Date());				
		return trxRepo.save(tran).doOnSuccess(x -> {
			log.info("Dentro de doOnSuccess");
			x.setStatus(true);
			CalculationService ca = (amount, balance) -> tran.getTypeTransaction().equals("retiro") ? balance - amount
					: tran.getTypeTransaction().equals("deposito") ? balance + amount : 0.0;
			webclient.getAccount(x.getIdAccount()).switchIfEmpty(Mono.empty()).flatMap(f -> {
				f.setBalance(ca.Calcule(x.getAmount(), f.getBalance()));
				tran.setInitialBalance(f.getBalance());
				log.info("Dentro de subscribe");
				return webclient.updateAccount(f);
			});
		});
	}
   
	@Override
	public Mono<Commission> saveTransfer(Commission tran) {
		tran.setStatus(true);
		tran.setDateTransaction(new Date());				
		return trxRepo.save(tran).doOnSuccess(x -> {
			log.info("Dentro de doOnSuccess");
			x.setStatus(true);
			CalculationService ca = (amount, balance) -> tran.getTypeTransaction().equals("retiro") ? balance - amount
					: tran.getTypeTransaction().equals("deposito") ? balance + amount : 0.0;
			webclient.getAccount(x.getIdAccount()).switchIfEmpty(Mono.empty()).flatMap(f -> {
				f.setBalance(ca.Calcule(x.getAmount(), f.getBalance()));
				tran.setInitialBalance(f.getBalance());
				log.info("Dentro de subscribe");
				return webclient.updateAccount(f);
			});
		});
	}
	
	 @Override
	    public Mono<Commission> update(Commission t) {
	        return  trxRepo.findById(t.getId())
	                .switchIfEmpty(Mono.empty())
	                .flatMap(o -> {
	                    if (t.getAmount() != null) {o.setAmount(t.getAmount());                         }
	                    if (t.getIdAccount() != null) {o.setIdAccount(t.getIdAccount());                   }
	                    if (t.getCurrency() != null) {o.setCurrency(t.getCurrency());                     }
	                    if (t.getDestinationAccount() != null) {o.setDestinationAccount(t.getDestinationAccount()); }
	                    if (t.getDestinationBank() != null) {o.setDestinationBank(t.getDestinationBank());       }
	                    if (t.getChannel() != null) {o.setChannel(t.getChannel());                       }
	                    if (t.getStatus() != null) {o.setStatus(t.getStatus());                         }
	                    return trxRepo.save(o);
	                });
	    }

	    @Override
	    public Mono<Commission> deleteById(String id) {
	        return  trxRepo.findById(id)
	                .switchIfEmpty(Mono.empty())
	                .flatMap(o -> {
	                    o.setStatus(false);
	                    return trxRepo.save(o);
	                });
	    }

	    @Override
	    public Mono<Commission> findById(String id) {
	        return trxRepo.findById(id);
	    }

	    @Override
	    public Mono<Account> getAccount(String idAccount) {
	        return webclient.getAccount(idAccount);
	    }
}

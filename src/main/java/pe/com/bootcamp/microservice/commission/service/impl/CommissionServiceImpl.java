package pe.com.bootcamp.microservice.commission.service.impl;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.com.bootcamp.microservice.commission.config.WebclientConfig;
import pe.com.bootcamp.microservice.commission.entity.Commission;
import pe.com.bootcamp.microservice.commission.model.Account;
import pe.com.bootcamp.microservice.commission.repository.ICommissionRepository;
import pe.com.bootcamp.microservice.commission.service.CalculationService;
import pe.com.bootcamp.microservice.commission.service.CommissionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommissionServiceImpl implements CommissionService {

	@Autowired
	ICommissionRepository trxRepo;

	private WebclientConfig webclient= new WebclientConfig();


	@Override
	public Flux<Commission> findByNumAccount(Date startDate, Date endDate, String numCuenta) {
		log.info("Dentro de findByNumAccount");
		return  trxRepo.findByIdAccount(numCuenta)
				.filter(c -> c.getDateTransaction().before(endDate) && c.getDateTransaction().after(startDate))
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NO_CONTENT)));
	}

	@Override
	public Mono<Commission> saveMaintenance(Commission tran) {
		tran.setStatus(true);
		tran.setDateTransaction(new Date());		
		tran.setConcept("comision por mantenimiento");
		return trxRepo.save(tran).doOnSuccess(x -> {
			log.info("Dentro de doOnSuccess");
			x.setStatus(true);
			CalculationService ca = (commission, balance) -> balance-commission;
			webclient.getAccount(x.getIdAccount())
			.filter(c -> c.getTypeAccount()=="CORRIENTE" && c.getProfileAccount()=="PERSONAL")
			.switchIfEmpty(Mono.empty())
			.flatMap(f -> {
				f.setBalance(ca.Calcule(x.getAmountCommission(), f.getBalance()));
				x.setConcept("");
				log.info("Dentro de subscribe");
				return webclient.updateAccount(f);
			});
		});
	} 

	@Override
	public Mono<Commission> saveUsing(Commission tran) {
		tran.setStatus(true);
		tran.setDateTransaction(new Date());		
		tran.setConcept("comision por uso");
		return trxRepo.save(tran).doOnSuccess(x -> {
			log.info("Dentro de doOnSuccess");
			x.setStatus(true);
			CalculationService ca = (commission, balance) -> balance-commission;
			webclient.getAccount(x.getIdAccount())
			.filter(c -> c.getTypeAccount()=="AHORRO" && this.numTrxInMonth(c.getNumberAccount()) > this.maxTrxAllowed(c.getTypeAccount()))
			.switchIfEmpty(Mono.empty())
			.flatMap(f -> {
				f.setBalance(ca.Calcule(x.getAmountCommission(), f.getBalance()));
				x.setConcept("");
				log.info("Dentro de subscribe");
				return webclient.updateAccount(f);
			});
		});
	} 


	private int numTrxInMonth(String numberAccount) {
		int numDeposits = webclient.getNumDeposits(numberAccount,LocalDate.now().getMonthValue());
		int numWithdraw = webclient.getNumWithdraw(numberAccount,LocalDate.now().getMonthValue());
		return numDeposits  + numWithdraw;
	}

	private int maxTrxAllowed(String typeAccount) {

		int maxTrxAllowed = 0;
		if (typeAccount=="ahorro"){
			maxTrxAllowed = 5;	
		}
		if (typeAccount=="corriente"){
			maxTrxAllowed = 5;	
		}
		if (typeAccount=="plazo_fijo"){
			maxTrxAllowed = 5;	
		}
		return maxTrxAllowed;
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

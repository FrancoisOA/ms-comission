package pe.com.bootcamp.microservice.commission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.com.bootcamp.microservice.commission.entity.Commission;
import pe.com.bootcamp.microservice.commission.model.Account;
import pe.com.bootcamp.microservice.commission.service.CommissionService;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path="/commission")
public class CommissionController {

	@Autowired
	CommissionService commissionService;

    @PostMapping("/commission-maintenance")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Commission> saveMaintenanceComm(@RequestBody Commission commission){
    	log.info("Metodo saveCommission");
        return commissionService.saveMaintenance(commission);
    }
    
    @PostMapping("/commission-using")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Commission> saveUsingComm(@RequestBody Commission commission){
    	log.info("Metodo saveCommission");
        return commissionService.saveUsing(commission);
    }

    @GetMapping("/commission/account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Account>getCommissionByBankAccount(@PathVariable String id){
    	log.info("Metodo getDepositByAccount");
        return commissionService.getAccount(id);
    }    
    
}
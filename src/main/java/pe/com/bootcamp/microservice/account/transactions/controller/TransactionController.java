package pe.com.bootcamp.microservice.account.transactions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.com.bootcamp.microservice.account.transactions.entity.Commission;
import pe.com.bootcamp.microservice.account.transactions.service.impl.DepositServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/deposit")
public class TransactionController {

	@Autowired
	private final DepositServiceImpl depositServiceImpl;

//	@PostMapping("/createDeposit")
//	@ResponseStatus(HttpStatus.CREATED)
//	public void createTrx(@RequestBody Transaction depositDTO) {
//		depositServiceImpl.save(depositDTO);
//	}
//	
//	@PostMapping("/createWithdraw")
//	@ResponseStatus(HttpStatus.CREATED)
//	public void createTrx(@RequestBody Transaction depositDTO) {
//		depositServiceImpl.createDep(depositDTO);
//	}
//	
//	@PostMapping("/createTransfer")
//	@ResponseStatus(HttpStatus.CREATED)
//	public void createTransfer(@RequestBody Transaction depositDTO) {
//		depositServiceImpl.createDep(depositDTO);
//	}

	@GetMapping(value = "/get/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseBody
	public Flux<Commission> findAll() {
		return depositServiceImpl.findAllDep();
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Mono<Commission>> findDepositDTOById(@PathVariable("id") String id) {
		Mono<Commission> deposit = depositServiceImpl.findByDepId(id);
		return new ResponseEntity<Mono<Commission>>(deposit, deposit != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@PutMapping("/update/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Commission> update(@PathVariable("id") String id, @RequestBody Commission depositDTO) {
		return depositServiceImpl.updateDep(id, depositDTO);
	}

	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") String id) {
		depositServiceImpl.deleteDep(id).subscribe();
	}
}
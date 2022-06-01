package pe.com.bootcamp.microservice.account.transactions.service;

@FunctionalInterface
public interface CalculationService {
	Double Calcule(Double amount, Double balance);
}

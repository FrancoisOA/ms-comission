package pe.com.bootcamp.microservice.commission.service;

@FunctionalInterface
public interface CalculationService {
	Double Calcule(Double amount, Double balance);
}

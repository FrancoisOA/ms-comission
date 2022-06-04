package pe.com.bootcamp.microservice.commission.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Deposit {
	private String id;
 	private Double Amount;
 	private String concept;
 	private Boolean status;
}

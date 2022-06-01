package pe.com.bootcamp.microservice.account.transactions.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Document(collection = "tb_comission")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Commission {
	@Id
	private String id;
	private String typeTransaction; //tipo de movimiento ( deposit, retiro, comision x mant)
	private String idAccount;// id cuenta
	private Double Amount; //monto de transaccion
	private String currency; //divisa
	private String destinationAccount ; //cuenta destino de transferencia
	private String destinationBank;//banco destino trx
	private String channel; //canal

	//salida
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date dateTransaction; //fecha transaccion	
	private Double initialBalance; //saldo inicial
	private Double dailyBalance; //saldo diario
	private Boolean status;
}
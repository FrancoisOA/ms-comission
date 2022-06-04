package pe.com.bootcamp.microservice.commission.entity;

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
	private String idAccount;
	private Double AmountCommission;
	private String currency;
	private String concept;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date dateTransaction;
	private Boolean status;
}

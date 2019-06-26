package application.rest.v1;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/api/remittance")
public class RemittanceService {
	
	public String fabRemitResponseTemplate = "{\r\n" + 
			"	\"status\" : \"SUCCESS\",\r\n" + 
			"	\"correlationId\" : \"X1234\",\r\n" + 
			"	\"transactionId\": \"ID\"\r\n" + 
			"	\"remittanceReferenceNumber\": \"RT12234\"\r\n"+
			"}";
	
	public String fabRemitRequestTemplate = "{\r\n" + 
			"	\"transactionId\" : \"X1234\",\r\n" + 
			"	\"senderAccountNumber\" : \"12345678901\",\r\n" + 
			"	\"transactionCurrency\": \"AED\"\r\n" + 
			"	\"transactionAmount\": \"100\"\r\n"+
			"	\"nostroAccount\" : \"23456789012\",\r\n" + 
			"	\"exchangeRate\" : \"{\r\n" + 
			"	\"fromCurrency\": \"AED\"\r\n" + 
			"	\"fromAmount\": \"100\"\r\n"+
			"	\"toCurrency\" : \"INR\",\r\n" + 
			"	\"toAmount\" : \"1870\",\r\n" + 
			"	\"conversionRate\": \"0.053475\"\r\n" + 
			"}"+
			"	\"beneficiaryDetails\" : \"{\r\n" + 
			"	\"beneName\": \"Receiver Name\"\r\n" + 
			"	\"beneAddress\": \"New Delhi, India\"\r\n"+
			"	\"beneBankName\" : \"INR\",\r\n" + 
			"	\"beneBankIban\" : \"ICICI Bank\",\r\n" + 
			"	\"beneBankSwiftCode\": \"ICICINBBCTS\"\r\n" + 
			"}"+
			"}"; 
	
	public String errorJson= "{\r\n" + 
			"	\"status\" : \"422\",\r\n" + 
			"	\"statusDesc\" : \"Invalid Data or No Data Found\",\r\n" + 
			"	\"body\" : \"request\",\r\n" + 
			"}";
	
	public String requesPostingtJsonTemplate = "{\r\n" + 
			"	\"transactionId\" : \"X1234\",\r\n" + 
			"	\"transactionCurrency\": \"AED\"\r\n" + 
			"	\"beneficiaryDetails\" : \"{\r\n" + 
			"	\"beneName\": \"Receiver Name\"\r\n" + 
			"	\"beneAddress\": \"New Delhi, India\"\r\n"+
			"	\"beneBankName\" : \"INR\",\r\n" + 
			"	\"beneBankIban\" : \"ICICI Bank\",\r\n" + 
			"	\"beneBankSwiftCode\": \"ICICINBBCTS\"\r\n" + 
			"}"+
			"}"; 
	
	public String debitBankingResponseTemplate = "{\r\n" + 
			"	\"status\" : \"200\",\r\n" + 
			"	\"status\" : \"SUCCESS\",\r\n" + 
			"	\"correlationId\" : \"X1234\",\r\n" + 
			"	\"transactionId\": \"ID\"\r\n" + 
			"	\"coreBankingReferenceNumber\": \"RT12234\"\r\n"+
			"}";
	
    public String postingResponseTemplate= "{\r\n" + 
		"	\"status\" : \"SUCCESS\",\r\n" + 
		"	\"correlationId\" : \"X1234\",\r\n" + 
		"	\"transactionId\": \"ID\"\r\n" + 
		"}";
	
    public String requestBankReversalTemplate = "{\r\n" + 
			"	\"transactionId\" : \"X1234\",\r\n" + 
			"	\"debitReferenceNumber\":\"DEBIT-1234\"\r\n" + 
			"	\"beneficiaryDetails\" : \"{\r\n" + 
			"	\"beneName\": \"Receiver Name\"\r\n" + 
			"	\"beneAddress\": \"New Delhi, India\"\r\n"+
			"	\"beneBankName\" : \"INR\",\r\n" + 
			"	\"beneBankIban\" : \"ICICI Bank\",\r\n" + 
			"	\"beneBankSwiftCode\": \"ICICINBBCTS\"\r\n" + 
			"}"+
			"}"; 
	
	public String responseBankReversaltTemplate = "{\r\n" + 
			"	\"correlationId\" : \"X1234\",\r\n" + 
			"	\"transactionId\": \"ID\"\r\n" + 
			"	\"reversalReferenceNumber\": \"RT12234\"\r\n"+
			"}";
	 public String requestOperationsTemplate = "{\r\n" + 
				"	\"transactionId\" : \"X1234\",\r\n" + 
				"	\"dbCrudOperation\":\"INSERT\"\r\n" + 
				"	\"checkPoint\": \"AML\"\r\n" + 
				"	\"query\": \"insert into remittance(correlationId, checkPoint, status) values('X1234', 'AML', 'SUCCESS')\"\r\n"+
				"}"; 
	
	@PostMapping(value="/fab/remit/service", produces="application/json")
	public ResponseEntity<?> fabRemitService(@RequestBody String request) throws Exception{
		request=fabRemitRequestTemplate;
		ResponseEntity<String> response=null;	
		if(null == request || request.trim().length() < 1){
			response = new ResponseEntity<String>(errorJson, HttpStatus.UNPROCESSABLE_ENTITY); 
		}
		else{
		
			UUID uuid = UUID.randomUUID();
			String transactionId = uuid.toString();
			fabRemitResponseTemplate=fabRemitResponseTemplate.replace("ID", transactionId);
			
			response = new ResponseEntity<String>(fabRemitResponseTemplate, HttpStatus.OK);
		}
		
		return response;
	}
	
	@PostMapping(value="/debit/banking", produces="application/json")
	public ResponseEntity<?> debitBanking(@RequestBody String request) throws Exception{
		request=fabRemitRequestTemplate;
		ResponseEntity<String> response=null;	
		if(null == request || request.trim().length() < 1){
			response = new ResponseEntity<String>(errorJson, HttpStatus.UNPROCESSABLE_ENTITY); 
		}
		else{
		
			UUID uuid = UUID.randomUUID();
			String transactionId = uuid.toString();
			debitBankingResponseTemplate=debitBankingResponseTemplate.replace("ID", transactionId);
			
			response = new ResponseEntity<String>(debitBankingResponseTemplate, HttpStatus.OK);
		}
		
		return response;
	}
	
	@PostMapping(value="/bank/posting", produces="application/json")
	public ResponseEntity<?> bankPostingService(@RequestBody String request) throws Exception{
		request=requesPostingtJsonTemplate;
		ResponseEntity<String> response=null;	
		if(null == request || request.trim().length() < 1){
			response = new ResponseEntity<String>(errorJson, HttpStatus.UNPROCESSABLE_ENTITY); 
		}
		else{
		
			UUID uuid = UUID.randomUUID();
			String transactionId = uuid.toString();
			postingResponseTemplate=postingResponseTemplate.replace("ID", transactionId);
			
			response = new ResponseEntity<String>(postingResponseTemplate, HttpStatus.OK);
		}
		
		return response;
	}
	
	
	@PostMapping(value="/bank/reversal", produces="application/json")
	public ResponseEntity<?> bankReversalService(@RequestBody String request) throws Exception{
		request=requestBankReversalTemplate;
		ResponseEntity<String> response=null;	
		if(null == request || request.trim().length() < 1){
			response = new ResponseEntity<String>(errorJson, HttpStatus.UNPROCESSABLE_ENTITY); 
		}
		else{
		
			UUID uuid = UUID.randomUUID();
			String transactionId = uuid.toString();
			responseBankReversaltTemplate=responseBankReversaltTemplate.replace("ID", transactionId);
			
			response = new ResponseEntity<String>(responseBankReversaltTemplate, HttpStatus.OK);
		}
		
		return response;
	}
	
	@PostMapping(value="/bank/operations", produces="application/json")
	public ResponseEntity<?> bankOperations(@RequestBody String request) throws Exception{
		request=requestOperationsTemplate;
		ResponseEntity<String> response=null;	
		if(null == request || request.trim().length() < 1){
			response = new ResponseEntity<String>(errorJson, HttpStatus.UNPROCESSABLE_ENTITY); 
		}
		else{
		
			UUID uuid = UUID.randomUUID();
			String transactionId = uuid.toString();
			postingResponseTemplate=postingResponseTemplate.replace("ID", transactionId);
			
			response = new ResponseEntity<String>(postingResponseTemplate, HttpStatus.OK);
		}
		
		return response;
	}

}

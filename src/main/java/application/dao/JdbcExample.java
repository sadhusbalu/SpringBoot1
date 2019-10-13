package application.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class JdbcExample
{

    public String getAccountBalance(String SSN) throws Exception{
		String accountBal = null;
		
		System.out.println("1. Connect to the database");

	    Properties connectionProps = new Properties();
	    connectionProps.put("user", "sadhu");
	    connectionProps.put("password", "sadhu07s");
	    Class.forName("oracle.jdbc.driver.OracleDriver");

    	try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@13.72.66.30:1521:cdb1", connectionProps);){
			
		    System.out.println("2. Auto Commit off!!!" + conn);
		    conn.setAutoCommit(false);
		    System.out.println("4. Get a Column of the first Row - access column by Position:");

		    PreparedStatement pStmt = conn.prepareStatement("select * from employee e, account a where a.account_id = e.account_id and e.SSN=?");
		    pStmt.setString(1, SSN);
		    ResultSet rset = pStmt.executeQuery();
		    rset.next();
		    accountBal = rset.getString("account_bal");
		    System.out.println("    Employee Name  : " + rset.getString("emp_name"));
		    System.out.println("    account Name: " + rset.getString("account_id"));
		    System.out.println("    accountBal  -- "+ accountBal);

      } catch (Exception e) {
		e.printStackTrace();
      } 
      return accountBal;
    }

}
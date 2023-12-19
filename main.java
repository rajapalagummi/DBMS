import java.sql.*;

public class main {

    public static void main(String[] args) {
        Connection conn = null;// Declaring Connection variable outside try-catch block
        Statement stmnt = null;// Declaring Statement variable outside try-catch block

        try {
        	//Load the JDBC driver into the code 
            Class.forName("org.postgresql.Driver");
            //using connection , create a connection with database 
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres","Palagummi@119");
            
            //for Atomicity - set the auto commit property to false so we can commit the whole transaction or rollback if any error occurs in the transaction.
            conn.setAutoCommit(false);
            //for isolation we set the Transaction isolation mode to serializable which is the highest level of isolation
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            //lets define the statement using connection
            stmnt = conn.createStatement();
            // Your code for database operations here
            
            /*The previous transactions (Tr-1, Tr-2, Tr-3 and Tr-4) are below
          //TRANSACTION 1
            //  String updateString= "ALTER TABLE stock ADD CONSTRAINT fk_PROD_ID FOREIGN KEY (prod_id) REFERENCES product(prod_id) ON DELETE CASCADE;";
              String updateString1 = "DELETE FROM product WHERE prod_id='p1'";
              stmt1.executeUpdate(updateString1);
   
              
              //TRANSACTION 2
             // String updateString= "ALTER TABLE stock ADD CONSTRAINT fk_depid FOREIGN KEY (dep_id) REFERENCES depot(dep_id) ON DELETE CASCADE;";
              String updateString2= "DELETE FROM depot WHERE dep_id='d1'";
              stmt1.executeUpdate(updateString2);*/
            
            /*TRANSACTION 3
            String updatestr= "UPDATE product SET prod_id='pp1' WHERE prod_id='p1'";
            stmt1.executeUpdate(updatestr);
            
          //TRANSACTION 4
            String updatestr2= "UPDATE depot SET dep_id='dd1' WHERE dep_id='d1'";
           
            stmt1.executeUpdate(updatestr2);*/
           
           
            
          //TRANSACTION 5
            String updateString5= "INSERT INTO product(prod_id,pname,price) VALUES('P100','CD',5)";
            String updateString6="INSERT INTO stock(prod_id,dep_id,quantity) VALUES('P100','D2',50)";
            /*Inserting a product with the given values into the tables Products and stock*/
            stmnt.executeUpdate(updateString5);
            stmnt.executeUpdate(updateString6);
            //Executed the updates
            
          //TRANSACTION 6
        
            String updateString7= "INSERT INTO depot(dep_id,address,volume) VALUES('D100','Chicago',100)";
            String updateString8="INSERT INTO stock(prod_id,dep_id,quantity) VALUES('P1','D100',100)";
            /* Inserting details into depot table with the above given values and simultaneously updating stock table with the same values*/
            stmnt.executeUpdate(updateString7);
            stmnt.executeUpdate(updateString8);
            //Executed the updates
            
            
            //if the transaction occurs successfully then commit the updates to database
            conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("An exception was thrown");
            //In case of an error in transaction the catch block is executed which rollbacks the transaction implementation.
            try {
            	//there are chances for some reason the connection has not been established or failed , 
            	//so we check the that its not null before closing or rollback 
                if (conn != null) {
                    conn.rollback();
                }
                //similarly to the connection, if for some reason the statement was not created and failed, we check the null property before closing it
                if (stmnt != null) {
                	stmnt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (stmnt != null) {
                	stmnt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

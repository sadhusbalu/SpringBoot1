Create MSSQL sample database
------------------------------------
USE master
GO
IF DB_ID(‘ecommerce’) IS NOT NULL
    DROP DATABASE [ecommerce1]
GO
CREATE DATABASE [ecommerce1];
GO

Create a Test Table
--------------------
In TestDb, create a dedicated test table named CustomerInfo
Use ecommerce1
CREATE TABLE Customer (  
CustomerId        INT IDENTITY(10000,1)    NOT NULL PRIMARY KEY,
FirstName varchar (50) NOT NULL,  
LastName varchar (50),  
DOB DATE NOT NULL,  
SSN varbinary(128) NOT NULL,
PWD varbinary(256) NOT NULL 
)  
GO 

Create Certificate and Symmetric Keys:
--------------------------------------
CREATE CERTIFICATE MyCertificate  
   WITH SUBJECT = 'Customer Social Security';  
GO
CREATE SYMMETRIC KEY SSN_Key_01  
    WITH ALGORITHM = AES_256  
    ENCRYPTION BY CERTIFICATE MyCertificate;  
GO


Testing with a PL / SQL Block:
------------------------------
DECLARE @var1 VARBINARY(8000), 
@var2 VARBINARY(8000), 
@Val1 VARCHAR(50),
@Val2 VARCHAR(50);
SELECT @Val1 = '123456789';
SELECT @Val2 = 'regular password';
OPEN SYMMETRIC KEY SSN_Key_01 DECRYPTION BY CERTIFICATE MyCertificate;
SELECT @var1 = EncryptByKey(Key_GUID('SSN_Key_01'), @Val1);
SELECT @var2 = EncryptByKey(Key_GUID('SSN_Key_01'), @Val2);
SELECT CAST(DecryptByKey(@var1) AS VARCHAR(50)), @Val1;
SELECT CAST(DecryptByKey(@var2) AS VARCHAR(50)), @Val2;
INSERT INTO Customer (FirstName, LastName, DOB, SSN, PWD) 
    VALUES( 'Chris', 'Jack', '10/05/1981', @var1, @var2);

Insert Query to MS-SQL with encrypted Column:
---------------------------------------------
OPEN SYMMETRIC KEY SSN_Key_01 DECRYPTION BY CERTIFICATE MyCertificate;
INSERT INTO Customer (FirstName, LastName, DOB, SSN, PWD) 
    VALUES( 'Chris', 'Jack', '10/05/1981', 
    EncryptByKey(Key_GUID('SSN_Key_01'), '0123456789'), 
    EncryptByKey(Key_GUID('SSN_Key_01'), 'my password'));  


Select Query - Type 1:
----------------------
OPEN SYMMETRIC KEY SSN_Key_01 DECRYPTION BY CERTIFICATE MyCertificate;
SELECT @var1 = EncryptByKey(Key_GUID('SSN_Key_01'), @Val1);
SELECT @var2 = EncryptByKey(Key_GUID('SSN_Key_01'), @Val2);
SELECT CAST(DecryptByKey(@var1) AS VARCHAR(50)), @Val1;
SELECT CAST(DecryptByKey(@var2) AS VARCHAR(50)), @Val2;

  
Select Query - Type 2:
----------------------
select * from customer;

OPEN SYMMETRIC KEY SSN_Key_01 DECRYPTION BY CERTIFICATE MyCertificate;
SELECT SSN, CONVERT(VARCHAR(50), DecryptByKey(SSN)),
PWD, CONVERT(VARCHAR(50), DecryptByKey(PWD))
 AS DecryptedVal FROM Customer;


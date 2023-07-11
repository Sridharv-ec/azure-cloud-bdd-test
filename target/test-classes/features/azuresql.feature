@cloudSql
Feature: Azure MySql feature file

  Scenario: Azure MySql CRUD operation
     Given Insert into MySql DB using "INSERT INTO user (id, username,password) VALUES (2, 'Paul Adam','Password321')"
#     When Query MySql DB and fetch "username" using SQL "SELECT * FROM user"
#     Then Update data in MySql DB using "UPDATE user SET username = 'Steve Smith' WHERE id = 2"
#     And Delete data from MySql DB using "DELETE FROM user WHERE id=2"
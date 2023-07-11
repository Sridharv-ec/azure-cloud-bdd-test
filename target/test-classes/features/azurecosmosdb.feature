@cloudCosmosDB
Feature: Azure cloud cosmos db feature file

  Scenario: Azure cloud cosmos db
    Given Create azure cosmos DB "myfirstCosmosDB"
    When Create azure cosmos container "myfirstContainer01" in the cosmos DB "myfirstCosmosDB"
    When Write data into the cosmos db "myfirstCosmosDB" of conainer "myfirstContainer01"
      | id  | partitionKey | firstName | lastName  | city     | country |
      | 001 | Part1        | John      | Wills     | London   | UK      |
      | 002 | Par2         | Luke      | Willshare | New York | US      |
      | 003 | Par3         | Mark      | Wood      | Mumbai   | India   |

     When Read data for item "001" from azure cosmos DB "myfirstCosmosDB" of cosmos container "myfirstContainer01"


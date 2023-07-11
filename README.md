<!--
*** Thanks for checking out the best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]




<!-- PROJECT LOGO -->
<br />
<p align="center">

  <h3 align="center">About Azure Cloud testing accelerator</h3>
  <p align="center">
    BDD testing framework for cloud services
  </p>


<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About Azure cloud testing accelerator</a>
      <ul>
        <li><a href="#built-with">Built with</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About Azure cloud testing accelerator


 
The cloud testing accelerator is an automation framework used to test Cloud  (Azure cloud) applications, processes and services using open source. We have included all major cloud capabilities and services needed for testing as generic BDD functions .

Any team can clone the accelerator and get started with cloud automation without he need to write code or devote effort in automation setup - this will save time and effort for the team.
 
### Major capabilities provided by tool:
* BDD Based framework - Business-friendly test scenarios which are easy to review and collaborate
* Generic methods – All Azure cloud services and application are exposed as generic functions and all projects specify details setup via config files
* Environment agnostic : Framework is env agnostic i.e. We can run tests on different env without code changes  
* Cucumber reporting Integrated – For easy test execution reporting and result sharing
* Test data management : Test data files are maintained in framework independent json files for easy change and maintenance
* Documentation and setup guide – Framework is accompanied by documentation and setup guide for easy of setup and use

### Built with

This section should list any major frameworks that you built your project with. Leave any add-ons/plugins for the acknowledgements section. Here are a few examples.
* [Java](https://www.java.com/en/)
* [Azure cloud](https://portal.azure.com/)
* [Cucumber](https://cucumber.io/)



<!-- GETTING STARTED -->
## Getting started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* Java : Get started by downloading Java -  https://www.java.com/en/download/
* Maven : Install Maven - https://maven.apache.org/install.html

### Installation

1. Clone the repo
   ```sh
   Yet to put the git repo
   ```
2. Install project dependencies
   ```sh
   mvn clean install
   ```
3. Update project specific configuration in properties file (src/test/resources/profile/sandbox/environment.properties)

   
4. Start all test cases execution by executing below command 
   ```sh
   mvn -Dprofile=profile/sandbox integration-test
   ```
5. Start specify test cases execution by tag using below command 
   ```sh
      mvn -Dprofile=profile/sandbox -Dcucumber.options="--tags @cloud" integration-test
   ```
<!-- USAGE EXAMPLES -->
## Usage

Environment agnostic: create properties file under src/test/resources/profile folder with environment name you want to use in run command i.e for int env create folder named int and use below run commands to fetch all configuration for int env

 ```sh
   mvn -Dprofile=profile/int integration-test
   ```
   
   
### Cucumber steps for different services:

BDD steps for Azure MySql:
```sh
 Given Query MySql DB and fetch "colunmnName" using SQL "select sql query"
 When Insert into MySql DB using "insert query"
 Then Update data in MySql DB using "Update query"
 And Delete data from MySql DB using "Delete query"
```
BDD steps for PostgresSql:
```
 Given Query postgresSql DB and fetch "colunmnName" using SQL "select sql query"
 When Insert into postgresSql DB using "insert query"
 Then Update data in postgresSql DB using "Update query"
 And Delete data from postgresSql DB using "Delete query"
```
BDD steps for storage account:
```
Steps for Azure cloud storage account for blob object
 
 Given Create azure storage container "containerName"
 When Upload "file name to be updated" blob Object into "containerName"
 Then download blob object "file name to be downloaded" from the "containerName"
 And delete blob container "containerName"
 
Steps for Azure cloud storage account for File share
  Given Create azure file share "fileShareName"
  When download file "file name to be downloaded" from the "fileShareName" file share
  Then delete file "file name to be deleted" from "fileShareName" file share
  And create directory "Directory name" in the "fileShareName" file share
  And delete directory "Directory name" in the "fileShareName" file share
  And Upload "file name to be updated" file into "fileShareName" file share
```

BDD steps for azure cosmos db:
```
 Given Create azure cosmos DB "comos-db-name"
 When Create azure cosmos container "cosmos-container-name" in the cosmos DB "comos-db-name"
 When Write data into the cosmos db "comos-db-name" of conainer "cosmos-container-name"
   | id  | partitionKey | firstName | lastName  | city     | country |
   | 001 | Part1        | John      | Wills     | London   | UK      |
   | 002 | Par2         | Luke      | Willshare | New York | US      |
   | 003 | Par3         | Mark      | Wood      | Mumbai   | India   |

 When Read data for item "001" from azure cosmos DB "comos-db-name" of cosmos container "cosmos-container-name"

```

BDD steps for cogninitive service OpenAi feature file:
```
 Given do text completions for the promt "Say this is a test" for deployment id "my-gpt-turbo-deployment-01"
 And do chat completions for the promt for deployment id "my-gpt-turbo-deployment-01"
      | You are a helpful assistant. You will talk like a pirate. |
      | Can you help me?"                                         |
      | Of course, me hearty! What can I do for ye?               |
      | What's the best way to train a parrot?                    |
```

BDD steps for cogninitive service OpenAi feature file:
```
 Given do text completions for the promt "Say this is a test" for deployment id "my-gpt-turbo-deployment-01"
 And do chat completions for the promt for deployment id "my-gpt-turbo-deployment-01"
      | You are a helpful assistant. You will talk like a pirate. |
      | Can you help me?"                                         |
      | Of course, me hearty! What can I do for ye?               |
      | What's the best way to train a parrot?                    |
```

BDD steps for Azure cogninitive service Speech to text feature file
```
  Scenario: Azure congnitive Speech to text
    Given convert speech to text
    And convert text "Hello AI world!" to speech
```

BDD steps for azure service bus:
```
 When Send the message in the queue "service bus queue name"
  |first message|
  |second message|
  |third message|
 Then receive message from the queue "service bus queue name"
```

BDD steps for Cloud Monitoring logs:
```
Given Query container CPU for column "Azure resource or service name to get the logs"
```

BDD steps for cogninitive service computer vision file:
```
Given retrieve the information about the image "myImage.jpg"
```

BDD steps for cogninitive service form recognizer feature file:
```
Given analyze document layout for the file "Form_1.jpg"
```

<!-- ROADMAP -->
## Roadmap



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.



<!-- CONTACT -->
## Contact

Sridhar Vuttarkar - sridhar.vuttarkar@publicissapient.com




<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/othneildrew/Best-README-Template/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/othneildrew/Best-README-Template/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/othneildrew/Best-README-Template/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/othneildrew/Best-README-Template/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/othneildrew
[product-screenshot]: images/screenshot.png

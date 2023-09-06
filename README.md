
# Spring Batch demo

This is a spring batch demo project.


## Authors

- [@marioskaff](https://github.com/marioskaff)


## Run Locally

Clone the project

```bash
  git clone https://github.com/marioskaff/spring-batch-demo.git
```
Import the project to your favorite IDE and import maven dependencies

Clean install the project

```bash
  mvn clean install
```

Run the project in your IDE using the following VM options

```bash
  # replace job1 by the name of the job you want to launch
  -Dspring.batch.job.name=job1
```

Access the database to check the data after the job execution
```bash
  http://localhost:8080/h2-console
```

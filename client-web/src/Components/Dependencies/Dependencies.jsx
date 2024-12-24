import React, { useState } from "react";
import styles from "./Dependencies.module.css";

function Dependencies({ dependencies, onAddDependency }) {
  const allDependencies = [
    {
      id: 1,
      name: "GraalVM Native Support",
      description: "Support for compiling Spring applications to native executables using the GraalVM native-image compiler.",
      category: "Developer Tools",
      dependency: "native",
    },
    {
      id: 2,
      name: "Spring Web",
      description: "Build web, including RESTful, applications using Spring MVC. Includes Tomcat as the default embedded container.",
      category: "Web",
      dependency: "web",
    },
    {
      id: 3,
      name: "Spring Boot DevTools",
      description: "Provides fast application restarts, LiveReload, and configurations for enhanced development experience.",
      category: "Developer Tools",
      dependency: "devtools",
    },
    {
      id: 4,
      name: "Spring Security",
      description: "Highly customizable authentication and access-control framework for securing Spring applications.",
      category: "Security",
      dependency: "security",
    },
    {
      id: 5,
      name: "Spring Data JPA",
      description: "Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate.",
      category: "SQL",
      dependency: "data-jpa",
    },
    {
      id: 6,
      name: "Spring Data MongoDB",
      description: "Store and retrieve data in MongoDB using Spring Data MongoDB.",
      category: "NoSQL",
      dependency: "data-mongodb",
    },
    {
      id: 7,
      name: "Spring Batch",
      description: "Batch processing including scheduling and managing large-volume batch jobs.",
      category: "Data Processing",
      dependency: "batch",
    },
    {
      id: 8,
      name: "Spring Boot Actuator",
      description: "Add production-ready features like monitoring, metrics, and health checks to your application.",
      category: "Operations",
      dependency: "actuator",
    },
    {
      id: 9,
      name: "Spring Cloud Config Client",
      description: "Client support for externalized configuration in distributed systems using Spring Cloud Config.",
      category: "Cloud Config",
      dependency: "cloud-config-client",
    },
    {
      id: 10,
      name: "Spring Cloud Netflix Eureka",
      description: "Service discovery for microservices with Spring Cloud Netflix Eureka.",
      category: "Cloud Discovery",
      dependency: "cloud-eureka",
    },
    {
      id: 11,
      name: "Spring Kafka",
      description: "Apache Kafka support for event-driven architectures using Spring.",
      category: "Messaging",
      dependency: "kafka",
    },
    {
      id: 12,
      name: "Spring WebFlux",
      description: "Reactive web applications support using Spring WebFlux.",
      category: "Reactive",
      dependency: "webflux",
    },
    {
      id: 13,
      name: "Spring AMQP",
      description: "Advanced Message Queuing Protocol (AMQP) support using RabbitMQ.",
      category: "Messaging",
      dependency: "amqp",
    },
    {
      id: 14,
      name: "Spring Data Redis",
      description: "Store and retrieve data in Redis using Spring Data Redis.",
      category: "NoSQL",
      dependency: "data-redis",
    },
  ];


  const [searchTerm, setSearchTerm] = useState("");

  const filteredDependencies = allDependencies.filter((dep) =>
    dep.name.toLowerCase().includes(searchTerm.toLowerCase()) &&
    !dependencies.includes(dep.dependency)
  );

  return (
    <div className={styles.container}>
      <input
        type="text"
        placeholder="Web, Security, JPA, Actuator, Devtools..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        className={styles.searchBar}
      />

      <div className={styles.listContainer}>
        <ul className={styles.list}>
          {filteredDependencies.map((dep) => (
            <li
              key={dep.id}
              className={styles.listItem}
              onClick={() => onAddDependency(dep)}
            >
              <strong>{dep.name}</strong>
              <p>{dep.description}</p>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default Dependencies;

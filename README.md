# FHIR Search Prototype
This frankenstein prototype is hacked together from a tutorial and some hapi-fhir code. It isn't Springified and comes up short in many ways, but it's getting me back into hibernate and should serve limited purposes. It is all about various compromises, including time.
https://www.javawebtutor.com/articles/jpa/jpa-example-using-maven.php
I carved out some of the jpa entities from jpaserver-starter and clipped at convenient points to be able to use java code to do sql select/searches on tables configured by hapi-fhir-jpaserver.
I'm specifically interested in the TRM_CONCEPT and related tables to demonstrate a crude text search, in very short order.

THROW THIS CODE OUT AFTER THE DEMO. Or don't. It's small and simple that it may help debug and understand things in the larger HAPI-FHIR, JPA and hibernate worlds, but don't use it in production, or as a base for work that will be.  Learn from it and start over. 


Chris Roeder, Thanksgiving 2022

mvn clean package  exec:java

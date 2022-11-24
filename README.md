# FHIR Search Prototype
This frankenstein prototype is hacked together from a tutorial and some hapi-fhir code. It isn't Springified and comes up short in many ways, but it's getting me back into hibernate and should serve limited purposes. It is all about various compromises, including time.
https://www.javawebtutor.com/articles/jpa/jpa-example-using-maven.php
I carved out some of the jpa entities from jpaserver-starter and clipped at convenient points to be able to use java code to do sql select/searches on tables configured by hapi-fhir-jpaserver.
I'm specifically interested in the TRM_CONCEPT and related tables to demonstrate a crude text search, in very short order.

THROW THIS CODE OUT AFTER THE DEMO. Or don't. It's small and simple that it may help debug and understand things in the larger HAPI-FHIR, JPA and hibernate worlds, but don't use it in production, or as a base for work that will be.  Learn from it and start over. 

Chris Roeder, Thanksgiving 2022


This is going well. A very basic display search works on the command line. 
TODO
- REST API
- search designation (crude synonym) text
  - DO NOT need to go to synonym relationships, because this is text search and if the search string is in the label or designation, we'll find it directly. This is a really important point involving the distinction between a full-on synonym concept and an additional label for the samme concept, otherwise described as multiple AUIs for the same CUI.
  - look at the lang field in combination with the use_code. For data loaded into the server I'm on, there are very few en designations. 
  - might query the ConceptTermDesignation class primarily, then filter back via applicaiton joins?
- (simple done) join to codeSystem to print those details, not just IDs
    - After a simple query directly on TRM_CONCEPT, do an application join by querying each concept for its CodeSystemVersion. If you need to filter on the CodeSystem, you can do so after you've hauled them all out of the database on the CodeSystemDisplayName, or join again to the CodeSystem and look at the getName() there.
    - no doubt, more sophisticated ways exist using HQL or equivalent. TODO
- (done) show a search by code
- why doesn't the code exit?

Lot's of stones in pots for Stone Soup leaving lots of remaining 80% to do:
- The javascript hack needs to parse the returned list of objects, regardless of order, for specific fields instead of printing them all as JSON
- Obvy, the REST API needs written
- The queries need refinement.

This much is enough to show a simple and small UI that can be used to validate a code, and search for another or a better one by text.
<br>Then there's the big task left to Do It Right (tm): writing a very simple IG and doing this from within HAPI-FHIR along an amended(?) FHIR API.

And even then there is room for more: 
- implementing a Lucene index
- exploring the query languages in FHIR



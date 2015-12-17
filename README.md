# LinkStore
A little self-hosted application to store links.

##Why?
Because reasons.  Primarily I want to leanr ReactJS (or some other formidable UI library), but also becuase of the impracticalilites of bookmarks, upvotes, saved links etc.  I also do not like telling everyone else which links I like, so I'd rather just keep them for myself.

##Technical
Just an eclipse+maven project making use of servlets 3.0, spring 4 on the backend.  No it does not depend on package managers in package managers (node, bower, etc.) - just some js, html and css files for the front end.  I will eventually go through a more complex frontend development workflow, but not yet.

##Using it

```
git clone https://github.com/jamesfarrugia/LinkStore.git
cd LinkStore/LinkStore
mvn package
```

Get a recent tomcat or any other servlet container and deploy the war file to it.  You could complicate things and use deploy from maven, but this is a personal application not CERN.

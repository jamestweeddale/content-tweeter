# tweeter
Integrates Java based services and APIs in a loosely coupled manner to use as sources of content for tweets. Flexible design allows new services and ContentFetchers to be added easily to provide new sources of tweet content.

This implementation of ContentTweeter tweets random words and related images at specified intervals. It uses the Wordnik 
dictionary API as a source of random words, Google Image Search as a source of related images, and the Twitter4J API
to interface with Twitter.

An implementation of this project is currently running, tweeting to Twitter as <a href="https://twitter.com/RandomTweetPony" target="_BLANK">@RandomTweetPony</a>

Project and all dependencies are compiled into a single jar file using Maven. The resulting jar can bein run at command line by simply running 'java -jar jtweeter.jar' provided the application configuration file is completed and in place. 

Sample configuration file inluding some information for running an instance of the project is located in main/resources/sample-jtweeter-config.xml

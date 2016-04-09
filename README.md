# tweeter
Integrates Java based services and APIs in a loosely coupled manner to use as sources of content for tweets. New services and ContentFetchers can be added easily to provide new sources of tweet content.

Currently available source of tweet content include random dictionary words, Google Image search results, and Unix/Linux fortunes.

The following APIs, libraries, and utilities are utilized to interface with remote services:
- Twitter4J API for Twitter.
- Knicker API for Wordnik online dictionary
- Google Image Search
- Unix/Linux fortune command

An implementation of this project is currently running, tweeting to Twitter as <a href="https://twitter.com/RandomTweetPony" target="_BLANK">@RandomTweetPony</a>. This implementation of ContentTweeter tweets random words, related images, and Linux fortunes at specified intervals.

Project and all dependencies are compiled into a single jar file using Maven. The resulting jar can be run at command line by simply running 'java -jar tweeter.jar' provided the application configuration file is completed and in place. 

Sample configuration file including some information for running an instance of the project is located in main/resources/sample-tweeter-config.xml

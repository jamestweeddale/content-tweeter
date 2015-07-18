# tweeter
Integrates a collection of services and APIs (ContentFetchers) in a loosely coupled manner to use as sources of content for 
tweets. Flexible design of the ContentTweeter and ContentFetchStrategy subclasses allow new services and ContentFetchers to 
be added, even at runtime, to provide new sources of tweet content.

This implementation of ContentTweeter tweets random words and related images at specified intervals. It uses the Wordnik 
dictionary API as a source of random words, Google Image Search as a source of related images, and the Twitter4J API
to interface with Twitter.

An implementation of this project is currently running, tweeting to Twitter as @RandomTweetPony every hour.

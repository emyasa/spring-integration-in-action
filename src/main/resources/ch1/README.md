Visual Representation: Chapter 1 - Part 2 "Hello World"

![alt text](./Screen%20Shot%202020-09-03%20at%201.53.04%20AM.png)

Summary of this Chapter: 

Spring Integration's Architecture: 
    At its core, it's a messaging framework that supports lightweigh, event-driven interactions within an application. 

    On top of that core, it provides an adapter-based platform that supports flexible integration of applications across the enterprise. 

Spring Integration's support for Enterpise Integration Patterns 

"A message is passed through a dchannel from one endponit to another endpoint."

Message
    A message is a unit of information that can be passed between different components, called message endpoints.

    > header 
    > payload 

    Types of Messages
        * Command Message - tells the receiver to do something. 
        * Event Message - notifies the receiver 
        * Document Message - transfer some data from the sender to receiver.

Message Channel 
    The message channel is the connection between multiple endpoints. The channel implementation manages the details of how and where a message is delivered but shouldn't need to interact with the payload of a message. 

    It logically decouples producers from consumer. 

    > point to point
    > publish-subscribe 

Message Endpoints
    Message endpoint are the components that actually do something with the message. 

    examples: (not limited to the ff)
        * channel adapters
        * messaging gateways
        * service activators


Enterprise Integration Patterns meet Inversion of Control

    * Dependency Injection
    * Method Invocation

    Dependency Injection is the first thing most people think of when they hear inversion of control because it's probably the most common application of the principle and the core functionality of IoC frameworks like Spring

    Method Invocation - use of callbacks that are in place within the framework to use of somthing a bit simplier e.g. if your scheduling a task, instead of manually doing the scheduler a simplier approach is to declaratively register your task. 


# LocoDrive [![CodeQL](https://github.com/juhu1705/LocoDrive/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/juhu1705/LocoDrive/actions/workflows/codeql-analysis.yml) [![Gradle Package](https://github.com/juhu1705/LocoDrive/actions/workflows/gradle-publish.yml/badge.svg)](https://github.com/juhu1705/LocoDrive/actions/workflows/gradle-publish.yml)

A fully functional Java library for controlling a model train railroad based on a loco net system. The sending logic of this library is written in rust and embedded as native library in Java.

## Features
| Feature                          | Description                                                                                     | Status  |
|----------------------------------|-------------------------------------------------------------------------------------------------|---------|
| Sending control                  | Control of sending messages to loconet                                                          | DONE    |
| Receiving control                | Possibility to handle received messages                                                         | DONE    |
| Configuration of the connection  | Control over the configuration settings of the loco net connection like BaudRate or FlowControl | PLANNED |

## Importing the LocoDrive

For now there is no release of this software to be imported in other projects. So if you want to use this project, clone this repository locally and build it yourself. Please note that this repository is system dependent. So you need to build its rust native for every system you want to use.

## Using the LocoDrive

The LocoDrive has a class called LocoNetHandler. With this class you can open a connection to a new port and start a reader. This reader will trigger defined events, wich you can receive by listening a register for them in the EventManager. The triggered event will send back a ILocoNetMessage to the loco net if given. If you want to send a message yourself create a ILocoNetMessage and send it over the LocoNetHandler.send(ILocoNetMessage) method to the loco net. This may return false if the message could not be sent to the loco net.

## Committing to the LocoDrive

### Setting up the project

To set up the project yourself you need the clang library for building the rust binary file, also you need gradle and rust installed. An initialisation script will be added to this repository in near future.

### Commitment rules

To commit to this repository please consider the Contributing rules.

Please note: Always add me to your pull request to test your changes with a active loco net connection or add some test logs.

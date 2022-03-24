# LocoDrive [![Test](https://github.com/juhu1705/Locodrive/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/juhu1705/Locodrive/actions/workflows/codeql-analysis.yml) [![Release](https://github.com/juhu1705/Locodrive/actions/workflows/codeql-release.yml/badge.svg)](https://github.com/juhu1705/Locodrive/actions/workflows/codeql-release.yml)

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

Please note: Always add me to your pull request to test your changes with an active loco net connection or add some test logs.

## Used Dependencies

### Rust

| Dependency | Version    | License                                 |
|------------|------------|-----------------------------------------|
| log        | ^0.4.6     | MIT or Apache v2.0                      |
| serialport | 4.0.1      | MPL v2.0                                |
| condvar    | 0.1.1      | GPU 2.0                                 |
| dirs       | 4.0.0      | MIT or Apache v2.0                      |
| flapigen   | 0.6.0-pre7 | BSD 3-Clause "New" or "Revised" License |
| bindgen    | 0.57.0     | BSD 3-Clause "New" or "Revised" License |

### Java

| Dependency                 | Version | License     |
|----------------------------|---------|-------------|
| JetBrains Java Annotations | 20.1.0  | Apache v2.0 |
| noisruker event-manager    | 1.1.0   | GPL v3.0    |
| noisruker logger           | 1.0.3   | GPL v3.0    |

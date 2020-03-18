# AlexaIntegration

AlexaIntegration is a java project that facilitates the use of Amazon's virtual assistant
Alexa and allows verbal execution of commands spoken to Alexa to execute on your device. 

## Requirements

```bash
Dropbox account
Java
Amazon Alexa hardware
IFTTT 
```

## Usage

The functionality of this program has to live on a Dropbox server and have the dropbox application downloaded and installed locally on your device. The AlexaIntegration.java file will run and constantly scan for a change in the last modified date and time of the AlexaControl.txt file. Once the Java program recognizes the change, it will read the last line of text on the file (of which the file has been appended to via IFTTT) and execute the command via the command prompt or terminal. 

## Structure
Run Java file --> Amazon Alexa device command --> IFTTT applet --> Dropbox appends file --> Java reads file --> Command Prompt/Terminal executes command.

It is vital that the structure of your environment exists on dropbox. This program is functional for both Mac OS and Windows OS. 
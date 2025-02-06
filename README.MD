# JDBG



![](https://i.imgur.com/DcwK9oG.png)



![](https://img.shields.io/github/stars/roger1337/JDBG.svg) ![](https://img.shields.io/github/forks/roger1337/JDBG.svg) ![](https://img.shields.io/github/tag/pandao/roger1337/JDBG.svg) ![](https://img.shields.io/github/release/roger1337/JDBG.svg) ![](https://img.shields.io/github/issues/roger1337/JDBG.svg) 



## Features

- Bypass any conventional java agent restrictions such as -XX:+DisableAttachMechanism, leveraging an injected dll and native interfaces JNI and JVMTI
- Class Analysis
  - Analyse decompiled classes at runtime
  - Analyse method bytecode and field definitions at runtime
  - Add classes to object analysis
  - Planned Features
     - Setting breakpoints to decompiled code and bytecode
        - Ability to add stack locals to object analysis
     - bytecode instrumentation and class redefinitions
    - static variable modification
- Object Analysis
  - Add classes to object workspace
  - View all instances of a class
  - Apply filters using the Exprtk C++ library to obtain relevant objects
     - Filters support recursive searches, e.g. obj.field1.field2 > 3 or 'hello' in obj.field1
     - Planned support for arrays
  - Observe object values (such as strings) and fields
  - Generate graphs based on the heap relationships of objects
     - Obtain pointer paths to specific objects, useful in determining classes from which they originate (especially for strings)
  - Planned features
     - Set field values


## How to use

To use JDBG, you must have a JVM running Java 17 or higher.

### Releases

You can download and run it in the releases tab of this project.

### Building it yourself

The java client is built in Intellij while the dll is built in Visual Studio

To build the dll, you must add the /includes of your jdk to the include path (For things like jni.h and jvmti.h)
You must also have the boost library for C++ installed.

You must add the dll to the /assets folder of the client
Furthermore, you must build the JDBG 32bit Helper executable and add it to the assets folder of the client.
- The reason for this is that we are unable to get the proper base address of kernel32.dll and LoadLibrary for 32 bit applicatons from 64bit in usermode.
















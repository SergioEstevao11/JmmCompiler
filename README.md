# Compilers Project

For this project, you need to install [Java](https://jdk.java.net/), [Gradle](https://gradle.org/install/), and [Git](https://git-scm.com/downloads/) (and optionally, a [Git GUI client](https://git-scm.com/downloads/guis), such as TortoiseGit or GitHub Desktop). Please check the [compatibility matrix](https://docs.gradle.org/current/userguide/compatibility.html) for Java and Gradle versions.


## GROUP: 9G

| Name     | UP | Contribution|
| ----------- | ----------- |----------- |
| André Santos   | up201907879   | 25%       |
| João Andrade   | up201905589        |25%|
| Sérgio Estêvão      | up201905680       |  25%|
| Sofia Germer   | up201907461        |25%|

## SUMMARY: 
This project's main goal was to acquire and apply the theoretical principals of the Compilers' course unit. This was achieved by building a compiler for programs written in the Java-- language. The main parts of the project are Syntactic error controller, Semantic analysis, OLLIR and Jasmin Code generation.


## SEMANTIC ANALYSIS: 

### Type Verification

**The following verifications are implemented:**

- The operations are performed with the same type (e.g. int + boolean); 

- Cannot use arrays directly for arithmetic operation (e.g. array1 + array2);

- An array access can only be made over an array (e.g. 1[10] is not valid);

- The index of the access array is an integer (e.g. a [true] is not allowed);

- Length method can only be used over arrays;

- The assignee's value is the same as the assigned's (e.g. a_int = b_boolean is not allowed);

- A boolean operation is performed only with booleans;

- Conditional expressions result in a boolean;

- Assumes Parameters as Initialized;

- New types allowed are from the class, superclass or imports.

### Method Verification 

**The following verifications are implemented:**

- The "target" of the method exists and contains the method (e.g. a.foo, see if 'a' exists and if it has a 'foo' method);

- The number of arguments in the invocation is equal to the number of parameters in the declaration;

- The type of the parameters matches the type of the arguments;

- The return type matches the method declaration return type;

- The method is either imported, belongs to the class or its superclass;

- When this invocation of the method is used, check if the method belongs to the class or there is a superclass;

- Allows overload of methods.


## CODE GENERATION: 
(describe how the code generation of your tool works and identify the possible problems your tool has regarding code generation.)

## PROS: 
The highlights of our project are:
- Organized code
- Complete and detailed AST
- Robust semantic analysis

## CONS:
Sadly we didn't get to implement all the optimizations, which would have made this an even better project.

## Project setup

There are three important subfolders inside the main folder. First, inside the subfolder named ``javacc`` you will find the initial grammar definition. Then, inside the subfolder named ``src`` you will find the entry point of the application. Finally, the subfolder named ``tutorial`` contains code solutions for each step of the tutorial. JavaCC21 will generate code inside the subfolder ``generated``.

## Compile and Running

To compile and install the program, run ``gradle installDist``. This will compile your classes and create a launcher script in the folder ``./build/install/comp2022-00/bin``. For convenience, there are two script files, one for Windows (``comp2022-00.bat``) and another for Linux (``comp2022-00``), in the root folder, that call tihs launcher script.

After compilation, a series of tests will be automatically executed. The build will stop if any test fails. Whenever you want to ignore the tests and build the program anyway, you can call Gradle with the flag ``-x test``.

## Test

To test the program, run ``gradle test``. This will execute the build, and run the JUnit tests in the ``test`` folder. If you want to see output printed during the tests, use the flag ``-i`` (i.e., ``gradle test -i``).
You can also see a test report by opening ``./build/reports/tests/test/index.html``.

## Checkpoint 1
For the first checkpoint the following is required:

1. Convert the provided e-BNF grammar into JavaCC grammar format in a .jj file
2. Resolve grammar conflicts, preferably with lookaheads no greater than 2
3. Include missing information in nodes (i.e. tree annotation). E.g. include the operation type in the operation node.
4. Generate a JSON from the AST

### JavaCC to JSON
To help converting the JavaCC nodes into a JSON format, we included in this project the JmmNode interface, which can be seen in ``src-lib/pt/up/fe/comp/jmm/ast/JmmNode.java``. The idea is for you to use this interface along with the Node class that is automatically generated by JavaCC (which can be seen in ``generated``). Then, one can easily convert the JmmNode into a JSON string by invoking the method JmmNode.toJson().

Please check the JavaCC tutorial to see an example of how the interface can be implemented.

### Reports
We also included in this project the class ``src-lib/pt/up/fe/comp/jmm/report/Report.java``. This class is used to generate important reports, including error and warning messages, but also can be used to include debugging and logging information. E.g. When you want to generate an error, create a new Report with the ``Error`` type and provide the stage in which the error occurred.


### Parser Interface

We have included the interface ``src-lib/pt/up/fe/comp/jmm/parser/JmmParser.java``, which you should implement in a class that has a constructor with no parameters (please check ``src/pt/up/fe/comp/CalculatorParser.java`` for an example). This class will be used to test your parser. The interface has a single method, ``parse``, which receives a String with the code to parse, and returns a JmmParserResult instance. This instance contains the root node of your AST, as well as a List of Report instances that you collected during parsing.

To configure the name of the class that implements the JmmParser interface, use the file ``config.properties``.

### Compilation Stages 

The project is divided in four compilation stages, that you will be developing during the semester. The stages are Parser, Analysis, Optimization and Backend, and for each of these stages there is a corresponding Java interface that you will have to implement (e.g. for the Parser stage, you have to implement the interface JmmParser).


### config.properties

The testing framework, which uses the class TestUtils located in ``src-lib/pt/up/fe/comp``, has methods to test each of the four compilation stages (e.g., ``TestUtils.parse()`` for testing the Parser stage). 

In order for the test class to find your implementations for the stages, it uses the file ``config.properties`` that is in root of your repository. It has four fields, one for each stage (i.e. ``ParserClass``, ``AnalysisClass``, ``OptimizationClass``, ``BackendClass``), and initially it only has one value, ``pt.up.fe.comp.SimpleParser``, associated with the first stage.

During the development of your compiler you will update this file in order to setup the classes that implement each of the compilation stages.

## Final Delivery
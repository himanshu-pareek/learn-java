# Signing code and granting it permissions

## Steps for the Code Signer

**Susan Jones** is the one creating application, signing it and sending it to **Ray**.

1. Download and try the sample application
2. Create JAR file containing the class file, using the `jar` tool
3. Generate **keys**, if the don't already exist, using the `keytool -genkey` command
4. [OPTIONAL] Get Certificate from Certificate Authority
5. Sign the JAR file, using the `jarsigner` tools and the private key
6. Export the public key certificate, using the `keytool -export` command
7. Supply the signed JAR file and the certificate to the receiver (Ray, in this case)

## Download and Try the Sample Application

1. Download [Count.java](https://docs.oracle.com/javase/tutorial/security/toolsign/examples/Count.java) file and place it in `Test` directory.
2. Create a file named `data` inside `TestData` directory.
3. Compile `Count.java` file using `javac` command.
4. Run `Count` class using `java` command with `TestData/data` file.

You can run the `step1.sh` file to complete all the commands automatically.

## Create a JAR File Containing the Class File

1. Create a jar file called `Count.jar` and put the file `Count.class` inside it.

You can run the `step2.sh` file to complete all the commands automatically.

## Generate Keys

1. Create a *keystore*, named `examplestore` and a *key*, named `signFiles` using `keytool`.

Run `step3.sh` file to do that for you. You will be prompted to enter `storepass` and `keypass`. You will also get an option to have the `keypass` same as `storepass`.

## Sign the JAR File

1. Sign the JAR file `Count.jar` inside `Test` directory, using the *private key* aliased by **signFiles** in keystore **examplestore** using the `jarsigner` command.

Run `step4.sh` file to do that for you. You will be prompted for keystore and privte key password.

## Export the Public Key Certificate

1. Copy the certificate corresponding to the *public key* related to the *private key* used to sign the JAR file from *keystore*.

Run `step5.sh` file to do that for you. You will be prompted for the password.

## Send Signed JAR and Public Key Certificate to Receiver

Now, send the signed JAR file (probably Test/sCount.jar file) and Public Key Certificate file (probably Test/Example.cer) to the receiver.

## Steps for the Code Receiver

**Ray** is the one, who receives the *Signed* JAR file along with Public Key Certificate. The JAR file contains class `Count.class`, which requests access to System Resources on system that it normally would not have permission to access.

1. Observe the restricted application. This application would not be able to access system resources until we import Susan's certificate and create a policy file.
2. Import Susan's certificate as a trusted certificate using `keytool -import` command.
3. Setup a policy file to grant permission.
4. Test reconfigured **Count** application.

### Observe the Restricted Application

An application can be run under a security manager by invoking the interpreter with the `-Djava.security.manager` command-line argument. But, what if the application to be invoked resides inside a JAR file?

Use `-cp` to specify a search path for application classes and resources.

Run script `receiver1.sh` to run the application - first without security manage, then with scurity manager.

### Import the Certificate as a Trusted Certificate

We have received signed JAR file (`sCount.jar`) and Public Key Certificate (`Example.cer`) from **Susan**.

1. Create keystore for **Ray** with name `exampleraystore`.

2. Import the Susan's certificate into the keystore.

Run script `receiver2.sh` to do the above


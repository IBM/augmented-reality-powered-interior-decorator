#### 1. Problems with Java Version
* If you have java version different from `1.8.x` already installed in your Mac, then you can simply run this command to uninstall existing java version.
```
$ sudo rm -rf /Library/Java/JavaVirtualMachines/jdk<version>.jdk
```
* After uninstalling the existing version you can download java version `1.8.x` from [here](https://adoptopenjdk.net/?variant=openjdk8&jvmVariant=openj9). 
```
$ java -version
java version "1.8.0_101"
```

#### 2. Problems related to IBM Mobile Foundation Server
* During step 3.2 if you are unable to add server and if you get some message as shown bellow,
```
$ mfpdev server add
? Enter the name of the new server profile: MyServer
? Enter the fully qualified URL of this server: https://mobilefoundation-xxxx-xxxxxx.xx-xx.mybluemix.net:443
? Enter the Mobile Foundation Server administrator login ID: admin
? Enter the Mobile Foundation Server administrator password: ****
? Save the administrator password for this server?: No
? Enter the context root of the MobileFirst administration services: mfpadmin
? Enter the  Mobile Foundation Server connection timeout in seconds: 30
? Make this server the default?: No
Verifying server configuration...
Cannot connect to server 'MyServer' at 'https://mobilefoundation-xxxx-xxxxxx.xx-xx.mybluemix.net:443'.
Failed to set runtime details.: Unexpected token N in JSON at position 4: HTTP 404 - Not Found
Error: The 'mfpdev server add' command has failed.
```
probably the server is not yet running. Wait for sometime and try again. 

* If the problem still persists you can delete the IBM Mobile Foundation & IBM Mobile Foundation Server resource and create it once again.

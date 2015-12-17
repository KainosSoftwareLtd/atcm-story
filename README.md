[![Build Status](https://travis-ci.org/KainosSoftwareLtd/atcm-story.svg?branch=master)](https://travis-ci.org/KainosSoftwareLtd/atcm-story)

## Basic Instructions

Install Wildfly (use brew)

```
brew install wildfly-as
```

Make sure you have this in your bash profile
```
export JBOSS_HOME=/usr/local/opt/wildfly-as/libexec
export PATH=${PATH}:${JBOSS_HOME}/bin
```

If you are sane and use defaults you can do the following

```
./gradlew clean build

cp build/libs/atcm-shop-0.0.1-SNAPSHOT.war /usr/local/opt/wildfly-as/libexec/standalone/deployments
```

To run

```
cd /usr/local/opt/wildfly-as/libexec/bin
./standalone.sh
```
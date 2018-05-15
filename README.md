[![Build Status](https://travis-ci.com/m4tty-d/single-tasker.svg?token=u8qaYxSWoeJmov6MB6BK&branch=master)](https://travis-ci.com/m4tty-d/single-tasker)
[![codecov](https://codecov.io/gh/m4tty-d/single-tasker/branch/master/graph/badge.svg?token=6oD8GoQbqT)](https://codecov.io/gh/m4tty-d/single-tasker)
[![Java Version](https://img.shields.io/badge/jdk-8-blue.svg)](https://docs.oracle.com/javase/8/)
[![Maven](https://img.shields.io/badge/tool-maven-ec702f.svg)](https://maven.apache.org/)
[![jUnit](https://img.shields.io/badge/junit-jupiter-25a162.svg)](https://junit.org/junit5/)

Single Tasker
======

A time management app with the help of the pomodoro technique.

## Motivation

The project was created for the Programming Technologies and Programming Environments courses. (University of Debrecen 2017/2018)

## How to run 

You have to create the jars:

```bash
$ mvn package
```

After the above's completion you can run it:

```bash
$ java -jar ./target/single-tasker-1.0-jar-with-dependencies.jar
```

## Documentation

The documentation and reports can be generated with:

```bash
$ mvn site
```
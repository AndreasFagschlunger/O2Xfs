O²Xfs
=====

Java API for accessing CEN/XFS API, EMV Level 2 Kernel

## Code Status

[![Build Status](https://api.travis-ci.org/AndreasFagschlunger/O2Xfs.svg?branch=develop)](https://travis-ci.org/AndreasFagschlunger/O2Xfs/)
[![Build Status](https://ci.appveyor.com/api/projects/status/github/AndreasFagschlunger/o2xfs)](https://ci.appveyor.com/project/AndreasFagschlunger/o2xfs)

## at.o2xfs.common
This project contains common utility classes.

## at.o2xfs.log
This project provides API for logging.

## at.o2xfs.win32
This Project provides API for accessing native Windows-Types over JNI.

## at.o2xfs.xfs
This Project is a Wrapper-Project for the CEN/XFS-API. Native C-Constants are defined as enums etc. It uses at.o2xfs.win32 to access the native data types and structures.

## at.o2xfs.xfs.service
This project provides a simpler API to use CEN/XFS. Basically there are Classes to execute CEN/XFS commands and you don't have to deal with details such as XFS StartUp, Open Services, Event-Handling, etc.

## at.o2xfs.operator
This project is a application which provides a GUI for executing and querying CEN/XFS-Commands.

## at.o2xfs.emv
This project provides API for performing an EMV transaction. Its pure Java and indepentend of the CEN/XFS projects.

## at.o2xfs.emv.demo
This project demonstrates the use of the at.o2xfs.emv project.

## at.o2xfs.ctapi
This Project is a Java-Wrapper for the native CT-API, which is a common API to access Smart Card Readers.

## at.o2xfs.emv.pinpad.impl.xfs
This project provides a PIN-Pad implementation for the PINPad-Interface of the at.o2xfs.emv project using CEN/XFS.

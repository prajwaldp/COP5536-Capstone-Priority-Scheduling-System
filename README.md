# UF Advanced Data Structures (COP5536) Capstone Project


## Problem Description

 
A company is constructing many buildings and plan to use software to keep track
of all buildings under construction in a new city. 

A building record has the following fields:

* `buildingNum`: unique integer identifier for each building.

* `executedTime`: total number of days spent so far on this building.

* `totalTime`: the total number of days needed to complete the construction of
* the building.
 
**The needed operations are:**

1. Print(buildingNum) prints the triplet buildingNume, executedTime, totalTime.

2. Print(buildingNum1, buildingNum2) prints all triplets bn, executedTime,
totalTime for which `buildingNum1 <= bn <= buildingNum2`.

3. Insert (buildingNum,totalTime) where buildingNum is different from existing
building numbers and executedTime = 0.
 
In order to complete the given task, you must use a min-heap and a Red-Black
Tree (RBT). Also, you may assume that the number of active buildings will not
exceed 2000.
 
A min heap should be used to store `(buildingNum, executedTime, totalTime)`
triplets ordered by `executedTime`. You will need a suitable mechanism to handle
duplicate executedTimes in your min heap. An RBT should be used store
`(buildingNum, executedTime, totalTime)` triplets ordered by `buildingNum`. You
are required to maintain pointers between corresponding nodes in the min-heap
and RBT.
 
The company works on one building at a time. When it is time to select a
building to work on, the building with the lowest `executedTime` (ties are
broken by selecting the building with the lowest `buildingNum`) is selected. The
selected building is worked on until complete or for 5 days, whichever happens
first. If the building completes during this period its number and day of
completion is output and it is removed from the data structures. Otherwise, the
building’s executedTime is updated. In both cases, The company selects the next
building to work on using the selection rule. When no building remains, the
completion date of the new city is output.

## Instructions

1. Unzip the zipped archive, change directory to it and compile the java files
into class files using the following command (on Linux)
```
$ make
```

2. Run the program using the following command
```
$ java risingCity [input-file.txt]
```

3. The output of the program is written to the file o​ utput_file.txt​. Use the
following command to view the content of the output file.
```
$ cat output_file.txt
```

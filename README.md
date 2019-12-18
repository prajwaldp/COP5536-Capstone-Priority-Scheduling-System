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

## Implementation Details

1. There are two counters for keeping track of passing days - d​ay​ keeps track
of the days passed since the construction began and ​activeBuildingDay​ keeps
track of the days passed since work began on the currently active building.

2. As buildings are introduced with passing days, they are inserted into the
MinHeap.

3. If no building is being worked on, the building returned by the `removeMin​`
operation on the MinHeap is marked as the active building.

4. With every passing day, the ​executedTime​ of the building being worked on is
incremented (both, the HeapNode’s and the RedBlackNode’s ​executedTime attribute
is incremented).

5. When it‘s time for work to cease on the a​ ctiveBuilding​ (when it has been
under construction for 5 days, with buildings in the queue), it is reinserted
back into the MinHeap. The a​ ctiveBuildingDay​ counter is reset. The building
that is then removed from the MinHeap (using the ​removeMin​ operation) becomes
the activeBuilding. The removeMin operation returns the building with the least
executed time (and building number, if the executed time is equal).

6. As buildings are inserted into the MinHeap (except, when the building is
reinserted after being worked on), corresponding RedBlackNode objects are
inserted into the RedBlackTree. When a building is completed, it is not
reinserted back into the MinHeap and the corresponding RedBlackNode is deleted.

7. Since the RedBlackTree is a self-balancing binary search tree and contains
all the buildings, it is used to retrieve buildings by the building number or in
a range of building numbers efficiently.

## Results

The repository contains the 7 java files and a sample input and outpute file.


```
$ java risingCity input_file.txt
$ cat output_file.txt
(15,1,200),(50,45,100)
(15,45,200),(50,45,100)
(15,47,200),(50,45,100)
(15,50,200),(30,0,50),(50,45,100)
(15,50,200),(30,1,50),(50,45,100)
(15,50,200),(30,5,50),(50,45,100)
(15,50,200),(30,40,50),(50,45,100)
(15,50,200),(30,45,50),(40,45,60),(50,45,100)
(15,50,200),(30,50,50),(40,45,60),(50,45,100)
(30,190)
(15,50,200),(40,50,60),(50,45,100)
(15,50,200),(40,50,60),(50,50,100)
(15,55,200),(40,54,60),(50,50,100)
(15,55,200),(40,55,60),(50,51,100)
(40,225)
(50,310)
(15,410)
```
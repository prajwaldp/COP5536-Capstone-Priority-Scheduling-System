compile:
	@echo "Compiling files"
	javac Command.java
	javac DuplicateBuildingNumException.java
	javac MinHeap.java
	javac HeapNode.java
	javac RedBlackNode.java
	javac RedBlackTree.java
	javac risingCity.java

clean:
	@echo "Removing class files"
	rm *.class

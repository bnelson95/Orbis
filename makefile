JCC = javac
JFLAGS = -g

default: 
	$(JCC) $(JFLAGS) */*.java

run:
	java general/Orbis

clean: 
	$(RM) */*.class
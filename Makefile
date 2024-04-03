JAVA_VERSION = 17
JAVAC=javac
JAVA=java
# -g debuggin information
JAVA_COMPILE_OPTIONS = -g --release $(JAVA_VERSION)
JAVA_OPTIONS =

SRC=$(shell find src -name "*.java")
CLASSPATH=classes
MAIN_CLASS=jlox.Lox

default: compile

compile: $(CLASSPATH)
	$(JAVAC) $(SRC) -d $(CLASSPATH)

run: compile
	$(JAVA) $(JAVA_OPTIONS) -cp $(CLASSPATH) $(MAIN_CLASS)


clean: $(CLASSPATH)
	rm -rf $(CLASSPATH)

$(CLASSPATH):
	mkdir $(CLASSPATH)


JAVA_VERSION = 17
JAVAC=javac
JAVA=java
JAVA_COMPILE_OPTIONS = --enable-preview --release $(JAVA_VERSION)
JAVA_OPTIONS = --enable-preview

SRC=$(wildcard src/main/java/jlox/*.java)
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


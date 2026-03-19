#!/bin/bash

JFLEX_JAR="/home/juanpa/Descargas/jflex-1.9.1/lib/jflex-full-1.9.1.jar"
JCUP_JAR="/home/juanpa/Descargas/java-cup-bin-11b-20160615/java-cup-11b.jar"

echo " JFlex......."
java -jar "$JFLEX_JAR" FormsLexer.jflex

echo " JCup........."
java -jar "$JCUP_JAR" -parser ParserForms ParserForms.cup
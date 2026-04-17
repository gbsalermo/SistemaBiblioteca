#!/bin/bash

echo "Limpando build antigo..."
rm -rf bin

echo "Criando pasta bin..."
mkdir bin

echo "Compilando projeto..."
javac -d bin src/application/Main.java src/ui/TelaPrincipal.java src/model/.java src/datastructure/.java

if [ $? -ne 0 ]; then
    echo "Erro na compilacao!"
    exit 1
fi

echo "Executando..."
java -cp bin application.Main
#!/bin/bash

echo "Limpando build antigo..."
rm -rf bin

echo "Criando pasta bin..."
mkdir bin

echo "Compilando projeto..."
javac -d bin \
    $(find src -name "*.java" -type f)

if [ $? -ne 0 ]; then
    echo "Erro na compilacao!"
    exit 1
fi

echo "Executando..."
java -cp bin application.Main
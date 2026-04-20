# 📚 Library Manager  
### 🚀 Sistema de Gestão de Biblioteca em Java com Estrutura Dinâmica

![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java)
![Swing](https://img.shields.io/badge/Swing-GUI-blue?style=for-the-badge)
![Data Structure](https://img.shields.io/badge/Data%20Structure-Doubly%20Linked%20List-green?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow?style=for-the-badge)

---

## 🎬 Demonstração do Sistema

![Demonstração do sistema](docs/demo.gif)

---

## 🧠 Sobre o Projeto

O **Library Manager** é um sistema completo de gerenciamento de livros desenvolvido em Java, com foco em:

* **Estruturas de Dados Dinâmicas**
* **Interface gráfica interativa (Swing)**
* **Arquitetura em camadas (MVC-like)**

O grande diferencial é a implementação de uma **Lista Duplamente Encadeada**, permitindo navegação bidirecional eficiente e manipulação dinâmica dos dados em memória.

---

## ✨ Funcionalidades

### 📥 Inserção de Livros
Adição dinâmica ao final da lista através do método:
`adicionarNoFim();`

### 🔍 Busca Inteligente
* Busca por **Título**
* Busca por **Autor**
* Algoritmo linear otimizado

### ❌ Remoção Dinâmica
`removerAtual();`
* Ajuste automático dos ponteiros `anterior` e `próximo`
* Integridade da lista garantida

### 🔄 Navegação Bidirecional
* `avancar();`
* `voltar();`

### 📊 Ordenação
* Por **Título**
* Por **Autor**
* Por **Ano**

---

## 🖥️ Interface do Sistema

### 🎛️ Painel Superior
* Ações de **Inserir, remover e buscar**
* Navegação entre registros e **Ordenação**

### 📋 Tabela de Livros
* Visualização dinâmica (Painel Esquerdo)
* Atualização em tempo real via `JTable`

### 📝 Formulário de Cadastro (Painel Direito)
Campos disponíveis:
* Título, Autor e Ano
* Gênero e Editora

---

## 💡 Diferencial Técnico

### 🔥 Lista Duplamente Encadeada
A estrutura permite que cada nó conheça seu predecessor e sucessor:
`[Anterior] ← [NÓ] → [Próximo]`

**🚀 Benefícios:**
* Navegação sem necessidade de reiniciar o percurso da lista
* Inserção e remoção eficientes em qualquer ponto
* Melhor performance em operações sequenciais

---

## 🏗️ Arquitetura do Projeto

A estrutura de pastas está organizada da seguinte forma:

```text
src/
├── application/
│   └── Main.java
├── model/
│   ├── Livro.java
│   └── No.java
├── repository/
│   └── ListaLivrosDuplamenteEncadeada.java
└── view/
    ├── TelaPrincipal.java
    ├── PainelSuperior.java
    ├── PainelEsquerdo.java
    └── PainelDireito.java

bin/  (Arquivos compilados)
lib/  (Dependências externas)
```

---

## ⚙️ Configuração (VS Code)

Configuração do arquivo `.vscode/settings.json` para reconhecimento correto do projeto e das bibliotecas:

```json
{
  "java.project.sourcePaths": ["src"],
  "java.project.outputPath": "bin",
  "java.project.referencedLibraries": [
    "lib/**/*.jar"
  ]
}
```

---

## 🛠️ Como Executar

Utilize o script de automação **`run.bat`** localizado na raiz do projeto. O script realiza automaticamente as seguintes tarefas:

* **Limpeza e organização** do build anterior.
* **Compilação** de todos os arquivos `.java` para a pasta `bin`.
* **Execução** do sistema via classe `Main`.

---

## 🎯 Roadmap

- [x] Interface gráfica completa
- [x] Navegação bidirecional funcional
- [x] Busca por título/autor
- [ ] Remoção completa (lógica final)
- [ ] Ordenação otimizada
- [ ] Persistência em arquivo (JSON/DB)
- [ ] Melhorias visuais (UI/UX)

---

## ⚠️ Status da Sprint

### ✔ Concluído
* **Interface gráfica completa (GUI)**
* **Sistema de busca funcional (Título/Autor)**

### ⚠️ Em andamento
* Lógica final de Remoção
* Algoritmos de Ordenação

### 🚨 Ação Necessária
Substituir os *mocks* (códigos temporários de teste) pelos métodos definitivos no arquivo:
* `ListaLivrosDuplamenteEncadeada.java`

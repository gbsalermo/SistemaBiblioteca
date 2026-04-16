# Sistema de Gestão de Biblioteca 📚

Este projeto consiste em um sistema de gerenciamento de livros desenvolvido em Java, focado na implementação de **Estruturas de Dados Dinâmicas** para a disciplina de Estrutura de Dados.

## 🚀 Proposta do Projeto

O objetivo principal é implementar uma estrutura onde cada nó representa um livro, permitindo a gestão eficiente do acervo.

### Funcionalidades principais:
- **Inserção Dinâmica:** Inserir livros no início, no fim ou em uma posição específica.
- **Remoção:** Excluir livros do acervo.
- **Busca:** Localizar livros por título ou autor.
- **Exibição:** Listar todos os livros cadastrados.
- **Ordenação:** Algoritmos para ordenar por Título, Autor e Ano de publicação.
- **Interface Gráfica:** Desenvolvida com a biblioteca **Swing**.
- **💡 Extra:** Implementação de **Lista Duplamente Encadeada** para navegação bidirecional (próximo/anterior).

---

## 📂 Estrutura de Pastas

- `src`: Contém todo o código-fonte (arquivos `.java`).
- `lib`: Destinada a dependências e bibliotecas externas (arquivos `.jar`).
- `bin`: Pasta onde o Java gera automaticamente os arquivos compilados (`.class`). *Nota: Esta pasta é ignorada pelo controle de versão (Git).*

---

## ⚙️ Configurações do Ambiente (`settings.json`)

Para manter a compatibilidade com o padrão JSON e garantir que o VS Code gerencie corretamente o projeto, o arquivo .vscode/settings.json define a estrutura de compilação sem comentários internos:

```json
{
    "java.project.sourcePaths": ["src"], 
    "java.project.outputPath": "bin", 
    "java.project.referencedLibraries": [
        "lib/**/*.jar"
    ]
}

🔍 Entendendo as Configurações:
java.project.sourcePaths: Informa ao compilador que todo o código-fonte (arquivos .java) deve ser buscado exclusivamente dentro da pasta src. Isso organiza onde o desenvolvimento acontece.
java.project.outputPath: Define o destino dos arquivos binários compilados (arquivos .class). Ao centralizar esses arquivos na pasta bin, mantemos o projeto organizado e garantimos que o Git possa ignorá-los facilmente.
java.project.referencedLibraries: Mapeia automaticamente todas as bibliotecas externas (arquivos .jar) que forem adicionadas à pasta lib. Isso é essencial para o gerenciamento de dependências no futuro.

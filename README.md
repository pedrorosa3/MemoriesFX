<div align="center">

# 🖼️ MemoriesFX

**Gestor de fotografias para desktop, feito em Java e JavaFX**

![Java](https://img.shields.io/badge/Java-11+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-13-1E90FF?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-3.x-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Derby](https://img.shields.io/badge/Apache%20Derby-embedded-2E7D32?style=for-the-badge)

👥 **Autores:** Pedro Rosa · Tiago Raposo · Tomás Nogueira &nbsp;·&nbsp; 📅 **Entregue em:** janeiro de 2025

</div>

---

## 📖 Sobre o projeto

O **MemoriesFX** é uma aplicação desktop para organizar uma biblioteca de fotografias. É a interface gráfica (GUI) construída sobre a lógica de negócio do projeto *MemoriesDB*: as mesmas relações entre **Fotografias**, **Álbuns** e **Tags**, agora numa janela em vez da linha de comandos.

## ✨ Funcionalidades

| | Funcionalidade | Descrição |
|---|---|---|
| 🖼️ | **Galeria** | Mostra as fotografias numa grelha de miniaturas |
| 🔍 | **Pesquisa** | Filtra a galeria por tags ou pela descrição |
| 🏷️ | **Tags** | Cria tags e adiciona-as ou remove-as das fotografias selecionadas |
| 📁 | **Álbuns** | Cria álbuns, adiciona e remove fotografias, e apaga álbuns |
| 💾 | **Persistência** | Guarda tudo numa base de dados Apache Derby embebida |

## 🛠️ Tecnologias

- **Java 11+**
- **JavaFX 13**: interface gráfica
- **JPA / EclipseLink**: mapeamento objeto-relacional
- **Apache Derby**: base de dados embebida (`data/derby/memoriesdb`)
- **metadata-extractor**: leitura dos metadados EXIF das imagens
- **JUnit 5**: testes
- **Maven**: build e dependências

## 🏗️ Arquitetura

```
src/main/java/
├── domain/     → Entidades: Picture, Album, Tag, Color
├── persist/    → Data Mappers (acesso à base de dados via JPA)
├── handlers/   → Lógica de negócio (PictureHandler)
├── gui/        → Componentes JavaFX (painéis, vistas, colunas)
├── utils/      → Utilitários (miniaturas, tabelas)
└── main/       → Ponto de entrada (App) e configurações
```

Os diagramas do modelo estão na raiz do repositório:

- [Modelo de Domínio](CSS-Modelo%20de%20Dominio.drawio.png)
- [Modelo de Dados](CSS-Modelo%20de%20Dados.drawio.png)
- [Adicionar Tag](CSS-AddTag.drawio.png)
- [Selecionar Pictures com Tag](CSS-SelecionarPicturescomTag.drawio%20(1).png)

## 🚀 Como executar

### Pré-requisitos

- **JDK 11** ou superior
- **Maven 3.x** (o Maven descarrega o JavaFX e as restantes dependências, não é preciso instalar o SDK do JavaFX à parte)

### 1. Clonar o repositório

```bash
git clone https://github.com/pedrorosa3/MemoriesFX.git
cd MemoriesFX
```

### 2. Pastas de imagens (já configuradas)

Não é preciso configurar nada. As pastas ficam dentro do projeto e funcionam em Windows, Linux e macOS:

| Pasta | Para que serve |
|---|---|
| `fotos/FotosParaImportar/` | Fotografias (`.jpg`) a importar. Já traz 5 imagens de exemplo geradas |
| `fotos/ManagerFotos/` | Onde a aplicação guarda as miniaturas que gere (criada automaticamente, ignorada pelo git) |

Para usar outras pastas, passe as propriedades `memoriesfx.importFolder` e `memoriesfx.managedFolder`, por exemplo:

```bash
mvn clean javafx:run -Dmemoriesfx.importFolder="C:/Users/<utilizador>/Pictures"
```

### 3. Compilar e executar

```bash
mvn clean javafx:run
```

A classe principal é `main.App`.

### 4. (Opcional) Inicializar a base de dados

Na primeira execução, ou para recomeçar do zero, passe o argumento `initdb`. A aplicação usa então a unidade de persistência de testes e importa as fotografias da `importFolder`:

```bash
mvn clean javafx:run -Djavafx.args="initdb"
```

Num IDE (Eclipse ou IntelliJ), basta executar `main.App` com `initdb` como argumento do programa.

### 5. Correr os testes

```bash
mvn test
```

Resultado esperado: **20 testes, 0 falhas** (`BUILD SUCCESS`). Os testes usam uma base de dados própria em `target/derby/testdb`, por isso não apagam os dados da aplicação.

### 🐳 Correr os testes com Docker

Se não quiser instalar o JDK nem o Maven, pode correr os testes num contentor:

```bash
docker build -t memoriesfx .
docker run --rm memoriesfx
```

#### 🖼️ Fotografias de teste

Os testes `TestPictureDM` e `TestPictureHandler` importam fotografias da pasta
[`fotos/FotosParaImportar/`](fotos/FotosParaImportar/). As fotografias originais usadas
na cadeira já não existem. Por isso, o repositório inclui **imagens geradas** com os mesmos
nomes e com a data EXIF (`DateTimeOriginal`) igual à data do nome do ficheiro.
Servem para os testes, tanto localmente como no Docker, e como exemplo para o `initdb`.

Para voltar a gerá-las (requer Python e Pillow):

```bash
pip install pillow
python scripts/gerar_fotos_teste.py
```

O Docker serve só para os testes. A interface gráfica (JavaFX) deve ser executada diretamente no computador, com `mvn clean javafx:run`.

## 📚 Guia de utilização

> 💡 Para selecionar uma fotografia, **clique na imagem**. Pode selecionar várias.

### 🏷️ Tags

- **Adicionar:** selecione as fotografias e escolha a tag.
- **Remover:** selecione as fotografias (da mesma forma) e remova a tag.

### 📁 Álbuns

<details>
<summary><b>Criar um álbum</b></summary>

1. Preencha o campo **Album Name**.
2. Preencha o campo **Album Tag** com o nome que quer dar à tag.
3. Preencha o campo **Album Description**.
4. Clique em **Create**.

</details>

<details>
<summary><b>Adicionar fotografias a um álbum</b></summary>

1. Selecione o álbum, clicando no seu cartão na lista de álbuns.
2. Aparece um painel com os dados do álbum.
3. Selecione as fotografias que quer adicionar.
4. Clique em **SetPictures**.

</details>

<details>
<summary><b>Remover fotografias de um álbum</b></summary>

1. Selecione o álbum, como descrito acima.
2. Selecione as fotografias que quer remover.
3. Clique em **RemovePictures**.

</details>

<details>
<summary><b>Apagar um álbum</b></summary>

1. Selecione o álbum.
2. Clique em **Delete Album**, no painel com as informações do álbum.

</details>

---

<div align="center">

Trabalho de grupo desenvolvido por **Pedro Rosa**, **Tiago Raposo** e **Tomás Nogueira** · janeiro de 2025 · Projeto com fins académicos 🎓

</div>

# Imagem para compilar o MemoriesFX e correr os testes
FROM maven:3.9-eclipse-temurin-11

WORKDIR /app

# Descarregar as dependências primeiro (fica em cache entre builds)
COPY pom.xml .
RUN mvn -B -q dependency:go-offline || true

# Código-fonte e fotografias de teste geradas (fotos/FotosParaImportar)
COPY src ./src
COPY fotos/FotosParaImportar ./fotos/FotosParaImportar

# Por omissão, compila e corre os testes
CMD ["mvn", "-B", "test"]

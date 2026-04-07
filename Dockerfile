# Estágio 1: Build (Onde a mágica acontece)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia apenas o que é essencial primeiro
COPY pom.xml .
COPY src ./src

# Pula os testes e limita a memória do Maven para não travar o Render
RUN mvn clean package -DskipTests -Dmaven.test.skip=true

# Estágio 2: Execução (Onde a API roda de fato)
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia apenas o arquivo .jar final do estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Define a porta que o Render usa
EXPOSE 10000

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-Xmx400m", "-jar", "app.jar"]
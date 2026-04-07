# ---------- Estágio 1: Build (Construção) ----------
# Usamos a imagem do Maven com Java 17 (sua versão)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia apenas o arquivo de configuração primeiro para otimizar o cache
COPY pom.xml .
# Copia todo o código fonte
COPY src ./src

# Executa o build limpando arquivos antigos e gerando o novo .jar
# Pulamos os testes para economizar muita memória RAM no Render
RUN mvn clean package -DskipTests

# ---------- Estágio 2: Runtime (Execução) ----------
# Imagem leve apenas com o JRE (ambiente de execução)
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia o arquivo .jar gerado no estágio anterior para a pasta atual
COPY --from=build /app/target/*.jar app.jar

# CRIA A PASTA DO BANCO DE DADOS (H2 FILE)
# Isso evita o erro de "folder not found" quando a API tenta salvar o arquivo
RUN mkdir -p /app/data

# Configura a porta padrão do Render
ENV PORT=10000
EXPOSE 10000

# Comando para rodar a aplicação
# -Xmx300m: Limita o Java a usar no máximo 300MB, deixando sobra para o sistema do Render
# -Dserver.port=10000: Garante que a API escute na porta que o Render espera
ENTRYPOINT ["java", "-Xmx300m", "-Dserver.port=10000", "-jar", "app.jar"]
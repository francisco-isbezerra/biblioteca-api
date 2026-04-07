# 📚 API de Gerenciamento de Biblioteca - Parte 1

Projeto desenvolvido para a disciplina de Desenvolvimento de Web Services. Esta API RESTful permite o gerenciamento completo de Autores, Livros e Empréstimos.

## 🚀 Funcionalidades Implementadas
- **CRUD Completo**: Endpoints para Criar, Ler, Atualizar e Deletar (Autores, Livros, Usuários, Perfis e Empréstimos).
- **Paginação**: Listagem de dados otimizada com suporte a `Pageable`.
- **HATEOAS**: Implementação de links de navegabilidade em conformidade com o nível 3 de maturidade de Richardson.
- **Documentação Swagger**: Interface interativa para testes de todos os endpoints.
- **Banco de Dados H2**: Banco de dados em memória configurado para facilitar a execução imediata.
- **Validação de Dados**: Uso de `@Valid` e Bean Validation para garantir a integridade das informações.

## 🛠️ Tecnologias Utilizadas
- **Java 17** (Microsoft OpenJDK)
- **Spring Boot 3.2.4**
- **Spring Data JPA** (Persistência de dados)
- **Springdoc OpenAPI 2.3.0** (Swagger UI)
- **Lombok** (Produtividade no código)
- **Maven** (Gerenciador de dependências)

## 📖 Como Executar e Testar
1. Clone este repositório para sua máquina local.
2. No IntelliJ, abra o projeto e aguarde o Maven baixar as dependências.
3. Certifique-se de que o **Annotation Processing** está habilitado nas configurações do IDE.
4. Execute a classe `BibliotecaApiApplication`.
5. Com a aplicação rodando, acesse a documentação interativa no navegador:
    - **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
    - **H2 Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

---
*Desenvolvido por Francisco (Abril/2026)*
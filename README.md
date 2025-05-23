# O problema

Há uma lanchonete de bairro que está expandindo devido seu grande sucesso. Porém o estabelecimento não estava preparado para sua expansão, com isso os pedidos sem um sistema que os gerencie se tornaram confusos e complicados, os funcionários começaram a perder os papéis que eram anotados os pedidos, a cozinha não tinha um direcionamento claro do que era cada pedido e os próprios antendentes não conseguiam lidar com a demanda total de clientes.

Com o crescimento da lanchonete e a adoção de uma arquitetura baseada em microsserviços, cada domínio do sistema passou a ser isolado para garantir escalabilidade, manutenção e evolução independentes.

## Solução Proposta

Gerenciar todo o fluxo de pagamento dos pedidos realizados na aplicação, atuando como intermediador entre o sistema de pedidos e o provedor de pagamentos (Mercado Pago).

## Desenho Técnico Microsserviços
<div align="center">
  <img src="https://i.ibb.co/dsPzD37s/arquitetura.png" alt="Arquitetura de Microsserviços">
</div>

## Evidências de Testes no SonarCloud
<div align="center">
  <img src="https://i.ibb.co/Y464zgZ9/payment-service.jpg" alt="Cobertura de testes no sonar">
</div>

## Modelo de Entidade e Relacionamento

<div align="center">
  <img src="https://i.ibb.co/HSFtJNM/model-payment-service.png" alt="Modelo de Entidade e Relacionamento do Banco de Dados PostgreSQL">
</div>

## Tecnologias
- **Spring Boot**: Framework para construção de aplicações Java.
    - `spring-boot-starter-web`: Para construir aplicações web.
    - `spring-boot-starter-data-jpa`: Para integração com JPA (Java Persistence API).
    - `spring-boot-starter-validation`: Para validação de dados.
- **PostgreSQL**: Banco de dados relacional robusto e amplamente adotado, escolhido por sua consistência, integridade transacional e suporte avançado a tipos de dados. É ideal para serviços onde a estrutura dos dados é bem definida e as relações entre entidades são fundamentais. Sua confiabilidade e conformidade com padrões SQL o tornam adequado para microsserviços que exigem forte controle de dados, validações e operações relacionais complexas.
- **Lombok**: Biblioteca para reduzir o código boilerplate.
- **Spring Cloud OpenFeign**: Para facilitar chamadas de serviços REST.
- **Springdoc OpenAPI**: Para gerar documentação da API.
- **Kubernates**: Para orquestrar contêineres de maneira eficiente e automatizada
- **Terraform**: Para gerenciamento do nosso IaC
- **AWS**: Toda a nossa infraestrutura em nuvem.

## Requisitos

- Java 21
- Docker
- Minikube e Kubernetes configurados localmente

## Estrutura do Projeto

- payment-service: responsável por toda a gestão de pagamentos, incluindo a integração com o gateway do Mercado Pago e o processamento de transações.
- register-service: gerencia os cadastros do sistema, como clientes, produtos e usuários.
- production-service: opera no ambiente da cozinha, sendo responsável por acompanhar e atualizar o status dos pedidos em produção.
- order-service: lida com a criação e o gerenciamento dos pedidos realizados pelos clientes, atuando como ponto central no fluxo de consumo.

## Documentação da API

- Após a aplicação estar em execução, a documentação estará disponível em:

```
http://<url_service>/swagger-ui/index.html
```

## Considerações Finais

Este projeto utiliza o Minikube para rodar uma instância local do Kubernetes, que gerencia a implantação dos serviços de backend e banco de dados. Certifique-se de ter o Minikube e o kubectl configurados corretamente para evitar problemas na execução.

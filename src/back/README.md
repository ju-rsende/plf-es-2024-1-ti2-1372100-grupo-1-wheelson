# Rotas de usuário

### Link swagger
 - Para utilizar o swagger, basta dar run no projeto e acessar o link.
[Swagger](http://localhost:8080/swagger-ui/index.html#/)
## Locador 
| Método | Rota                       | Descrição                                                          |
| ------ | -------------------------- | ------------------------------------------------------------------ |
| GET   | `/locador/{id}`                   | Recebe um locador pelo ID                                             |
| GET   | `/locador`                   | Recebe todos os locadores                                           |
| GET   | `/locador/status/false`                   | Recebe todos os locadores com status false                         |
| GET   | `/locador/email/{email}`                   | Recebe um locador pelo email                                       |
| GET   | `/locador/login/{email}/{senha}`                   | Recebe um locador pelo email e senha                                       |
| POST  | `/locador`                   | Cadastra um locador                                           |
| PUT  | `/locador/{id}`                   | Altera os dados de um locador                                           |
| DELETE  | `/locador{id}`                   | Apaga o perfil de um Locador                                        |

## Locatário
| Método | Rota                       | Descrição                                                          |
| ------ | -------------------------- | ------------------------------------------------------------------ |
| GET   | `/locatario/{id}`                   | Recebe um locatário pelo ID                                       |
| GET   | `/locatario`                   | Recebe todos os locatários                                           |
| GET   | `/locatario/status/false`                   | Recebe todos os locatários com status false              |
| GET   | `/locatario/email/{email}`                   | Recebe um locatário pelo email                                       |
| GET   | `/locatario/login/{email}/{senha}`                   | Recebe um locatário pelo email e senha
| POST  | `/locatario`                   | Cadastra um locatário                                           |
| PUT  | `/locatario/{id}`                   | Altera os dados de um locatário                                           |
| DELETE  | `/locatario/{id}`                   | Apaga o perfil de um Locatário                                        |
| PATCH | `/locatario/{id}` | Atualiza o status do locatário para true |

## Carro
| Método | Rota                       | Descrição                                                          |
| ------ | -------------------------- | ------------------------------------------------------------------ |
| GET   | `/carro/{id}`                   | Retorna um carro específico baseado no seu ID                                     |
| GET   | `/carro/all`                   | Retorna uma lista com todos os carros|
| GET  | `/carro/locador/{id}`                   |  Retorna uma lista de carros baseados no ID do locador                                           |
| GET  | `/carro/disponiveis` | Retorna uma lista de carros disponíveis para aluguel                                           |
| POST   | `/carro`                   | Cria um novo carro                                           |
| PUT   | `/carro/{id}`                   | Atualiza um carro existente baseado no ID fornecido            |
| DELETE   | `/carro/{id}`                   | Deleta um carro baseado no ID fornecido                                       |

## Aluguel
| Método | Rota                       | Descrição                                                          |
| ------ | -------------------------- | ------------------------------------------------------------------ |
| GET   | `/aluguel/{id}	`               | Busca um aluguel pelo seu ID. |
| GET   | `/aluguel/alugueis`            | Busca todos os aluguéis.|
| GET  | `/aluguel/alugueis/locador/{id}` |  Busca todos os aluguéis pelo ID do locador. |
| GET   | `/aluguel/alugueis/locatario/{id}` | Busca todos os aluguéis pelo ID do locatário. |
| GET   | `/aluguel/alugueis/carro/{id}` | Busca todos os aluguéis pelo ID do carro.           |
| PATCH   | `/aluguel/{id}	`               | Atualiza parcialmente um aluguel (status de pagamento, ativo, valor total). |
| PATCH   | `/aluguel/finalizar/{id}` | Finaliza um aluguel e atualiza o status de pagamento, atribui saldo ao locador. |
| PATCH   | `/aluguel/devolver/{id}`  | Inicia o processo de devolução de um aluguel. |
| POST   | `/aluguel`                  | Cria um novo aluguel.   |
| DELETE   | `/aluguel/{id}	`          |Deleta um aluguel pelo seu ID. |
| GET    | `/aluguel/taxa-mensal`|Recebe um MAP<Mês,taxa>, referente ao indicador de desempenho "Taxa de alugueis feitas no mês"    |

## Problema
| Método | Rota                       | Descrição                                                          |
| ------ | -------------------------- | ------------------------------------------------------------------ |
| GET   | `/problema/{id}`                   | Recebe um problema pelo ID                                       |
| GET   | `/problema`                   | Recebe todos os problemas                                           |
| GET   | `/problema/resolvido`  |  Recebe todos os problemas resolvidos                                           |
| GET   | `/problema/naoResolvido`  |  Recebe todos os problemas não resolvidos                                           |
| GET   | `/problema/aluguel/{id}`                   | Recebe um problema pelo ID do aluguel                                       |
| POST  | `/problema`                   | Cadastra um problema                                           |
| PUT  | `/problema/{id}`                   | Altera os dados de um problema                                           |
| PUT | `/problema/resolver/{id}`                   | Altera o status de um problema para resolvido                                           |
| DELETE  | `/problema/{id}`                   | Apaga o perfil de um problema                                        |

## Adm
| Método | Rota                       | Descrição                                                          |
| ------ | -------------------------- | ------------------------------------------------------------------ |
| GET    | `/adm/login/{email}/{senha}`                   | Recebe true caso tenha um perfil de adm com esses dados no servidor, falso caso não tenha                              |
| POST   | `/adm`                   | Cadastra um adm                                           |

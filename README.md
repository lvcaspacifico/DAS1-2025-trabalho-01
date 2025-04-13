# Design e Arquitetura de Software 1 - Trabalho 01 | Chat de mensagens

**Desafio:**

> Implementar um cliente de CHAT em java que conecte ao tópico no Azure Service Bus utilizano o código básico disponível no repositório 
> https://github.com/waltercoan/das-1-2025/tree/main/eventos/src/main/java/br/univille/ativchat


## Notas

Pra sincronizar as mensagens, o tempo de espera é 7 segundos. Expus ao professor que isso poderia ser demais ou de menos dependendo da quantidade de mensagens, mas a alternativa trava a thread da interface devido ao funcionamento do ServiceBus nunca parar de rodar

* Não é possível enviar uma mensagem vazia
* Não é possível enviar uma mensagem com >1000 caracteres
* A sincronização é de 7 em 7 segundos, o que vier nesse tempo veio

![https://i.imgur.com/BkKsezu.png](https://i.imgur.com/BkKsezu.png)
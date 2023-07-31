# Documentação

## Overview API
A API valida as informações enviadas e retorna um erro ou sucesso de acordo com os dados recebidos. Algumas requisições podem exigir campos obrigatórios que devem ser preenchidos, mas que em alguns casos o campo pode ser null.

Para garantir a qualidade da integração mesmo depois de homologada, monitoramos cada requisição, então pode acontecer de acionarmos o parceiro em casos que precisarem de correções.

## Endpoints de Envio

Os endpoints de envio de dados são: order e product.

* Order recebe os dados de vendas e itens vendidos, dependendo da integração os itens vendidos podem ser omitidos.
* Product recebe os dados de catálogo, preço e estoque.

O limite de itens enviados para cada API é 1000, ou seja qualquer valor maior do que esse os endpoints retornarão um erro.

O campo ts é um campos do tipo timestamp, como o envio dos dados é parcelado de 1000 em 1000 registros usamos o campo ts para identificar uma carga de dados, por exemplo, para enviar 5000 registros, a API de envio precisará ser acionada pelo menos 5x, esses envios podem conter o mesmo ts para representar uma única carga.

## Campos Customizados
Caso queira adicionar campos adicionais tanto no endpoint de order quanto no de product voce pode incluir novos campos com um prefixo custom_ então por exemplo se quiser acrescentar um campo de cor no endpoint de product o campo poderia ser custom_cor ou custom_color o valor sempre deve ser passado como string.

## Dados de vendas
Para enviar dados de vendas utilize o seguinte exemplo:

```json
{
    "ts": "<YYYYMMDDHHMMSS>",
    "rows": [
        {
            "default_order_id": "325",
            "default_order_number": "00001",
            "default_date": "2022-05-01 14:23:33",
            "default_total_amount": 100.00,
            "default_subtotal_amount": 100.00,
            "default_discount_amount": 0.00,
            "default_shipping_amount": 0.00,
            "default_status": "Faturado",
            "default_salesperson_id": "123",
            "default_salesperson_code": "X381A",
            "default_shipping_type": "SEDEX",
            "default_payment_id": "432",
            "default_payment_type": "Boleto / Cartão de Crédito",
            "default_payment_installments": "1",
            "default_invoice_number": "29393",
            "items": [
                {
                    "default_item_id": "432",
                    "default_item_sku": "A1202",
                    "default_item_ean": "7898960921267",
                    "default_item_name": "Camisa Preta",
                    "default_item_quantity": 2,
                    "default_item_list_price_amount": 100.00,
                    "default_item_price_amount": 100.00,
                    "default_item_discount_amount": 0,
                    "default_item_status": "Faturado"
                }
            ]
        }
    ]
}
```

**Resposta de sucesso**: 200
```json
{
	"message": "Sent"
}
```

**Resposta de sucesso**: 400
```json
{
	"content": [
		{
			"entity": "order",
			"errors": [
				{
					"description": "field 'default_discount_amount' must be decimal value",
					"indexes": [
						0
					]
				}
			]
		}
	],
	"message": "Error to send data"
}
```

### Vendas
| Campo	| Exemplo	| Tipo	| Descrição	| Obrigatório |
| :--- | :--- | :--- | :--- | :--- |
| default_order_id |	"1", "01", "ABC001"	| string |	Id único da venda na base	| Sim |
| default_order_number |	"1", "01", "ABC001"	 | string |	Repetir o ID da venda	| Sim |
| default_invoice_number |	89928, 728829, 874748	| integer |	Número da nota. Se não houver emissão fiscal informar o mesmo valor do campo Id da venda	| Sim |
| default_date |	"2021-11-25 10:25:08"	| string |	Data e Hora da venda, ISO 8601. Obs: Não é aceito milisegundos	| Sim |
| default_total_amount (1) | 	1189.50	 | float |	Valor liquido da venda (Subtotal da venda - desconto + frete)	| Sim |
| default_subtotal_amount |	1199.50	| float | 	Soma de todos os itens vendidos com o desconto de cada item aplicado (Total líquido dos itens vendidos)	| Sim |
| default_shipping_amount |	40.00	| float	| Valor do frete se houver.	| Não |
| default_discount_amount |	60.00	| float	| Valor do desconto	| Sim |
| default_status |	"Faturado", "Cancelado", "Devolvido", "Bonificação" ou "Transferência"	| string |	Status da venda	| Sim |
| default_salesperson_id |	"1", "V01"	| string |	Id do Vendedor.	| Sim |
| default_salesperson_code |	"1", "V01"	| string |	Pode ser igual ao ID do Vendedor	| Sim |
| default_shipping_type |	"Correios", "Retirado", entre outros...	| string |	Tipo de entrega	| Não |
| default_payment_id |	"01, 02, 03"	| string |	Id do pagamento na base	| Não |
| default_payment_type |	"Cartão a vista, Cartão Parcelado, Dinheiro", entre outros...	| string |	Tipo de pagamento	| Não |
| default_payment_installments |	10	| integer |	Número de parcelas quando a venda for parcelada, senão informar 1	| Não |

Validações
1. O Valor líquido da venda deverá ser o resultado do cálculo (Subtotal da venda - desconto da venda + frete).

### Itens da Venda
| Campo	| Exemplo	| Tipo	| Descrição	| Obrigatório |
| :--- | :--- | :--- | :--- | :--- |
| default_item_id |	"1, 1000, 100000"	| string	| Id interno do produto	| Sim  |
| default_item_ean |	"0123456789123"	| string	| Código EAN	| Sim  |
| default_item_sku |	"0123456789123"	| string	| Usar o EAN	| Sim |
| default_item_name |	"Camisa Polo"	| string	| Nome do produto	| Sim |
| default_item_quantity |	3	| inteiro	| Quantidade vendida	| Sim |
| default_item_list_price_amount (1) |	105.99	| float	| Preço bruto unitário.	| Sim |
| default_item_price_amount (2) |	100.00	| float	| Preço líquido unitário com desconto. (Preço bruto unitário - Desconto unitário do item)	| Sim |
| default_item_discount_amount |	5.99	| float	V| alor de desconto unitário do item.	| Sim |
| default_item_status |	"Faturado" ou "Cancelado"	| string	| Status da venda do item	| Sim |

### Validações
1. O resultado de default_item_list_price_amount - default_item_discount_amount tem que ser igual ao valor de default_item_price_amount.
2. A soma do cálculo default_item_price_amount * default_item_quantity de todos itens de cada venda deve ser igual ao campo Subtotal da Venda (default_subtotal_amount).
3. Ratear o valor dos desconto entre os itens, por exemplo, se for vendido 4 itens de um mesmo produto e o item teve um desconto de R$ 40,00 esse campo fica com o valor R$ 10,00.

## Dados de catálogo

Para enviar os dados de catálogo, segue exemplo em json e possíveis respostas:

```json
{
	"ts": "<YYYYMMDDHHMMSS>",
	"rows": [
		{
			"default_product_id": "123",
			"default_product_name": "Camisa Preta",
			"default_sku_id": "325",
			"default_sku": "XS001-M",
			"default_category": "Mulher > Roupa > Camiseta",
			"default_status": "Faturado",
			"default_ean": "7898960921267",
			"default_description": "Camisa preta com manga curta",
			"default_brand": "Balmain",
			"default_manufacturer": "Balmain LTDA",
			"default_stock_quantity": 21,
			"default_list_price": 453.22,
			"default_price": 399.99,
			"default_width": "150",
			"default_height": "150",
			"default_weight_net": "0,5",
			"default_price_id": "625",
			"default_stock_id": "525"
		}
	]
}
```

Resposta de sucesso: 200

```json
{
	"message": "Sent"
}
```

Resposta de erro: 400

```json
{
	"content": [
		{
			"entity": "product",
			"errors": [
				{
					"description": "field 'default_stock_quantity' must be decimal value",
					"indexes": [
						0
					]
				}
			]
		}
	],
	"message": "Error to send data"
}
```

| Campo	| Exemplo	| Tipo	| Descrição	| Obrigatório |
| :--- | :--- | :--- | :--- | :--- |
| default_product_id |	"1", "P001"	| string	| Id do produto na base	| Sim |
| default_product_name |	"Camisa Polo"	| string	| Nome do produto	| Sim |
| default_sku_id |	"1", "S001"	| string	| ID do sku na base, pode ser identico ao ID do produto	| Sim |
| default_ean |	"0123456789123"	| string	| EAN	| Sim |
| default_sku |	"0123456789123"	| string	| EAN ou código do fabricante	| Sim |
| default_description |	"Camisa Polo Branca G"	| string	| Descrição do produto	| Sim |
| default_category |	"Camisas"	| string	| Categoria.	| Não |
| default_manufacturer |	"Polo Ralph Lauren"	| string	| Fabricante.	| Não |
| default_brand |	"Polo Ralph Lauren"	| string	| Marca	| Não |
| default_weight_net |	0.300	| float	| Peso líquido.	| Não |
| default_width |	83.00	| float	| Largura da peça (cm).	| Não |
| default_height |	87.00	| float	| Altura da peça (cm).	| Não |
| default_stock_id |	"1", "01	| string	| Id do estoque.	| Não |
| default_price_id |	"1", "01	| string	| Id do preço na base.	| Não |
| default_stock_quantity (1)	| 1.0	| float	| Quantidade do item no estoque	| Sim |
| default_list_price (2)	| 105.99	| float	| Preço bruto (de).	| Sim |
| default_price (2)	| 100.00	| float	| Preço bruto com desconto (por). (Preço bruto - desconto)	| Sim |
| default_status |	"Ativo", "Inativo"	| string	| Status do produto	| Sim |

### Validações
1. A Quantidade do Estoque não pode ser menor que zero.
2. Os valores de Preço Bruto e Preço Líquido não podem ser menores ou iguais a zero.

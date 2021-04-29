# GERADOR DE QRCODE PIX ESTÁTICO

Gerador de QRCode PIX

<p align="center">
  <img src="https://www.bcb.gov.br/content/estabilidadefinanceira/piximg/logo_pix.png">
</p>

## Como utilizar

### Gerar o texto copia e cola do qrcode
```java
	Payload payload = new Payload()
                .setPixKey("SUACHAVEPIX")
                .setDescription("DESCRICAO")
                .setMerchantName("NOME")
                .setMerchantCity("CIDADE")
                .setAmount(new BigDecimal("1.00"))
                .setTxid("TXID1234");
        
    	String qrcode = payload.getPayload();
    	System.out.println(qrcode);
```

### Salvar em uma imagem o qrcode usando o gerador de qrcode https://github.com/kenglxn/QRGen

```java
	ByteArrayOutputStream byteArrayOutputStream = QRCode
                .from(qrcode)
                .to(ImageType.JPG)
                .withSize(200, 200).stream();

    	try (OutputStream outputStream = new FileOutputStream("qrcode.jpg")) {
         	byteArrayOutputStream.writeTo(outputStream);
    	}
```

### Faça teste da a string que gerou aqui https://pix.nascent.com.br/tools/pix-qr-decoder/

### Créditos

Para assistir o vídeo dessa implementação, acesse: [Integração Pix PHP: Gerando payload e QR Code estático do Pix com PHP (YouTube)](https://youtu.be/eO11iFgrdCA)

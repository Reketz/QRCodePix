package br.com.systech.qrcodepix;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

public class Main {

    public static void main(String[] args) throws IOException {
        Payload payload = new Payload()
                .setPixKey("SUACHAVEPIX")
                .setDescription("DESCRICAO")
                .setMerchantName("NOME")
                .setMerchantCity("CIDADE")
                .setAmount(new BigDecimal("1.00"))
                .setTxid("TXID1234");
        
        String qrcode = payload.getPayload();
        System.out.println(qrcode);
        
        ByteArrayOutputStream byteArrayOutputStream = QRCode
                .from(qrcode)
                .to(ImageType.JPG)
                .withSize(200, 200).stream();

        try (OutputStream outputStream = new FileOutputStream("qrcode.jpg")) {
            byteArrayOutputStream.writeTo(outputStream);
        }
    }
}

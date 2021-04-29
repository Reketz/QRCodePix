package br.com.systech.qrcodepix;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Payload {

    public static final String ID_PAYLOAD_FORMAT_INDICATOR = "00";
    public static final String ID_MERCHANT_ACCOUNT_INFORMATION = "26";
    public static final String ID_MERCHANT_ACCOUNT_INFORMATION_GUI = "00";
    public static final String ID_MERCHANT_ACCOUNT_INFORMATION_KEY = "01";
    public static final String ID_MERCHANT_ACCOUNT_INFORMATION_DESCRIPTION = "02";
    public static final String ID_MERCHANT_CATEGORY_CODE = "52";
    public static final String ID_TRANSACTION_CURRENCY = "53";
    public static final String ID_TRANSACTION_AMOUNT = "54";
    public static final String ID_COUNTRY_CODE = "58";
    public static final String ID_MERCHANT_NAME = "59";
    public static final String ID_MERCHANT_CITY = "60";
    public static final String ID_ADDITIONAL_DATA_FIELD_TEMPLATE = "62";
    public static final String ID_ADDITIONAL_DATA_FIELD_TEMPLATE_TXID = "05";
    public static final String ID_CRC16 = "63";

    private String pixKey;
    private String description;
    private String merchantName;
    private String merchantCity;
    private String txid;
    private String amount;

    public Payload setPixKey(String pixKey) {
        this.pixKey = pixKey;
        return this;
    }

    public Payload setDescription(String description) {
        this.description = description;
        return this;
    }

    public Payload setMerchantName(String merchantName) {
        this.merchantName = merchantName;
        return this;
    }

    public Payload setMerchantCity(String merchantCity) {
        this.merchantCity = merchantCity;
        return this;
    }

    public Payload setTxid(String txid) {
        this.txid = txid;
        return this;
    }

    public Payload setAmount(BigDecimal amount) {
        this.amount = amount.setScale(2, RoundingMode.UP).toString();
        return this;
    }

    private String getAddictionalDataFieldTemplate() {
        String tx = getFormatSize(ID_ADDITIONAL_DATA_FIELD_TEMPLATE_TXID, this.txid);
        return getFormatSize(ID_ADDITIONAL_DATA_FIELD_TEMPLATE, tx);
    }

    private String getMerchantAccountInformation() {
        String gui = getFormatSize(ID_MERCHANT_ACCOUNT_INFORMATION_GUI, "br.gov.bcb.pix");
        String key = getFormatSize(ID_MERCHANT_ACCOUNT_INFORMATION_KEY, this.pixKey);
        String desc = this.description != null && this.description.length() > 0 ? getFormatSize(ID_MERCHANT_ACCOUNT_INFORMATION_DESCRIPTION, this.description) : "";
        return getFormatSize(ID_MERCHANT_ACCOUNT_INFORMATION, gui + key + desc);
    }

    private String getFormatSize(String id, String value) {
        String size = String.format("%02d", value.length());
        return id + size + value;
    }

    public String getPayload() {
        String payload = getFormatSize(ID_PAYLOAD_FORMAT_INDICATOR, "01")
                + getMerchantAccountInformation()
                + getFormatSize(ID_MERCHANT_CATEGORY_CODE, "0000")
                + getFormatSize(ID_TRANSACTION_CURRENCY, "986")
                + getFormatSize(ID_TRANSACTION_AMOUNT, this.amount)
                + getFormatSize(ID_COUNTRY_CODE, "BR")
                + getFormatSize(ID_MERCHANT_NAME, this.merchantName)
                + getFormatSize(ID_MERCHANT_CITY, this.merchantCity)
                + getAddictionalDataFieldTemplate()
                + ID_CRC16 + "04";
        Integer crcRes = getCRC16(payload.getBytes());
        return payload + Integer.toHexString(crcRes).toUpperCase();
    }

    public int getCRC16(byte[] buffer) {
        int wCRCin = 0xffff;
        int wCPoly = 0x1021;
        for (byte b : buffer) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((wCRCin >> 15 & 1) == 1);
                wCRCin <<= 1;
                if (c15 ^ bit) {
                    wCRCin ^= wCPoly;
                }
            }
        }
        wCRCin &= 0xffff;
        return wCRCin ^= 0x0000;
    }

}

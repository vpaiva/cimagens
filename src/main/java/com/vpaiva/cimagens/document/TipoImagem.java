package com.vpaiva.cimagens.document;

public enum TipoImagem {
    JPEG("image/jpeg"), GIF("image/gif"), BMP("image/bmp"), PNG("image/png");

    private String mimeType;

    private TipoImagem(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public static TipoImagem getImagem(String mimeType) {

        for (TipoImagem tipoImagem : TipoImagem.values()) {
            if (tipoImagem.getMimeType().equals(mimeType)) {
                return tipoImagem;
            }
        }

        return null;
    }
}

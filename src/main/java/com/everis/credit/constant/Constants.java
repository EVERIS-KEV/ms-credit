package com.everis.credit.constant;

public enum Constants
{
    ;

    public static class Messages {
        public static final String REGISTERED_CREDIT = "Crédito registrado.";
        public static final String CLIENT_NO_MORE_CREDITS = "Usted ya no puede tener mas creditos.";
        public static final String CLIENT_NOT_REGISTERED = "Cliente no registrado";

        public static final String INCORRECT_OPERATION = "Operacion incorrecta.";
        public static final String INCORRECT_DATA = "Datos incorrectos.";
    }

    public static class Path{

        public static final String GATEWAY = "44.196.6.42:8090/";
        public static final String SERVICE_PATH = "service";
        public static final String HTTP_CONSTANT = "http://";

        public static final String CUSTOMERS_PATH = HTTP_CONSTANT.concat(GATEWAY).concat(SERVICE_PATH).concat("/customers");
        public static final String LOGIC_PATH = HTTP_CONSTANT.concat(GATEWAY).concat(SERVICE_PATH).concat("/logic");
    }
}

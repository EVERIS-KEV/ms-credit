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

        private static final String IPR = "host.docker.internal"; 
        private static final String PORT = "8090"; 

        private static final String GATEWAY = IPR.concat(":").concat(PORT);
        private static final String SERVICE_PATH = "/service";
        private static final String HTTP_CONSTANT = "http://";

        public static final String CUSTOMERS_PATH = HTTP_CONSTANT.concat(GATEWAY).concat(SERVICE_PATH).concat("/customers");
        public static final String LOGIC_PATH = HTTP_CONSTANT.concat(GATEWAY).concat(SERVICE_PATH).concat("/logic");
    }
}

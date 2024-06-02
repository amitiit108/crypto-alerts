package com.cryptoalerts.service;

public @interface Transactional {

    boolean readOnly();

}

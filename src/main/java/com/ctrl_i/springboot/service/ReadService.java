package com.ctrl_i.springboot.service;

import com.ctrl_i.springboot.dto.Envelope;

public interface ReadService {
    public Envelope rateWhileRead(String uid,int aid,double rate);
}

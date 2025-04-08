package com.pm.patientservice.exception;

import com.pm.patientservice.dto.PatientResponseDTO;

import java.util.UUID;

public class PatientNotFoundException extends RuntimeException{
    public PatientNotFoundException(String message){
        super(message);
    }
}

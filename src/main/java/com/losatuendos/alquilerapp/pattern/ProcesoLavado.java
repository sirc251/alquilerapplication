package com.losatuendos.alquilerapp.pattern;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

//Bridge pattern
@Service
public class ProcesoLavado {
    private final LavadoImplementacion impl;
    public ProcesoLavado(@Qualifier("estandar") LavadoImplementacion impl){
        this.impl = impl;
    }
    public void lavar(LotePrendas lote){
        impl.lavar(lote);
    }
}

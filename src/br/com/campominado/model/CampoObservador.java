package br.com.campominado.model;

@FunctionalInterface
public interface CampoObservador {

    public void EventoOcorreu(Campo campo, CampoEvento evento);
}

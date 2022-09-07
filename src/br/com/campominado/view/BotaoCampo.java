package br.com.campominado.view;

import br.com.campominado.model.Campo;
import br.com.campominado.model.CampoEvento;
import br.com.campominado.model.CampoObservador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BotaoCampo extends JButton implements CampoObservador, MouseListener {

    private Campo campo;
    private final Color BG_PADRAO = new Color(184,184,184);
    private final Color BG_MARCAR = new Color(8,179,247);
    private final Color BG_EXPLODIR = new Color(246, 14, 17);
    private final Color TEXTO_VERDE = new Color(44, 105, 14);

    public BotaoCampo(Campo campo) {
        this.campo = campo;
        setBackground(BG_PADRAO);
        setOpaque(true);
        setBorder(BorderFactory.createBevelBorder(0));

        addMouseListener(this);
        campo.registrarObservador(this);
    }

    @Override
    public void EventoOcorreu(Campo campo, CampoEvento evento) {
        switch (evento){
            case ABRIR:
                aplicarEstiloAbrir();
                break;
            case MARCAR:
                aplicarEstiloMarcar();
                break;
            case EXPLODIR:
                aplicarEstiloExplodir();
                break;
            default:
                aplicarEstiloPadrao();
        }
        SwingUtilities.invokeLater(() ->{
            repaint();
            validate();
        });
    }

    private void aplicarEstiloPadrao() {
        setBackground(BG_PADRAO);
        setText("");
        setOpaque(true);
        setBorder(BorderFactory.createBevelBorder(0));
    }

    private void aplicarEstiloExplodir() {

        setBackground(BG_EXPLODIR);
        setForeground(Color.WHITE);
        setText("X");
    }

    private void aplicarEstiloMarcar() {
        setBackground(BG_MARCAR);
        setForeground(Color.BLACK);
        setText("M");
        //setIcon();
    }

    private void aplicarEstiloAbrir() {
        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        if(campo.isMinado()){
            setBackground(BG_EXPLODIR);
            return;
        }
        switch (campo.minasNaVizinhanca()){
            case 1:
                setForeground(TEXTO_VERDE);
                break;
            case 2:
                setForeground(Color.BLUE);
                break;
            case 3:
                setForeground(Color.YELLOW);
                break;
            case 4,5,6:
                setForeground(Color.RED);
                break;
            default:
                setForeground(Color.PINK);
        }
        String valor = !campo.vizinhancaSegura() ? campo.minasNaVizinhanca() + "" : "";
        setText(valor);

    }

    /*
    Interfaces do mouse
     */

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 1){
            campo.abrir();
        }else{
            campo.alternarMarcacao();
        }

    }
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}

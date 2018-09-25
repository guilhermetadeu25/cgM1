/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetom1;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author guilh
 */
public class Recursos {

    int lastX = 2;
    Random cores = new Random();
    long tempo;
    long ultimaBola = 0;
    int xSeta = 420;
    int ySeta = 720;

    public void LimpaTela(Graphics g, int x, int y) {
        g.setColor(new Color(230,230,250));

        g.fillRect(0, 0, x, y);
    }

    public void GeraLinha(Graphics g, int x, int y) {

        g.setColor(Color.BLACK);

        g.drawLine(0, 700, x, 700);
        g.drawLine(0, 701, x, 701);
        g.drawLine(0, 702, x, 702);
    }

    public void GeraImagem(ArrayList<BolaBase> lista, Graphics g) {
        for (BolaBase p : lista) {
            p.criaImagem(g);
        }
    }

    public void GeraMovimento(ArrayList<BolaBase> lista) {
        for (BolaBase p : lista) {
            p.movimento();
        }
    }

    public void VerificaLateral(int width, int height, ArrayList<BolaBase> lista) {
        for (BolaBase p : lista) {
            p.verificaLateral(width, height);
        }
    }

    public ArrayList<BolaBase> GeraBolas() {
        ArrayList<BolaBase> lista = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            for (int j = 1; j < 17; j++) {
                int cor = cores.nextInt(5);
                BolaMorre b = new BolaMorre("imagem/bola" + cor + ".png");
                b.setX((lastX += 50));
                b.setY(50 * i);
                b.setOp(cor);
                lista.add(b);
            }

            if (i % 2 == 0) {
                lastX = 2;
            } else {
                lastX = 20;
            }
        }

        return lista;
    }

    public BolaAtira GeraBolaPrincipal() {
        int cor = cores.nextInt(5);
        BolaAtira bola = new BolaAtira("imagem/bola" + cor + ".png");
        bola.Gerar(400, 720);
        bola.setOp(cor);

        return bola;
    }

    long pegaTempo() {
        return System.currentTimeMillis();
    }

    public void GeraTiro(BolaAtira bola) {
        bola.setIncX(0);
        bola.setIncY(-1);
        bola.setY(675);

    }

    public boolean VerificaOP(BolaBase principal, BolaBase morre) {
        if (principal.getOp() == morre.getOp()) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<BolaBase> verificaColisao(ArrayList<BolaBase> principal, ArrayList<BolaBase> cima) {

        //lista que será retornada para sumir
        ArrayList<BolaBase> some = new ArrayList();

        //cria uma copia de principal
        ArrayList<BolaBase> copia;

        //faz a copia
        copia = principal;

        for (BolaBase p : principal) {
            for (BolaBase c : cima) {
                if (p.acertou(c) && this.VerificaOP(p, c)) {
                    some.add(p);
                    some.add(c);
                } else if (p.acertou(c) && !this.VerificaOP(p, c)) {
                    p.setIncY(0);
                    cima.add(p);
                    break;
                }
            }
        }

        //quando atirar, precisa fazr a bola principal sair da lista principal e ir para a lista de bolas de cima
        //add tempo para tirar a intersect inicial
        /*
        for (BolaBase a : principal) {
            for (BolaBase b : copia) {
                if (a.acertou(b)) {
                    some.add(a);
                }
            }
        }*/
        return some;
    }

    void GeraSeta(Graphics g, boolean direita, boolean esquerda, BolaAtira bola,int height) {
        /*
        if (direita && xSeta < 470) {
            xSeta += 1;
        }

        if (esquerda && xSeta > 380) {
            xSeta -= 1;
        }

        g.drawLine(420, ySeta, xSeta, 680);
        g.drawLine(419, ySeta, (xSeta - 1), 680);
        g.drawLine(421, ySeta, (xSeta + 1), 680);
        */
        
        if(esquerda && bola.getX() > 0)
            bola.setIncX(-1);
        else{
            if(direita && bola.getX() < 900 - bola.getLargura())
                bola.setIncX(1);
            else
                bola.setIncX(0);
        }
    }

    void SomeBolas(ArrayList<BolaBase> lista, ArrayList<BolaBase> listaPrincipal, ArrayList<BolaBase> some) {
        lista.removeAll(some);

        listaPrincipal.removeAll(some);

        some.clear();
    }

    public void TocaMusica(){
        try {           
            URL caminho = new URL("file:///C:/Users/guilh/Documents/NetBeansProjects/ProjetoM1/build/classes/musica/musicaFundoWav.wav");
            
            AudioInputStream audio = AudioSystem.getAudioInputStream(caminho);

            Clip clip = AudioSystem.getClip();
            
            clip.open(audio);
            
            clip.start();
            
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            Logger.getLogger(Recursos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean verificaLinha(ArrayList<BolaBase> lista, int altura) {
        for(BolaBase b : lista)
        {
            if(b.getY() > 600)
                return true;
        }
        
        return false;
    }
}

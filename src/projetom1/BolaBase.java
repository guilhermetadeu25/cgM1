/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetom1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author guilh
 */
public abstract class BolaBase {

    protected int x = 0;
    protected float testeX = 0;
    protected int y = 0;
    protected float testeY = 0;
    protected double incX = 0;
    protected double incY = 0;
    protected int largura = 0;
    protected int altura = 0;
    protected int op;
    protected ImageIcon imagem;
    protected Rectangle retanguloFundo = new Rectangle(0, 0, 30, 30);

    public BolaBase() {
    }

    public BolaBase(String url) {
        imagem = new ImageIcon(this.getClass().getResource("/").getPath() + url);
        largura = imagem.getIconWidth();
        altura = imagem.getIconHeight();
        retanguloFundo.height = altura;
        retanguloFundo.width = largura;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }
    
    
    public float getTesteX() {
        return testeX;
    }

    public void setTesteX(float testeX) {
        this.testeX = testeX;
    }

    public float getTesteY() {
        return testeY;
    }

    public void setTesteY(float testeY) {
        this.testeY = testeY;
    }

    public float getLargura() {
        return largura;
    }

    public float getAltura() {
        return altura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
        this.retanguloFundo.width = largura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
        this.retanguloFundo.width = altura;
    }

    public int getX() {
        return x;
    }

    public void setX(int x){
        this.x = x;
        this.retanguloFundo.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        this.retanguloFundo.y = y;
    }

    public double getIncX() {
        return incX;
    }

    public void setIncX(float incX) {
        this.incX = incX;
    }

    public double getIncY() {
        return incY;
    }

    public void setIncY(float incY) {
        this.incY = incY;
    }

    public void criaImagem(Graphics g) {
        if (imagem != null) {
            g.drawImage(imagem.getImage(), x, y, null);
        }
    }

    public void movimento() {
        x += incX;
        y += incY;
        this.retanguloFundo.x = x;
        this.retanguloFundo.y = y;
    }

    public Paredes verificaLateral(int largura, int altura){
        Paredes lado = Paredes.NENHUMA;
        
        if (x < 0){
            incX = (incX * -1);
            lado = Paredes.ESQUERDA;
        }
        else if(x > largura - 55){
            incX = (incX * -1);
            lado = Paredes.DIREITA;
        }
        
        if(y < 28){
            incY = (incY * -1);
            lado = Paredes.BAIXO;
        }
        else if(y > altura - this.altura){
            incY = (incY * -1);
            lado = Paredes.BAIXO;
        }
        
        return lado;
    }
    public void atirar()
    {
        this.setIncY(-1);
    }
    
    public boolean acertou(BolaBase outra){
        if(this.equals(outra))
            return this.retanguloFundo.intersects(outra.retanguloFundo);
        
        else
            return this.retanguloFundo.intersects(outra.retanguloFundo);
    }
}

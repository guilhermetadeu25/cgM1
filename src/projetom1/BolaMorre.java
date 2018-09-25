/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetom1;

import java.awt.Graphics;

/**
 *
 * @author guilh
 */
public class BolaMorre extends BolaBase {

    public BolaMorre(String url) {
        super(url);
    }

    @Override
    public void criaImagem(Graphics g) {
        if (imagem != null) {
            g.drawImage(imagem.getImage(), x, y, null);
        }
    }

    public void Gerar(int x, int y, Graphics g) {
        this.setX(x);
        this.setY(y);
        this.criaImagem(g);
    }
}

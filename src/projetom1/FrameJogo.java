/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetom1;

import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author guilh
 */
public class FrameJogo extends javax.swing.JFrame implements Runnable {

    private boolean esquerda;
    private boolean direita;
    private boolean atira;
    private boolean começaGame = false;
    private final Recursos recursos = new Recursos();
    private long ultimaBola = 0;
    private long tempo;
    private MediaPlayer tocador;
    private boolean fim = false;

    public FrameJogo() {
        initComponents();
        createBufferStrategy(2);
        Thread t = new Thread(this);
        t.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bolinhas - 150654");
        setMinimumSize(new java.awt.Dimension(900, 800));
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 770, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 541, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            atira = true;
        }

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            começaGame = true;
        }

        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            esquerda = true;
        }

        if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            direita = true;
        }
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            atira = false;
        }

        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            esquerda = false;
        }

        if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            direita = false;
        }
    }//GEN-LAST:event_formKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrameJogo().setVisible(true);
            }
        });
    }

    @Override
    public void run() {

        Graphics g;

        //cria o random pra imagens
        Random cores = new Random();
        
        //começa a tocar a musica do game
        recursos.TocaMusica();
                
        
        //cria random de 0 até 4
        int cor = cores.nextInt(4);

        //bola de baixo cores random        
        BolaAtira bolaPrincipal = new BolaAtira();

        //lista para as bolas de cima
        ArrayList<BolaBase> lista = new ArrayList();
        //lista para adicionar as bolas acertadas
        ArrayList<BolaBase> some = new ArrayList();
        //lista de bolas principais
        ArrayList<BolaBase> listaPrincipal = new ArrayList();

        //lista de bolas de cima
        lista = recursos.GeraBolas();

        //bola principal
        bolaPrincipal = recursos.GeraBolaPrincipal();
        
        while (true) {

            g = getBufferStrategy().getDrawGraphics();

            if (começaGame == false || fim == true) {
                
                //tela inicial
                PlanoFundo fundo = new PlanoFundo("imagem/planoFundo.png");
                fundo.criaImagem(g);
                
            } 
            
            else {

                //metodo que limpa a tela e gera a linha
                recursos.LimpaTela(g, getWidth(), getHeight());
                recursos.GeraLinha(g, getWidth(), getHeight());

                //pega o tempo do sistema
                tempo = recursos.pegaTempo();

                //gera a imagem das bolas
                recursos.GeraImagem(lista, g);

                
                //desenha, movimenta e verifica colisão das bolas atiradas
                recursos.GeraImagem(listaPrincipal, g);
                recursos.GeraMovimento(listaPrincipal);
                recursos.VerificaLateral(getWidth(), getHeight(), listaPrincipal);

                
                //desenha a seta e seu movimento
                recursos.GeraSeta(g, direita, esquerda, bolaPrincipal,getHeight());

                bolaPrincipal.movimento();
                
                //verifica se acertou a mesma cor
                some = recursos.verificaColisao(listaPrincipal, lista);

                //desenha a imagem da bola principal
                bolaPrincipal.criaImagem(g);

                if (atira && tempo > ultimaBola + 500) {
                    ultimaBola = tempo;

                    recursos.GeraTiro(bolaPrincipal);
                    
                    listaPrincipal.add(bolaPrincipal);
                    //bolaPrincipal.movimento();
                    
                    
                        //recursos
                        if(tempo > ultimaBola + 1000)
                        {
                            lista.add(bolaPrincipal);
                            listaPrincipal.remove(bolaPrincipal);
                        }            
                    
                    bolaPrincipal = recursos.GeraBolaPrincipal();
                }

                //remove as bolas da tela
                recursos.SomeBolas(lista, listaPrincipal, some);
                
                fim = recursos.verificaLinha(lista, getHeight());

            }
                g.dispose();
                getBufferStrategy().show();

                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                }
            
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

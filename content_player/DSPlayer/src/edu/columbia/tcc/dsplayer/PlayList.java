/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.columbia.tcc.dsplayer;

import edu.columbia.tcc.ejb.facade.ContentFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tokio
 */
public final class PlayList extends Thread {
    public ContentFacade contentEJB = new ContentFacade();
    private static final String PLAYER_HOST = "http://localhost/";
    private Integer currentAudience;
    
    List<String> listContent = null;
    int listIntex = 0;

    public PlayList() {
        updateContentList();
    }

    public Integer getCurrentAudience() {
        return currentAudience;
    }

    public void setCurrentAudience(Integer currentAudience) {
        this.currentAudience = currentAudience;
    }

    public void updateContentList() {
        try {
            listContent = contentEJB.lisContent();
        } catch (Exception ex) {
            Logger.getLogger(PlayList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getListSize() {
        return listContent == null ? 0 : listContent.size();
    }

    public void incrementListIntex() {
        this.listIntex++;
        if (listIntex >= getListSize()) {
            listIntex = 0;
        }
        System.out.println("Content: "+getContentName());
    }
    
    public void decrementListIntex() {
        this.listIntex--;
        if (listIntex < 0) {
            listIntex = getListSize() - 1;
        }
        System.out.println("Content: "+getContentName());
    }

    public String getContent() {
        return listContent != null ? PLAYER_HOST + listContent.get(listIntex) : PLAYER_HOST;
    }
    
    public String getContentName() {
        return listContent != null ? listContent.get(listIntex) : "";
    }

    @Override
    public void run() {
        while (true) {
            try {
                listContent = contentEJB.lisContent();
                Thread.sleep(10000);
            } catch (Exception ex) {
                Logger.getLogger(PlayList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}

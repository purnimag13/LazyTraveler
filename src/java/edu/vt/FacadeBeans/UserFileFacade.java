/*
 * Created by Tara Laughlin on 2018.11.19  * 
 * Copyright © 2018 Tara Laughlin. All rights reserved. * 
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.UserFile;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author taralaughlin
 */
@Stateless
public class UserFileFacade extends AbstractFacade<UserFile> {

    @PersistenceContext(unitName = "TheLazyTravelerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFileFacade() {
        super(UserFile.class);
    }
    
}

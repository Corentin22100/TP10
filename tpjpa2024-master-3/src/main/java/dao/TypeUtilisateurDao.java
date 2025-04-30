package dao;

import domain.TypeUtilisateur;
import jpa.EntityManagerHelper;

public class TypeUtilisateurDao {

    public TypeUtilisateur save(TypeUtilisateur t) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(t);
        EntityManagerHelper.commit();
        return t;
    }

    public TypeUtilisateur read(Long id) {
        return EntityManagerHelper.getEntityManager().find(TypeUtilisateur.class, id);
    }

    public TypeUtilisateur update(TypeUtilisateur t) {
        EntityManagerHelper.beginTransaction();
        TypeUtilisateur r = EntityManagerHelper.getEntityManager().merge(t);
        EntityManagerHelper.commit();
        return r;
    }

    public void delete(TypeUtilisateur t) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().remove(t);
        EntityManagerHelper.commit();
    }
}
